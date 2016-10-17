package com.inspiredpanama.inspiredmead;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.GregorianCalendar;

/**
 * Created by ctilluma on 10/17/16.
 * <p>
 * Handles creating a batch of mead from a provided mead, either new or existing.
 */

public class NewMeadDialogClass extends Dialog implements
        View.OnClickListener {

    public Mead mMead;
    private Activity c;
    private DBMead db;
    private TextView tName, tOG, tCapacity, tVolume;

    public NewMeadDialogClass(Activity a, Mead mMead) {
        super(a);

        this.c = a;
        this.mMead = mMead;
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
        db = new DBMead(c);
        setTextViews();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_ok:
                saveTextViews();
                break;
            case R.id.button_cancel:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

    private void setTextViews() {
        tName = (TextView) findViewById(R.id.name);
        tOG = (TextView) findViewById(R.id.og);
        tCapacity = (TextView) findViewById(R.id.capacity);
        tVolume = (TextView) findViewById(R.id.volume);


        //Validate Gravity Text
        tOG.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                double mDouble;

                try {
                    mDouble = Double.parseDouble(tOG.getText().toString());

                    //Validate
                    if (mDouble < 0.985) {
                        tOG.setError("Gravity too Low.");
                    } else if (mDouble > 1.50) {
                        tOG.setError("Gravity is too high.");
                    }
                } catch (NumberFormatException e) {
                    Log.e("NewMead - OG: ", e.toString());
                }
            }
        });

        //Validate Capacity Text
        tCapacity.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                double mDoubleCap, mDoubleVol;

                try {
                    mDoubleCap = Double.parseDouble(tCapacity.getText().toString());
                    mDoubleVol = Double.parseDouble(tVolume.getText().toString());

                    //Validate
                    if (mDoubleCap < 0.00) {
                        tCapacity.setError("Capacity can't be less than zero.");
                    } else {
                        if ((mDoubleVol > 0) && (mDoubleCap < mDoubleVol)) {
                            tCapacity.setError("Capacity can't be less than volume.");
                        }
                    }
                } catch (NumberFormatException e) {
                    Log.e("NewMead - Capacity: ", e.toString());
                }
            }
        });

        //Validate Volume Text
        tVolume.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                double mDoubleCap, mDoubleVol;

                try {
                    mDoubleCap = Double.parseDouble(tCapacity.getText().toString());
                    mDoubleVol = Double.parseDouble(tVolume.getText().toString());

                    //Validate
                    if (mDoubleVol < 0.00) {
                        tVolume.setError("Volume can't be less than zero.");
                    } else if ((mDoubleCap > 0) && (mDoubleCap < mDoubleVol)) {
                        tVolume.setError("Volume can't be greater than capacity.");
                    }
                } catch (NumberFormatException e) {
                    Log.e("NewMead - Volume: ", e.toString());
                }
            }
        });

    }

    //Set Data from Dialog
    private void saveTextViews() {
        long myID;

        //Name Text
        if (tName.getText().toString().isEmpty()) {
            mMead.setName("");
        } else {
            mMead.setName(tName.getText().toString());
        }

        //Original Gravity
        if (tOG.getText().toString().isEmpty()) {
            mMead.setOG(0.000);
        } else {
            try {
                mMead.setOG(Double.parseDouble(tOG.getText().toString()));
            } catch (NumberFormatException e) {
                Log.e("NewMead - Gravity: ", e.toString());
            }
        }

        //Capacity
        if (tCapacity.getText().toString().isEmpty()) {
            mMead.setCapacity(19.000);
        } else try {
            mMead.setCapacity(Double.parseDouble(tCapacity.getText().toString()));
        } catch (NumberFormatException e) {
            Log.e("NewMead - Capacity: ", e.toString());
        }

        //Volume
        if (tVolume.getText().toString().isEmpty()) {
            mMead.setVolume(0.000);
        } else {
            try {
                mMead.setVolume(Double.parseDouble(tVolume.getText().toString()));
            } catch (NumberFormatException e) {
                Log.e("NewMead - Volume: ", e.toString());
            }
        }

        mMead.setStartDate(new GregorianCalendar());

        //Attempt to insert mead record into DB
        myID = db.insertMead(mMead);


        //Check if data was entered by ID > 0
        if (myID > 0) {
            //Retrieve proper mead record from database
            mMead = db.getMeadRecordFromID(myID);
        } else {
            Toast.makeText(c, "Mead Entry Database Insert Failed", Toast.LENGTH_SHORT).show();
        }
    }
}
