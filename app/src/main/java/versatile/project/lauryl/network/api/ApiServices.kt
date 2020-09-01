package versatile.project.lauryl.network.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*
import versatile.project.lauryl.model.LoginResponse

interface ApiServices {

    /* @GET
     fun getSleepHoursInfo(@Header("Authorization") accessToken: String,@Url url: String): Call<MySleepHours>

     @Headers("Content-Type:application/x-www-form-urlencoded")
     @FormUrlEncoded
     @POST("introspect")
     fun checkTokenStatus(@Header("Authorization") accessToken: String,@Field("token") tokenString: String): Call<MyAccessTokenExpiryData>

     @Headers("Content-Type:application/json")
     @POST("get_last_sync_time")
     fun fetchLastDvcSycTime(@Body bodyParams: String): Call<MyLastDvcSyncTime>

     @Multipart
     @POST("Empanelement_Application")
     fun submitEmpanelmentForm(@Part("doctor_id") docId: RequestBody,@Part cvvFilePart: MultipartBody.Part?,@Part mdclQulifFilePart: MultipartBody.Part?,@Part anualPrctceFilePart: MultipartBody.Part?,@Part crntIdmtyInsFilePart: MultipartBody.Part?, @Part("first_name") docName: RequestBody, @Part("last_name") lastName: RequestBody, @Part("national_id") nationalId: RequestBody, @Part("date_of_birth") dob: RequestBody, @Part("gender") gender: RequestBody, @Part("medical_registration_number") medRegNum: RequestBody, @Part("apc_no") yearOfReg: RequestBody,@Part("clicnic_type") clicnicType: RequestBody,@Part("specialization") specialization: RequestBody, @Part("name_of_clinic") namOfClinic: RequestBody, @Part("hospital_license_no") clinicLicenseNum: RequestBody,@Part("year_of_registration2") yearOfHospitalReg: RequestBody, @Part("address_of_clinic1") clinicAdrsOne: RequestBody, @Part("address2") clinicAdrsTwo: RequestBody, @Part("postcode") postalCode: RequestBody,@Part("state") state: RequestBody,@Part("country") country: RequestBody,@Part("phone_no") phNum: RequestBody,@Part("mobile_no") mblNum: RequestBody,@Part("email_address") emailId: RequestBody,@Part("status") status: RequestBody): Call<DocEmpanelmentResponse>

 */

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("oauth/token")
    fun executeLogin(
        @Field("grant_type") grantType: String, @Field("username") userName: String, @Field(
            "password"
        ) password: String
    ): Call<LoginResponse>

}