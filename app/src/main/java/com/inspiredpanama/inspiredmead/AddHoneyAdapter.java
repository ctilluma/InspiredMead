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

public class AddHoneyAdapter extends ArrayAdapter {

    private Activity activity;
    private List<Honey> honeyList;

    public AddHoneyAdapter(Activity activity, List<Honey> items) {
        super(activity, R.layout.content_mead_layout, items);
        this.activity = activity;
        this.honeyList = items;
    }

    @Override
    public int getCount() {
        return honeyList.size();
    }

    @Override
    public Honey getItem(int index) {
        return honeyList.get(index);
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

        Honey mHoney = honeyList.get(position);

        name.setText(mHoney.getName());

        String tString; //temp String

        for (int i = 0; i < honeyList.size(); i++) {
            if (honeyList.get(i).getMetric() == false) {
                tString = "gallons : ";
            } else {
                tString = "litres : ";
            }

            amount.setText((String.valueOf(df.format(mHoney.getVolume()))));
            type.setText(tString);
        }
        return convertView;

    }
}
