package com.milicom.code.products.service;

import com.milicom.code.products.payload.entities.ProductModel;

import java.util.List;

public interface ProductService {

    List<ProductModel> findProductsAvailables();
}
