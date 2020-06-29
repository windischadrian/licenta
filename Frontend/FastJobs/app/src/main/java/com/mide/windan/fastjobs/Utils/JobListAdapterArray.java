package com.mide.windan.fastjobs.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mide.windan.fastjobs.Models.Job;
import com.mide.windan.fastjobs.R;

import java.util.ArrayList;
import java.util.List;


public class JobListAdapterArray extends ArrayAdapter<Job> {
    private Activity activity;
    private List<Job> jobList;
    private List<String> distance;
    private static LayoutInflater inflater = null;

    public JobListAdapterArray (Activity activity, int textViewResourceId, List<Job> jobList, List<String> distance) {
        super(activity, textViewResourceId, jobList);
        try {
            this.activity = activity;
            this.jobList = jobList;
            this.distance = distance;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JobListAdapterArray (Activity activity, int textViewResourceId, List<Job> jobList) {
        super(activity, textViewResourceId, jobList);
        try {
            this.activity = activity;
            this.jobList = jobList;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getCount() {
        return jobList.size();
    }

    public Job getItem(Job position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView jobTitleTextView;
        public TextView beginDateTextView;
        public TextView endDateTextView;
        public TextView distanceTextView;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.job_item, null);
                holder = new ViewHolder();

                holder.jobTitleTextView = (TextView) vi.findViewById(R.id.jobTitleTextView);
                holder.beginDateTextView = (TextView) vi.findViewById(R.id.beginDateTextView);
                holder.endDateTextView = (TextView) vi.findViewById(R.id.endDateTextView);
                holder.distanceTextView = (TextView) vi.findViewById(R.id.distanceTextView);


                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }



            holder.jobTitleTextView.setText(jobList.get(position).getJobTitle());
            holder.beginDateTextView.setText(jobList.get(position).getBeginDate());
            holder.endDateTextView.setText(jobList.get(position).getEndDate());
            if(distance.get(position) == null) {
                holder.distanceTextView.setText("");
            }else{
                holder.distanceTextView.setText(distance.get(position).toString() + " km");
            }


        } catch (Exception e) {


        }
        return vi;
    }
}
