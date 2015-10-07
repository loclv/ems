package framgia.employeemanagement.sqlite.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import framgia.employeemanagement.sqlite.model.Employee;
import framgia.employeemanagement.sqlite.model.PlaceOfBirth;

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
    private static final String KEY_DATE_OF_BIRTH = "date_of_birth";
    private static final String KEY_PHONE_NUMBER = "phone_number";
    private static final String KEY_DEPARTMENT = "department";
    private static final String KEY_POSITION = "position";
    private static final String KEY_STATUS = "status";
    private static final String KEY_START_TIME = "start_time";

    // PLACE_OF_BIRTH Table - column names
    private static final String KEY_PLACE_NAME = "place_name";

    // TABLE_EMPLOYEE_PLACES_OF_BIRTH Tanle -column names
    private static final String KEY_EMPLOYEE_ID = "employee_id";
    private static final String KEY_PLACE_OF_BIRTH_ID = "place_of_birth_id";

    // Table Create Statements
    // EMPLOYEE Table Create Statements
    private static final String CREATE_TABLE_EMPLOYEE = "CREATE TABLE " + TABLE_EMPLOYEE
            + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_NAME + " TEXT,"
            + KEY_DATE_OF_BIRTH + " DATETIME,"
            + KEY_PHONE_NUMBER + " TEXT,"
            + KEY_DEPARTMENT + " TEXT,"
            + KEY_POSITION + " TEXT,"
            + KEY_STATUS + " TEXT,"
            + KEY_START_TIME + " DATETIME)";

    // PLACE_OF_BIRTH Table Create Statements
    private static final String CREATE_TABLE_PLACE_OF_BIRTH = "CREATE TABLE " + TABLE_PLACE_OF_BIRTH
            + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_PLACE_NAME + " TEXT)";

    // EMPLOYEE_PLACES_OF_BIRTH Table Create Statements
    private static final String CREATE_EMPLOYEE_PLACES_OF_BIRTH = "CREATE TABLE " + TABLE_EMPLOYEE_PLACES_OF_BIRTH
            + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_EMPLOYEE_ID + " INTEGER,"
            + KEY_PLACE_OF_BIRTH_ID + " INTEGER)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_EMPLOYEE);
        db.execSQL(CREATE_TABLE_PLACE_OF_BIRTH);
        db.execSQL(CREATE_EMPLOYEE_PLACES_OF_BIRTH);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACE_OF_BIRTH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE_PLACES_OF_BIRTH);

        // create new tables
        onCreate(db);
    }

    // ------------------------ employees table methods ------------------------//
    // Creating a employee
    public long createEployee(Employee employee, long place_of_birth_id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, employee.getName());
        values.put(KEY_DATE_OF_BIRTH, employee.getDate_of_birth());
        values.put(KEY_PHONE_NUMBER, employee.getPhone_number());
        values.put(KEY_DEPARTMENT, employee.getDepartment());
        values.put(KEY_POSITION, employee.getPosition());
        values.put(KEY_STATUS, employee.getStatus());
        values.put(KEY_START_TIME, employee.getStart_time());

        // insert row
        long employee_id = db.insert(TABLE_EMPLOYEE, null, values);

        //assigning employee to place_of_birth
        createEmployeePlaceOfBirth(employee_id, place_of_birth_id);

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
        Employee emp = new Employee();
        emp.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        emp.setName(c.getString(c.getColumnIndex(KEY_NAME)));
        emp.setDate_of_birth(c.getString(c.getColumnIndex(KEY_DATE_OF_BIRTH)));
        emp.setPhone_number(c.getString(c.getColumnIndex(KEY_PHONE_NUMBER)));
        emp.setDepartment(c.getString(c.getColumnIndex(KEY_DEPARTMENT)));
        emp.setPosition(c.getString(c.getColumnIndex(KEY_POSITION)));
        emp.setStatus(c.getString(c.getColumnIndex(KEY_STATUS)));
        emp.setStart_time(c.getString(c.getColumnIndex(KEY_START_TIME)));

        return emp;
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = new ArrayList<Employee>();
        String selectQuery = "SELECT * FROM " + TABLE_EMPLOYEE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to list
        if(c.moveToFirst()) {
            do {
                Employee emp = new Employee();
                emp.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                emp.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                emp.setDate_of_birth(c.getString(c.getColumnIndex(KEY_DATE_OF_BIRTH)));
                emp.setPhone_number(c.getString(c.getColumnIndex(KEY_PHONE_NUMBER)));
                emp.setDepartment(c.getString(c.getColumnIndex(KEY_DEPARTMENT)));
                emp.setPosition(c.getString(c.getColumnIndex(KEY_POSITION)));
                emp.setStatus(c.getString(c.getColumnIndex(KEY_STATUS)));
                emp.setStart_time(c.getString(c.getColumnIndex(KEY_START_TIME)));

                // adding to employee list
                employeeList.add(emp);
            } while (c.moveToNext());
        }

        return employeeList;
    }

    // Updating a employee values only
    public int updateEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, employee.getName());
        values.put(KEY_DATE_OF_BIRTH, employee.getDate_of_birth());
        values.put(KEY_PHONE_NUMBER, employee.getPhone_number());
        values.put(KEY_DEPARTMENT, employee.getDepartment());
        values.put(KEY_POSITION, employee.getPosition());
        values.put(KEY_STATUS, employee.getStatus());
        values.put(KEY_START_TIME, employee.getStart_time());

        // updating row
        return db.update(TABLE_EMPLOYEE, values, KEY_ID + " = ?", new String[] { String.valueOf(employee.getId()) });
    }

    // Delete a employee
    public void deleteEmployee(long employee_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMPLOYEE, KEY_ID + " = ?", new String[] { String.valueOf(employee_id)});
    }

    // ------------------------ places of birth table methods ------------------------//
    // Creating place of birth
    public long createPlaceOfBirth(PlaceOfBirth placeOfBirth){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PLACE_NAME, placeOfBirth.getPlace_name());

        // insert row
        long place_of_birth_id = db.insert(TABLE_PLACE_OF_BIRTH, null, values);

        return place_of_birth_id;
    }

    // getting all places of birth
    public List<PlaceOfBirth> getPlacesOfBirth() {
        List<PlaceOfBirth> placeOfBirthList = new ArrayList<PlaceOfBirth>();
        String selectQuery = "SELECT * FROM " + TABLE_PLACE_OF_BIRTH;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to list
        if(c.moveToFirst()) {
            do {
                PlaceOfBirth plb = new PlaceOfBirth();
                plb.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                plb.setPlace_name(c.getString(c.getColumnIndex(KEY_NAME)));

                // adding to place of birth list
                placeOfBirthList.add(plb);
            } while (c.moveToNext());
        }

        return placeOfBirthList;
    }

    // updating a place of birth
    public int updatePlaceOfBirth(PlaceOfBirth placeOfBirth) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PLACE_NAME, placeOfBirth.getPlace_name());

        // updating row
        return db.update(TABLE_PLACE_OF_BIRTH, values, KEY_ID + " = ?",
                new String[] { String.valueOf(placeOfBirth.getId()) });
    }

    // ------------------------ employee_place_of_birth table methods ------------------------//
    // Create employee_placeOfBirth
    public long createEmployeePlaceOfBirth(long employee_id, long place_of_birth_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EMPLOYEE_ID, employee_id);
        values.put(KEY_PLACE_OF_BIRTH_ID, place_of_birth_id);

        long id = db.insert(TABLE_EMPLOYEE_PLACES_OF_BIRTH, null, values);

        return id;
    }

    // Updating a employee_place_of_birth
    public int updateEmployeePlaceOfBirth(long id, long placeOfBirth_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PLACE_OF_BIRTH_ID, placeOfBirth_id);

        return  db.update(TABLE_EMPLOYEE_PLACES_OF_BIRTH, values, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    // Deleting a employee_place_of_birth
    public void deleteEmployeePlaceOfBirth(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMPLOYEE_PLACES_OF_BIRTH, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    // Closing database
    // Call this method when you don’t need access to db anymore.
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
