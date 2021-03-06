/**
 * Created by ctilluma on 10/8/16.
 *
 * Creates new additions for use in Mead
 */
package com.inspiredpanama.inspiredmead;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class AddAdditionsAdapter extends ArrayAdapter {

    private Activity activity;
    private List<Additive> mAddList;

    public AddAdditionsAdapter(Activity activity, List<Additive> items) {
        super(activity, R.layout.content_mead_layout, items);
        this.activity = activity;
        this.mAddList = items;
    }


    @Override
    public int getCount() {
        return mAddList.size();
    }

    @Override
    public Additive getItem(int index) {
        return mAddList.get(index);
    }

    public long getItemID(int index) {
        return index;
    }

    //Set information for each item in list.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Formatters
        DecimalFormat df = new DecimalFormat("#.##");

        LayoutInflater mInflater = (LayoutInflater) activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.content_add_layout, null);

        TextView amountView = (TextView) convertView.findViewById(R.id.al_amount);
        TextView typeView = (TextView) convertView.findViewById(R.id.al_type);
        TextView nameView = (TextView) convertView.findViewById(R.id.al_name);

        Additive mAddit = mAddList.get(position);
        String name = mAddit.getName();
        double amount = mAddit.getAmount();
        boolean isMetric = mAddit.getMetric();
        boolean isWeight = mAddit.getIsWeight();

        nameView.setText(name);

        String tString = ""; //temp String

        for (int i = 0; i < mAddList.size(); i++) {
            if (!isWeight) {
                if (!isMetric) {
                    tString = "gallons : ";
                } else {
                    tString = "litres : ";
                }
            } else {
                if (!isMetric) {
                    tString = "oz : ";
                } else {
                    tString = "grams : ";
                }
            }
        }

        amountView.setText((String.valueOf(df.format(amount))));
        typeView.setText(tString);

        return convertView;
    }
}
