package com.example.user.appproject;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class UserLogin extends AppCompatActivity implements View.OnClickListener{

    public static final String LOGIN_URL = "http://ansellfamily3.000webhostapp.com/userLogin.php";
    //public static final String LOGIN_URL = "http://192.168.1.11/userLogin.php";
    public static final String KEY_EMAIL="email";
    public static final String KEY_PASSWORD="password";

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private String email;
    private String password;
    public Boolean CheckEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        editTextEmail = (EditText) findViewById(R.id.edtLogin1);
        editTextPassword = (EditText) findViewById(R.id.edtLogin2);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(this);
    }


    private void userLogin() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Validating please wait");
        dialog.setProgressStyle(dialog.STYLE_SPINNER);
        dialog.show();
        email = editTextEmail.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            openMenu();
                            dialog.dismiss();
                        }else{
                            Toast.makeText(UserLogin.this,response,Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserLogin.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_EMAIL,email);
                map.put(KEY_PASSWORD,password);
                return map;
            }
        };

        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void openMenu(){
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra(KEY_EMAIL, email);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        checkIfEditextsAreEmpty();
        if(CheckEditText) {
            userLogin();
        }
        else {

            Toast.makeText(UserLogin.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();
        }

    }

    private void checkIfEditextsAreEmpty(){
        // Getting values from EditText.
        String emailHolder = editTextEmail.getText().toString().trim();
       String PasswordHolder = editTextPassword.getText().toString().trim();

        // Checking whether EditText value is empty or not.
        if (TextUtils.isEmpty(emailHolder) || TextUtils.isEmpty(PasswordHolder)) {

            // If any of EditText is empty then set variable value as False.
            CheckEditText = false;

        } else {

            // If any of EditText is filled then set variable value as True.
            CheckEditText = true;
        }
    }

}
