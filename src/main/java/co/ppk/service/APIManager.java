package co.ppk.service;

import co.ppk.dto.*;
import co.ppk.model.PaymentInfoRequest;

public interface APIManager {


    APIResponse getPaymentInfo(PaymentInfoRequest paymentRequest);

    APIResponse createCustomer(CustomerDto customer);

    APIResponse setFaceplate(String customerId, String facePlate);

    APIResponse getCustomerByIdentification(String identification);

    APIResponse createCompany(CustomerDto customer);

    TransactionTDto getInitTransactionByFacePlate (String faceplate);

    TransactionDto getConfirmedTransactionByFacePlate (String faceplate);

    String setConfirmedInitTransactionByFacePlate (TransactionDto transaction);

    APIResponse getConfirmedEndTransactionByFacePlate(String faceplate);

    BillboardDto getBillboardByCode (String code);

    APIResponse setEndTransaction (TransactionTDto endTransaction);

    String setTemporalTransaction (TransactionTDto transaction);

    RateDto getRate();

    APIResponse putEndTransactionById(String id);


    void updateBillboard(BillboardDto billboard);
    void deleteTemporalTransaction (String id);
    void delleteBillboard(String billboardId);
}
