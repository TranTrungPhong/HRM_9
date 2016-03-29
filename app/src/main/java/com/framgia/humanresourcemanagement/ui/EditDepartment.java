package com.framgia.humanresourcemanagement.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
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
    private EditText mEditTextName;
    private EditText mEditTextDescription;
    private Button mButtonUpdate;
    private Button mButtonCancel;
    private Department mDepartment;
    private DBManager mDB;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.edit_department);
        initView();
    }

    private void initView() {
        mEditTextName = (EditText)findViewById(R.id.edit_name_depart);
        mEditTextDescription = (EditText)findViewById(R.id.edit_description_depart);
        mButtonUpdate = (Button)findViewById(R.id.button_update_depart);
        mButtonCancel = (Button)findViewById(R.id.button_cancle_edit_depart);
        mButtonUpdate.setOnClickListener(this);
        mButtonCancel.setOnClickListener(this);

        Intent intent = getIntent();
        mDepartment = (Department)intent.getSerializableExtra(DepartmentActivity.KEY_EDIT_DEPART);
        mEditTextName.setText(mDepartment.getName().toString());
        mEditTextDescription.setText(mDepartment.getDescription().toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_update_depart:
                mDB = new DBManager(getApplicationContext());
                boolean result = mDB.updateValue(DatabaseHelper.TABLE_NAME_DEPARTMENT,
                        new String[]{
                                DatabaseHelper.COLUMN_DEPARTMENT_DESCRIPTION
                        },
                        new String[]{
                                mEditTextDescription.getText().toString()
                        },
                        "name='" + mDepartment.getName().toString() + "'"
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
            case R.id.button_cancle_edit_depart:
                finish();
                break;
        }
    }
}
