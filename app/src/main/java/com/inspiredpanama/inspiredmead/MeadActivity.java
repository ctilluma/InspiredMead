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
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.Toast;

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
        meadSort(SORT_BREW_DATE, SORT_TYPE_ASC);
        hidePDialog();

        //Create and populate List
        ListView listView = (ListView) findViewById(R.id.list);
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

        if (ab != null) {
            ab.show();
        }
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
        final ArrayAdapter<String> arrayAdapterSort = new ArrayAdapter<>(
                MeadActivity.this,
                android.R.layout.select_dialog_item);
        arrayAdapterSort.add("Name");
        arrayAdapterSort.add("Brew Date");
        arrayAdapterSort.add("Alcohol");
        arrayAdapterSort.add("Test");
        arrayAdapterSort.add("Volume");

        switch (item.getItemId()) {
            case R.id.abm_add_honey:
                //Add Honey Dialog
                NewHoneyAddClass newHoney = new NewHoneyAddClass(this, db);
                newHoney.show();
                return true;

            case R.id.abm_add_other:
                //Add Other Dialog
                NewOtherAddClass newOther = new NewOtherAddClass(this, db);
                newOther.show();
                return true;

            case R.id.abm_settings:
                // Perform App Settings
                return true;

            case R.id.abm_action_add:
                // Add Mead Window
                NewMeadDialogClass newMeadClass = new NewMeadDialogClass(this, new Mead());
                newMeadClass.show();

                //Add Mead Record to List
                mead.add(new MeadData(newMeadClass.mMead));

                //Let Adapter know to update
                mAdapter.notifyDataSetChanged();

                return true;

            case R.id.abm_action_sort: //Descending Sort
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
                                meadSort(which, SORT_TYPE_DEC);
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                sortTypeDes.show();

                return true;
            case R.id.abm_action_sort_r: //Ascending Sort
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
                NewTestDialogClass newTestClass = new NewTestDialogClass(this, mead.get(info.position).getId());
                newTestClass.show();

                //Let Adapter know to update
                mAdapter.notifyDataSetChanged();

                return true;

            case 1: //Filter and Rack
                NewFilterDialogClass newFilterDialog = new NewFilterDialogClass(this, mead.get(info.position).getId());
                newFilterDialog.show();

                return true;

            case 2: //Bottle
                return true;

            case 3: //Add Honey
                // Check for honey existing before bringing up dialog.
                if (db.numberOfHoneyRows() > 0) {
                    // Add Mead Window
                    NewAddHoneyDialogClass newHoneyClass = new NewAddHoneyDialogClass(this, mead.get(info.position).getId(), db);
                    newHoneyClass.show();
                    return true;
                } else {
                    Toast.makeText(MeadActivity.this, "You must add honey before it can be added to the mead.", Toast.LENGTH_SHORT).show();
                    return false;
                }

            case 4: //Add Other
                if (db.numberOfAdditiveRows() > 0) {
                    // Add Other Additive Window
                    NewAddAdditiveDialogClass newAddClass = new NewAddAdditiveDialogClass(this, mead.get(info.position).getId(), db);
                    newAddClass.show();
                    return true;
                } else {
                    Toast.makeText(MeadActivity.this, "You must add an additive before it can be added to the mead.", Toast.LENGTH_SHORT).show();
                    return false;
                }


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
                return Integer.valueOf((int) mead1.getBrewDate().getTimeInMillis()).compareTo((int) mead2.getBrewDate().getTimeInMillis());
            }
        });
    }

    public class NewFilterDialogClass extends Dialog implements
            View.OnClickListener {

        public Activity c;
        Spinner mSpinner;
        Mead mMead;
        EditText mText;
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

            //Get Mead Record
            mMead = db.getMeadRecordFromID(meadID);


            //Set EditText
            mText = (EditText) findViewById(R.id.fd_waste);

            //Validate Text
            mText.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                }

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    //Validate Specific Gravity

                    double tDouble = 0.00;
                    try {
                        tDouble = Double.parseDouble(mText.getText().toString());
                    } catch (NumberFormatException e) {
                        Toast.makeText(MeadActivity.this, "Invalid Number", Toast.LENGTH_SHORT).show();
                    }

                    if (tDouble < 0.00) {
                        mText.setError("Can't be negative.");
                    } else {
                        if (tDouble > mMead.getVolume()) {
                            mText.setError("Can't be greater than total volume.");
                        }
                    }


                }
            });
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.fd_button_ok:
                    EditText waste = (EditText) findViewById(R.id.fd_waste);
                    Double wasteAmt = Double.parseDouble(waste.getText().toString());

                    mMead.addWaste(wasteAmt);

                    Filter mFilter = new Filter();
                    mFilter.setFilterSize(mSpinner.getSelectedItemPosition());
                    mFilter.setFilterDate(new GregorianCalendar());
                    mFilter.setMeadID(meadID);

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


}
