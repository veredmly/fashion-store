package logic;


import data.*;
import exceptions.*;
import json.JsonConverter;
import log.Log;
import org.json.simple.parser.ParseException;


import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;


public class ServerWorker {
    Chain chain;
    HashMap<String, Shop> shops;
    HashMap<String, String> shopsWithIpAddress = new HashMap<String, String>();
    Log log = new Log();
    public ServerWorker() {
        chain= new Chain();
        shops = new HashMap<>();
        shops.put("Holon",new Shop("Holon"));
        shops.put("Tel-aviv",new Shop("Tel-aviv"));
        chain.addShop(shops.get("Holon"));
        chain.addShop(shops.get("Tel-aviv"));

    }

    public Chain getChain() {
        return chain;
    }


    //create first manager
    public void createFirstManager (String detailsManager,String shopName) throws PhoneNumberException, IDException, AgeException, ParseException, BankAccountException, UserAlreadyExistException, org.json.simple.parser.ParseException {

        String details = JsonConverter.CreateFirstManagerFromJsonString(detailsManager);
        Employee employee = Employee.createEmployee("Manager",shops.get(shopName),chain.getCustomers(),details+","+shopName);
        ManagerEmployee Manager = (ManagerEmployee)employee;
        log.makeLog(Level.INFO,"Create first manager");
    }

    //log in to the system
    public String [] logIn(String logInDetails,String shopName,String ipOnlineEmployee) throws NotExistUserException, PasswordMistakeException, UserAlreadyOnlineException, ParseException, org.json.simple.parser.ParseException {
        String [] details = JsonConverter.logInDetailsFromJsonString(logInDetails);
        String [] typeAndNameEmployee = shops.get(shopName).logInEmployee(details[0],details[1],ipOnlineEmployee);
        return typeAndNameEmployee;
    }

    // change the password
    public void changePassword (String jsonPassword,String shopName) throws ParseException, PasswordMistakeException, NotExistUserException, org.json.simple.parser.ParseException {
        String [] details = JsonConverter.changePasswordRequestFromJsonString(jsonPassword);
        for (Employee employee: shops.get(shopName).getEmployeesList()) {
            if (employee.getId().equals(details[0])) {
                employee.setPassword(details[1]);
                return;
            }
        }
        throw new NotExistUserException();
    }

    //exit from system
    public void exitEmployee (String shopName,String ipClient) throws ParseException {
        shops.get(shopName).exitLogEmployee(ipClient);
    }

    //the online manager create new employee
    public void createNewEmployee(String newEmployeeDetails,String shopName,String ipOnlineEmployee) throws PhoneNumberException, IDException, AgeException, ParseException, NoIpExistException, BankAccountException, UserAlreadyExistException, org.json.simple.parser.ParseException {
        String [] details = JsonConverter.createNewEmployeeFromJsonString(newEmployeeDetails); // type,details
        String [] detailsSplit = details[1].split(",");
        if (shops.get(shopName).ifExistEmployee(detailsSplit[1])) {
            throw new UserAlreadyExistException();
        }
        Employee currentOnline = shops.get(shopName).getEmployeeOnlineByIp(ipOnlineEmployee);
        if (currentOnline instanceof ManagerEmployee) {
            ((ManagerEmployee)currentOnline).createNewEmployee(details[0],details[1]+","+shopName);
        }

        log.makeLog(Level.INFO,"Create new employee");

    }

    //return list json of items in inventory
    public String getItemsList (String shopName) throws IOException {
        String jsonStringItems = JsonConverter.itemsListToJsonString(shops.get(shopName).getItemList());
        return jsonStringItems;
    }

    //the online employee create new customer
    public void createNewCustomer(String customerDetails,String shopName,String ipOnlineEmployee) throws ParseException, NotEnoughDetailsForCustomerException, IDException, NoDetailsForCustomerException, NoIpExistException, PhoneNumberException, UserAlreadyExistException, org.json.simple.parser.ParseException { // get id customer
        String detailsCustomer = JsonConverter.createNewCustomerFromJsonString(customerDetails);
        String[] detailsSplit = detailsCustomer.split(",");
        if (chain.ifExistCustomer(detailsSplit[0]).equals("yes"))
            throw new UserAlreadyExistException();
        Employee currentOnline = shops.get(shopName).getEmployeeOnlineByIp(ipOnlineEmployee);
        if (currentOnline instanceof CashierEmployee ) {
            ((CashierEmployee)currentOnline).createCustomer(detailsCustomer);
        }
        else if (currentOnline instanceof ManagerEmployee) {
            ((ManagerEmployee)currentOnline).createCustomer(detailsCustomer);
        }

        log.makeLog(Level.INFO,"Create new customer");

    }

    //to on sell of customer - to this items
    public String OnSellToCustomer (String OnSellDetails,String shopName,String ipOnlineEmployee) throws ParseException, NotEnoughItemsToSellException, IOException, ItemNotFoundException, NoIpExistException, org.json.simple.parser.ParseException {
        String [] detailsOnSellFromJson = JsonConverter.OnSellDetailsRequestFromJsonString(OnSellDetails);
        Employee currentOnline = shops.get(shopName).getEmployeeOnlineByIp(ipOnlineEmployee);
        Customer customerOnSellFor = null;
        for (Customer customer: chain.getCustomers()) {
            if (customer.getID().equals(detailsOnSellFromJson[2])) {
                customerOnSellFor = customer;
                break;
            }
        }
        double sumOfPurchase = 0;
        if (currentOnline instanceof CashierEmployee) {
            sumOfPurchase = ((CashierEmployee)currentOnline).OnSell(detailsOnSellFromJson[0],detailsOnSellFromJson[1],customerOnSellFor,shops.get(shopName));
        }
        else if (currentOnline instanceof ManagerEmployee) {
            sumOfPurchase = ((ManagerEmployee)currentOnline).OnSell(detailsOnSellFromJson[0],detailsOnSellFromJson[1],customerOnSellFor,shops.get(shopName));

        }
        log.makeLog(Level.INFO,"Sell for customer");

        return JsonConverter.serverAnswerToJsonString("OK",String.valueOf(sumOfPurchase));
    }

    public String getReportByItemJsonString(String shopName) throws IOException {
        HashMap<String,Integer> reportByItem = shops.get(shopName).getReportByItem();
        String reportByItemJsonString = JsonConverter.reportToJsonString(reportByItem);
        return reportByItemJsonString;
    }

    public String getReportByCategoryToJsonString(String shopName) throws IOException {
        HashMap<String,Integer> reportByCategory = shops.get(shopName).getReportByCategory();
        String reportByCategoryJsonString = JsonConverter.reportToJsonString(reportByCategory);
        return reportByCategoryJsonString;
    }

    public String getReportByTypeToJsonString(String shopName) throws IOException {
        HashMap<String,Integer> reportByType = shops.get(shopName).getReportByType();
        String reportByTypeJsonString = JsonConverter.reportToJsonString(reportByType);
        return reportByTypeJsonString;
    }

    //return how much items exist in the inventory to this item
    public String checkCountExistItem(String jsonStringItemCheckExist,String shopName,String ipOnlineEmployee) throws ParseException, IOException, ItemNotFoundException, NoIpExistException, org.json.simple.parser.ParseException {
        String item = JsonConverter.checkExistItemFromJsonString(jsonStringItemCheckExist);
        Employee currentOnline =  shops.get(shopName).getEmployeeOnlineByIp(ipOnlineEmployee);
        int countItems = 0;
        if (currentOnline instanceof CashierEmployee) {
            countItems = ((CashierEmployee)currentOnline).OnExist(item);
        }
        else if (currentOnline instanceof ManagerEmployee) {
            countItems = ((ManagerEmployee)currentOnline).OnExist(item);
        }
        return JsonConverter.itemsCountExistToJson(countItems);
    }

    //return price of item
    public String getPriceOfItem (String jsonStringItemPrice,String shopName,String ipOnlineEmployee) throws ParseException, NoIpExistException, IOException, ItemNotFoundException, org.json.simple.parser.ParseException {
        String item = JsonConverter.priceItemRequestFromJsonString(jsonStringItemPrice);
        Employee currentOnline =  shops.get(shopName).getEmployeeOnlineByIp(ipOnlineEmployee);
        double priceItem = 0.0;
        if (currentOnline instanceof CashierEmployee) {
            priceItem = ((CashierEmployee)currentOnline).OnPriceCheck(item);
        }
        else if (currentOnline instanceof ManagerEmployee) {
            priceItem = ((ManagerEmployee)currentOnline).OnExist(item);
        }
        return JsonConverter.priceItemAnswerToJsonString(priceItem);
    }

    //return list of waiting employees for chat with this employee
    public String getWaitingToChatEmployeesList (String shopName, String ipOnlineEmployee) throws NoIpExistException {
        HashSet<String> waitingForChat = shops.get(shopName).getEmployeeOnlineByIp(ipOnlineEmployee).getWaitingForChatEmployees();
        String waitingForChatListJson = JsonConverter.waitingForChatEmployeesListToJsonString(waitingForChat);
        return waitingForChatListJson;
    }


    public void disconnectedFromChatPanel (String shopName,String ipOnlineEmployee) throws NoIpExistException {
        Employee currentOnline =  shops.get(shopName).getEmployeeOnlineByIp(ipOnlineEmployee);
        currentOnline.setConnected();
        shops.get(shopName).removeConnectedEmployee(currentOnline);
    }

    //return list of connected employees to chat
    public String getIdConnectedList (String shopName){
        Set<String> IdConnected = new HashSet<>();
        for (String nameShop: shops.keySet()) {
            if (!nameShop.equals(shopName)) {
                ((HashSet) IdConnected).addAll(shops.get(nameShop).getIdConnectedEmployees());
            }
        }
        String IdListEmployeesToJson = JsonConverter.IdConnectedEmployeesListToJsonString(IdConnected);
        return IdListEmployeesToJson;
    }

    //return list of dis connected employees to the chat
    public String getIdDisConnectedList (String shopName){
        Set<String> IdDisConnected = new HashSet<>();
        for (String nameShop: shops.keySet()) {
            if (!nameShop.equals(shopName)) {
                IdDisConnected.addAll(shops.get(nameShop).getIdDisConnectedEmployees());
            }
        }
        String IdListEmployeesToJson = JsonConverter.IdDisConnectedEmployeesListToJsonString(IdDisConnected);
        return IdListEmployeesToJson;
    }

    //check if this customer exist , to do sell
    public String checkExistCustomer (String jsonStringIdCustomer) throws ParseException, org.json.simple.parser.ParseException {
        String IdCustomer = JsonConverter.checkExistCustomerByIDFromJsonString(jsonStringIdCustomer);
        String answerCustomer = chain.ifExistCustomer(IdCustomer);
        String answerJson = JsonConverter.answerIfExistCustomerToJsonString(answerCustomer);
        return answerJson;
    }

    //return the details of customer
    public String getDetailsCustomer (String jsonStringIdCustomer) throws ParseException, org.json.simple.parser.ParseException {
        String IdCustomer = JsonConverter.getDetailsOfCustomerRequestFromJsonString(jsonStringIdCustomer);
        String detailsCustomer = chain.getDetailsCustomer(IdCustomer);
        String jsonDetails = JsonConverter.detailsCustomerReturnToJsonString(detailsCustomer);
        return jsonDetails;
    }

    //check if the employee available to chat and if yes - change to connect and in chat for bouth of them
    public String startChatWith (String shopName,String ipEmployee,String IdChatWithJson) throws NoIpExistException, ParseException, NotExistUserException, NotConnectedUserException, org.json.simple.parser.ParseException {
        String Id = JsonConverter.startChatWithRequestFromJsonString(IdChatWithJson);
        for (Shop shop : shops.values()) {
            if (!shop.getBranchName().equals(shopName)) {
                if (shop.ifExistEmployee(Id)) {
                    if (shop.availableToStartChat(Id)) { //check connected and online boolean
                        shops.get(shopName).getEmployeeOnlineByIp(ipEmployee).setInChat();
                        shop.getEmployeeOnlineByIp(shop.getIpEmployeeOnlineByID(Id)).setInChat();
                        return shop.getIpEmployeeOnlineByID(Id);
                    }
                    else {
                        shop.getEmployeeById(Id).addEmployeeToWaitingChatList(shops.get(shopName).getEmployeeOnlineByIp(ipEmployee).getId());
                        throw new NotConnectedUserException();
                    }
                }

            }
        }
        throw new NotExistUserException();
    }

    //change employee to status connect
    public void setToConnectEmployee(String shopName,String ipEmployee) throws NoIpExistException {
        Employee employee = shops.get(shopName).getEmployeeOnlineByIp(ipEmployee);
        employee.setConnected();
        shops.get(shopName).addConnectedEmployee(employee);
    }

    //change employee to status dis connect
    public void setToDisConnectEmployee(String shopName,String ipEmployee) throws NoIpExistException {
        Employee employee = shops.get(shopName).getEmployeeOnlineByIp(ipEmployee);
        employee.setConnected();
        employee.setInChat();
        shops.get(shopName).removeConnectedEmployee(employee);
        shops.get(shopName).getEmployeeOnlineByIp(ipEmployee).setConnected();
    }

    //remove employees from connect list and change status
    public void removeConnectToChat (String firstEmployeeID, String shopNameFirstEmployee, String secondEmployeeIp) throws NoIpExistException {
        Employee firstEmployee = shops.get(shopNameFirstEmployee).getEmployeeById(firstEmployeeID);
        shops.get(shopNameFirstEmployee).removeConnectedEmployee(firstEmployee);
        firstEmployee.setConnected();
        firstEmployee.setInChat();

        for (Shop shop: shops.values()) {
            if (!shop.getBranchName().equals(shopNameFirstEmployee)) {
                if (shop.getEmployeeOnlineByIp(secondEmployeeIp)!=null) {
                    Employee secondEmployee = shop.getEmployeeOnlineByIp(secondEmployeeIp);
                    shop.removeConnectedEmployee(secondEmployee);
                    secondEmployee.setConnected();
                    secondEmployee.setInChat();
                }
            }
        }

    }

}
