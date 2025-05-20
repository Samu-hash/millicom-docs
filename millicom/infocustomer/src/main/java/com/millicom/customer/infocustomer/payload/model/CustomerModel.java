package com.millicom.customer.infocustomer.payload.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "customers")
@Data
@ToString
public class CustomerModel {

    @Id
    @Column(name = "id_customer")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int identity;

    @Column(name = "name")
    private String name;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "address")
    private String address;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "credit_card")
    private String creditCard;
    @Column(name = "credit_cvv")
    private String creditCvv;

    //campos de auditoria
    @Column(name = "username_add")
    private String usernameAdd;
    @Column(name = "date_add")
    private String dateAdd;
    @Column(name = "username_upd")
    private String usernameUpd;
    @Column(name = "date_upd")
    private String dateUpd;
}
