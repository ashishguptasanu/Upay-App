package volunteer.upay.com.upay.activities.ui.addstudents;

import android.text.TextUtils;

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

    public String getFirstWord() {
        String name = student.getStudentName();
        if (!TextUtils.isEmpty(name)) {
            return name.substring(0, 1).toUpperCase();
        }
        return "U";
    }

    public String getSubTitle() {
        return student.getParentName();
    }


}
