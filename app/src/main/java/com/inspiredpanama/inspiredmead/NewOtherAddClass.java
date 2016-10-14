package com.inspiredpanama.inspiredmead;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by ctilluma on 10/14/16.
 */

public class NewOtherAddClass extends Dialog implements
        View.OnClickListener {

    public Activity c;
    public Dialog d;
    private DBMead db;

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
    }

    @Override
    public void onClick(View view) {
        EditText mEditText;
        switch (view.getId()) {
            case R.id.oe_button_ok:
                String name;
                double sg;
                String flavor;
                Additive mAddit = new Additive();

                //Set Data from Dialog

                mEditText = (EditText) findViewById(R.id.oe_name);
                if (mEditText.getText().toString() != null) {
                    name = mEditText.getText().toString();
                } else {
                    name = "";
                }

                mEditText = (EditText) findViewById(R.id.oe_sg);
                if (Double.parseDouble(mEditText.getText().toString()) > 0.00) {
                    sg = Double.parseDouble(mEditText.getText().toString());
                } else {
                    sg = 0.00;
                }

                mEditText = (EditText) findViewById(R.id.oe_desc);
                if (mEditText.getText().toString() != null) {
                    flavor = mEditText.getText().toString();
                } else {
                    flavor = "";
                }

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