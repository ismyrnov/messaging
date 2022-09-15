package com.ismyrnov.messaging.kafka.streaming.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Employee implements Serializable {
private String name;
private String company;
private String position;
private Integer experience;
}
