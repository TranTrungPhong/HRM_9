package com.framgia.humanresourcemanagement.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.framgia.humanresourcemanagement.R;
import com.framgia.humanresourcemanagement.data.DBManager;
import com.framgia.humanresourcemanagement.data.DatabaseHelper;
import com.framgia.humanresourcemanagement.util.Employee;

import java.io.Serializable;


/**
 * Created by PhongTran on 3/21/2016.
 */
public class EditEmployee extends Activity implements View.OnClickListener {
    private EditText mEditName;
    private EditText mEditAddress;
    private EditText mEditBirthday;
    private EditText mEditPhone;
    private EditText mEditStatus;
    private EditText mEditPosition;
    private Button mButtonUpdate;
    private Button mButtonCancle;
    private DBManager mDBManager;
    private Employee mEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_employee_layout);
        initView();
    }

    private void initView() {
        mEditName = (EditText) findViewById(R.id.edit_name_edit_staff);
        mEditAddress = (EditText) findViewById(R.id.edit_address_edit_staff);
        mEditBirthday = (EditText) findViewById(R.id.edit_birth_edit_staff);
        mEditPhone = (EditText) findViewById(R.id.edit_phone_edit_staff);
        mEditStatus = (EditText) findViewById(R.id.edit_status_edit_staff);
        mEditPosition = (EditText) findViewById(R.id.edit_position_edit_staff);
        mButtonUpdate = (Button) findViewById(R.id.button_update_edit_staff);
        mButtonCancle = (Button) findViewById(R.id.button_cancle_edit_staff);

        Intent intent = getIntent();
        mEmployee = (Employee) intent.getSerializableExtra(InfoActivity.KEY_BOX_EDIT);
        if (mEmployee == null) {
            mEmployee = (Employee) intent.getSerializableExtra(EmployeeActivity.KEY_BOX_EDIT_EM);
        }
        mEditName.setText(mEmployee.getName());
        mEditAddress.setText(mEmployee.getAddress());
        mEditBirthday.setText(mEmployee.getBirthday());
        mEditPhone.setText(mEmployee.getPhone());
        mEditStatus.setText(mEmployee.getStatus());
        mEditPosition.setText(mEmployee.getPosition());
        mButtonCancle.setOnClickListener(this);
        mButtonUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_update_edit_staff:
                mDBManager = new DBManager(getApplicationContext());
                boolean result = mDBManager.updateValue(DatabaseHelper.TABLE_NAME_STAFF,
                        new String[]{
                                DatabaseHelper.COLUMN_EMPLOYEE_ADDRESS,
                                DatabaseHelper.COLUMN_EMPLOYEE_BIRTHDAY,
                                DatabaseHelper.COLUMN_EMPLOYEE_PHONE,
                                DatabaseHelper.COLUMN_EMPLOYEE_STATUS,
                                DatabaseHelper.COLUMN_EMPLOYE_POSITION},
                        new String[]{
                                mEditAddress.getText().toString(),
                                mEditBirthday.getText().toString(),
                                mEditPhone.getText().toString(),
                                mEditStatus.getText().toString(),
                                mEditPosition.getText().toString()
                        },
                        "name='" + mEditName.getText().toString() + "'"
                );
                if (result) {
                    Toast.makeText(getApplicationContext(), R.string.edit_done, Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.edit_fail, Toast.LENGTH_SHORT)
                            .show();
                }
                finish();
                break;
            case R.id.button_cancle_edit_staff:
                finish();
                break;
        }
    }
}
