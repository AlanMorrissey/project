package com.example.user.appproject;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
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


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText firstname, lastname,password,email;
    Button insert;
    RequestQueue requestQueue;
    public Boolean CheckEditText;
    String insertUrl = "http://ansellfamily3.000webhostapp.com/insertRecord.php";
   // String insertUrl = "http://192.168.1.9/insertRecord.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstname = (EditText) findViewById(R.id.edtUserFname);
        lastname = (EditText) findViewById(R.id.edtUserLname);
        password = (EditText) findViewById(R.id.UserRegPassEDT);
        insert = (Button) findViewById(R.id.buttonLogin);
        email = (EditText) findViewById(R.id.userRegEmailEDT);

        insert.setOnClickListener(this);
    }

    private void register(){
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Registering please wait");
        dialog.setProgressStyle(dialog.STYLE_SPINNER);
        dialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
            if(response.trim().equals("success")) {
                Intent intent = new Intent(MainActivity.this, UserLogin.class);
                startActivity(intent);
                dialog.dismiss();
                Log.d("RESPONSE", response);
            }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters  = new HashMap<String, String>();
                parameters.put("firstname",firstname.getText().toString());
                parameters.put("lastname",lastname.getText().toString());
                parameters.put("email",email.getText().toString());
                parameters.put("password",password.getText().toString());

                return parameters;
            }
        };

        request.setRetryPolicy(new RetryPolicy() {
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

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    @Override
    public void onClick(View v) {
        checkIfEditextsAreEmpty();
        if(CheckEditText) {
            register();
        }
        else {

            Toast.makeText(MainActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();
        }

    }

    private void checkIfEditextsAreEmpty(){
        // Getting values from EditText.
        String fname = firstname.getText().toString().trim();
        String lname = lastname.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String emailHolder = email.getText().toString().trim();

        // Checking whether EditText value is empty or not.
        if (TextUtils.isEmpty(fname) || TextUtils.isEmpty(lname) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(emailHolder)){

            // If any of EditText is empty then set variable value as False.
            CheckEditText = false;

        } else {

            // If any of EditText is filled then set variable value as True.
            CheckEditText = true;
        }
    }
    public void onLogin(View view){
        Intent intent = new Intent(MainActivity.this,UserLogin.class);
        startActivity(intent);
    }



}
