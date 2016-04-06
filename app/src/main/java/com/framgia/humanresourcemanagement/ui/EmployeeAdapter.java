package com.framgia.humanresourcemanagement.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.framgia.humanresourcemanagement.R;
import com.framgia.humanresourcemanagement.util.Employee;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by PhongTran on 3/20/2016.
 */
public class EmployeeAdapter extends ArrayAdapter<Employee> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<Employee> mListEmployee;

    private int mCount;
    private int mStepItem;

    public EmployeeAdapter(Context context, List<Employee> listEmployee, int mStartItem, int mStepItem) {
        super(context, R.layout.row_layout, listEmployee);
        this.mCount = Math.min(mStartItem, listEmployee.size());
        this.mStepItem = mStepItem;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mListEmployee = new ArrayList<Employee>();
        mListEmployee = listEmployee;
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public Employee getItem(int position) {
        return mListEmployee.get(position);
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
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (mListEmployee.size() <= position) {
            return convertView;
        }
        viewHolder.mTextEmployeeName.setText(mListEmployee.get(position).getName());
        if (mListEmployee.get(position).getStatus().equalsIgnoreCase("LeftJob")) {
            viewHolder.mTextEmployeeName.setBackgroundColor(Color.GRAY);
        } else {
            viewHolder.mTextEmployeeName.setBackgroundColor(Color.WHITE);
        }
        return convertView;
    }

    private class ViewHolder {
        private TextView mTextEmployeeName;
    }

    public boolean showMore() {
        if (mCount == mListEmployee.size()) {
            return true;
        } else {
            mCount = Math.min(mCount + mStepItem, mListEmployee.size());
            notifyDataSetChanged();
            return endReached();
        }
    }

    public boolean endReached() {
        return mCount == mListEmployee.size();
    }

}
