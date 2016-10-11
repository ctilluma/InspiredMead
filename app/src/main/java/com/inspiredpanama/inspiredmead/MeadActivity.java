package com.inspiredpanama.inspiredmead;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.GregorianCalendar;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MeadActivity extends AppCompatActivity {
    //Variables
    private ProgressDialog pDialog;
    private List<MeadData> mead;
    private ListView listView;
    private MeadListAdapter mAdapter;
    private DBMead db;

    private List<MeadData> getAllMeadFromDB() {
        return db.getAllMead();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mead);

        db = new DBMead(this);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        mead = getAllMeadFromDB();
        hidePDialog();

        //Create and populate List
        listView = (ListView) findViewById(R.id.list);
        mAdapter = new MeadListAdapter(this, mead);
        listView.setAdapter(mAdapter);
        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {

                //Start new activity and send ID of mead selection.
                Intent myIntent = new Intent(MeadActivity.this, MeadDisplay.class);
                myIntent.putExtra("meadID", mead.get(position).getId()); //Optional parameters
                MeadActivity.this.startActivity(myIntent);

            }
        });


        //Create Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();

        ab.show();

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    //TODO:Menu System/*

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_mead_activity, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // Perform App Settings
                return true;

            case R.id.action_add:
                // Add Mead Window
                NewMeadDialogClass newMeadClass=new NewMeadDialogClass(this);
                newMeadClass.show();

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    // Context Menu for pop up control of long press
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()==R.id.list) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;


            //Display Header Title in Context Menu
            // menu.setHeaderTitle(mead.get(info.position).getName());

            String[] menuItems = getResources().getStringArray(R.array.mead_context_menu);
            for (int i = 0; i<menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    //Context Menu Handling

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        // Track which item was selected from list
        // mead.get(info.position).getName()

        switch (item.getItemId()) {
            case 0: //Test
                NewTestDialogClass newTestClass=new NewTestDialogClass(this, info.position);
                newTestClass.show();
                return true;

            case 1: //Filter
                return true;

            case 2: //Bottle
                return true;

            case 3: //Add Honey
                return true;

            case 4: //Delete
                //Bring up alert dialog to verify before deletion.
                AlertDialog.Builder alert = new AlertDialog.Builder(
                        this);
                alert.setTitle("Alert!!");
                alert.setMessage("Are you sure to delete entry?");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Call DB to delete, then remove from stack
                        db.deleteMead(mead.get(info.position).getId());
                        mead.remove(info.position);

                        //Notify change in Adapter Status
                        mAdapter.notifyDataSetChanged();

                        dialog.dismiss();

                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alert.show();

                return true;

            default:
                return true;
        }


    }

    public class NewMeadDialogClass extends Dialog implements
            android.view.View.OnClickListener {

        public Activity c;
        public Dialog d;

        public NewMeadDialogClass(Activity a) {
            super(a);

            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.mead_edit);
            Button button_ok = (Button) findViewById(R.id.button_ok);
            Button button_cancel = (Button) findViewById(R.id.button_cancel);
            button_ok.setOnClickListener(this);
            button_cancel.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_ok:
                    Mead myMead = new Mead();
                    long myID = 0;
                    TextView textView;

                    //Set Data from Dialog
                    //TODO Check boundaries of numbers
                    textView = (TextView) findViewById(R.id.name);
                    if (textView.getText().toString() == null || textView.getText().toString().isEmpty()) {
                        myMead.setName("");
                    } else {
                        myMead.setName(textView.getText().toString());
                    }

                    textView = (TextView) findViewById(R.id.og);
                    if (textView.getText().toString() == null || textView.getText().toString().isEmpty()) {
                        myMead.setOG(0.000);
                    } else {
                        myMead.setOG(Double.parseDouble(textView.getText().toString()));
                    }

                    textView = (TextView) findViewById(R.id.capacity);
                    if (textView.getText().toString() == null || textView.getText().toString().isEmpty()) {
                        myMead.setCapacity(19.000);
                    } else {
                        myMead.setCapacity(Double.parseDouble(textView.getText().toString()));
                    }
                    textView = (TextView) findViewById(R.id.volume);
                    if (textView.getText().toString() == null || textView.getText().toString().isEmpty()) {
                        myMead.setVolume(0.000);
                    } else {
                        myMead.setVolume(Double.parseDouble(textView.getText().toString()));
                    }

                    myMead.setStartDate(new GregorianCalendar());

                    //Attempt to insert mead record into DB
                    myID = db.insertMead(myMead);


                    //Check if data was entered by ID > 0
                    if (myID > 0) {
                        //Retrieve proper mead record from database
                        myMead = db.getMeadRecordFromID(myID);
                        //Add Mead Record to List
                        mead.add(new MeadData(myMead));

                        //Let Adapter know to update
                        mAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(c, "Mead Entry Database Insert Failed", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.button_cancel:
                    dismiss();
                    break;
                default:
                    break;
            }
            dismiss();
        }
    }

    public class NewTestDialogClass extends Dialog implements
            android.view.View.OnClickListener {

        public Activity c;
        public Dialog d;
        private int meadID;

        public NewTestDialogClass(Activity a, int meadID) {
            super(a);

            this.c = a;
            this.meadID = meadID;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.new_test_dialog);
            Button button_ok = (Button) findViewById(R.id.button_ok);
            Button button_cancel = (Button) findViewById(R.id.button_cancel);
            button_ok.setOnClickListener(this);
            button_cancel.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_ok:

                    double myTest = 1.0;
                    TextView textView;

                    //Set Data from Dialog

                    textView = (TextView) findViewById(R.id.sg);
                    if (!textView.getText().toString().isEmpty()) {
                        myTest = Double.parseDouble(textView.getText().toString());
                        SpecGravity myTestSpec = new SpecGravity(myTest);

                        //Add to database and get database ID
                        myTestSpec.setID(db.insertTest(myTestSpec.getTestGravity(), myTestSpec.getTestDate().getTimeInMillis(),mead.get(meadID).getId()));
                        mead.get(meadID).setDate(myTestSpec.getTestDate());
                    } else {
                        Toast.makeText(c, "Test Entry Database Insert Failed", Toast.LENGTH_SHORT).show();
                    }

                    //Let Adapter know to update
                    mAdapter.notifyDataSetChanged();

                    break;
                case R.id.button_cancel:
                    dismiss();
                    break;
                default:
                    break;
            }
            dismiss();
        }
    }



}
