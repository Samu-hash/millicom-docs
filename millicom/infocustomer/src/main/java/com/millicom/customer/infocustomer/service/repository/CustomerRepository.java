package com.millicom.customer.infocustomer.service.repository;

import com.millicom.customer.infocustomer.payload.model.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel, Integer> {

    @Query(nativeQuery = true, value = "select * from customers where id_customer=?1")
    CustomerModel findByCondition(String customer);


    @Query(nativeQuery = true, value = "update customers set status='I' where ?1")
    void updateFields(String condition);

    @Query(nativeQuery = true, value = "select * from customers where email = ?1 and password = ?2")
    CustomerModel createLogin(String user, String pass);
}
