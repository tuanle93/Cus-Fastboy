package net.fastboy.cus.cusfastboy;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by FastboyAdmin on 17/03/2017.
 */

public class CreatInforActivity extends Activity {

    int[] s_day = new int[30];
    private String[] m_arraySpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creatinfor);


        this.m_arraySpinner = new String[]{""};


        Spinner smonth = (Spinner) findViewById(R.id.spinner_day);
        ArrayAdapter<String> madapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, m_arraySpinner);
        smonth.setAdapter(madapter);

        smonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner sday = (Spinner) findViewById(R.id.spinner_day);
        ArrayAdapter<String> sadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, s_day[30]);
        sday.setAdapter(sadapter);
    }
}
