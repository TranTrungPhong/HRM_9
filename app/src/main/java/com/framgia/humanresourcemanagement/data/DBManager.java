package com.framgia.humanresourcemanagement.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.widget.Toast;


import com.framgia.humanresourcemanagement.R;
import com.framgia.humanresourcemanagement.util.Department;
import com.framgia.humanresourcemanagement.util.Employee;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PhongTran on 3/15/2016.
 */
public class DBManager extends DatabaseHelper {
    private static final String DB_PATH = Environment.getDataDirectory().getPath()
            + "/data/com.framgia.humanresourcemanagement/databases";
    private static final String DB_NAME = "test";
    private Context mContext;

    private SQLiteDatabase mSQLiteDatabase;
    private OpenHelper mOpenHelper;
    private List<Department> mListDepartment = new ArrayList<>();
    private List<Employee> mListEmployee = new ArrayList<>();


    public DBManager(Context mContext) {
        super(mContext);
        this.mContext = mContext;
        mOpenHelper = new OpenHelper(mContext);
        copyDB();
    }

    private void copyDB() {
        File newFol = new File(DB_PATH);
        if (!newFol.exists()) {
            newFol.mkdirs();
        }
        if (newFol.exists()) {
            File file = new File(DB_PATH + "/" + DB_NAME);
            if (file.exists()) {
                return;
            } else {
                try {
                    file.createNewFile();
                    InputStream inputStream = mContext.getAssets().open(DB_NAME);
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    int len;
                    byte buff[] = new byte[1024];
                    while ((len = inputStream.read(buff)) > 0) {
                        fileOutputStream.write(buff, 0, len);
                    }
                    inputStream.close();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void openDB() {
        mSQLiteDatabase = mOpenHelper.getWritableDatabase();
    }

    private void closeDB(){
        mOpenHelper.close();
    }
    public void getDepartment() {
        mListDepartment.clear();
        openDB();
        String[] cols = {
                DatabaseHelper.COLUMN_DEPARTMENT_ID,
                DatabaseHelper.COLUMN_DEPARTMENT_NAME,
                DatabaseHelper.COLUMN_DEPARTMENT_DESCRIPTION};
        Cursor cursor = mSQLiteDatabase.query(DatabaseHelper.TABLE_NAME_DEPARTMENT, cols,
                null, null, null, null, null);
        if (cursor == null) {
            return;
        }
        Department itemDepartment;
        int indexId = cursor.getColumnIndex(DatabaseHelper.COLUMN_DEPARTMENT_ID);
        int indexName = cursor.getColumnIndex(DatabaseHelper.COLUMN_DEPARTMENT_NAME);
        int indexDescription = cursor.getColumnIndex(DatabaseHelper.COLUMN_DEPARTMENT_DESCRIPTION);
        int id;
        String sName;
        String sDescription;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            id = cursor.getInt(indexId);
            sName = cursor.getString(indexName);
            sDescription = cursor.getString(indexDescription);
            itemDepartment = new Department(id, sName, sDescription);
            mListDepartment.add(itemDepartment);
            cursor.moveToNext();
        }
        closeDB();
    }

    public boolean insertValue(String table_name, String[] cols, String... valus) {
        openDB();
        ContentValues contentValues = new ContentValues();
        int indexColSize = cols.length;
        for (int i = 0; i < indexColSize; i++) {
            contentValues.put(cols[i], valus[i]);
        }
        long newRow = mSQLiteDatabase.insert(table_name, null, contentValues);
        closeDB();
        return newRow != -1;
    }

    public boolean updateValue(String table_name, String[] cols, String[] values, String where) {
        openDB();
        ContentValues contentValues = new ContentValues();
        int indexColSize = cols.length;
        for (int i = 0; i < indexColSize; i++) {
            contentValues.put(cols[i], values[i]);
        }
        long countRow = mSQLiteDatabase.update(table_name, contentValues, where, null);
        closeDB();
        return countRow != -1;
    }

//    public boolean deleteAllDB(String tabble_name) {
//        openDB();
//        long del = mSQLiteDatabase.delete(tabble_name, null, null);
//        closeDB();
//        return del != -1;
//    }

    public List<Department> getListDepartment() {
        return mListDepartment;
    }

    public List<Employee> getListEmployee() {
        return mListEmployee;
    }

    public void getEmployee(String sDepartment) {
        mListEmployee.clear();
        openDB();
        String[] cols = {
                DatabaseHelper.COLUMN_EMPLOYEE_ID,
                DatabaseHelper.COLUMN_EMPLOYEE_NAME,
                DatabaseHelper.COLUMN_EMPLOYEE_ADDRESS,
                DatabaseHelper.COLUMN_EMPLOYEE_BIRTHDAY,
                DatabaseHelper.COLUMN_EMPLOYEE_PHONE,
                DatabaseHelper.COLUMN_EMPLOYEE_STATUS,
                DatabaseHelper.COLUMN_EMPLOYE_POSITION
        };
        String selec = DatabaseHelper.COLUMN_EMPLOYE_POSITION + " = ?";
        String[] Args = {sDepartment};
        Cursor cursor2 = mSQLiteDatabase.query(DatabaseHelper.TABLE_NAME_STAFF,
                cols, selec, Args, null, null, null, null);
        if (cursor2 == null) {
            Toast.makeText(mContext.getApplicationContext(), R.string.cursor_em_null,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Employee itemEmployee;
        int indexID;
        int indexStatus;
        int indexName;
        int indexBirthday;
        int indexPosition;
        int indexAddress;
        int indexPhone;
        indexID = cursor2.getColumnIndex(DatabaseHelper.COLUMN_EMPLOYEE_ID);
        indexStatus = cursor2.getColumnIndex(DatabaseHelper.COLUMN_EMPLOYEE_STATUS);
        indexName = cursor2.getColumnIndex(DatabaseHelper.COLUMN_EMPLOYEE_NAME);
        indexBirthday = cursor2.getColumnIndex(DatabaseHelper.COLUMN_EMPLOYEE_BIRTHDAY);
        indexPosition = cursor2.getColumnIndex(DatabaseHelper.COLUMN_EMPLOYE_POSITION);
        indexAddress = cursor2.getColumnIndex(DatabaseHelper.COLUMN_EMPLOYEE_ADDRESS);
        indexPhone = cursor2.getColumnIndex(DatabaseHelper.COLUMN_EMPLOYEE_PHONE);
        int ID;
        String sName;
        String sBirth;
        String sPosition;
        String sAddress;
        String sPhone;
        String sStatus;
        cursor2.moveToFirst();
        while (!cursor2.isAfterLast()) {
            ID = cursor2.getInt(indexID);
            sStatus = cursor2.getString(indexStatus);
            sName = cursor2.getString(indexName);
            sBirth = cursor2.getString(indexBirthday);
            sPosition = cursor2.getString(indexPosition);
            sAddress = cursor2.getString(indexAddress);
            sPhone = cursor2.getString(indexPhone);
            itemEmployee = new Employee(ID, sName, sAddress, sBirth, sPhone, sStatus, sPosition);
            mListEmployee.add(itemEmployee);
            cursor2.moveToNext();
        }
        closeDB();
    }
}
