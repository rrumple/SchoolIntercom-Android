package com.myschoolintercom.www.schoolintercom.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myschoolintercom.www.schoolintercom.Constants;
import com.myschoolintercom.www.schoolintercom.R;
import com.myschoolintercom.www.schoolintercom.model.UserData;
import com.myschoolintercom.www.schoolintercom.types.SchoolData;
import com.myschoolintercom.www.schoolintercom.types.UserInfo;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RandyMBP on 7/29/15.
 */
public class GrandparentAdapter extends BaseAdapter
{
    Context mContext;
    LayoutInflater mInflater;
    List<UserInfo> grandparentData;
    UserData mainUserData;

    public GrandparentAdapter(Context context, LayoutInflater inflater, UserData mainUserData)
    {
        mContext = context;
        mInflater = inflater;
        grandparentData = new ArrayList<UserInfo>();
        this.mainUserData = mainUserData;
    }
    @Override
    public int getCount() {
        return grandparentData.size();
    }

    @Override
    public Object getItem(int position) {
        return grandparentData.get(position);
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
            convertView = mInflater.inflate(R.layout.settings_row, parent, false);

            holder = new ViewHolder();
            holder.grandparentNameTextview = (TextView) convertView.findViewById(R.id.setting_name);


            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        UserInfo data = (UserInfo) getItem(position);

        holder.grandparentNameTextview.setText(data.getFirstName());


        return convertView;
    }

    private static class ViewHolder
    {
        public TextView grandparentNameTextview;

    }

    public void updateData(List<UserInfo> grandparentData)
    {
        this.grandparentData = grandparentData;
        notifyDataSetChanged();
    }
}
