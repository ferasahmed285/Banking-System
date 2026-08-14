package com.Backend.Entities;

import java.sql.Date;
import java.time.LocalDate;

public class Employee extends Client {


    private double salary;


    public Employee( String name, String email, String phone, String password, LocalDate DOB, double salary) {
        super( name, email, phone, password,DOB);
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

}