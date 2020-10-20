package versatile.project.lauryl.orders.history.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;

import versatile.project.lauryl.R;
import versatile.project.lauryl.orders.history.model.ServiceItemType;

public class ServiceItemViewHolder extends ChildViewHolder {

  private AppCompatTextView artistName;

  public ServiceItemViewHolder(View itemView) {
    super(itemView);
    artistName = itemView.findViewById(R.id.txtItemNameQuantity);
  }
  public void bind(ServiceItemType recipe) {
    artistName.setText(recipe.getScannedItemType());
  }
}