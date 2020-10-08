package versatile.project.lauryl.adapter;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import versatile.project.lauryl.R;

import versatile.project.lauryl.databinding.SchedulePickUpLstItemBinding;
import versatile.project.lauryl.model.TopServicesDataItem;


public class SchedulePickUpAdapterJava extends RecyclerView.Adapter<SchedulePickUpAdapterJava.SchedulePickupViewHolder>{
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    private List<TopServicesDataItem> topServicesDataItems;
    private OnItemClickListener onItemClickListener;
    private int selectedPosition=-1;

    public SchedulePickUpAdapterJava(List<TopServicesDataItem> netBankingList,OnItemClickListener itemClickListener) {
        this.topServicesDataItems = netBankingList;
        this.onItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public SchedulePickupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SchedulePickUpLstItemBinding itemPaymentNetbankingBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.schedule_pick_up_lst_item, parent, false);
        return new SchedulePickupViewHolder(itemPaymentNetbankingBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SchedulePickupViewHolder holder, int position) {
            holder.bind(position,topServicesDataItems.get(position),onItemClickListener);
    }


    @Override
    public int getItemCount() {
        if(topServicesDataItems!=null) {
            return topServicesDataItems.size();
        }
        return 0;
    }


    class SchedulePickupViewHolder extends RecyclerView.ViewHolder {
        SchedulePickUpLstItemBinding itemPaymentNetbankingBinding;

        public SchedulePickupViewHolder(@NonNull SchedulePickUpLstItemBinding itemPaymentNetbankingBinding) {
            super(itemPaymentNetbankingBinding.getRoot());
            this.itemPaymentNetbankingBinding = itemPaymentNetbankingBinding;
        }

        public void bind(int position, TopServicesDataItem netBanking,OnItemClickListener clickListener) {
            if (selectedItems.get(position, false)) {
                itemPaymentNetbankingBinding.sevrcsLyot.setBackgroundResource(R.drawable.item_netbank_selected);
                itemPaymentNetbankingBinding.srvcTitleTxt.setTextColor(itemPaymentNetbankingBinding.getRoot().getContext().getResources().getColor(R.color.white));
                itemPaymentNetbankingBinding.srvcDescTxt.setTextColor(itemPaymentNetbankingBinding.getRoot().getContext().getResources().getColor(R.color.white));

            } else {
                itemPaymentNetbankingBinding.sevrcsLyot.setBackgroundResource(R.drawable.grey_corner_radius_stroke_bckgrnd);
                itemPaymentNetbankingBinding.srvcTitleTxt.setTextColor(itemPaymentNetbankingBinding.getRoot().getContext().getResources().getColor(R.color.orange));
                itemPaymentNetbankingBinding.srvcDescTxt.setTextColor(itemPaymentNetbankingBinding.getRoot().getContext().getResources().getColor(R.color.grey_color));

            }
//            Glide
//                    .with(itemPaymentNetbankingBinding.getRoot().getContext())
//                    .load(netBanking.getBankLogo())
//                    .centerCrop()
//                    .placeholder(R.drawable.rzp_loader_circle)
//                    .into(itemPaymentNetbankingBinding.iconBank);

            itemPaymentNetbankingBinding.srvcTitleTxt.setText(netBanking.getProductTitle());
            itemPaymentNetbankingBinding.srvcDescTxt.setText(netBanking.getDescription());
            itemPaymentNetbankingBinding.executePendingBindings();
            itemPaymentNetbankingBinding.getRoot().setOnClickListener(view -> {
                selectedPosition=position;
                if (selectedItems.get(selectedPosition, false)) {
                    selectedItems.delete(selectedPosition);
                   view.setSelected(false);
                }
                else {
                    selectedItems.put(selectedPosition, true);
                  view.setSelected(true);
                }
                notifyDataSetChanged();
                clickListener.onItemClicked(netBanking);
            });
        }
    }


    public interface OnItemClickListener {
        void onItemClicked(TopServicesDataItem netBanking);
    }
}
