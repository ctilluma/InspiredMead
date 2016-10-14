/**
 * Created by ctilluma on 10/8/16.
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

        TextView amount = (TextView) convertView.findViewById(R.id.al_amount);
        TextView type = (TextView) convertView.findViewById(R.id.al_type);
        TextView name = (TextView) convertView.findViewById(R.id.al_name);

        Additive mAddit = mAddList.get(position);

        name.setText(mAddit.getName());

        String tString = ""; //temp String

        for (int i = 0; i < mAddList.size(); i++) {
            if (mAddList.get(i).getIsWeight() == false) {
                if (mAddList.get(i).getMetric() == false) {
                    tString = "gallons : ";
                } else {
                    tString = "litres : ";
                }
            } else {
                if (mAddList.get(i).getMetric() == false) {
                    tString = "oz : ";
                } else {
                    tString = "grams : ";
                }
            }
        }


        amount.setText((String.valueOf(df.format(mAddit.getAmount()))));
        type.setText(tString);

        return convertView;
    }
}
