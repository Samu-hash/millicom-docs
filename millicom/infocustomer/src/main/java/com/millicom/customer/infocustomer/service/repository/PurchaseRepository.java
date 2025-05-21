package com.millicom.customer.infocustomer.service.repository;

import com.millicom.customer.infocustomer.payload.model.PurchaseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<PurchaseModel, Integer> {

    @Query(nativeQuery = true, value = "select * from purchases where id_client = ?1")
    List<PurchaseModel> getPurchasesIdUser(Integer id);
}
