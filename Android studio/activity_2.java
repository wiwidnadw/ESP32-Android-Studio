// Little project from wiwidnadw
// program use java language
//u can reach me on https://github.com/wiwidnadw
//https://www.youtube.com/channel/UClxwaaJ-or0SJtlWMi3pHpA
// line : wiwidnadw
//gmail : nurahmaddw@gmail.com

package com.example.flowcalibration2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



import static com.example.flowcalibration2.activity_1.ip_address;


public class activity_2 extends AppCompatActivity {
    private static Button valve1on, valve1off, valve2on, valve2off, valve3on, valve3off, save_flow;
    private Spinner spflowunit;
    private String[] flowunit = {
            "L/h",
            "t/h", "m3/h",
    };

    TextView txvalue;
    Handler handler = new Handler();
    boolean statusdevice = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        spflowunit = (Spinner) findViewById(R.id.spinner);
        // inisialiasi Array Adapter dengan memasukkan string array di atas
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, flowunit);

        // mengeset Array Adapter tersebut ke Spinner
        spflowunit.setAdapter(adapter);
        save_flow = (Button) findViewById(R.id.save_flow);

        save_flow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView rangeunit = findViewById(R.id.range_unit);
                TextView txunit = findViewById(R.id.tx_unit);

                String setflowunit = String.valueOf(spflowunit.getSelectedItem());
                rangeunit.setText(setflowunit);
                txunit.setText(setflowunit);

            }
        });


        valve1on = (Button) findViewById(R.id.valve1_on);
        valve1off = (Button) findViewById(R.id.valve1_off);
        valve2on = (Button) findViewById(R.id.valve2_on);
        valve2off = (Button) findViewById(R.id.valve2_off);
        valve3on = (Button) findViewById(R.id.valve3_on);
        valve3off = (Button) findViewById(R.id.valve3_off);

        txvalue = (TextView ) findViewById(R.id.tx_value);

        handler.postDelayed(status_data,0);

        valve1on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    request_to_url("led1");
                }

            }
        });
        valve1off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        valve2on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        valve2off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        valve3on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        valve3off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private Runnable status_data = new Runnable() {
        @Override
        public void run() {
            if (statusdevice) {
                request_to_url("");
                handler.postDelayed(this, 2000);
                Log.d("Status", "Connectivity_esp32");
            }else {
                handler.removeCallbacks(status_data);
                Log.d("Status","Finalizado");
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        statusdevice = false;
    }

    public void request_to_url (String command) {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()) {

            new request_data().execute("http://" + ip_address + "/" + command);

        }else {
            Toast.makeText(activity_2.this, "Not connected  ", Toast.LENGTH_LONG).show();

        }
    }

    private class request_data extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            return Connectivity.geturl(url[0]);

        }

        @Override
        protected void onPostExecute(String result_data) {
            if(result_data !=null) {
                txvalue.setText(result_data);

            }else{

                Toast.makeText(activity_2.this, "Null data", Toast.LENGTH_LONG).show();
            }
        }
    }


}


