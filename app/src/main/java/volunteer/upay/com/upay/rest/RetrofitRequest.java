package volunteer.upay.com.upay.rest;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.Url;
import volunteer.upay.com.upay.models.CenterListResponse;
import volunteer.upay.com.upay.models.GeneralResponseModel;
import volunteer.upay.com.upay.models.StudentRemoteModel;
import volunteer.upay.com.upay.util.Utilities;

/**
 * @author amanbansal
 * @version 1.0
 * @since 2/1/17
 */
public class RetrofitRequest {

    private static ApiInterfaceRetrofit requestInterface = RetrofitAdapter.getRetrofit(Utilities.URL).create(ApiInterfaceRetrofit.class);

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

    public static Call<GeneralResponseModel> getAllVolunteersDetails(Map<String, String> headers, String centerId) {
        return requestInterface.getAllVolunteerDetails(headers, centerId);
    }

    public static Call<GeneralResponseModel> deleteVolunteer(Map<String, String> headers, String volunteerId) {
        return requestInterface.deleteVolunteer(headers, volunteerId);
    }

    public static Call<GeneralResponseModel> deleteStudent(Map<String, String> headers, String studentId) {
        return requestInterface.deleteStudent(headers, studentId);
    }

    public static Call<GeneralResponseModel> addStudents(@HeaderMap Map<String, String> headerMap, @Body List<StudentRemoteModel> studentRemoteModels) {
        return requestInterface.addStudents(headerMap,  studentRemoteModels);
    }

}
