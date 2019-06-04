package co.ppk.validators;

import co.ppk.dto.Message;
import co.ppk.exception.Codes;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

public class MessageValidator extends BaseValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target.getClass().equals(Message.class)) {
            Message message = (Message) target;
            if (Objects.isNull(message.getQueryResult()) || StringUtils.isEmpty(message.getQueryResult())) {
                errors.rejectValue("queryResult", Codes.QUERY_RESULT_CANNOT_BE_NULL.getErrorCode());
            }

            /** TODO: Implement all validators for input message object */
        }
    }
}
