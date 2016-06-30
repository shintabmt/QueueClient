package com.example.queueclient.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by shintabmt on 6/30/2016.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private String name;
    private String uid;
    private QueueType queueType;
}
