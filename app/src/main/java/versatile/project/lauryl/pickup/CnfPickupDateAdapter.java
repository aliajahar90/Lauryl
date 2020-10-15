package versatile.project.lauryl.pickup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import versatile.project.lauryl.R;
import versatile.project.lauryl.databinding.ItemPaymentNetbankingBinding;
import versatile.project.lauryl.databinding.ListItemCnfSchedulePickupBinding;

public class CnfPickupDateAdapter  extends RecyclerView.Adapter<CnfPickupDateAdapter.ViewHolder> {

    List<String> stringList;
    OnDateClickListener onDateClickListener;
    private int selectedPosition = -1;

    public CnfPickupDateAdapter(List<String> stringList,OnDateClickListener onDateClickListener) {
        this.stringList = stringList;
        this.onDateClickListener=onDateClickListener;
    }

    @NonNull
    @Override
    public CnfPickupDateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemCnfSchedulePickupBinding listItemCnfSchedulePickupBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.list_item_cnf_schedule_pickup, parent, false);
        return new ViewHolder(listItemCnfSchedulePickupBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CnfPickupDateAdapter.ViewHolder holder, int position) {
        holder.bind(stringList.get(position),position);
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ListItemCnfSchedulePickupBinding listItemCnfSchedulePickupBinding;
        public ViewHolder(@NonNull ListItemCnfSchedulePickupBinding listItemCnfSchedulePickupBinding) {
            super(listItemCnfSchedulePickupBinding.getRoot());
            this.listItemCnfSchedulePickupBinding=listItemCnfSchedulePickupBinding;
        }
        public void bind(String data,int position){
            if (selectedPosition == position) {
                listItemCnfSchedulePickupBinding.linDate.setBackgroundColor(listItemCnfSchedulePickupBinding.getRoot().getResources().getColor(R.color.colorPrimary));
                listItemCnfSchedulePickupBinding.textDay.setTextColor(listItemCnfSchedulePickupBinding.getRoot().getResources().getColor(R.color.white));
                listItemCnfSchedulePickupBinding.txtMonth.setTextColor(listItemCnfSchedulePickupBinding.getRoot().getResources().getColor(R.color.white));
                listItemCnfSchedulePickupBinding.txtDate.setTextColor(listItemCnfSchedulePickupBinding.getRoot().getResources().getColor(R.color.white));
            } else {
                listItemCnfSchedulePickupBinding.linDate.setBackgroundColor(listItemCnfSchedulePickupBinding.getRoot().getResources().getColor(R.color.white));
                listItemCnfSchedulePickupBinding.textDay.setTextColor(listItemCnfSchedulePickupBinding.getRoot().getResources().getColor(R.color.orange));
                listItemCnfSchedulePickupBinding.txtMonth.setTextColor(listItemCnfSchedulePickupBinding.getRoot().getResources().getColor(R.color.orange));
                listItemCnfSchedulePickupBinding.txtDate.setTextColor(listItemCnfSchedulePickupBinding.getRoot().getResources().getColor(R.color.orange));
            }
            listItemCnfSchedulePickupBinding.textDay.setText(getReadableDay(data));
            listItemCnfSchedulePickupBinding.txtMonth.setText(getReadableMonth(data));
            listItemCnfSchedulePickupBinding.txtDate.setText(getReadableDate(data));
            listItemCnfSchedulePickupBinding.linDate.setOnClickListener(view -> {
                selectedPosition=position;
                notifyDataSetChanged();
                onDateClickListener.onDateClicked(data);
            });

        }

    }

    interface OnDateClickListener{
        void onDateClicked(String date);
    }
    String getReadableDay(String stringDate){
        try {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");
            LocalDate dateTime = formatter.parseLocalDate(stringDate);
            LocalDate.Property pDoW = dateTime.dayOfWeek();
            String strST = pDoW.getAsShortText();
            return strST.toUpperCase();
        }catch (Exception e){

        }
        return "";
    }
    String getReadableMonth(String stringDate){
        try {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");
            LocalDate dateTime = formatter.parseLocalDate(stringDate);
            LocalDate.Property pDoW = dateTime.monthOfYear();
            String strST = pDoW.getAsShortText();
            return strST.toUpperCase();
        }catch (Exception e){

        }
        return "";
    }
    String getReadableDate(String stringDate){
        try {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");
            LocalDate dateTime = formatter.parseLocalDate(stringDate);
            return String.valueOf(dateTime.getDayOfMonth());
        }catch (Exception e){

        }
        return "";
    }
}
