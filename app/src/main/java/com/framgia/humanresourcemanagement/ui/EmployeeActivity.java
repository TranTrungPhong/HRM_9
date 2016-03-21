package com.framgia.humanresourcemanagement.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.framgia.humanresourcemanagement.R;
import com.framgia.humanresourcemanagement.data.DBManager;
import com.framgia.humanresourcemanagement.util.Employee;

import java.io.Serializable;
import java.util.ArrayList;
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
    private EditText mEditSearch;
    private Button mButtonAddEmployee;
    private Button mButtonSearchEmployee;

    private EmployeeAdapter mAdapter;
    private ProgressBar mProgressBar;
    private Handler mHandler;
    private boolean mCallback;

    private Employee mEmployee;
    private Employee mEmployee2;
    private Intent mIntent;
    private List<Employee> mListEmployee;

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
        Bundle bundlerecei = intentrecei.getBundleExtra(DepartmentActivity.KEY_BOX);
        mDepartmentName = bundlerecei.getString(DepartmentActivity.KEY_NAME_DEPARTMENT);
    }

    private void initView() {
        mDBManager = new DBManager(getApplicationContext());
        mDBManager.getEmployee(mDepartmentName);
        mListEmployee = mDBManager.getListEmployee();

        View footer = getLayoutInflater().inflate(R.layout.progress_bar_footer, null);
        mProgressBar = (ProgressBar) footer.findViewById(R.id.progressBar);
        mEditSearch = (EditText) findViewById(R.id.edit_search_employee);
        mButtonAddEmployee = (Button) findViewById(R.id.button_add_employee);
        mButtonSearchEmployee = (Button) findViewById(R.id.button_search_employee);
        mAdapter = new EmployeeAdapter(this, mListEmployee, SIZE_FIRST_ITEM, SIZE_LOAD_MORE_ITEM);


        mProgressBar.setVisibility((SIZE_FIRST_ITEM < mListEmployee.size()) ? View.VISIBLE : View.GONE);
        mListviewEmployee = (ListView) findViewById(R.id.lv_employee);
        mListviewEmployee.setAdapter(mAdapter);

        mButtonAddEmployee.setOnClickListener(this);
        mButtonSearchEmployee.setOnClickListener(this);
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
                    mHandler.postDelayed(showMore, 300);
                    mCallback = true;
                }
            }
        });
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
                boolean flag = true;
                for (int i = 0; i < mListEmployee.size(); i++) {
                    String sName = mListEmployee.get(i).getName();
                    if (sName.equalsIgnoreCase(mEditSearch.getText().toString())) {
                        mIntent = new Intent(EmployeeActivity.this, InfoActivity.class);
                        mIntent.putExtra(KEY_BOX_EM, mListEmployee.get(i));
                        startActivity(mIntent);
                        mEditSearch.setText("");
                        flag = false;
                    }
                }
                if (flag) {
                    Toast.makeText(getApplicationContext(), R.string.search_depart_null, Toast.LENGTH_SHORT)
                            .show();
                }
                break;
        }
    }
}
