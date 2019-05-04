package volunteer.upay.com.upay.Models;

public class VolunteerLogModel {
    private String id;
    private String volunteer_id;
    private String center_id;
    private String attendance_status;
    private String timestmp;
    private String in_time;
    private String out_time;
    private String class_taught;
    private String subject;
    private String work_done;
    private String thought_of_day;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVolunteer_id() {
        return volunteer_id;
    }

    public void setVolunteer_id(String volunteer_id) {
        this.volunteer_id = volunteer_id;
    }

    public String getCenter_id() {
        return center_id;
    }

    public void setCenter_id(String center_id) {
        this.center_id = center_id;
    }

    public String getAttendance_status() {
        return attendance_status;
    }

    public void setAttendance_status(String attendance_status) {
        this.attendance_status = attendance_status;
    }

    public String getTimestmp() {
        return timestmp;
    }

    public void setTimestmp(String timestmp) {
        this.timestmp = timestmp;
    }

    public String getIn_time() {
        return in_time;
    }

    public void setIn_time(String in_time) {
        this.in_time = in_time;
    }

    public String getOut_time() {
        return out_time;
    }

    public void setOut_time(String out_time) {
        this.out_time = out_time;
    }

    public String getClass_taught() {
        return class_taught;
    }

    public void setClass_taught(String class_taught) {
        this.class_taught = class_taught;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getWork_done() {
        return work_done;
    }

    public void setWork_done(String work_done) {
        this.work_done = work_done;
    }

    public String getThought_of_day() {
        return thought_of_day;
    }

    public void setThought_of_day(String thought_of_day) {
        this.thought_of_day = thought_of_day;
    }
}
