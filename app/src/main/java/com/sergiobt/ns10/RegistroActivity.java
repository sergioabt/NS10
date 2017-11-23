package com.sergiobt.ns10;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

public class RegistroActivity extends NavD implements NavigationView.OnNavigationItemSelectedListener {



    private String usuario, contrasena,correo;
    EditText eCorreo, eContrasena, eRepContrasena, eUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_registro);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.contenedor_nav);
        getLayoutInflater().inflate(R.layout.activity_registro, contentFrameLayout);
        Bundle extras = getIntent().getExtras();

        eUsuario = (EditText) findViewById(R.id.eUsuario);
        eCorreo = (EditText) findViewById(R.id.eCorreo);
        eContrasena = (EditText) findViewById(R.id.eContrasena);
        eRepContrasena = (EditText) findViewById(R.id.eRepContrasena);
    }

    public void registrar(View view) {
        usuario = eUsuario.getText().toString();
        contrasena = eContrasena.getText().toString();
        correo = eCorreo.getText().toString();

        Intent intent = new Intent();
        intent.putExtra("usuario", usuario);
        intent.putExtra("contrasena", contrasena);
        intent.putExtra("correo", correo);
        setResult(RESULT_OK, intent);
        finish();
    }
}
