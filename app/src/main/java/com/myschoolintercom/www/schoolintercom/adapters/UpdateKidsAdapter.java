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
import com.myschoolintercom.www.schoolintercom.R;
import com.myschoolintercom.www.schoolintercom.model.UpdateProfileModel;
import com.myschoolintercom.www.schoolintercom.model.UserData;
import com.myschoolintercom.www.schoolintercom.types.Kid;
import com.myschoolintercom.www.schoolintercom.types.KidInfo;
import com.myschoolintercom.www.schoolintercom.types.SchoolData;
import com.myschoolintercom.www.schoolintercom.types.Teacher;
import com.myschoolintercom.www.schoolintercom.types.UserInfo;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RandyMBP on 7/30/15.
 */
public class UpdateKidsAdapter extends BaseAdapter
{
    Context mContext;
    LayoutInflater mInflater;
    List<KidInfo> kidData;
    UserData mainUserData;

    public UpdateKidsAdapter(Context context, LayoutInflater inflater, UserData mainUserData)
    {
        mContext = context;
        mInflater = inflater;
        kidData = new ArrayList<KidInfo>();
        this.mainUserData = mainUserData;
    }
    @Override
    public int getCount() {
        return kidData.size();
    }

    @Override
    public Object getItem(int position) {
        return kidData.get(position);
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
            convertView = mInflater.inflate(R.layout.update_kids_row, parent, false);

            holder = new ViewHolder();
            holder.kidNameTextview = (TextView) convertView.findViewById(R.id.school_name_textview);
            holder.schoolLogoImageview = (ImageView) convertView.findViewById(R.id.school_logo_imageview);
            holder.deleteButton = (ImageButton) convertView.findViewById(R.id.delete_kid_button);
            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final KidInfo data = (KidInfo) getItem(position);
                    new AlertDialog.Builder(mContext)
                            .setTitle("Remove Child?")
                            .setMessage("Are you sure you want to remove " + data.getKidFName())
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which)
                                {

                                }
                            })
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    UpdateProfileModel.deleteKidFromDatabase(data.getId(), new UpdateProfileModel.WebRequestCompleteCallback() {
                                        @Override
                                        public void webRequestResponse(ArrayList dataArray) {
                                            final Handler mainHandler = new Handler(Looper.getMainLooper());
                                            mainHandler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    for (int i = 0; i < kidData.size(); i++) {
                                                        if (kidData.get(i).getId().equals(data.getId())) {
                                                            kidData.remove(i);
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

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        KidInfo data = (KidInfo) getItem(position);

        holder.kidNameTextview.setText(data.getKidsFullName());

        File file = new File(mContext.getFilesDir(), mainUserData.getSchoolImageName(data.getSchoolID()));

        if(file.exists()){
            Picasso.with(mContext).load(file).into(holder.schoolLogoImageview);

        }
        else
        {
            String fileName = mainUserData.getSchoolImageName(data.getSchoolID());
            String url = Constants.SCHOOL_LOGO_PATH + fileName;
            Picasso.with(mContext).load(url).into(holder.schoolLogoImageview);
        }

        return convertView;
    }

    private static class ViewHolder
    {
        public TextView kidNameTextview;
        public ImageView schoolLogoImageview;
        public ImageButton deleteButton;


    }

    public void updateData(List<KidInfo> kidData)
    {
        this.kidData = kidData;
        notifyDataSetChanged();
    }
}