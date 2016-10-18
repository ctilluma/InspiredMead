package com.inspiredpanama.inspiredmead;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by ctilluma on 10/17/16.
 *
 * Class to create new gravity test records
 *
 */

public class NewTestDialogClass extends Dialog implements
        View.OnClickListener {

    public Activity c;
    EditText mText;
    DBMead db;
    MeadData mead;
    private long meadID;

    public NewTestDialogClass(Activity a, long meadID) {
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

        //Set Database
        db = new DBMead(c);
        mead = new MeadData(db.getMeadRecordFromID(meadID));

        //Set EditText
        mText = (EditText) findViewById(R.id.sg);

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
                if (mText.getText().toString().length() == 0) {
                    mText.setError("Gravity value is required.");
                } else {
                    double tDouble = 1.00;
                    try {
                        tDouble = Double.parseDouble(mText.getText().toString());
                    } catch (NumberFormatException e) {
                        Toast.makeText(c, "Invalid Number", Toast.LENGTH_SHORT).show();
                    }

                    if (tDouble > 1.5) {
                        mText.setError("Gravity is too high.");
                    } else {
                        if (tDouble < 0.95) {
                            mText.setError("Gravity is too low.");
                        }
                    }

                }
            }
        });
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            // Check for what is clicked
            case R.id.button_ok:

                double myTest;


                //Set Data from Dialog

                if (!mText.getText().toString().isEmpty()) {
                    myTest = Double.parseDouble(mText.getText().toString());
                    SpecGravity myTestSpec = new SpecGravity(myTest);

                    //Add to database and get database ID
                    myTestSpec.setID(db.insertTest(myTestSpec.getTestGravity(), myTestSpec.getTestDate().getTimeInMillis(), mead.getId()));

                    mead.setDate(myTestSpec.getTestDate());

                    //Get Alcohol Level put entries into current and update mead record.
                    Mead mMead = db.getMeadRecordFromID(mead.getId());
                    Double mAlcohol = myTestSpec.getAlcohol(mMead.getOG());
                    mMead.setAlcohol(mAlcohol);
                    db.updateMead(mMead);
                    mead.setAlcohol(myTestSpec.getAlcohol(db.getMeadRecordFromID(mead.getId()).getOG()));

                } else {
                    Toast.makeText(c, "Test Entry Database Insert Failed", Toast.LENGTH_SHORT).show();
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