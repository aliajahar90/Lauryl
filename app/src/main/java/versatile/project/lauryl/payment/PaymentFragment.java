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
import com.razorpay.Razorpay;

import versatile.project.lauryl.R;
import versatile.project.lauryl.base.BaseBinding;
import versatile.project.lauryl.databinding.PaymentFragmentBinding;
import versatile.project.lauryl.payment.data.PaymentBaseShareData;
import versatile.project.lauryl.payment.viewModel.PaymentViewModel;
import versatile.project.lauryl.utils.AllConstants;

public class PaymentFragment extends BaseBinding<PaymentViewModel, PaymentFragmentBinding> {
    private PaymentFragmentBinding paymentFragmentBinding;
    private PaymentViewModel paymentViewModel;
    public static final int PaymentTypeUpi = 1;
    public static final int PaymentTypeCards = 2;
    public static final int PaymentTypeNetBanking = 3;
    private static PaymentBaseShareData paymentBaseShareData;
    private Razorpay razorpay;

    public static PaymentFragment newInstance(int viewType) {
        PaymentFragment paymentFragment = new PaymentFragment();
        try {
            paymentBaseShareData = new Gson().fromJson((String) paymentFragment.getArguments().get(AllConstants.PaymentData), PaymentBaseShareData.class);
        } catch (Exception e) {
            Log.d("Error", "Null Pointer");
        }
        if (paymentBaseShareData == null) {
            paymentBaseShareData = new PaymentBaseShareData();
        }
        paymentBaseShareData.setViewType(viewType);
        return paymentFragment;
    }

    private PaymentFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        paymentFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.payment_fragment, container, false);
        displayView(paymentBaseShareData.getViewType());
        paymentFragmentBinding.setViewmodel(paymentViewModel);
        return paymentFragmentBinding.getRoot();
    }

    private void displayView(int type) {
        switch (type) {
            case PaymentTypeCards:
                paymentFragmentBinding.paymentCard.getRoot().setVisibility(View.VISIBLE);
                paymentFragmentBinding.paymentNetBank.getRoot().setVisibility(View.GONE);
                paymentFragmentBinding.paymentUPI.getRoot().setVisibility(View.GONE);
                activateCardsPayment();
                deActivateUpiPayment();
                deActivateNetBankingPayment();
                break;
            case PaymentTypeNetBanking:
                paymentFragmentBinding.paymentNetBank.getRoot().setVisibility(View.VISIBLE);
                paymentFragmentBinding.paymentCard.getRoot().setVisibility(View.GONE);
                paymentFragmentBinding.paymentUPI.getRoot().setVisibility(View.GONE);
                activateNetBankingPayment();
                deActivateCardsPayment();
                deActivateUpiPayment();
                break;
            case PaymentTypeUpi:
            default:
                paymentFragmentBinding.paymentUPI.getRoot().setVisibility(View.VISIBLE);
                paymentFragmentBinding.paymentNetBank.getRoot().setVisibility(View.GONE);
                paymentFragmentBinding.paymentCard.getRoot().setVisibility(View.GONE);
                activateUpiPayment();
                deActivateCardsPayment();
                deActivateNetBankingPayment();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onClicks();


    }

    void onClicks() {
        paymentFragmentBinding.rlUpi.setOnClickListener(view -> {
            displayView(PaymentFragment.PaymentTypeUpi);
        });
        paymentFragmentBinding.rlCards.setOnClickListener(view -> {
            displayView(PaymentFragment.PaymentTypeCards);
        });
        paymentFragmentBinding.rlNetbank.setOnClickListener(view -> {
            displayView(PaymentFragment.PaymentTypeNetBanking);
        });
    }

    @Override
    protected void initializeViewModel() {
        paymentViewModel = new ViewModelProvider(this).get(PaymentViewModel.class);
    }

    void activateUpiPayment() {
        paymentFragmentBinding.rlUpi.setBackground(getResources().getDrawable(R.drawable.selected_payment));
        paymentFragmentBinding.imgUPI.setImageResource(R.drawable.upi_white);
        paymentFragmentBinding.txtBankAccount.setTextColor(getResources().getColor(R.color.white));
    }

    void deActivateUpiPayment() {
        paymentFragmentBinding.rlUpi.setBackgroundColor(getResources().getColor(R.color.white));
        paymentFragmentBinding.imgUPI.setImageResource(R.drawable.upi);
        paymentFragmentBinding.txtBankAccount.setTextColor(getResources().getColor(R.color.orange));
    }

    void activateCardsPayment() {
        paymentFragmentBinding.rlCardsChild.setBackground(getResources().getDrawable(R.drawable.selected_payment));
        paymentFragmentBinding.imgCards.setImageResource(R.drawable.cards_white);
        paymentFragmentBinding.txtCards.setTextColor(getResources().getColor(R.color.white));

    }

    void deActivateCardsPayment() {
        paymentFragmentBinding.rlCardsChild.setBackgroundColor(getResources().getColor(R.color.white));
        paymentFragmentBinding.imgCards.setImageResource(R.drawable.cards);
        paymentFragmentBinding.txtCards.setTextColor(getResources().getColor(R.color.orange));

    }

    void activateNetBankingPayment() {
        paymentFragmentBinding.rlNetbank.setBackground(getResources().getDrawable(R.drawable.selected_payment));
        paymentFragmentBinding.imgNetBank.setImageResource(R.drawable.netbanking_white);
        paymentFragmentBinding.txtNetBanking.setTextColor(getResources().getColor(R.color.white));

    }

    void deActivateNetBankingPayment() {
        paymentFragmentBinding.rlNetbank.setBackgroundColor(getResources().getColor(R.color.white));
        paymentFragmentBinding.imgNetBank.setImageResource(R.drawable.netbanking);
        paymentFragmentBinding.txtNetBanking.setTextColor(getResources().getColor(R.color.white));

    }

    private void initRazorpay() {

        razorpay = new Razorpay(getActivity());
        razorpay.getPaymentMethods(new Razorpay.PaymentMethodsCallback() {
            @Override
            public void onPaymentMethodsReceived(String result) {
                Log.d("Result", "" + result);
                //inflateLists(result);
            }

            @Override
            public void onError(String error) {
                Log.d("Get Payment error", error);
            }
        });
    }
}
