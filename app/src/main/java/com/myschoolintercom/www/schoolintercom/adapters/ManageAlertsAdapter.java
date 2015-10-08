package com.myschoolintercom.www.schoolintercom.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.myschoolintercom.www.schoolintercom.Helpers;
import com.myschoolintercom.www.schoolintercom.R;
import com.myschoolintercom.www.schoolintercom.model.UserData;
import com.myschoolintercom.www.schoolintercom.types.AlertData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RandyMBP on 9/13/15.
 */
public class ManageAlertsAdapter extends BaseAdapter
{
    Context mContext;
    LayoutInflater mInflater;
    List<AlertData> alertData;
    UserData mainUserData;

    public ManageAlertsAdapter(Context context, LayoutInflater inflater, UserData mainUserData)
    {
        mContext = context;
        mInflater = inflater;
        alertData = new ArrayList<AlertData>();
        this.mainUserData = mainUserData;
    }

    @Override
    public int getCount() {
        return alertData.size();
    }

    @Override
    public Object getItem(int position) {
        return alertData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null)
        {
            convertView = mInflater.inflate(R.layout.manage_alert_row, null);

            holder = new ViewHolder();
            holder.alertTextView = (TextView) convertView.findViewById(R.id.alert_text_textview);
            holder.alertDateTextView = (TextView) convertView.findViewById(R.id.alert_date_textview);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        AlertData alert = (AlertData) getItem(position);

        holder.alertTextView.setText(alert.getAlertText());



        holder.alertDateTextView.setText("Sent: " + Helpers.dateToStringMMMMddyyyy(alert.getAlertTimeSent()));

        return convertView;
    }

    private static class ViewHolder
    {
        public TextView alertTextView;
        public TextView alertDateTextView;
    }

    public void updateData(List<AlertData> alertData)
    {
        this.alertData = alertData;
        notifyDataSetChanged();
    }
}
