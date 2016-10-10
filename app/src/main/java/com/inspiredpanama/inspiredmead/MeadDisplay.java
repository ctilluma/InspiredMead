package com.inspiredpanama.inspiredmead;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MeadDisplay extends AppCompatActivity {
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

        //Load DataBase Record w/ Progress Display
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        myMead = db.getMeadRecordFromID(meadID);
        hidePDialog();

    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}

