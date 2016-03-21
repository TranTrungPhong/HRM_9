package com.framgia.humanresourcemanagement.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.framgia.humanresourcemanagement.R;
import com.framgia.humanresourcemanagement.data.DBManager;
import com.framgia.humanresourcemanagement.util.Employee;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by PhongTran on 3/21/2016.
 */
public class InfoActivity extends Activity implements View.OnClickListener {
    public static final String KEY_BOX_EDIT = "edit";

    private TextView mTextName;
    private TextView mTextAddress;
    private TextView mTextBirthday;
    private TextView mTextPhone;
    private TextView mTextStatus;
    private TextView mTextPosition;
    private Button mButtonEdit;
    private Employee mEmployee;
    private String mString;
    private List<Employee> mListEmployee;
    private DBManager mDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff);
        mDBManager = new DBManager(getApplicationContext());
        mListEmployee = mDBManager.getListEmployee();

        mTextName = (TextView) findViewById(R.id.text_name_employee);
        mTextAddress = (TextView) findViewById(R.id.text_address_employee);
        mTextBirthday = (TextView) findViewById(R.id.text_birthday_employee);
        mTextPhone = (TextView) findViewById(R.id.text_phone_employee);
        mTextStatus = (TextView) findViewById(R.id.text_status_employee);
        mTextPosition = (TextView) findViewById(R.id.text_position_employee);

        final Intent intent = getIntent();
        mEmployee = (Employee) intent.getSerializableExtra(EmployeeActivity.KEY_BOX_EM);
        mTextName.setText("Name : " + mEmployee.getName());
        mTextAddress.setText("Address : " + mEmployee.getAddress());
        mTextBirthday.setText("Birthday : " + mEmployee.getBirthday());
        mTextPhone.setText("Phone : " + mEmployee.getPhone());
        mTextStatus.setText("Status : " + mEmployee.getStatus());
        mTextPosition.setText("Position : " + mEmployee.getPosition());
        mString = mTextName.getText().toString();
        mButtonEdit = (Button) findViewById(R.id.button_edit_info_staff);
        mButtonEdit.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mListEmployee = mDBManager.getListEmployee();
        for (int i = 0; i < mListEmployee.size(); i++) {
            if (mListEmployee.get(i).getName().equals(mEmployee.getName())) {
                mTextName.setText("Name : " + mEmployee.getName());
                mTextAddress.setText("Address : " + mListEmployee.get(i).getAddress());
                mTextBirthday.setText("Birthday : " + mListEmployee.get(i).getBirthday());
                mTextPhone.setText("Phone : " + mListEmployee.get(i).getPhone());
                mTextStatus.setText("Status : " + mListEmployee.get(i).getStatus());
                mTextPosition.setText("Position : " + mListEmployee.get(i).getPosition());
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_edit_info_staff) {
            Intent intent1 = new Intent();
            intent1.setClass(InfoActivity.this, EditEmployee.class);
            intent1.putExtra(KEY_BOX_EDIT, mEmployee);
            startActivity(intent1);
        }
    }
}
