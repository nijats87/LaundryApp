package com.start.laundryapp.models;


import java.util.List;

public class MakeOrderModel {

   private int numberOfClothes;
   private int orderTypeId;
   private int terminalPointId;
   private int executionTypeId;
   private String notes;
   private List<OrderClothesModel> clothes;

   public int getNumberOfClothes() {
      return numberOfClothes;
   }

   public void setNumberOfClothes(int numberOfClothes) {
      this.numberOfClothes = numberOfClothes;
   }

   public int getOrderTypeId() {
      return orderTypeId;
   }

   public void setOrderTypeId(int orderTypeId) {
      this.orderTypeId = orderTypeId;
   }

   public int getTerminalPointId() {
      return terminalPointId;
   }

   public void setTerminalPointId(int terminalPointId) {
      this.terminalPointId = terminalPointId;
   }

   public int getExecutionTypeId() {
      return executionTypeId;
   }

   public void setExecutionTypeId(int executionTypeId) {
      this.executionTypeId = executionTypeId;
   }

   public String getNotes() {
      return notes;
   }

   public void setNotes(String notes) {
      this.notes = notes;
   }

   public List<OrderClothesModel> getClothes() {
      return clothes;
   }

   public void setClothes(List<OrderClothesModel> clothes) {
      this.clothes = clothes;
   }

}
