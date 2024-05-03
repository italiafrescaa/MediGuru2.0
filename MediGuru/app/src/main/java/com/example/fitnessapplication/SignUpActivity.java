package com.example.fitnessapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fitnessapplication.helpers.StringHelper;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    EditText nome, cognome, email, password, conferma;
    Button sign_up_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nome = findViewById(R.id.nome);
        cognome = findViewById(R.id.cognome);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        conferma = findViewById(R.id.confirm);

        sign_up_btn = findViewById(R.id.sign_up_btn);

        sign_up_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                processFormField();
            }
        });
    }

    public void goToSignInAct(View view){
        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    public void processFormField(){
        if(!validateNome() || !validateCognome() || !validateEmail() || !validatePasswordAndConfirm()){
            return;
        }

        RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);

        String url = "http://192.168.1.2:9080/api/v1/user/register";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equalsIgnoreCase("success")) {  //se la risposta del server è "success"
                    nome.setText(null);
                    cognome.setText(null);
                    email.setText(null);
                    password.setText(null);
                    conferma.setText(null);
                    Toast.makeText(SignUpActivity.this, "Registrazione avvenuta con successo", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                System.out.println(error.getMessage());
                Toast.makeText(SignUpActivity.this, "Errore nella registrazione", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nome", nome.getText().toString());
                params.put("cognome", cognome.getText().toString());
                params.put("email", email.getText().toString());
                params.put("password", password.getText().toString());

                return params;
            }
        };

        queue.add(stringRequest);

    }


    public boolean validateNome(){
        String name = nome.getText().toString();

        if(name.isEmpty()){
            nome.setError("Il nome non deve essere vuoto");
            return false;
        }
        else{
            nome.setError(null);
            return true;
        }
    }

    public boolean validateCognome(){
        String surname = cognome.getText().toString();

        if(surname.isEmpty()){
            cognome.setError("Il cognome non deve essere vuoto");
            return false;
        }
        else{
            cognome.setError(null);
            return true;
        }
    }

    public boolean validateEmail(){
        String email_e = email.getText().toString();

        if(email_e.isEmpty()){
            email.setError("L'email non deve essere vuota");
            return false;
        }
        else if(!StringHelper.regexEmailValidationPattern(email_e)){
            email.setError("Indirizzo email non valido");
            return false;
        }
        else{
            email.setError(null);
            return true;
        }
    }

    public boolean validatePasswordAndConfirm(){
        String psw = password.getText().toString();
        String pswConfirm = conferma.getText().toString();

        if(psw.isEmpty()){
            password.setError("La password non può essere vuota");
            return false;
        }
        else if(pswConfirm.isEmpty()){
            conferma.setError("La conferma non può essere vuota");
            return false;
        }
        else if(!psw.equals(pswConfirm)){
            password.setError("Le password non corrispondono");
            return false;
        }
        else{
            password.setError(null);
            conferma.setError(null);
            return true;
        }
    }
}