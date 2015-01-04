package com.dongyun.sangdang;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PostListAdapter extends BaseAdapter {

    Activity context;
    ArrayList<String> title;
    ArrayList<String> date;
    ArrayList<String> author;

    public PostListAdapter(Activity context, ArrayList<String> title,
                           ArrayList<String> date, ArrayList<String> author) {
        super();
        this.context = context;
        this.title = title;
        this.date = date;
        this.author = author;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return title.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }



    private class ViewHolder {
        TextView txtViewTitle;
        TextView txtViewDate;
        TextView txtViewAuthor;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        LayoutInflater inflater =  context.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.row_layout, null);
            holder = new ViewHolder();
            holder.txtViewTitle = (TextView) convertView.findViewById(R.id.title);
            holder.txtViewDate = (TextView) convertView.findViewById(R.id.date);
            holder.txtViewAuthor = (TextView) convertView.findViewById(R.id.author);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtViewTitle.setText(title.get(position));
        holder.txtViewDate.setText(date.get(position));
        holder.txtViewAuthor.setText(author.get(position));
        return convertView;
    }

}