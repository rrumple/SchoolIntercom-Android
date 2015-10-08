package com.myschoolintercom.www.schoolintercom.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.myschoolintercom.www.schoolintercom.R;
import com.myschoolintercom.www.schoolintercom.model.UserData;
import com.myschoolintercom.www.schoolintercom.types.UserInfo;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RandyMBP on 9/12/15.
 */
public class UserListAdapter extends BaseAdapter
{
    Context mContext;
    LayoutInflater mInflater;
    List<UserInfo> userData;
    UserData mainUserData;

    public UserListAdapter(Context context, LayoutInflater inflater, UserData mainUserData)
    {
        mContext = context;
        mInflater = inflater;
        userData = new ArrayList<UserInfo>();
        this.mainUserData = mainUserData;
    }
    @Override
    public int getCount() {
        return userData.size();
    }

    @Override
    public Object getItem(int position) {
        return userData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        UserInfo data = (UserInfo) getItem(position);

        if(convertView == null)
        {
            convertView = mInflater.inflate(R.layout.user_list_row, parent, false);

            holder = new ViewHolder();
            holder.usernameTextView = (TextView) convertView.findViewById(R.id.user_name_textview);
            holder.kidNameTextView = (TextView) convertView.findViewById(R.id.kid_name_textview);
            holder.deleteButton = (ImageButton) convertView.findViewById(R.id.delete_school_button);


            //if(!mainUserData.getAccountType().equals("1") || data.getUserID().equals("999"))
            //{
                holder.deleteButton.setVisibility(View.INVISIBLE);
            /*}
            else
            {


                holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final UserInfo data = (UserInfo) getItem(position);
                        new AlertDialog.Builder(mContext)
                                .setTitle("Remove User?")
                                .setMessage("Are you sure you want to remove " + data.getFirstName() + " " + data.getLastName())
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which)
                                    {

                                    }
                                })
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                            try{
                                                ((OnNewsItemSelectedListener) mContext).onNewsItemPicked(position);
                                            }catch (ClassCastException cce){

                                            }
                                        /*
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
            }*/




            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }




        holder.usernameTextView.setText(data.getName());
        holder.kidNameTextView.setText(data.getKidName());


        return convertView;
    }

    private static class ViewHolder
    {
        public TextView usernameTextView;
        public TextView kidNameTextView;
        public ImageButton deleteButton;
        public TextView userID;

    }

    public void updateData(List<UserInfo> userData)
    {
        this.userData = userData;
        notifyDataSetChanged();
    }

    public interface OnNewsItemSelectedListener{
        public void onNewsItemPicked(int position);
    }

}