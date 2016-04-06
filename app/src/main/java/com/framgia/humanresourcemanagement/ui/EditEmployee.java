package com.framgia.humanresourcemanagement.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.framgia.humanresourcemanagement.util.Employee;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Created by PhongTran on 3/21/2016.
 */
public class EditEmployee extends Activity implements View.OnClickListener,
        DatePickerDialog.OnDateSetListener {
    private EditText mEditName;
    private EditText mEditAddress;
    private TextView mTextBirthday;
    private EditText mEditPhone;
    private Spinner mSpinnerStatus;
    private Spinner mSpinnerPosition;
    private Button mButtonUpdate;
    private Button mButtonCancel;
    private DBManager mDBManager;
    private Employee mEmployee;
    private Calendar mCalendar;
    private Date mDate;
    private DatePickerDialog mDateDialog;
    private String mStatusChoose = "";
    private String mPositionChoose = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_employee_layout);
        mDateDialog = new DatePickerDialog(
                EditEmployee.this,
                EditEmployee.this, 1, 1, 2000);
        initView();
        initViewSpinerStatus();
        initViewSpinePosition();
    }

    private void initView() {
        mEditName = (EditText) findViewById(R.id.text_name_edit_staff);
        mEditAddress = (EditText) findViewById(R.id.edit_address_edit_staff);
        mTextBirthday = (TextView) findViewById(R.id.text_birth_edit_staff);
        getDefaultInfor();
        mEditPhone = (EditText) findViewById(R.id.edit_phone_edit_staff);
        mSpinnerStatus = (Spinner) findViewById(R.id.spiner_status_edit_staff);
        mSpinnerPosition = (Spinner) findViewById(R.id.spiner_position_edit_staff);
        mButtonUpdate = (Button) findViewById(R.id.button_update_edit_staff);
        mButtonCancel = (Button) findViewById(R.id.button_cancle_edit_staff);

        Intent intent = getIntent();
        mEmployee = (Employee) intent.getSerializableExtra(InfoActivity.KEY_BOX_EDIT);
        if (mEmployee == null) {
            mEmployee = (Employee) intent.getSerializableExtra(EmployeeActivity.KEY_BOX_EDIT_EM);
        }
        mEditName.setText(mEmployee.getName());
        mEditAddress.setText(mEmployee.getAddress());
        mTextBirthday.setText(mEmployee.getBirthday());
        mEditPhone.setText(mEmployee.getPhone());
        mButtonCancel.setOnClickListener(this);
        mButtonUpdate.setOnClickListener(this);
        mTextBirthday.setOnClickListener(this);
    }

    private void initViewSpinerStatus() {
        String oldStatus = mEmployee.getStatus().toString();
        final String status[] = {
                oldStatus, "Leader", "Group Leader", "Developer", "New Dev", "Interns", "LeftJob"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                status);
        adapter.setDropDownViewResource
                (android.R.layout.simple_list_item_single_choice);
        mSpinnerStatus.setAdapter(adapter);
        mSpinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mStatusChoose = status[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initViewSpinePosition() {
        String oldPosition = mEmployee.getPosition().toString();
        mDBManager = new DBManager(getApplicationContext());
        mDBManager.getDepartment();
        List<Department> listDepartment = mDBManager.getListDepartment();
        final String[] posit = new String[listDepartment.size() + 1];
        posit[0] = oldPosition;
        for (int i = 1; i < listDepartment.size() + 1; i++) {
            posit[i] = listDepartment.get(i - 1).getName();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_update_edit_staff:
                mDBManager = new DBManager(getApplicationContext());
                boolean result = mDBManager.updateValue(DatabaseHelper.TABLE_NAME_STAFF,
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
                        },
                        "id='" + mEmployee.getmID() + "'"
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
            case R.id.text_birth_edit_staff:
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
                EditEmployee.this,
                EditEmployee.this, nam, thang, ngay);
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
