package it.uniroma3.idd.hw3.domain;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class SimpleTable {

    @SerializedName(value = "_id")
    private TableId _id;

    @SerializedName(value = "cells")
    private JsonArray cells;

}
