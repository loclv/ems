package framgia.employeemanagement;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends Activity {
    private Button btSearchMain;
    private Button btAddnew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set data to spinner
        setSpinnerMain();
        //set list of department on listview and add listener
        setListViewMain();
        //set click listener on Search Button
        setSearchButtonOnClickListener();
    }
    private void setSpinnerMain(){
        // Creat spinner for Department list
        Spinner spinner = (Spinner) findViewById(R.id.spDepMain);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterSpiner = ArrayAdapter.createFromResource(this,
                R.array.deparmentSpinner_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterSpiner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapterSpiner);
    }
    private void setListViewMain(){
        //Creat listview of Deparments
        final ListView listView = (ListView) findViewById(R.id.listDivMain);
        // Create an ArrayAdapter using the string array and a default Listview layout
        ArrayAdapter<CharSequence> adapterListview = ArrayAdapter.createFromResource(this,
                R.array.deparmentListview_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterListview.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Apply the adapter to the ListView
        listView.setAdapter(adapterListview);
        //set on Listview item click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setContentView(R.layout.display_employee_list);
                //Update name of departmmen
                TextView textList = (TextView) findViewById(R.id.txList);
                textList.setText(parent.getAdapter().getItem(position).toString());
            }
        });
    }
    private void setSearchButtonOnClickListener(){
        btSearchMain = (Button)findViewById(R.id.btMain);
        btSearchMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.display_employee_list);
                //Update name of departmmen
                TextView textList = (TextView) findViewById(R.id.txList);
                textList.setText("Result");
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
