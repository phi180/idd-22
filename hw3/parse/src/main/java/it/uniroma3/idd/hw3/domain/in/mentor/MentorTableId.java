package it.uniroma3.idd.hw3.domain.in.mentor;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class MentorTableId {

    @SerializedName("$oid")
    private String oid;

}
