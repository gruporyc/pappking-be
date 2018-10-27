/******************************************************************
 *  
 * This code is for the Pappking service project.
 *
 * 
 * © 2018, Pappking Management All rights reserved.
 * 
 * 
 ******************************************************************/

package co.ppk.web.controller;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.ppk.dto.Message;
import co.ppk.service.BusinessManager;
import co.ppk.validators.MessageValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.ppk.enums.ResponseKeyName;
import co.ppk.service.APIManager;

/**
 * Only service exposition point of services to FE clients layer
 * 
 * @author jmunoz
 *
 */

@RestController
@RequestMapping("/v1")
public class ProxyEndpointController extends BaseRestController {

	private static final Logger LOGGER = LogManager.getLogger(ProxyEndpointController.class);

	private static final String CURRENT_USER_LOCALE = "language";

	/** The error properties. */
	@Autowired
	@Qualifier("errorProperties")
	private Properties errorProperties;

	@Autowired
	APIManager apiManager;

//	@Autowired
//	MessageValidator messageValidator;

	@Autowired
	BusinessManager businessManager;

	/**
	 * entry endpoint receiving the message from messaging API to perform proper action
	 *
	 * @since 30/06/2018
	 *
	 * @author jmunoz
	 * @version 1.0
	 */

	@RequestMapping(value = "/message", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> processMessage(@RequestHeader(required = false, value = CURRENT_USER_LOCALE) String language,
												 @RequestBody Message message, BindingResult result, HttpServletRequest request) {
       // messageValidator.validate(message, result);
		System.out.println("------------------------------------------------------------------- ");

		String serviceResponse = "";
        HashMap<String, String> response = new HashMap<>();
		try {
			switch(message.getQueryResult().getIntent().getDisplayName()) {

				case "registroClientes" :
					serviceResponse = businessManager.customersRegister(message.getQueryResult().getQueryText());
					break;

				case "registrarPlaca" :
					serviceResponse = businessManager.registerFacePlate(message.getQueryResult().getQueryText());
					break;

				case "registroEmpresas" :
					serviceResponse = businessManager.companyRegister(message.getQueryResult().getQueryText());
					break;

				case "inicioDeServicio" :
					serviceResponse = businessManager.initService(message.getQueryResult().getQueryText(), message.getSession());
					break;

				case "FinServicio" :
					serviceResponse = businessManager.endService(message.getQueryResult().getQueryText());
					break;

				case "ConfirmacionInicio" :
					serviceResponse = businessManager.startConfirmation(message.getQueryResult().getQueryText());
					break;

				case "ConfirmacionFin" :
					serviceResponse = businessManager.endConfirmation(message.getQueryResult().getQueryText());
					break;

				case "autorizacionInicio" :
					serviceResponse = businessManager.startAuthorization(message.getQueryResult().getQueryText(),message.getSession());
					break;

				case "autorizacionFin" :
					serviceResponse = businessManager.endAuthorization(message.getQueryResult().getQueryText());
					break;

				case "consultaAutorizada" :
					serviceResponse = businessManager.authorizedConsultation(message.getQueryResult().getQueryText());
					break;

				case "consultaSaldoCliente" :
					serviceResponse = businessManager.checkCustomerBalance(message.getQueryResult().getQueryText());
					break;

				case "consultaSaldoEmpresa" :
					serviceResponse = businessManager.checkCompanyBalance(message.getQueryResult().getQueryText());
					break;

				case "recargarSaldoCliente" :
					serviceResponse = businessManager.reloadCustomerBalance(message.getQueryResult().getQueryText());
					break;

				case "recargarSaldoEmpresa" :
					serviceResponse = businessManager.reloadCompanyBalance(message.getQueryResult().getQueryText());
					break;

				case "generaPromocion" :
					serviceResponse = businessManager.generatePromotion(message.getQueryResult().getQueryText());
					break;

				case "redimePromocion" :
					serviceResponse = businessManager.redeemPromotion(message.getQueryResult().getQueryText());
					break;

				case "actualizarValla" :
					serviceResponse = businessManager.updateBillboard(message.getQueryResult().getQueryText());
					break;

				case "eliminarValla" :
					serviceResponse = businessManager.deleteBillboard(message.getQueryResult().getQueryText());
					break;
			}
			response.put("fulfillmentText",serviceResponse);
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%"+response);
            return ResponseEntity.ok(response);
		}catch (Exception e) {
            response.put("fulfillmentText", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
	}
}
