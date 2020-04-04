package volunteer.upay.com.upay.activities.ui.addstudents;

import androidx.constraintlayout.widget.ConstraintLayout;
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


import com.google.android.material.bottomsheet.BottomSheetBehavior;

import volunteer.upay.com.upay.R;
import volunteer.upay.com.upay.adapters.AddStudentsAdapter;
import volunteer.upay.com.upay.databinding.AddStudentsFragmentBinding;
import volunteer.upay.com.upay.models.Student;


public class AddStudentsFragment extends Fragment {

    private AddStudentsFragmentBinding mBinding;
    private AddStudentsAdapter addStudentsAdapter;
    private AddStudentsViewModel mViewModel;
    private BottomSheetBehavior<ConstraintLayout> mBottomSheetBehaviour;

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
        mBinding.bottomSheetTitle.setOnClickListener((view) -> expandBottomSheet());
        mViewModel.studentLiveData.observe(getViewLifecycleOwner(), getObserver());
        mBottomSheetBehaviour = BottomSheetBehavior.from(mBinding.bottomSheet);
        return binding.getRoot();
    }

    private void expandBottomSheet() {
        if (mBottomSheetBehaviour.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    private Observer<Student> getObserver() {
        return (student) -> {
            addStudentsAdapter.addItem(student);
            clearFields();
        };
    }

    private void clearFields() {
        mBinding.age.setText("");
        mBinding.studentName.setText("");
        mBinding.parentName.setText("");
        mBinding.studentName.requestFocus();
    }

    public void uploadStudentsData() {
        mViewModel.uploadStudentsData(addStudentsAdapter.getStudentList());
    }
}
