package com.Backend.DAO;
import com.Backend.Entities.Client;
import com.Backend.Entities.Employee;

import static com.Backend.Database.Data.clients;
import static com.Backend.Database.Data.employees;
import com.Backend.Database.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmployeeDAO {


    public static boolean verifyEmail(String email, String password) {
        for (Employee employee : employees) {
            if (employee.getEmail().equals(email) && employee.getPassword().equals(password))
                return true;
        }
        return false;
    }
    public static Employee getEmployeeByEmail(String email) {
        for (Employee employee: employees){
            if (Objects.equals(employee.getEmail(), email))
                return employee;
        }
        return null;
    }


    public static void add(Employee employee) {
        employees.add(employee);
    }



}
