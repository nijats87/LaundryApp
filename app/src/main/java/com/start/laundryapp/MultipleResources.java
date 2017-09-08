package com.start.laundryapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nijats87 on 07-Sep-17.
 */

public class MultipleResources {

    public class Result {
        public class Items {
            @SerializedName("name")
            public String name;

            @SerializedName("nameAz")
            public String nameAz;

            @SerializedName("nameRu")
            public String nameRu;

            @SerializedName("ordinal")
            public Integer ordinal;

            @SerializedName("id")
            public Integer id;
        }

        @SerializedName("targetUrl")
        public String targetUrl;

        @SerializedName("success")
        public boolean success;

        @SerializedName("error")
        public String error;

        @SerializedName("unAuthorizedRequest")
        public boolean unAuthorizedRequest;

        @SerializedName("__abp")
        public boolean __abp;

    }


}
