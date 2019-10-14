package com.mkp.bisa;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("Registered")
public class Login extends AppCompatActivity {

    EditText username, password,text1,text2;
    Button btnLogin;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.userEmail);
        password = findViewById(R.id.userPassword);

        btnLogin = findViewById(R.id.loginButton);

        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String usernameKey = username.getText().toString();
                String passwordKey = password.getText().toString();


                if (usernameKey.equals("admin@massindo.com") && passwordKey.equals("mas5indo")) {
                    //jika login berhasil
                    Toast.makeText(getApplicationContext(), "LOGIN SUKSES",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, dashboard.class);
                    Login.this.startActivity(intent);
                    finish();
                }


                else {
                    //jika login gagal
                    AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                    builder.setMessage("Username atau Password Anda salah!")
                            .setNegativeButton("Retry", null).create().show();
                }
            }

        });

    }
}