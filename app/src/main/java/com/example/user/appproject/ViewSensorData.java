package com.example.user.appproject;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ViewSensorData extends AppCompatActivity {
    RequestQueue requestQueue;
    String showUrl = "http://ansellfamily3.000webhostapp.com/showSensorData.php";
   // String showUrl = "http://192.168.1.11/showSensorData.php";
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sensor_data);
        result = (TextView) findViewById(R.id.textView);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        getSensorData();
    }

    public void getSensorData(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                showUrl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
                try {
                    JSONArray sensor = response.getJSONArray("sensor");
                    for (int i = 0; i < sensor.length(); i++) {
                        JSONObject student = sensor.getJSONObject(i);

                        String value = student.getString("Value");

                        result.append("\n \n \n" + "Sensor value recieved:" + value +"\n \n");
                    }
                    result.append("");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.append(error.getMessage());

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

}
