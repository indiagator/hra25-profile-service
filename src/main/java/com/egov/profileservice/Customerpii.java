package com.egov.profileservice;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "customerpii")
public class Customerpii {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @Column(name = "lastname", length = 100)
    private String lastname;

    @Size(max = 100)
    @Column(name = "middlename", length = 100)
    private String middlename;

    @Size(max = 100)
    @Column(name = "firstname", length = 100)
    private String firstname;

    @Size(max = 100)
    @Column(name = "aadhaar", length = 100)
    private String aadhaar;

    @Size(max = 100)
    @Column(name = "pan", length = 100)
    private String pan;

    @Size(max = 100)
    @Column(name = "voterid", length = 100)
    private String voterid;

    @Size(max = 100)
    @Column(name = "passport", length = 100)
    private String passport;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "biometriclink", length = Integer.MAX_VALUE)
    private String biometriclink;

    @Column(name = "photolink", length = Integer.MAX_VALUE)
    private String photolink;

}