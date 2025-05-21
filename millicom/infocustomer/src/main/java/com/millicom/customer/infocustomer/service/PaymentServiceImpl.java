package com.millicom.customer.infocustomer.service;

import com.millicom.customer.infocustomer.payload.error.ErrorHandler;
import com.millicom.customer.infocustomer.payload.error.ValidationException;
import com.millicom.customer.infocustomer.payload.mapper.PurchaseDetailToDto;
import com.millicom.customer.infocustomer.payload.mapper.PurchaseToDto;
import com.millicom.customer.infocustomer.payload.model.PurchaseDetailsModel;
import com.millicom.customer.infocustomer.payload.model.PurchaseModel;
import com.millicom.customer.infocustomer.payload.request.payment.PaymentProductListRequest;
import com.millicom.customer.infocustomer.payload.request.payment.PaymentRequest;
import com.millicom.customer.infocustomer.service.repository.PurchaseDetailRepository;
import com.millicom.customer.infocustomer.service.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{

    private final PurchaseRepository purchaseRepository;
    private final PurchaseDetailRepository purchaseDetailRepository;

    @Override
    public int processPayment(PaymentRequest request) {
        try {

            PurchaseModel purchaseModel = PurchaseToDto.INSTANCE.classToModel(request);

            request.getDetails().forEach(detail->{
                purchaseModel.setTotalProduct(detail.getQuantity()+purchaseModel.getTotalProduct());
                purchaseModel.setTotalPay((detail.getQuantity()*detail.getPrice())+purchaseModel.getTotalPay());
            });

            PurchaseModel modelNew = purchaseRepository.save(purchaseModel);

            List<PurchaseDetailsModel> modelList = new ArrayList<>();
            for(PaymentProductListRequest payment: request.getDetails()){
                modelList.add(PurchaseDetailToDto
                        .INSTANCE.classToModel(payment, modelNew.getIdPurchase())
                );
            }

            purchaseDetailRepository.saveAll(modelList);

            return 1;
        }catch (Exception e){
            throw new ValidationException(List.of(new ErrorHandler(2, e.getMessage())));
        }
    }

    @Override
    public List<PurchaseModel> getPurchases(Integer idUser) {
        return purchaseRepository.getPurchasesIdUser(idUser);
    }
}
