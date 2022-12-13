package it.uniroma3.idd.hw3.domain;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class TableId {

    @SerializedName("$oid")
    private String oid;

}
