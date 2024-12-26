package com.orlov.kcprovider.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "customers")
public class User {
    @Id
    @Column(name = "customer_uuid")
    private UUID customerUUID;

    @Column(name = "preferred_email")
    private String email;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;
}


