package co.ppk.exception.advice;

import co.ppk.exception.Codes;
import co.ppk.exception.PpkFieldValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h1>Exception controller advice</h1>
 * This ExceptionControllerAdvice handles all the global exception scenarios in application.
 * <p>
 * <b>Note:</b> All exception related scenarios handled by this class.
 * @author jmunoz
 */

@ControllerAdvice
public class ExceptionControllerAdvice {

  /** The log variable */
  private final Logger log = LoggerFactory.getLogger(ExceptionControllerAdvice.class);


  @ExceptionHandler(PpkFieldValidationException.class)
  public ResponseEntity<Object> handleDppmFieldValidationException(PpkFieldValidationException ex) {
    return processBindingError(ex.getBindingResult());
  }

  
  private ResponseEntity<Object> processBindingError(BindingResult result ) {
	    List<FieldError> validationErrors = result.getFieldErrors();

	    java.lang.Error error = new java.lang.Error(Codes.FIELDS_VALIDATION_ERROR.getErrorCode());

	    for (FieldError fieldError : validationErrors) {
//	    	error.addFieldError(fieldError.getCode(), fieldError.getField(), fieldError.getDefaultMessage());
	    }



	    Map<String, java.lang.Error> errorMap = new HashMap<>();
	    errorMap.put("Error", error);
	    return new ResponseEntity<Object>(errorMap, HttpStatus.BAD_REQUEST);
	  }
  
  

}
