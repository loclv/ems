package framgia.employeemanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import framgia.employeemanagement.Ultility.Libs;
import framgia.employeemanagement.sqlite.helper.DatabaseHelper;

/**
 * Created by FRAMGIA\nguyen.huu.quyen on 05/10/2015.
 */
public class DisplayEmployeeListActivity extends Activity {
    private static final int VIEWMODE = 1;
    private static final int EDITMODE = 3;
    private static ListView sListEmployee;
    private static EmployeeAdapter sEmployeeAdapter;
    private static TextView sTextResultList;
    private static EditText sEditTextEmployeeName;
    private static Button sButtonSearchOnList;
    private static List<Employee> sEmployeeArr;
    private DatabaseHelper db;
    private String EmployeeName = "";
    private String NameOfList = "";
    private String DepartmentName = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_employee_list);
        db = new DatabaseHelper(this);
        setFindViewById();
        //Update name of list data
        updateNameDataList();
        //set data to list
        setListData();
    }

    public void setFindViewById() {
        sEditTextEmployeeName = (EditText) findViewById(R.id.txEmployeeList);
        sTextResultList = (TextView) findViewById(R.id.txList);
        sButtonSearchOnList = (Button) findViewById(R.id.btSearchList);
        sButtonSearchOnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NameOfList = getString(R.string.result);
                EmployeeName = sEditTextEmployeeName.getText().toString();
                setListData();
            }
        });
    }

    /******
     * Function to Update name of list data
     *************/
    public void updateNameDataList() {
        Intent intent = getIntent();
        NameOfList = intent.getStringExtra(getString(R.string.employee_list_mode));
        EmployeeName = intent.getStringExtra(getString(R.string.intent_employee_name));
        DepartmentName = intent.getStringExtra(getString(R.string.intent_department));
        //Update name of department
        sTextResultList.setText(NameOfList);
    }

    /******
     * Function to set data in ArrayList
     *************/
    public void setListData() {
        if (NameOfList.equals(getString(R.string.result))) {
            sEmployeeArr = db.getListEmployees(EmployeeName, DepartmentName);
        } else {
            sEmployeeArr = db.getListEmployeesDepartment(NameOfList);
        }
        sListEmployee = (ListView) findViewById(R.id.listEmployee);
        /**************** Create Custom Adapter *********/
        sEmployeeAdapter = new EmployeeAdapter(this, sEmployeeArr);
        sListEmployee.setAdapter(sEmployeeAdapter);
        sListEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DisplayEmployeeListActivity.this,
                    DisplayEmployeeDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(DisplayEmployeeListActivity.this.getString(R.string.display_mode),
                    VIEWMODE);
                bundle.putLong(DisplayEmployeeDetailActivity.EMPLOYEE_ID,
                    sEmployeeArr.get(position).getId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        sListEmployee.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
                                           long id) {
                final Employee employee = sEmployeeArr.get(position);
                Libs lib = new Libs(DisplayEmployeeListActivity.this);
                lib.longClickAlert(employee.getId());
                return true;
            }
        });
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
