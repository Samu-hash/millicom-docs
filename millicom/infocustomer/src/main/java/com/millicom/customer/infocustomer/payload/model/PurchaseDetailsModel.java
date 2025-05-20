package com.millicom.customer.infocustomer.payload.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "purchases_details")
@Data
@ToString
public class PurchaseDetailsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detail")
    private int idDetail;

    @Column(name = "id_purchase")
    private int idPurchase;

    @Column(name = "id_product")
    private int idProduct;

    @Column(name = "qty_total")
    private int qtyTotal;

    @Column(name = "price_total")
    private double priceTotal;
}
