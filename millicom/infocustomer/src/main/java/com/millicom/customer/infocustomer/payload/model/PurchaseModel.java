package com.millicom.customer.infocustomer.payload.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "purchases")
@Data
@ToString
public class PurchaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_purchase")
    private int idPurchase;

    @Column(name = "id_client")
    private int idClient;

    @Column(name = "total_product")
    private int totalProduct;

    @Column(name = "total_pay")
    private double totalPay;

    @Column(name = "type_pay")
    private String typePay;

    @Column(name = "status")
    private String status;
}
