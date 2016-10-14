package com.inspiredpanama.inspiredmead;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

/**
 * Created by ctilluma on 10/14/16.
 */

public class NewAddHoneyDialogClass extends Dialog implements
        View.OnClickListener {

    public Activity c;
    public Dialog d;
    Spinner mHoneySpinner;
    Spinner mHoneyTypeSpinner;
    List<String> mStringList;
    private long meadID;
    private DBMead db;


    public NewAddHoneyDialogClass(Activity a, long meadID, DBMead db) {
        super(a);

        this.c = a;
        this.meadID = meadID;
        this.db = db;
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
        ArrayAdapter<String> mHoneySpinAdapter = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item, mStringList);
        mHoneySpinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mHoneySpinner.setAdapter(mHoneySpinAdapter);

        mHoneyTypeSpinner = (Spinner) findViewById(R.id.ah_metric_spinner);
        ArrayAdapter<CharSequence> mHoneyTypeSpinnger = ArrayAdapter.createFromResource(c,
                R.array.volume_types, android.R.layout.simple_spinner_item);
        mHoneyTypeSpinner.setAdapter(mHoneyTypeSpinnger);

    }

    @Override
    public void onClick(View view) {
        double mVolume;

        switch (view.getId()) {
            case R.id.ah_button_ok:
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
