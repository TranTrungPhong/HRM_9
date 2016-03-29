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
 * Created by PhongTran on 3/28/2016.
 */
public class AddDepartment extends Activity implements View.OnClickListener {
    private Button mButtonAdd;
    private Button mButtonHuy;
    private EditText mEditName;
    private EditText mEditDes;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_department);
        initView();
    }

    private void initView() {
        dbManager = new DBManager(getApplicationContext());
        mButtonAdd = (Button) findViewById(R.id.button_add_depart2);
        mButtonHuy = (Button) findViewById(R.id.button_cancle_add_depart);
        mEditName = (EditText) findViewById(R.id.name_add_depart);
        mEditDes = (EditText) findViewById(R.id.description_depart);
        mButtonAdd.setOnClickListener(this);
        mButtonHuy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add_depart2:
                boolean addDepartment = dbManager.insertValue(DatabaseHelper.TABLE_NAME_DEPARTMENT,
                        new String[]{
                                DatabaseHelper.COLUMN_DEPARTMENT_NAME,
                                DatabaseHelper.COLUMN_DEPARTMENT_DESCRIPTION
                        },
                        new String[]{
                                mEditName.getText().toString(),
                                mEditDes.getText().toString(),
                        }
                );
                if (addDepartment) {
                    Toast.makeText(getApplicationContext(), R.string.add_done, Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.add_fail, Toast.LENGTH_SHORT)
                            .show();
                }
                finish();
                break;
            case R.id.button_cancle_add_depart:
                finish();
                break;
        }
    }
}
