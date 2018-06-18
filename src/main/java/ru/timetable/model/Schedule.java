package ru.timetable.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name = "Shcedule", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Schedule {
    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String station;

    @NotNull
    private String departureTime;

    @NotNull
    private String arrivalTime;

    @NotNull
    private int trainNumber;

    @NotNull
    private int dailyRouteId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    public int getDailyRouteId() {
        return dailyRouteId;
    }

    public void setDailyRouteId(int dailyRouteId) {
        this.dailyRouteId = dailyRouteId;
    }
}