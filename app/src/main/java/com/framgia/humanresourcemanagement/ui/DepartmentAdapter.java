package com.framgia.humanresourcemanagement.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.framgia.humanresourcemanagement.R;
import com.framgia.humanresourcemanagement.data.DBManager;
import com.framgia.humanresourcemanagement.util.Department;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by PhongTran on 3/20/2016.
 */
public class DepartmentAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private DBManager mDBManager;
    private List<Department> mListDepartment;

    public DepartmentAdapter(Context mContext, List<Department> listDepartment) {
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
        mDBManager = new DBManager(mContext);
        mListDepartment = listDepartment;
    }

    @Override
    public int getCount() {
        return mListDepartment.size();
    }

    @Override
    public Department getItem(int position) {
        return mListDepartment.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_department, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mTextDepartName = (TextView) convertView.findViewById(R.id.text_depart_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTextDepartName.setText(mListDepartment.get(position).getName());
        return convertView;
    }

    private class ViewHolder {
        private TextView mTextDepartName;
    }
}
