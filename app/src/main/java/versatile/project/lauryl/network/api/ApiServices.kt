package versatile.project.lauryl.network.api

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*
import versatile.project.lauryl.model.*
import versatile.project.lauryl.orders.history.model.OrderItemsResponse
import versatile.project.lauryl.model.address.AddressResponse
import versatile.project.lauryl.model.city.CitiResponse
import versatile.project.lauryl.orders.create.model.CreateOrderResponse
import versatile.project.lauryl.pickup.data.CnfPickupResponse
import versatile.project.lauryl.profile.data.GetProfileResponse

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

    @POST("static/user/save")
    fun registerUser(@Body inputJsonBody: JsonObject): Call<BooleanResponse>

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("oauth/token")
    fun executeLogin(
        @Field("grant_type") grantType: String,
        @Field("username") userName: String,
        @Field("password") password: String,
        @Field("appVersion") appVersion: String
    ): Call<LoginResponse>

    @POST("static/user/otp/validate")
    fun checkOtp(@Body inputJsonBody: JsonObject): Call<BooleanResponse>

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @GET("static/user/exists/{phoneNumber}")
    fun checkUserExistance(@Path("phoneNumber") phNum: String): Call<BooleanResponse>

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @GET("static/user/otp/{phoneNumber}")
    fun requestOtp(@Path("phoneNumber") phNum: String): Call<OtpResponse>

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @GET("static/user/forgotPassword/{phoneNumber}")
    fun requestOtpForResetPassword(@Path("phoneNumber") phNum: String): Call<OtpResponse>

    @POST("static/user/resetPassword")
    fun resetPassword(@Body inputJsonBody: JsonObject): Call<OtpResponse>

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("oauth/token")
    fun executeVersatileLogin(
        @Field("grant_type") grantType: String,
        @Field("username") userName: String,
        @Field("password") password: String
    ): Call<VersatileLoginResponse>

    @POST("api/v1/inventory/service/list")
    fun getTopServices(
        @Query("access_token") accessToken: String,
        @Body inputJsonBody: JsonObject
    ): Call<TopServicesResponse>

    @POST("api/v1/order/list")
    fun getMyOrders(
        @Query("access_token") accessToken: String,
        @Body inputJsonBody: JsonObject
    ): Call<MyOrdersResponse>

    @GET("api/v1/user/address/list/{number}")
    fun getAddresses(
        @Path("number") number: String,
        @Query("access_token") accessToken: String
    ): Call<AddressResponse>

    @POST("api/v1/schedulerPickUp/list")
    fun getPickUpDateAndTime(
        @Query("access_token") accessToken: String,
        @Body inputJsonBody: JsonObject
    ): Call<CnfPickupResponse>

    @GET("api/v1/user/info")
    fun getMyProfile(@Query("access_token") accessToken: String): Call<GetProfileResponse>
    @POST("api/v1/order/item/list")
    fun getOrderItems(@Query("access_token") accessToken: String,@Body inputJsonBody: JsonObject): Call<OrderItemsResponse>


    @GET("api/v1/country/list")
    fun getCities(@Query("access_token") accessToken: String): Call<CitiResponse>
    @GET("api/v1/order/get/invoice/{invoice}")
    fun getInvoice(@Path("invoice") invoice:String,@Query("access_token") accessToken: String): Call<String>

    @POST("api/v1/user/address/save")
    fun saveAddress(@Query("access_token") accessToken: String,
                    @Body inputJsonBody: JsonObject):Call<BooleanResponse>
     @POST("api/v1/user/address/delete")
    fun deleteAddress(@Query("access_token") accessToken: String,
                    @Body idArray :JsonArray ):Call<BooleanResponse>

    @POST("api/v1/order/createOrder")
    fun createOrder(
        @Query("access_token") accessToken: String,
        @Body inputJsonBody: JsonObject
    ): Call<CreateOrderResponse>

    @POST(" api/v1/order/modify")
    fun modifyOrder(
        @Query("access_token") accessToken: String,
        @Body inputJsonBody: JsonObject
    ): Call<BooleanResponse>




    @POST("api/v1/order/item/update/stage")
    fun cancelOrder(
        @Query("access_token") accessToken: String,
        @Body inputJsonBody: JsonObject
    ): Call<BooleanResponse>

    @GET("static/user/subscribe/{number}")
    fun updateSubscription(@Path("number") phoneNumber: String): Call<BooleanResponse>
}