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

    TransactionTDto getEndTransactionByFacePlate (String faceplate);

    TransactionDto getConfirmedTransactionByFacePlate (String faceplate);

    String setConfirmedInitTransactionByFacePlate (TransactionDto transaction);

    String setAutorizationInitTransactionByFacePlate (TransactionDto transaction);


    APIResponse getConfirmedEndTransactionByFacePlate(String faceplate);

    BillboardDto getBillboardByCode (String code);
    BillboardDto getBillboardById (String id);


    APIResponse setEndTransaction (TransactionTDto endTransaction);

    String setTemporalTransaction (TransactionTDto transaction);

    RateDto getRate();

    APIResponse putEndTransactionById(String id);

    WorkCodeDto getWorkCodeByAuthorizationCode(String authorizationCode);

    OperatorDto getOperatorById(String id);



    void updateTransaction(TransactionDto transaction);
    void updateBillboard(BillboardDto billboard);
    void deleteTemporalTransaction (String id);
    void delleteBillboard(String billboardId);
}
