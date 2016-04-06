package com.framgia.humanresourcemanagement.ui;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.framgia.humanresourcemanagement.R;
import com.framgia.humanresourcemanagement.data.DBManager;
import com.framgia.humanresourcemanagement.data.DatabaseHelper;
import com.framgia.humanresourcemanagement.util.Department;

import java.util.List;


public class DepartmentActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String KEY_NAME_DEPARTMENT = "name_department";
    private ListView mListViewDepartment;
    private DepartmentAdapter mDepartmentAdapter;
    private DBManager mDBManager;
    private List<Department> mListDepartment;
    private Button mButtonAdd;
    private Button mButtonSearch;
    private AutoCompleteTextView mAutoSearchDepart;
    private String mNameDepartChoose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_department);
        initView();
    }

    private void initView() {
        mDBManager = new DBManager(getApplicationContext());
        mDBManager.getDepartment();
        mListDepartment = mDBManager.getListDepartment();
        mButtonAdd = (Button) findViewById(R.id.button_add_depart);
        mButtonSearch = (Button) findViewById(R.id.button_search_depart);
        mAutoSearchDepart = (AutoCompleteTextView) findViewById(R.id.autocomplete_depart);
        mListViewDepartment = (ListView) findViewById(R.id.listview_department);
        mDepartmentAdapter = new DepartmentAdapter(this, mListDepartment);
        mListViewDepartment.setAdapter(mDepartmentAdapter);
        mButtonAdd.setOnClickListener(this);
        mButtonSearch.setOnClickListener(this);
        mAutoSearchDepart.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mNameDepartChoose = mAutoSearchDepart.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mDBManager.getDepartment();
        int indexSize = mDBManager.getListDepartment().size();
        String[] products = new String[indexSize];
        for (int i = 0; i < indexSize; i++) {
            products[i] = mListDepartment.get(i).getName().toString();
        }
        mAutoSearchDepart.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, products));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDBManager.getDepartment();
        mListDepartment = mDBManager.getListDepartment();
        mDepartmentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add_depart:
                Intent intent = new Intent(DepartmentActivity.this, AddDepartment.class);
                startActivity(intent);
                break;
            case R.id.button_search_depart:
                boolean flag = true;
                for (int i = 0; i < mListDepartment.size(); i++) {
                    String sName = mListDepartment.get(i).getName();
                    if (sName.equalsIgnoreCase(mNameDepartChoose)) {
                        Intent intentSend = new Intent(DepartmentActivity.this, EmployeeActivity.class);
                        intentSend.putExtra(KEY_NAME_DEPARTMENT, sName);
                        startActivity(intentSend);
                        flag = false;
                    }
                }
                if (flag) {
                    Toast.makeText(getApplicationContext(), R.string.search_depart_null,
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}

