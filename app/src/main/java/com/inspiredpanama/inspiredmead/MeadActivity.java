package com.inspiredpanama.inspiredmead;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MeadActivity extends AppCompatActivity {
    //STATICS
    public static final int SORT_NAME = 0;
    public static final int SORT_BREW_DATE = 1;
    public static final int SORT_LAST_TEST = 2;
    public static final int SORT_ALCOHOL = 3;
    public static final int SORT_VOLUME = 4;
    public static final int SORT_TYPE_ASC = 0;
    public static final int SORT_TYPE_DEC = 1;


    //Variables
    private ProgressDialog pDialog;
    private List<MeadData> mead;
    private ListView listView;
    private MeadListAdapter mAdapter;
    private DBMead db;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

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
        meadSort(SORT_BREW_DATE, SORT_TYPE_ASC);
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
                startActivityForResult(myIntent, 0);

            }
        });


        //Create Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();

        ab.show();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
        final ArrayAdapter<String> arrayAdapterSort = new ArrayAdapter<String>(
                MeadActivity.this,
                android.R.layout.select_dialog_item);
        arrayAdapterSort.add("Name");
        arrayAdapterSort.add("Brew Date");
        arrayAdapterSort.add("Alcohol");
        arrayAdapterSort.add("Test");
        arrayAdapterSort.add("Volume");

        switch (item.getItemId()) {
            case R.id.action_settings:
                // Perform App Settings
                return true;

            case R.id.action_add:
                // Add Mead Window
                NewMeadDialogClass newMeadClass = new NewMeadDialogClass(this);
                newMeadClass.show();

                return true;

            case R.id.action_sort: //Descending Sort
                AlertDialog.Builder sortTypeDes = new AlertDialog.Builder(MeadActivity.this);
                sortTypeDes.setTitle("Select Sort Method");

                sortTypeDes.setNegativeButton(
                        "cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                sortTypeDes.setAdapter(
                        arrayAdapterSort,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String strName = arrayAdapterSort.getItem(which);
                                Log.i("onClick: Des: ", String.valueOf(which));
                                meadSort(which, SORT_TYPE_DEC);
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                sortTypeDes.show();

                return true;
            case R.id.action_sort_r: //Ascending Sort
                AlertDialog.Builder sortTypeAsc = new AlertDialog.Builder(MeadActivity.this);
                sortTypeAsc.setTitle("Select Sort Method");

                sortTypeAsc.setNegativeButton(
                        "cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                sortTypeAsc.setAdapter(
                        arrayAdapterSort,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String strName = arrayAdapterSort.getItem(which);
                                Log.i("onClick: Asc: ", String.valueOf(which));
                                meadSort(which, SORT_TYPE_ASC);
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                sortTypeAsc.show();
                return true;

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
        if (v.getId() == R.id.list) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;


            //Display Header Title in Context Menu
            // menu.setHeaderTitle(mead.get(info.position).getName());

            String[] menuItems = getResources().getStringArray(R.array.mead_context_menu);
            for (int i = 0; i < menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    //Context Menu Handling

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        // Track which item was selected from list
        // mead.get(info.position).getName()

        switch (item.getItemId()) {
            case 0: //Test
                NewTestDialogClass newTestClass = new NewTestDialogClass(this, info.position);
                newTestClass.show();
                return true;

            case 1: //Filter and Rack
                NewFilterDialogClass newFilterDialog = new NewFilterDialogClass(this, mead.get(info.position).getId());
                newFilterDialog.show();

                return true;

            case 2: //Bottle
                return true;

            case 3: //Add Honey
                // Add Mead Window
                NewAddHoneyDialogClass newHoneyClass = new NewAddHoneyDialogClass(this, mead.get(info.position).getId());
                newHoneyClass.show();
                return true;

            case 4: //Add Other
                // Add Mead Window
                NewAddAdditiveDialogClass newAddClass = new NewAddAdditiveDialogClass(this, mead.get(info.position).getId());
                newAddClass.show();
                return true;

            case 5: //Delete
                //Bring up alert dialog to verify before deletion.
                AlertDialog.Builder alertDelete = new AlertDialog.Builder(
                        this);
                alertDelete.setTitle("Alert!!");
                alertDelete.setMessage("Are you sure to delete entry?");
                alertDelete.setPositiveButton("YES", new DialogInterface.OnClickListener() {

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
                alertDelete.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alertDelete.show();

                return true;

            default:
                return true;
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        mAdapter.notifyDataSetChanged();

        String resultData = data.getStringExtra("MEAD");


        // fetch the message String
        Mead tMead = db.getMeadRecordFromID(Long.valueOf(resultData));

        for (int i = 0; i < mead.size(); i++) {
            if (mead.get(i).getId() == tMead.getId()) {
                mead.remove(i);
                mead.add(new MeadData(tMead));
                meadSort(SORT_BREW_DATE, SORT_TYPE_ASC);
                mAdapter.notifyDataSetChanged();
                break;
            }
        }
    }

    private void meadSort(int column, int direction) {

        /***************
         * Sorting function to display listArray in different orders.
         */
        switch (column) {
            case SORT_ALCOHOL:
                if (direction == SORT_TYPE_ASC) {
                    Collections.sort(mead, new Comparator<MeadData>() {
                        @Override
                        public int compare(MeadData mead1, MeadData mead2) {
                            return Integer.valueOf((int) (mead1.getAlcohol() * 100)).compareTo((int) (mead2.getAlcohol() * 100));
                        }
                    });
                } else {
                    Collections.sort(mead, new Comparator<MeadData>() {
                        @Override
                        public int compare(MeadData mead1, MeadData mead2) {
                            return Integer.valueOf((int) (mead2.getAlcohol() * 100)).compareTo((int) (mead1.getAlcohol() * 100));
                        }
                    });
                }
                break;
            case SORT_BREW_DATE:
                if (direction == SORT_TYPE_ASC) {
                    Collections.sort(mead, new Comparator<MeadData>() {
                        @Override
                        public int compare(MeadData mead1, MeadData mead2) {
                            return Integer.valueOf((int) mead1.getBrewDate().getTimeInMillis()).compareTo((int) mead2.getBrewDate().getTimeInMillis());
                        }
                    });
                } else {
                    Collections.sort(mead, new Comparator<MeadData>() {
                        @Override
                        public int compare(MeadData mead1, MeadData mead2) {
                            return Integer.valueOf((int) mead2.getBrewDate().getTimeInMillis()).compareTo((int) mead1.getBrewDate().getTimeInMillis());
                        }
                    });
                }
                break;

            case SORT_LAST_TEST:
                if (direction == SORT_TYPE_ASC) {
                    Collections.sort(mead, new Comparator<MeadData>() {
                        @Override
                        public int compare(MeadData mead1, MeadData mead2) {
                            return Integer.valueOf((int) mead1.getDate().getTimeInMillis()).compareTo((int) mead2.getDate().getTimeInMillis());
                        }
                    });
                } else {
                    Collections.sort(mead, new Comparator<MeadData>() {
                        @Override
                        public int compare(MeadData mead1, MeadData mead2) {
                            return Integer.valueOf((int) mead2.getDate().getTimeInMillis()).compareTo((int) mead1.getDate().getTimeInMillis());
                        }
                    });
                }
                break;
            case SORT_NAME:
                if (direction == SORT_TYPE_ASC) {
                    Collections.sort(mead, new Comparator<MeadData>() {
                        @Override
                        public int compare(MeadData mead1, MeadData mead2) {
                            return mead1.getName().compareToIgnoreCase(mead2.getName());
                        }
                    });
                } else {
                    Collections.sort(mead, new Comparator<MeadData>() {
                        @Override
                        public int compare(MeadData mead1, MeadData mead2) {
                            return mead2.getName().compareToIgnoreCase(mead1.getName());
                        }
                    });
                }
                break;
            case SORT_VOLUME:
                if (direction == SORT_TYPE_ASC) {
                    Collections.sort(mead, new Comparator<MeadData>() {
                        @Override
                        public int compare(MeadData mead1, MeadData mead2) {
                            return Integer.valueOf((int) (mead1.getVolume() * 100)).compareTo((int) (mead2.getVolume() * 100)); //*100 to increase decimal precision
                        }
                    });
                } else {
                    Collections.sort(mead, new Comparator<MeadData>() {
                        @Override
                        public int compare(MeadData mead1, MeadData mead2) {
                            return Integer.valueOf((int) (mead2.getVolume() * 100)).compareTo((int) (mead1.getVolume() * 100));
                        }
                    });
                }
                break;

            default:
                break;
        }

        Collections.sort(mead, new Comparator<MeadData>() {
            @Override
            public int compare(MeadData mead1, MeadData mead2) {
                return Integer.valueOf((int) mead2.getBrewDate().getTimeInMillis()).compareTo((int) mead2.getBrewDate().getTimeInMillis());
            }
        });
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Mead Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    public class NewMeadDialogClass extends Dialog implements
            View.OnClickListener {

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

    public class NewAddHoneyDialogClass extends Dialog implements
            View.OnClickListener {

        public Activity c;
        public Dialog d;
        Spinner mHoneySpinner;
        Spinner mHoneyTypeSpinner;
        List<String> mStringList;
        private long meadID;


        public NewAddHoneyDialogClass(Activity a, long meadID) {
            super(a);

            this.c = a;
            this.meadID = meadID;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.add_honey_add);


            Button button_ok = (Button) findViewById(R.id.ah_button_ok);
            Button button_cancel = (Button) findViewById(R.id.ah_button_cancel);
            button_ok.setOnClickListener(this);
            button_cancel.setOnClickListener(this);

            mStringList = db.getHoneyNameList();

            mStringList.add("Add Honey"); //Create Entry to add new Honey

            //Set Spinners & Adapters
            mHoneySpinner = (Spinner) findViewById(R.id.ah_name_spinner);
            ArrayAdapter<String> mHoneySpinAdapter = new ArrayAdapter<String>(MeadActivity.this, android.R.layout.simple_spinner_item, mStringList);
            mHoneySpinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mHoneySpinner.setAdapter(mHoneySpinAdapter);

            mHoneyTypeSpinner = (Spinner) findViewById(R.id.ah_metric_spinner);
            ArrayAdapter<CharSequence> mHoneyTypeSpinnger = ArrayAdapter.createFromResource(MeadActivity.this,
                    R.array.volume_types, android.R.layout.simple_spinner_item);
            mHoneyTypeSpinner.setAdapter(mHoneyTypeSpinnger);

        }

        @Override
        public void onClick(View view) {
            double mVolume;

            switch (view.getId()) {
                case R.id.ah_button_ok:
                    //Check for AddHoney First

                    if (mHoneySpinner.getSelectedItemPosition() == (mStringList.size() - 1)) {  // Check if last item in list
                        // Create Add Honey Dialog
                        NewHoneyAddClass newHoney = new NewHoneyAddClass(MeadActivity.this);
                        newHoney.show();

                    }

                    //Set Data from Dialog
                    //TODO Check boundaries of numbers
                    EditText mAmountText = (EditText) findViewById(R.id.ah_amount);
                    if (mAmountText.getText().toString() == null || mAmountText.getText().toString().isEmpty()) {
                        mVolume = 0.00;
                    } else {
                        mVolume = Double.parseDouble(mAmountText.getText().toString());
                    }

                    boolean metric = (mHoneyTypeSpinner.getSelectedItemPosition() != 0);

                    long honeyID = db.getHoneyIDFromName(mStringList.get(mHoneySpinner.getSelectedItemPosition()));

                    //Attempt to insert honey record into DB
                    db.insertHoneyAddition(honeyID, meadID, mVolume, metric);

                    break;
                case R.id.ah_button_cancel:
                    dismiss();
                    break;
                default:
                    break;
            }
            dismiss();
        }
    }

    public class NewAddAdditiveDialogClass extends Dialog implements
            View.OnClickListener {

        public Activity c;
        public Dialog d;
        Spinner mAdditiveSpinner;
        Spinner mAdditiveTypeSpinner;
        Spinner mAdditiveWeightSpinner;
        ArrayAdapter<CharSequence> mAdditiveTypeSpinAdapter;
        List<String> mStringList;
        private long meadID;


        public NewAddAdditiveDialogClass(Activity a, long meadID) {
            super(a);

            this.c = a;
            this.meadID = meadID;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.add_other_add);


            Button button_ok = (Button) findViewById(R.id.ao_button_ok);
            Button button_cancel = (Button) findViewById(R.id.ao_button_cancel);
            button_ok.setOnClickListener(this);
            button_cancel.setOnClickListener(this);

            mStringList = db.getAdditiveNameList();

            mStringList.add("Add Other"); //Create Entry to add new Honey

            //Set Spinners & Adapters
            mAdditiveSpinner = (Spinner) findViewById(R.id.ao_name_spinner);
            ArrayAdapter<String> mAdditiveSpinAdapter = new ArrayAdapter<String>(MeadActivity.this, android.R.layout.simple_spinner_item, mStringList);
            mAdditiveSpinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mAdditiveSpinner.setAdapter(mAdditiveSpinAdapter);

            mAdditiveWeightSpinner = (Spinner) findViewById(R.id.ao_weight_spinner);
            ArrayAdapter<CharSequence> mAdditiveWeightSpinAdapter = ArrayAdapter.createFromResource(MeadActivity.this,
                    R.array.is_weight, android.R.layout.simple_spinner_item);
            mAdditiveWeightSpinner.setAdapter(mAdditiveWeightSpinAdapter);
            mAdditiveWeightSpinner.setSelection(0);

            setTypeSpinner(false);

            mAdditiveWeightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> spinner, View container,
                                           int position, long id) {
                    if (position > 0) {
                        setTypeSpinner(true);
                    } else {
                        setTypeSpinner(false);
                    }

                    // mAdditiveTypeSpinAdapter.notifyDataSetChanged();
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });

        }


        private void setTypeSpinner(boolean isWeight) {
            if (isWeight) {
                mAdditiveTypeSpinner = (Spinner) findViewById(R.id.ao_metric_spinner);
                mAdditiveTypeSpinAdapter = ArrayAdapter.createFromResource(MeadActivity.this,
                        R.array.weight_types, android.R.layout.simple_spinner_item);
                mAdditiveTypeSpinner.setAdapter(mAdditiveTypeSpinAdapter);

            } else {
                mAdditiveTypeSpinner = (Spinner) findViewById(R.id.ao_metric_spinner);
                ArrayAdapter<CharSequence> mAdditiveTypeSpinAdapter = ArrayAdapter.createFromResource(MeadActivity.this,
                        R.array.volume_types, android.R.layout.simple_spinner_item);
                mAdditiveTypeSpinner.setAdapter(mAdditiveTypeSpinAdapter);

            }
        }

        @Override
        public void onClick(View view) {
            double mVolume;

            switch (view.getId()) {
                case R.id.ao_button_ok:
                    //Check for AddHoney First

                    if (mAdditiveSpinner.getSelectedItemPosition() == (mStringList.size() - 1)) {  // Check if last item in list
                        // Create Add Other Dialog
                        NewOtherAddClass newOther = new NewOtherAddClass(MeadActivity.this);
                        newOther.show();

                    }

                    //Set Data from Dialog
                    //TODO Check boundaries of numbers
                    EditText mAmountText = (EditText) findViewById(R.id.ao_amount);
                    if (mAmountText.getText().toString() == null || mAmountText.getText().toString().isEmpty()) {
                        mVolume = 0.00;
                    } else {
                        mVolume = Double.parseDouble(mAmountText.getText().toString());
                    }

                    boolean metric = (mAdditiveTypeSpinner.getSelectedItemPosition() != 0);
                    boolean weight = (mAdditiveWeightSpinner.getSelectedItemPosition() != 0);

                    long additiveID = db.getAdditiveIDFromName(mStringList.get(mAdditiveSpinner.getSelectedItemPosition()));

                    //Attempt to insert honey record into DB
                    db.insertAdditiveAdd(additiveID, meadID, mVolume, metric, weight);

                    break;
                case R.id.ah_button_cancel:
                    dismiss();
                    break;
                default:
                    break;
            }
            dismiss();
        }
    }

    public class NewFilterDialogClass extends Dialog implements
            View.OnClickListener {

        public Activity c;
        public Dialog d;
        Spinner mSpinner;
        private long meadID;

        public NewFilterDialogClass(Activity a, long meadID) {
            super(a);

            this.c = a;
            this.meadID = meadID;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.mead_filter);
            Button button_ok = (Button) findViewById(R.id.fd_button_ok);
            Button button_cancel = (Button) findViewById(R.id.fd_button_cancel);
            button_ok.setOnClickListener(this);
            button_cancel.setOnClickListener(this);
            mSpinner = (Spinner) findViewById(R.id.fd_filter_spinner);
            ArrayAdapter<CharSequence> mAdapter = ArrayAdapter.createFromResource(MeadActivity.this,
                    R.array.mead_filter_menu, android.R.layout.simple_spinner_item);
            mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(mAdapter);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.fd_button_ok:
                    EditText waste = (EditText) findViewById(R.id.fd_waste);
                    Double wasteAmt = Double.parseDouble(waste.getText().toString());

                    //Get Mead Record
                    Mead mMead = db.getMeadRecordFromID(meadID);
                    mMead.addWaste(wasteAmt);

                    Filter mFilter = new Filter();
                    mFilter.setFilterSize(mSpinner.getSelectedItemPosition());
                    mFilter.setFilterDate(new GregorianCalendar());
                    mFilter.setMeadID(meadID);

                    Log.i("FD(mID,waste,filter): ", String.valueOf(meadID) + " " + String.valueOf(wasteAmt) + " " + String.valueOf(mFilter.getFilterSize()));

                    db.updateMead(mMead);
                    db.insertFilter(mFilter);

                    break;

                case R.id.fd_button_cancel:
                    dismiss();
                    break;

                default:
                    break;
            }
            dismiss();
        }
    }

    public class NewTestDialogClass extends Dialog implements
            View.OnClickListener {

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
                        myTestSpec.setID(db.insertTest(myTestSpec.getTestGravity(), myTestSpec.getTestDate().getTimeInMillis(), mead.get(meadID).getId()));

                        mead.get(meadID).setDate(myTestSpec.getTestDate());

                        //Get Alcohol Level put entries into current and update mead record.
                        Mead mMead = db.getMeadRecordFromID(mead.get(meadID).getId());
                        Double mAlcohol = myTestSpec.getAlcohol(mMead.getOG());
                        mMead.setAlcohol(mAlcohol);
                        db.updateMead(mMead);
                        mead.get(meadID).setAlcohol(myTestSpec.getAlcohol(db.getMeadRecordFromID(mead.get(meadID).getId()).getOG()));

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

    public class NewHoneyAddClass extends Dialog implements
            View.OnClickListener {

        public Activity c;
        public Dialog d;

        public NewHoneyAddClass(Activity a) {
            super(a);

            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.honey_edit);
            Button button_ok = (Button) findViewById(R.id.he_button_ok);
            Button button_cancel = (Button) findViewById(R.id.he_button_cancel);
            button_ok.setOnClickListener(this);
            button_cancel.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            EditText mEditText;
            switch (view.getId()) {
                case R.id.he_button_ok:
                    String name;
                    double brix;
                    String flavor;

                    //Set Data from Dialog

                    mEditText = (EditText) findViewById(R.id.he_name);
                    if (mEditText.getText().toString() != null) {
                        name = mEditText.getText().toString();
                    } else {
                        name = "";
                    }

                    mEditText = (EditText) findViewById(R.id.he_brix);
                    if (Double.parseDouble(mEditText.getText().toString()) > 0.00) {
                        brix = Double.parseDouble(mEditText.getText().toString());
                    } else {
                        brix = 0.00;
                    }

                    mEditText = (EditText) findViewById(R.id.he_desc);
                    if (mEditText.getText().toString() != null) {
                        flavor = mEditText.getText().toString();
                    } else {
                        flavor = "";
                    }

                    db.insertHoney(brix, name, flavor);
                    break;
                case R.id.he_button_cancel:
                    dismiss();
                    break;
                default:
                    break;
            }
            dismiss();
        }

    }

    public class NewOtherAddClass extends Dialog implements
            View.OnClickListener {

        public Activity c;
        public Dialog d;

        public NewOtherAddClass(Activity a) {
            super(a);

            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.other_edit);
            Button button_ok = (Button) findViewById(R.id.oe_button_ok);
            Button button_cancel = (Button) findViewById(R.id.oe_button_cancel);
            button_ok.setOnClickListener(this);
            button_cancel.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            EditText mEditText;
            switch (view.getId()) {
                case R.id.oe_button_ok:
                    String name;
                    double sg;
                    String flavor;
                    Additive mAddit = new Additive();

                    //Set Data from Dialog

                    mEditText = (EditText) findViewById(R.id.oe_name);
                    if (mEditText.getText().toString() != null) {
                        name = mEditText.getText().toString();
                    } else {
                        name = "";
                    }

                    mEditText = (EditText) findViewById(R.id.oe_sg);
                    if (Double.parseDouble(mEditText.getText().toString()) > 0.00) {
                        sg = Double.parseDouble(mEditText.getText().toString());
                    } else {
                        sg = 0.00;
                    }

                    mEditText = (EditText) findViewById(R.id.oe_desc);
                    if (mEditText.getText().toString() != null) {
                        flavor = mEditText.getText().toString();
                    } else {
                        flavor = "";
                    }

                    mAddit.setSG(sg);

                    db.insertAdditive(mAddit.getBrix(), name, flavor);
                    break;
                case R.id.he_button_cancel:
                    dismiss();
                    break;
                default:
                    break;
            }
            dismiss();
        }

    }

}
