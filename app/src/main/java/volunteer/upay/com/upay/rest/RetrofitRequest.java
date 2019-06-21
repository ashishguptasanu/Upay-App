package volunteer.upay.com.upay.rest;


import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import volunteer.upay.com.upay.Models.CenterListResponse;
import volunteer.upay.com.upay.Models.GeneralResponseModel;

/**
 * @author amanbansal
 * @version 1.0
 * @since 2/1/17
 */
public class RetrofitRequest {

    private static ApiInterfaceRetrofit requestInterface = RetrofitAdapter.getRetrofit(null).create(ApiInterfaceRetrofit.class);

    public static Call<GeneralResponseModel> registerUser(String endpoint, HashMap<String, Object> params) {
        return requestInterface.registerUser(endpoint, new RetrofitFieldMap(params).getParams());
    }

    public static Call<CenterListResponse> fetchCenters(Map<String, String> params) {
        return requestInterface.fetchCenters(params);
    }

    public static Call<GeneralResponseModel> markAttendance(Map<String, String> headers, Map<String, String> fieldMap) {
        return requestInterface.markAttendance(headers, fieldMap);
    }

    public static Call<GeneralResponseModel> getVolunteersDetails(Map<String, String> headers, String volunteerId) {
        return requestInterface.getVolunteersDetails(headers, volunteerId);
    }

    public static Call<GeneralResponseModel> getAllVolunteersDetails(Map<String, String> headers, String volunteerId) {
        return requestInterface.getAllVolunteerDetails(headers, volunteerId);
    }


}
