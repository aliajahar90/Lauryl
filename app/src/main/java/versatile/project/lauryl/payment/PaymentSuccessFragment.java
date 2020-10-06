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
import versatile.project.lauryl.base.BaseBinding;
import versatile.project.lauryl.databinding.FragmentPaymentSuccessBinding;
import versatile.project.lauryl.payment.data.PaymentBaseShareData;
import versatile.project.lauryl.payment.viewModel.PaymentSuccessViewModel;
import versatile.project.lauryl.payment.viewModel.PaymentViewModel;
import versatile.project.lauryl.utils.AllConstants;

public class PaymentSuccessFragment extends BaseBinding<PaymentSuccessViewModel, FragmentPaymentSuccessBinding> {
    private  PaymentBaseShareData paymentBaseShareData;
    public static final String TAG=PaymentSuccessFragment.class.getName();
    private FragmentPaymentSuccessBinding paymentSuccessBinding;
    private PaymentSuccessViewModel paymentSuccessViewModel;
    public static PaymentSuccessFragment newInstance() {
        PaymentSuccessFragment paymentSuccessFragment = new PaymentSuccessFragment();
        try {
            paymentSuccessFragment.paymentBaseShareData = new Gson().fromJson((String) paymentSuccessFragment.getArguments().get(AllConstants.Payment.PaymentData), PaymentBaseShareData.class);
        } catch (Exception e) {
            Log.d("Error", "Null Pointer");
        }
        return paymentSuccessFragment;
    }

    private PaymentSuccessFragment() {
    }
    @Override
    protected void initializeViewModel() {
        paymentSuccessViewModel= new ViewModelProvider(this).get(PaymentSuccessViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        paymentSuccessBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_payment_success,container,false);
        return paymentSuccessBinding.getRoot();
    }
}
