package com.milicom.code.products.controller;

import com.milicom.code.products.service.ProductService;
import com.milicom.code.products.utils.authorizations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ProductsController {

    private final ProductService productService;
    private final Authorization authorization;

    @GetMapping("/products-availables")
    public ResponseEntity<?> getProducts(){
        return authorization.getResponseOk(productService.findProductsAvailables());
    }
}
