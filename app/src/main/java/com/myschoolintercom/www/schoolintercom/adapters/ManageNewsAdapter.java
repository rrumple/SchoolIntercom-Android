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
import com.myschoolintercom.www.schoolintercom.types.NewsData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RandyMBP on 9/13/15.
 */
public class ManageNewsAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mInflater;
    List<NewsData> newsData;
    UserData mainUserData;

    public ManageNewsAdapter(Context context, LayoutInflater inflater, UserData mainUserData) {
        mContext = context;
        mInflater = inflater;
        newsData = new ArrayList<NewsData>();
        this.mainUserData = mainUserData;
    }

    @Override
    public int getCount() {
        return newsData.size();
    }

    @Override
    public Object getItem(int position) {
        return newsData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.manage_news_row, null);

            holder = new ViewHolder();
            holder.newsTextView = (TextView) convertView.findViewById(R.id.news_text_textview);
            holder.newsDateTextView = (TextView) convertView.findViewById(R.id.news_date_textview);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        NewsData post = (NewsData) getItem(position);

        holder.newsTextView.setText(post.getNewsTitle());


        String className = "";
        if(post.getClassID().equals("0"))
            className = "All Classes";
        else
            className = mainUserData.getClassName(post.getClassID());

        holder.newsDateTextView.setText(Helpers.dateToStringMMMMddyyyy(post.getNewsDate()) + " - " + className);

        return convertView;
    }

    private static class ViewHolder {
        public TextView newsTextView;
        public TextView newsDateTextView;
    }

    public void updateData(List<NewsData> newsData) {
        this.newsData = newsData;
        notifyDataSetChanged();
    }

}
