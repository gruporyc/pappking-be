package co.ppk.service.impl;

import co.ppk.dto.*;
import co.ppk.model.PaymentInfoRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import co.ppk.service.APIManager;
import co.ppk.utilities.PropertyManager;
import co.ppk.utilities.RestTemplateHelper;

import java.util.HashMap;
import java.util.Map;

@Component
public class APIManagerImpl implements APIManager {

	private final static Logger LOGGER = LogManager.getLogger(APIManagerImpl.class);

	@Autowired
	private RestTemplateHelper client;

	@Autowired
	private PropertyManager pm;

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
                pm.getProperty("ppk.payments.balance.endpoint") + "/" + customerId, BalanceDto.class);
		LOGGER.debug("Response Status=======================  " + response.getStatusCode());

        return response.getBody();
    }

    @Override
    public PaymentServiceDto getPaymentService(String serviceId) {
        ResponseEntity<PaymentServiceDto> response = client.processRequestGet(
                pm.getProperty("ppk.payments.service.endpoint") + "/" + serviceId, PaymentServiceDto.class);
        LOGGER.debug("Response Status=======================  " + response.getStatusCode());

        return response.getBody();
    }

    @Override
    public Boolean payService(PaymentRequestDto payment) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("customer_id", payment.getCustomerId());
        requestBody.put("service_id", payment.getServiceId());
        requestBody.put("operator", String.valueOf(payment.getOperator()));
        requestBody.put("amount", String.valueOf(payment.getAmount()));
        ResponseEntity<Boolean> response = client.processRequestPost(
                pm.getProperty("ppk.payments.pay.endpoint"), requestBody, Boolean.class);
        LOGGER.debug("Response Status=======================  " + response.getStatusCode());

        return response.getBody();
    }

	@Override
	public APIResponse createCustomer(CustomerDto customer) {
		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("identification", customer.getIdentification());
		requestBody.put("name", customer.getName());
		requestBody.put("lastName", customer.getLastName());
		requestBody.put("email", customer.getEmail());
		requestBody.put("address", customer.getAddress());
		requestBody.put("phone", customer.getPhone());
		requestBody.put("type", "C");
		requestBody.put("status", "ACTIVE");
		String property = pm.getProperty("ppk.customer.base.endpoint");
		ResponseEntity<Object> response = client.processRequestPostObject(pm.getProperty("ppk.customer.base.endpoint"),
		requestBody, Object.class);

		return new APIResponse(response.getStatusCode().value(), response.getBody());
	}

    @Override
    public APIResponse createCompany(CustomerDto customer) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("identification", customer.getIdentification());
        requestBody.put("name", customer.getName());
        requestBody.put("email", customer.getEmail());
        requestBody.put("address", customer.getAddress());
        requestBody.put("phone", customer.getPhone());
        requestBody.put("type", "E");
        requestBody.put("status", "ACTIVE");
        String property = pm.getProperty("ppk.customer.base.endpoint");
        ResponseEntity<Object> response = client.processRequestPostObject(pm.getProperty("ppk.customer.base.endpoint"),
                requestBody, Object.class);

        return new APIResponse(response.getStatusCode().value(), response.getBody());
    }

	@Override
	public APIResponse getCustomerByIdentification(String identification) {
        String s = pm.getProperty("ppk.customer.identification.endpoint") + "/" + identification;
        ResponseEntity<Object> response = client.processRequestGet(
				pm.getProperty("ppk.customer.identification.endpoint") + "/" + identification, Object.class);

		return new APIResponse(response.getStatusCode().value(), response.getBody());

	}

	@Override
    public APIResponse setFaceplate(String customerId, String facePlate) {
        return new APIResponse(200, new HashMap<String,Object>() {{
            put("success", true);
        }});
    }


    @Override
    public TransactionTDto getInitTransactionByFacePlate(String facePlate) {

        ResponseEntity<TransactionTDto> response = client.processRequestGet(
                pm.getProperty("ppk.transaction.base.endpoint") + "/init/" + facePlate, TransactionTDto.class);
        LOGGER.debug("Response Status=======================  " + response.getStatusCode());

        return response.getBody();
    }

    @Override
    public TransactionTDto getEndTransactionByFacePlate(String facePlate) {

        ResponseEntity<TransactionTDto> response = client.processRequestGet(
                pm.getProperty("ppk.transaction.base.endpoint") + "/end/" + facePlate, TransactionTDto.class);
        LOGGER.debug("Response Status=======================  " + response.getStatusCode());

        return response.getBody();
    }

    @Override
    public TransactionDto getConfirmedTransactionByFacePlate(String facePlate) {
        ResponseEntity<TransactionDto> response = client.processRequestGet(
        pm.getProperty("ppk.transaction.base.endpoint") + "/confirmed/" + facePlate, TransactionDto.class);
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
            pm.getProperty("ppk.transaction.base.endpoint") + "/billboards/find/" + code, BillboardDto.class);
    //LOGGER.debug("Response Status=======================  " + response.getStatusCode());
        return response.getBody();
}

    @Override
    public BillboardDto getBillboardById(String id) {

        ResponseEntity<BillboardDto> response = client.processRequestGet(
                pm.getProperty("ppk.transaction.base.endpoint") + "/billboards/find/id/" + id, BillboardDto.class);
        //LOGGER.debug("Response Status=======================  " + response.getStatusCode());
        return response.getBody();
    }

    @Override
    public OperatorDto getOperatorById(String id) {

        ResponseEntity<OperatorDto> response = client.processRequestGet(
                pm.getProperty("ppk.operator.base.endpoint") + "/" + id, OperatorDto.class);
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

        ResponseEntity<String> response = client.processRequestPost(pm.getProperty("ppk.transaction.base.endpoint") + "/temporal-transaction/create",
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

        ResponseEntity<String> response = client.processRequestPost(pm.getProperty("ppk.transaction.base.endpoint") + "/create",
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

        ResponseEntity<String> response = client.processRequestPost(pm.getProperty("ppk.transaction.base.endpoint") + "/autorization/create",
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
                pm.getProperty("ppk.transaction.base.endpoint") + "/rate", RateDto.class);
        LOGGER.debug("Response Status=======================  " + response.getStatusCode());
        return response.getBody();
    }

    @Override
    public APIResponse putEndTransactionById(String id) {

        String s = pm.getProperty("ppk.transaction.base.endpoint") + "/status/update/" + id;
        ResponseEntity<Object> response = client.processRequestGet(
                pm.getProperty("ppk.transaction.base.endpoint") + "/status/update/" + id, Object.class);

        return new APIResponse(response.getStatusCode().value(), response.getBody());

    }

    @Override
    public WorkCodeDto getWorkCodeByAuthorizationCode(String workCode) {
        ResponseEntity<WorkCodeDto> response = client.processRequestGet(
                pm.getProperty("ppk.operator.base.endpoint") + "/work-codes/" + workCode, WorkCodeDto.class);
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
                pm.getProperty("ppk.transaction.base.endpoint") + "/update",requestBody, Object.class);

    }

    @Override
    public void updateBillboard(BillboardDto billboard) {

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("id", billboard.getId());
        requestBody.put("code", billboard.getCode());
        requestBody.put("address", billboard.getAddress());

        ResponseEntity<Object> response = client.processRequestPut(
                pm.getProperty("ppk.transaction.base.endpoint") + "/billboards",requestBody, Object.class);

    }

    @Override
    public void delleteBillboard(String billboardId) {

        ResponseEntity<Object> response = client.processRequestDelete(
                pm.getProperty("ppk.transaction.base.endpoint") + "/billboards/" + billboardId, Object.class);

    }

    @Override
    public void deleteTemporalTransaction(String temporalTransactionId) {

        ResponseEntity<Object> response = client.processRequestDelete(
                pm.getProperty("ppk.transaction.base.endpoint") + "/temporal-transaction/delete/" + temporalTransactionId, Object.class);

    }
}
