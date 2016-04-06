package com.framgia.humanresourcemanagement.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.framgia.humanresourcemanagement.R;
import com.framgia.humanresourcemanagement.data.DBManager;
import com.framgia.humanresourcemanagement.data.DatabaseHelper;
import com.framgia.humanresourcemanagement.util.Department;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Created by PhongTran on 3/21/2016.
 */
public class AddEmployeeActivity extends Activity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private Button mButtonOK;
    private Button mButtonHuy;
    private EditText mEditName;
    private EditText mEditAddress;
    private TextView mTextBirthday;
    private EditText mEditPhone;
    private Spinner mSpinnerStatus;
    private Spinner mSpinnerPosition;
    private DBManager mDBManager;
    private Calendar mCalendar;
    private Date mDate;
    private DatePickerDialog mDateDialog;
    private String mStatusChoose = "";
    private String mPositionChoose = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_employee);
        mDateDialog = new DatePickerDialog(
                AddEmployeeActivity.this,
                AddEmployeeActivity.this, 1, 1, 2000);
        initView();
        initViewSpinerStatus();
        initViewSpinePosition();
    }

    private void initViewSpinePosition() {
        mDBManager.getDepartment();
        List<Department> listDepartment = mDBManager.getListDepartment();
        final String[] posit = new String[listDepartment.size()];
        for (int i = 0; i < listDepartment.size(); i++) {
            posit[i] = listDepartment.get(i).getName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                posit);
        adapter.setDropDownViewResource
                (android.R.layout.simple_list_item_single_choice);
        mSpinnerPosition.setAdapter(adapter);
        mSpinnerPosition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPositionChoose = posit[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initViewSpinerStatus() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                DatabaseHelper.SPINER_STATUS);
        adapter.setDropDownViewResource
                (android.R.layout.simple_list_item_single_choice);
        mSpinnerStatus.setAdapter(adapter);
        mSpinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mStatusChoose = DatabaseHelper.SPINER_STATUS[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initView() {
        mTextBirthday = (TextView) findViewById(R.id.text_birth_add_staff);
        getDefaultInfor();
        mDBManager = new DBManager(getApplicationContext());
        mEditName = (EditText) findViewById(R.id.edit_name_add_staff);
        mEditAddress = (EditText) findViewById(R.id.edit_address_add_staff);
        mEditPhone = (EditText) findViewById(R.id.edit_phone_add_staff);
        mSpinnerStatus = (Spinner) findViewById(R.id.spiner_status_add_staff);
        mSpinnerPosition = (Spinner) findViewById(R.id.spiner_position_add_staff);
        mButtonOK = (Button) findViewById(R.id.button_add_staff);
        mButtonHuy = (Button) findViewById(R.id.button_cancle_add_staff);
        mButtonOK.setOnClickListener(this);
        mButtonHuy.setOnClickListener(this);
        mTextBirthday.setOnClickListener(this);

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
                                mTextBirthday.getText().toString(),
                                mEditPhone.getText().toString(),
                                mStatusChoose,
                                mPositionChoose
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
            case R.id.text_birth_add_staff:
                mDateDialog.show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mTextBirthday.setText(
                (dayOfMonth) + "/" + (monthOfYear + 1) + "/" + year);
        mCalendar.set(year, monthOfYear, dayOfMonth);
        mDate = mCalendar.getTime();
        String s = mTextBirthday.getText() + "";
        String strArrtmp[] = s.split("/");
        int ngay = Integer.parseInt(strArrtmp[0]);
        int thang = Integer.parseInt(strArrtmp[1]) - 1;
        int nam = Integer.parseInt(strArrtmp[2]);
        mDateDialog = new DatePickerDialog(
                AddEmployeeActivity.this,
                AddEmployeeActivity.this, nam, thang, ngay);
    }

    public void getDefaultInfor() {
        mCalendar = Calendar.getInstance();
        SimpleDateFormat dft = null;
        dft = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String strDate = dft.format(mCalendar.getTime());
        mTextBirthday.setText(strDate);
        //dft = new SimpleDateFormat("hh:mm a", Locale.getDefault());
    }
}
