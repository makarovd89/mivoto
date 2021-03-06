package com.herokuapp.mivoto.to;

public class RestaurantTo extends BaseTo {
    private String name;
    private String address;
    private String phone;

    public RestaurantTo() {
    }

    public RestaurantTo(Integer id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public RestaurantTo(RestaurantTo restaurantTo) {
        this(restaurantTo.id, restaurantTo.name, restaurantTo.address, restaurantTo.phone);
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
