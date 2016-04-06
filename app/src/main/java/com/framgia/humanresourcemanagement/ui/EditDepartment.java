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
import com.framgia.humanresourcemanagement.util.Department;

/**
 * Created by PhongTran on 3/29/2016.
 */
public class EditDepartment extends Activity implements View.OnClickListener {
    private EditText mEditName;
    private EditText mEditDes;
    private Button mButtonUpdate;
    private Button mButtonCancel;
    private DBManager mDBManager;
    private Department mDepartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_department);
        initView();
    }

    private void initView() {
        mEditName = (EditText) findViewById(R.id.text_name_edit_depart);
        mEditDes = (EditText) findViewById(R.id.edit_description_depart);
        mButtonUpdate = (Button) findViewById(R.id.button_edit_depart2);
        mButtonCancel = (Button) findViewById(R.id.button_cancle_edit_depart);

        Intent intent = getIntent();
        mDepartment = (Department) intent.getSerializableExtra(DepartmentAdapter.KEY_EDIT_DEPART);
        mEditName.setText(mDepartment.getName());
        mEditDes.setText(mDepartment.getDescription());

        mButtonCancel.setOnClickListener(this);
        mButtonUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_edit_depart2:
                mDBManager = new DBManager(getApplicationContext());
                boolean result = mDBManager.updateValue(DatabaseHelper.TABLE_NAME_DEPARTMENT,
                        new String[]{
                                DatabaseHelper.COLUMN_DEPARTMENT_NAME,
                                DatabaseHelper.COLUMN_DEPARTMENT_DESCRIPTION
                        },
                        new String[]{
                                mEditName.getText().toString(),
                                mEditDes.getText().toString()
                        },
                        "id ='" + mDepartment.getmID() + "'"
                );
                if (result) {
                    Toast.makeText(getApplicationContext(), R.string.edit_done, Toast.LENGTH_SHORT)
                            .show();
                }
                finish();
                break;
            case R.id.button_cancle_edit_depart:
                finish();
                break;
            default:
                break;
        }
    }
}
