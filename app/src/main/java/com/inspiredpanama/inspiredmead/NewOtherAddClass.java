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
 * Handles new additives.
 */

public class NewOtherAddClass extends Dialog implements
        View.OnClickListener {

    public Activity c;
    private DBMead db;
    private EditText eName, eSG, eDesc;

    public NewOtherAddClass(Activity a, DBMead db) {
        super(a);

        this.c = a;
        this.db = db;
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

        //Set Inputs
        eName = (EditText) findViewById(R.id.oe_name);
        eSG = (EditText) findViewById(R.id.oe_sg);
        eDesc = (EditText) findViewById(R.id.oe_desc);

        //Validate Gravity Text
        eSG.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                double mDouble;

                try {
                    mDouble = Double.parseDouble(eSG.getText().toString());

                    //Validate
                    if (mDouble < 0.00) {
                        eSG.setError("Specific Gravity too Low.");
                    } else if (mDouble > 1.50) {
                        eSG.setError("Specific Gravity too high.");
                    }
                } catch (NumberFormatException e) {
                    Log.e("NewOther - SG: ", e.toString());
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        // CHeck for what was clicked.
        switch (view.getId()) {
            case R.id.oe_button_ok:
                String name;
                double sg;
                String flavor;
                Additive mAddit = new Additive();

                //Set Data from Dialog
                //Set Name
                name = eName.getText().toString();

                //Set SG
                sg = 0.00;
                if (!eSG.getText().toString().equals("")) {
                    try {
                        double mDouble = Double.parseDouble(eSG.getText().toString());

                        if (mDouble > 0.00) {
                            sg = Double.parseDouble(eSG.getText().toString());
                        }
                    } catch (NumberFormatException e) {
                        Log.e("NewOther - SG: ", e.toString());
                    }
                }

                //Set Description
                flavor = eDesc.getText().toString();

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