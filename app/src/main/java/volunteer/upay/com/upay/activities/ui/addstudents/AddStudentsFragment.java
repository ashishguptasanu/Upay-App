package volunteer.upay.com.upay.activities.ui.addstudents;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import volunteer.upay.com.upay.R;
import volunteer.upay.com.upay.adapters.AddStudentsAdapter;
import volunteer.upay.com.upay.databinding.AddStudentsFragmentBinding;
import volunteer.upay.com.upay.models.Student;


public class AddStudentsFragment extends Fragment {

    private AddStudentsViewModel mViewModel;
    private AddStudentsFragmentBinding mBinding;
    private AddStudentsAdapter addStudentsAdapter;

    public static AddStudentsFragment newInstance() {
        return new AddStudentsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        AddStudentsFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.add_students_fragment, container, false);
        mBinding = binding;
        mViewModel = new ViewModelProvider(this).get(AddStudentsViewModel.class);
        binding.setViewModel(mViewModel);
        addStudentsAdapter = new AddStudentsAdapter(this, null);
        mBinding.recyclerView.setAdapter(addStudentsAdapter);
        mViewModel.studentLiveData.observe(getViewLifecycleOwner(), getObserver());
        return binding.getRoot();
    }

    private Observer<Student> getObserver() {
        return new Observer<Student>() {
            @Override
            public void onChanged(Student student) {
                addStudentsAdapter.addItem(student);
                mBinding.age.setText("");
                mBinding.studentName.setText("");
                mBinding.parentName.setText("");
            }
        };
    }


}
