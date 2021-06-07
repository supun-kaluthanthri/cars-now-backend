package com.cars_now.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "BOOKING")
public class Booking implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_sequence")
    @SequenceGenerator(name = "id_sequence", sequenceName = "ID_SEQ")
    private Long bookingId;

    @Column(name = "bookingStartDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date bookingStartDate;


    @Column(name = "bookingEndDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date bookingEndDate;

    @Column(name = "carReturnDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date carReturnDate;

    @Column(name = "totalDistance")
    private int totalDistance;

    @Column(name = "status")
    private int status;

    @Column(name =  "amount")
    private double amount;

    // FKs carId, renterId
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car")
    private Car car;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renter")
    private Renter renter;

}
