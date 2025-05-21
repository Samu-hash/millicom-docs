package com.millicom.customer.infocustomer.payload.mapper;

import com.millicom.customer.infocustomer.payload.model.CustomerModel;
import com.millicom.customer.infocustomer.payload.request.CustomerCreateRequest;
import com.millicom.customer.infocustomer.payload.request.CustomerRequest;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CustomerRequestToCustomer {

    CustomerRequestToCustomer INSTANCE = Mappers.getMapper(CustomerRequestToCustomer.class);

    @Mapping(target = "usernameAdd", source = "request.usernameManager")
    @Mapping(target = "usernameUpd", source = "request.usernameManager")
    CustomerModel classToModel(CustomerRequest request);

    CustomerModel classToModelCreate(CustomerCreateRequest request);

    @AfterMapping
    default void setDefaultValues(@MappingTarget CustomerModel model){
        model.setDateAdd(LocalDateTime.now().toString());
    }

}
