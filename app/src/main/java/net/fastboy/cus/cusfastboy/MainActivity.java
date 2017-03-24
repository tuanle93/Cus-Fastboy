package net.fastboy.cus.cusfastboy;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.icu.text.DecimalFormatSymbols;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    EditText numberphone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        Intent intent = getIntent();
        Bundle intentBundle = intent.getExtras();
        String data = intentBundle.getString("data");
        try {
           String read =  MyCache.readAllCachedText(MainActivity.this, "cookie.txt");
            Log.e("TXT", read);
        }catch (Exception e){
            Log.e("Err0", e.toString());
        }
        TextView tv = (TextView) findViewById(R.id.namecompany);
        tv.setText(data);

        numberphone = (EditText)findViewById(R.id.numberphone);

    }

}
