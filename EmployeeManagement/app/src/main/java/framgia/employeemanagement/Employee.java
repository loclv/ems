package framgia.employeemanagement;

/**
 * Created by FRAMGIA\nguyen.huu.quyen on 05/10/2015.
 */
public class Employee {
    private String Name = "";
    private String Image = "";
    private String Address = "";
    private String Birthday = "";
    private String Phone = "";
    private String Department = "";
    private String Position = "";
    private String Status = "";
    private String JoinDate = "";
    private String LeaveDate = "";

    /***********
     * Set Methods
     ******************/

    public void setName(String EmployeeName) {
        this.Name = EmployeeName;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public void setAddress(String EmployeeAddress) {
        this.Address = EmployeeAddress;
    }

    public void setBirthday(String EmployeeBirthday) {
        this.Birthday = EmployeeBirthday;
    }

    public void setPhone(String EmployeePhone) {
        this.Phone = EmployeePhone;
    }

    public void setDepartment(String EmployeeDepartment) {
        this.Department = EmployeeDepartment;
    }

    public void setPosition(String EmployeePosition) {
        this.Position = EmployeePosition;
    }

    public void setStatus(String EmployeeStatus) {
        this.Status = EmployeeStatus;
    }

    public void setJoinDate(String EmployeeJoinDate) {
        this.JoinDate = EmployeeJoinDate;
    }

    public void setLeaveDate(String EmployeeLeaveDate) {
        this.LeaveDate = EmployeeLeaveDate;
    }

    /***********
     * Get Methods
     ****************/
    public String getName() {
        return this.Name;
    }

    public String getImage() {
        return this.Image;
    }

    public String getAddress() {
        return this.Address;
    }

    public String getBirthday() {
        return this.Birthday;
    }

    public String getPhone() {
        return this.Phone;
    }

    public String getDepartment() {
        return this.Department;
    }

    public String getPosition() {
        return this.Position;
    }

    public String getStatus() {
        return this.Status;
    }

    public String getJoinDate() {
        return this.JoinDate;
    }

    public String getLeaveDate() {
        return this.LeaveDate;
    }
}
