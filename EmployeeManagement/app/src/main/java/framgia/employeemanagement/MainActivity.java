package framgia.employeemanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class MainActivity extends Activity {
    private static final int ADDMODE = 2;
    private static Button sButtonSearchMain;
    private static Button sButtonAddnew;
    private static ListView sListDepartment;
    private static Spinner sSpinnerDepartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setFindViewById();
        //set data to spinner
        setSpinnerMain();
        //set list of department on listview and add listener
        setListViewMain();
        //set click listener on Search Button
        setSearchButtonOnClickListener();
        //set click listener on Add Button
        setAddButtonOnClickListener();
    }

    public void setFindViewById() {
        sListDepartment = (ListView) findViewById(R.id.listDivMain);
        sSpinnerDepartment = (Spinner) findViewById(R.id.spDepMain);
        sButtonSearchMain = (Button) findViewById(R.id.btSearchMain);
        sButtonAddnew = (Button) findViewById(R.id.btAdd);
    }

    private void setSpinnerMain() {
        // Creat spinner for Department list
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterSpiner = ArrayAdapter.createFromResource(this,
                R.array.deparmentSpinner_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterSpiner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sSpinnerDepartment.setAdapter(adapterSpiner);
    }

    private void setListViewMain() {
        //Creat listview of Deparments
        // Create an ArrayAdapter using the string array and a default Listview layout
        ArrayAdapter<CharSequence> adapterListview = ArrayAdapter.createFromResource(this,
                R.array.deparmentListview_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterListview.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Apply the adapter to the ListView
        sListDepartment.setAdapter(adapterListview);
        //set on Listview item click listener
        sListDepartment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DisplayEmployeeListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(getString(R.string.employee_list_mode), parent.getAdapter().getItem(position).toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void setSearchButtonOnClickListener() {
        sButtonSearchMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayEmployeeListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(getString(R.string.employee_list_mode), getString(R.string.result));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void setAddButtonOnClickListener() {
        sButtonAddnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayEmployeeDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(getString(R.string.display_mode), ADDMODE);
                intent.putExtras(bundle);
                startActivity(intent);
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
