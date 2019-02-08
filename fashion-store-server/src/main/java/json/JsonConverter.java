package json;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Set;

public class JsonConverter {

    public static String typeEmployeeToJsonString (String type) { // from server
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type",type);
        return jsonObject.toString();
    }

    public static String nameEmployeeToJsonString (String name) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",name);
        return jsonObject.toString();
    }

    public static String nameEmployeeFromJsonString (String nameJson) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject)parser.parse(nameJson);
        String type = json.get("name").toString();
        return type;
    }

    public static String typeEmployeeFromJsonString(String typeJson) throws ParseException { // in client
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject)parser.parse(typeJson);
        String type = json.get("type").toString();
        return type;
    }

    public static String exitLoginEmployeeToJsonString () {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action","exitEmployee");
        return jsonObject.toString();
    }

    public static String serverAnswerToJsonString (String type, String message) { // from server
        JSONObject json = new JSONObject();
        json.put("Type",type);
        json.put("message",message);
        return json.toString();
    }

    public static String [] serverAnswerFromJsonString (String jsonAnswer) throws ParseException { //in client
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject)parser.parse(jsonAnswer);
        String [] exception = new String [2];
        exception[0] = json.get("Type").toString();
        exception[1] = json.get("message").toString();
        return exception;
    }

    public static String [] convertJsonStringToActionAndJsonString (String details) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(details);
        String [] actionAndString = new String[2];
        actionAndString[0] = json.get("action").toString();
        json.remove("action");
        actionAndString[1] = json.toString();
        return actionAndString;
    }
    public static String getItemsListTOJsonString () { // to ask server from client
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action","getItemsList");
        return jsonObject.toString();
    }

    public static String createFirstManagerToJsonString (String details) { // from client
        JSONObject detailsObj = new JSONObject();
        detailsObj.put("details",details);
        detailsObj.put("action","firstManager");
        return detailsObj.toString();
    }

    public static String changePasswordRequestToJsonString (String password,String IDEmployee) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action","changePassword");
        jsonObject.put("IdEmployee",IDEmployee);
        jsonObject.put("password",password);
        return jsonObject.toString();
    }

    public static String [] changePasswordRequestFromJsonString (String jsonPassword) throws ParseException {
        String [] IdAndPassword = new String [2];
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject)parser.parse(jsonPassword);
        IdAndPassword [0] = jsonObject.get("IdEmployee").toString();
        IdAndPassword [1] = jsonObject.get("password").toString();
        return IdAndPassword;
    }

    public static String CreateFirstManagerFromJsonString(String jsonDetails) throws ParseException { // get in server
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(jsonDetails);
        String detailsFromJson = json.get("details").toString();
        return detailsFromJson;
    }


    public static String logInDetailsToJsonString(String userName,String password) { //from client
        JSONObject logInObj = new JSONObject();
        logInObj.put("userName",userName);
        logInObj.put("password",password);
        logInObj.put("action","logInEmployee");
        return logInObj.toString();
    }

    public static String [] logInDetailsFromJsonString(String jsonLogInDetails) throws ParseException { // get in sever
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(jsonLogInDetails);
        String [] logInDetails = new String[2];
        logInDetails[0] = json.get("userName").toString();
        logInDetails[1] = json.get("password").toString();
        return logInDetails;
    }

    public static String createNewEmployeeToJsonString(String typeEmployee,String details) { //from client
        JSONObject newEmployeeObj = new JSONObject();
        newEmployeeObj.put("type",typeEmployee);
        newEmployeeObj.put("details",details);
        newEmployeeObj.put("action","createNewEmployee");
        return newEmployeeObj.toString();
    }

    public static String[] createNewEmployeeFromJsonString(String jsonNewEmployeeDetails) throws ParseException { //get in server
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(jsonNewEmployeeDetails);
        String [] newEmployeeDetails = new String [2];
        newEmployeeDetails[0] = json.get("type").toString();
        newEmployeeDetails[1] = json.get("details").toString();

        return newEmployeeDetails;
    }

    public static String createNewCustomerToJsonString (String details) { // from client
        JSONObject newCustomerObj = new JSONObject();
        newCustomerObj.put("details",details);
        newCustomerObj.put("action","createNewCustomer");
        return newCustomerObj.toString();
    }

    public static String createNewCustomerFromJsonString(String jsonDetailsNewCustomer) throws ParseException { //get in server
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(jsonDetailsNewCustomer);
        String detailsCustomer = json.get("details").toString();
        return detailsCustomer;
    }

    public static String itemsListToJsonString(Set<String> itemsList) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("itemsSet",itemsList.toString());
        return jsonObject.toString();
    }

    public static String [] itemsListFromJsonString (String itemsListJson) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(itemsListJson);
        String itemsStringSet = json.get("itemsSet").toString();
        String [] itemStringSplit = (itemsStringSet.substring(0,itemsStringSet.length()-1)).split(", ");
        itemStringSplit[0] = "";
        return itemStringSplit;
    }

    public static String OnSellDetailsRequestToJsonString (String itemsList,String countOfItemsList,String IDCustomer) {
        JSONObject onSellObj = new JSONObject();
        onSellObj.put("itemList",itemsList);
        onSellObj.put("countOfItemsList",countOfItemsList);
        onSellObj.put("IDCustomer",IDCustomer);
        onSellObj.put("action","OnSellForCustomer");
        return onSellObj.toString();

    }

    public static String [] OnSellDetailsRequestFromJsonString(String jsonOnSellDetails) throws ParseException { //get in server
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(jsonOnSellDetails);
        String [] detailsOnSell = new String[3];
        detailsOnSell[0] = json.get("itemList").toString();
        detailsOnSell[1] = json.get("countOfItemsList").toString();
        detailsOnSell[2] = json.get("IDCustomer").toString();

        return detailsOnSell;
    }

    public static String reportToJsonString (HashMap<String,Integer> reportByItem) { //from server

        JSONObject jsonObject = new JSONObject();
        for (Object key : reportByItem.keySet()) {
            jsonObject.put(key.toString(), reportByItem.get(key));
        }
        return jsonObject.toString();

    }


    public static JSONObject reportFromJsonString (String reportByItemJson) throws ParseException {
        JSONParser parser = new JSONParser();
        System.out.println(reportByItemJson);
        JSONObject jsonObject = (JSONObject)parser.parse(reportByItemJson);
        System.out.println(jsonObject);
        return jsonObject;
    }

    public static String checkExistItemsToJsonString (String item) { // from client
        JSONObject existJson = new JSONObject();
        existJson.put("item",item);
        existJson.put("action","checkExistItem");
        return existJson.toString();
    }

    public static String checkExistItemFromJsonString (String jsonStringExistItem) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonExistItem = (JSONObject) parser.parse(jsonStringExistItem);
        String item = jsonExistItem.get("item").toString();
        return item;
    }

    public static String itemsCountExistToJson (int countItems) {
        JSONObject itemsJson = new JSONObject();
        itemsJson.put("countItems",countItems);
        itemsJson.put("action","checkExistItem");
        return itemsJson.toString();
    }

    public static String itemsCountExistFromJson (String itemsJsonString) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonCountExistItems = (JSONObject) parser.parse(itemsJsonString);
        String countItems = jsonCountExistItems.get("countItems").toString();
        return countItems;
    }

    public static String checkExistCustomerByIDToJsonString(String IDCustomer) { // in client
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action","checkExistCustomer");
        jsonObject.put("Id",IDCustomer);
        return jsonObject.toString();
    }

    public static String checkExistCustomerByIDFromJsonString (String jsonIdCustomer) throws ParseException { // in server from client
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject)parser.parse(jsonIdCustomer);
        String Id = jsonObject.get("Id").toString();
        return Id;
    }


    public static String answerIfExistCustomerToJsonString (String answer) { // from serve
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ifExist",answer);
        return jsonObject.toString();
    }

    public static String answerIfExistCustomerFromJsonString (String jsonAnswer) throws ParseException { // in server from client
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject)parser.parse(jsonAnswer);
        String answer = jsonObject.get("ifExist").toString();
        return answer;
    }

    public static String getDetailsOfCustomerRequestToJsonString (String IdCustomer) throws ParseException { // in server from client
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action","getDetailsCustomerRequest");
        jsonObject.put("Id",IdCustomer);
        return jsonObject.toString();
    }

    public static String getDetailsOfCustomerRequestFromJsonString (String jsonRequestDetailsCustomer) throws ParseException { // in server from client
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject)parser.parse(jsonRequestDetailsCustomer);
        String IdCustomer = jsonObject.get("Id").toString();
        return IdCustomer;
    }

    public static String detailsCustomerReturnToJsonString (String details) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("details",details);
        return jsonObject.toString();
    }

    public static String detailsCustomerReturnFromJsonString (String detailsJson) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject)parser.parse(detailsJson);
        String details = jsonObject.get("details").toString();
        return details;
    }

    public static String listOnlineEmployeesRequestToJsonString () {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action","listOnlineEmployees");
        return jsonObject.toString();
    }

    public static String priceItemRequestToJsonString (String item) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action","priceItem");
        jsonObject.put("item",item);
        return jsonObject.toString();
    }

    public static String priceItemRequestFromJsonString (String jsonItem) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonExistItem = (JSONObject) parser.parse(jsonItem);
        String item = jsonExistItem.get("item").toString();
        return item;
    }

    public static String priceItemAnswerToJsonString (double price) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("price",price);
        return jsonObject.toString();
    }

    public static String priceItemAnswerFromJsonString (String jsonPrice) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject)parser.parse(jsonPrice);
        String price = jsonObject.get("price").toString();
        return price;
    }

    public static String connectedRequestToJsonString () {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action","connectToChat");

        return jsonObject.toString();
    }

    public static String disConnectedRequestToJsonString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action","disConnectToChat");

        return jsonObject.toString();
    }

    public static String IdConnectedRequestToJsonString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action","IdConnected");
        return jsonObject.toString();
    }

    public static String IdDisConnectedRequestToJsonString () {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action","IdDisConnected");
        return jsonObject.toString();
    }

    public static String IdConnectedEmployeesListToJsonString (Set<String> IdEmployees) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("listId",IdEmployees);
        jsonObject.put("action","listIDConnectedEmployees");
        return jsonObject.toString();
    }

    public static String waitingForChatEmployeesListToJsonString (Set<String> IdEmployees) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("listId",IdEmployees);
        jsonObject.put("action","waitingForChat");
        return jsonObject.toString();
    }

    public static String IdDisConnectedEmployeesListToJsonString (Set<String> IdEmployees) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("listId",IdEmployees);
        jsonObject.put("action","listIDDisConnectedEmployees");
        return jsonObject.toString();
    }

    public static String [] IdConnectedAndDisEmployeesListFromJsonString (String IdEmployeesJson) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject)parser.parse(IdEmployeesJson);
        String IdEmployees = jsonObject.get("listId").toString();
        String [] IdListEmployees = (IdEmployees.substring(1,IdEmployees.length()-1)).split(",");
        return IdListEmployees;
    }

    public static String onSellRequestToJsonString (String details) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action","onSell");
        jsonObject.put("details",details);
        return jsonObject.toString();
    }

    public static String startChatWithRequestToJsonString (String IdToChat) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action","startChatWith");
        jsonObject.put("id",IdToChat);
        return jsonObject.toString();
    }

    public static String startChatWithRequestFromJsonString (String jsonRequestChatId) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject)parser.parse(jsonRequestChatId);
        String Id = jsonObject.get("id").toString();
        return Id;
    }

    public static String getConnectChat() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action","connect");
        return jsonObject.toString();
    }

    public static String messageFromClientToJsonString (String message,String action) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action",action);
        jsonObject.put("message",message);
        return jsonObject.toString();
    }

    public static String messageFromClientFromJsonString (String messageJson) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject)parser.parse(messageJson);
        String message = jsonObject.get("message").toString();
        return message;
    }

    public static String getListWaitingForChat() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action","getListWaitingForChat");
        return jsonObject.toString();
    }

    public static String IdWaitingToChatEmployeesListToJsonString (Set<String> IdEmployees) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("listId",IdEmployees);
        jsonObject.put("action","listIDDisConnectedEmployees");
        return jsonObject.toString();
    }

    public static String setDisConnectToJsonString () {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action","setToDisConnectEmployee");
        return jsonObject.toString();
    }

    public static String setConnectToJsonString () {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action","setToConnectEmployee");
        return jsonObject.toString();
    }

    public static String employeesAmountToJsonString (int amount) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("amount",amount);
        return jsonObject.toString();
    }

    public static String employeesAmountFromJsonString (String amountJson) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject)parser.parse(amountJson);
        String amount = jsonObject.get("amount").toString();
        return amount;
    }

    public static String getReportRequestToJsonString(String typeReport) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action",typeReport);
        return jsonObject.toString();

    }



}
