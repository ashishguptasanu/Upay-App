package volunteer.upay.com.upay.activities.ui.addstudents;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import volunteer.upay.com.upay.models.Student;
import volunteer.upay.com.upay.models.StudentRemoteModel;
import volunteer.upay.com.upay.repsitories.AddStudentRepository;


public class AddStudentsViewModel extends AndroidViewModel {
    public MutableLiveData<Student> studentLiveData;
    private AddStudentRepository addStudentRepository;
    private String studentName;
    private String parentName;
    private String age;


    public AddStudentsViewModel(@NonNull Application application) {
        super(application);
        studentLiveData = new MutableLiveData<>();
        addStudentRepository = new AddStudentRepository(getApplication().getApplicationContext());
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

    public void uploadStudentsData(List<Student> students) {
        List<StudentRemoteModel> studentRemoteModelList = new ArrayList<>();
        for (Student s : students) {
            studentRemoteModelList.add(new StudentRemoteModel(s));
        }
        addStudentRepository.addStudents(studentRemoteModelList);
    }
}
