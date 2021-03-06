/**
 * Created by ctilluma on 10/8/16.
 */
package com.inspiredpanama.inspiredmead;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MeadListAdapter extends ArrayAdapter {

    private Activity activity;
    private List<MeadData> meadList;

    public MeadListAdapter(Activity activity, List<MeadData> items) {
        super(activity, R.layout.content_mead_layout, items);
        this.activity = activity;
        this.meadList = items;
    }


    @Override
    public int getCount() {
        return meadList.size();
    }

    @Override
    public MeadData getItem(int index) {
        return meadList.get(index);
    }

    public long getItemID(int index) {
        return index;
    }

    //Set information for each item in list.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Formatters
        DecimalFormat df = new DecimalFormat("#.##");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss zzz");

        LayoutInflater mInflater = (LayoutInflater) activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.content_mead_layout, null);

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        TextView alcohol = (TextView) convertView.findViewById(R.id.alcohol);
        TextView volume = (TextView) convertView.findViewById(R.id.volume);

        MeadData mead = meadList.get(position);

        name.setText(mead.getName());
        if (mead.getDate() != null) {
            date.setText(String.valueOf(dateFormatter.format(mead.getDate().getTime())));
        }

        GregorianCalendar checkDate = new GregorianCalendar();
        //Subtract DAYS_BETWEEN_TESTING from current Date
        checkDate.add(Calendar.DATE, (MeadDisplay.DAYS_BETWEEN_TESTING * -1));
        if (checkDate.after(mead.getDate())) {
            date.setTextColor(ContextCompat.getColor(activity, R.color.mead_red));
        }

        alcohol.setText((String.valueOf(df.format(mead.getAlcohol())) + "% ABV"));
        volume.setText(String.valueOf(mead.getVolume()) + " litres");

        return convertView;
    }
}
