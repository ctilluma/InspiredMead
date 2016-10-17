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
import android.widget.EditText;

/**
 * Created by ctilluma on 10/14/16.
 *
 * Handles adding Honey
 */

public class NewHoneyAddClass extends Dialog implements
        View.OnClickListener {

    public Activity c;
    private DBMead db;
    private EditText eName, eBrix, eDesc;

    public NewHoneyAddClass(Activity a, DBMead db) {
        super(a);

        this.c = a;
        this.db = db;
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

        //Set Inputs
        eName = (EditText) findViewById(R.id.he_name);
        eBrix = (EditText) findViewById(R.id.he_brix);
        eDesc = (EditText) findViewById(R.id.he_desc);


        //Validate Gravity Text
        eBrix.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                double mDouble;

                try {
                    mDouble = Double.parseDouble(eBrix.getText().toString());

                    //Validate
                    if (mDouble < 0.00) {
                        eBrix.setError("BRIX too Low.");
                    } else if (mDouble > 100.00) {
                        eBrix.setError("BRIX is too high.");
                    }
                } catch (NumberFormatException e) {
                    Log.e("NewHoney - BRIX: ", e.toString());
                }
            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.he_button_ok:
                String name;
                double brix;
                String flavor;

                //Set Data from Dialog
                //Set Name
                name = eName.getText().toString();

                //Set BRIX
                brix = 80.00;
                if (!eBrix.getText().toString().equals("")) {
                    try {
                        double tDouble = Double.parseDouble(eBrix.getText().toString());

                        if (tDouble > 0.00) {
                            brix = tDouble;
                        }
                    } catch (NumberFormatException e) {
                        Log.e("NewHoney - BRIX: ", String.valueOf(e));
                    }
                }

                //Set Description
                flavor = eDesc.getText().toString();

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

