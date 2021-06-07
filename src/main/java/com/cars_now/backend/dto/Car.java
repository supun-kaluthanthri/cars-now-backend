package com.cars_now.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "CAR")
public class Car implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_sequence")
    @SequenceGenerator(name = "id_sequence", sequenceName = "ID_SEQ")
    private Long carId;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "modelYear")
    private String modelYear;

    @Column(name = "fuelType")
    private String fuelType;

    @Column(name = "carPlateNumber")
    private String carPlateNumber;

    @Column(name = "passengerLimit")
    private int passengerLimit;

    @Column(name = "dailyRate")
    private double dailyRate;

    @Column(name = "allowedKmPerDay")
    private int allowedKmPerDay;

    @Column(name = "additionalRatePerKm")
    private double additionalRatePerKm;

    @Column(name = "status")
    private int status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "carOwner")
    private CarOwner carOwner;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "car")
    private List<Booking> bookings;


}
