package volunteer.upay.com.upay.activities.ui.addstudents;

import androidx.databinding.DataBindingUtil;
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


public class AddStudentsFragment extends Fragment {

    private AddStudentsViewModel mViewModel;
    private AddStudentsFragmentBinding mBinding;

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
        mBinding.recyclerView.setAdapter(new AddStudentsAdapter(this, null));
        return binding.getRoot();
    }


}
