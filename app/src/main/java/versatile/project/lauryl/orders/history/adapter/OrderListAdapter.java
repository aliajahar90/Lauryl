package versatile.project.lauryl.orders.history.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;


import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;

import java.util.List;

import versatile.project.lauryl.R;

import versatile.project.lauryl.databinding.ListItemParentServiceBinding;
import versatile.project.lauryl.databinding.ListItemServiceChildBinding;
import versatile.project.lauryl.orders.history.model.ServiceItemType;
import versatile.project.lauryl.orders.history.model.ServiceType;

public class OrderListAdapter extends ExpandableRecyclerAdapter<ServiceType, ServiceItemType, ServiceTypeViewHolder, ServiceItemViewHolder> {


  /**
   * Primary constructor. Sets up {@link #mParentList} and {@link #mFlatItemList}.
   * <p>
   * Any changes to {@link #mParentList} should be made on the original instance, and notified via
   * {@link #notifyParentInserted(int)}
   * {@link #notifyParentRemoved(int)}
   * {@link #notifyParentChanged(int)}
   * {@link #notifyParentRangeInserted(int, int)}
   * {@link #notifyChildInserted(int, int)}
   * {@link #notifyChildRemoved(int, int)}
   * {@link #notifyChildChanged(int, int)}
   * methods and not the notify methods of RecyclerView.Adapter.
   *
   * @param parentList List of all parents to be displayed in the RecyclerView that this
   *                   adapter is linked to
   */
  public OrderListAdapter(@NonNull List<ServiceType> parentList) {
    super(parentList);
  }

  @NonNull
  @Override
  public ServiceTypeViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
    ListItemParentServiceBinding view = DataBindingUtil.inflate(LayoutInflater.from(parentViewGroup.getContext()),R.layout.list_item_parent_service,parentViewGroup,false);
    return new ServiceTypeViewHolder(view.getRoot());
  }

  @Override
  public ServiceItemViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
    ListItemServiceChildBinding view = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.list_item_service_child,parent,false);
    return new ServiceItemViewHolder(view.getRoot());
  }

  @Override
  public void onBindParentViewHolder(@NonNull ServiceTypeViewHolder parentViewHolder, int parentPosition, @NonNull ServiceType parent) {
    parentViewHolder.bind(parent);
  }

  @Override
  public void onBindChildViewHolder(@NonNull ServiceItemViewHolder childViewHolder, int parentPosition, int childPosition, @NonNull ServiceItemType child) {
    childViewHolder.bind(child);
  }


}