package net.fastboy.cus.cusfastboy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Base64;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.scottyab.aescrypt.AESCrypt;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by FastboyAdmin on 14/03/2017.
 */

public class HomeActivity extends Activity {
    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private static String url = "http://cus.fastboy.co/api-login?data=";
    String email, password, en_data, base_data, aes_data;
    String sttL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        Button bt_login = (Button) findViewById(R.id.login);

        if(MyCache.assetExists(HomeActivity.this,"cookie.txt")==true){
            Log.e("File exists","False");

        }else{
            Log.e("File exists","True");
            MyCache.deleteCache(HomeActivity.this);

        }

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText x_email = (EditText) findViewById(R.id.email);
                EditText x_pass = (EditText) findViewById(R.id.password);
                String get_email = x_email.getText().toString();
                String get_pass = x_pass.getText().toString();
                if (get_email.matches("") || get_pass.matches("")) {
                    TextView log = (TextView) findViewById(R.id.valid_pass);
                    log.setText("Invalid email or password");
                } else {
                    try {
                        String tab1 = "{\"username\":\"";
                        String tab2 = "\",\"pass\":\"";
                        String tab3 = "\"}";

                        Log.e("data", tab1 + get_email + tab2 + get_pass + tab3);
                        en_data = tab1 + get_email + tab2 + get_pass + tab3;
                        aes_data = AESCrypt.encrypt("FWEF943JFKDKS39R", en_data);
                        Log.e("EN_CODE", aes_data);
                        Log.e("DECODEa", AESCrypt.decrypt("FWEF943JFKDKS39R", aes_data));


                        new Getdata().execute(en_data);
                    } catch (GeneralSecurityException e) {
                        Log.e("DEMO ENCODE", "err");
                    }
                }
            }
        });
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class Getdata extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... param) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url + en_data);
            Log.e("URL", url + en_data);
            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {

                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String stt = jsonObj.getString("status");

                    Log.e("DATA Tra ve", stt);
                    if (stt.matches("success")) {

                        pDialog.dismiss();
                        String newJson = jsonStr.replace("{\"status\":\"success\",\"data\":\"", "");
                        String xnewJson = newJson.replace("\\", "");
                        Log.e("xnewJson", xnewJson);
                        String xlast_json = xnewJson.replace("}\"}", "}");
                        Log.e("last_json", xlast_json);
                        JSONObject xjsonObj = new JSONObject(xlast_json);
                        String nameshop = xjsonObj.getString("name_shop");
                        Log.e("nameshop", nameshop);
                        String email = xjsonObj.getString("email");
                        String user_name = xjsonObj.getString("user_name");
                        String phone = xjsonObj.getString("phone");

                        String cache = "{\"status\":\"success\",\"nameshop\":\""+nameshop + "\":\","+"\"email\":\""+email+"\",\"username\":\""+user_name+"\",\"phone\":\""+phone+"\"}";
                        Log.e("cache",cache);
                        MyCache.writeAllCachedText(HomeActivity.this, "cookie.txt",cache);

                        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                        intent.putExtra("data", nameshop);
                        startActivity(intent);

                    } else if (stt.matches("error")) {

                        String message = jsonObj.getString("message");
                        Log.e("DATA message", message);
                        //alert
                        pDialog.dismiss();
                        runOnUiThread(new Runnable() {

                            public void run() {

                                Toast.makeText(getApplicationContext(), "Your email or password failse", Toast.LENGTH_SHORT).show();
                                dialogBox("1");
                            }
                        });
                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(HomeActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }
    }

    public void dialogBox(String key) {
        TextView tv = (TextView) findViewById(R.id.valid_pass);
        if (key == "1") {
            tv.setText("Your username/password combination was incorrect");
        } else if (key == "0") {
            tv.setText("");
        }
    }
}
