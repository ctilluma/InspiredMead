package com.inspiredpanama.inspiredmead;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

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

        EditText name = (EditText) findViewById(R.id.md_name);
        EditText og = (EditText) findViewById(R.id.md_OG);
        EditText sg = (EditText) findViewById(R.id.md_SG);
        EditText alcohol = (EditText) findViewById(R.id.md_alcohol);

        name.setText("");
        name.setText(myMead.getName());
        if (myMead.getOG() > 0.900) {
            og.setText(String.valueOf(myMead.getOG()));
        }
        if (myMead.getLastTest() != null) {
            sg.setText(String.valueOf(myMead.getLastTest().getTestGravity()));
        }
        if (myMead.getAlcohol() >= 0.000 ) {
            alcohol.setText(String.valueOf(myMead.getAlcohol()));
        }
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

}

