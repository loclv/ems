package framgia.employeemanagement;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by FRAMGIA\nguyen.huu.quyen on 05/10/2015.
 */
public class DisplayEmployeeListActivity extends Activity {
    private static ListView sListEmployee;
    private static EmployeeAdapter sEmployeeAdapter;
    private static TextView sTextResultList;
    private static Button sButtonSearchOnList;
    private static ArrayList<Employee> sEmployeeArr = new ArrayList<Employee>();
    private static DisplayEmployeeListActivity sEmployeeActivity = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_employee_list);
        setFindViewById();
        //Update name of list data
        updateNameDataList();
        //set data to list
        setListData(1);
    }

    public void setFindViewById() {
        sTextResultList = (TextView) findViewById(R.id.txList);
        sButtonSearchOnList = (Button) findViewById(R.id.btSearchList);
        sButtonSearchOnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO get new list and display
                setListData(2);
            }
        });
    }

    /******
     * Function to Update name of list data
     *************/
    public void updateNameDataList() {
        Intent intent = getIntent();
        String Ename = intent.getStringExtra(getString(R.string.employee_list_mode));
        //Update name of departmmen
        sTextResultList.setText(Ename);
    }

    /******
     * Function to set data in ArrayList
     *************/
    public void setListData(int mod) {
        sEmployeeArr.clear();
        for (int i = 0; i < 8; i++) {

            final Employee employee = new Employee();
            /******* Firstly take data in model object ******/
            if (mod == 1) {
                employee.setName("Nguyen Huu Quyen  " + i);
            } else {
                employee.setName("Ha Minh Hoang  " + i);
            }
            employee.setImage("/storage/emulated/0/QuyenNH/employee00" + i + ".jpg");
            employee.setPosition("Position " + i);
            employee.setJoinDate("15/08/2014");
            employee.setLeaveDate("");
            if (i == 3 || i == 5) {
                employee.setLeaveDate("15/09/2015");
            }
            /******** Take Model Object in ArrayList **********/
            sEmployeeArr.add(employee);
            sEmployeeActivity = this;
            Resources res = getResources();
            sListEmployee = (ListView) findViewById(R.id.listEmployee);  // List defined in XML ( See Below )
            /**************** Create Custom Adapter *********/
            sEmployeeAdapter = new EmployeeAdapter(sEmployeeActivity, sEmployeeArr, res);
            sListEmployee.setAdapter(sEmployeeAdapter);
        }
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
