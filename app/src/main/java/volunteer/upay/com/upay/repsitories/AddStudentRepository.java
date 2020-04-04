package volunteer.upay.com.upay.repsitories;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import volunteer.upay.com.upay.models.GeneralResponseModel;
import volunteer.upay.com.upay.models.StudentRemoteModel;
import volunteer.upay.com.upay.rest.RestCallback;
import volunteer.upay.com.upay.rest.RetrofitRequest;
import volunteer.upay.com.upay.util.Utilities;

import static volunteer.upay.com.upay.util.Utilities.getHeaderMap;

public class AddStudentRepository implements RestCallback.RestCallbacks<GeneralResponseModel> {
    private Context context;

    public AddStudentRepository(@NonNull Context context) {
        this.context = context;
    }

    public void addStudents(List<StudentRemoteModel> studentRemoteModels) {
        Call<GeneralResponseModel> call = RetrofitRequest.addStudents(getHeaderMap(), studentRemoteModels);
        RestCallback<GeneralResponseModel> restCallback = new RestCallback<>(context, call, this);
        restCallback.executeRequest();
    }

    @Override
    public void onResponse(Call<GeneralResponseModel> call, Response<GeneralResponseModel> response) {
        if (response != null && response.body() != null && response.body().getResponse() != null) {
            Log.e("pass", "Success");
        } else {
            Log.e("pass", "Failure");
        }
    }

    @Override
    public void onFailure(Call<GeneralResponseModel> call, Throwable t) {
        Log.e("pass", "Failure");
    }
}
