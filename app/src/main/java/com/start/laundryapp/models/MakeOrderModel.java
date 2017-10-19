package com.start.laundryapp.models;


import org.json.JSONArray;

public class MakeOrderModel {

   private String numberOfClothes;
   private int orderTypeId;
   private int terminalPointId;
   private int executionTypeId;
   private String notes;
   private JSONArray clothes;

   public String getNumberOfClothes() {
      return numberOfClothes;
   }

   public void setNumberOfClothes(String numberOfClothes) {
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

   public JSONArray getClothes() {
      return clothes;
   }

   public void setClothes(JSONArray clothes) {
      this.clothes = clothes;
   }

}
