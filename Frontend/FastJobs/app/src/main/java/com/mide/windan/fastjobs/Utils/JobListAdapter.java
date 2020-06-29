package com.mide.windan.fastjobs.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mide.windan.fastjobs.R;

public class JobListAdapter extends BaseAdapter {

    Context context;
    String[] jobTitleList;
    String[] startDateList;
    String[] endDateList;
    LayoutInflater inflater;

    public JobListAdapter(Context applicationContext, String[] jobTitleList, String[] startDateList, String[] endDateList){
        this.context = applicationContext;
        this.jobTitleList = jobTitleList;
        this.startDateList = startDateList;
        this.endDateList = endDateList;
        inflater = LayoutInflater.from(applicationContext);
    }

    @Override
    public int getCount() {
        return jobTitleList.length;
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
        convertView = inflater.inflate(R.layout.job_item, parent,  false);
        TextView jobTitleTextView = convertView.findViewById(R.id.jobTitleTextView);
        jobTitleTextView.setText(jobTitleList[position]);
        TextView beginDateTextView = convertView.findViewById(R.id.beginDateTextView);
        beginDateTextView.setText(startDateList[position]);
        TextView endDateTextView = convertView.findViewById(R.id.endDateTextView);
        endDateTextView.setText(endDateList[position]);

        return convertView;
    }
}
