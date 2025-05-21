package com.millicom.customer.infocustomer.payload.mapper;

import com.millicom.customer.infocustomer.payload.model.PurchaseDetailsModel;
import com.millicom.customer.infocustomer.payload.request.payment.PaymentProductListRequest;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PurchaseDetailToDto {

    PurchaseDetailToDto INSTANCE = Mappers.getMapper(PurchaseDetailToDto.class);

    @Mapping(target = "idPurchase", source = "purchaseId")
    @Mapping(target = "idProduct", source = "request.productId")
    @Mapping(target = "qtyTotal", source = "request.quantity")
    @Mapping(target = "priceTotal", source = "request.price")
    PurchaseDetailsModel classToModel(PaymentProductListRequest request, Integer purchaseId);
}
