package com.millicom.customer.infocustomer.payload.mapper;

import com.millicom.customer.infocustomer.payload.model.PurchaseModel;
import com.millicom.customer.infocustomer.payload.request.payment.PaymentRequest;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PurchaseToDto {
    PurchaseToDto INSTANCE = Mappers.getMapper(PurchaseToDto.class);

    @Mapping(target = "idClient", source = "request.idUser")
    PurchaseModel classToModel(PaymentRequest request);

    @AfterMapping
    default void setDefaultValues(@MappingTarget PurchaseModel model){
        model.setTypePay("CREDIT_CARD");
        model.setStatus("A");
        model.setTotalPay(0);
        model.setTotalPay(0.0);
    }
}
