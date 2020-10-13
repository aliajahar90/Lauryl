package versatile.project.lauryl.pickup;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import versatile.project.lauryl.R;
import versatile.project.lauryl.application.MyApplication;
import versatile.project.lauryl.base.BaseActivity;
import versatile.project.lauryl.base.BaseBinding;
import versatile.project.lauryl.databinding.CnfSchdulePckupFragmentBinding;
import versatile.project.lauryl.payment.data.PaymentBaseShareData;
import versatile.project.lauryl.pickup.data.CnfPickupResponse;
import versatile.project.lauryl.pickup.data.PickUpSharedData;
import versatile.project.lauryl.pickup.viewmodel.CnfSchedulePickupViewModel;
import versatile.project.lauryl.screens.HomeScreen;
import versatile.project.lauryl.utils.AllConstants;
import versatile.project.lauryl.utils.Globals;

public class CnfSchedulePckUpFragment extends BaseBinding<CnfSchedulePickupViewModel, CnfSchdulePckupFragmentBinding> {
    private CnfSchdulePckupFragmentBinding cnfSchdulePckupFragmentBinding;
    private CnfSchedulePickupViewModel cnfSchedulePickupViewModel;
    private PickUpSharedData pickUpSharedData;

    public static CnfSchedulePckUpFragment newInstance() {
        CnfSchedulePckUpFragment cnfSchedulePckUpFragment = new CnfSchedulePckUpFragment();
        try {
            cnfSchedulePckUpFragment.pickUpSharedData = new Gson().fromJson((String) cnfSchedulePckUpFragment.getArguments().get(AllConstants.PickUp.PickUpData), PickUpSharedData.class);
        } catch (Exception e) {
            Log.d("Error", "Null Pointer");
        }
        return cnfSchedulePckUpFragment;
    }

    @Override
    protected void initializeViewModel() {
        cnfSchedulePickupViewModel = new ViewModelProvider(this).get(CnfSchedulePickupViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        cnfSchdulePckupFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.cnf_schdule_pckup_fragment, container, false);
        return cnfSchdulePckupFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((HomeScreen) getActivity()).selectCnfPckUp();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showLoader();
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("currentPage",1);
        jsonObject.addProperty("pageSize",10);
        jsonObject.add("search", new JsonArray());
        jsonObject.addProperty("sortBy","createdAt");
        jsonObject.addProperty("sort","ASC");
        cnfSchedulePickupViewModel.getPickUpDateTime(((MyApplication)getActivity().getApplicationContext()).getAccessToken(),jsonObject);
        cnfSchedulePickupViewModel.observePickupDateTimeSuccessResponse().observe(this, cnfPickupResponse -> {
            hideLoader();

        });
        cnfSchedulePickupViewModel.observePickupDateTimeErrorResponse().observe(this, error -> {
            hideLoader();
           // Globals.Companion.showPopoUpDialog(getActivity(),"Error","Error fetching date and time");
        });
    }

    void showLoader(){
        if(getActivity() instanceof BaseActivity)
            ((BaseActivity)getActivity()).showLoading();
    }
    void hideLoader(){
        if(getActivity() instanceof BaseActivity)
            ((BaseActivity)getActivity()).hideLoading();
    }
}