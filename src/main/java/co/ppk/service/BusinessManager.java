package co.ppk.service;

public interface BusinessManager {

    String customersRegister(String queryText);

    String registerFacePlate(String queryText);

    String companyRegister(String queryText);

    String initService(String queryText, String session);

    String endService(String queryText);

    String startConfirmation(String queryText);

    String endConfirmation(String queryText);

    String endAuthorization(String queryText);

    String updateBillboard(String billboard);

    String deleteBillboard(String billboardId);

    String startAuthorization(String queryText);

    String authorizedConsultation(String queryText);

    String checkCustomerBalance(String queryText);

    String checkCompanyBalance(String queryText);

    String reloadCustomerBalance(String queryText);

    String reloadCompanyBalance(String queryText);

    String redeemPromotion(String queryText);

    String generatePromotion(String queryText);






}
