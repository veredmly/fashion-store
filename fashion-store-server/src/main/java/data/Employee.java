package data;

//enum EmployeeType {MANAGER,CASHIER,SELLER;}

import exceptions.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public abstract class Employee {
    protected String name;
    protected String id;
    protected String phone;
    protected String bankAccount;
    protected String branch;
    protected String employeeNumber;
    protected boolean isOnline;
    protected String password;
    protected boolean isConnected;  // waiting for chat with new thread
    protected boolean inChat; // in active chat
    private HashSet<String> waitingForChatEmployees;

    public Employee() {
        isOnline = false;
        isConnected = false;
        inChat = false;
        waitingForChatEmployees = new HashSet<String>();
    }

    public void addEmployeeToWaitingChatList (String ID) {
        waitingForChatEmployees.add(ID);

    }

    public void removeEmployeeFromWaitingChatList (String ID) {
        waitingForChatEmployees.remove(ID);
    }

    public HashSet<String> getWaitingForChatEmployees () {
        return waitingForChatEmployees;
    }

    //check if employee exist in the waiting list
    public boolean ifExistEmployeeInWaiting (String ID) {
        for (String IDEmployee: waitingForChatEmployees) {
            if (ID.equals(IDEmployee)) {
                return true;
            }
        }
        return false;
    }

    public void setOnline() {
        isOnline = !isOnline;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected() {
        isConnected = !isConnected;
    }

    public boolean isInChat() {
        return inChat;
    }

    public void setInChat() {
        inChat = !inChat;
    }

    @Override
    public String toString() {
        return "{" +
                "name=" + name +
                ",id=" + id +
                ",phone=" + phone +
                ",bankAccount=" + bankAccount +
                ",branch=" + branch +
                ",employeeNumber=" + employeeNumber +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) throws PhoneNumberException {
        if (phone.length() == 9 || phone.length() == 10)
            this.phone = phone;
        else
            throw new PhoneNumberException();
    }

    public String getAccountNumber() {
        return bankAccount;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    //create new employee by type
    public static Employee createEmployee(String typeEmployee,Shop shop, List<Customer> customers,String detailsOfEmployee) throws PhoneNumberException, IDException, AgeException, BankAccountException {
        Employee employee;
        switch (typeEmployee) {
            case "Seller":
                employee = new SellerEmployee();
                updateEmployee(employee,detailsOfEmployee);
                shop.addEmployee(employee);
                break;
            case "Cashier":
                employee = new CashierEmployee();
                updateEmployee(employee,detailsOfEmployee);
                shop.addEmployee(employee);
                ((CashierEmployee) employee).setCustomers(customers);
                ((CashierEmployee) employee).add(shop.getBranchInventory());
                break;
            case "Manager":
                employee = new ManagerEmployee();
                updateEmployee(employee,detailsOfEmployee);
                shop.addEmployee(employee);
                ((ManagerEmployee) employee).setShop(shop);
                ((ManagerEmployee) employee).setCustomers(customers);
                ((ManagerEmployee) employee).add(shop.getBranchInventory());
                break;
            default:
                employee = null;
                break;
        }
        employee.password = shop.getCountNumberEmployee();


        return employee;
    }

    public static void updateEmployee(Employee Employee, String detailsOfEmployee) throws AgeException, IDException, PhoneNumberException, BankAccountException {

        String[] details = detailsOfEmployee.split(",");
        Employee.setName(details[0]);
        Employee.setId(details[1]);
        Employee.setPhone(details[2]);
        Employee.setBankAccount(details[3]);
        Employee.setBranch(details[4]);

    }

    public void setPassword(String i_password) throws PasswordMistakeException {
        if (i_password.length()>6) {
//            if (i_password.matches("^([a-zA-Z+]+[0-9]+)$"))
                password = i_password;
//            else
//                throw new PasswordMistakeException();

        }
        else
            throw new PasswordMistakeException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return  id == employee.id &&
                employeeNumber == employee.employeeNumber &&
                isOnline == employee.isOnline &&
                Objects.equals(name, employee.name) &&
                Objects.equals(phone, employee.phone) &&
                Objects.equals(bankAccount, employee.bankAccount) &&
                Objects.equals(branch, employee.branch) &&
                Objects.equals(password, employee.password);
    }


    public void setId(String id) throws IDException {
        if (id.length() == 9)
            this.id = id;
        else
            throw new IDException();
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) throws BankAccountException {
        if (bankAccount.length() <5) {
            throw new BankAccountException();
        }
        else {
            this.bankAccount = bankAccount;

        }
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getPassword() {
        return password;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setIsOnline() {
        this.isOnline = !isOnline;
    }
}