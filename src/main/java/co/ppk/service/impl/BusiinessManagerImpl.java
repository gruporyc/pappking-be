package co.ppk.service.impl;

import co.ppk.dto.*;
import co.ppk.service.APIManager;
import co.ppk.service.BusinessManager;
import co.ppk.utilities.HourGeneration;
import co.ppk.utilities.MessageHelper;
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
public class BusiinessManagerImpl implements BusinessManager{

    @Autowired
    APIManager apiManager;

    @Autowired
    MessageHelper messageHelper;

    @Override
    public String customersRegister(String queryText) {
        /** TODO Implement business logic for create register */

        String[] data = messageHelper.asArray(queryText);
        String response = "";
        if(data.length==5) {
            CustomerDto customer = new CustomerDto();
            customer.setIdentification(data[1]);
            customer.setName(data[2]);
            customer.setLastName(data[3]);
            customer.setEmail(data[4]);

            try {
                APIResponse apiResponse = apiManager.createCustomer(customer);
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
    public String registerFacePlate(String queryText) {
        /** TODO Implement business logic for end service */
        String response = "";
        String[] data = messageHelper.asArray(queryText);
        if(data.length == 3) {
            try {
                APIResponse customerResponse = apiManager.getCustomerByIdentification(data[1]);
                if (customerResponse.getHttpCode() == 200) {
                    Gson gson = new Gson();
                    String customer = String.valueOf(new JSONObject(gson.toJson(customerResponse.getBody())).get("customer"));
                    String id = String.valueOf(new JSONObject(customer).get("id"));

                    APIResponse facePlateResponse = apiManager.setFaceplate(id, data[2]);
                    switch (facePlateResponse.getHttpCode()) {
                        case 200:
                            response = FACEPLATE_REGISTER_SUCCESS + data[2];
                            break;
                        case 304:
                            response = FACEPLATE_ALREADY_EXISTS;
                            break;
                    }
                }
            } catch (HttpClientErrorException e) {
                if (e.getStatusCode().value() == 404) {
                    response = CUSTOMER_NOT_EXISTS;
                } else {
                    response = UNEXPECTED_ERROR;
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                response = UNEXPECTED_ERROR;
                e.printStackTrace();
            }
        } else {
            response =  REQUEST_DATA_ERROR_FACEPLATE;
        }
        return response;
    }

    @Override
    public String companyRegister(String queryText) {
        /** TODO Implement business logic for end service */

        String[] data = messageHelper.asArray(queryText);
        String response = "";
        if(data.length==4) {
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
        /** TODO Implement business logic for init service */
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

            apiManager.setTemporalTransaction(transaction);

            return START_TRANSACTION_SUCCESS + data[2];
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String endService(String queryText) {
        /** TODO Implement business logic for end service */
        String[] data = messageHelper.asArray(queryText);
        String response = "";
//        if(data.length==2) {
//            try {
//                //se busca la tarifa
//                APIResponse transactionResponse = apiManager.getRate();
//                if (transactionResponse.getHttpCode() == 200) {
//                    Gson gson = new Gson();
//                    String activeRate = String.valueOf(new JSONObject(gson.toJson(transactionResponse.getBody())).get("rate"));
//                    String value = String.valueOf(new JSONObject(activeRate).get("value"));
//                    //se consulta el servicio de transacciones para validar que la transaccion esta abierta y confirmada
//                    //Abierta = tabla temporal status I,  confirmada = tabla final cerrada = N
//                     transactionResponse = apiManager.getConfirmedTransactionByFacePlate(data[1]);
//                    if (transactionResponse.getHttpCode() == 200) {
//                        //este servicio debe devolver el calculo del tiempo y el monto a cobrar segun la tarifa vigente
//                         gson = new Gson();
//                        String transaction = String.valueOf(new JSONObject(gson.toJson(transactionResponse.getBody())).get("transaction"));
//                        String id = String.valueOf(new JSONObject(transaction).get("id"));
//                        String phone = String.valueOf(new JSONObject(transaction).get("phone"));
//                        String license_plate = String.valueOf(new JSONObject(transaction).get("license_plate"));
//                        String billboards_code = String.valueOf(new JSONObject(transaction).get("billboards_code"));
//                        String start_date = String.valueOf(new JSONObject(transaction).get("start_date"));
//                        String start_time = String.valueOf(new JSONObject(transaction).get("start_time"));
//                        TransactionTDto endTransaction = new TransactionTDto();
//                        endTransaction.setPhone(phone);
//                        endTransaction.setLicense_plate(license_plate);
//                        endTransaction.setBillboards_code(billboards_code);
//                        HourGeneration datetime = new HourGeneration();
//                        TimeCalculation timeCalculation = new TimeCalculation();
//                        String endDate = datetime.getDateFormat();
//                        String endTime = datetime.getHourFormat();
//                        endTransaction.setDate(endDate);
//                        endTransaction.setTime(endTime);
//                        String dat[]={start_date+" "+start_time,endDate+" "+endTime};
//                        Long minutes = timeCalculation.TimeCalculation(dat);
//                        endTransaction.setTime(Long.toString(minutes));
//                        Long price = Long.parseLong(value) * minutes;
//                        endTransaction.setPrice(Long.toString(price));
//                        endTransaction.setAction("F");
//                        //INSERTO LA TRANSACCION TEMPORAL DE FINALIZACION
//                        transactionResponse = apiManager.setTemporalTransaction(endTransaction);
//                        if (transactionResponse.getHttpCode() == 200) {
//                            //REALIZAR EL UPDATE DEL STATUS DE LA TRANSACCION A CERRADA = P
//                            transactionResponse = apiManager.putEndTransactionById(id);
//                            response = TRANSACTION_COMPLETED+data[1]+TRANSACTION_COMPLETED_TIME+minutes+TRANSACTION_COMPLETED_PAYMENT+price;
//                        }else{
//                            response = TRANSACTION_COMPLETED_ERROR;
//                        }
//                    }else{
//                        //se debe enviar un mensaje de que la transaccion no existe, se debe verificar que las respuesta del servicio sea un NotFound
//                        response=CONFIRMED_TRANSACTION_NOT_EXIST + data[1];
//                    }
//                }else response = "MENSAJE PARA DECIR QUE NO HAY TARIFA";
//            }catch (HttpClientErrorException e) {
//                response = UNEXPECTED_ERROR;
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//        }else{
//            response = REQUEST_DATA_ERROR_END_SERVICE;
//
//        }
        return response;
    }


    @Override
    public String startConfirmation(String queryText) {
        /** TODO Implement business logic for Start Confirmation */
        String[] data = messageHelper.asArray(queryText);
        String response = "";
//        if(data.length==2) {
//            //Se verifica q la transaccion  existe (debe estar abierta (tabla temporal con ACTION=I))
//            APIResponse transactionResponse = apiManager.getInitTransactionByFacePlate(data[1]);
//            if (transactionResponse.getHttpCode() == 200) {
//                //confirmar la transaccion de inicio, insertar en tabla definitiva y elimminar de la temporal
//
//                TransactionTDto transaction = new TransactionTDto();
//                transactionResponse = apiManager.setConfirmedInitTransactionByFacePlate(transaction);
//                if (transactionResponse.getHttpCode() == 200) {
//                    //aqui se debe eliminar la transaccion temporal
//                    //se realizo la confirmacion de forma exitosa
//                    response = START_CONFIRMATION_SUCCESS+data[1];
//                }else{
//                    //se genero un error al momento de confirmar la transaccion
//                    response = START_CONFIRMATION_ERROR;
//                }
//
//            }else{
//                //confirmar transaccion
//                response = CONFIRMED_TRANSACTION_NOT_EXIST;
//            }
//
//        }else{
//            response = REQUEST_DATA_ERROR_START_CONFIRMATION;
//
//        }
        return response;
    }

    @Override
    public String endConfirmation(String queryText) {
        /** TODO Implement business logic for Start Confirmation */
        String[] data = messageHelper.asArray(queryText);
        String response = "";
        if(data.length==2) {
            APIResponse transactionResponse = apiManager.getConfirmedEndTransactionByFacePlate(data[1]);
            if (transactionResponse.getHttpCode() == 200) {

            }

        }else{

        }
        return "Prueba Confirmacion de Fin";
    }

    @Override
    public String updateBillboard(String queryText) {

        String[] data = messageHelper.asArray(queryText);
        if(4 != data.length) {
            /**TODO: Update this error message */
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
    public String startAuthorization(String queryText) {
        /** TODO Implement business logic for Start Confirmation */
        return "Prueba Autorizacion  de inicio";
    }

    @Override
    public String endAuthorization(String queryText) {
        /** TODO Implement business logic for Start Confirmation */
        return "Prueba Autorizacion  de fin";
    }

    @Override
    public String authorizedConsultation(String queryText) {
        /** TODO Implement business logic for Start Confirmation */
        return "Prueba consultaAutorizada";
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

}
