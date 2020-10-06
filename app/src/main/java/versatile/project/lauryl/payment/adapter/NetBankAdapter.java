package versatile.project.lauryl.payment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import versatile.project.lauryl.R;
import versatile.project.lauryl.databinding.ItemPaymentNetbankingBinding;
import versatile.project.lauryl.payment.data.NetBanking;

public class NetBankAdapter extends RecyclerView.Adapter<NetBankAdapter.NetBankingViewHolder> {

    private List<NetBanking> netBankingList;
    private OnItemClickListener onItemClickListener;
    private int selectedPosition=-1;

    public NetBankAdapter(List<NetBanking> netBankingList, OnItemClickListener itemClickListener) {
        this.netBankingList = netBankingList;
        this.onItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public NetBankingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPaymentNetbankingBinding itemPaymentNetbankingBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_payment_netbanking, parent, false);
        return new NetBankingViewHolder(itemPaymentNetbankingBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NetBankingViewHolder holder, int position) {
        holder.bind(position, netBankingList.get(position), onItemClickListener);
    }

    @Override
    public int getItemCount() {
        if(netBankingList!=null) {
            return netBankingList.size();
        }
        return 0;
    }

    class NetBankingViewHolder extends RecyclerView.ViewHolder {
        ItemPaymentNetbankingBinding itemPaymentNetbankingBinding;

        public NetBankingViewHolder(@NonNull ItemPaymentNetbankingBinding itemPaymentNetbankingBinding) {
            super(itemPaymentNetbankingBinding.getRoot());
            this.itemPaymentNetbankingBinding = itemPaymentNetbankingBinding;
        }

        public void bind(int position, NetBanking netBanking, OnItemClickListener clickListener) {
            if (selectedPosition == position) {
                itemPaymentNetbankingBinding.rlBankName.setBackgroundResource(R.drawable.item_netbank_selected);
                itemPaymentNetbankingBinding.txtBankName.setTextColor(itemPaymentNetbankingBinding.getRoot().getContext().getResources().getColor(R.color.white));
            } else {
                itemPaymentNetbankingBinding.rlBankName.setBackgroundResource(R.drawable.item_netbank_box);
                itemPaymentNetbankingBinding.txtBankName.setTextColor(itemPaymentNetbankingBinding.getRoot().getContext().getResources().getColor(R.color.dark_grey));

            }
            Glide
                    .with(itemPaymentNetbankingBinding.getRoot().getContext())
                    .load(netBanking.getBankLogo())
                    .centerCrop()
                    .placeholder(R.drawable.rzp_loader_circle)
                    .into(itemPaymentNetbankingBinding.iconBank);

            itemPaymentNetbankingBinding.txtBankName.setText(netBanking.getBankName());
            itemPaymentNetbankingBinding.executePendingBindings();
            itemPaymentNetbankingBinding.getRoot().setOnClickListener(view -> {
                selectedPosition=position;
                notifyDataSetChanged();
                clickListener.onItemClicked(netBanking);
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(NetBanking netBanking);
    }
}
