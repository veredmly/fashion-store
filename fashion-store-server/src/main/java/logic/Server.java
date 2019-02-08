package logic;


import exceptions.*;
import json.JsonConverter;
import log.Log;
import org.json.simple.parser.ParseException;

import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;

public class Server {
    private Vector<SocketData> allConnections;
    private ServerWorker serverWorker;
    private Map<String,String> ipChatting;
    private Log log;

    public Server() {
        allConnections = new Vector<>();
        serverWorker = new ServerWorker();
        ipChatting = new HashMap<>();
        log = new Log();
    }

    public void createConnection(Socket socket) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String nameOfShop = "";
                int amountOfEmployees;
                SocketData currentSocketData = new SocketData(socket);
                allConnections.add(currentSocketData);
                System.out.println(new Date()
                        + " --> Client connected from "
                        + currentSocketData.getClientAddress());
                try {
                    nameOfShop = currentSocketData.getInputStream().readLine();
                    serverWorker.shopsWithIpAddress.put(currentSocketData.getClientAddress(), nameOfShop);
                    amountOfEmployees = serverWorker.shops.get(nameOfShop).getEmployeesAmount();
                    String amountOfEmployeesJson = JsonConverter.employeesAmountToJsonString(amountOfEmployees);

                    currentSocketData.getOutputStream().println(amountOfEmployeesJson);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String line = "";
                String shopName = serverWorker.shopsWithIpAddress.get(currentSocketData.getClientAddress());
                while (!line.equals("exit")) {
                    try {

                        line = currentSocketData.getInputStream().readLine();
                        String jsonAnswer = JsonConverter.serverAnswerToJsonString("OK", "");
                        String[] actionAndDetails = JsonConverter.convertJsonStringToActionAndJsonString(line);
                        String methods = actionAndDetails[0];
                        String details = actionAndDetails[1];
                        switch (methods) {
                            case "firstManager":
                                serverWorker.createFirstManager(details, shopName);
                                currentSocketData.getOutputStream().println(jsonAnswer);
                                break;
                            case "logInEmployee":
                                String[] typeAndName = serverWorker.logIn(details, shopName, currentSocketData.getClientAddress());
                                currentSocketData.getOutputStream().println(jsonAnswer);
                                String typeEmployeeJson = JsonConverter.typeEmployeeToJsonString(typeAndName[0]);
                                currentSocketData.getOutputStream().println(typeEmployeeJson);
                                String nameEmployeeJson = JsonConverter.nameEmployeeToJsonString(typeAndName[1]);
                                currentSocketData.getOutputStream().println(nameEmployeeJson);
                                break;
                            case "createNewEmployee":
                                serverWorker.createNewEmployee(details, shopName, currentSocketData.getClientAddress());
                                currentSocketData.getOutputStream().println(jsonAnswer);
                                break;
                            case "createNewCustomer":
                                serverWorker.createNewCustomer(details, shopName, currentSocketData.getClientAddress());
                                currentSocketData.getOutputStream().println(jsonAnswer);
                                break;
                            case "changePassword":
                                serverWorker.changePassword(details, shopName);
                                currentSocketData.getOutputStream().println(jsonAnswer);
                                break;

                            case "checkExistCustomer":
                                String answer = serverWorker.checkExistCustomer(details);
                                currentSocketData.getOutputStream().println(answer);
                                break;
                            case "getDetailsCustomerRequest":
                                String detailsCustomer = serverWorker.getDetailsCustomer(details);
                                currentSocketData.getOutputStream().println(detailsCustomer);
                                break;
                            case "priceItem":
                                String price = serverWorker.getPriceOfItem(details, shopName, currentSocketData.getClientAddress());
                                currentSocketData.getOutputStream().println(jsonAnswer);
                                currentSocketData.getOutputStream().println(price);
                                break;
                            case "getListWaitingForChat":
                                String waitingForChatList = serverWorker.getWaitingToChatEmployeesList(shopName, currentSocketData.getClientAddress());
                                currentSocketData.getOutputStream().println(waitingForChatList);
                                break;
                            case "disConnectToChat":
                                String messageDis = JsonConverter.messageFromClientFromJsonString(details);
                                String chattingWithIPDis = getIPChattingWith(currentSocketData.getClientAddress());
                                String myIDDis = serverWorker.shops.get(shopName).getEmployeeOnlineByIp(currentSocketData.getClientAddress()).getId();
                                String myNameDis = serverWorker.shops.get(shopName).searchNameEmployeeByIdEmployee(myIDDis);
                                currentSocketData.getOutputStream().println(
                                        myNameDis + "@" + messageDis);
                                openChatWithSpecificEmployee(messageDis, chattingWithIPDis,
                                        myNameDis);
                                serverWorker.removeConnectToChat(myIDDis,shopName,chattingWithIPDis);
                                if (ipChatting.keySet().contains(currentSocketData.getClientAddress()))
                                    ipChatting.remove(currentSocketData.getClientAddress());
                                else
                                    ipChatting.remove(chattingWithIPDis);
                                break;
                            case "startChatWith":
                                String ipConnectWith = serverWorker.startChatWith(shopName, currentSocketData.getClientAddress(), details);
                                currentSocketData.getOutputStream().println(jsonAnswer);
                                ipChatting.put(ipConnectWith, currentSocketData.getClientAddress());
                                String idSendMessage = serverWorker.shops.get(shopName).getEmployeeOnlineByIp(currentSocketData.getClientAddress()).getId();
                                String nameSendMessage = serverWorker.shops.get(shopName).searchNameEmployeeByIdEmployee(idSendMessage);
                                currentSocketData.getOutputStream().println("Welcome to Chat!");
                                sendMessageFromTheServer("chat With " + nameSendMessage
                                        , ipConnectWith);
                                break;
                            case "OnSellForCustomer":
                                String json = serverWorker.OnSellToCustomer(details, shopName, currentSocketData.getClientAddress());
                                currentSocketData.getOutputStream().println(json);
                                break;
                            case "exitEmployee":
                                serverWorker.exitEmployee(shopName, currentSocketData.getClientAddress());
                                System.out.println(serverWorker.getChain().getShops().get(0).getCurrentOnlineEmployees().size());
                                break;
                            case "IdConnected":
                                String listOfConnectedEmployeesJson = serverWorker.getIdConnectedList(shopName);
                                currentSocketData.getOutputStream().println(listOfConnectedEmployeesJson);
                                break;
                            case "IdDisConnected":
                                String listOfDisConnectedEmployeesJson = serverWorker.getIdDisConnectedList(shopName);
                                currentSocketData.getOutputStream().println(listOfDisConnectedEmployeesJson);
                                break;
                            case "messageToSend":
                                String message = JsonConverter.messageFromClientFromJsonString(details);
                                String chattingWithIP = getIPChattingWith(currentSocketData.getClientAddress());
                                String myID = serverWorker.shops.get(shopName).getEmployeeOnlineByIp(currentSocketData.getClientAddress()).getId();
                                String myName = serverWorker.shops.get(shopName).searchNameEmployeeByIdEmployee(myID);
                                currentSocketData.getOutputStream().println(myName + "@" + message);
                                openChatWithSpecificEmployee(message, chattingWithIP,
                                        myName);
                                log.makeLog(Level.INFO,"From: " +myName+ " The message is: " + message);
                                break;
                            case "setToConnectEmployee":
                                serverWorker.setToConnectEmployee(shopName, currentSocketData.getClientAddress());
                                currentSocketData.getOutputStream().println(jsonAnswer);
                                break;

                            case "setToDisConnectEmployee":
                                break;
                            case "listOnlineEmployees":
                                break;
                            case "getReportSellsByItem":
                                String reportByItem = serverWorker.getReportByItemJsonString(shopName);
                                currentSocketData.getOutputStream().println(reportByItem);
                                break;
                            case "getReportSellsByCategory":
                                String reportByCategory = serverWorker.getReportByItemJsonString(shopName);
                                currentSocketData.getOutputStream().println(reportByCategory);
                                break;
                            case "getReportSellsByType":
                                String reportByType = serverWorker.getReportByTypeToJsonString(shopName);
                                currentSocketData.getOutputStream().println(reportByType);
                                break;
                            case "checkExistItem":
                                String countItems = serverWorker.checkCountExistItem(details, shopName, currentSocketData.getClientAddress());
                                currentSocketData.getOutputStream().println(jsonAnswer);
                                currentSocketData.getOutputStream().println(countItems);
                                break;
                            case "getItemsList":
                                String jsonItems = serverWorker.getItemsList(shopName);
                                currentSocketData.getOutputStream().println(jsonAnswer);
                                currentSocketData.getOutputStream().println(jsonItems);
                                break;
                        }

                    }
                    catch (IDException e) {
                        String jsonException = JsonConverter.serverAnswerToJsonString(e.getClass().getSimpleName(), e.getMessage());
                        currentSocketData.getOutputStream().println(jsonException);
                        e.printStackTrace();
                    }
                    catch (PhoneNumberException e) {
                        String jsonException = JsonConverter.serverAnswerToJsonString(e.getClass().getSimpleName(), e.getMessage());
                        currentSocketData.getOutputStream().println(jsonException);
                        e.printStackTrace();
                    }
                    catch (AgeException e) {
                        String jsonException = JsonConverter.serverAnswerToJsonString(e.getClass().getSimpleName(), e.getMessage());
                        currentSocketData.getOutputStream().println(jsonException);
                        System.out.println(e.getMessage());
                    }
                    catch (BankAccountException e) {
                        String jsonException = JsonConverter.serverAnswerToJsonString(e.getClass().getSimpleName(), e.getMessage());
                        currentSocketData.getOutputStream().println(jsonException);
                        e.printStackTrace();
                    }
                    catch (PasswordMistakeException e) {
                        String jsonException = JsonConverter.serverAnswerToJsonString(e.getClass().getSimpleName(), e.getMessage());
                        currentSocketData.getOutputStream().println(jsonException);
                        e.printStackTrace();
                    }
                    catch (NotExistUserException e) {
                        String jsonException = JsonConverter.serverAnswerToJsonString(e.getClass().getSimpleName(), e.getMessage());
                        currentSocketData.getOutputStream().println(jsonException);
                        e.printStackTrace();
                    }
                    catch (UserAlreadyOnlineException e) {
                        String jsonException = JsonConverter.serverAnswerToJsonString(e.getClass().getSimpleName(), e.getMessage());
                        currentSocketData.getOutputStream().println(jsonException);
                        e.printStackTrace();
                    }
                    catch (ParseException e) {
                        String jsonException = JsonConverter.serverAnswerToJsonString(e.getClass().getSimpleName(), e.getMessage());
                        currentSocketData.getOutputStream().println(jsonException);
                        System.out.println(e.getMessage());
                    }
                    catch (SocketException e) {

                    }
                    catch (IOException e) {
                        String jsonException = JsonConverter.serverAnswerToJsonString(e.getClass().getSimpleName(), e.getMessage());
                        currentSocketData.getOutputStream().println(jsonException);
                        e.printStackTrace();
                    }
                    catch (NoIpExistException e) {
                        e.printStackTrace();
                    }
                    catch (NotEnoughDetailsForCustomerException e) {
                        String jsonException = JsonConverter.serverAnswerToJsonString(e.getClass().getSimpleName(), e.getMessage());
                        currentSocketData.getOutputStream().println(jsonException);
                        e.printStackTrace();
                    }
                    catch (NoDetailsForCustomerException e) {
                        String jsonException = JsonConverter.serverAnswerToJsonString(e.getClass().getSimpleName(), e.getMessage());
                        currentSocketData.getOutputStream().println(jsonException);
                        e.printStackTrace();
                    }
                    catch (ItemNotFoundException e) {
                        String jsonException = JsonConverter.serverAnswerToJsonString(e.getClass().getSimpleName(), e.getMessage());
                        currentSocketData.getOutputStream().println(jsonException);
                        e.printStackTrace();
                    }
                    catch (NotEnoughItemsToSellException e) {
                        String jsonException = JsonConverter.serverAnswerToJsonString(e.getClass().getSimpleName(), e.getMessage());
                        currentSocketData.getOutputStream().println(jsonException);
                    }
                    catch (UserAlreadyExistException e) {
                        String jsonException = JsonConverter.serverAnswerToJsonString(e.getClass().getSimpleName(), e.getMessage());
                        currentSocketData.getOutputStream().println(jsonException);
                        e.printStackTrace();
                    }
                    catch (NotConnectedUserException e) {
                        String jsonException = JsonConverter.serverAnswerToJsonString(e.getClass().getSimpleName(), e.getMessage());
                        currentSocketData.getOutputStream().println(jsonException);
                        e.printStackTrace();
                    }
                }
            } // run
        }).start();
    }


    public void openChatWithSpecificEmployee (String theMessage, String
        ipEmployeeThatGetMessage,
                                              String idEmployeeSendMessage){
            for (SocketData sd : allConnections)
                if (sd.getClientAddress().equals(ipEmployeeThatGetMessage)) {
                    sd.getOutputStream().println(
                            idEmployeeSendMessage + "@" + theMessage);
                }

        }
        public void sendMessageFromTheServer (String theMessage, String ipEmployeeThatGetMessage)
        {
            for (SocketData sd : allConnections)
                if (sd.getClientAddress().equals(ipEmployeeThatGetMessage)) {
                    sd.getOutputStream().println(theMessage);
                }

        }
        public String getIPChattingWith (String currentIP){
            for (String ip : ipChatting.keySet()) {
                if (ip.equals(currentIP)) {
                    return ipChatting.get(ip);
                } else {
                    if (ipChatting.get(ip).equals(currentIP)) {
                        return ip;
                    }
                }
            }
            return null;
        }
        public ServerSocket getSSLServerSocket () throws GeneralSecurityException, IOException {
            System.setProperty("javax.net.ssl.keyStore", "C:\\Users\\mlyne\\IdeaProjects\\fashionstore\\fashion-store-server\\src\\main\\java\\za.store");
            System.setProperty("javax.net.ssl.keyStorePassword", "123456");
            ServerSocket sslServerSocket
                    = ((SSLServerSocketFactory) SSLServerSocketFactory.getDefault()).createServerSocket(7000);
            return sslServerSocket;
        }
}

