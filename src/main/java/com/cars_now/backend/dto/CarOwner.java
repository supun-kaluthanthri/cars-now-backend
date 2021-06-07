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
@Table(name = "CAR_OWNER")
public class CarOwner implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_sequence")
    @SequenceGenerator(name = "id_sequence", sequenceName = "ID_SEQ")
    private Long carOwnerId;

    @Column(name =  "firstName")
    private String firstName;

    @Column(name =  "lastName")
    private String lastName;

    @Column(name =  "gender")
    private String gender;

    @Column(name =  "email")
    private String email;

    @Column(name =  "address")
    private String address;

    @Column(name =  "nicNo")
    private String nicNo;

    @Column(name =  "mobileNo")
    private String mobileNo;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "carOwner")
    @JsonIgnore
    private List<Car> cars;
}

