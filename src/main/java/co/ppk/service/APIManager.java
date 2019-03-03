package co.ppk.service;

import co.ppk.dto.APIResponse;
import co.ppk.dto.PaymentRequestDto;
import co.ppk.dto.PaymentServiceDto;
import co.ppk.dto.*;
import co.ppk.model.PaymentInfoRequest;

public interface APIManager {


    APIResponse getPaymentInfo(PaymentInfoRequest paymentRequest);

    BalanceDto getCustomerBalance(String customerId);

    SimpleResponseDto createCustomer(UserDto customer);

    String setFaceplate(FaceplateDto faceplate);

    UserDto getCustomerByIdentification(String identification);

    APIResponse createCompany(UserDto customer);

    TransactionTDto getInitTransactionByFacePlate(String faceplate);

    TransactionTDto getEndTransactionByFacePlate(String faceplate);

    TransactionDto getConfirmedTransactionByFacePlate(String faceplate);

    String setConfirmedInitTransactionByFacePlate(TransactionDto transaction);

    String setAutorizationInitTransactionByFacePlate(TransactionDto transaction);


    APIResponse getConfirmedEndTransactionByFacePlate(String faceplate);

    BillboardDto getBillboardByCode(String code);
    BillboardDto getBillboardById(String id);


    APIResponse setEndTransaction(TransactionTDto endTransaction);

    String setTemporalTransaction(TransactionTDto transaction);

    RateDto getRate();

    APIResponse putEndTransactionById(String id);

    WorkCodeDto getWorkCodeByAuthorizationCode(String authorizationCode);

    OperatorDto getOperatorById(String id);

    FaceplateDto getFaceplateByFaceplate(String faceplate);

    void updateTransaction(TransactionDto transaction);
    void updateBillboard(BillboardDto billboard);
    void deleteTemporalTransaction(String id);
    void delleteBillboard(String billboardId);



    PaymentServiceDto getPaymentService(String serviceId);

    SimpleResponseDto payService(PaymentRequestDto payment);

    SimpleResponseDto createPaymentCustomer(CreatePaymentCustomerDto customer);
}
