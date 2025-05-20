package com.millicom.customer.infocustomer.service;

import com.millicom.customer.infocustomer.payload.error.ErrorHandler;
import com.millicom.customer.infocustomer.payload.error.ValidationException;
import com.millicom.customer.infocustomer.payload.model.PurchaseModel;
import com.millicom.customer.infocustomer.payload.request.payment.PaymentRequest;
import com.millicom.customer.infocustomer.service.repository.PurchaseDetailRepository;
import com.millicom.customer.infocustomer.service.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{

    private final PurchaseRepository purchaseRepository;
    private final PurchaseDetailRepository purchaseDetailRepository;

    @Override
    public int processPayment(PaymentRequest request) {
        try {

            purchaseRepository.save(new PurchaseModel());

            return 1;
        }catch (Exception e){
            throw new ValidationException(List.of(new ErrorHandler(2, e.getMessage())));
        }
    }
}
