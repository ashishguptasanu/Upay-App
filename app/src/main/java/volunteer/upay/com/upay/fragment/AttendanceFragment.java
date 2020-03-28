package volunteer.upay.com.upay.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import volunteer.upay.com.upay.models.Centers;
import volunteer.upay.com.upay.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttendanceFragment extends Fragment {
    private Button mPresentButton;

    public AttendanceFragment() {
    }

    public static AttendanceFragment newInstance(Centers center) {
        AttendanceFragment fragment = new AttendanceFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("center", center);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_attendance, container, false);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("center")) {
            Centers centers = (Centers) bundle.getSerializable("center");
            if (centers != null) {
                Toast.makeText(getActivity(), centers.getCenter_name(), Toast.LENGTH_SHORT).show();

            }
        }

        mPresentButton = view.findViewById(R.id.presentButton);
        return view;
    }

}
