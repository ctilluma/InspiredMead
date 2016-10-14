package com.inspiredpanama.inspiredmead;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
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
 */

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
    private DBMead db;


    public NewAddAdditiveDialogClass(Activity a, long meadIDdb, DBMead db) {
        super(a);

        this.c = a;
        this.meadID = meadID;
        this.db = db;
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
        ArrayAdapter<String> mAdditiveSpinAdapter = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item, mStringList);
        mAdditiveSpinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAdditiveSpinner.setAdapter(mAdditiveSpinAdapter);

        mAdditiveWeightSpinner = (Spinner) findViewById(R.id.ao_weight_spinner);
        ArrayAdapter<CharSequence> mAdditiveWeightSpinAdapter = ArrayAdapter.createFromResource(c,
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
            mAdditiveTypeSpinAdapter = ArrayAdapter.createFromResource(c,
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
        double mVolume;

        switch (view.getId()) {
            case R.id.ao_button_ok:
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
