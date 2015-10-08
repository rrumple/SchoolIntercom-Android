package com.myschoolintercom.www.schoolintercom.adapters;

/**
 * Created by RandyMBP on 7/31/15.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.preference.DialogPreference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.myschoolintercom.www.schoolintercom.Constants;
import com.myschoolintercom.www.schoolintercom.R;
import com.myschoolintercom.www.schoolintercom.model.UpdateProfileModel;
import com.myschoolintercom.www.schoolintercom.model.UserData;
import com.myschoolintercom.www.schoolintercom.types.KidInfo;
import com.myschoolintercom.www.schoolintercom.types.Teacher;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by RandyMBP on 7/30/15.
 */
public class TeacherAdapter extends BaseAdapter
{
    Context mContext;
    LayoutInflater mInflater;
    List<Teacher> teacherData;
    UserData mainUserData;
    KidInfo kidData;
    LinearLayout teacherLayout;

    public TeacherAdapter(Context context, LayoutInflater inflater, UserData mainUserData, KidInfo kidData, LinearLayout teacherLayout)
    {
        mContext = context;
        mInflater = inflater;
        teacherData = new ArrayList<Teacher>();
        this.mainUserData = mainUserData;
        this.kidData = kidData;
        this.teacherLayout = teacherLayout;
    }
    @Override
    public int getCount() {
        return teacherData.size();
    }

    @Override
    public Object getItem(int position) {
        return teacherData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;


        if(convertView == null)
        {
            convertView = mInflater.inflate(R.layout.teacher_row, parent, false);

            holder = new ViewHolder();
            holder.teacherNameTextview = (TextView) convertView.findViewById(R.id.teacherName);
            holder.classNameTextview = (TextView) convertView.findViewById(R.id.classNameText);
            holder.deleteButton = (ImageButton) convertView.findViewById(R.id.delete_class_button);
            holder.classID = (TextView) convertView.findViewById(R.id.classIDTextview);
            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Teacher data = (Teacher) getItem(position);
                    new AlertDialog.Builder(mContext)
                            .setTitle("Remove Teacher?")
                            .setMessage("Are you sure you want to remove " + data.getTeacherName())
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which)
                                {

                                }
                            })
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //FlurryAgent.logEvent("USER_REMOVED_TEACHER_FROM_KID");
                                    UpdateProfileModel.deleteClass(kidData.getId(), data.getClassroomData().get(0).getClassID(), new UpdateProfileModel.WebRequestCompleteCallback() {
                                        @Override
                                        public void webRequestResponse(ArrayList dataArray) {
                                            final Handler mainHandler = new Handler(Looper.getMainLooper());
                                            mainHandler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    for (int i = 0; i < teacherData.size(); i++) {
                                                        if (teacherData.get(i).getClassroomData().get(0).getClassID().equals(data.getClassroomData().get(0).getClassID())) {
                                                            teacherData.remove(i);

                                                        }
                                                    }
                                                    if (teacherData.size() == 0)
                                                        teacherLayout.setVisibility(View.INVISIBLE);

                                                    notifyDataSetChanged();

                                                    try {
                                                        ((OnNewsItemSelectedListener) mContext).onNewsItemPicked(position);
                                                    } catch (ClassCastException cce) {

                                                    }


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


        Teacher data = (Teacher) getItem(position);
        holder.teacherNameTextview.setText(data.getTeacherName());
        holder.classNameTextview.setText(data.getClassroomData().get(0).getClassName());



        return convertView;
    }

    private static class ViewHolder
    {
        public TextView teacherNameTextview;
        public TextView classNameTextview;
        public ImageButton deleteButton;
        public TextView classID;
    }

    public void updateData(List<Teacher> teacherData)
    {
        this.teacherData = teacherData;
        notifyDataSetChanged();
    }

    public interface OnNewsItemSelectedListener{
        public void onNewsItemPicked(int position);
    }
}
