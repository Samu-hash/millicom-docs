package com.millicom.customer.infocustomer.service.repository;

import com.millicom.customer.infocustomer.payload.model.PurchaseDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseDetailRepository extends JpaRepository<PurchaseDetailsModel, Integer> {
}
