package com.millicom.customer.infocustomer.service;

import com.millicom.customer.infocustomer.payload.model.PurchaseModel;
import com.millicom.customer.infocustomer.payload.request.payment.PaymentRequest;

import java.util.List;

public interface PaymentService {

    int processPayment(PaymentRequest request);

    List<PurchaseModel> getPurchases(Integer idUser);
}
