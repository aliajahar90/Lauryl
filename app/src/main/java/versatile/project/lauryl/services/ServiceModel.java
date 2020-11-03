package versatile.project.lauryl.services;

import versatile.project.lauryl.model.TopServicesDataItem;

public class ServiceModel {
    private TopServicesDataItem topServicesDataItem;

    public String getServiceTypeName() {
        return serviceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }

    private String serviceTypeName;
    private boolean isServiceType;

    public TopServicesDataItem getTopServicesDataItem() {
        return topServicesDataItem;
    }

    public void setTopServicesDataItem(TopServicesDataItem topServicesDataItem) {
        this.topServicesDataItem = topServicesDataItem;
    }



    public boolean isServiceType() {
        return isServiceType;
    }

    public void setServiceType(boolean serviceType) {
        isServiceType = serviceType;
    }
}
