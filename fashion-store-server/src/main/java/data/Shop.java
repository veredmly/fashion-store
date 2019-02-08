package data;



import exceptions.*;
import observer.ISellObserver;

import java.io.IOException;
import java.util.*;

public class Shop {
    private String branchName;
    private ISellObserver branchInventory;
    private ArrayList<Employee> employees;
    private int countOfSells;
    private double sumOfSells;
    private String countNumberEmployee;
    private HashMap<Employee,String> currentOnlineEmployees;
    private ArrayList<Employee> connectedEmployees;

    public Shop(String branchName) {
        this.branchName = branchName;
        this.branchInventory = new Inventory(branchName);
        employees = new ArrayList<Employee>();
        currentOnlineEmployees = new HashMap<>();
        this.countNumberEmployee = "0000";
        countOfSells = 0;
        connectedEmployees = new ArrayList<>();

    }

    public boolean ifExistEmployee (String IdEmployee) {
        for (Employee employee: employees) {
            if (employee.getId().equals(IdEmployee)){
                return true;
            }
        }
        return false;
    }

    public Set <String> getIdConnectedEmployees () { // return set of connect employees
        Set <String> IdConnectedEmployees = new HashSet<>();
        for (Employee employee: connectedEmployees) {
            IdConnectedEmployees.add(employee.getId());
        }
        return IdConnectedEmployees;
    }

    public Set <String> getIdDisConnectedEmployees() { // return set of dis connect employees
        Set<String> IdDisConnectedEmployees = new HashSet<>();
        for (Employee employee: employees) {
            if (!connectedEmployees.contains(employee)) {
                IdDisConnectedEmployees.add(employee.getId());
            }
        }return IdDisConnectedEmployees;

    }

    public boolean availableToStartChat (String Id) { // check if the employee available - connect and not inChat
        for (Employee employee: employees) {
            if (employee.getId().equals(Id)) {
                if (employee.isOnline() && employee.isConnected() && !employee.isInChat()) {
                    return true;
                }
                else return false;
            }
        }
        return false;
    }

    public Employee getEmployeeById (String ID){
        for (Employee employee: employees) {
            if (employee.getId().equals(ID)) {
                return employee;
            }
        }
        return null;
    }

    public List<Employee> getConnectedEmployees () {
        return connectedEmployees;
    }
    public void addConnectedEmployee(Employee employee) {
        connectedEmployees.add(employee);
    }

    public void removeConnectedEmployee (Employee employee) {
        connectedEmployees.remove(employee);
    }

    public HashMap<Employee,String> getCurrentOnlineEmployees () {
        return currentOnlineEmployees;
    }

    public void addOnlineEmployee(Employee i_currentOnlineEmployee,String ipClient) {

        currentOnlineEmployees.put(i_currentOnlineEmployee,ipClient);
    }

    //return Ip of online employee by Id
    public String getIpEmployeeOnlineByID(String IdEmployeeOnline) throws NotExistUserException {
        for (Employee employee: currentOnlineEmployees.keySet()) {
            if (employee.getId().equals(IdEmployeeOnline)) {
                return currentOnlineEmployees.get(employee);
            }
        }
        throw new NotExistUserException();
    }

    //return employee Online by Ip
    public Employee getEmployeeOnlineByIp(String ipClient) throws NoIpExistException {
        for (Employee employee: currentOnlineEmployees.keySet()) {
            if (currentOnlineEmployees.get(employee).equals(ipClient)) {
                return employee;
            }
        }
        throw new NoIpExistException();
    }


    public void exitLogEmployee (String ipClient) {
        for (Employee employee: currentOnlineEmployees.keySet()) {
            if (currentOnlineEmployees.get(employee).equals(ipClient)) {
                employee.setIsOnline();
                currentOnlineEmployees.remove(employee);
            }
        }
    }

    public List<Employee> getEmployeesList(){
        return employees;
    }

    public int getEmployeesAmount () {
        return employees.size();
    }

    // create number employee and add the employee to the list of shop
    public void addEmployee (Employee employee) {
        int numbersOfEmployees = employees.size();
        numbersOfEmployees++;
        countNumberEmployee = Integer.toString(numbersOfEmployees);
        int lengthOfNumber  = countNumberEmployee.length();
        if (lengthOfNumber <2)
            countNumberEmployee = "000" + countNumberEmployee;
        else
        if (lengthOfNumber<3)
            countNumberEmployee = "00" + countNumberEmployee;
        else
        if (lengthOfNumber <4)
            countNumberEmployee = "0" + countNumberEmployee;
        employee.setEmployeeNumber(countNumberEmployee);
        employees.add(employee);
    }

    public ISellObserver getBranchInventory() {    //getter for Inventory to get it from the shop//
        return branchInventory;
    }

    public void updateCountOfSells (){
        countOfSells++;
    }
    public void updateSumOfSells (double i_sumOfSell){
        sumOfSells += i_sumOfSell;
    }

    //return count of sells
    public int getReportCountOfSells() {
        return countOfSells;
    }

    //return sum of sells
    public double getSumOfSells() {
        return sumOfSells;
    }

    //return count of employee in shop
    public String getCountNumberEmployee() {
        return countNumberEmployee;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Shop) {
            Shop shop = (Shop)obj;
            if (branchName == shop.branchName)
                return true;
        }
        return false;
    }


    // log in - check if exist employee, if not already online - if not - change status and add to list
    public String [] logInEmployee (String id,String password,String ipClient) throws UserAlreadyOnlineException, PasswordMistakeException,NotExistUserException {
        String [] details = new String [2];
        for (Employee employee: employees)
        {
            if (employee.getId().equals(id)) {
                if (employee.getPassword().equals(password)) {
                    if (!employee.isOnline()){
                        employee.setIsOnline();
                        addOnlineEmployee(employee,ipClient);
                        details[0] = employee.getClass().getSimpleName();
                        details[1] = employee.getName();
                        return details;
                    }
                    else {
                        throw new UserAlreadyOnlineException();
                    }
                }
                else throw new PasswordMistakeException();
            }
        }
        throw new NotExistUserException();
    }

    //return items in inventory
    public Set<String> getItemList() throws IOException {
        return getReportByItem().keySet();
    }

    public HashMap<String,Integer> getReportByCategory() throws IOException {
        return ((Inventory)branchInventory).getReportSellsByCategory();
    }

    public HashMap<String,Integer> getReportByItem() throws IOException {
        return ((Inventory)branchInventory).getReportSellsByItem();
    }

    public HashMap<String,Integer> getReportByType() throws IOException {
        return ((Inventory)branchInventory).getReportSellsByType();
    }

    public int getCountSellsForType(String type) throws ItemNotFoundException, IOException {
        HashMap<String,Integer> reportSellsByType = ((Inventory)branchInventory).getReportSellsByType();

        if (reportSellsByType.containsKey(type)) {
            return reportSellsByType.get(type);
        }
        else
            throw new ItemNotFoundException();
    }

    public int getCountSellsForItem(String type) throws ItemNotFoundException, IOException {
        HashMap<String,Integer> reportSellsByItem = ((Inventory)branchInventory).getReportSellsByItem();

        if (reportSellsByItem.containsKey(type)) {
            return reportSellsByItem.get(type);
        }
        else
            throw new ItemNotFoundException();
    }

    public int getCountSellsForCategory(String type) throws ItemNotFoundException, IOException {
        HashMap<String,Integer> reportSellsByCategory = ((Inventory)branchInventory).getReportSellsByCategory();

        if (reportSellsByCategory.containsKey(type)) {
            return reportSellsByCategory.get(type);
        }
        else
            throw new ItemNotFoundException();
    }

    public String getBranchName() {
        return branchName;
    }

    //return name employee by Id
    public String searchNameEmployeeByIdEmployee (String ID) {
        for (Employee employee: employees) {
            if (employee.getId().equals(ID)) {
                return employee.getName();
            }
        }
        return null;
    }

}


