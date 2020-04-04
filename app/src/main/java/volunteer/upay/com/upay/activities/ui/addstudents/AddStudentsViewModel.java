package volunteer.upay.com.upay.activities.ui.addstudents;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import volunteer.upay.com.upay.models.Student;


public class AddStudentsViewModel extends AndroidViewModel {
    public MutableLiveData<Student> studentLiveData;
    private String studentName;
    private String parentName;
    private String age;


    public AddStudentsViewModel(@NonNull Application application) {
        super(application);
        studentLiveData = new MutableLiveData<>();
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }


    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }


    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void addStudent() {
        Student student = new Student(studentName, parentName, age);
        studentLiveData.postValue(student);
    }
}
