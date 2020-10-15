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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
import versatile.project.lauryl.payment.data.PaymentBaseShareData;
import versatile.project.lauryl.pickup.data.CnfPickupResponse;
import versatile.project.lauryl.pickup.data.PickUpSharedData;
import versatile.project.lauryl.pickup.viewmodel.CnfSchedulePickupViewModel;
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
    private int PAGE_SIZE = 200;
    private int CURRENT_PAGE = 1;
    List<String> rawDates = new ArrayList<>();
    List<String> uniqueDates = new ArrayList<>();
    CnfPickupDateAdapter adapter = null;
    CnfPickupTimeAdapter cnfPickupTimeAdapter = null;
    EndlessRecyclerViewScrollListener scrollListener;
    String selectedTime=null;

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
        resetState();
        loadDateTimeFromApi();
        if (adapter == null) {
            adapter = new CnfPickupDateAdapter(uniqueDates, this);
            cnfSchdulePckupFragmentBinding.horztlScrlVw.setAdapter(adapter);
        }
        cnfSchedulePickupViewModel.observePickupDateTimeSuccessResponse().observe(this, cnfPickupResponse -> {
            hideLoader();
            // horizontal RecyclerView
            cnfSchdulePckupFragmentBinding.horztlScrlVw.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            cnfSchdulePckupFragmentBinding.horztlScrlVw.setLayoutManager(mLayoutManager);
            cnfSchdulePckupFragmentBinding.horztlScrlVw.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.HORIZONTAL));
            cnfSchdulePckupFragmentBinding.horztlScrlVw.setItemAnimator(new DefaultItemAnimator());
            for (String s : cnfPickupResponse) {
                rawDates.add(s);
            }
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
            if(selectedTime!=null) {
                if (getActivity() instanceof HomeScreen) {
                    ((HomeScreen) getActivity()).selectPayment();
                }
                HomeNavigationController.getInstance(getActivity()).addPaymentFragment();
            }else{
                    Globals.Companion.showToastMsg(getActivity(),"Please select pickup time");
            }
        });
    }

    private void setUpTimeView() {
        int numberOfColumns = 3;
        cnfSchdulePckupFragmentBinding.gridPickUpTimer.setHasFixedSize(true);
        cnfSchdulePckupFragmentBinding.gridPickUpTimer.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        cnfSchdulePckupFragmentBinding.horztlScrlVw.addItemDecoration(new DividerItemDecoration(getActivity(), GridLayoutManager.HORIZONTAL));
        cnfSchdulePckupFragmentBinding.horztlScrlVw.setItemAnimator(new DefaultItemAnimator());

    }

    private void listenOnScroll() {
         scrollListener= new EndlessRecyclerViewScrollListener((LinearLayoutManager) mLayoutManager) {
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
        List<String> uniqueTimes = new ArrayList<>();
        List<String> timeSlotList = cnfSchedulePickupViewModel.getSlotForSelectedDate(date);
        Set<String> uniqueTimeSet = new LinkedHashSet<>(timeSlotList);
        for (String s : uniqueTimeSet) {
            uniqueTimes.add(s);
        }
        cnfPickupTimeAdapter = new CnfPickupTimeAdapter(getActivity(), uniqueTimes, this);
        cnfSchdulePckupFragmentBinding.gridPickUpTimer.setAdapter(cnfPickupTimeAdapter);
    }


    @Override
    public void onTimeClicked(String time) {
selectedTime=time;
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
    void resetState(){
        selectedTime=null;
        adapter=null;
        CURRENT_PAGE=1;
        isLastPage=false;
        rawDates.clear();
        uniqueDates.clear();
        if(scrollListener!=null){
            scrollListener.resetState();
        }
    }
}