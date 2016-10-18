package com.inspiredpanama.inspiredmead;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

/**
 * Created by ctilluma on 10/14/16.
 *
 * Used for the addition and editing of honey in a mead batch.
 */

public class NewAddHoneyDialogClass extends Dialog implements
        View.OnClickListener {

    public Activity c;
    private Spinner mHoneySpinner;
    private Spinner mHoneyTypeSpinner;
    private List<String> mStringList;
    private long meadID;
    private DBMead db;
    private Honey mHoney;
    private boolean isEdit;
    private EditText mAmountText;

    public NewAddHoneyDialogClass(Activity a, long meadID, DBMead db) {
        super(a);

        this.c = a;
        this.meadID = meadID;
        this.db = db;
        this.isEdit = false;
    }

    public NewAddHoneyDialogClass(Activity a, long meadID, DBMead db, Honey mHoney) {
        super(a);

        this.c = a;
        this.meadID = meadID;
        this.db = db;
        this.isEdit = true;
        this.mHoney = mHoney;
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

        //Set Spinners & Adapters
        mHoneySpinner = (Spinner) findViewById(R.id.ah_name_spinner);
        ArrayAdapter<String> mHoneySpinAdapter = new ArrayAdapter<>(c, android.R.layout.simple_spinner_item, mStringList);
        mHoneySpinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mHoneySpinner.setAdapter(mHoneySpinAdapter);
        if (isEdit) {  //Find and set name if existing edit
            for (int i = 0; i < mStringList.size(); i++) {
                if (mStringList.get(i).equals(mHoney.getName())) {
                    mHoneySpinner.setSelection(i);
                    break;
                }
            }
        }

        mHoneyTypeSpinner = (Spinner) findViewById(R.id.ah_metric_spinner);
        ArrayAdapter<CharSequence> mHoneyTypeSpinAdapt = ArrayAdapter.createFromResource(c,
                R.array.volume_types, android.R.layout.simple_spinner_item);
        mHoneyTypeSpinner.setAdapter(mHoneyTypeSpinAdapt);
        if (isEdit) {
            if (mHoney.getMetric()) {
                mHoneyTypeSpinner.setSelection(1);
            } else {
                mHoneyTypeSpinner.setSelection(0);
            }
        }

        //Amount Edit
        mAmountText = (EditText) findViewById(R.id.ah_amount);

        //Set Amount if Edit
        if (isEdit) {
            mAmountText.setText(String.valueOf(mHoney.getVolume()));
        }

        //Validate Amount Text
        mAmountText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                double mDouble;

                try {
                    mDouble = Double.parseDouble(mAmountText.getText().toString());

                    //Validate
                    if (mDouble < 0.00) {
                        mAmountText.setError("Amount must be positive.");
                    }
                } catch (NumberFormatException e) {
                    Log.e("NewHonAdd - Amt: ", e.toString());
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        double mVolume = 0.00;

        switch (view.getId()) {
            case R.id.ah_button_ok:
                //Set Data from Dialog
                EditText mAmountText = (EditText) findViewById(R.id.ah_amount);
                if (mAmountText.getText().toString().isEmpty()) {
                    mVolume = 0.00;
                } else {
                    try {
                        mVolume = Double.parseDouble(mAmountText.getText().toString());
                    } catch (NumberFormatException e) {
                        Log.e("NewHonAdd - onClick: ", String.valueOf(e));
                    }
                }

                boolean metric = (mHoneyTypeSpinner.getSelectedItemPosition() != 0);

                long honeyID = db.getHoneyIDFromName(mStringList.get(mHoneySpinner.getSelectedItemPosition()));

                if (isEdit) { //Update Existing
                    db.updateHoneyAdd(mHoney.getID(), honeyID, meadID, mVolume, metric);
                } else {
                    //Attempt to insert honey record into DB
                    db.insertHoneyAddition(honeyID, meadID, mVolume, metric);
                }
                this.dismiss();

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
