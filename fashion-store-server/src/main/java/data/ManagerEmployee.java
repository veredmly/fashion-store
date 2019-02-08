package data;

import exceptions.AgeException;
import exceptions.BankAccountException;
import exceptions.IDException;
import exceptions.PhoneNumberException;

public class ManagerEmployee extends CashierEmployee{
    private Shop shop;

    public ManagerEmployee() {

    }

    public void setShop(Shop i_shop) {
        shop= i_shop;
    }

    @Override
    public String toString() {
        return "ManagerEmployee{" +
                "name=" + name +
                ",id=" + id +
                ",phone=" + phone +
                ",bankAccount='" + bankAccount +
                ",branch=" + branch +
                ",employeeNumber=" + employeeNumber +
                '}';
    }

    //just the manager can create employee - get the details and create
    public void createNewEmployee(String typeEmployee,String details) throws IDException, AgeException, PhoneNumberException, BankAccountException {
        Employee newEmployee = Employee.createEmployee(typeEmployee,shop,customers,details);
    }

}
