package co.ppk.service.impl;

import co.ppk.dto.*;
import co.ppk.model.PaymentInfoRequest;
import co.ppk.service.APIManager;
import co.ppk.utilities.RestTemplateHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class APIManagerImpl implements APIManager {

	private final static Logger LOGGER = LogManager.getLogger(APIManagerImpl.class);

	@Autowired
	private RestTemplateHelper client;

    @Value("${ppk.payments.service.endpoint}")
    private String ppkPaymentsServiceEndpoint;
    @Value("${ppk.payments.balance.endpoint}")
    private String ppkPaymentsBalanceEndpoint;
    @Value("${ppk.payments.pay.endpoint}")
    private String ppkPaymentsPayEndpoint;
    @Value("${ppk.payments.customer.endpoint}")
    private String ppkPaymentsCustomerEndpoint;
    @Value("${ppk.customer.identification.endpoint}")
    private String ppkCustomerIdentificationEndpoint;
    @Value("${ppk.transaction.base.endpoint}")
    private String ppkTransactionBaseEndpoint;
    @Value("${ppk.operator.base.endpoint}")
    private String ppkOperatorBaseEndpoint;
    @Value("${ppk.payments.base.endpoint}")
    private String ppkPaymentsBaseEndpoint;
    @Value("${ppk.customer.base.endpoint}")
    private String ppkCustomerBaseEndpoint;

	@Override
	public APIResponse getPaymentInfo(PaymentInfoRequest paymentRequest) {
		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("ammount", paymentRequest.getAmmount());
		requestBody.put("concept", paymentRequest.getConcept());
		return new APIResponse(200, null);
	}

    @Override
    public BalanceDto getCustomerBalance(String customerId) {
        ResponseEntity<BalanceDto> response = client.processRequestGet(
            ppkPaymentsBalanceEndpoint + "/" + customerId, BalanceDto.class);
		LOGGER.debug("Response Status=======================  " + response.getStatusCode());

        return response.getBody();
    }

    @Override
    public PaymentServiceDto getPaymentService(String serviceId) {
        ResponseEntity<PaymentServiceDto> response = client.processRequestGet(
                ppkPaymentsServiceEndpoint + "/" + serviceId, PaymentServiceDto.class);
        LOGGER.debug("Response Status=======================  " + response.getStatusCode());

        return response.getBody();
    }

    @Override
    public SimpleResponseDto payService(PaymentRequestDto payment) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("customerId", payment.getCustomerId());
        requestBody.put("serviceId", payment.getServiceId());
        requestBody.put("operator", String.valueOf(payment.getOperator()));
        requestBody.put("amount", String.valueOf(payment.getAmount()));
        ResponseEntity<SimpleResponseDto> response = client.processRequestPost(
            ppkPaymentsPayEndpoint, requestBody, SimpleResponseDto.class);
        LOGGER.debug("Response Status=======================  " + response.getStatusCode());

        return response.getBody();
    }

    @Override
    public SimpleResponseDto createPaymentCustomer(CreatePaymentCustomerDto customer) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("customer_id", customer.getCustomerId());
        requestBody.put("balance", String.valueOf(customer.getBalance()));
        requestBody.put("status", customer.getStatus());
        ResponseEntity<SimpleResponseDto> response = client.processRequestPost(
            ppkPaymentsCustomerEndpoint, requestBody, SimpleResponseDto.class);
        LOGGER.debug("Response Status=======================  " + response.getStatusCode());

        return response.getBody();
    }

  @Override
	public SimpleResponseDto createCustomer(UserDto customer) {
		Map<String, String> requestBody = new HashMap<>();
		requestBody.put("identification", customer.getIdentification());
		requestBody.put("name", customer.getName());
		requestBody.put("lastName", customer.getLastName());
		requestBody.put("email", customer.getEmail());
		requestBody.put("address", customer.getAddress());
		requestBody.put("phone", customer.getPhone());
		requestBody.put("type", "C");
		requestBody.put("status", "ACTIVE");
        ResponseEntity<SimpleResponseDto> response = client.processRequestPost(ppkCustomerBaseEndpoint,
                requestBody, SimpleResponseDto.class);

        return response.getBody();
	}


    @Override
    public APIResponse createCompany(UserDto customer) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("identification", customer.getIdentification());
        requestBody.put("name", customer.getName());
        requestBody.put("email", customer.getEmail());
        requestBody.put("address", customer.getAddress());
        requestBody.put("phone", customer.getPhone());
        requestBody.put("type", "E");
        requestBody.put("status", "ACTIVE");
        ResponseEntity<Object> response = client.processRequestPostObject(ppkCustomerBaseEndpoint,
                requestBody, Object.class);
        return new APIResponse(response.getStatusCode().value(), response.getBody());
    }

	@Override
	public UserDto getCustomerByIdentification(String identification) {
       ResponseEntity<UserDto> response = client.processRequestGet(
           ppkCustomerIdentificationEndpoint + "/" + identification, UserDto.class);

		return  response.getBody();

	}

	@Override
    public String setFaceplate(FaceplateDto faceplate) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("faceplate", faceplate.getFaceplate());
        requestBody.put("customerid", faceplate.getCustomerid());
        ResponseEntity<String> response = client.processRequestPost(ppkCustomerBaseEndpoint + "/faceplate/",
                requestBody, String.class);
        return response.getBody();
    }


    @Override
    public TransactionTDto getInitTransactionByFacePlate(String facePlate) {

        ResponseEntity<TransactionTDto> response = client.processRequestGet(
                ppkTransactionBaseEndpoint + "/init/" + facePlate, TransactionTDto.class);
        LOGGER.debug("Response Status=======================  " + response.getStatusCode());

        return response.getBody();
    }

    @Override
    public TransactionTDto getEndTransactionByFacePlate(String facePlate) {

        ResponseEntity<TransactionTDto> response = client.processRequestGet(
                ppkTransactionBaseEndpoint + "/end/" + facePlate, TransactionTDto.class);
        LOGGER.debug("Response Status=======================  " + response.getStatusCode());

        return response.getBody();
    }

    @Override
    public TransactionDto getConfirmedTransactionByFacePlate(String facePlate) {
        ResponseEntity<TransactionDto> response = client.processRequestGet(
        ppkTransactionBaseEndpoint + "/confirmed/" + facePlate, TransactionDto.class);
        LOGGER.debug("Response Status=======================  " + response.getStatusCode());
        return response.getBody();

    }

    @Override
    public APIResponse setEndTransaction(TransactionTDto endTransaction) {
        return new APIResponse(200, new HashMap<String,Object>() {{
            put("success", true);
        }});
    }

    @Override
    public BillboardDto getBillboardByCode(String code) {

        ResponseEntity<BillboardDto> response = client.processRequestGet(
            ppkTransactionBaseEndpoint + "/billboards/find/" + code, BillboardDto.class);
    //LOGGER.debug("Response Status=======================  " + response.getStatusCode());
        return response.getBody();
}

    @Override
    public BillboardDto getBillboardById(String id) {

        ResponseEntity<BillboardDto> response = client.processRequestGet(
                ppkTransactionBaseEndpoint + "/billboards/find/id/" + id, BillboardDto.class);
        //LOGGER.debug("Response Status=======================  " + response.getStatusCode());
        return response.getBody();
    }

    @Override
    public OperatorDto getOperatorById(String id) {

        ResponseEntity<OperatorDto> response = client.processRequestGet(
                ppkOperatorBaseEndpoint + "/" + id, OperatorDto.class);
        //LOGGER.debug("Response Status=======================  " + response.getStatusCode());
        return response.getBody();
    }

    @Override
    public FaceplateDto getFaceplateByFaceplate(String faceplate) {

        ResponseEntity<FaceplateDto> response = client.processRequestGet(
            ppkCustomerBaseEndpoint + "/faceplate/" + faceplate, FaceplateDto.class);
        //LOGGER.debug("Response Status=======================  " + response.getStatusCode());
        return response.getBody();
    }

    @Override
    public String setTemporalTransaction(TransactionTDto transaction) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("phone", transaction.getPhone());
        requestBody.put("license_plate", transaction.getLicense_plate());
        requestBody.put("billboards_code", transaction.getBillboards_code());
        requestBody.put("date", transaction.getDate());
        requestBody.put("hour", transaction.getHour());
        requestBody.put("time", transaction.getTime());
        requestBody.put("price", transaction.getPrice());
        requestBody.put("action", transaction.getAction());

        ResponseEntity<String> response = client.processRequestPost(ppkTransactionBaseEndpoint + "/temporal-transaction/create",
                requestBody, String.class);

        return response.getBody();
    }

    @Override
    public String setConfirmedInitTransactionByFacePlate(TransactionDto transaction) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("phone", transaction.getPhone());
        requestBody.put("license_plate", transaction.getLicense_plate());
        requestBody.put("billboards_code", transaction.getBillboards_code());
        requestBody.put("start_date", transaction.getStart_date());
        requestBody.put("start_time", transaction.getStart_time());
        requestBody.put("end_date", transaction.getEnd_date());
        requestBody.put("end_time", transaction.getEnd_time());
        requestBody.put("time", transaction.getTime());
        requestBody.put("price", transaction.getPrice());
        requestBody.put("closed", transaction.getClosed());

        ResponseEntity<String> response = client.processRequestPost(ppkTransactionBaseEndpoint + "/create",
                requestBody, String.class);

        return response.getBody();
    }

    @Override
    public String setAutorizationInitTransactionByFacePlate(TransactionDto transaction) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("phone", transaction.getPhone());
        requestBody.put("license_plate", transaction.getLicense_plate());
        requestBody.put("billboards_code", transaction.getBillboards_code());
        requestBody.put("start_date", transaction.getStart_date());
        requestBody.put("start_time", transaction.getStart_time());
        requestBody.put("end_date", transaction.getEnd_date());
        requestBody.put("end_time", transaction.getEnd_time());
        requestBody.put("time", transaction.getTime());
        requestBody.put("price", transaction.getPrice());
        requestBody.put("closed", transaction.getClosed());

        ResponseEntity<String> response = client.processRequestPost(ppkTransactionBaseEndpoint + "/autorization/create",
                requestBody, String.class);

        return response.getBody();
    }

    @Override
    public APIResponse getConfirmedEndTransactionByFacePlate(String facePlate) {
        return new APIResponse(200, new HashMap<String,Object>() {{
            put("success", true);
        }});
    }

    @Override
    public RateDto getRate() {
        ResponseEntity<RateDto> response = client.processRequestGet(
                ppkTransactionBaseEndpoint + "/rate", RateDto.class);
        LOGGER.debug("Response Status=======================  " + response.getStatusCode());
        return response.getBody();
    }

    @Override
    public APIResponse putEndTransactionById(String id) {

        String s = ppkTransactionBaseEndpoint + "/status/update/" + id;
        ResponseEntity<Object> response = client.processRequestGet(
                ppkTransactionBaseEndpoint + "/status/update/" + id, Object.class);

        return new APIResponse(response.getStatusCode().value(), response.getBody());

    }

    @Override
    public WorkCodeDto getWorkCodeByAuthorizationCode(String workCode) {
        ResponseEntity<WorkCodeDto> response = client.processRequestGet(
                ppkOperatorBaseEndpoint + "/work-codes/" + workCode, WorkCodeDto.class);
        //LOGGER.debug("Response Status=======================  " + response.getStatusCode());
        return response.getBody();

	}

    @Override
    public void updateTransaction(TransactionDto transaction) {

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("id", transaction.getId());
        requestBody.put("phone", transaction.getPhone());
        requestBody.put("license_plate", transaction.getLicense_plate());
        requestBody.put("billboards_code", transaction.getBillboards_code());
        requestBody.put("start_date", transaction.getStart_date());
        requestBody.put("start_time", transaction.getStart_time());
        requestBody.put("end_date", transaction.getEnd_date());
        requestBody.put("end_time", transaction.getEnd_time());
        requestBody.put("time", transaction.getTime());
        requestBody.put("price", transaction.getPrice());
        requestBody.put("closed", transaction.getClosed());
        ResponseEntity<Object> response = client.processRequestPut(
                ppkTransactionBaseEndpoint + "/update",requestBody, Object.class);

    }

    @Override
    public void updateBillboard(BillboardDto billboard) {

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("id", billboard.getId());
        requestBody.put("code", billboard.getCode());
        requestBody.put("address", billboard.getAddress());

        ResponseEntity<Object> response = client.processRequestPut(
                ppkTransactionBaseEndpoint + "/billboards",requestBody, Object.class);

    }

    @Override
    public void delleteBillboard(String billboardId) {

        ResponseEntity<Object> response = client.processRequestDelete(
                ppkTransactionBaseEndpoint + "/billboards/" + billboardId, Object.class);

    }

    @Override
    public void deleteTemporalTransaction(String temporalTransactionId) {

        ResponseEntity<Object> response = client.processRequestDelete(
                ppkTransactionBaseEndpoint + "/temporal-transaction/delete/" + temporalTransactionId, Object.class);

    }
}
