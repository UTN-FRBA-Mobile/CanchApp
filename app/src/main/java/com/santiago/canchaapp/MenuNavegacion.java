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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_navegacion);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, 0, 0);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        //setUserData(user);

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
            default: Fragment fragment = new RegistrarClub();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
                drawer.closeDrawer(GravityCompat.START); break;
        }

        return true;
    }

    private void signOut() {
        firebaseAuth.signOut();
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    goLogInScreen();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.notLogOut, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goLogInScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


    protected void onStart() {
        super.onStart();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        View headerLayout = navigationView.getHeaderView(0);
        ImageView imgPerfil = headerLayout.findViewById(R.id.imgPerfilGmail);
        TextView txtNombre = headerLayout.findViewById(R.id.txtNombreGmail);
        TextView txtEmail = headerLayout.findViewById(R.id.txtEmailGmail);
        txtNombre.setText(user.getDisplayName());
        txtEmail.setText(user.getEmail());
        Picasso.with(getApplicationContext()).load(user.getPhotoUrl()).into(imgPerfil);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
