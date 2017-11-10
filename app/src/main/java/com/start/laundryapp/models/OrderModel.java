package com.start.laundryapp.models;

import java.util.ArrayList;

public class OrderModel {
    public int id;
    public int numberOfClothes;
    public ArrayList<ClothesModel> clothes;
    public int orderTypeId;
    public int executionTypeId;
    public int terminalPointId;
    public int status;
    public String notes;
}
