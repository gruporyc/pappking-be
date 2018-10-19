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

	//responseEntity<Object> response = client.processRequestPostObject(pm.getProperty("ppk.payments.getinfo.endpoint"),
//		requestBody, Object.class);
//		LOGGER.debug("Response Status=======================  " + response.getStatusCode());

		//return new APIResponse(response.getStatusCode().value(), response.getBody());
		return new APIResponse(200, null);
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
                pm.getProperty("TRANSACTION_API_BASE_PATH") + "/init/" + facePlate, TransactionTDto.class);
        LOGGER.debug("Response Status=======================  " + response.getStatusCode());

        return response.getBody();
    }

    @Override
    public TransactionTDto getEndTransactionByFacePlate(String facePlate) {

        ResponseEntity<TransactionTDto> response = client.processRequestGet(
                pm.getProperty("TRANSACTION_API_BASE_PATH") + "/end/" + facePlate, TransactionTDto.class);
        LOGGER.debug("Response Status=======================  " + response.getStatusCode());

        return response.getBody();
    }

    @Override
    public TransactionDto getConfirmedTransactionByFacePlate(String facePlate) {
        ResponseEntity<TransactionDto> response = client.processRequestGet(
        pm.getProperty("TRANSACTION_API_BASE_PATH") + "/confirmed/" + facePlate, TransactionDto.class);
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
            pm.getProperty("TRANSACTION_API_BASE_PATH") + "/billboards/find/" + code, BillboardDto.class);
    //LOGGER.debug("Response Status=======================  " + response.getStatusCode());
        return response.getBody();
}

    @Override
    public BillboardDto getBillboardById(String id) {

        ResponseEntity<BillboardDto> response = client.processRequestGet(
                pm.getProperty("TRANSACTION_API_BASE_PATH") + "/billboards/find/id/" + id, BillboardDto.class);
        //LOGGER.debug("Response Status=======================  " + response.getStatusCode());
        return response.getBody();
    }

    @Override
    public OperatorDto getOperatorById(String id) {

        ResponseEntity<OperatorDto> response = client.processRequestGet(
                pm.getProperty("OPERATOR_API_BASE_PATH") + "/" + id, OperatorDto.class);
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

        ResponseEntity<String> response = client.processRequestPost(pm.getProperty("TRANSACTION_API_BASE_PATH") + "/temporal-transaction/create",
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

        ResponseEntity<String> response = client.processRequestPost(pm.getProperty("TRANSACTION_API_BASE_PATH") + "/create",
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

        ResponseEntity<String> response = client.processRequestPost(pm.getProperty("TRANSACTION_API_BASE_PATH") + "autorization/create",
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
                pm.getProperty("TRANSACTION_API_BASE_PATH") + "/rate", RateDto.class);
        LOGGER.debug("Response Status=======================  " + response.getStatusCode());
        return response.getBody();
    }

    @Override
    public APIResponse putEndTransactionById(String id) {

        String s = pm.getProperty("TRANSACTION_API_BASE_PATH") + "/status/update/" + id;
        ResponseEntity<Object> response = client.processRequestGet(
                pm.getProperty("TRANSACTION_API_BASE_PATH") + "/status/update/" + id, Object.class);

        return new APIResponse(response.getStatusCode().value(), response.getBody());

    }

    @Override
    public WorkCodeDto getWorkCodeByAuthorizationCode(String workCode) {

        ResponseEntity<WorkCodeDto> response = client.processRequestGet(
                pm.getProperty("OPERATOR_API_BASE_PATH") + "/work-codes/" + workCode, WorkCodeDto.class);
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
                pm.getProperty("TRANSACTION_API_BASE_PATH") + "/update",requestBody, Object.class);

    }

    @Override
    public void updateBillboard(BillboardDto billboard) {

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("id", billboard.getId());
        requestBody.put("code", billboard.getCode());
        requestBody.put("address", billboard.getAddress());

        ResponseEntity<Object> response = client.processRequestPut(
                pm.getProperty("TRANSACTION_API_BASE_PATH") + "/billboards",requestBody, Object.class);

    }

    @Override
    public void delleteBillboard(String billboardId) {

        ResponseEntity<Object> response = client.processRequestDelete(
                pm.getProperty("TRANSACTION_API_BASE_PATH") + "/billboards/" + billboardId, Object.class);

    }

    @Override
    public void deleteTemporalTransaction(String temporalTransactionId) {

        ResponseEntity<Object> response = client.processRequestDelete(
                pm.getProperty("TRANSACTION_API_BASE_PATH") + "/temporal-transaction/delete/" + temporalTransactionId, Object.class);

    }
}
