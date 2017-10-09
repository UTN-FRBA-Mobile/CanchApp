package com.santiago.canchaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.santiago.canchaapp.app.fragment.CanchasFragment;
import com.santiago.canchaapp.app.fragment.RegistrarClubFragment;
import com.santiago.canchaapp.app.fragment.ReservasFragment;
import com.santiago.canchaapp.app.otros.FragmentTags;
import com.santiago.canchaapp.dominio.DataBase;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.santiago.canchaapp.app.otros.FragmentTags.MIS_ALQUILERES;
import static com.santiago.canchaapp.app.otros.FragmentTags.MIS_CANCHAS;
import static com.santiago.canchaapp.app.otros.FragmentTags.MIS_RESERVAS;
import static com.santiago.canchaapp.app.otros.FragmentTags.REGISTRAR_CLUB;


public class MenuNavegacion extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.nav_view)
    public NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    public DrawerLayout drawer;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    private FirebaseAuth firebaseAuth;
    private GoogleApiClient googleApiClient;
    public ActionBarDrawerToggle toggle;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_navegacion);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, 0, 0);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.navCerrarSesion:
                signOut(); break;
            case R.id.navRegistrarClub:
                abrirFragment(RegistrarClubFragment.nuevaInstancia(), REGISTRAR_CLUB); break;
            case R.id.navMisReservas:
                abrirFragment(ReservasFragment.nuevaInstanciaParaReservas(), MIS_RESERVAS); break;
            case R.id.navMisAlquileres:
                abrirFragment(ReservasFragment.nuevaInstanciaParaAlquileres(), MIS_ALQUILERES); break;
            case R.id.navMisCanchas:
                abrirFragment(CanchasFragment.nuevaInstancia(), MIS_CANCHAS); break;
        }
        return true;
    }

    private void abrirFragment(Fragment fragment, FragmentTags tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment, tag.toString())
                .addToBackStack(null)
                .commit();
        drawer.closeDrawer(GravityCompat.START);
    }

    private void signOut() {
        firebaseAuth.signOut();
        try {
            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    if (status.isSuccess())
                        goLogInScreen();
                    else
                        Toast.makeText(getApplicationContext(), R.string.notLogOut, Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch(Exception e) {
            Toast.makeText(getApplicationContext(), R.string.notLogOut, Toast.LENGTH_SHORT).show();
        }
    }

    private void goLogInScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


    protected void onStart() {
        super.onStart();
        user = firebaseAuth.getCurrentUser();
        setUserData(user);
        changeVisibleMenu();
    }

    private void changeVisibleMenu() {
        Bundle parametros = this.getIntent().getExtras();
        Boolean mostrarSeccionClub = parametros.getBoolean("mostrarSeccionClub");
        navigationView.getMenu().findItem(R.id.menuComplejo).setVisible(mostrarSeccionClub);
        if(mostrarSeccionClub)
            getInfoClub();
        else
            abrirFragment(ReservasFragment.nuevaInstanciaParaReservas(), MIS_RESERVAS);
    }

    private void getInfoClub() {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean tieneClub = dataSnapshot.getValue() != null;
                changeItemMenuClub(tieneClub);
                if(!tieneClub)
                    abrirFragment(RegistrarClubFragment.nuevaInstancia(), REGISTRAR_CLUB);
                else
                    abrirFragment(CanchasFragment.nuevaInstancia(), MIS_CANCHAS);
            }

            private void changeItemMenuClub(boolean mostrar) {

                navigationView.getMenu().findItem(R.id.navMisAlquileres).setVisible(mostrar);
                navigationView.getMenu().findItem(R.id.navMisCanchas).setVisible(mostrar);
                navigationView.getMenu().findItem(R.id.navMiClub).setVisible(mostrar);
                navigationView.getMenu().findItem(R.id.navRegistrarClub).setVisible(!mostrar);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MenuNavegacion.this, R.string.txtErrorLogin, Toast.LENGTH_LONG).show();
            }
        };
        DatabaseReference referenceUser = DataBase.getInstancia().getReferenceIdClubUser(user.getUid());
        referenceUser.addListenerForSingleValueEvent(valueEventListener);
    }

    private void setUserData(FirebaseUser user) {
        View headerLayout = navigationView.getHeaderView(0);
        ImageView imgPerfil = headerLayout.findViewById(R.id.imgPerfilGmail);
        TextView txtNombre = headerLayout.findViewById(R.id.txtNombreGmail);
        TextView txtEmail = headerLayout.findViewById(R.id.txtEmailGmail);
        txtNombre.setText(user.getDisplayName());
        txtEmail.setText(user.getEmail());
        Picasso.with(getApplicationContext()).load(user.getPhotoUrl()).into(imgPerfil);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}
}
