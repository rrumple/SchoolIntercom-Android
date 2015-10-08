package com.myschoolintercom.www.schoolintercom.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.ColorInt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myschoolintercom.www.schoolintercom.Constants;
import com.myschoolintercom.www.schoolintercom.R;
import com.myschoolintercom.www.schoolintercom.model.UserData;
import com.myschoolintercom.www.schoolintercom.types.FundraiserData;
import com.myschoolintercom.www.schoolintercom.types.KidInfo;
import com.myschoolintercom.www.schoolintercom.types.Teacher;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RandyMBP on 8/1/15.
 */
public class FundraisingAdapter extends BaseAdapter
{
    Context mContext;
    LayoutInflater mInflater;
    List<FundraiserData> fundraiserData;
    UserData mainUserData;


    public FundraisingAdapter(Context context, LayoutInflater inflater, UserData mainUserData)
    {
        mContext = context;
        mInflater = inflater;
        fundraiserData = new ArrayList<FundraiserData>();
        this.mainUserData = mainUserData;

    }
    @Override
    public int getCount() {
        return fundraiserData.size();
    }

    @Override
    public Object getItem(int position) {
        return fundraiserData.get(position);
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
            convertView = mInflater.inflate(R.layout.fundraiser_row, parent, false);

            holder = new ViewHolder();
            holder.schoolLogoImageview = (ImageView) convertView.findViewById(R.id.school_logo_imageview);
            holder.fundraiserTitle = (TextView) convertView.findViewById(R.id.fundraiser_title_textview);
            holder.contentTextView = (TextView) convertView.findViewById(R.id.content_textview);
            holder.buyButton = (Button) convertView.findViewById(R.id.purchase_button);
            holder.moreInfoButton = (Button) convertView.findViewById(R.id.more_info_button);



            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }


        final FundraiserData data = (FundraiserData) getItem(position);
        holder.fundraiserTitle.setText(data.getMainTitle());
        holder.contentTextView.setText(data.getDetailText());
        if(data.getIsInAppPurchase().equals("1")) {
            if(data.isBuyButtonEnabled()) {
                if (Integer.parseInt(mainUserData.getAccountType()) > 0 && Integer.parseInt(mainUserData.getAccountType()) < 8) {
                    holder.buyButton.setEnabled(false);
                    holder.buyButton.setText("Granted");
                } else {
                    holder.buyButton.setEnabled(true);
                    holder.buyButton.setText("Buy $" + data.getPrice());
                    holder.buyButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                ((BuyButtonPressedListener) mContext).onBuyButtonPicked(position, data.getMainTitle());
                            } catch (ClassCastException cce) {

                            }
                        }
                    });
                }
            }
            else
            {
                if(mainUserData.isHasPurchased())
                {
                    holder.buyButton.setEnabled(false);
                    if (Integer.parseInt(mainUserData.getAccountType()) > 0 && Integer.parseInt(mainUserData.getAccountType()) < 8)
                        holder.buyButton.setText("Granted");
                    else
                        holder.buyButton.setText("Purchased");

                    holder.buyButton.setTextColor(Color.GRAY);
                }
            }

        }
        else
        {
            holder.buyButton.setText(data.getBuyButtonText());
            if(!data.getBuyButtonLink().equals(""))
            {
                holder.buyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            ((BuyButtonLinkPressedListener) mContext).onBuyButtonLinkPicked(position, data.getBuyButtonLink());
                        } catch (ClassCastException cce) {

                        }
                    }
                });
            }
        }

        if(!data.getMoreInfoLink().equals(""))
        {
            holder.moreInfoButton.setVisibility(View.VISIBLE);
            holder.moreInfoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try{
                        ((ShowMoreInfoListener) mContext).onMoreInfoPicked(position);
                    }catch (ClassCastException cce){

                    }
                }
            });
        }
        else
        {
            holder.moreInfoButton.setVisibility(View.INVISIBLE);
        }


        File file = new File(mContext.getFilesDir(), mainUserData.getSchoolData().getSchoolImageName());

        if(file.exists()){
            Picasso.with(mContext).load(file).into(holder.schoolLogoImageview);

        }
        else
        {
            String fileName = mainUserData.getSchoolData().getSchoolImageName().toString();
            String url = Constants.SCHOOL_LOGO_PATH + fileName;
            Picasso.with(mContext).load(url).into(holder.schoolLogoImageview);
        }



        return convertView;
    }

    private static class ViewHolder
    {
        public ImageView schoolLogoImageview;
        public TextView fundraiserTitle;
        public TextView contentTextView;
        public Button buyButton;
        public Button moreInfoButton;
    }

    public void updateData(List<FundraiserData> fundraiserData)
    {
        this.fundraiserData = fundraiserData;
        notifyDataSetChanged();
    }

    public interface ShowMoreInfoListener{
        public void onMoreInfoPicked(int position);
    }

    public interface BuyButtonPressedListener {
        public void onBuyButtonPicked(int position, String title);
    }

    public interface BuyButtonLinkPressedListener {
        public void onBuyButtonLinkPicked(int position, String title);
    }

}
