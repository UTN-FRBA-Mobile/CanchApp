package com.santiago.canchaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.santiago.canchaapp.app.fragment.AlquileresFragment;
import com.santiago.canchaapp.app.fragment.BuscarCanchasFragment;
import com.santiago.canchaapp.app.fragment.ClubFragment;
import com.santiago.canchaapp.app.fragment.RegistrarClubFragment;
import com.santiago.canchaapp.app.fragment.ReservasFragment;
import com.santiago.canchaapp.app.otros.FragmentTags;
import com.santiago.canchaapp.dominio.DataBase;
import com.santiago.canchaapp.dominio.Usuario;
import com.santiago.canchaapp.servicios.Sesion;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.santiago.canchaapp.app.otros.FragmentTags.BUSCAR_CANCHAS;
import static com.santiago.canchaapp.app.otros.FragmentTags.MIS_ALQUILERES;
import static com.santiago.canchaapp.app.otros.FragmentTags.MIS_RESERVAS;
import static com.santiago.canchaapp.app.otros.FragmentTags.MI_CLUB;
import static com.santiago.canchaapp.app.otros.FragmentTags.REGISTRAR_CLUB;

public class MenuNavegacion extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {

    private FirebaseAuth firebaseAuth;
    private GoogleApiClient googleApiClient;
    public ActionBarDrawerToggle toggle;

    @BindView(R.id.nav_view)
    public NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    public DrawerLayout drawer;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_navegacion);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, 0, 0);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        setGoogleSignIn();
        setUserData();
        changeVisibleMenu();
    }

    private void setGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
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
            case R.id.navBuscarCanchas:
                abrirFragment(BuscarCanchasFragment.nuevaInstancia(), BUSCAR_CANCHAS, true); break;
            case R.id.navRegistrarClub:
                abrirFragment(RegistrarClubFragment.nuevaInstancia(), REGISTRAR_CLUB, true); break;
            case R.id.navMisReservas:
                abrirFragment(ReservasFragment.nuevaInstancia(), MIS_RESERVAS, true); break;
            case R.id.navMisAlquileres:
                abrirFragment(AlquileresFragment.nuevaInstancia(), MIS_ALQUILERES, true); break;
            case R.id.navMiClub:
                abrirFragment(ClubFragment.nuevaInstancia(Sesion.getInstancia().getUsuario().getIdClub(), true), MI_CLUB, true); break;
        }
        return true;
    }

    private void abrirFragment(Fragment fragment, FragmentTags tag, boolean incluirEnBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment, tag.toString());
        if (incluirEnBackStack) {
            transaction.addToBackStack(tag.toString());
        }
        transaction.commit();
        drawer.closeDrawer(GravityCompat.START);
    }

    private void signOut() {
        firebaseAuth.signOut();
        try {
            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    if (status.isSuccess()) {
                        goLogInScreen();
                        finish();
                    }
                    else {
                        showToast(R.string.notLogOut);
                    }
                }
            });
        }
        catch(Exception e) {
            showToast(R.string.notLogOut);
        }
    }

    private void goLogInScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void changeVisibleMenu() {
        Bundle parametros = this.getIntent().getExtras();
        Boolean mostrarSeccionClub = parametros.getBoolean("mostrarSeccionClub");
        navigationView.getMenu().findItem(R.id.menuComplejo).setVisible(mostrarSeccionClub);
        if(mostrarSeccionClub) {
            getInfoClub();
        }
        else {
            abrirFragment(ReservasFragment.nuevaInstancia(), MIS_RESERVAS, false);
        }
    }

    private void getInfoClub() {
        Usuario user = Sesion.getInstancia().getUsuario();
        Boolean tieneClub = user.tieneClub();
        changeItemMenuClub(tieneClub);
        if(!tieneClub) {
            abrirFragment(RegistrarClubFragment.nuevaInstancia(), REGISTRAR_CLUB, false);
        }
        else{
            abrirFragment(ClubFragment.nuevaInstancia(Sesion.getInstancia().getUsuario().getIdClub(), true), MI_CLUB, false);
        }

    }

    private void changeItemMenuClub(boolean mostrar) {
        navigationView.getMenu().findItem(R.id.navMisAlquileres).setVisible(mostrar);
        navigationView.getMenu().findItem(R.id.navMiClub).setVisible(mostrar);
        navigationView.getMenu().findItem(R.id.navRegistrarClub).setVisible(!mostrar);
    }

    private void setUserData() {
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        View headerLayout = navigationView.getHeaderView(0);
        ImageView imgPerfil = headerLayout.findViewById(R.id.imgPerfilGmail);
        TextView txtNombre = headerLayout.findViewById(R.id.txtNombreGmail);
        TextView txtEmail = headerLayout.findViewById(R.id.txtEmailGmail);
        txtNombre.setText(user.getDisplayName());
        txtEmail.setText(user.getEmail());
        Picasso.with(getApplicationContext()).load(user.getPhotoUrl()).into(imgPerfil);
    }

    private void showToast(int idTxt) {
        Toast.makeText(getApplicationContext(), idTxt, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}
}
