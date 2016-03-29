package com.framgia.humanresourcemanagement.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by PhongTran on 3/15/2016.
 */
public class DatabaseHelper {
    public static final String SQL_GET_DEPARTMENT = "SELECT * FROM department";
    public static final String SQL_GET_STAFF = "SELECT * FROM staff";
    public static final String TABLE_NAME_STAFF = "staff";
    public static final String TABLE_NAME_DEPARTMENT = "department";
    public static final String COLUMN_EMPLOYEE_NAME = "name";
    public static final String COLUMN_EMPLOYEE_ID = "id";
    public static final String COLUMN_DEPARTMENT_ID = "id";
    public static final String COLUMN_EMPLOYEE_ADDRESS = "address";
    public static final String COLUMN_EMPLOYEE_BIRTHDAY = "birthday";
    public static final String COLUMN_EMPLOYEE_PHONE = "phone";
    public static final String COLUMN_EMPLOYEE_STATUS = "status";
    public static final String COLUMN_EMPLOYE_POSITION = "position";
    public static final String COLUMN_DEPARTMENT_NAME = "name";
    public static final String COLUMN_DEPARTMENT_DESCRIPTION = "description";
    public static final String[] SPINER_STATUS = {
            "", "Leader", "Group Leader", "Developer", "New Dev", "Interns", "LeftJob"
    };

    public static final String DATABASE_NAME = "mydb";
    public static final int DATABASE_VERSION = 1;

    protected static Context sContext;
    protected OpenHelper mOpenHelper;
    private static SQLiteDatabase sSQLiteDatabase;

    public static final String CREATE_TABLE_DEPARTMENT = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME_DEPARTMENT + "("
            + COLUMN_DEPARTMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_DEPARTMENT_NAME + " TEXT NOT NULL,"
            + COLUMN_DEPARTMENT_DESCRIPTION + " TEXT DEFAULT '')";

    public static final String CREATE_TABLE_EMPLOYEE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME_STAFF + "("
            + COLUMN_EMPLOYEE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_EMPLOYEE_NAME + " TEXT NOT NULL,"
            + COLUMN_EMPLOYEE_ADDRESS + " TEXT DEFAULT '',"
            + COLUMN_EMPLOYEE_BIRTHDAY + " TEXT DEFAULT '',"
            + COLUMN_EMPLOYEE_PHONE + " TEXT DEFAULT '',"
            + COLUMN_EMPLOYEE_STATUS + " TEXT DEFAULT '',"
            + COLUMN_EMPLOYE_POSITION + " TEXT DEFAULT '')";

    public static final String INIT_DEPARTMENT_DATA = "INSERT INTO " + TABLE_NAME_DEPARTMENT
            + "(" + COLUMN_DEPARTMENT_NAME + ")"
            + " VALUES " +
            "('Training'), ('BO'), ('KiThuat'), ('NhanSu'), ('TaiChinh'), ('KeToan'), ('Design')";

    public DatabaseHelper(Context context) {
        this.sContext = context;
    }

    public DatabaseHelper open() throws SQLException {
        mOpenHelper = new OpenHelper(sContext);
        sSQLiteDatabase = mOpenHelper.getWritableDatabase();
        return this;
    }

    public static class OpenHelper extends SQLiteOpenHelper {
        public OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_DEPARTMENT);
            db.execSQL(CREATE_TABLE_EMPLOYEE);
            db.execSQL(INIT_DEPARTMENT_DATA);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_DEPARTMENT);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_STAFF);
            onCreate(db);
        }
    }
}
