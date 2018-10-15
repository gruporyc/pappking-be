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

    APIResponse getConfirmedTransactionByFacePlate (String faceplate);

    APIResponse setConfirmedInitTransactionByFacePlate (TransactionTDto transaction);

    APIResponse getConfirmedEndTransactionByFacePlate(String faceplate);

    BillboardDto getBillboardByCode (String code);

    APIResponse setEndTransaction (TransactionTDto endTransaction);

    String setTemporalTransaction (TransactionTDto transaction);

    APIResponse getRate();

    APIResponse putEndTransactionById(String id);


    void updateBillboard(BillboardDto billboard);

    void delleteBillboard(String billboardId);
}
