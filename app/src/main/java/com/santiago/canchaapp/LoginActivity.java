package com.santiago.canchaapp;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.santiago.canchaapp.dominio.DataBase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, DialogoTengoClub.DialogoTengoClubListener{
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private GoogleApiClient googleApiClient;
    @BindView(R.id.btnLogin)
    public SignInButton btnGoogleLogin;
    @BindView(R.id.progressBar)
    public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setGoogleApiClient();
        setBtnLogin();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null)
                    showActivityFinal(user);
                else
                    changeVisibilityLoadToButton();
            }
        };
    }

    private void showActivityFinal(FirebaseUser user) {
        DatabaseReference referenceUser = DataBase.getInstancia().getReferenceUser(user.getUid());
        referenceUser.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue() != null)
                            goMainScreen(Boolean.valueOf(dataSnapshot.child("tieneClub").toString()));
                        else
                            showDialog();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        changeVisibilityLoadToButton();
                        Toast.makeText(LoginActivity.this, R.string.txtErrorLogin, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void setGoogleApiClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(LoginActivity.this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void setBtnLogin() {
        btnGoogleLogin.setSize(SignInButton.SIZE_WIDE);
        btnGoogleLogin.setColorScheme(SignInButton.COLOR_DARK);
        btnGoogleLogin.setOnClickListener(LoginActivity.this);
    }

    @Override
    public void onDialogPositiveClick(Boolean mostrarSeccionClub) {
        DataBase.getInstancia().insertUser(firebaseAuth.getCurrentUser(), mostrarSeccionClub);
        goMainScreen(mostrarSeccionClub);
    }

    @Override
    public void onDialogNegativeClick() {
        firebaseAuth.signOut();
        try {
            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    if (!status.isSuccess())
                        Toast.makeText(getApplicationContext(), R.string.notLogOut, Toast.LENGTH_SHORT).show();
                    changeVisibilityLoadToButton();
                }
            });
        }
        catch(Exception e) {
            Toast.makeText(getApplicationContext(), R.string.notLogOut, Toast.LENGTH_SHORT).show();
            changeVisibilityLoadToButton();
        }
    }

    private void showDialog(){
        DialogFragment dialogoTengoClub = new DialogoTengoClub();
        dialogoTengoClub.setCancelable(false);
        dialogoTengoClub.show(this.getFragmentManager(), "dialogoTengoClub");
    }

    @Override
    protected void onStart() {
        super.onStart();
        changeVisibilityButtonToLoad();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAuthListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                signIn();
                break;
        }
    }

    private void signIn() {
        changeVisibilityButtonToLoad();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        else {
            changeVisibilityLoadToButton();
            Toast.makeText(LoginActivity.this, R.string.txtErrorLogin, Toast.LENGTH_LONG).show();

        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess())
            firebaseAuthWithGoogle(result.getSignInAccount());
        else {
            changeVisibilityLoadToButton();
            Toast.makeText(this, R.string.notLogIn, Toast.LENGTH_SHORT).show();
        }
    }

    private void changeVisibilityLoadToButton(){
        progressBar.setVisibility(View.GONE);
        btnGoogleLogin.setVisibility(View.VISIBLE);
    }

    private void changeVisibilityButtonToLoad(){
        progressBar.setVisibility(View.VISIBLE);
        btnGoogleLogin.setVisibility(View.GONE);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount signInAccount) {
        AuthCredential credential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    changeVisibilityLoadToButton();
                    Toast.makeText(getApplicationContext(), R.string.notFirebaseAuth, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goMainScreen(boolean mostrarSeccionClub) {
        final Intent intent = new Intent(this, MenuNavegacion.class);
        intent.putExtra("mostrarSeccionClub", mostrarSeccionClub);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}
}