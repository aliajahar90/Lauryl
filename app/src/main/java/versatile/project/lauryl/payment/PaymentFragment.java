package versatile.project.lauryl.payment;


import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.logging.Logger;

import versatile.project.lauryl.R;
import versatile.project.lauryl.base.BaseActivity;
import versatile.project.lauryl.base.BaseBinding;
import versatile.project.lauryl.base.DeferredFragmentTransaction;
import versatile.project.lauryl.base.HomeNavigationController;
import versatile.project.lauryl.databinding.PaymentFragmentBinding;
import versatile.project.lauryl.payment.adapter.NetBankAdapter;
import versatile.project.lauryl.payment.data.NetBanking;
import versatile.project.lauryl.payment.data.PaymentBaseShareData;
import versatile.project.lauryl.payment.util.CardFormattingTextWatcher;
import versatile.project.lauryl.payment.util.PaymentDefferedFragmentTransaction;
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
    private NetBanking activeBankForCheckout = null;
    public static final String TAG = PaymentFragment.class.getName();


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
        initRazorpay();
        createWebView();
        paymentFragmentBinding.setViewmodel(paymentViewModel);
        paymentViewModel.setRazorpay(razorpay);
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
        showLoading();
        paymentViewModel.getValidPaymentMethods();
        controlViewVisibility();
        onClicks();
        paymentViewModel.getPaymentMethodError().observe(this, s -> {
            hideLoading();
            getMyActivity().shout(s);
        });
        paymentViewModel.getPaymentSuccess().observe(this, paymentSuccess -> {
            hideLoading();
            HomeNavigationController.getInstance(getActivity()).addPaymentSuccessFragment();
        });
        paymentViewModel.getPaymentError().observe(this, paymentError -> {
            hideLoading();
            if (paymentError.getCode() == -1) {
                Log.d("Payment", "checksum error");
                HomeNavigationController.getInstance(getActivity()).addPaymentErrorFragment();
            } else {
                Log.d("Payment", "payment Erro");
                HomeNavigationController.getInstance(getActivity()).addPaymentErrorFragment();
            }
        });

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
        paymentViewModel.onSwitchPaymentViewWebCheckout().observe(this, aBoolean -> {
            if(aBoolean){
                paymentFragmentBinding.wvCheckout.setVisibility(View.VISIBLE);
                paymentFragmentBinding.rlOptionPage.setVisibility(View.GONE);
            }
        });
        paymentViewModel.onSwitchPaymentViewDefault().observe(this, aBoolean -> {
            if(aBoolean){
                paymentFragmentBinding.rlOptionPage.setVisibility(View.VISIBLE);
                paymentFragmentBinding.wvCheckout.setVisibility(View.GONE);
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
                    processCardPaymentService();
                    break;
                case PaymentTypeNetBanking:
                    processNetBankPaymentService();
                    break;
            }
        });
        paymentFragmentBinding.paymentCard.inputCardNumber.addTextChangedListener(new CardFormattingTextWatcher(paymentFragmentBinding.paymentCard.inputCardNumber, new CardFormattingTextWatcher.CardType() {
            @Override
            public void setCardType(String s) {
                String cardType = "";
                if (paymentFragmentBinding.paymentCard.txtCardType.toString().length() == 0) {
                    {
                        cardType = razorpay.getCardNetwork(s);
                    }
                    paymentFragmentBinding.paymentCard.txtCardType.setText(cardType);
                }
            }

            @Override
            public void resetCardType() {
                paymentFragmentBinding.paymentCard.txtCardType.setText("");
            }
        }));
        hotBankOnclick();

    }

    private void hotBankOnclick() {
        paymentFragmentBinding.paymentNetBank.rlSBIN.setOnClickListener(view -> {
            switchToSelectedBackground(paymentFragmentBinding.paymentNetBank.rlSBIN);
            switchToDefaultdBackground(paymentFragmentBinding.paymentNetBank.rlHDFC);
            switchToDefaultdBackground(paymentFragmentBinding.paymentNetBank.rlBB);
            switchToDefaultdBackground(paymentFragmentBinding.paymentNetBank.rlICIC);
            switchToDefaultdBackground(paymentFragmentBinding.paymentNetBank.rlCANARA);
            switchToDefaultdBackground(paymentFragmentBinding.paymentNetBank.rlKotak);
            NetBanking netBanking = new NetBanking();
            netBanking.setBankCode(AllConstants.Payment.HotBanks.SBIN);
            netBanking.setBankName("SBI");
            activeBankForCheckout = netBanking;

        });
        paymentFragmentBinding.paymentNetBank.rlHDFC.setOnClickListener(view -> {
            switchToDefaultdBackground(paymentFragmentBinding.paymentNetBank.rlSBIN);
            switchToSelectedBackground(paymentFragmentBinding.paymentNetBank.rlHDFC);
            switchToDefaultdBackground(paymentFragmentBinding.paymentNetBank.rlBB);
            switchToDefaultdBackground(paymentFragmentBinding.paymentNetBank.rlICIC);
            switchToDefaultdBackground(paymentFragmentBinding.paymentNetBank.rlCANARA);
            switchToDefaultdBackground(paymentFragmentBinding.paymentNetBank.rlKotak);
            NetBanking netBanking = new NetBanking();
            netBanking.setBankCode(AllConstants.Payment.HotBanks.HDFC);
            netBanking.setBankName("HDFC");
            activeBankForCheckout = netBanking;

        });
        paymentFragmentBinding.paymentNetBank.rlBB.setOnClickListener(view -> {
            switchToDefaultdBackground(paymentFragmentBinding.paymentNetBank.rlSBIN);
            switchToDefaultdBackground(paymentFragmentBinding.paymentNetBank.rlHDFC);
            switchToSelectedBackground(paymentFragmentBinding.paymentNetBank.rlBB);
            switchToDefaultdBackground(paymentFragmentBinding.paymentNetBank.rlICIC);
            switchToDefaultdBackground(paymentFragmentBinding.paymentNetBank.rlCANARA);
            switchToDefaultdBackground(paymentFragmentBinding.paymentNetBank.rlKotak);
            NetBanking netBanking = new NetBanking();
            netBanking.setBankCode(AllConstants.Payment.HotBanks.BARB_R);
            netBanking.setBankName("Bank of Baroda");
            activeBankForCheckout = netBanking;

        });
        paymentFragmentBinding.paymentNetBank.rlICIC.setOnClickListener(view -> {
            switchToDefaultdBackground(paymentFragmentBinding.paymentNetBank.rlSBIN);
            switchToDefaultdBackground(paymentFragmentBinding.paymentNetBank.rlHDFC);
            switchToDefaultdBackground(paymentFragmentBinding.paymentNetBank.rlBB);
            switchToSelectedBackground(paymentFragmentBinding.paymentNetBank.rlICIC);
            switchToDefaultdBackground(paymentFragmentBinding.paymentNetBank.rlCANARA);
            switchToDefaultdBackground(paymentFragmentBinding.paymentNetBank.rlKotak);
            NetBanking netBanking = new NetBanking();
            netBanking.setBankCode(AllConstants.Payment.HotBanks.ICIC);
            netBanking.setBankName("ICICI");
            activeBankForCheckout = netBanking;

        });
        paymentFragmentBinding.paymentNetBank.rlCANARA.setOnClickListener(view -> {
            switchToDefaultdBackground(paymentFragmentBinding.paymentNetBank.rlSBIN);
            switchToDefaultdBackground(paymentFragmentBinding.paymentNetBank.rlHDFC);
            switchToDefaultdBackground(paymentFragmentBinding.paymentNetBank.rlBB);
            switchToDefaultdBackground(paymentFragmentBinding.paymentNetBank.rlICIC);
            switchToSelectedBackground(paymentFragmentBinding.paymentNetBank.rlCANARA);
            switchToDefaultdBackground(paymentFragmentBinding.paymentNetBank.rlKotak);
            NetBanking netBanking = new NetBanking();
            netBanking.setBankCode(AllConstants.Payment.HotBanks.CNRB);
            netBanking.setBankName("Canara Bank");
            activeBankForCheckout = netBanking;

        });
        paymentFragmentBinding.paymentNetBank.rlKotak.setOnClickListener(view -> {
            switchToDefaultdBackground(paymentFragmentBinding.paymentNetBank.rlSBIN);
            switchToDefaultdBackground(paymentFragmentBinding.paymentNetBank.rlHDFC);
            switchToDefaultdBackground(paymentFragmentBinding.paymentNetBank.rlBB);
            switchToDefaultdBackground(paymentFragmentBinding.paymentNetBank.rlICIC);
            switchToDefaultdBackground(paymentFragmentBinding.paymentNetBank.rlCANARA);
            switchToSelectedBackground(paymentFragmentBinding.paymentNetBank.rlKotak);
            NetBanking netBanking = new NetBanking();
            netBanking.setBankCode(AllConstants.Payment.HotBanks.KKBK);
            netBanking.setBankName("Kotak");
            activeBankForCheckout = netBanking;

        });

    }

    void switchToSelectedBackground(View view) {
        view.setBackgroundResource(R.drawable.circle_primary_stroke_bckgrnd);
    }

    void switchToDefaultdBackground(View view) {
        view.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    protected void initializeViewModel() {
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
        NetBankAdapter mAdapter = new NetBankAdapter(netBankingList, netBanking -> {
            activeBankForCheckout = netBanking;
        });
        paymentFragmentBinding.paymentNetBank.listBanks.setItemAnimator(new DefaultItemAnimator());
        paymentFragmentBinding.paymentNetBank.listBanks.setAdapter(mAdapter);
        // mAdapter.notifyDataSetChanged();
    }

    private void processUpiService() {
        showLoading();
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
            hideLoading();
            Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
        });
    }

    private void processCardPaymentService() {
        showLoading();
        paymentViewModel.validateCard(paymentFragmentBinding.paymentCard.inputCardNumber.getText().toString(),
                paymentFragmentBinding.paymentCard.inputName.getText().toString(),
                paymentFragmentBinding.paymentCard.inputMonth.getText().toString(),
                paymentFragmentBinding.paymentCard.inputYear.getText().toString(),
                paymentFragmentBinding.paymentCard.inputCvv.getText().toString());
        setCardValidationObserver();
    }

    private void setCardValidationObserver() {
        paymentViewModel.getCardPaymentValidationSuccess().observe(this, aBoolean -> {
            if (aBoolean) {
                try {
                    JSONObject data = new JSONObject("{currency: 'INR'}");
                    data.put("amount", 1 * 100);
                    data.put("email", "gaurav.kumar@example.com");
                    data.put("contact", "9123456789");
                    data.put("method", "card");
                    data.put("card[name]", paymentFragmentBinding.paymentCard.inputName.getText().toString());
                    data.put("card[number]", paymentFragmentBinding.paymentCard.inputCardNumber.getText().toString());
                    data.put("card[expiry_month]", paymentFragmentBinding.paymentCard.inputMonth.getText().toString());
                    data.put("card[expiry_year]", paymentFragmentBinding.paymentCard.inputYear.getText().toString());
                    data.put("card[cvv]", paymentFragmentBinding.paymentCard.inputCvv.getText().toString());
                    paymentViewModel.processPayment(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        paymentViewModel.getCardPaymentValidationError().observe(this, msg -> {
            hideLoading();
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        });
    }

    private void processNetBankPaymentService() {
        if (activeBankForCheckout != null) {
            showLoading();
            try {
                JSONObject data = new JSONObject("{currency: 'INR'}");
                data.put("amount", 1 * 100);
                data.put("email", "gaurav.kumar@example.com");
                data.put("contact", "9123456789");
                data.put("method", "netbanking");
                data.put("bank", activeBankForCheckout.getBankCode());
                paymentViewModel.processPayment(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void showLoading() {
        if (getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).showLoading();
    }

    private void hideLoading() {
        ((BaseActivity) getActivity()).hideLoading();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        while (!HomeNavigationController.getInstance(getActivity()).getDeferredFragmentTransactions().isEmpty()) {
            HomeNavigationController.getInstance(getActivity()).getDeferredFragmentTransactions().remove().commit();
        }
    }

    @Override
    public void onDestroyView() {
        if (paymentFragmentBinding.wvCheckout.getVisibility() == View.VISIBLE) {
            PaymentDefferedFragmentTransaction defferedFragmentTransaction = new PaymentDefferedFragmentTransaction() {
                @Override
                public void commit() {
                    HomeNavigationController.getInstance(getActivity()).addPaymentErrorFragment();
                }
            };
            PaymentBaseShareData.PaymentError paymentError = new PaymentBaseShareData.PaymentError();
            paymentError.setCode(-1);
            paymentError.setDescription("User left the payment");
            paymentError.setPaymentData(null);
            defferedFragmentTransaction.setPaymentError(paymentError);
            Queue<DeferredFragmentTransaction> paymentDefferedFragmentTransactionQueue=new ArrayDeque<>();
            paymentDefferedFragmentTransactionQueue.add(defferedFragmentTransaction);
           HomeNavigationController.getInstance(getActivity()).setDeferredFragmentTransactions(paymentDefferedFragmentTransactionQueue);
        }
        Log.d("Payment","OnDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d("Payment","OnDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d("Payment","OnDestroyDetach");
        super.onDetach();
    }
}
