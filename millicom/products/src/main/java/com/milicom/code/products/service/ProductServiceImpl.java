package com.milicom.code.products.service;

import com.milicom.code.products.payload.entities.ProductModel;
import com.milicom.code.products.service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Override
    public List<ProductModel> findProductsAvailables() {
        return productRepository.findProductsAvailables();
    }
}
