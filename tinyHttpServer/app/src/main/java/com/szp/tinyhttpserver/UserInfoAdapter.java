package com.szp.tinyhttpserver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class UserInfoAdapter extends BaseAdapter{
    private Context mContext;
    ArrayList<HashMap<String, String>> mUserInfoList = new ArrayList<HashMap<String, String>>();

    public UserInfoAdapter(Context context, ArrayList<HashMap<String, String>> list) {
        mContext = context;
        mUserInfoList = list;
    }

    @Override
    public int getCount() {
        return mUserInfoList != null ? mUserInfoList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mUserInfoList != null ? mUserInfoList.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.user_info_item, null);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.value = (TextView) convertView.findViewById(R.id.value);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(mUserInfoList.get(position).get(Utils.ITEM_NAME));
        viewHolder.value.setText(mUserInfoList.get(position).get(Utils.ITEM_VALUE));

        return convertView;
    }

    private class ViewHolder {
        TextView name;
        TextView value;
    }
}
