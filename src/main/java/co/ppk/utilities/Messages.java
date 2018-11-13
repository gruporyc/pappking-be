package co.ppk.utilities;

public interface Messages {

    String CUSTOMER_REGISTER_SUCCESS = "Se ha registrado en Pappking";
    String CUSTOMER_ALREADY_EXISTS = "Ya usted se encuentra registrado en Pappking";
    String UNEXPECTED_ERROR = "Se ha producido un error, envia el comando de nuevo";
    String REQUEST_DATA_ERROR_CUSTOMER = "Error en datos, ejemplo de comando:  registro numeroDocumento nombre apellido correo";
    String FACEPLATE_REGISTER_SUCCESS = "Se ha registrado satisfactoriamente la placa ";
    String FACEPLATE_ALREADY_EXISTS = "La placa que intenta registrar ya existe";
    String CUSTOMER_NOT_EXISTS = "Este usuario no se encuentra registrado";
    String REQUEST_DATA_ERROR_FACEPLATE = "Error en datos, ejemplo de comando:  Placa numero_documento numero_placa";
    String START_TRANSACTION_SUCCESS = "Empieza el conteo de tiempo para el vehiculo placas: ";
    String START_SERVICE_EXISTS = "Ya existe conteo de tiempo Pappking para el vehiculo placas: ";
    String BILLBOARD_INVALID = "No existe la valla codigo: ";
    String REQUEST_DATA_ERROR_START_SERVICE = "Error en datos, ejemplo de comando:  inicio codigoValla Placa";
    String TRANSACTION_COMPLETED = "Finalizo el conteo del tiempo Pappking para la placa: ";
    String TRANSACTION_COMPLETED_TIME = " su tiempo de parqueo es: ";
    String TRANSACTION_COMPLETED_PAYMENT = "minutos el valor Pappking es: ";

    String TRANSACTION_COMPLETED_ERROR = "Se produjo un error al finalizar su tiempo Pappking, por favor intente de nuevo";
    String REQUEST_DATA_ERROR_END_SERVICE = "Error en datos, ejemplo de comando:  FIN Placa";
    String CONFIRMED_TRANSACTION_NOT_EXIST = "No existe una transaccion de inicio confirmada para el vehiculo placas: ";
    String INIT_CONFIRMED_TRANSACTION_NOT_EXIST = "No existe una transaccion de inicio confirmada para continuar el procesos de finalizacion de servicio";
    String END_TRANSACTION_NOT_EXIST = "No existe una transaccion finalizada pendiente por confirmar";
    String REQUEST_DATA_ERROR_START_CONFIRMATION = "Error en datos, ejemplo de comando:  ENTRADA placa";
    String START_CONFIRMATION_SUCCESS = "Se confirmo la transaccion de inicio de manera exitosa para el vehiculo placa: ";
    String END_CONFIRMATION_SUCCESS = "Se confirmo la transaccion de Fin de manera exitosa para el vehiculo placa: ";
    String START_CONFIRMATION_ERROR = "Se genera un error en la confirmacion por favor intenta de nuevo ";
    String REQUEST_DATA_ERROR_END_CONFIRMATION = "Error en datos, ejemplo de comando:  SALIDA placa";
    String RATE_NOT_FOUND = "No se encuentra valor de tarifa, consultar con el operador Pappking";
    String WORK_CODE_NOT_EXIST = "No existe el codigo de Autorizacion";
    String OPERATOR_INVALID = "NO SE ENCUENTRA OPERADOR VALIDO PARA INICIAR TRANSACCION";
    String OPERATOR_BILLBOARD_INVALID = "OPERADOR NO ASIGNADO A VALLA";
    String REQUEST_DATA_ERROR_START_AUTORIZATION = "ERROR DE COMANDO, EJEMPLO COMANDO VALIDO: COMIENZO CEDULA COD_AUTORIZACION VALLA PLACA";
    String REQUEST_DATA_ERROR_END_AUTORIZATION = "ERROR DE COMANDO, EJEMPLO COMANDO VALIDO: COMIENZO CEDULA COD_AUTORIZACION PLACA";
    String REQUEST_DATA_ERROR_AUTORIZED_CONSULTATION = "ERROR DE COMANDO, EJEMPLO COMANDO VALIDO: CONSULTA CEDULA CODIGO PLACA";
    String UNAUTHORIZED_CONSULTATION = "NO ESTA AUTORIZADO PARA REALIZAR ESTA CONSULTA";
    String AUTHORIZED = "VEHICULO AUTORIZADO PARA PARQUEAR EN LA VALLA: ";
    String UNAUTHORIZED = "VEHICULO NO AUTORIZADO PARA SERVICIO PAPPKING";
    String REQUEST_DATA_ERROR_CHARGE_BALANCE = "COMANDO INVALIDO, ENVIE LA PALABRA RECARGA MAS SU NUMERO DE DOCUEMNTO, EJEMPLO: RECARGA 12345678965";
    String URL_CHARGE_BALANCE=  "Siga el siguiente enlace para realizar su recarga de saldo: \n   http://payments.pappkingws.com/payments/customer/";
    String REQUEST_DATA_ERROR_CHECK_CUSTOMER_BALANCE = "COMANDO INVALIDO, EJEMPLO DE COMANDO: CONSULTA NUMERO_DOCUMENTO";
    String GET_BALANCE_ERROR = "SE PRESENTO UN ERROR RECUPERANDO EL SALDO ACTUAL, FAVOR INTENTE DE NUEVO O CONSULTE CON UN OPERADOR PAPPKING";
    String CUSTOMER_BALANCE_INFORMATION = "CONSULTA DE SALDO PARA CLIENTE: ";
    String CUSTOMER_BALANCE_AMOUNT = " SU SALDO ACTUAL ES: ";



}