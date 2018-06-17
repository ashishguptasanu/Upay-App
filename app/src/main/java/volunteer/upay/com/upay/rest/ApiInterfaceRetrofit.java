package volunteer.upay.com.upay.rest;


import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import volunteer.upay.com.upay.Models.CenterListResponse;
import volunteer.upay.com.upay.Models.GeneralResponseModel;

/**
 * @author amanbansal
 * @version 1.0
 * @since 2/1/17
 */

public interface ApiInterfaceRetrofit {

    @Headers("Content-Type: application/json")
    @POST("{endpoint}")
    Call<GeneralResponseModel> registerUser(@Path("endpoint") String endpoint, @Body Map<String, Object> map);


    @Headers("Content-Type: application/json")
    @GET("get_center_details.php")
    Call<CenterListResponse> fetchCenters(@HeaderMap Map<String, String> map);

    @FormUrlEncoded
    @POST("submit_volunteer_attendance.php")
    Call<GeneralResponseModel> markAttendance(@HeaderMap Map<String, String> headerMap, @FieldMap Map<String, String> fieldMap);


}
