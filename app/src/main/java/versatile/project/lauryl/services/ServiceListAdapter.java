package versatile.project.lauryl.services;

import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;
import versatile.project.lauryl.R;
import versatile.project.lauryl.databinding.ListItemTypeofserviceBinding;
import versatile.project.lauryl.databinding.SchedulePickUpLstItemBinding;
import versatile.project.lauryl.model.TopServicesDataItem;
import versatile.project.lauryl.utils.AllConstants;

public class ServiceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private SparseBooleanArray selectedItems;
    private List<ServiceModel> serviceModels;
    private final static int VIEW_TYPE_TYPEOFSERVICE=0;
    private final static int VIEW_TYPE_SERVICE=1;
    private int selectedPosition=-1;
    private OnItemClickListener onItemClickListener;
    public ServiceListAdapter(List<ServiceModel> serviceModels, OnItemClickListener itemClickListener,SparseBooleanArray selectedItems) {
        this.serviceModels = serviceModels;
        this.onItemClickListener = itemClickListener;
        this.selectedItems=selectedItems;
        Timber.e("selected items count : "+selectedItems.size());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==VIEW_TYPE_SERVICE) {
            SchedulePickUpLstItemBinding schedulePickUpLstItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.schedule_pick_up_lst_item,parent,false);
            return new ServiceViewHolder(schedulePickUpLstItemBinding);
        } else {
            ListItemTypeofserviceBinding typeofserviceBinding =  DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.list_item_typeofservice,parent,false);
            return new TypeOfServiceHolder(typeofserviceBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ServiceModel serviceModel=serviceModels.get(position);
        if(serviceModel.isServiceType()){
            TypeOfServiceHolder typeOfServiceHolder= (TypeOfServiceHolder) holder;
            typeOfServiceHolder.bind(serviceModel.getServiceTypeName());
        }else {
            ServiceViewHolder serviceViewHolder= (ServiceViewHolder) holder;
            serviceViewHolder.bind(position,serviceModel.getTopServicesDataItem(),onItemClickListener);
        }
    }

    @Override
    public int getItemCount() {
        return serviceModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);
        ServiceModel item = serviceModels.get(position);
        if(!item.isServiceType()) {
            return VIEW_TYPE_SERVICE;
        } else {
            return VIEW_TYPE_TYPEOFSERVICE;
        }
    }

     class ServiceViewHolder extends RecyclerView.ViewHolder {
         SchedulePickUpLstItemBinding itemPaymentNetbankingBinding;
         private int selectedPosition = -1;

         public ServiceViewHolder(SchedulePickUpLstItemBinding itemPaymentNetbankingBinding) {
             super(itemPaymentNetbankingBinding.getRoot());
             this.itemPaymentNetbankingBinding=itemPaymentNetbankingBinding;

         }
         public void bind(int position, TopServicesDataItem netBanking, OnItemClickListener clickListener) {
             if (selectedItems.get(position, false)) {
                 itemPaymentNetbankingBinding.sevrcsLyot.setBackgroundResource(R.drawable.item_netbank_selected);
                 itemPaymentNetbankingBinding.srvcTitleTxt.setTextColor(itemPaymentNetbankingBinding.getRoot().getContext().getResources().getColor(R.color.white));
                 itemPaymentNetbankingBinding.srvcDescTxt.setTextColor(itemPaymentNetbankingBinding.getRoot().getContext().getResources().getColor(R.color.white));
                 itemPaymentNetbankingBinding.txtPrice.setTextColor(itemPaymentNetbankingBinding.getRoot().getContext().getResources().getColor(R.color.white));

             } else {
                 itemPaymentNetbankingBinding.sevrcsLyot.setBackgroundResource(R.drawable.grey_corner_radius_stroke_bckgrnd);
                 itemPaymentNetbankingBinding.srvcTitleTxt.setTextColor(itemPaymentNetbankingBinding.getRoot().getContext().getResources().getColor(R.color.orange));
                 itemPaymentNetbankingBinding.srvcDescTxt.setTextColor(itemPaymentNetbankingBinding.getRoot().getContext().getResources().getColor(R.color.grey_color));
                 itemPaymentNetbankingBinding.txtPrice.setTextColor(itemPaymentNetbankingBinding.getRoot().getContext().getResources().getColor(R.color.colorPrimary));
             }
             Glide.with(itemPaymentNetbankingBinding.getRoot().getContext())
                    .load(netBanking.getImgUrl())
                    .centerCrop()
                    .placeholder(R.drawable.wash_fold_icon)
                    .into(itemPaymentNetbankingBinding.srvcsItemImg);
             if(TextUtils.equals(netBanking.getServiceType(), AllConstants.Services.SERVICE_TYPE_SUBSCRIPTION)) {
                 itemPaymentNetbankingBinding.txtPrice.setText("\u20B9 " + netBanking.getCostprice()+"/Month");
             }
             itemPaymentNetbankingBinding.srvcTitleTxt.setText(netBanking.getProductTitle());
             itemPaymentNetbankingBinding.srvcDescTxt.setText(netBanking.getDescription());
             itemPaymentNetbankingBinding.executePendingBindings();
             itemPaymentNetbankingBinding.getRoot().setOnClickListener(view -> {
                 selectedPosition = position;
                 if (selectedItems.get(selectedPosition, false)) {

                     selectedItems.delete(selectedPosition);
                     view.setSelected(false);
                 } else {
                     selectedItems.put(selectedPosition, true);
                     view.setSelected(true);
                 }
                 notifyDataSetChanged();
                 clickListener.onItemClicked(selectedItems);
             });
         }
     }

     class TypeOfServiceHolder extends RecyclerView.ViewHolder{
            ListItemTypeofserviceBinding listItemTypeofserviceBinding;
         public TypeOfServiceHolder(@NonNull ListItemTypeofserviceBinding typeofserviceBinding) {
             super(typeofserviceBinding.getRoot());
             this.listItemTypeofserviceBinding=typeofserviceBinding;
         }
         void bind(String typeOfService){
             listItemTypeofserviceBinding.txtTypeOfService.setText(typeOfService);
         }
     }
    public interface OnItemClickListener {
        void onItemClicked(SparseBooleanArray sparseBooleanArray);
    }
    public List<TopServicesDataItem> getTopServicesDataItems() {
        List<TopServicesDataItem> topServicesDataItemList=new ArrayList<>();
        for(ServiceModel serviceModel:serviceModels)
        {
            topServicesDataItemList.add(serviceModel.getTopServicesDataItem());
        }
        return topServicesDataItemList;
    }

}
