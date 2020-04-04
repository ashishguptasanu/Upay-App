package volunteer.upay.com.upay.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import volunteer.upay.com.upay.R;
import volunteer.upay.com.upay.activities.ui.addstudents.AddStudentItemViewModel;
import volunteer.upay.com.upay.databinding.AddStudentItemBinding;
import volunteer.upay.com.upay.models.Student;

public class AddStudentsAdapter extends RecyclerView.Adapter<AddStudentsAdapter.MyViewHolder> {
    private List<Student> studentList;
    private ViewModelStoreOwner viewModelStoreOwner;

    public AddStudentsAdapter(@NonNull ViewModelStoreOwner viewModelStoreOwner, @Nullable List<Student> students) {
        this.viewModelStoreOwner = viewModelStoreOwner;
        studentList = students;
        if (studentList == null) {
            studentList = new ArrayList<>();
        }
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        AddStudentItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.add_student_item, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Student student = studentList.get(position);
        AddStudentItemViewModel viewModel = new ViewModelProvider(viewModelStoreOwner).get(AddStudentItemViewModel.class);
        viewModel.setStudent(student);
        holder.binding.setViewModel(viewModel);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return studentList == null ? 0 : studentList.size();
    }

    public void addItem(@NonNull Student student) {
        studentList.add(0, student);
        notifyItemInserted(0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private AddStudentItemBinding binding;

        public MyViewHolder(@NonNull AddStudentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
