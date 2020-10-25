package versatile.project.lauryl.payment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;

import versatile.project.lauryl.R;
import versatile.project.lauryl.application.MyApplication;
import versatile.project.lauryl.base.BaseBinding;
import versatile.project.lauryl.base.HomeNavigationController;
import versatile.project.lauryl.databinding.FragmentPaymentErrorBinding;
import versatile.project.lauryl.databinding.FragmentPaymentSuccessBinding;
import versatile.project.lauryl.payment.data.PaymentBaseShareData;
import versatile.project.lauryl.payment.viewModel.PaymentErrorViewModel;
import versatile.project.lauryl.payment.viewModel.PaymentSuccessViewModel;
import versatile.project.lauryl.utils.AllConstants;

public class PaymentErrorFragment extends BaseBinding<PaymentErrorViewModel, FragmentPaymentErrorBinding> {
    private  PaymentBaseShareData paymentBaseShareData;
    public static final String TAG= PaymentErrorFragment.class.getName();
    private FragmentPaymentErrorBinding paymentErrorBinding;
    private PaymentErrorViewModel paymentErrorViewModel;
    public static PaymentErrorFragment newInstance() {
        PaymentErrorFragment paymentSuccessFragment = new PaymentErrorFragment();
        return paymentSuccessFragment;
    }
    private PaymentErrorFragment() {
    }
    @Override
    protected void initializeViewModel() {
        paymentErrorViewModel= new ViewModelProvider(this).get(PaymentErrorViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        paymentErrorBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_payment_error,container,false);
        try {
            paymentBaseShareData = new Gson().fromJson((String)getArguments().get(AllConstants.Payment.PaymentData), PaymentBaseShareData.class);
        } catch (Exception e) {
            Log.d("Error", "Null Pointer");
        }
        return paymentErrorBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (paymentBaseShareData!=null && paymentBaseShareData.getPaymentError()!=null){
            String des=paymentBaseShareData.getPaymentError().getDescription();
            Log.v("paymentErrorDesc",des);
            PaymentBaseShareData.RazorPayError error=new Gson().fromJson(des, PaymentBaseShareData.RazorPayError.class);
            paymentErrorBinding.txtTransactionId.setText(error.getError().getCode());
            paymentErrorBinding.txtReason.setText(error.getError().getDescription());
    }
        onClicks();
    }
    void onClicks(){
        paymentErrorBinding.btnRetry.setOnClickListener(view -> {
            MyApplication myApplication=(MyApplication) getActivity().getApplicationContext();
            myApplication.setActiveSessionOrderNumber(paymentBaseShareData.getPaymentError().getSerializedOrderInformation());
            myApplication.setCreateOrderSerializedService(paymentBaseShareData.getPaymentError().getSerializedServiceInformation());
            myApplication.setCreateOrderSerializdedAddressData(paymentBaseShareData.getPaymentError().getSerializedAddressInformation());
            myApplication.setCreateOrderSerializdedProfile(paymentBaseShareData.getPaymentError().getSerializedProfileInformation());
            HomeNavigationController.getInstance(getActivity()).enableBackButton();
            getActivity().getSupportFragmentManager().popBackStackImmediate();

        });
    }
}
