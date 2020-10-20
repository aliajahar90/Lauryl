package versatile.project.lauryl.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import versatile.project.lauryl.model.*
import versatile.project.lauryl.model.address.AddressModel
import versatile.project.lauryl.model.address.AddressResponse
import versatile.project.lauryl.model.city.CitiResponse
import versatile.project.lauryl.model.city.CityModel
import versatile.project.lauryl.network.api.ApiServices
import versatile.project.lauryl.network.api.RetrofitObj

open class LaurylRepository {

    var apiServices: ApiServices = RetrofitObj.getApiObj()!!
    var apiVersatileServices: ApiServices = RetrofitObj.getVersatileApiObj()!!
    var userExistResponseLiveData: MutableLiveData<BooleanResponse> = MutableLiveData()
    var otpResponseLiveData: MutableLiveData<OtpResponse> = MutableLiveData()
    var checkOtpResponseLiveData: MutableLiveData<BooleanResponse> = MutableLiveData()
    var registerUserResponseLiveData: MutableLiveData<BooleanResponse> = MutableLiveData()
    var requestOtpForResetPasswordLiveData: MutableLiveData<OtpResponse> = MutableLiveData()
    var resetPasswordLiveData: MutableLiveData<OtpResponse> = MutableLiveData()
    var versatileLoginLiveData: MutableLiveData<VersatileLoginResponse> = MutableLiveData()
    var topServicesLiveData: MutableLiveData<TopServicesResponse> = MutableLiveData()
    var myOrdersLiveData: MutableLiveData<MyOrdersResponse> = MutableLiveData()
    var citiesLiveData: MutableLiveData<ArrayList<CityModel>> = MutableLiveData()
    var statesLiveData: MutableLiveData<ArrayList<String>> = MutableLiveData()
    var addressLiveData: MutableLiveData<ArrayList<AddressModel>> = MutableLiveData()
    var saveAddressLiveData: MutableLiveData<BooleanResponse> = MutableLiveData()
    var deleteAddressLiveData: MutableLiveData<BooleanResponse> = MutableLiveData()
    fun getMyOrdersLiveDta(): LiveData<MyOrdersResponse> {
        return myOrdersLiveData
    }

    fun getTopServicesLiveDta(): LiveData<TopServicesResponse> {
        return topServicesLiveData
    }

    fun getVersatileLoginLiveDta(): LiveData<VersatileLoginResponse> {
        return versatileLoginLiveData
    }

    fun getResetPasswordLiveDta(): LiveData<OtpResponse> {
        return resetPasswordLiveData
    }

    fun getrequestOtpForResetPasswordLiveDta(): LiveData<OtpResponse> {
        return requestOtpForResetPasswordLiveData
    }

    fun getregisterUserLiveDta(): LiveData<BooleanResponse> {
        return registerUserResponseLiveData
    }

    fun getChkOtpLiveDta(): LiveData<BooleanResponse> {
        return checkOtpResponseLiveData
    }

    fun getOtpLiveDta(): LiveData<OtpResponse> {
        return otpResponseLiveData
    }

    fun getUserExistanceLiveDta(): LiveData<BooleanResponse> {
        return userExistResponseLiveData
    }

    fun chkUserExistance(phNum: String) {

        apiServices.checkUserExistance(phNum).enqueue(object : Callback<BooleanResponse> {

            override fun onResponse(
                call: Call<BooleanResponse>,
                response: Response<BooleanResponse>
            ) {
                if (response != null && response.isSuccessful) {
                    userExistResponseLiveData.postValue(response.body())
                } else {
                    userExistResponseLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<BooleanResponse>, t: Throwable) {
                userExistResponseLiveData.postValue(null)
            }

        })

    }

    fun generateOtp(phNum: String) {

        apiServices.requestOtp(phNum).enqueue(object : Callback<OtpResponse> {

            override fun onResponse(call: Call<OtpResponse>, response: Response<OtpResponse>) {
                if (response != null && response.isSuccessful) {
                    otpResponseLiveData.postValue(response.body())
                } else {
                    otpResponseLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<OtpResponse>, t: Throwable) {
                otpResponseLiveData.postValue(null)
            }

        })

    }

    fun checkOtp(inputJsonObj: JsonObject) {

        apiServices.checkOtp(inputJsonObj).enqueue(object : Callback<BooleanResponse> {

            override fun onResponse(
                call: Call<BooleanResponse>,
                response: Response<BooleanResponse>
            ) {
                if (response != null && response.isSuccessful) {
                    checkOtpResponseLiveData.postValue(response.body())
                } else {
                    checkOtpResponseLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<BooleanResponse>, t: Throwable) {
                checkOtpResponseLiveData.postValue(null)
            }

        })

    }


    fun registerUser(inputJsonObj: JsonObject) {

        apiServices.registerUser(inputJsonObj).enqueue(object : Callback<BooleanResponse> {

            override fun onResponse(
                call: Call<BooleanResponse>,
                response: Response<BooleanResponse>
            ) {
                if (response != null && response.code() == 201) {
                    var booleanStsResponse = BooleanResponse()
                    booleanStsResponse.status = true
                    registerUserResponseLiveData.postValue(booleanStsResponse)
                } else {
                    registerUserResponseLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<BooleanResponse>, t: Throwable) {
                registerUserResponseLiveData.postValue(null)
            }

        })

    }

    fun generateOtpToResetPswrd(phNum: String) {

        apiServices.requestOtpForResetPassword(phNum).enqueue(object : Callback<OtpResponse> {

            override fun onResponse(call: Call<OtpResponse>, response: Response<OtpResponse>) {
                if (response != null && response.isSuccessful) {
                    requestOtpForResetPasswordLiveData.postValue(response.body())
                } else {
                    requestOtpForResetPasswordLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<OtpResponse>, t: Throwable) {
                requestOtpForResetPasswordLiveData.postValue(null)
            }

        })

    }

    fun resetPassword(inputJsonObj: JsonObject) {

        apiServices.resetPassword(inputJsonObj).enqueue(object : Callback<OtpResponse> {

            override fun onResponse(call: Call<OtpResponse>, response: Response<OtpResponse>) {
                if (response != null && response.isSuccessful) {
                    resetPasswordLiveData.postValue(response.body())
                } else {
                    resetPasswordLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<OtpResponse>, t: Throwable) {
                resetPasswordLiveData.postValue(null)
            }

        })

    }

    fun getTopServices(accessToken: String, inputJsonObj: JsonObject) {

        apiVersatileServices.getTopServices(accessToken, inputJsonObj)
            .enqueue(object : Callback<TopServicesResponse> {

                override fun onResponse(
                    call: Call<TopServicesResponse>,
                    response: Response<TopServicesResponse>
                ) {
                    if (response != null && response.code() == 200) {
                        topServicesLiveData.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<TopServicesResponse>, t: Throwable) {
                    topServicesLiveData.postValue(null)
                }

            })

    }

    fun getMyOrders(accessToken: String, inputJsonObj: JsonObject) {

        apiVersatileServices.getMyOrders(accessToken, inputJsonObj)
            .enqueue(object : Callback<MyOrdersResponse> {

                override fun onResponse(
                    call: Call<MyOrdersResponse>,
                    response: Response<MyOrdersResponse>
                ) {
                    if (response != null && response.code() == 200) {
                        myOrdersLiveData.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<MyOrdersResponse>, t: Throwable) {
                    myOrdersLiveData.postValue(null)
                }

            })

    }

    fun getCities(accessToken: String) {
        apiServices.getCities(accessToken).enqueue(object : Callback<CitiResponse> {
            override fun onFailure(call: Call<CitiResponse>, t: Throwable) {
                citiesLiveData.postValue(null)
            }

            override fun onResponse(call: Call<CitiResponse>, response: Response<CitiResponse>) {
                citiesLiveData.postValue(response.body()?.data?.list as ArrayList<CityModel>)
            }
        })
    }

    fun getStates() {
        val list = ArrayList<String>()
        list.add("Andhra")
        list.add("Telangana")
        list.add("Kerala")
        list.add("West Bangal")
        statesLiveData.postValue(list)
    }

    fun getAddresses(accessToken: String, number: String) {

        apiServices.getAddresses(accessToken = accessToken, number = number)
            .enqueue(object : Callback<AddressResponse> {
                override fun onFailure(call: Call<AddressResponse>, t: Throwable) {
                    t.printStackTrace()
                    addressLiveData.postValue(null)

                }

                override fun onResponse(
                    call: Call<AddressResponse>,
                    response: Response<AddressResponse>
                ) {
                    addressLiveData.postValue(response.body()?.data?.list as ArrayList<AddressModel>?)
                }

            })
    }

    fun saveAddress(accessToken: String, address: JsonObject) {
        Timber.e("saving address")

        apiServices.saveAddress(accessToken, address).enqueue(object : Callback<BooleanResponse> {
            override fun onFailure(call: Call<BooleanResponse>, t: Throwable) {
                saveAddressLiveData.postValue(null)
            }

            override fun onResponse(
                call: Call<BooleanResponse>,
                response: Response<BooleanResponse>
            ) {
                saveAddressLiveData.postValue(response.body())
            }

        })
    }

    fun deleteAddress(accessToken: String, id: String) {
        val array = JsonArray()
        array.add(id)
        apiServices.deleteAddress(accessToken, array).enqueue(object : Callback<BooleanResponse> {
            override fun onFailure(call: Call<BooleanResponse>, t: Throwable) {
                deleteAddressLiveData.postValue(null)
            }

            override fun onResponse(
                call: Call<BooleanResponse>,
                response: Response<BooleanResponse>
            ) {
                deleteAddressLiveData.postValue(response.body())
            }

        })
    }
}



