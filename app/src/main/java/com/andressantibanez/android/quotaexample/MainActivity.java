package com.andressantibanez.android.quotaexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.andressantibanez.android.quota.QuotaLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QuotaLayout salesQuota = (QuotaLayout) findViewById(R.id.sales_quota);
        salesQuota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Clicked Sales!", Toast.LENGTH_LONG).show();
            }
        });

        QuotaLayout paymentsQuota = (QuotaLayout) findViewById(R.id.payments_quota);
        paymentsQuota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Clicked Payments!", Toast.LENGTH_LONG).show();
            }
        });
    }

}
