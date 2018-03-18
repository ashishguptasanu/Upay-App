package volunteer.upay.com.upay.Models;

/**
 * Created by ashish on 18/3/18.
 */

public class Student {
    String id;
    String studentName;
    String parentName;
    String age;
    String clss;
    String school;
    String centerName;
    String centerId;
    String zoneName;
    String zoneId;
    String photoUrl;
    String comments;

    public String getId() {
        return id;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getParentName() {
        return parentName;
    }

    public String getAge() {
        return age;
    }

    public String getClss() {
        return clss;
    }

    public String getSchool() {
        return school;
    }

    public String getCenterName() {
        return centerName;
    }

    public String getCenterId() {
        return centerId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public String getZoneId() {
        return zoneId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getComments() {
        return comments;
    }



    public Student(String id, String studentName, String parentName, String age, String clss, String school, String centerName, String centerId, String zoneName, String zoneId, String photoUrl, String comments) {
        this.id = id;
        this.studentName = studentName;
        this.parentName = parentName;
        this.age = age;
        this.clss = clss;
        this.school = school;
        this.centerName = centerName;
        this.centerId = centerId;
        this.zoneName = zoneName;
        this.zoneId = zoneId;
        this.photoUrl = photoUrl;
        this.comments = comments;
    }
}
