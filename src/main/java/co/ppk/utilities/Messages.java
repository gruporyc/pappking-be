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
    String TRANSACTION_COMPLETED_PAYMENT = " el valor Pappking es: ";
    String TRANSACTION_COMPLETED_ERROR = "Se produjo un error al finalizar su tiempo Pappking, por favor intente de nuevo";
    String REQUEST_DATA_ERROR_END_SERVICE = "Error en datos, ejemplo de comando:  FIN Placa";
    String CONFIRMED_TRANSACTION_NOT_EXIST = "No existe una transaccion de inicio confirmada para el vehiculo placas: ";
    String REQUEST_DATA_ERROR_START_CONFIRMATION = "Error en datos, ejemplo de comando:  ENTRADA placa";

    String START_CONFIRMATION_SUCCESS = "Se confirmo la transaccion de manera exitosa para el vehiculo placa: ";
    String START_CONFIRMATION_ERROR = "Se genera un error en la confirmacion por favor intenta de nuevo ";
    String RATE_NOT_FOUND = "No se encuentra valor de tarifa, consultar con el operador Pappking";
}