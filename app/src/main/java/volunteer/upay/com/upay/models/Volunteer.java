package volunteer.upay.com.upay.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by ashish on 25/3/18.
 */

@Entity
public class Volunteer implements Serializable {
    @PrimaryKey
    @NonNull
    private String id;

    public String getId() {
        return id;
    }

    private String center_name;
    private String center_id;
    private String zone_name;
    private String zone_id;
    private String upay_id;
    private String email_id;
    private String phone;
    private String password;
    private String admin_access;
    private String name;
    private String added_by;

    public String getPhotoUrl() {
        return photoUrl;
    }

    private String photoUrl;

    public String getCenter_name() {
        return center_name;
    }

    public String getCenter_id() {
        return center_id;
    }

    public String getZone_name() {
        return zone_name;
    }

    public String getZone_id() {
        return zone_id;
    }

    public String getUpay_id() {
        return upay_id;
    }

    public String getEmail_id() {
        return email_id;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getAdmin_access() {
        return admin_access;
    }

    public String getName() {
        return name;
    }

    public String getAdded_by() {
        return added_by;
    }

    public boolean containText(String text) {
        return (center_name != null && center_name.toLowerCase().contains(text.toLowerCase()))
                || (zone_name != null && zone_name.toLowerCase().contains(text.toLowerCase()))
                || (name != null && name.toLowerCase().contains(text.toLowerCase()));
    }

    public Volunteer(String id, String center_name, String center_id, String zone_name, String zone_id, String upay_id, String email_id, String phone, String password, String admin_access, String name, String added_by, String photoUrl) {
        this.id = id;
        this.center_name = center_name;
        this.center_id = center_id;
        this.zone_name = zone_name;
        this.zone_id = zone_id;
        this.upay_id = upay_id;
        this.email_id = email_id;
        this.phone = phone;
        this.password = password;
        this.admin_access = admin_access;
        this.name = name;
        this.added_by = added_by;
        this.photoUrl = photoUrl;
    }


}
