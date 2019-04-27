package volunteer.upay.com.upay.Models;

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
}
