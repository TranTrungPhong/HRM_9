package com.framgia.humanresourcemanagement.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.framgia.humanresourcemanagement.R;
import com.framgia.humanresourcemanagement.data.DBManager;
import com.framgia.humanresourcemanagement.data.DatabaseHelper;


/**
 * Created by PhongTran on 3/21/2016.
 */
public class AddEmployeeActivity extends Activity implements View.OnClickListener {
    private Button mButtonOK;
    private Button mButtonHuy;
    private EditText mEditName;
    private EditText mEditAddress;
    private EditText mEditbirthday;
    private EditText mEditPhone;
    private EditText mEditStatus;
    private EditText mEditPosition;
    private DBManager mDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_employee);
        initView();
    }

    private void initView() {
        mDBManager = new DBManager(getApplicationContext());
        mEditName = (EditText) findViewById(R.id.edit_name_add_staff);
        mEditAddress = (EditText) findViewById(R.id.edit_address_add_staff);
        mEditbirthday = (EditText) findViewById(R.id.edit_birth_add_staff);
        mEditPhone = (EditText) findViewById(R.id.edit_phone_add_staff);
        mEditStatus = (EditText) findViewById(R.id.edit_status_add_staff);
        mEditPosition = (EditText) findViewById(R.id.edit_position_add_staff);
        mButtonOK = (Button) findViewById(R.id.button_add_staff);
        mButtonHuy = (Button) findViewById(R.id.button_cancle_add_staff);
        mButtonOK.setOnClickListener(this);
        mButtonHuy.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add_staff:
                boolean addEmployee = mDBManager.insertValue(DatabaseHelper.TABLE_NAME_STAFF,
                        new String[]{
                                DatabaseHelper.COLUMN_EMPLOYEE_NAME,
                                DatabaseHelper.COLUMN_EMPLOYEE_ADDRESS,
                                DatabaseHelper.COLUMN_EMPLOYEE_BIRTHDAY,
                                DatabaseHelper.COLUMN_EMPLOYEE_PHONE,
                                DatabaseHelper.COLUMN_EMPLOYEE_STATUS,
                                DatabaseHelper.COLUMN_EMPLOYE_POSITION},
                        new String[]{
                                mEditName.getText().toString(),
                                mEditAddress.getText().toString(),
                                mEditbirthday.getText().toString(),
                                mEditPhone.getText().toString(),
                                mEditStatus.getText().toString(),
                                mEditPosition.getText().toString()
                        }
                );
                if (addEmployee) {
                    Toast.makeText(getApplicationContext(), R.string.add_done, Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.add_fail, Toast.LENGTH_SHORT)
                            .show();
                }
                finish();
                break;
            case R.id.button_cancle_add_staff:
                finish();
                break;
        }
    }
}
