package com.example.queueclient.persistences;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by shintabmt on 7/1/2016.
 */
@Data
@NoArgsConstructor
public class Server implements Serializable {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField (uniqueCombo = true)
    private String name;
    @DatabaseField(uniqueCombo = true)
    private String ipAddress;

    public Server(String name, String ipAddress) {
        this.name = name;
        this.ipAddress = ipAddress;
    }
}
