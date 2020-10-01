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
import versatile.project.lauryl.databinding.PaymentFragmentBinding;
import versatile.project.lauryl.payment.data.PaymentBaseShareData;
import versatile.project.lauryl.payment.viewModel.PaymentViewModel;
import versatile.project.lauryl.utils.AllConstants;

public  class PaymentFragment  extends BaseBinding<PaymentViewModel, PaymentFragmentBinding> {
    private PaymentFragmentBinding paymentFragmentBinding;
    private PaymentViewModel paymentViewModel;
    public static final int PaymentTypeUpi=1;
    public static final int PaymentTypeCards=2;
    public static final int PaymentTypeNetBanking=3;
    private static PaymentBaseShareData paymentBaseShareData;
    public static PaymentFragment newInstance(int viewType){
        PaymentFragment paymentFragment=new PaymentFragment();
        try {
            paymentBaseShareData = new Gson().fromJson((String) paymentFragment.getArguments().get(AllConstants.PaymentData), PaymentBaseShareData.class);
        }
        catch (Exception e){
            Log.d("Error","Null Pointer");
        }
        if(paymentBaseShareData==null) {
            paymentBaseShareData=new PaymentBaseShareData();
        }
        paymentBaseShareData.setViewType(viewType);
        return paymentFragment;
    }

    private PaymentFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        paymentFragmentBinding= DataBindingUtil.inflate(inflater,R.layout.payment_fragment,container,false);
        displayView(paymentBaseShareData.getViewType());
        paymentFragmentBinding.setViewmodel(paymentViewModel);
        return paymentFragmentBinding.getRoot();
    }

    private void displayView(int type){
        switch (type){
            case PaymentTypeCards:paymentFragmentBinding.paymentCard.getRoot().setVisibility(View.VISIBLE);
                paymentFragmentBinding.paymentNetBank.getRoot().setVisibility(View.GONE);
                paymentFragmentBinding.paymentUPI.getRoot().setVisibility(View.GONE);
                break;
            case PaymentTypeNetBanking: paymentFragmentBinding.paymentNetBank.getRoot().setVisibility(View.VISIBLE);
                paymentFragmentBinding.paymentCard.getRoot().setVisibility(View.GONE);
                paymentFragmentBinding.paymentUPI.getRoot().setVisibility(View.GONE);
                break;
            case PaymentTypeUpi:
            default:paymentFragmentBinding.paymentUPI.getRoot().setVisibility(View.VISIBLE);
                paymentFragmentBinding.paymentNetBank.getRoot().setVisibility(View.GONE);
                paymentFragmentBinding.paymentCard.getRoot().setVisibility(View.GONE);
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onClicks();


    }
    void onClicks(){
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
}
