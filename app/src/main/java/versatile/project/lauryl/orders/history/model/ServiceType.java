package versatile.project.lauryl.orders.history.model;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.List;


public class ServiceType implements Parent<ServiceItemType> {

  private String serviceName;
  private List<ServiceItemType> mIngredients;
  public ServiceType(String title, List<ServiceItemType> items) {
    this.serviceName=title;
  this.mIngredients=items;
  }

  @Override
  public List<ServiceItemType> getChildList() {
    return mIngredients;
  }

  @Override
  public boolean isInitiallyExpanded() {
    return true;
  }

  public String getTile() {
    return serviceName;
  }
}