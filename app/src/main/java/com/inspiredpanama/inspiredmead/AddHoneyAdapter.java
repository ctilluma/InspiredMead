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

        TextView amountView = (TextView) convertView.findViewById(R.id.al_amount);
        TextView typeView = (TextView) convertView.findViewById(R.id.al_type);
        TextView nameView = (TextView) convertView.findViewById(R.id.al_name);

        Honey mHoney = honeyList.get(position);
        String name = mHoney.getName();
        double amount = mHoney.getVolume();
        boolean isMetric = mHoney.getMetric();

        String tString; //temp String

        if (isMetric) {
                tString = "litres : ";
        } else {
            tString = "gallons : ";
            }

        //Set View
        nameView.setText(name);
        amountView.setText((String.valueOf(df.format(amount))));
        typeView.setText(tString);


        return convertView;

    }
}
