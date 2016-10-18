package com.inspiredpanama.inspiredmead;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static java.lang.Math.round;

public class MeadDisplay extends AppCompatActivity {
    //STATICS
    public static final int DAYS_BETWEEN_TESTING = 60;

    //Variables
    Mead myMead;
    DBMead db;
    DecimalFormat df = new DecimalFormat("#.##");  //Format to two decimal place precision for viewing
    private MeadListAdapter mAdapter;
    private AddAdditionsAdapter mAddAdapter;
    private AddHoneyAdapter mHoneyAdapter;
    private List<Honey> mHoneyList;
    private List<Additive> mAddList;

    //List Views for Activity
    private ListView mHoneyListView;
    private ListView mAddListView;

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

        //Load DataBase Record
        myMead = db.getMeadRecordFromID(meadID);

        //Create Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();

        if (ab != null) {
            ab.show();
        }

        //Display MeadData
        displayMead();

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
            case R.id.abd_add_honey:
                //Add Honey Dialog
                NewHoneyAddClass newHoney = new NewHoneyAddClass(this, db);
                newHoney.show();
                return true;

            case R.id.abd_add_other:
                //Add Other Dialog
                NewOtherAddClass newOther = new NewOtherAddClass(this, db);
                newOther.show();
                return true;

            case R.id.abd_action_back:
                doExit();
                return true;

            case R.id.abd_settings:
                // Perform App Settings
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void updateAndDisplay() {
        db.updateMead(myMead);
        displayMead();
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
        TextView waste = (TextView) findViewById(R.id.md_waste);
        TextView wastePer = (TextView) findViewById(R.id.md_waste_per);

        //Set Name in Display
        name.setText("");
        name.setText(myMead.getName());

        //Set Original Gravity in Display
        if (myMead.getOG() > 0.900) {
            og.setText(String.valueOf(myMead.getOG()));
        } else {
            og.setText("N/A");
        }

        //Set CUrrent Specific Gravity in Display
        if (myMead.getLastTest() != null) {
            sg.setText(String.valueOf(myMead.getLastTest().getTestGravity()));
        } else {
            sg.setText("N/A");
        }

        //Set Alcohol in Display
        if (myMead.getAlcohol() >= 0.000) {
            alcohol.setText(String.valueOf(df.format(myMead.getAlcohol())));
        } else {
            alcohol.setText(String.valueOf(0));
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
        } else {
            capacity.setText("N/A");
        }

        //Set Volume Bottles in Display
        if (myMead.getVolume() >= 0.000) {
            volume.setText(String.valueOf(myMead.getVolume()));
        } else {
            volume.setText("N/A");
        }

        //Set Estimated Bottles in Display
        if (myMead.getVolume() >= 0.000) {
            estBottles.setText(String.valueOf(round((myMead.getVolume() * 0.9) / 0.75)));
        } else {
            estBottles.setText("N/A");
        }

        //Set Current Bottles in Display
        //TODO : Need to calculate current bottles from inventory database

        //Set Waste  in Display
        if (myMead.getWaste() >= 0.000) {
            waste.setText(String.valueOf(myMead.getWaste()));
        } else {
            estBottles.setText("0.00");
        }

        //Set Waste Per in Display
        if (myMead.getWaste() >= 0.000) {
            wastePer.setText(String.valueOf(df.format(myMead.getWaste() / myMead.getOrigVolume())));  //formatted to 2 lines of precision
        } else {
            estBottles.setText("0.00");
        }

        //Display List Adapters
        displayListOnClick();
    }

    //onClick for Name Field
    public void onClickName(View v) {
        //Create Alert dialog for Name Entry
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Enter Mead Name");

        // Create EditText view for input
        final EditText input = new EditText(this);

        input.setText(myMead.getName());
        input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        // input.setRawInputType(InputType.TYPE_CLASS_TEXT); //Change keyboard type
        alert.setView(input);

        alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //Get String and store
                myMead.setName(input.getEditableText().toString());
                updateAndDisplay();

            }
        });
        alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();

    }

    //onClick for Original Gravity Field
    public void onClickOG(View v) {
        //Create Alert dialog for OG Entry
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Original Gravity");

        // Create EditText view for input
        final EditText input = new EditText(this);

        input.setText(String.valueOf(myMead.getOG()));
        input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        // input.setRawInputType(InputType.TYPE_CLASS_TEXT); //Change keyboard type
        alert.setView(input);

        alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //Get String and store
                myMead.setOG(Double.parseDouble(input.getEditableText().toString()));
                updateAndDisplay();

            }
        });
        alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();

    }

    //onClick for Waste Field
    public void onClickWaste(View v) {
        //Create Alert dialog for Waste Entry
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Waste");

        // Create EditText view for input
        final EditText input = new EditText(this);

        input.setText(String.valueOf(myMead.getWaste()));
        input.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        alert.setView(input);

        //Validate Text
        input.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                double mDouble;

                try {
                    mDouble = Double.parseDouble(input.getText().toString());

                    //Validate
                    if (mDouble > myMead.getVolume()) {
                        input.setError("Waste can't be greater than volume.");
                    } else if (mDouble < 0.00) {
                        input.setError("Waste can't be less than zero.");
                    }
                } catch (NumberFormatException e) {
                    Log.e("MeadDisp - EditWaste: ", e.toString());
                }
            }
        });

        alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //Get String and store
                try {
                    myMead.setWaste(Double.parseDouble(input.getEditableText().toString()));
                } catch (NumberFormatException e) {
                    Log.e("MeadDisp - EditWaste: ", e.toString());
                }

                updateAndDisplay();

            }
        });
        alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

    //onClick for Capacity Field
    public void onClickCapacity(View v) {
        //Create Alert dialog for Capacity Entry
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Capacity");

        // Create EditText view for input
        final EditText input = new EditText(this);

        input.setText(String.valueOf(myMead.getCapacity()));
        input.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        alert.setView(input);

        //Validate Text
        input.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                double mDouble;

                try {
                    mDouble = Double.parseDouble(input.getText().toString());

                    //Validate
                    if (mDouble < myMead.getVolume()) {
                        input.setError("Capacity can't be less than volume.");
                    } else if (mDouble < 0.00) {
                        input.setError("Capacity can't be less than zero.");
                    }
                } catch (NumberFormatException e) {
                    Log.e("MeadDisp - EditCap: ", e.toString());
                }
            }
        });


        alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //Get String and store
                try {
                    myMead.setCapacity(Double.parseDouble(input.getEditableText().toString()));
                } catch (NumberFormatException e) {
                    Log.e("MeadDisp - EditCap: ", e.toString());
                }

                updateAndDisplay();

            }
        });
        alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

    //onClick for Volume Field
    public void onClickVolume(View v) {
        //Create Alert dialog for Volume Entry
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Current Volume");

        // Create EditText view for input
        final EditText input = new EditText(this);

        input.setText(String.valueOf(myMead.getVolume()));
        input.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        alert.setView(input);

        //Validate Text
        input.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                double mDouble;

                try {
                    mDouble = Double.parseDouble(input.getText().toString());

                    //Validate
                    if (mDouble > myMead.getCapacity()) {
                        input.setError("Volume can't be greater than capacity.");
                    } else if (mDouble < 0.00) {
                        input.setError("Volume can't be less than zero.");
                    }
                } catch (NumberFormatException e) {
                    Log.e("MeadDisp - EditVolume: ", e.toString());
                }
            }
        });

        alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //Get String and store
                try {
                    myMead.setVolume(Double.parseDouble(input.getEditableText().toString()));
                } catch (NumberFormatException e) {
                    Log.e("MeadDisp - EditVolume: ", e.toString());
                }

                updateAndDisplay();
            }
        });

        alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

    //onClick for Date - DatePicker
    public void onClickDate(View v) {
        final EditText input = new EditText(this);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                GregorianCalendar myCalendar = myMead.getStartDate();

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                myMead.setStartDate(myCalendar);

                updateAndDisplay();
            }
        };

        new DatePickerDialog(MeadDisplay.this, date,
                myMead.getStartDate().get(Calendar.YEAR),
                myMead.getStartDate().get(Calendar.MONTH),
                myMead.getStartDate().get(Calendar.DAY_OF_MONTH)).show();

        db.updateMead(myMead);
    }

    //onClick for Time - TimePicker
    public void onClickTime(View v) {
        final EditText input = new EditText(this);

        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                GregorianCalendar myCalendar = myMead.getStartDate();

                myCalendar.set(Calendar.HOUR_OF_DAY, hour);
                myCalendar.set(Calendar.MINUTE, minute);

                myMead.setStartDate(myCalendar);

                updateAndDisplay();
            }
        };

        new TimePickerDialog(MeadDisplay.this, time,
                myMead.getStartDate().get(Calendar.HOUR_OF_DAY),
                myMead.getStartDate().get(Calendar.MINUTE), true).show();

        db.updateMead(myMead);
    }

    //onClick for NewTest Field
    public void onClickTest(View v) {
        NewTestDialogClass newTestClass = new NewTestDialogClass(this, myMead.getId());
        newTestClass.show();

        //Let Adapter know to update
        mAdapter.notifyDataSetChanged();

        displayMead();
    }

    private void doExit() {
        Intent intentMessage = new Intent();

        // put the message in Intent
        intentMessage.putExtra("MEAD", String.valueOf(myMead.getId()));
        // Set The Result in Intent
        setResult(0, intentMessage);

        finish();
    }

    private void displayListOnClick() {
        mHoneyListView = (ListView) findViewById(R.id.md_honey_list);
        mAddListView = (ListView) findViewById(R.id.md_addition_list);
        mHoneyList = db.getHoneyAdditionsFromMead(myMead.getId());
        mAddList = db.getAdditiveAdditionsFromMead(myMead.getId());

        // Set List Adapters
        mAddAdapter = new AddAdditionsAdapter(this, mAddList);
        mHoneyAdapter = new AddHoneyAdapter(this, mHoneyList);
        mAddListView.setAdapter(mAddAdapter);
        mHoneyListView.setAdapter(mHoneyAdapter);


        mAddListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {

                //Start perform activty when addition is clicked - Edit Dialog
                NewAddAdditiveDialogClass newAdditiveClass = new NewAddAdditiveDialogClass(MeadDisplay.this, myMead.getId(), db, db.getAdditiveAdditionsFromMead(myMead.getId()).get(position));
                newAdditiveClass.show();
                updateAndDisplay();
            }
        });

        mAddListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int position, long arg3) {
                AlertDialog.Builder alertDelete = new AlertDialog.Builder(
                        MeadDisplay.this);
                alertDelete.setTitle("Alert!!");
                alertDelete.setMessage("Are you sure to delete entry?");
                alertDelete.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Call DB to delete, then remove from stack
                        db.deleteAdditiveAdd(db.getAdditiveAdditionsFromMead(myMead.getId()).get(position).getID());
                        mAddList.remove(position);
                        mAddAdapter.notifyDataSetChanged();
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
            }
        });

        mHoneyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {

                //Start perform activty when addition is clicked - Edit Dialog
                NewAddHoneyDialogClass newHoneyClass = new NewAddHoneyDialogClass(MeadDisplay.this, myMead.getId(), db, db.getHoneyAdditionsFromMead(myMead.getId()).get(position));
                newHoneyClass.show();
                updateAndDisplay();
            }
        });

        mHoneyListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int position, long arg3) {

                AlertDialog.Builder alertDelete = new AlertDialog.Builder(
                        MeadDisplay.this);
                alertDelete.setTitle("Alert!!");
                alertDelete.setMessage("Are you sure to delete entry?");
                alertDelete.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Call DB to delete, then remove from stack
                        db.deleteHoneyAdd(db.getHoneyAdditionsFromMead(myMead.getId()).get(position).getID());
                        mHoneyList.remove(position);
                        mHoneyAdapter.notifyDataSetChanged();
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
            }
        });




    }

}

