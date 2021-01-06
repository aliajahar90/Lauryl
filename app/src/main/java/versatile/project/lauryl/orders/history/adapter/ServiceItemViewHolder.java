package versatile.project.lauryl.orders.history.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;

import versatile.project.lauryl.R;
import versatile.project.lauryl.orders.history.model.ServiceItemType;

public class ServiceItemViewHolder extends ChildViewHolder {

  private AppCompatTextView artistName;
  private AppCompatTextView textPrice;

  public ServiceItemViewHolder(View itemView) {
    super(itemView);
    artistName = itemView.findViewById(R.id.txtItemNameQuantity);
    textPrice=itemView.findViewById(R.id.txtPrice);
  }
  public void bind(ServiceItemType recipe) {
      int qty= (int) Double.parseDouble(recipe.getQtyPurchased());
      artistName.setText(qty+" x "+recipe.getScannedItemType());
      if(recipe.getProductPrice()!=null && !TextUtils.isEmpty(recipe.getProductPrice()) && (Double.parseDouble(recipe.getProductPrice()))>0) {
          textPrice.setText("\u20B9 " + recipe.getProductPrice());
      }
  }
}