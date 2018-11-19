package csc472.depaul.edu.metalcrawler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context context;
    String[] labels;
    String[] descriptions;
    LayoutInflater inflater;

    public CustomAdapter(Context applicationContext, String[] labels, String[] descriptions){
        this.context = applicationContext;
        this.labels = labels;
        this.descriptions = descriptions;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return labels.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.activity_listview, null);
        TextView key = (TextView) convertView.findViewById(R.id.key);
        TextView description = (TextView) convertView.findViewById(R.id.textView);
        key.setText(labels[position]);
        description.setText(descriptions[position]);
        return convertView;
    }

}
