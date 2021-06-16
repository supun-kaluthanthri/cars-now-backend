package com.cars_now.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @Column(name = "imageUrl")
    private String imageUrl;

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

    public Car() {
    }

    public Car(Long carId, String brand, String model, String modelYear, String fuelType, String carPlateNumber, int passengerLimit,
               double dailyRate, int allowedKmPerDay, double additionalRatePerKm, String imageUrl, int status, CarOwner carOwner, List<Booking> bookings) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.modelYear = modelYear;
        this.fuelType = fuelType;
        this.carPlateNumber = carPlateNumber;
        this.passengerLimit = passengerLimit;
        this.dailyRate = dailyRate;
        this.allowedKmPerDay = allowedKmPerDay;
        this.additionalRatePerKm = additionalRatePerKm;
        this.imageUrl = imageUrl;
        this.status = status;
        this.carOwner = carOwner;
        this.bookings = bookings;
    }
}
