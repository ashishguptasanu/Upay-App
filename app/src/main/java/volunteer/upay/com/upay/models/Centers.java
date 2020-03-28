package volunteer.upay.com.upay.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by ashish on 10/3/18.
 */
@Entity
public class Centers implements Serializable, Comparable<Centers> {
    private String center_name;
    @PrimaryKey
    @NonNull
    private String center_id;
    private String zone_name;
    private String zone_id;
    private String latitude;
    private String longitude;
    private String center_head_name;
    private String center_head_phone;
    private String center_address;

    public String getCenterType() {
        return centerType;
    }

    private String centerType;

    public Centers(String center_name, @NonNull String center_id, String zone_name, String zone_id, String latitude, String longitude, String center_head_name, String center_head_phone, String center_address, String centerType) {
        this.center_name = center_name;
        this.center_id = center_id;
        this.zone_name = zone_name;
        this.zone_id = zone_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.center_head_name = center_head_name;
        this.center_head_phone = center_head_phone;
        this.center_address = center_address;
        this.centerType = centerType;
    }


    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getCenter_head_name() {
        return center_head_name;
    }

    public String getCenter_head_phone() {
        return center_head_phone;
    }

    public String getCenter_address() {
        return center_address;
    }

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

    private int getZoneIdInt() {
        try {
            return Integer.parseInt(zone_id);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public double getLongitudeDouble() {
        return Double.parseDouble(longitude);
    }

    public double getLatitudeDouble() {
        return Double.parseDouble(latitude);
    }

    @Override
    public int compareTo(@NonNull Centers o) {
        if (getZoneIdInt() > o.getZoneIdInt()) {
            return 1;
        } else if (getZoneIdInt() == o.getZoneIdInt()) {
            return 0;
        } else {
            return -1;
        }
    }
}
