package com.sergiobt.ns10;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private String usuarioR, contrasenaR, usuarioE, contrasenaE;
    private EditText eUsuario, eContrasena;
    private GoogleApiClient googleApiClient;
    private SignInButton signInButton;
    public static final int SIGN_IN_CODE = 777;



    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        eUsuario = (EditText) findViewById(R.id.eUsuario);
        eContrasena = (EditText) findViewById(R.id.eContrasena);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN_CODE);
            }
        });


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            usuarioR = extras.getString("usuario");
            contrasenaR = extras.getString("contrasena");
        }

    }

    public void aceptar(View view) {

        //se realizan las validaciones, SI SE CUMPLEN:
        usuarioE = eUsuario.getText().toString();
        contrasenaE = eContrasena.getText().toString();


        if (usuarioE.equals(usuarioR) && contrasenaE.equals(contrasenaR)) {



            Intent intent = new Intent(LoginActivity.this,
                    MainActivity.class);
            prefs = getSharedPreferences("SP", Context.MODE_PRIVATE);
            editor = prefs.edit();

            editor.putInt("optlog", 1);
            editor.commit();
            intent.putExtra("usuario", usuarioR);
            intent.putExtra("contrasena", contrasenaR);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SIGN_IN_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSigninResult(result);
        }
        if (requestCode == 1234 && resultCode == RESULT_OK) {
            usuarioR = data.getExtras().getString("usuario");
            contrasenaR = data.getExtras().getString("contrasena");
            Toast.makeText(this, usuarioR, Toast.LENGTH_SHORT).show();
            Log.d("usuario", usuarioR); //visualizar en el monitor las variables
            Log.d("contrasena", contrasenaR);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleSigninResult(GoogleSignInResult result) {
        if (result.isSuccess()) {

            Intent intent = new Intent(this, MainActivity.class);
            prefs = getSharedPreferences("SP", Context.MODE_PRIVATE);
            editor = prefs.edit();
            //almacenamos el valor de optLog
            editor.putInt("optlog", 2);
            editor.commit();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        } else {
            Toast.makeText(this, "No se puede iniciar sesión", Toast.LENGTH_SHORT).show();
        }
    }

    public void Registrarse(View view) {
        Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
        //startActivityForResult(intent, 1234);
        startActivity(intent);
    }

    public void myMethod() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.sergiobt.ns10",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}