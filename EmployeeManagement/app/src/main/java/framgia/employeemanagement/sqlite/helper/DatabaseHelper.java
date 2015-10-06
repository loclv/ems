package framgia.employeemanagement.sqlite.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_NAME + " TEXT,"
            + KEY_DATE_OF_BIRTH + " DATETIME,"
            + KEY_PHONE_NUMBER + " TEXT,"
            + KEY_DEPARTMENT + " TEXT,"
            + KEY_POSITION + " TEXT,"
            + KEY_STATUS + " TEXT,"
            + KEY_START_TIME + " DATETIME)";

    // PLACE_OF_BIRTH Table Create Statements
    private static final String CREATE_TABLE_PLACE_OF_BIRTH = "CREATE TABLE " + TABLE_PLACE_OF_BIRTH
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_PLACE_NAME + " TEXT)";

    // EMPLOYEE_PLACES_OF_BIRTH Table Create Statements
    private static final String CREATE_EMPLOYEE_PLACES_OF_BIRTH = "CREATE TABLE " + TABLE_EMPLOYEE_PLACES_OF_BIRTH
            + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_EMPLOYEE_ID + " INTEGER,"
            + KEY_PLACE_OF_BIRTH_ID + " INTEGER)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //
    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_EMPLOYEE);
        db.execSQL(CREATE_TABLE_PLACE_OF_BIRTH);
        db.execSQL(CREATE_EMPLOYEE_PLACES_OF_BIRTH);

        // create new tables
        onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
