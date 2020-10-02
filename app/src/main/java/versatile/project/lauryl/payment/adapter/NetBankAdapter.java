package versatile.project.lauryl.payment.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import versatile.project.lauryl.R;
import versatile.project.lauryl.databinding.ItemPaymentNetbankingBinding;
import versatile.project.lauryl.payment.data.NetBanking;

public class NetBankAdapter extends RecyclerView.Adapter<NetBankAdapter.NetBankingViewHolder> {

    private List<NetBanking> netBankingList;

    public NetBankAdapter(List<NetBanking> netBankingList) {
        this.netBankingList = netBankingList;
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
        holder.bind(netBankingList.get(position));
    }

    @Override
    public int getItemCount() {
        return netBankingList.size();
    }

    class NetBankingViewHolder extends RecyclerView.ViewHolder {
        ItemPaymentNetbankingBinding itemPaymentNetbankingBinding;

        public NetBankingViewHolder(@NonNull ItemPaymentNetbankingBinding itemPaymentNetbankingBinding) {
            super(itemPaymentNetbankingBinding.getRoot());
            this.itemPaymentNetbankingBinding = itemPaymentNetbankingBinding;
        }

        public void bind(NetBanking netBanking) {
            itemPaymentNetbankingBinding.txtBankName.setText(netBanking.getBankName());
            itemPaymentNetbankingBinding.executePendingBindings();
        }
    }
}
