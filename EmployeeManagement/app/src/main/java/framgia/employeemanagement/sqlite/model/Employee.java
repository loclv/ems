package framgia.employeemanagement.sqlite.model;

/**
 * Created by FRAMGIA\luu.vinh.loc on 06/10/2015.
 */
public class Employee {
    int id;
    String name;
    String date_of_birth;
    String phone_number;
    String department;
    String position;
    String status;
    String start_time;

    // constructors
    public Employee() {
    }

    public Employee(String name, String date_of_birth, String phone_number, String department, String position, String status, String start_time) {
        this.name = name;
        this.date_of_birth = date_of_birth;
        this.phone_number = phone_number;
        this.department = department;
        this.position = position;
        this.status = status;
        this.start_time = start_time;
    }

    public Employee(int id, String name, String date_of_birth, String phone_number, String department, String position, String status, String start_time) {
        this.id = id;
        this.name = name;
        this.date_of_birth = date_of_birth;
        this.phone_number = phone_number;
        this.department = department;
        this.position = position;
        this.status = status;
        this.start_time = start_time;
    }

    // setter
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    // getter
    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDate_of_birth() {
        return this.date_of_birth;
    }

    public String getPhone_number() {
        return this.phone_number;
    }

    public  String getDepartment() {
        return this.department;
    }

    public String getPosition() {
        return this.position;
    }

    public String getStatus() {
        return this.status;
    }

    public String getStart_time() {
        return this.start_time;
    }
}
