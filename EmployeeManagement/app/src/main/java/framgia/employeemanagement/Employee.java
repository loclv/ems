package framgia.employeemanagement;

/**
 * Created by FRAMGIA\nguyen.huu.quyen on 05/10/2015.
 */
public class Employee {
    private long id;
    private String name = "";
    private String imagePath = "";
    private String placeOfBirth = "";
    private String birthday = "";
    private String phone = "";
    private String department = "";
    private String position = "";
    private String status = "";
    private String joinDate = "";
    private String leaveDate = "";

    // constructors
    public Employee() {
    }

    public Employee(String Name, String Image, String Address, String Birthday, String Phone, String
        Department, String Position, String Status, String JoinDate, String LeaveDate) {
        this.name = Name;
        this.imagePath = Image;
        this.placeOfBirth = Address;
        this.birthday = Birthday;
        this.phone = Phone;
        this.department = Department;
        this.position = Position;
        this.status = Status;
        this.joinDate = JoinDate;
        this.leaveDate = LeaveDate;
    }

    /***********
     * Set Methods
     ******************/
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String EmployeeName) {
        this.name = EmployeeName;
    }

    public void setImage(String Image) {
        this.imagePath = Image;
    }

    public void setPlaceOfBirth(String EmployeeAddress) {
        this.placeOfBirth = EmployeeAddress;
    }

    public void setBirthday(String EmployeeBirthday) {
        this.birthday = EmployeeBirthday;
    }

    public void setPhone(String EmployeePhone) {
        this.phone = EmployeePhone;
    }

    public void setDepartment(String EmployeeDepartment) {
        this.department = EmployeeDepartment;
    }

    public void setPosition(String EmployeePosition) {
        this.position = EmployeePosition;
    }

    public void setStatus(String EmployeeStatus) {
        this.status = EmployeeStatus;
    }

    public void setJoinDate(String EmployeeJoinDate) {
        this.joinDate = EmployeeJoinDate;
    }

    public void setLeaveDate(String EmployeeLeaveDate) {
        this.leaveDate = EmployeeLeaveDate;
    }

    /***********
     * Get Methods
     ****************/
    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getImage() {
        return this.imagePath;
    }

    public String getPlaceOfBirth() {
        return this.placeOfBirth;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getDepartment() {
        return this.department;
    }

    public String getPosition() {
        return this.position;
    }

    public String getStatus() {
        return this.status;
    }

    public String getJoinDate() {
        return this.joinDate;
    }

    public String getLeaveDate() {
        return this.leaveDate;
    }
}
