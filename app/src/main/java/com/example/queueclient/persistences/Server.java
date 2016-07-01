package com.example.queueclient.persistences;

import com.j256.ormlite.field.DatabaseField;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by shintabmt on 7/1/2016.
 */
@Data
@NoArgsConstructor
public class Server {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String ipAddress;

    public Server(String name, String ipAddress) {
        this.name = name;
        this.ipAddress = ipAddress;
    }
}
