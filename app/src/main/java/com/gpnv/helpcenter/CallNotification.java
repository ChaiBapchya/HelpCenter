package com.gpnv.helpcenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class CallNotification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_notification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button b = (Button) this.findViewById(R.id.call_store);
        //String store_number=DBAdapter.storeNumber;
        b.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+DBAdapter.storeNumber));
                startActivity(callIntent);

            }

        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
