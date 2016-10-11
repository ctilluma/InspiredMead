package com.inspiredpanama.inspiredmead;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static java.lang.Math.round;

public class MeadDisplay extends AppCompatActivity {
    //STATICS
    public static final int DAYS_BETWEEN_TESTING = 60;

    //Variables
    Mead myMead;
    DBMead db;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Prep DataBase
        db = new DBMead(this);

        //Retrieve ID from Intent
        Intent intent = getIntent();
        long meadID = intent.getLongExtra("meadID", 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mead_display);

        myMead = db.getMeadRecordFromID(meadID);

        //Load DataBase Record w/ Progress Display
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        myMead = db.getMeadRecordFromID(meadID);
        hidePDialog();


        //Create Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();

        ab.show();

        //Display MeadData
        displayMead();

    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_mead_display, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_back:
                finish();
                return true;

            case R.id.action_settings:
                // Perform App Settings
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void displayMead() {
        //Display MeadData
        SimpleDateFormat dateFormatterDate = new SimpleDateFormat("yyyy.MM.dd");
        SimpleDateFormat dateFormatterTime = new SimpleDateFormat("HH:mm:ss zzz");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss zzz");


        TextView name = (TextView) findViewById(R.id.md_name);
        TextView og = (TextView) findViewById(R.id.md_OG);
        TextView sg = (TextView) findViewById(R.id.md_SG);
        TextView alcohol = (TextView) findViewById(R.id.md_alcohol);
        TextView startDate = (TextView) findViewById(R.id.md_start_date);
        TextView startTime = (TextView) findViewById(R.id.md_start_time);
        TextView sgDate = (TextView) findViewById(R.id.md_test_date);
        TextView capacity = (TextView) findViewById(R.id.md_capacity);
        TextView volume = (TextView) findViewById(R.id.md_volume);
        TextView estBottles = (TextView) findViewById(R.id.md_est_bottles);
        TextView curBottles = (TextView) findViewById(R.id.md_cur_bottles);

        //Set Name in Display
        name.setText("");
        name.setText(myMead.getName());

        //Set Original Gravity in Display
        if (myMead.getOG() > 0.900) {
            og.setText(String.valueOf(myMead.getOG()));
        }

        //Set CUrrent Specific Gravity in Display
        if (myMead.getLastTest() != null) {
            sg.setText(String.valueOf(myMead.getLastTest().getTestGravity()));
        }

        //Set Alcohol in Display
        if (myMead.getAlcohol() >= 0.000) {
            alcohol.setText(String.valueOf(myMead.getAlcohol()));
        }

        //Set Start Date in Display
        if (myMead.getStartDate() == null) {
            startDate.setText("0000.00.00");
            startTime.setText("00:00:00 EST");
        } else {
            startDate.setText(String.valueOf(dateFormatterDate.format(myMead.getStartDate().getTime())));
            startTime.setText(String.valueOf(dateFormatterTime.format(myMead.getStartDate().getTime())));
        }

        //Set Test Date in Display
        if (myMead.getLastTest() == null) {
            sgDate.setText("0000.00.00 00:00:00 EST");
            sgDate.setTextColor(ContextCompat.getColor(this, R.color.mead_red));
        } else {
            sgDate.setText(String.valueOf(dateFormatter.format(myMead.getLastTest().getTestDate().getTime())));
            GregorianCalendar checkDate = new GregorianCalendar();
            //Subtract DAYS_BETWEEN_TESTING from current Date
            checkDate.add(Calendar.DATE, (DAYS_BETWEEN_TESTING * -1));
            if (checkDate.after(myMead.getLastTest().getTestDate())) {
                sgDate.setTextColor(ContextCompat.getColor(this, R.color.mead_red));
            }
        }

        //Set Capacity Bottles in Display
        if (myMead.getVolume() >= 0.000) {
            capacity.setText(String.valueOf(myMead.getCapacity()));
        }

        //Set Volume Bottles in Display
        if (myMead.getVolume() >= 0.000) {
            volume.setText(String.valueOf(myMead.getVolume()));
        }

        //Set Estimated Bottles in Display
        if (myMead.getVolume() >= 0.000) {
            estBottles.setText(String.valueOf(round((myMead.getVolume() * 0.9) / 0.75)));
        }

        //Set Current Bottles in Display
        //TODO : Need to calculate current bottles from inventory database

    }

}

