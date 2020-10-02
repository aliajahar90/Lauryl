package versatile.project.lauryl.payment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.razorpay.Razorpay;

import org.json.JSONObject;

import java.util.List;

import versatile.project.lauryl.R;
import versatile.project.lauryl.base.BaseBinding;
import versatile.project.lauryl.databinding.PaymentFragmentBinding;
import versatile.project.lauryl.payment.adapter.NetBankAdapter;
import versatile.project.lauryl.payment.data.NetBanking;
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
    List<NetBanking> netBankingList;
    public static int activePaymentType = PaymentTypeUpi;

    public static PaymentFragment newInstance(int viewType) {
        PaymentFragment paymentFragment = new PaymentFragment();
        try {
            paymentBaseShareData = new Gson().fromJson((String) paymentFragment.getArguments().get(AllConstants.Payment.PaymentData), PaymentBaseShareData.class);
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
        createWebView();
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
                initNetBankingService();
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
        paymentViewModel.getValidPaymentMethods();
        controlViewVisibility();
        onClicks();


    }

    void controlViewVisibility() {
        paymentViewModel.isUpiPaymentSupported().observe(this, aBoolean -> {
            if (!aBoolean) {
                paymentFragmentBinding.rlUpi.setVisibility(View.GONE);
            }
        });
        paymentViewModel.isCardPaymentSupported().observe(this, aBoolean -> {
            if (!aBoolean) {
                paymentFragmentBinding.rlCards.setVisibility(View.GONE);
            }
        });
        paymentViewModel.isNetBankingPaymentSupported().observe(this, aBoolean -> {
            if (!aBoolean) {
                paymentFragmentBinding.rlNetbank.setVisibility(View.GONE);
            }
        });
        paymentViewModel.isSBINetBankingSupported().observe(this, aBoolean -> {
            if (!aBoolean) {
                paymentFragmentBinding.paymentNetBank.rlSBIN.setVisibility(View.GONE);
            }
        });
        paymentViewModel.isHDFCNetBankingSupported().observe(this, aBoolean -> {
            if (!aBoolean) {
                paymentFragmentBinding.paymentNetBank.rlHDFC.setVisibility(View.GONE);
            }
        });
        paymentViewModel.isBBNetBankingSupported().observe(this, aBoolean -> {
            if (!aBoolean) {
                paymentFragmentBinding.paymentNetBank.iconBB.setVisibility(View.GONE);
            }
        });
        paymentViewModel.isICICINetBankingSupported().observe(this, aBoolean -> {
            if (!aBoolean) {
                paymentFragmentBinding.paymentNetBank.iconICIC.setVisibility(View.GONE);
            }
        });
        paymentViewModel.isCanaraNetBankingSupported().observe(this, aBoolean -> {
            if (!aBoolean) {
                paymentFragmentBinding.paymentNetBank.rlCANARA.setVisibility(View.GONE);
            }
        });
        paymentViewModel.isKotakNetBankingSupported().observe(this, aBoolean -> {
            if (!aBoolean) {
                paymentFragmentBinding.paymentNetBank.rlKotak.setVisibility(View.GONE);
            }
        });
        paymentViewModel.getNetBankingList().observe(this, netBankings -> {
            netBankingList = netBankings;
        });
        paymentViewModel.onSwitchPaymentView().observe(this, integer -> {
            switch (integer) {
                case AllConstants.Payment.PaymentViewOptions:
                    paymentFragmentBinding.rlOptionPage.setVisibility(View.VISIBLE);
                    paymentFragmentBinding.wvCheckout.setVisibility(View.GONE);
                    break;
                case AllConstants.Payment.PaymentViewCheckout:
                    paymentFragmentBinding.wvCheckout.setVisibility(View.VISIBLE);
                    paymentFragmentBinding.rlOptionPage.setVisibility(View.GONE);
                    break;

            }
        });
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
        paymentFragmentBinding.rlPaymentButton.setOnClickListener(view -> {
            switch (activePaymentType) {
                case PaymentTypeUpi:
                    processUpiService();
                    break;
                case PaymentTypeCards:
                case PaymentTypeNetBanking:
                    break;
            }
        });
    }

    @Override
    protected void initializeViewModel() {
        initRazorpay();
        paymentViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new PaymentViewModel(razorpay);
            }
        }).get(PaymentViewModel.class);
    }

    void activateUpiPayment() {
        activePaymentType = PaymentFragment.PaymentTypeUpi;
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
        activePaymentType = PaymentFragment.PaymentTypeCards;
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
        activePaymentType = PaymentFragment.PaymentTypeNetBanking;
        paymentFragmentBinding.rlNetbank.setBackground(getResources().getDrawable(R.drawable.selected_payment));
        paymentFragmentBinding.imgNetBank.setImageResource(R.drawable.netbanking_white);
        paymentFragmentBinding.txtNetBanking.setTextColor(getResources().getColor(R.color.white));

    }

    void deActivateNetBankingPayment() {
        paymentFragmentBinding.rlNetbank.setBackgroundColor(getResources().getColor(R.color.white));
        paymentFragmentBinding.imgNetBank.setImageResource(R.drawable.netbanking);
        paymentFragmentBinding.txtNetBanking.setTextColor(getResources().getColor(R.color.orange));

    }

    private void initRazorpay() {

        razorpay = new Razorpay(getActivity(), "rzp_live_uCuH3yqyHZjoYj");

    }

    private void createWebView() {
        razorpay.setWebView(paymentFragmentBinding.wvCheckout);
    }

    private void initNetBankingService() {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        paymentFragmentBinding.paymentNetBank.listBanks.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        paymentFragmentBinding.paymentNetBank.listBanks.setLayoutManager(layoutManager);
        NetBankAdapter mAdapter = new NetBankAdapter(netBankingList);
        paymentFragmentBinding.paymentNetBank.listBanks.setItemAnimator(new DefaultItemAnimator());
        paymentFragmentBinding.paymentNetBank.listBanks.setAdapter(mAdapter);
        // mAdapter.notifyDataSetChanged();
    }

    private void processUpiService() {
        paymentViewModel.validateVPA(paymentFragmentBinding.paymentUPI.inputUPI.getText().toString());
        setVpaValidationObserver();
    }

    private void setVpaValidationObserver() {
        paymentViewModel.onVpaValidationSuccess().observe(this, aBoolean -> {
            if (aBoolean) {
                try {
                    JSONObject payload = new JSONObject("{currency: 'INR'}");
                    payload.put("amount", "100");
                    payload.put("contact", "9999999999");
                    payload.put("email", "customer@name.com");
                    payload.put("order_id", "order_FkB8IeJE6PpkDe");
                    payload.put("method", "upi");
                    payload.put("payment_capture", "1");
                    payload.put("vpa", paymentFragmentBinding.paymentUPI.inputUPI.getText().toString());
                    paymentViewModel.processPayment(payload);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getActivity(), "Entered Vpa not valid", Toast.LENGTH_SHORT).show();
            }
        });
        paymentViewModel.onVpaValidationFailed().observe(this, errorMsg -> {
            Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
        });
    }
}
