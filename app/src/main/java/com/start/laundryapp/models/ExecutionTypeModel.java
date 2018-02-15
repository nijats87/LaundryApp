package com.start.laundryapp.models;

public class ExecutionTypeModel {

    private String name;
    private String nameAz;
    private String nameRu;
    private Integer ordinal;
    private Integer id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameAz() {
        return nameAz;
    }

    public void setNameAz(String nameAz) {
        this.nameAz = nameAz;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public Integer getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
