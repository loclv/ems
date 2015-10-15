package framgia.employeemanagement.sqlite.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import framgia.employeemanagement.Employee;

/**
 * Created by FRAMGIA\luu.vinh.loc on 06/10/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "employeeManager";
    // Table names
    private static final String TABLE_EMPLOYEE = "employees";
    private static final String TABLE_PLACE_OF_BIRTH = "places_of_birth";
    private static final String TABLE_EMPLOYEE_PLACES_OF_BIRTH = "employee_places_of_birth";
    // Common column names
    private static final String KEY_ID = "id";
    // EMPLOYEE Table - column names
    private static final String KEY_NAME = "name";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_DATE_OF_BIRTH = "date_of_birth";
    private static final String KEY_PHONE_NUMBER = "phone_number";
    private static final String KEY_DEPARTMENT = "department";
    private static final String KEY_POSITION = "position";
    private static final String KEY_STATUS = "status";
    private static final String KEY_START_TIME = "start_time";
    private static final String KEY_LEAVEDATE = "leavedate";
    // Table Create Statements
    // EMPLOYEE Table Create Statements
    private static final String CREATE_TABLE_EMPLOYEE = "CREATE TABLE " + TABLE_EMPLOYEE
        + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + KEY_NAME + " TEXT,"
        + KEY_IMAGE + " TEXT,"
        + KEY_ADDRESS + " TEXT,"
        + KEY_DATE_OF_BIRTH + " DATETIME,"
        + KEY_PHONE_NUMBER + " TEXT,"
        + KEY_DEPARTMENT + " TEXT,"
        + KEY_POSITION + " TEXT,"
        + KEY_STATUS + " TEXT,"
        + KEY_START_TIME + " DATETIME,"
        + KEY_LEAVEDATE + " DATETIME)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // To check is Database empty?
    boolean isRowEmpty;

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_EMPLOYEE);
        //db.execSQL(CREATE_TABLE_PLACE_OF_BIRTH);
        //db.execSQL(CREATE_EMPLOYEE_PLACES_OF_BIRTH);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);
        // create new tables
        onCreate(db);
    }

    // ------------------------ employees table methods ------------------------//
    // Creating a employee
    public long addEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        addValuesEmployee(employee, values);
        // insert row
        long employee_id = db.insert(TABLE_EMPLOYEE, null, values);
        return employee_id;
    }

    // Get a employee
    public Employee getEmployee(long employee_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_EMPLOYEE
            + " WHERE " + KEY_ID + " = " + employee_id;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null)
            c.moveToFirst();
        return getAnEmployee(c);
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_EMPLOYEE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // Looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                // adding to employee list
                employeeList.add(getAnEmployee(c));
            } while (c.moveToNext());
        }
        return employeeList;
    }

    // Get list employees
    public List<Employee> getListEmployees(String name, String department) {
        List<Employee> employeeList = new ArrayList<>();
        String selectQuery;
        if (!department.equals("None")) {
            selectQuery = "SELECT * FROM " + TABLE_EMPLOYEE + " WHERE " + KEY_NAME + " LIKE "
                + "'%" + name + "%'" + " AND " + KEY_DEPARTMENT + " = " + "'" + department + "'";
        } else {
            selectQuery = "SELECT * FROM " + TABLE_EMPLOYEE + " WHERE " + KEY_NAME + " LIKE "
                + "'%" + name + "%'";
        }
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // Looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                // adding to employee list
                employeeList.add(getAnEmployee(c));
            } while (c.moveToNext());
        }
        return employeeList;
    }

    // Get list employees
    public List<Employee> getListEmployeesDepartment(String department) {
        List<Employee> employeeList = new ArrayList<>();
        String selectQuery;
        selectQuery = "SELECT * FROM " + TABLE_EMPLOYEE + " WHERE " + KEY_DEPARTMENT + " = " + "'"
            + department + "'";
        Log.v("", selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // Looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                // adding to employee list
                employeeList.add(getAnEmployee(c));
            } while (c.moveToNext());
        }
        return employeeList;
    }

    public Employee getAnEmployee(Cursor c) {
        Employee emp = new Employee();
        emp.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        emp.setName(c.getString(c.getColumnIndex(KEY_NAME)));
        emp.setImage(c.getString(c.getColumnIndex(KEY_IMAGE)));
        emp.setPlaceOfBirth(c.getString(c.getColumnIndex(KEY_ADDRESS)));
        emp.setBirthday(c.getString(c.getColumnIndex(KEY_DATE_OF_BIRTH)));
        emp.setPhone(c.getString(c.getColumnIndex(KEY_PHONE_NUMBER)));
        emp.setDepartment(c.getString(c.getColumnIndex(KEY_DEPARTMENT)));
        emp.setPosition(c.getString(c.getColumnIndex(KEY_POSITION)));
        emp.setStatus(c.getString(c.getColumnIndex(KEY_STATUS)));
        emp.setJoinDate(c.getString(c.getColumnIndex(KEY_START_TIME)));
        emp.setLeaveDate(c.getString(c.getColumnIndex(KEY_LEAVEDATE)));
        return emp;
    }

    // Updating a employee values only
    public int updateEmployee(Employee employee, long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        addValuesEmployee(employee, values);
        // updating row
        return db.update(TABLE_EMPLOYEE, values, KEY_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void addValuesEmployee(Employee employee, ContentValues values) {
        values.put(KEY_NAME, employee.getName());
        values.put(KEY_IMAGE, employee.getImage());
        values.put(KEY_ADDRESS, employee.getPlaceOfBirth());
        values.put(KEY_DATE_OF_BIRTH, employee.getBirthday());
        values.put(KEY_PHONE_NUMBER, employee.getPhone());
        values.put(KEY_DEPARTMENT, employee.getDepartment());
        values.put(KEY_POSITION, employee.getPosition());
        values.put(KEY_STATUS, employee.getStatus());
        values.put(KEY_START_TIME, employee.getJoinDate());
        values.put(KEY_LEAVEDATE, employee.getLeaveDate());
    }

    // Delete a employee
    public void deleteEmployee(long employee_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMPLOYEE, KEY_ID + " = ?", new String[]{String.valueOf(employee_id)});
    }

    // Check database is empty?
    public boolean isEmployeeTableEmpty() {
        String selectQuery = "SELECT * FROM " + TABLE_PLACE_OF_BIRTH;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // if database 's row is exit, always can move to first row
        if (c.moveToFirst()) {
            isRowEmpty = false;
        } else {
            isRowEmpty = true;
        }
        return isRowEmpty;
    }

    // Closing database
    // Call this method when you don’t need access to db anymore.
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}