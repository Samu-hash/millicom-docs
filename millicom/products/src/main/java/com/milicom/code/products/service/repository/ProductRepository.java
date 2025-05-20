package com.milicom.code.products.service.repository;

import com.milicom.code.products.payload.entities.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Integer> {

    @Query(nativeQuery = true, value = "select * from products where qty_current > 0 and status = 'A'")
    List<ProductModel> findProductsAvailables();
}
