package com.myschoolintercom.www.schoolintercom.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.myschoolintercom.www.schoolintercom.Constants;
import com.myschoolintercom.www.schoolintercom.Helpers;
import com.myschoolintercom.www.schoolintercom.NSHelpers;
import com.myschoolintercom.www.schoolintercom.R;
import com.myschoolintercom.www.schoolintercom.model.UpdateProfileModel;
import com.myschoolintercom.www.schoolintercom.model.UserData;
import com.myschoolintercom.www.schoolintercom.screens.MainScreens.SwitchSchoolsScreen;
import com.myschoolintercom.www.schoolintercom.types.NewsData;
import com.myschoolintercom.www.schoolintercom.types.SchoolData;
import com.myschoolintercom.www.schoolintercom.types.Teacher;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RandyMBP on 5/17/15.
 */
public class SwitchSchoolsAdapter extends BaseAdapter
{
    Context mContext;
    LayoutInflater mInflater;
    List<SchoolData> schoolData;
    UserData mainUserData;

    public SwitchSchoolsAdapter(Context context, LayoutInflater inflater, UserData mainUserData)
    {
        mContext = context;
        mInflater = inflater;
        schoolData = new ArrayList<SchoolData>();
        this.mainUserData = mainUserData;
    }
    @Override
    public int getCount() {
        return schoolData.size();
    }

    @Override
    public Object getItem(int position) {
        return schoolData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null)
        {
            convertView = mInflater.inflate(R.layout.switch_school_row, parent, false);

            holder = new ViewHolder();
            holder.schoolNameTextview = (TextView) convertView.findViewById(R.id.school_name_textview);
            holder.schoolLogoImageview = (ImageView) convertView.findViewById(R.id.school_logo_imageview);
            holder.deleteButton = (ImageButton) convertView.findViewById(R.id.delete_school_button);
            holder.schoolBadge = (TextView) convertView.findViewById(R.id.school_badge);

            if(mainUserData.getAccountType().equals("6"))
            {
                holder.deleteButton.setVisibility(View.INVISIBLE);
            }
            else
            {


                holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final SchoolData data = (SchoolData) getItem(position);
                        new AlertDialog.Builder(mContext)
                                .setTitle("Remove School?")
                                .setMessage("Are you sure you want to remove " + data.getSchoolName())
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which)
                                    {

                                    }
                                })
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(mainUserData.removeSchoolFromPhone(data.getSchoolID()))
                                        {
                                            try{
                                                ((OnNewsItemSelectedListener) mContext).onNewsItemPicked(position);
                                            }catch (ClassCastException cce){

                                            }
                                            //logout of this school
                                        }

                                        UpdateProfileModel.changeSchoolStatusForUser(data.getSchoolID(), mainUserData.getUserID(), "0", new UpdateProfileModel.WebRequestCompleteCallback() {
                                            @Override
                                            public void webRequestResponse(ArrayList dataArray) {
                                                final Handler mainHandler = new Handler(Looper.getMainLooper());
                                                mainHandler.post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        for (int i = 0; i < schoolData.size(); i++) {
                                                            if (schoolData.get(i).getSchoolID().equals(data.getSchoolID())) {
                                                                schoolData.remove(i);
                                                            }
                                                        }


                                                        notifyDataSetChanged();


                                                    }
                                                });
                                            }
                                        });

                                    }
                                })
                                        //.setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                    }
                });
            }




            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        SchoolData data = (SchoolData) getItem(position);

        if(data.getBadgeCount() > 9)
        {
            holder.schoolBadge.setVisibility(View.VISIBLE);
            holder.schoolBadge.setText("9+");
        }
        else if(data.getBadgeCount() == 0)
        {
            holder.schoolBadge.setVisibility(View.INVISIBLE);
        }
        else
        {
            holder.schoolBadge.setVisibility(View.VISIBLE);
            holder.schoolBadge.setText(Integer.toString(data.getBadgeCount()));
        }

        holder.schoolNameTextview.setText(data.getSchoolName());

        File file = new File(mContext.getFilesDir(), data.getSchoolImageName());

        if(file.exists()){
            Picasso.with(mContext).load(file).into(holder.schoolLogoImageview);

        }
        else
        {
            String fileName = data.getSchoolImageName().toString();
            String url = Constants.SCHOOL_LOGO_PATH + fileName;
            Picasso.with(mContext).load(url).into(holder.schoolLogoImageview);
        }

        return convertView;
    }

    private static class ViewHolder
    {
        public TextView schoolNameTextview;
        public ImageView schoolLogoImageview;
        public ImageButton deleteButton;
        public TextView schoolID;
        public TextView schoolBadge;

    }

    public void updateData(List<SchoolData> schoolData)
    {
        this.schoolData = schoolData;
        notifyDataSetChanged();
    }

    public interface OnNewsItemSelectedListener{
        public void onNewsItemPicked(int position);
    }

}
