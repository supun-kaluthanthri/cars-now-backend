package com.cars_now.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "RENTER")
public class Renter implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_sequence")
    @SequenceGenerator(name = "id_sequence", sequenceName = "ID_SEQ")
    private Long renterId;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name =  "gender")
    private String gender;

    @Column(name =  "address")
    private String address;

    @Column(name =  "nicNo")
    private String nicNo;

    @Column(name =  "email")
    private String email;

    @Column(name =  "mobileNo")
    private String mobileNo;

    @Column(name =  "licenceNo")
    private String licenseNo;

    @Column(name = "userId")
    private Integer userId;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "renter")
    private List<Booking> bookings;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "renter")
    private List<Feedback> feedbacks;


}
