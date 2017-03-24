package net.fastboy.cus.cusfastboy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by FastboyAdmin on 14/03/2017.
 */

public class SuccessActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String tmp = extras.getString("result");

        TextView namecompany = (TextView)findViewById(R.id.namecompany);
        namecompany.setText(tmp);

        final EditText numberphone = (EditText)findViewById(R.id.numberphone);
        numberphone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        Button enterphone = (Button)findViewById(R.id.enterphone);

        enterphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txphone = numberphone.getText().toString();
                int counter = 0;
                for( int i=0; i<txphone.length(); i++ ) {
                    if( txphone.charAt(i) == '$' ) {
                        counter++;
                    }
                }
                if(txphone.matches("")||counter<10){
                    Toast.makeText(SuccessActivity.this,"Please enter a valid phone number",Toast.LENGTH_LONG).show();
                    TextView phonestt = (TextView)findViewById(R.id.phonestt);
                    phonestt.setText("Please enter a valid phone number");
                }
            }
        });
    }

}