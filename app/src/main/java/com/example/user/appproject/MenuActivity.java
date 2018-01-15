package com.example.user.appproject;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Vibrator;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MenuActivity extends AppCompatActivity {

    public String URL ="http://ansellfamily3.000webhostapp.com/checkForAlarm.php";
  // public String URL ="http://192.168.1.11/checkForAlarm.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        TextView welcome = (TextView) findViewById(R.id.WelcomeTxt);
        String[] items = {getResources().getString(R.string.ViewSensorData), getResources().getString(R.string.ViewTimestamp),
                getResources().getString(R.string.UpdateData),getResources().getString(R.string.Findstation)};
       final String message = getIntent().getExtras().getString("email");
        welcome.setText("Logged in as," + " " + message);
        checkIfAlarmActivated();
        ListView menu_items = (ListView) findViewById(R.id.item_list);

        ArrayAdapter<String> adapt = new ArrayAdapter<String>(this, R.layout.menu_item, items);


        menu_items.setAdapter(adapt);

        menu_items.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                TextView textView = (TextView) itemClicked;
                String strText = textView.getText().toString();
                if (strText.equalsIgnoreCase(getResources().getString(R.string.ViewSensorData))) {
                    startActivity(new Intent(MenuActivity.this,ViewSensorData.class));
                }

                if (strText.equalsIgnoreCase(getResources().getString(R.string.ViewTimestamp))) {
                    startActivity(new Intent(MenuActivity.this,ViewTimestamp.class));
                }

                if (strText.equalsIgnoreCase(getResources().getString(R.string.UpdateData))) {
                    Intent intent = new Intent(MenuActivity.this,UpdateActivity.class);
                    intent.putExtra("currentEmail",message);
                    startActivity(intent);
                }

                if (strText.equalsIgnoreCase(getResources().getString(R.string.Findstation))) {
                    startActivity(new Intent(MenuActivity.this,NearestStation.class));
                }

            }
        });
    }
    private void checkIfAlarmActivated(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("activated")) {
                            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            // Vibrate for 500 milliseconds
                            v.vibrate(500);
                            AlertDialog.Builder dialog = new AlertDialog.Builder(MenuActivity.this);
                            dialog.setCancelable(false);
                            dialog.setTitle("ALERT");
                            dialog.setMessage("Alarm has detected activity! view your alarm activity and alert the police!" );
                            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    //Action for "OK".
                                    dialog.dismiss();;
                                }
                            });


                            final AlertDialog alert = dialog.create();
                            alert.show();
                        }
                        else{

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    }

