package versatile.project.lauryl.pickup;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

import versatile.project.lauryl.R;
import versatile.project.lauryl.databinding.ItemPickupTimeGridBinding;
import versatile.project.lauryl.databinding.ListItemCnfSchedulePickupBinding;

public class CnfPickupTimeAdapter extends RecyclerView.Adapter<CnfPickupTimeAdapter.ViewHolder> {

    List<String> stringList;
    OnTimeClickListener onDateClickListener;
    Context context;
    private int selectedPosition = -1;

    public CnfPickupTimeAdapter(Context context, List<String> stringList, OnTimeClickListener onDateClickListener) {
        this.context = context;
        this.stringList = stringList;
        this.onDateClickListener = onDateClickListener;
    }

    @NonNull
    @Override
    public CnfPickupTimeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPickupTimeGridBinding listItemCnfSchedulePickupBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_pickup_time_grid, parent, false);
        return new ViewHolder(listItemCnfSchedulePickupBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CnfPickupTimeAdapter.ViewHolder holder, int position) {
        holder.bind(stringList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemPickupTimeGridBinding listItemCnfSchedulePickupBinding;

        public ViewHolder(@NonNull ItemPickupTimeGridBinding listItemCnfSchedulePickupBinding) {
            super(listItemCnfSchedulePickupBinding.getRoot());
            this.listItemCnfSchedulePickupBinding = listItemCnfSchedulePickupBinding;
        }

        public void bind(String data, int position) {
            listItemCnfSchedulePickupBinding.txtTime.setText(data);
            if (isCurrentTimeOrAfter(data)) {
                if (selectedPosition == position) {
                    listItemCnfSchedulePickupBinding.txtTime.setBackgroundResource(R.drawable.verify_otp_bckgrnd);
                    listItemCnfSchedulePickupBinding.txtTime.setTextColor(context.getResources().getColor(R.color.white));

                } else {
                    listItemCnfSchedulePickupBinding.txtTime.setBackgroundResource(R.drawable.white_bckgrnd_with_radius);
                    listItemCnfSchedulePickupBinding.txtTime.setTextColor(context.getResources().getColor(R.color.orange));
                }
            } else {
                listItemCnfSchedulePickupBinding.txtTime.setBackgroundResource(R.drawable.grey_bckgrnd_with_radius);
                listItemCnfSchedulePickupBinding.txtTime.setTextColor(Color.WHITE);
            }
            listItemCnfSchedulePickupBinding.linTime.setOnClickListener(view -> {
                selectedPosition = position;
                notifyDataSetChanged();
                onDateClickListener.onTimeClicked(selectedPosition >= 0 ? data : null);
            });

        }

    }

    interface OnTimeClickListener {
        void onTimeClicked(String time);
    }

    boolean isCurrentTimeOrAfter(String time) {

        try {
            String[] arrOfStr = time.toLowerCase().split("-");
            LocalTime currentTime = LocalTime.now();
            DateTimeFormatter currentDateFormat = DateTimeFormat.forPattern("hh aa");
            String str = currentTime.toString(currentDateFormat);
            DateTimeFormatter formatter = DateTimeFormat.forPattern("hh aa");
            LocalTime parseLocalTime = formatter.parseLocalTime(str);
            String tm = StringUtils.leftPad(arrOfStr[0], 4, "0");
            String myTime = tm.replaceAll("..", "$0 ");
            String[] words = myTime.split("\\s");
            String toSubmit = words[0] + " " + words[1].trim();
            LocalTime dateTime = formatter.parseLocalTime(toSubmit);
            return dateTime.isEqual(parseLocalTime) || dateTime.isAfter(parseLocalTime);
        } catch (Exception e) {

        }
        return false;
    }

    String getReadableMonth(String stringDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");
            LocalDate dateTime = formatter.parseLocalDate(stringDate);
            LocalDate.Property pDoW = dateTime.monthOfYear();
            String strST = pDoW.getAsShortText();
            return strST.toUpperCase();
        } catch (Exception e) {

        }
        return "";
    }

    String getReadableDate(String stringDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");
            LocalDate dateTime = formatter.parseLocalDate(stringDate);
            return String.valueOf(dateTime.getDayOfMonth());
        } catch (Exception e) {

        }
        return "";
    }
}
