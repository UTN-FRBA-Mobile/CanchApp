package com.santiago.canchaapp;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.google.android.gms.common.data.BitmapTeleporter;
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
import com.santiago.canchaapp.dominio.Usuario;
import com.santiago.canchaapp.servicios.Sesion;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, DialogoTengoClub.DialogoTengoClubListener{
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private GoogleApiClient googleApiClient;
    private DatabaseReference referenceUser;
    private ValueEventListener valueEventListener;
    private Context context;
    @BindView(R.id.btnLogin)
    public SignInButton btnGoogleLogin;
    @BindView(R.id.progressBar)
    public ProgressBar progressBar;
    final boolean[] gotResult = new boolean[1];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        context = getApplicationContext();
        setGoogleApiClient();
        setBtnLogin();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    showActivityFinal(user);
                }
                else {
                    changeVisibilityLoadToButton();
                }
            }
        };
    }

    private void showActivityFinal(FirebaseUser user) {
        gotResult[0] = false;
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                gotResult[0] = true;
                if (dataSnapshot.getValue() != null) {
                    Usuario usuario = dataSnapshot.getValue(Usuario.class);
                    setearUsuarioEnSesion(usuario);
                    goMainScreen(usuario.getEsDuenio());
                } else {
                    showDialog();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                gotResult[0] = true;
                changeVisibilityLoadToButton();
            }
        };
        referenceUser = DataBase.getInstancia().getReferenceUser(user.getUid());
        if(DataBase.getInstancia().isOnline(context)) {
            DataBase.getInstancia().setTimeoutFirebase(referenceUser, valueEventListener, LoginActivity.this, new Runnable() {
                @Override
                public void run() {
                    if(!gotResult[0]) {
                        changeVisibilityLoadToButton();
                        showText(R.string.txtMalaConexion);
                    }
                }
            });
        }
        else{
            changeVisibilityLoadToButton();
            showText(R.string.txtSinConexion);
        }
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
        btnGoogleLogin.setColorScheme(SignInButton.COLOR_LIGHT);
        btnGoogleLogin.setOnClickListener(LoginActivity.this);
    }

    @Override
    public void onDialogPositiveClick(Boolean mostrarSeccionClub) {
        Usuario usuario = DataBase.getInstancia().insertUser(firebaseAuth.getCurrentUser(), mostrarSeccionClub);
        setearUsuarioEnSesion(usuario);
        goMainScreen(mostrarSeccionClub);
    }

    @Override
    public void onDialogNegativeClick() {
        firebaseAuth.signOut();
        try {
            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    changeVisibilityLoadToButton();
                    if (!status.isSuccess()) {
                        Toast.makeText(getApplicationContext(), R.string.notLogOut, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        catch(Exception e) {
            changeVisibilityLoadToButton();
            Toast.makeText(getApplicationContext(), R.string.notLogOut, Toast.LENGTH_SHORT).show();
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
        if (referenceUser != null) {
            referenceUser.removeEventListener(valueEventListener);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin: signIn(); break;
        }
    }

    private void signIn() {
        if(DataBase.getInstancia().isOnline(context)) {
            changeVisibilityButtonToLoad();
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
        else {
            showText(R.string.txtSinConexion);
        }
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
            showText(R.string.txtErrorLogin);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        int mensaje = R.string.notLogIn;
        if (result.isSuccess() && DataBase.getInstancia().isOnline(context))
            firebaseAuthWithGoogle(result.getSignInAccount());
        else {
            changeVisibilityLoadToButton();
            if(!DataBase.getInstancia().isOnline(context))
                mensaje = R.string.txtSinConexion;
            showText(mensaje);
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
        if(DataBase.getInstancia().isOnline(context)) {
            firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        changeVisibilityLoadToButton();
                        Toast.makeText(getApplicationContext(), R.string.notLogIn, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            changeVisibilityLoadToButton();
            showText(R.string.txtSinConexion);
        }
    }

    private void goMainScreen(boolean mostrarSeccionClub) {
        final Intent intent = new Intent(this, MenuNavegacion.class);
        intent.putExtra("mostrarSeccionClub", mostrarSeccionClub);
        startActivity(intent);
    }

    private void setearUsuarioEnSesion(Usuario usuario) {
        Sesion.getInstancia().setDatosUsuario(usuario);
    }

    private void showText(int idTxt){
        Toast.makeText(this, idTxt, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}

}