package versatile.project.lauryl.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import versatile.project.lauryl.data.source.LaurylRepository;
import versatile.project.lauryl.profile.data.GetProfileResponse;

public class ProfileRepository extends LaurylRepository {
    private static ProfileRepository single_instance = null;
    private ProfileRepository() {
    }

    // static method to create instance of Singleton class
    public static ProfileRepository getInstance() {
        if (single_instance == null)
            single_instance = new ProfileRepository();

        return single_instance;
    }
    private MutableLiveData<GetProfileResponse> getProfileResponseLiveData = new MutableLiveData<>();
    private MutableLiveData<Throwable> throwableMutableLiveData = new MutableLiveData<>();

    public void getProfileInformation(String accessToken) {
        getApiVersatileServices().getMyProfile(accessToken).enqueue(new Callback<GetProfileResponse>() {
            @Override
            public void onResponse(Call<GetProfileResponse> call, Response<GetProfileResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    String res="{\n" +
                            "    \"data\": {\n" +
                            "        \"id\": \"5f7d9f18742bca0c68321d65\",\n" +
                            "        \"userName\": \"9542603950\",\n" +
                            "        \"password\": null,\n" +
                            "        \"email\": \"test@gmail.com\",\n" +
                            "        \"firstName\": \"vijay\",\n" +
                            "        \"lastName\": \"k\",\n" +
                            "        \"isVerified\": true,\n" +
                            "        \"isPrimaryUser\": true,\n" +
                            "        \"isActive\": true,\n" +
                            "        \"isAdmin\": true,\n" +
                            "        \"profilePicUrl\": null,\n" +
                            "        \"phoneNumber\": 9542603950,\n" +
                            "        \"expiryDate\": null,\n" +
                            "        \"addressList\": [\n" +
                            "            {\n" +
                            "                \"createdBy\": null,\n" +
                            "                \"createdAt\": 1602068249179,\n" +
                            "                \"modifiedBy\": null,\n" +
                            "                \"modifiedAt\": 1602068249180,\n" +
                            "                \"isDeleted\": false,\n" +
                            "                \"createdByUser\": null,\n" +
                            "                \"modifiedByUser\": null,\n" +
                            "                \"sellerId\": null,\n" +
                            "                \"vAccountId\": null,\n" +
                            "                \"marketPlaceName\": null,\n" +
                            "                \"id\": \"5f7d9f19742bca0c68321d66\",\n" +
                            "                \"address1\": \"vijay-1-address1\",\n" +
                            "                \"address2\": \"vijay-1-address2\",\n" +
                            "                \"city\": null,\n" +
                            "                \"state\": null,\n" +
                            "                \"country\": null,\n" +
                            "                \"pinCode\": null,\n" +
                            "                \"phoneNumber\": 9542603950\n" +
                            "            },\n" +
                            "            {\n" +
                            "                \"createdBy\": null,\n" +
                            "                \"createdAt\": 1602068250272,\n" +
                            "                \"modifiedBy\": null,\n" +
                            "                \"modifiedAt\": 1602068250274,\n" +
                            "                \"isDeleted\": false,\n" +
                            "                \"createdByUser\": null,\n" +
                            "                \"modifiedByUser\": null,\n" +
                            "                \"sellerId\": null,\n" +
                            "                \"vAccountId\": null,\n" +
                            "                \"marketPlaceName\": null,\n" +
                            "                \"id\": \"5f7d9f1a742bca0c68321d67\",\n" +
                            "                \"address1\": \"vijay-2-address1\",\n" +
                            "                \"address2\": \"vijay-2-address2\",\n" +
                            "                \"city\": null,\n" +
                            "                \"state\": null,\n" +
                            "                \"country\": null,\n" +
                            "                \"pinCode\": null,\n" +
                            "                \"phoneNumber\": 9542603950\n" +
                            "            }\n" +
                            "        ],\n" +
                            "        \"firstTimeLogin\": false\n" +
                            "    }\n" +
                            "}";
                    GetProfileResponse getProfileResponse=new Gson().fromJson(res,GetProfileResponse.class);
                    getProfileResponseLiveData.postValue(getProfileResponse);
                }else {
                    getProfileResponseLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<GetProfileResponse> call, Throwable t) {
                String res="{\n" +
                        "    \"data\": {\n" +
                        "        \"id\": \"5f7d9f18742bca0c68321d65\",\n" +
                        "        \"userName\": \"9542603950\",\n" +
                        "        \"password\": null,\n" +
                        "        \"email\": \"test@gmail.com\",\n" +
                        "        \"firstName\": \"vijay\",\n" +
                        "        \"lastName\": \"k\",\n" +
                        "        \"isVerified\": true,\n" +
                        "        \"isPrimaryUser\": true,\n" +
                        "        \"isActive\": true,\n" +
                        "        \"isAdmin\": true,\n" +
                        "        \"profilePicUrl\": null,\n" +
                        "        \"phoneNumber\": 9542603950,\n" +
                        "        \"expiryDate\": null,\n" +
                        "        \"addressList\": [\n" +
                        "            {\n" +
                        "                \"createdBy\": null,\n" +
                        "                \"createdAt\": 1602068249179,\n" +
                        "                \"modifiedBy\": null,\n" +
                        "                \"modifiedAt\": 1602068249180,\n" +
                        "                \"isDeleted\": false,\n" +
                        "                \"createdByUser\": null,\n" +
                        "                \"modifiedByUser\": null,\n" +
                        "                \"sellerId\": null,\n" +
                        "                \"vAccountId\": null,\n" +
                        "                \"marketPlaceName\": null,\n" +
                        "                \"id\": \"5f7d9f19742bca0c68321d66\",\n" +
                        "                \"address1\": \"vijay-1-address1\",\n" +
                        "                \"address2\": \"vijay-1-address2\",\n" +
                        "                \"city\": null,\n" +
                        "                \"state\": null,\n" +
                        "                \"country\": null,\n" +
                        "                \"pinCode\": null,\n" +
                        "                \"phoneNumber\": 9542603950\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"createdBy\": null,\n" +
                        "                \"createdAt\": 1602068250272,\n" +
                        "                \"modifiedBy\": null,\n" +
                        "                \"modifiedAt\": 1602068250274,\n" +
                        "                \"isDeleted\": false,\n" +
                        "                \"createdByUser\": null,\n" +
                        "                \"modifiedByUser\": null,\n" +
                        "                \"sellerId\": null,\n" +
                        "                \"vAccountId\": null,\n" +
                        "                \"marketPlaceName\": null,\n" +
                        "                \"id\": \"5f7d9f1a742bca0c68321d67\",\n" +
                        "                \"address1\": \"vijay-2-address1\",\n" +
                        "                \"address2\": \"vijay-2-address2\",\n" +
                        "                \"city\": null,\n" +
                        "                \"state\": null,\n" +
                        "                \"country\": null,\n" +
                        "                \"pinCode\": null,\n" +
                        "                \"phoneNumber\": 9542603950\n" +
                        "            }\n" +
                        "        ],\n" +
                        "        \"firstTimeLogin\": false\n" +
                        "    }\n" +
                        "}";
                GetProfileResponse getProfileResponse=new Gson().fromJson(res,GetProfileResponse.class);
                getProfileResponseLiveData.postValue(getProfileResponse);
                //throwableMutableLiveData.postValue(t);
            }
        });
    }

    public MutableLiveData<GetProfileResponse> getGetProfileResponseLiveData() {
        return getProfileResponseLiveData;
    }

    public MutableLiveData<Throwable> getThrowableMutableLiveData() {
        return throwableMutableLiveData;
    }
}
