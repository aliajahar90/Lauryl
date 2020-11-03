package versatile.project.lauryl.pickup;

import android.app.AlertDialog;
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
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import versatile.project.lauryl.R;
import versatile.project.lauryl.application.MyApplication;
import versatile.project.lauryl.base.BaseActivity;
import versatile.project.lauryl.base.BaseBinding;
import versatile.project.lauryl.base.HomeNavigationController;
import versatile.project.lauryl.databinding.CnfSchdulePckupFragmentBinding;
import versatile.project.lauryl.home.HomeFragment;
import versatile.project.lauryl.model.TopServicesDataItem;
import versatile.project.lauryl.model.address.AddressModel;
import versatile.project.lauryl.orders.create.model.CreateOrderData;
import versatile.project.lauryl.payment.data.PaymentBaseShareData;
import versatile.project.lauryl.pickup.data.CnfPickupResponse;
import versatile.project.lauryl.pickup.data.PickUpSharedData;
import versatile.project.lauryl.pickup.viewmodel.CnfSchedulePickupViewModel;
import versatile.project.lauryl.profile.data.GetProfileResponse;
import versatile.project.lauryl.screens.HomeScreen;
import versatile.project.lauryl.utils.AllConstants;
import versatile.project.lauryl.utils.EndlessRecyclerViewScrollListener;
import versatile.project.lauryl.utils.Globals;

public class CnfSchedulePckUpFragment extends BaseBinding<CnfSchedulePickupViewModel, CnfSchdulePckupFragmentBinding> implements CnfPickupDateAdapter.OnDateClickListener, CnfPickupTimeAdapter.OnTimeClickListener {
    private CnfSchdulePckupFragmentBinding cnfSchdulePckupFragmentBinding;
    private CnfSchedulePickupViewModel cnfSchedulePickupViewModel;
    private PickUpSharedData pickUpSharedData;
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGE_SIZE = 0;
    private int PAGE_SIZE = 400;
    private int CURRENT_PAGE = 1;
    List<String> rawDates = new ArrayList<>();
    List<String> uniqueDates = new ArrayList<>();
    CnfPickupDateAdapter adapter = null;
    CnfPickupTimeAdapter cnfPickupTimeAdapter = null;
    EndlessRecyclerViewScrollListener scrollListener;
    String selectedTime = null;
    String selectedDate = null;
    private Gson mGson;

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
        mGson=new Gson();
        resetState();
        loadDateTimeFromApi();
        if (adapter == null) {
            adapter = new CnfPickupDateAdapter(uniqueDates, this);
            cnfSchdulePckupFragmentBinding.horztlScrlVw.setAdapter(adapter);
            // horizontal RecyclerView
            cnfSchdulePckupFragmentBinding.horztlScrlVw.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            cnfSchdulePckupFragmentBinding.horztlScrlVw.setLayoutManager(mLayoutManager);
            cnfSchdulePckupFragmentBinding.horztlScrlVw.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.HORIZONTAL));
            cnfSchdulePckupFragmentBinding.horztlScrlVw.setItemAnimator(new DefaultItemAnimator());
        }
        cnfSchedulePickupViewModel.observePickupDateTimeSuccessResponse().observe(this, cnfPickupResponse -> {
            hideLoader();
            rawDates.addAll(cnfPickupResponse);
            Set<String> uniqueDateSet = new LinkedHashSet<>(rawDates);
            for (String s : uniqueDateSet) {
                //uniqueDates.clear();
                if (!uniqueDates.contains(s)) {
                    if (isTodayOrFuture(s)) {
                        uniqueDates.add(s);
                    }
                }
            }
            adapter.notifyDataSetChanged();
            //adapter.notifyItemRangeInserted(adapter.getItemCount(), uniqueDates.size() - 1);
            //((LinearLayoutManager) mLayoutManager).scrollToPositionWithOffset(0, adapter.getItemCount());

            listenOnScroll();

        });
        cnfSchedulePickupViewModel.observePickupDateTimeErrorResponse().observe(this, error -> {
            hideLoader();
            Globals.Companion.showPopoUpDialog(getActivity(), "Error", "Error fetching date and time");
        });

        setUpTimeView();
        cnfSchedulePickupViewModel.isLastItemReached().observe(this, aBoolean -> {
            if (aBoolean) {
                isLastPage = true;
            }
        });

        cnfSchdulePckupFragmentBinding.schdlePckUpBtn.setOnClickListener(view -> {
            if (selectedTime != null) {
                createOrderOnHoldOrder();
            } else {
                Globals.Companion.showToastMsg(getActivity(), "Please select pickup time");
            }
        });
        cnfSchedulePickupViewModel.getCreateOrderSuccessEvent().observe(this, it -> {
            if (getActivity() instanceof HomeScreen) {
                ((HomeScreen) getActivity()).selectPayment();
                HomeNavigationController.getInstance(getActivity()).addPaymentFragment();
            }
        });
        cnfSchedulePickupViewModel.getCreateOrderFailedEvent().observe(this, it->{
            new AlertDialog.Builder(getActivity())
                    .setTitle(getString(R.string.lauryl))
                    .setMessage(R.string.order_not_created)
                    .setPositiveButton("Ok", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .show();
        });

        return cnfSchdulePckupFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((HomeScreen) getActivity()).selectCnfPckUp();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void setUpTimeView() {
        int numberOfColumns = 3;
        cnfSchdulePckupFragmentBinding.gridPickUpTimer.setHasFixedSize(true);
        cnfSchdulePckupFragmentBinding.gridPickUpTimer.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        cnfSchdulePckupFragmentBinding.horztlScrlVw.addItemDecoration(new DividerItemDecoration(getActivity(), GridLayoutManager.HORIZONTAL));
        cnfSchdulePckupFragmentBinding.horztlScrlVw.setItemAnimator(new DefaultItemAnimator());

    }

    private void listenOnScroll() {
        scrollListener = new EndlessRecyclerViewScrollListener((LinearLayoutManager) mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (!isLastPage) {
                    CURRENT_PAGE += 1;
                    loadDateTimeFromApi();
                    Log.v("loadMore", "page" + page + "iten" + totalItemsCount);
                }
            }
        };
        cnfSchdulePckupFragmentBinding.horztlScrlVw.addOnScrollListener(scrollListener);
    }

    void loadDateTimeFromApi() {
        showLoader();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("currentPage", CURRENT_PAGE);
        jsonObject.addProperty("pageSize", PAGE_SIZE);
        jsonObject.add("search", new JsonArray());
        jsonObject.addProperty("sortBy", "createdAt");
        jsonObject.addProperty("sort", "ASC");
        cnfSchedulePickupViewModel.getPickUpDateTime(((MyApplication) getActivity().getApplicationContext()).getAccessToken(), jsonObject);
    }

    void showLoader() {
        if (getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).showLoading();
    }

    void hideLoader() {
        if (getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).hideLoading();
    }

    @Override
    public void onDateClicked(String date) {
        selectedDate=date;
        List<String> uniqueTimes = new ArrayList<>();
        List<String> timeSlotList = cnfSchedulePickupViewModel.getSlotForSelectedDate(date);
        Set<String> uniqueTimeSet = new LinkedHashSet<>(timeSlotList);
        for (String s : uniqueTimeSet) {
            uniqueTimes.add(s);
        }
        cnfPickupTimeAdapter = new CnfPickupTimeAdapter(getActivity(), uniqueTimes, this, date);
        cnfSchdulePckupFragmentBinding.gridPickUpTimer.setAdapter(cnfPickupTimeAdapter);
    }


    @Override
    public void onTimeClicked(String time) {
        if(time!=null) {
            selectedTime = time;
        }
    }

    boolean isTodayOrFuture(String stringDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");
            LocalDate dateToCompare = formatter.parseLocalDate(stringDate);
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter currentDateFormat = DateTimeFormat.forPattern("MM/dd/yyyy");
            String str = currentDate.toString(currentDateFormat);
            LocalDate localCurrentDate = formatter.parseLocalDate(str);
            return dateToCompare.isEqual(localCurrentDate) || dateToCompare.isAfter(localCurrentDate);
        } catch (Exception e) {

        }
        return false;
    }

    void resetState() {
        selectedTime = null;
        selectedDate=null;
        adapter = null;
        CURRENT_PAGE = 1;
        isLastPage = false;
        rawDates.clear();
        uniqueDates.clear();
        if (scrollListener != null) {
            scrollListener.resetState();
        }
    }

    void createOrderOnHoldOrder() {
        MyApplication myApplication=(MyApplication) getActivity().getApplicationContext();
        CreateOrderData.Details details = new CreateOrderData.Details();
        String currentDateTimeInMilis=getCurrentDateTime();
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("orderNumber",currentDateTimeInMilis);
        myApplication.setActiveSessionOrderNumber(mGson.toJson(jsonObject));
        GetProfileResponse getProfileResponse = mGson.fromJson(myApplication.getCreateOrderSerializdedProfile(), GetProfileResponse.class);
        AddressModel addressModel = mGson.fromJson(myApplication.getCreateOrderSerializdedAddressData(), AddressModel.class);
        List<TopServicesDataItem> topServicesDataItemList=mGson.fromJson(myApplication.getCreateOrderSerializedService(), new TypeToken<List<TopServicesDataItem>>(){}.getType());
        JsonObject pickupTimingJson=new JsonObject();
        pickupTimingJson.addProperty(AllConstants.Orders.pickupDate,selectedDate);
        pickupTimingJson.addProperty(AllConstants.Orders.pickupTime,selectedTime);
        myApplication.setActiveSessionPickupSlots(mGson.toJson(pickupTimingJson));
        List<String> localServiceList=new ArrayList<>();
        if(topServicesDataItemList!=null) {
            for (TopServicesDataItem item : topServicesDataItemList) {
                localServiceList.add(item.getVSku());
            }
        }
        CreateOrderData createOrderData=new CreateOrderData();
        //keeping static as of now
        details.setOrderNumber(currentDateTimeInMilis);
        details.setOrderTotal("100");
        details.setVAccountId(AllConstants.Orders.vAccountId);
        details.setMarketPlaceName(AllConstants.Orders.MARKET_PLACE_NAME);
        details.setOrderDateTime(currentDateTimeInMilis);
        details.setPaymentDateTime(getCurrentDateTime());
        details.setPaymentReceived(false);
        details.setShippingAddress1(addressModel!=null?addressModel.getAddresType():"");

        String shippingAddress2="";
        if(addressModel!=null){
            shippingAddress2=addressModel.getStreetName()+" "+addressModel.getPinCode();
        }
        details.setShippingAddress2(shippingAddress2);
        details.setShippingAddress3(addressModel!=null?addressModel.getLandmark():"");
        details.setShippingCity(addressModel!=null?addressModel.getCity():"");
        details.setShippingState(addressModel!=null?addressModel.getState():"");
        details.setShippingCountry(addressModel!=null?addressModel.getCountry():"");
        details.setPickupAddress1(addressModel!=null?addressModel.getAddresType():"");
        String pickupAddress2="";
        if(addressModel!=null){
            pickupAddress2=addressModel.getStreetName()+" "+addressModel.getPinCode();
        }
        details.setPickupAddress2(pickupAddress2);
        details.setPickupAddress3(addressModel!=null?addressModel.getLandmark():"");
        details.setPickupCountryCode("+91");
        details.setPickupCity(addressModel!=null?addressModel.getCity():"");
        details.setPickupState(addressModel!=null?addressModel.getState():"");
        details.setPickupCountry(addressModel!=null?addressModel.getCountry():"");
        details.setShippingPostCode(addressModel!=null?addressModel.getPinCode():"");
        details.setTransactionId("");
        details.setServiceList(localServiceList);
        details.setPhoneNumber(((MyApplication) getActivity().getApplicationContext()).getMobileNumber());
        details.setOrderStage("On Hold");
        if(getProfileResponse!=null && getProfileResponse.getProfileData()!=null) {
            details.setEmailId(getProfileResponse.getProfileData().getEmail());
        }else {
            details.setEmailId("");
        }
        details.setPickupDate(selectedDate);
        details.setPickupSlot(selectedTime);
        details.setLatitude(addressModel!=null?addressModel.getLatitude():"");
        details.setLongitude(addressModel!=null?addressModel.getLongitude():"");
        details.setSpecialInstructions(cnfSchdulePckupFragmentBinding.inputSpclInstruction.getText().toString());
        if(getProfileResponse!=null && getProfileResponse.getProfileData()!=null) {
            details.setBuyerName(getProfileResponse.getProfileData().getFirstName() + " " + getProfileResponse.getProfileData().getLastName());
        }
        else {
            details.setBuyerName("");
        }
        createOrderData.setDetails(details);
        cnfSchedulePickupViewModel.createOrderOnServerWithoutPayment(((MyApplication) getActivity().getApplicationContext()).getAccessToken(), createOrderData);
    }
    String getCurrentDateTime(){
        DateTime dateTime=DateTime.now();
        return String.valueOf(dateTime.getMillis());
    }
}