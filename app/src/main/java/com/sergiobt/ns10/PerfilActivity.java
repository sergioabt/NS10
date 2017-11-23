package com.sergiobt.ns10;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class PerfilActivity extends NavD implements GoogleApiClient.OnConnectionFailedListener, NavigationView.OnNavigationItemSelectedListener {

    private String usuarioR,contrasenaR;
    private TextView nombre, correo;
    private ImageView foto;
    private int optLog;
    private GoogleApiClient googleApiClient;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction.replace(R.id.content,new HomeFragment()).commit();
                    return true;
                case R.id.navigation_general:
                    transaction.replace(R.id.content,new GeneralFragment()).commit();
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_perfil);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.contenedor_nav);
        getLayoutInflater().inflate(R.layout.activity_perfil, contentFrameLayout);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content,new HomeFragment()).commit();




        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            usuarioR = extras.getString("usuario");
            contrasenaR = extras.getString("contrasena");
            optLog = extras.getInt("optLog");
                    }


        //nombre = (TextView) findViewById(R.id.tNombre);
        //correo = (TextView) findViewById(R.id.tCorreo);
        //foto = (ImageView) findViewById(R.id.iFoto);






    }
    @Override
    public boolean onCreateOptionsMenu(Menu menup) {
        getMenuInflater().inflate(R.menu.menup, menup);
        return super.onCreateOptionsMenu(menup);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;

        switch(id){
            case R.id.mPrincipal:
                intent =  new Intent(PerfilActivity.this,
                        MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.mCerrar:

                intent =  new Intent(PerfilActivity.this,
                        LoginActivity.class);
                prefs = getSharedPreferences("SP", Context.MODE_PRIVATE);
                editor = prefs.edit();

                editor.putInt("optlog", 0);
                editor.commit();
                intent.putExtra("usuario", usuarioR);
                intent.putExtra("contrasena", contrasenaR);

                startActivity(intent);
                finish();
                break;       }



        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
