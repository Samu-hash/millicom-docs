package com.milicom.code.products.payload.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "products")
@Data
@ToString
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private int idProduct;
    @Column(name = "title")
    private String title;
    @Lob
    @Column(name = "details", columnDefinition = "TEXT")
    private String details;
    @Column(name = "url_product")
    private String urlProduct;
    @Column(name = "qty")
    private int qty;
    @Column(name = "qty_min")
    private String qtyMin;
    @Column(name = "qty_current")
    private String qtyCurrent;
    @Column(name = "price_unit")
    private double priceUnit;
    @Column(name = "price_total")
    private double priceTotal;
    @Column(name = "status")
    private String status;
    @Column(name = "slug_detail")
    private String slugDetail;
}
