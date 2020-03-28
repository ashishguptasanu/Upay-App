package volunteer.upay.com.upay.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by amanbansal on 26/05/18.
 */

public class Data {
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("centers")
    @Expose
    private List<Centers> centers = null;
    @SerializedName("attendance")
    @Expose
    private List<VolunteerLogModel> attendance = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Centers> getCenters() {
        return centers;
    }

    public void setCenters(List<Centers> centers) {
        this.centers = centers;
    }

    public List<VolunteerLogModel> getAttendance() {
        return attendance;
    }

    public void setAttendance(List<VolunteerLogModel> attendance) {
        this.attendance = attendance;
    }
}
