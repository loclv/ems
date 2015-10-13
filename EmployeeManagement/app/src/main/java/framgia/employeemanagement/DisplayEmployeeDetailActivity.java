package framgia.employeemanagement;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.Calendar;

/**
 * Created by FRAMGIA\nguyen.huu.quyen on 06/10/2015.
 */
public class DisplayEmployeeDetailActivity extends Activity {
    private static final int VIEWMODE = 1;
    private static final int ADDMODE = 2;
    private static final int EDITMODE = 3;
    private static final int SELECT_PICTURE = 1;
    private static EditText txJoinDate = null;
    private static EditText txBirthDay = null;
    private static EditText txPhone = null;
    private static EditText txLeaveDate = null;
    private static EditText txname = null;
    private static Spinner spinnerPlaceOfBirth = null;
    private static Spinner spinnerPosition = null;
    private static Spinner spinnerDepartment = null;
    private static Spinner spinnerStatus = null;
    private static Button btupdate = null;
    private static Button btCancel = null;
    private static Button btEdit = null;
    private static Button btAdd = null;
    private static Button btCancelAdd = null;
    private static ImageView imgavartar = null;
    private static ArrayAdapter<CharSequence> adapterSpinerPlaceOfBirth;
    private static ArrayAdapter<CharSequence> adapterSpinerDepartment;
    private static ArrayAdapter<CharSequence> adapterSpinerPosition;
    private static ArrayAdapter<CharSequence> adapterSpinerStatus;
    private Uri selectedImageUri;
    private static Employee employee;
    private String imageName = "Photo1.jpeg";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_employee);
        //Set id to items
        setFindViewById();
        //receive intent to set mode
        Intent intent = getIntent();
        int displayMode = intent.getIntExtra(getString(R.string.display_mode), VIEWMODE);
        setModeDisplay(displayMode);
        //set listener for buttons
        setButtonListenner();
    }

    public void setFindViewById() {
        txname = (EditText) findViewById(R.id.editName);
        txJoinDate = (EditText) findViewById(R.id.editJoinDate);
        txLeaveDate = (EditText) findViewById(R.id.editLeaveDate);
        txBirthDay = (EditText) findViewById(R.id.editBirthday);
        //Place of birth
        spinnerPlaceOfBirth = (Spinner) findViewById(R.id.spinnerPlaceOfBirth);
        adapterSpinerPlaceOfBirth = ArrayAdapter.createFromResource(this,
                R.array.place_of_birth_array, android.R.layout.simple_spinner_item);
        adapterSpinerPlaceOfBirth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlaceOfBirth.setAdapter(adapterSpinerPlaceOfBirth);
        //Position
        spinnerPosition = (Spinner) findViewById(R.id.spinnerPosition);
        adapterSpinerPosition = ArrayAdapter.createFromResource(this,
                R.array.position_array, android.R.layout.simple_spinner_item);
        adapterSpinerPosition.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPosition.setAdapter(adapterSpinerPosition);
        //Department
        spinnerDepartment = (Spinner) findViewById(R.id.spinnerDeparment);
        adapterSpinerDepartment = ArrayAdapter.createFromResource(this,
                R.array.deparmentListview_array, android.R.layout.simple_spinner_item);
        adapterSpinerDepartment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartment.setAdapter(adapterSpinerDepartment);
        //Status
        spinnerStatus = (Spinner) findViewById(R.id.spinnerStatus);
        adapterSpinerStatus = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_item);
        adapterSpinerStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapterSpinerStatus);
        //Phone
        txPhone = (EditText) findViewById(R.id.editPhone);
        btupdate = (Button) findViewById(R.id.btUpdate);
        btCancel = (Button) findViewById(R.id.buttonCancelUpdate);
        btEdit = (Button) findViewById(R.id.buttonEdit);
        btAdd = (Button) findViewById(R.id.buttonAdd);
        btCancelAdd = (Button) findViewById(R.id.buttonCancelAdd);
        imgavartar = (ImageView) findViewById(R.id.imageAvatar);
        imgavartar.setImageResource(R.drawable.avar);
        employee = new Employee();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                selectedImageUri = data.getData();
                imgavartar.setImageURI(selectedImageUri);
            }
        }
    }

    private void deleteFile(String inputPath, String inputFile) {
        try {
            // delete the original file
            new File(inputPath + inputFile).delete();
        } catch (Exception e) {
            Log.e(getString(R.string.tag), e.getMessage());
        }
    }

    public static String getPathFromUri(Uri uri, Context context) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, filePathColumn,
                null, null, null);
        String filePath;
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            filePath = cursor.getString(columnIndex);
            cursor.close();
        } else {
            filePath = uri.getPath();
        }
        return filePath;
    }

    private void copyFile() {
        InputStream in = null;
        OutputStream out = null;
        String outputPath;
        try {
            //create output directory if it doesn't exist
            File root = Environment.getExternalStorageDirectory();
            File dir = new File(getString(R.string.format_image_path,root.getAbsolutePath(),getString(R.string.folder_photo)));
            if (!dir.exists()) {
                dir.mkdirs();
            }
            in = new FileInputStream(getPathFromUri(selectedImageUri, DisplayEmployeeDetailActivity.this));
            String imagePath = getString(R.string.format_image_path,dir.getPath(), imageName);
            out = new FileOutputStream(imagePath);
            employee.setImage(imagePath);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            // write the output file (You have now copied the file)
            out.flush();
            out.close();
            out = null;
        } catch (FileNotFoundException fnfe1) {
            Log.e(getString(R.string.tag), fnfe1.getMessage());
        } catch (Exception e) {
            Log.e(getString(R.string.tag), e.getMessage());
        }
    }

    /******
     * Function to set display mode
     *************/
    public void setModeDisplay(int mode) {
        switch (mode) {
            case VIEWMODE:
                getEmployee();
                displayEmployeeDetail();
                setButton(VIEWMODE);
                break;
            case ADDMODE:
                enableEditEmployee();
                setButton(ADDMODE);
                break;
            case EDITMODE:
                getEmployee();
                displayEmployeeDetail();
                enableEditEmployee();
                setButton(EDITMODE);
                break;
            default:
                break;//do nothing
        }
    }

    /******
     * Function to display
     *************/
    public void displayEmployeeDetail() {
        //Set name
        txname.setText(employee.getName());
        txname.setEnabled(false);
        //Set Place of birth
        spinnerPlaceOfBirth.setSelection(adapterSpinerPlaceOfBirth.getPosition(employee.getAddress()));
        spinnerPlaceOfBirth.setEnabled(false);
        //Set birthday
        txBirthDay.setText(employee.getBirthday());
        txBirthDay.setEnabled(false);
        //Set Phone
        txPhone.setText(employee.getPhone());
        txPhone.setEnabled(false);
        //SetPosition
        spinnerPosition.setSelection(adapterSpinerPosition.getPosition(employee.getPosition()));
        spinnerPosition.setEnabled(false);
        //SetDepartment
        spinnerDepartment.setSelection(adapterSpinerDepartment.getPosition(employee.getDepartment()));
        spinnerDepartment.setEnabled(false);
        //SetStatus
        spinnerStatus.setSelection(adapterSpinerStatus.getPosition(employee.getStatus()));
        spinnerStatus.setEnabled(false);
        //SetJoinDate
        txJoinDate.setText(employee.getJoinDate());
        txJoinDate.setEnabled(false);
        //SetLeaveDate
        txLeaveDate.setText(employee.getLeaveDate());
        txLeaveDate.setEnabled(false);
        //Set avatar
        File imgFile = new File(employee.getImage());
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imgavartar.setImageBitmap(myBitmap);
        }
        imgavartar.setEnabled(false);
    }

    //Get an Employee
    public void getEmployee() {
        //TODO get employee informaiton
        //create an annonymos employee
        employee.setName("Emplpyee");
        employee.setPosition("Manager");
        employee.setBirthday("04/07/1989");
        employee.setLeaveDate("");
        employee.setJoinDate("15/09/2015");
        employee.setAddress("Đà Nẵng");
        employee.setDepartment("Education");
        employee.setPhone("0905477041");
        employee.setImage("/storage/emulated/0/QuyenNH/photo1.jpeg");
        employee.setStatus("Parttime");
    }

    //Get an Employee
    public void setEmployee() {
        //create an annonymos employee
        employee.setName(txname.getText().toString());
        employee.setPosition(spinnerPosition.getSelectedItem().toString());
        employee.setBirthday(txBirthDay.getText().toString());
        employee.setLeaveDate(txLeaveDate.getText().toString());
        employee.setJoinDate(txJoinDate.getText().toString());
        employee.setAddress(spinnerPlaceOfBirth.getSelectedItem().toString());
        employee.setDepartment(spinnerDepartment.getSelectedItem().toString());
        employee.setPhone(txPhone.getText().toString());
        employee.setStatus(spinnerStatus.getSelectedItem().toString());
    }

    public void addEmployee() {
        //TODO add new employee

    }

    public void updateEmployee() {
        //TODO Update employee

    }

    /******
     * Edit and Add employee mode
     *************/
    public void enableEditEmployee() {
        //Set name
        txname.setEnabled(true);
        //Set Place of birth
        spinnerPlaceOfBirth.setEnabled(true);
        //Set birthday
        txBirthDay.setEnabled(true);
        //Set Phone
        txPhone.setEnabled(true);
        //SetPosition
        spinnerPosition.setEnabled(true);
        //SetDepartment
        spinnerDepartment.setEnabled(true);
        //SetStatus
        spinnerStatus.setEnabled(true);
        //SetJoinDate
        txJoinDate.setEnabled(true);
        //SetLeaveDate
        txLeaveDate.setEnabled(true);
        //Set avatar
        imgavartar.setEnabled(true);
    }

    //Set buttons invisible, according to the request, change the status
    public void setButton(int mode) {
        btupdate.setVisibility(View.INVISIBLE);
        btCancel.setVisibility(View.INVISIBLE);
        btEdit.setVisibility(View.INVISIBLE);
        btAdd.setVisibility(View.INVISIBLE);
        btCancelAdd.setVisibility(View.INVISIBLE);
        switch (mode) {
            case VIEWMODE:
                btEdit.setVisibility(View.VISIBLE);
                break;
            case EDITMODE:
                btCancel.setVisibility(View.VISIBLE);
                btupdate.setVisibility(View.VISIBLE);
                break;
            case ADDMODE:
                btAdd.setVisibility(View.VISIBLE);
                btCancelAdd.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog.OnDateSetListener callback;
            switch (v.getId()) {
                case R.id.btUpdate:
                    copyFile();
                    setEmployee();
                    updateEmployee();
                    displayEmployeeDetail();
                    setButton(VIEWMODE);
                    break;
                case R.id.buttonCancelUpdate:
                    displayEmployeeDetail();
                    setButton(VIEWMODE);
                    break;
                case R.id.buttonAdd:
                    copyFile();
                    setEmployee();
                    addEmployee();
                    displayEmployeeDetail();
                    setButton(VIEWMODE);
                    break;
                case R.id.buttonCancelAdd:
                    onBackPressed();
                    break;
                case R.id.buttonEdit:
                    enableEditEmployee();
                    setButton(EDITMODE);
                    break;
                case R.id.editBirthday:
                    callback = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            txBirthDay.setText(getString(R.string.format_date, dayOfMonth, monthOfYear + 1, year));
                        }
                    };
                    DatePickerDialog birthDateDialog = new DatePickerDialog(DisplayEmployeeDetailActivity.this,
                            callback, mYear, mMonth, mDay);
                    birthDateDialog.show();
                    break;
                case R.id.editJoinDate:
                    callback = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            txJoinDate.setText(getString(R.string.format_date, dayOfMonth, monthOfYear + 1, year));
                        }
                    };
                    DatePickerDialog joinDateDialog = new DatePickerDialog(DisplayEmployeeDetailActivity.this,
                            callback, mYear, mMonth, mDay);
                    joinDateDialog.show();
                    break;
                case R.id.editLeaveDate:
                    callback = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            txLeaveDate.setText(getString(R.string.format_date, dayOfMonth, monthOfYear + 1, year));
                        }
                    };
                    DatePickerDialog leaveDateDialog = new DatePickerDialog(DisplayEmployeeDetailActivity.this,
                            callback, mYear, mMonth, mDay);
                    leaveDateDialog.show();
                    break;
                case R.id.imageAvatar:
                    Intent intent = new Intent();
                    intent.setType(getString(R.string.image_type));
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, getString(R.string.intent_select_picute)), SELECT_PICTURE);
                    break;
            }
        }
    };

    public void setButtonListenner() {
        btupdate.setOnClickListener(clickListener);
        btCancel.setOnClickListener(clickListener);
        btEdit.setOnClickListener(clickListener);
        btAdd.setOnClickListener(clickListener);
        btCancelAdd.setOnClickListener(clickListener);
        imgavartar.setOnClickListener(clickListener);
        txJoinDate.setOnClickListener(clickListener);
        txLeaveDate.setOnClickListener(clickListener);
        txBirthDay.setOnClickListener(clickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
