package versatile.project.lauryl.payment.viewModel;



import versatile.project.lauryl.base.BaseViewModel;
import versatile.project.lauryl.base.SingleLiveEvent;
import versatile.project.lauryl.payment.data.repsitory.PaymentSuccessRepository;

public class PaymentSuccessViewModel extends BaseViewModel {
    private PaymentSuccessRepository paymentSuccessRepository;
    public PaymentSuccessViewModel() {
        this.paymentSuccessRepository = new PaymentSuccessRepository();
    }

    public void updateSubscription(String number){
        paymentSuccessRepository.updateSubscription(number);
    }
    public SingleLiveEvent<Boolean> getSubscriptionCallUpdate(){
        return paymentSuccessRepository.getIsSubscriptionCallCompleted();
    }


}
