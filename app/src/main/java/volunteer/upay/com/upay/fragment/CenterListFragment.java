package volunteer.upay.com.upay.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import volunteer.upay.com.upay.Models.CenterListResponse;
import volunteer.upay.com.upay.Models.Centers;
import volunteer.upay.com.upay.R;
import volunteer.upay.com.upay.rest.RestCallback;
import volunteer.upay.com.upay.rest.RetrofitRequest;

import static volunteer.upay.com.upay.util.Utilities.getHeaderMap;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnCenterSelectListener}
 * interface.
 */
public class CenterListFragment extends Fragment implements RestCallback.RestCallbacks<CenterListResponse> {

    private OnCenterSelectListener mListener;
    private CenterListAdapter adapter;


    public CenterListFragment() {
    }

    public static CenterListFragment newInstance() {
        return new CenterListFragment();
    }

    private void fetchCenterList() {
        Call<CenterListResponse> call = RetrofitRequest.fetchCenters(getHeaderMap());
        RestCallback<CenterListResponse> responseRestCallback = new RestCallback<>(getActivity(), call, this);
        responseRestCallback.executeRequest();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_centeritem_list, container, false);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(adapter = new CenterListAdapter(null, mListener));
            fetchCenterList();
        }
        return view;
    }

    private void updateUI(CenterListResponse response) {
        if (adapter != null && response.getResponse() != null && response.getResponse().getData() != null && response.getResponse().getData().getCenters() != null) {
            List<Centers> centerList = response.getResponse().getData().getCenters();
            adapter.setItems(centerList);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCenterSelectListener) {
            mListener = (OnCenterSelectListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResponse(Call<CenterListResponse> call, Response<CenterListResponse> response) {
        if (response != null && response.isSuccessful() && response.body() != null) {
            updateUI(response.body());
        } else {
//            Toast.makeText(getActivity(), "Some Problem occurred while fetching, Please try again.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onFailure(Call<CenterListResponse> call, Throwable t) {
//        Toast.makeText(getActivity(), "Some Problem occurred while fetching, Please try again.", Toast.LENGTH_SHORT).show();
    }

    public interface OnCenterSelectListener {
        void onCenterSelect(Centers item);
    }
}
