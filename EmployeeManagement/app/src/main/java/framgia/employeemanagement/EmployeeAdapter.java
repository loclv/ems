package framgia.employeemanagement;
/**
 * Created by FRAMGIA\nguyen.huu.quyen on 05/10/2015.
 */
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

/*********
 * Adapter class extends with BaseAdapter and implements with OnClickListener
 ************/
public class EmployeeAdapter extends BaseAdapter {
    /***********
     * Declare Used Variables
     *********/
    private Context context;
    private List<Employee> data;
    private static LayoutInflater sInflater = null;
    Employee employee = null;

    /*************
     * EmployeeAdapter Constructor
     *****************/
    public EmployeeAdapter(Activity a, List<Employee> d) {
        /********** Take passed values **********/
        context = a;
        data = d;
        /***********  Layout inflator to call external xml layout () ***********/
        sInflater = LayoutInflater.from(context);
    }

    /********
     * What is the size of Passed Arraylist Size
     ************/
    @Override
    public int getCount() {
        if (data.size() <= 0)
            return 1;
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*********
     * Create a holder Class to contain inflated xml file elements
     *********/
    public static class ViewHolder {
        public TextView employeeNameForm;
        public TextView detailInformation;
        public ImageView avartar;
    }

    /******
     * Depends upon data size called for each row , Create each Employee row
     *****/
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;
        if (convertView == null) {
            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = sInflater.inflate(R.layout.employee_listview, null);
            /****** View Holder Object to contain tabitem.xml file elements ******/
            holder = new ViewHolder();
            holder.employeeNameForm = (TextView) vi.findViewById(R.id.employeeNameForm);
            holder.detailInformation = (TextView) vi.findViewById(R.id.employeeDetailForm);
            holder.avartar = (ImageView) vi.findViewById(R.id.employeeAvartarForm);
            /************  Set holder with LayoutInflater ************/
            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();
        if (data == null || data.size() == 0) {
            holder.employeeNameForm.setText(context.getString(R.string.msg_no_data));
        } else {
            /***** Get each Model object from Arraylist ********/
            employee = null;
            employee = data.get(position);
            /************  Set Model values in Holder elements ***********/
            //Display several data only
            holder.employeeNameForm.setText(employee.getName());
            holder.detailInformation.setText(context
                .getString(R.string.format_employee_detail, employee.getPosition(),
                    employee.getLeaveDate()));
            File imgFile = new File(employee.getImage());
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                holder.avartar.setImageBitmap(myBitmap);
            } else {
                holder.avartar.setImageResource(R.drawable.avar);
            }
            vi.setBackgroundColor((employee.getLeaveDate().equals("")) ?
                context.getResources().getColor(R.color.white) : context.getResources().getColor(R
                .color.gray));
        }
        return vi;
    }
}