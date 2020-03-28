package volunteer.upay.com.upay.activities.ui.addstudents;

import androidx.lifecycle.ViewModel;

import volunteer.upay.com.upay.models.Student;

public class AddStudentItemViewModel extends ViewModel {
    private Student student;


    public void setStudent(Student student) {
        this.student = student;
    }

    public String getTitle() {
        return student.getStudentName();
    }

    public String getSubTitle() {
        return student.getParentName();
    }


}
