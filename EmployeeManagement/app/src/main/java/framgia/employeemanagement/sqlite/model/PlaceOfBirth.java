package framgia.employeemanagement.sqlite.model;

/**
 * Created by FRAMGIA\luu.vinh.loc on 06/10/2015.
 */
public class PlaceOfBirth {
    int id;
    String place_name;

    // constructors
    public PlaceOfBirth(){
    };

    public PlaceOfBirth(String place_name) {
        this.place_name = place_name;
    }

    public PlaceOfBirth(int id, String place_name) {
        this.id = id;
        this.place_name = place_name;
    }

    // setter
    public void setId(int id) {
        this.id = id;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    // getter
    public int getId() {
        return this.id;
    }

    public String getPlace_name() {
        return this.place_name;
    }
}
