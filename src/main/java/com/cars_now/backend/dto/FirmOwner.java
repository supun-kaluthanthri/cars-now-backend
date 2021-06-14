package com.cars_now.backend.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "FIRM_OWNER")
public class FirmOwner implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_sequence")
    @SequenceGenerator(name = "id_sequence", sequenceName = "ID_SEQ")
    private Long firmOwnerId;

    @Column(name = "email")
    private String email;

    @Column(name =  "firstName")
    private String firstName;

    @Column(name =  "lastName")
    private String lastName;

    @Column(name = "firmAddress")
    private String firmAddress;

    @Column(name = "userId")
    private Integer userId;

}

