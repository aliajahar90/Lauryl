package versatile.project.lauryl.payment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import versatile.project.lauryl.R;
import versatile.project.lauryl.base.BaseBinding;
import versatile.project.lauryl.databinding.FragmentPaymentUpiBinding;
import versatile.project.lauryl.payment.viewModel.UpiPaymentViewModel;

public class UpiPaymentFragment extends BaseBinding<UpiPaymentViewModel, FragmentPaymentUpiBinding> {
    private FragmentPaymentUpiBinding fragmentPaymentUpiBinding;
    private UpiPaymentViewModel upiPaymentViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentPaymentUpiBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment_upi,container,false);
        return fragmentPaymentUpiBinding.getRoot();

    }

    @Override
    protected void initializeViewModel() {
        upiPaymentViewModel= new ViewModelProvider(this).get(UpiPaymentViewModel.class);
    }
}
