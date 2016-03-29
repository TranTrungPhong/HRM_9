package com.framgia.humanresourcemanagement.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.framgia.humanresourcemanagement.R;
import com.framgia.humanresourcemanagement.data.DBManager;
import com.framgia.humanresourcemanagement.data.DatabaseHelper;
import com.framgia.humanresourcemanagement.util.Employee;

import java.util.List;


/**
 * Created by PhongTran on 3/20/2016.
 */
public class EmployeeActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String KEY_BOX_EM = "box_em";
    public static final String KEY_BOX_EDIT_EM = "box_edit_em";
    public final int SIZE_FIRST_ITEM = 10;
    public final int SIZE_LOAD_MORE_ITEM = 10;

    private DBManager mDBManager;
    private String mDepartmentName;
    private ListView mListviewEmployee;
    private Button mButtonAddEmployee;
    private Button mButtonSearchEmployee;
    private TextView mTextNameList;

    private EmployeeAdapter mAdapter;
    private ProgressBar mProgressBar;
    private Handler mHandler;
    private boolean mCallback;

    private Employee mEmployee;
    private Employee mEmployee2;
    private Intent mIntent;
    private List<Employee> mListEmployee;
    private AutoCompleteTextView mAutoItem;
    private String mNameChoose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_employee_layout);
        receiveNameDepartment();
        initView();
        mHandler = new Handler();
    }

    private void receiveNameDepartment() {
        Intent intentrecei = getIntent();
        mDepartmentName = intentrecei.getStringExtra(DepartmentActivity.KEY_NAME_DEPARTMENT);
        if (intentrecei == null) {
            mDepartmentName = intentrecei.getStringExtra(DepartmentAdapter.KEY_NAME_DEPARTMENT);
        }
    }

    private void initView() {
        mDBManager = new DBManager(getApplicationContext());
        mTextNameList = (TextView) findViewById(R.id.text_name_list);
        mTextNameList.setText(mDepartmentName + "");
        View footer = getLayoutInflater().inflate(R.layout.progress_bar_footer, null);
        mProgressBar = (ProgressBar) footer.findViewById(R.id.progressBar);
        mButtonAddEmployee = (Button) findViewById(R.id.button_add_employee);
        mButtonSearchEmployee = (Button) findViewById(R.id.button_search_employee);
        initData();
        mDBManager.getEmployee(mDepartmentName);
        mListEmployee = mDBManager.getListEmployee();
        mAdapter = new EmployeeAdapter(this, mListEmployee, SIZE_FIRST_ITEM, SIZE_LOAD_MORE_ITEM);
        mAdapter.notifyDataSetChanged();
        mProgressBar.setVisibility((SIZE_FIRST_ITEM < mListEmployee.size()) ? View.VISIBLE : View.GONE);
        mListviewEmployee = (ListView) findViewById(R.id.lv_employee);
        mListviewEmployee.setAdapter(mAdapter);

        mButtonAddEmployee.setOnClickListener(this);
        mButtonSearchEmployee.setOnClickListener(this);

        searchView();

        mListviewEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mEmployee = mListEmployee.get(position);
                mIntent = new Intent(EmployeeActivity.this, InfoActivity.class);
                mIntent.putExtra(KEY_BOX_EM, mEmployee);
                startActivity(mIntent);
            }
        });

        mListviewEmployee.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mEmployee2 = mListEmployee.get(position);
                Intent intent = new Intent(EmployeeActivity.this, EditEmployee.class);
                intent.putExtra(KEY_BOX_EDIT_EM, mEmployee2);
                startActivity(intent);
                return true;
            }
        });
        mListviewEmployee.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstItem, int itemCount, int totalItemCount) {
                if (firstItem + itemCount == totalItemCount && !mAdapter.endReached() && !mCallback) {
                    if(mHandler != null){
                        mHandler.postDelayed(showMore, 300);
                    }
                        mCallback = true;
                }
            }
        });
    }

    private void searchView() {
        mAutoItem = (AutoCompleteTextView) findViewById(R.id.autocompletetextview);
        mAutoItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mNameChoose = mAutoItem.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mDBManager.getEmployee(mDepartmentName);
        int indexSize = mDBManager.getListEmployee().size();
        String[] products = new String[indexSize];
        for (int i = 0; i < indexSize; i++) {
            products[i] = mListEmployee.get(i).getName().toString();
        }
        mAutoItem.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, products));
    }

    private void initData() {
        mDBManager.getEmployee(mDepartmentName);
        mListEmployee = mDBManager.getListEmployee();
        for (int i = 0; i < 50; i++) {
            String name = "name" + i;
            String status = "Intern";
            if (i % 2 == 0) {
                status = "New Dev";
            }
            if (i % 3 == 0) {
                status = "Leader";
            }
            if (i % 5 == 0) {
                status = "LeftJob";
            }
            if (i % 10 == 0) {
                status = "Group Leader";
            }
            boolean flag = false;
            for (int k = 0; k < mListEmployee.size(); k++) {
                if (name.equalsIgnoreCase(mListEmployee.get(k).getName())) {
                    flag = true;
                }
            }
            if (flag) continue;
            Employee employee = new Employee(i, name, "1", "1", "1", status, mDepartmentName);
            mDBManager.insertValue(DatabaseHelper.TABLE_NAME_STAFF,
                    new String[]{
                            DatabaseHelper.COLUMN_EMPLOYEE_NAME,
                            DatabaseHelper.COLUMN_EMPLOYEE_ADDRESS,
                            DatabaseHelper.COLUMN_EMPLOYEE_BIRTHDAY,
                            DatabaseHelper.COLUMN_EMPLOYEE_PHONE,
                            DatabaseHelper.COLUMN_EMPLOYEE_STATUS,
                            DatabaseHelper.COLUMN_EMPLOYE_POSITION},
                    new String[]{
                            employee.getName(),
                            employee.getAddress(),
                            employee.getBirthday(),
                            employee.getPhone(),
                            employee.getStatus(),
                            employee.getPosition()
                    }
            );
        }
    }

    private Runnable showMore = new Runnable() {
        @Override
        public void run() {
            boolean noMoreToShow = mAdapter.showMore();
            mProgressBar.setVisibility(noMoreToShow ? View.GONE : View.VISIBLE);
            mCallback = false;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        searchView();
        mDBManager.getEmployee(mDepartmentName);
        mListEmployee = mDBManager.getListEmployee();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add_employee:
                Intent intent = new Intent(EmployeeActivity.this, AddEmployeeActivity.class);
                startActivity(intent);
                break;
            case R.id.button_search_employee:
                for (int i = 0; i < mListEmployee.size(); i++) {
                    String sName = mListEmployee.get(i).getName();
                    if (sName.equalsIgnoreCase(mNameChoose)) {
                        mIntent = new Intent(EmployeeActivity.this, InfoActivity.class);
                        mIntent.putExtra(KEY_BOX_EM, mListEmployee.get(i));
                        startActivity(mIntent);
                    }
                }
                break;
        }
    }
}
