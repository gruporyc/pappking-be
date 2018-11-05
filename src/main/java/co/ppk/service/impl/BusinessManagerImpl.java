package co.ppk.service.impl;

import co.ppk.dto.*;
import co.ppk.service.APIManager;
import co.ppk.service.BusinessManager;
import co.ppk.utilities.HourGeneration;
import co.ppk.utilities.MessageHelper;
import co.ppk.utilities.TimeCalculation;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Objects;

import static co.ppk.utilities.Messages.*;


@Component
public class BusinessManagerImpl implements BusinessManager{

    @Autowired
    APIManager apiManager;

    @Autowired
    MessageHelper messageHelper;

    @Override
    public String customersRegister(String queryText) {
        //COMANDO: registro 9991191899 Pepito Perez pperez@gmail.com
        String[] data = messageHelper.asArray(queryText);
        String response = "";
        if(5!=data.length) {
            return  REQUEST_DATA_ERROR_CUSTOMER;
        }
        try{
            CustomerDto customer = new CustomerDto();
            customer.setIdentification(data[1]);
            customer.setName(data[2]);
            customer.setLastName(data[3]);
            customer.setEmail(data[4]);
            String customerResponse = apiManager.createCustomer(customer);
            if (customerResponse.equals("S")) {
                return CUSTOMER_ALREADY_EXISTS;
            }
            //ENVIAR A PAGOS EL customerResponse que es el ID
            CreatePaymentCustomerDto paymentCustomer = new CreatePaymentCustomerDto();
            paymentCustomer.setCustomerId(customerResponse);
            apiManager.createPaymentCustomer(paymentCustomer);
            return CUSTOMER_REGISTER_SUCCESS;
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String registerFaceplate(String queryText) {
        /** TODO Implement business logic for end service */
        String response = "";
        String[] data = messageHelper.asArray(queryText);
        if (3 != data.length) {
            return REQUEST_DATA_ERROR_FACEPLATE;
        }
        try {
            CustomerDto customerResponse = apiManager.getCustomerByIdentification(data[1]);
            if (Objects.isNull(customerResponse.getId()) || customerResponse.getId().isEmpty()) {
                return CUSTOMER_NOT_EXISTS;
            }
            FaceplateDto faceplate = new FaceplateDto();
            faceplate.setFaceplate(data[2]);
            faceplate.setCustomerid(customerResponse.getId());
            String faceplateResponse = apiManager.setFaceplate(faceplate);
            if (faceplateResponse.equals("S")) {
                return FACEPLATE_ALREADY_EXISTS;
            }
            return FACEPLATE_REGISTER_SUCCESS + data[2];
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @Override
    public String companyRegister(String queryText) {
        /** TODO Implement business logic for end service */

        String[] data = messageHelper.asArray(queryText);
        String response = "";
        if(4==data.length) {
            CustomerDto customer = new CustomerDto();
            customer.setIdentification(data[1]);
            customer.setName(data[2]);
            customer.setEmail(data[3]);
             try {
                APIResponse apiResponse = apiManager.createCompany(customer);
                switch (apiResponse.getHttpCode()) {
                    case 200:
                        response = CUSTOMER_REGISTER_SUCCESS;
                        break;
                    case 304:
                        response = CUSTOMER_ALREADY_EXISTS;
                        break;
                }
            } catch (HttpClientErrorException e) {
                response = UNEXPECTED_ERROR;
            }
        }else{
            response =  REQUEST_DATA_ERROR_CUSTOMER;
        }
        return response;
    }



    @Override
    public String initService(String queryText, String session) {
        //COMANDO = INICIO VALLA PLACA
        String[] data = messageHelper.asArray(queryText);
        String response = "";
        if(3 != data.length) {
            return REQUEST_DATA_ERROR_START_SERVICE;
        }
        try {
            TransactionTDto transaction = apiManager.getInitTransactionByFacePlate(data[2]);
            if (Objects.nonNull(transaction.getId()) && !transaction.getId().isEmpty() ) {
                return START_SERVICE_EXISTS + data[2];
            }
            BillboardDto billboardResponse = apiManager.getBillboardByCode(data[1]);
            if (Objects.isNull(billboardResponse.getId()) || billboardResponse.getId().isEmpty() ) {
                return BILLBOARD_INVALID + data[1];
            }

            transaction.setPhone(session);
            transaction.setLicense_plate(data[2]);
            transaction.setBillboards_code(data[1]);
            HourGeneration datetime = new HourGeneration();
            transaction.setDate(datetime.getDateFormat());
            transaction.setHour(datetime.getHourFormat());
            transaction.setTime("0");
            transaction.setPrice("0");
            transaction.setAction("I");

            String resp = apiManager.setTemporalTransaction(transaction);
            if (resp.equals("S"))
                return START_TRANSACTION_SUCCESS + data[2];
            return resp;
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String endService(String queryText) {
        //COMANDO = FIN PLACA
        String[] data = messageHelper.asArray(queryText);
        String response = "";
       if(2!=data.length) {
           return REQUEST_DATA_ERROR_END_SERVICE;
       }
      try {
            RateDto rate = apiManager.getRate();
            if (Objects.isNull(rate.getValue()) || rate.getId().isEmpty() ) {
                return RATE_NOT_FOUND;
            }
            String value = rate.getValue();
            TransactionDto currentTransaction = apiManager.getConfirmedTransactionByFacePlate(data[1]);
            if (Objects.isNull(currentTransaction.getId()) || currentTransaction.getId().isEmpty() ) {
                return CONFIRMED_TRANSACTION_NOT_EXIST + data[1];
          }
            TransactionTDto endTransaction = new TransactionTDto();
            endTransaction.setPhone(currentTransaction.getPhone());
            endTransaction.setLicense_plate(currentTransaction.getLicense_plate());
            endTransaction.setBillboards_code(currentTransaction.getBillboards_code());
            HourGeneration datetime = new HourGeneration();
            TimeCalculation timeCalculation = new TimeCalculation();
            String endDate = datetime.getDateFormat();
            String endTime = datetime.getHourFormat();
            endTransaction.setDate(endDate);
            endTransaction.setHour(endTime);
            String dat[]={currentTransaction.getStart_date()+" "+currentTransaction.getStart_time(),endDate+" "+endTime};
            Long minutes = timeCalculation.TimeCalculation(dat);
            endTransaction.setTime(Long.toString(minutes));
            Long price = Long.parseLong(value) * minutes;
            endTransaction.setPrice(Long.toString(price));
            endTransaction.setAction("F");
            String reponse = "S";
            response = apiManager.setTemporalTransaction(endTransaction);
            if(!response.equals("S")){
                return response;
            }
            currentTransaction.setClosed("P");
            FaceplateDto faceplateResponse = apiManager.getFaceplateByFaceplate(data[1]);
            if (Objects.isNull(faceplateResponse.getId()) || faceplateResponse.getId().isEmpty() ) {
                apiManager.updateTransaction(currentTransaction);
                return TRANSACTION_COMPLETED+data[1]+TRANSACTION_COMPLETED_TIME+minutes+TRANSACTION_COMPLETED_PAYMENT+price+", favor realice su pago en efectivo al operador Pappking, Le invitamos a registrarse como usuario frecuente para que disfrute los beneficios, Consulte al operador.";
            }
            BalanceDto balance = apiManager.getCustomerBalance(faceplateResponse.getCustomerid());
            if (balance.getBalance() < price ) {
              apiManager.updateTransaction(currentTransaction);
              return TRANSACTION_COMPLETED+data[1]+TRANSACTION_COMPLETED_TIME+minutes+TRANSACTION_COMPLETED_PAYMENT+price+ ", Su saldo no es suficiente para el pago de esta transaccion por favor realice su pago en efectivo al operador pappking";
            }
            PaymentRequestDto paymentService = new PaymentRequestDto();
            paymentService.setCustomerId(faceplateResponse.getCustomerid());
            paymentService.setServiceId(currentTransaction.getId());
            paymentService.setOperator(false);
            paymentService.setAmount(price);
            apiManager.payService(paymentService);
            apiManager.updateTransaction(currentTransaction);
            return TRANSACTION_COMPLETED+data[1]+TRANSACTION_COMPLETED_TIME+minutes+TRANSACTION_COMPLETED_PAYMENT+price+", Su pago fue descontado de su saldo Pappking";

            //LLAMADA A PAGOS y PROMOCIONES

        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @Override
    public String startConfirmation(String queryText) {
        //ENTRADA PLACA
        String[] data = messageHelper.asArray(queryText);
        String response = "";
        if(2!=data.length) {
            return REQUEST_DATA_ERROR_START_CONFIRMATION;
        }
        try {
            TransactionTDto temporalTransaction = apiManager.getInitTransactionByFacePlate(data[1]);
            if (Objects.isNull(temporalTransaction.getId()) || temporalTransaction.getId().isEmpty() ) {
                return CONFIRMED_TRANSACTION_NOT_EXIST + data[1];
            }
            TransactionDto transaction = new TransactionDto();
            transaction.setPhone(temporalTransaction.getPhone());
            transaction.setLicense_plate(temporalTransaction.getLicense_plate());
            transaction.setBillboards_code(temporalTransaction.getBillboards_code());
            transaction.setStart_date(temporalTransaction.getDate());
            transaction.setStart_time(temporalTransaction.getHour());
            transaction.setEnd_date("");
            transaction.setEnd_time("");
            transaction.setTime(temporalTransaction.getTime());
            transaction.setPrice(temporalTransaction.getPrice());
            transaction.setClosed("N");
            apiManager.setConfirmedInitTransactionByFacePlate(transaction);
            apiManager.deleteTemporalTransaction(temporalTransaction.getId());

            response = START_CONFIRMATION_SUCCESS+data[1];

        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @Override
    public String endConfirmation(String queryText) {
        // SALIDA  PLACA CEDULA_OPERADOR
        String[] data = messageHelper.asArray(queryText);
        String response = "";
        if(3!=data.length) {
            return REQUEST_DATA_ERROR_END_CONFIRMATION;
        }
        try{
            TransactionTDto temporalTransaction = apiManager.getEndTransactionByFacePlate(data[1]);
            if (Objects.isNull(temporalTransaction.getId()) || temporalTransaction.getId().isEmpty() ) {
                return END_TRANSACTION_NOT_EXIST;
            }
            TransactionDto currentTransaction = apiManager.getConfirmedTransactionByFacePlate(data[1]);
            if (Objects.isNull(currentTransaction.getId()) || currentTransaction.getId().isEmpty() ) {
                return INIT_CONFIRMED_TRANSACTION_NOT_EXIST;
            }
            TransactionDto transaction = new TransactionDto();
            transaction.setId(currentTransaction.getId());
            transaction.setPhone(currentTransaction.getPhone());
            transaction.setLicense_plate(currentTransaction.getLicense_plate());
            transaction.setBillboards_code(currentTransaction.getBillboards_code());
            transaction.setStart_date(currentTransaction.getStart_date());
            transaction.setStart_time(currentTransaction.getStart_time());
            transaction.setEnd_date(temporalTransaction.getDate());
            transaction.setEnd_time(temporalTransaction.getHour());
            transaction.setTime(temporalTransaction.getTime());
            transaction.setPrice(temporalTransaction.getPrice());
            transaction.setClosed("S");
            apiManager.updateTransaction(transaction);
            apiManager.deleteTemporalTransaction(temporalTransaction.getId());
            PaymentServiceDto currentPayment = apiManager.getPaymentService(currentTransaction.getId());
            if (Objects.nonNull(currentPayment.getId()) && !currentPayment.getId().isEmpty() && currentPayment.getStatus().equals("APPROVED") ) {
                return END_CONFIRMATION_SUCCESS+data[1]+" La transaccion ya fue pagada";
            }
            //BUSCAR OPERADOR
            //LLAMADA A PAGOS Y TRANSACCIONES
            return END_CONFIRMATION_SUCCESS+data[1];

        }catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String startAuthorization(String queryText, String session) {
        String[] data = messageHelper.asArray(queryText);
        //COMIENZO CEDULA COD_AUTORIZACION VALLA PLACA
        if(5 != data.length) {
            return REQUEST_DATA_ERROR_START_AUTORIZATION;
        }
        try {
            WorkCodeDto workCode = apiManager.getWorkCodeByAuthorizationCode(data[2]);
            if (Objects.isNull(workCode.getId()) || workCode.getId().isEmpty()) {
                return WORK_CODE_NOT_EXIST;
            }
            BillboardDto billboardResponse = apiManager.getBillboardByCode(data[3]);
            if (Objects.isNull(billboardResponse.getId()) || billboardResponse.getId().isEmpty()) {
                return BILLBOARD_INVALID + data[3];
            }
            OperatorDto operator = apiManager.getOperatorById(workCode.getOperatorId());
            if (Objects.isNull(operator.getId()) || operator.getId().isEmpty()) {
                return OPERATOR_INVALID;
            }
            if(!data[1].equals(operator.getDocument_number()) ){
                return OPERATOR_BILLBOARD_INVALID;
            }
            TransactionTDto temporalTransaction = apiManager.getInitTransactionByFacePlate(data[4]);
            if (Objects.nonNull(temporalTransaction.getId()) && !temporalTransaction.getId().isEmpty() ) {
                return START_SERVICE_EXISTS + data[4];
            }

            TransactionDto transaction = new TransactionDto();
            transaction.setPhone(session);
            transaction.setLicense_plate(data[4]);
            transaction.setBillboards_code(data[3]);
            HourGeneration datetime = new HourGeneration();
            transaction.setStart_date(datetime.getDateFormat());
            transaction.setStart_time(datetime.getHourFormat());
            transaction.setEnd_date("");
            transaction.setEnd_time("");
            transaction.setTime("0");
            transaction.setPrice("0");
            transaction.setClosed("N");
            String resp = apiManager.setAutorizationInitTransactionByFacePlate(transaction);
            if (resp.equals("S"))
                return START_TRANSACTION_SUCCESS + data[4];
            return resp;
        }catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String endAuthorization(String queryText) {
        String[] data = messageHelper.asArray(queryText);
        //FINAL CEDULA COD_AUTORIZACION PLACA
        if(4 != data.length) {
            return REQUEST_DATA_ERROR_END_AUTORIZATION;
        }
        try {
            WorkCodeDto workCode = apiManager.getWorkCodeByAuthorizationCode(data[2]);
            if (Objects.isNull(workCode.getId()) || workCode.getId().isEmpty()) {
                return WORK_CODE_NOT_EXIST;
            }
            OperatorDto operator = apiManager.getOperatorById(workCode.getOperatorId());
            if (Objects.isNull(operator.getId()) || operator.getId().isEmpty()) {
                return OPERATOR_INVALID;
            }
            if(!data[1].equals(operator.getDocument_number())){
                return OPERATOR_BILLBOARD_INVALID;
            }
            RateDto rate = apiManager.getRate();
            if (Objects.isNull(rate.getValue()) || rate.getId().isEmpty() ) {
                return RATE_NOT_FOUND;
            }
            String value = rate.getValue();
            TransactionDto currentTransaction = apiManager.getConfirmedTransactionByFacePlate(data[3]);
            if (Objects.isNull(currentTransaction.getId()) || currentTransaction.getId().isEmpty() ) {
                return INIT_CONFIRMED_TRANSACTION_NOT_EXIST;
            }
            currentTransaction.setId(currentTransaction.getId());
            currentTransaction.setPhone(currentTransaction.getPhone());
            currentTransaction.setLicense_plate(currentTransaction.getLicense_plate());
            currentTransaction.setBillboards_code(currentTransaction.getBillboards_code());
            currentTransaction.setStart_date(currentTransaction.getStart_date());
            currentTransaction.setStart_time(currentTransaction.getStart_time());
            HourGeneration datetime = new HourGeneration();
            TimeCalculation timeCalculation = new TimeCalculation();
            String endDate = datetime.getDateFormat();
            String endTime = datetime.getHourFormat();
            currentTransaction.setEnd_date(endDate);
            currentTransaction.setEnd_time(endTime);
            String dat[]={currentTransaction.getStart_date()+" "+currentTransaction.getStart_time(),endDate+" "+endTime};
            Long minutes = timeCalculation.TimeCalculation(dat);
            currentTransaction.setTime(Long.toString(minutes));
            Long price = Long.parseLong(value) * minutes;
            currentTransaction.setPrice(Long.toString(price));
            currentTransaction.setClosed("S");
            apiManager.updateTransaction(currentTransaction);
            return TRANSACTION_COMPLETED+data[3]+TRANSACTION_COMPLETED_TIME+minutes+TRANSACTION_COMPLETED_PAYMENT+price;

        }catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public String authorizedConsultation(String queryText) {
        String[] data = messageHelper.asArray(queryText);
        //CONSULTA CEDULA CODIGO PLACA
        if(4 != data.length) {
            return REQUEST_DATA_ERROR_AUTORIZED_CONSULTATION;
        }
        try {
            WorkCodeDto workCode = apiManager.getWorkCodeByAuthorizationCode(data[2]);
            if (Objects.isNull(workCode.getId()) || workCode.getId().isEmpty()) {
                return WORK_CODE_NOT_EXIST;
            }
            OperatorDto operator = apiManager.getOperatorById(workCode.getOperatorId());
            if (Objects.isNull(operator.getId()) || operator.getId().isEmpty()) {
                return UNAUTHORIZED_CONSULTATION;
            }
            TransactionDto currentTransaction = apiManager.getConfirmedTransactionByFacePlate(data[3]);
            if (Objects.nonNull(currentTransaction.getId()) && !currentTransaction.getId().isEmpty() ) {
                return AUTHORIZED + currentTransaction.getBillboards_code();
            }
            TransactionTDto temporlTransaction = apiManager.getInitTransactionByFacePlate(data[3]);
            if (Objects.nonNull(temporlTransaction.getId()) && !temporlTransaction.getId().isEmpty() ) {
                return AUTHORIZED + temporlTransaction.getBillboards_code();
            }
            return UNAUTHORIZED;


        }catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



    @Override
    public String updateBillboard(String queryText) {

        String[] data = messageHelper.asArray(queryText);
        if(4 != data.length) {

            return REQUEST_DATA_ERROR_CUSTOMER;
        }

        BillboardDto currentBillboard = apiManager.getBillboardByCode(data[1]);

        if(Objects.isNull(currentBillboard.getId()) || currentBillboard.getId().isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }

        BillboardDto newBillboard = new BillboardDto();

        newBillboard.setId(currentBillboard.getId());
        newBillboard.setCode(data[2]);
        newBillboard.setAddress(data[3]);

        apiManager.updateBillboard(newBillboard);

        /**TODO: Update this success message */
        return START_TRANSACTION_SUCCESS;
    }

    @Override
    public String deleteBillboard(String queryText) {
        String[] data = messageHelper.asArray(queryText);
        if(2 != data.length) {
            /**TODO: Update this error message */
            return REQUEST_DATA_ERROR_CUSTOMER;
        }
        BillboardDto currentBillboard = apiManager.getBillboardByCode(data[1]);

        if(Objects.isNull(currentBillboard.getId()) || currentBillboard.getId().isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
        apiManager.delleteBillboard(currentBillboard.getId());
        /**TODO: Update this success message */
        return START_TRANSACTION_SUCCESS;
    }

    @Override
    public BalanceDto getBalance(String customerId){
        return apiManager.getCustomerBalance(customerId);
    }

    @Override
    public PaymentServiceDto getPaymentService(String serviceId) {
        return apiManager.getPaymentService(serviceId);
    }

    @Override
    public SimpleResponseDto payService(PaymentRequestDto payment) {
        return apiManager.payService(payment);
    }

    @Override
    public SimpleResponseDto createPaymentCustomer(CreatePaymentCustomerDto request) {
        return apiManager.createPaymentCustomer(request);
    }

    @Override
    public String checkCustomerBalance(String queryText) {
        /** TODO Implement business logic for Start Confirmation */
        return "Prueba consultaSaldoCliente";
    }

    @Override
    public String checkCompanyBalance(String queryText) {
        /** TODO Implement business logic for Start Confirmation */
        return "Prueba consultaSaldoEmpresa";
    }


    @Override
    public String reloadCustomerBalance(String queryText) {
        /** TODO Implement business logic for Start Confirmation */
        return "Prueba recargarSaldoCliente";
    }

    @Override
    public String reloadCompanyBalance(String queryText) {
        /** TODO Implement business logic for Start Confirmation */
        return "Prueba recargarSaldoEmpresa";
    }

    @Override
    public String generatePromotion(String queryText) {
        /** TODO Implement business logic for Start Confirmation */
        return "Prueba generaPromocion";
    }

    @Override
    public String redeemPromotion(String queryText) {
        /** TODO Implement business logic for Start Confirmation */
        return "Prueba redimePromocion";
    }

    @Override
    public String chargeBalance(String queryText) {
        String[] data = messageHelper.asArray(queryText);
        //recarga cedula
        if(2 != data.length) {
            /**TODO: Update this error message */
            return REQUEST_DATA_ERROR_CHARGE_BALANCE;
        }
        CustomerDto customerResponse = apiManager.getCustomerByIdentification(data[1]);
        if (Objects.isNull(customerResponse.getId()) || customerResponse.getId().isEmpty()) {
            return CUSTOMER_NOT_EXISTS;
        }
        /** TODO Implement business logic for Start Confirmation */
        return URL_CHARGE_BALANCE + customerResponse.getId();

    }

}
