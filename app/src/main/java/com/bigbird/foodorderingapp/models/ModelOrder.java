package com.bigbird.foodorderingapp.models;

public class ModelOrder {
    String id;
    String dateTime;
    boolean isScheduled;
    boolean isCompleted;
    ModelUserTypeUser orderPlacer;
    ProductItemModel dishItem;

    public ModelOrder() {
    }

    public ModelOrder(boolean isScheduled, boolean isCompleted, String id, String dateTime, ModelUserTypeUser orderPlacer, ModelKitchenUser orderReceiver, ProductItemModel dishItem) {
        this.id = id;
        this.isCompleted = isCompleted;
        this.isScheduled = isScheduled;
        this.dateTime = dateTime;
        this.orderPlacer = orderPlacer;

        this.dishItem = dishItem;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public ModelUserTypeUser getOrderPlacer() {
        return orderPlacer;
    }

    public void setOrderPlacer(ModelUserTypeUser orderPlacer) {
        this.orderPlacer = orderPlacer;
    }


    public ProductItemModel getDishItem() {
        return dishItem;
    }

    public void setDishItem(ProductItemModel dishItem) {
        this.dishItem = dishItem;
    }

    public boolean isScheduled() {
        return isScheduled;
    }

    public void setScheduled(boolean scheduled) {
        isScheduled = scheduled;
    }

    @Override
    public String toString() {
        return "ModelOrder{" +
                "id='" + id + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", orderPlacer=" + orderPlacer +
                ", dishItem=" + dishItem +
                ", isScheduled=" + isScheduled +
                '}';
    }
}
