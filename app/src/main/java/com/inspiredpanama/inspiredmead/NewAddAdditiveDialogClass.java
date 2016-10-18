package com.inspiredpanama.inspiredmead;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

/**
 * Created by ctilluma on 10/14/16.
 *
 * Allows for adding and editing any additives into a mead batch.
 */

public class NewAddAdditiveDialogClass extends Dialog implements
        View.OnClickListener {

    public Activity c;
    private Spinner mAdditiveSpinner;
    private Spinner mAdditiveTypeSpinner;
    private Spinner mAdditiveWeightSpinner;
    private List<String> mStringList;
    private long meadID;
    private DBMead db;
    private Additive mAdditive;
    private boolean isEdit;
    private EditText mAmountText;

    public NewAddAdditiveDialogClass(Activity a, long meadID, DBMead db) {
        super(a);

        this.c = a;
        this.meadID = meadID;
        this.db = db;
        this.isEdit = false;
    }

    public NewAddAdditiveDialogClass(Activity a, long meadID, DBMead db, Additive mAdditive) {
        super(a);

        this.c = a;
        this.meadID = meadID;
        this.db = db;
        this.mAdditive = mAdditive;
        this.isEdit = true;
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

        //Set Spinners & Adapters
        mAdditiveSpinner = (Spinner) findViewById(R.id.ao_name_spinner);
        ArrayAdapter<String> mAdditiveSpinAdapter = new ArrayAdapter<>(c, android.R.layout.simple_spinner_item, mStringList);
        mAdditiveSpinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAdditiveSpinner.setAdapter(mAdditiveSpinAdapter);
        if (isEdit) {  //Find and set name if existing edit
            for (int i = 0; i < mStringList.size(); i++) {
                if (mStringList.get(i).equals(mAdditive.getName())) {
                    mAdditiveSpinner.setSelection(i);
                    break;
                }
            }
        }

        mAdditiveWeightSpinner = (Spinner) findViewById(R.id.ao_weight_spinner);
        ArrayAdapter<CharSequence> mAdditiveWeightSpinAdapter = ArrayAdapter.createFromResource(c,
                R.array.is_weight, android.R.layout.simple_spinner_item);
        mAdditiveWeightSpinner.setAdapter(mAdditiveWeightSpinAdapter);
        mAdditiveWeightSpinner.setSelection(0);
        if (isEdit) {  //Find and set name if existing edit
            if (mAdditive.getIsWeight()) {
                mAdditiveWeightSpinner.setSelection(1);
            }
        }

        setTypeSpinner((mAdditiveWeightSpinner.getSelectedItemPosition() > 0));
        if (isEdit) {
            if (mAdditive.getMetric()) {
                mAdditiveTypeSpinner.setSelection(1);
            }
        }

        mAdditiveWeightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> spinner, View container,
                                       int position, long id) {
                if (position > 0) {
                    setTypeSpinner(true);
                } else {
                    setTypeSpinner(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //Amount Edit
        mAmountText = (EditText) findViewById(R.id.ao_amount);

        //Set Amount if Edit
        if (isEdit) {
            mAmountText.setText(String.valueOf(mAdditive.getAmount()));
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
                    Log.e("NewAddAdd - Amt: ", e.toString());
                }
            }
        });
    }


    private void setTypeSpinner(boolean isWeight) {
        if (isWeight) {
            mAdditiveTypeSpinner = (Spinner) findViewById(R.id.ao_metric_spinner);
            ArrayAdapter<CharSequence> mAdditiveTypeSpinAdapter = ArrayAdapter.createFromResource(c,
                    R.array.weight_types, android.R.layout.simple_spinner_item);
            mAdditiveTypeSpinner.setAdapter(mAdditiveTypeSpinAdapter);

        } else {
            mAdditiveTypeSpinner = (Spinner) findViewById(R.id.ao_metric_spinner);
            ArrayAdapter<CharSequence> mAdditiveTypeSpinAdapter = ArrayAdapter.createFromResource(c,
                    R.array.volume_types, android.R.layout.simple_spinner_item);
            mAdditiveTypeSpinner.setAdapter(mAdditiveTypeSpinAdapter);

        }
    }

    @Override
    public void onClick(View view) {
        double mVolume = 0.00;

        switch (view.getId()) {
            case R.id.ao_button_ok:
                //Set Data from Dialog
                if (mAmountText.getText().toString().isEmpty()) {
                    mVolume = 0.00;
                } else {
                    try {
                        mVolume = Double.parseDouble(mAmountText.getText().toString());
                    } catch (NumberFormatException e) {
                        Log.e("NewAddAdd - onClick: ", String.valueOf(e));
                    }
                }

                boolean metric = (mAdditiveTypeSpinner.getSelectedItemPosition() != 0);
                boolean weight = (mAdditiveWeightSpinner.getSelectedItemPosition() != 0);

                long additiveID = db.getAdditiveIDFromName(mStringList.get(mAdditiveSpinner.getSelectedItemPosition()));

                if (isEdit) {  //Update existing record
                    db.updateAdditiveAdd(mAdditive.getID(), additiveID, meadID, mVolume, metric, weight);
                } else {
                    //Attempt to insert honey record into DB
                    db.insertAdditiveAdd(additiveID, meadID, mVolume, metric, weight);
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
