package versatile.project.lauryl.payment.data.repsitory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import versatile.project.lauryl.base.SingleLiveEvent;
import versatile.project.lauryl.data.source.LaurylRepository;
import versatile.project.lauryl.model.BooleanResponse;

public class PaymentSuccessRepository extends LaurylRepository {

    SingleLiveEvent<Boolean> isSubscriptionCallCompleted=new SingleLiveEvent<>();
    public void updateSubscription(String number){
        getApiServices().updateSubscription(number).enqueue(new Callback<BooleanResponse>() {
            @Override
            public void onResponse(Call<BooleanResponse> call, Response<BooleanResponse> response) {
                isSubscriptionCallCompleted.setValue(true);
            }

            @Override
            public void onFailure(Call<BooleanResponse> call, Throwable t) {
                isSubscriptionCallCompleted.setValue(true);
            }
        });
    }

    public SingleLiveEvent<Boolean> getIsSubscriptionCallCompleted() {
        return isSubscriptionCallCompleted;
    }
}
