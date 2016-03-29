package com.framgia.humanresourcemanagement.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import com.framgia.humanresourcemanagement.R;
import com.framgia.humanresourcemanagement.data.DBManager;
import com.framgia.humanresourcemanagement.util.Employee;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by PhongTran on 3/20/2016.
 */
public class EmployeeAdapter extends ArrayAdapter<Employee> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<Employee>  mList;

    private int mCount;
    private int mStepItem;

    public EmployeeAdapter(Context context, List<Employee> mListEmployee, int mStartItem, int mStepItem) {
        super(context, R.layout.row_layout, mListEmployee);
        this.mCount = Math.min(mStartItem, mListEmployee.size());
        this.mStepItem = mStepItem;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mList = new ArrayList<Employee>();
        mList = mListEmployee;
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public Employee getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_employee, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mTextEmployeeName = (TextView) convertView.findViewById(R.id.text_staff_name);
            viewHolder.mTextEmployeeStatus = (TextView) convertView.findViewById(R.id.text_staff_status);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (mList.size() <= position) {
            return convertView;
        }
        viewHolder.mTextEmployeeName.setText(mList.get(position).getName());
        viewHolder.mTextEmployeeStatus.setText(mList.get(position).getStatus());
        if (mList.get(position).getStatus().equalsIgnoreCase("LeftJob")) {
            viewHolder.mTextEmployeeName.setBackgroundColor(Color.GRAY);
            viewHolder.mTextEmployeeStatus.setBackgroundColor(Color.GRAY);
        } else {
            viewHolder.mTextEmployeeName.setBackgroundColor(Color.WHITE);
            viewHolder.mTextEmployeeStatus.setBackgroundColor(Color.WHITE);
        }
        return convertView;
    }

    private class ViewHolder {
        private TextView mTextEmployeeName, mTextEmployeeStatus;
    }

    public boolean showMore() {
        if (mCount == mList.size()) {
            return true;
        } else {
            mCount = Math.min(mCount + mStepItem, mList.size());
            notifyDataSetChanged();
            return endReached();
        }
    }

    public boolean endReached() {
        return mCount == mList.size();
    }

}
