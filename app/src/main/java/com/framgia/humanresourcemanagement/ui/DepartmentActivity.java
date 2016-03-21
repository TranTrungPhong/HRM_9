package com.framgia.humanresourcemanagement.ui;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

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
    public static final String KEY_BOX = "box";
    private ListView mListviewDepartment;
    private DepartmentAdapter mAdapter;
    private DBManager mDBManager;
    private List<Department> mListDepart;
    private Button mbuttonAdd;
    private Button mbuttonSearch;
    private EditText meditTextSeart;
    private DatabaseHelper mhelper = new DatabaseHelper(this);
    private DatabaseHelper.OpenHelper openHelper = new DatabaseHelper.OpenHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_department);
        initView();
    }

    private void initView() {
        mDBManager = new DBManager(getApplicationContext());
        mDBManager.getDepartment();
        mListDepart = mDBManager.getListDepartment();

        mbuttonAdd = (Button) findViewById(R.id.button_add_depart);
        mbuttonSearch = (Button) findViewById(R.id.button_search_depart);
        meditTextSeart = (EditText) findViewById(R.id.edit_search_depart);
        mListviewDepartment = (ListView) findViewById(R.id.listview_department);
        mAdapter = new DepartmentAdapter(this, mListDepart);
        mListviewDepartment.setAdapter(mAdapter);
        mbuttonAdd.setOnClickListener(this);
        mbuttonSearch.setOnClickListener(this);
        mListviewDepartment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentSend = new Intent();
                intentSend.setClass(DepartmentActivity.this, EmployeeActivity.class);
                Bundle bundleSend = new Bundle();
                bundleSend.putString(KEY_NAME_DEPARTMENT, mListDepart.get(position).getName());
                intentSend.putExtra(KEY_BOX, bundleSend);
                startActivity(intentSend);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDBManager.getDepartment();
        mListDepart = mDBManager.getListDepartment();
        mAdapter.notifyDataSetInvalidated();
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
                for (int i = 0; i < mListDepart.size(); i++) {
                    String sName = mListDepart.get(i).getName();
                    if (sName.equalsIgnoreCase(meditTextSeart.getText().toString())) {
                        Intent intentSend = new Intent(DepartmentActivity.this, EmployeeActivity.class);
                        Bundle bundleSend = new Bundle();
                        bundleSend.putString(KEY_NAME_DEPARTMENT, sName);
                        intentSend.putExtra(KEY_BOX, bundleSend);
                        startActivity(intentSend);
                        flag = false;
                    }
                }
                if (flag) {
                    Toast.makeText(getApplicationContext(), R.string.search_null, Toast.LENGTH_SHORT)
                            .show();
                }
                break;
        }
    }
}

