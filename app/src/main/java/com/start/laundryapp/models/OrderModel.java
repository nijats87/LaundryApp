package com.start.laundryapp.models;

import java.util.ArrayList;

/**
 * Created by nijats87 on 10/23/2017.
 */

public class OrderModel {
    public int id;
    public String notes;
    public ArrayList<ClothesModel> clothes;
    public int executionTypeId;
    public int orderTypeId;
    public int terminalPointId;
    public int status;
    public int numberOfClothes;
}
