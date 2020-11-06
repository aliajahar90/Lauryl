package versatile.project.lauryl.payment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import timber.log.Timber;
import versatile.project.lauryl.R;
import versatile.project.lauryl.base.BaseBinding;
import versatile.project.lauryl.base.HomeNavigationController;
import versatile.project.lauryl.databinding.FragmentPaymentSuccessBinding;
import versatile.project.lauryl.home.HomeFragment;
import versatile.project.lauryl.payment.data.PaymentBaseShareData;
import versatile.project.lauryl.payment.viewModel.PaymentSuccessViewModel;
import versatile.project.lauryl.payment.viewModel.PaymentViewModel;
import versatile.project.lauryl.screens.HomeScreen;
import versatile.project.lauryl.utils.AllConstants;

public class PaymentSuccessFragment extends BaseBinding<PaymentSuccessViewModel, FragmentPaymentSuccessBinding> {
    private PaymentBaseShareData paymentBaseShareData;
    public static final String TAG = PaymentSuccessFragment.class.getName();
    private FragmentPaymentSuccessBinding paymentSuccessBinding;
    private PaymentSuccessViewModel paymentSuccessViewModel;

    public static PaymentSuccessFragment newInstance() {
        PaymentSuccessFragment paymentSuccessFragment = new PaymentSuccessFragment();
        return paymentSuccessFragment;
    }

    private PaymentSuccessFragment() {
    }

    @Override
    protected void initializeViewModel() {
        paymentSuccessViewModel = new ViewModelProvider(this).get(PaymentSuccessViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        paymentSuccessBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment_success, container, false);
        try {
           paymentBaseShareData= new Gson().fromJson((String)getArguments().get(AllConstants.Payment.PaymentData), PaymentBaseShareData.class);
        } catch (Exception e) {
            Log.d("Error", "Null Pointer");
        }
        return paymentSuccessBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        paymentSuccessBinding.txtDebitedAmount.setText("\u20B9 "+paymentBaseShareData.getPaymentSuccess().getCreateOrderData().getDetails().getOrderTotal());
        paymentSuccessBinding.txtOrderId.setText(paymentBaseShareData.getPaymentSuccess().getCreateOrderData().getDetails().getOrderNumber());
        paymentSuccessBinding.txtDateTime.setText(getPickupDate(paymentBaseShareData.getPaymentSuccess().getCreateOrderData().getDetails().getPickupDate())+" "+paymentBaseShareData.getPaymentSuccess().getCreateOrderData().getDetails().getPickupSlot());
        paymentSuccessBinding.txtMethod.setText(paymentBaseShareData.getPaymentSuccess().getPaymenMethod());
//       // paymentSuccessBinding.btnOderStatus.setText(paymentBaseShareData.getPaymentSuccess().getPaymentTransactionId());
        paymentSuccessBinding.btnOderStatus.setOnClickListener(view -> {
            if (getActivity() instanceof HomeScreen) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
               // fm.popBackStack();
                for(int i = 1; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }
                ((HomeScreen) getActivity()).showBackButton();
                ((HomeScreen) getActivity()).displayMyOrdersFragment(0);

            }

        });

    }

    private String getOrderDate(String date) {
        try {
            DateTime someDate = new DateTime(java.lang.Long.valueOf(date), DateTimeZone.forID("Asia/Kolkata"));
            DateTimeFormatter dateTimeFormate = DateTimeFormat.forPattern("MMM dd,yyyy");
            return "Placed on " + someDate.toString(dateTimeFormate);
        } catch (Exception e) {

        }
        return "";
    }

    private String getOrderTime(String date) {
        try {
            DateTime someDate = new DateTime(java.lang.Long.valueOf(date), DateTimeZone.forID("Asia/Kolkata"));
            DateTimeFormatter dateTimeFormate = DateTimeFormat.forPattern("hh:mm a");
            return "" + someDate.toString(dateTimeFormate).toUpperCase();
        } catch (Exception e) {

        }
        return "";
    }
    private String getPickupDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");
            DateTime someDate =formatter.parseDateTime(date);
            DateTimeFormatter dateTimeFormate = DateTimeFormat.forPattern("MMM dd,yyyy");
            return  someDate.toString(dateTimeFormate);
        } catch (Exception e) {

        }
        return "";
    }
}
