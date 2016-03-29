package com.framgia.humanresourcemanagement.ui;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.framgia.humanresourcemanagement.R;
import com.framgia.humanresourcemanagement.data.DBManager;
import com.framgia.humanresourcemanagement.util.Department;
import com.framgia.humanresourcemanagement.util.Employee;

import java.util.List;


/**
 * Created by PhongTran on 3/20/2016.
 */
public class DepartmentAdapter extends BaseAdapter implements View.OnClickListener{
    public static final String KEY_EDIT_DEPART = "edit_depart";
    public static final String KEY_NAME_DEPARTMENT = "name_department";
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private DBManager mDBManager;
    private List<Department> mListDepartment;
    private Department mDepartment;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_department, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mTextDepartName = (TextView) convertView.findViewById(R.id.text_depart_name);
            viewHolder.mButtonEdit = (Button) convertView.findViewById(R.id.button_edit_item_depart);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTextDepartName.setText(mListDepartment.get(position).getName());
        mDepartment = mListDepartment.get(position);
        viewHolder.mButtonEdit.setOnClickListener(this);
        viewHolder.mButtonEdit.setTag(mDepartment);
        viewHolder.mTextDepartName.setOnClickListener(this);
        viewHolder.mTextDepartName.setTag(mDepartment);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        Department department = (Department)v.getTag();
        switch (v.getId()){
            case R.id.text_depart_name:
                Intent intentSend = new Intent(mContext, EmployeeActivity.class);
                intentSend.putExtra(KEY_NAME_DEPARTMENT, department.getName());
                //Log.i("Phong",mDepartment.getName()+"");
                mContext.startActivity(intentSend);
                break;
            case R.id.button_edit_item_depart:
                Intent intent = new Intent(mContext, EditDepartment.class);
                intent.putExtra(KEY_EDIT_DEPART, department);
                mContext.startActivity(intent);
                break;
            default:
                break;
        }
    }

    private class ViewHolder {
        private TextView mTextDepartName;
        private Button mButtonEdit;
    }
}
