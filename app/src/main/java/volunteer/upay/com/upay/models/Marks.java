package volunteer.upay.com.upay.models;

public class Marks {
    String id;
    String studentId;
    String totalMarks;
    String marksObtained;
    String sybmittedDate;
    String submittedBy;

    public String getTestName() {
        return testName;
    }

    String testName;

    public String getId() {
        return id;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getTotalMarks() {
        return totalMarks;
    }

    public String getMarksObtained() {
        return marksObtained;
    }

    public String getSybmittedDate() {
        return sybmittedDate;
    }

    public String getSubmittedBy() {
        return submittedBy;
    }

    public Marks(String id, String studentId, String testName, String totalMarks, String marksObtained, String sybmittedDate, String submittedBy) {
        this.id = id;
        this.studentId = studentId;
        this.testName = testName;
        this.totalMarks = totalMarks;
        this.marksObtained = marksObtained;
        this.sybmittedDate = sybmittedDate;
        this.submittedBy = submittedBy;
    }
}
