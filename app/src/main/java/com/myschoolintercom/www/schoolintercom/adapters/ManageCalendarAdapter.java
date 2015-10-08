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
import com.myschoolintercom.www.schoolintercom.types.CalendarData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RandyMBP on 9/13/15.
 */
public class ManageCalendarAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mInflater;
    List<CalendarData> calendarData;
    UserData mainUserData;

    public ManageCalendarAdapter(Context context, LayoutInflater inflater, UserData mainUserData) {
        mContext = context;
        mInflater = inflater;
        calendarData = new ArrayList<CalendarData>();
        this.mainUserData = mainUserData;
    }

    @Override
    public int getCount() {
        return calendarData.size();
    }

    @Override
    public Object getItem(int position) {
        return calendarData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.manage_calendar_row, null);

            holder = new ViewHolder();
            holder.calendarTextView = (TextView) convertView.findViewById(R.id.calendar_text_textview);
            holder.calendarDateTextView = (TextView) convertView.findViewById(R.id.calendar_date_textview);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CalendarData event = (CalendarData) getItem(position);

        holder.calendarTextView.setText(event.getCalTitle());


        String className = "";
        if(event.getClassID().equals("0"))
            className = "All Classes";
        else
            className = mainUserData.getClassName(event.getClassID());

        holder.calendarDateTextView.setText(Helpers.dateToStringMMMMddyyyyhhmma(event.getCalStartDate()) + " - " + className);

        return convertView;
    }

    private static class ViewHolder {
        public TextView calendarTextView;
        public TextView calendarDateTextView;
    }

    public void updateData(List<CalendarData> calendarData) {
        this.calendarData = calendarData;
        notifyDataSetChanged();
    }
}