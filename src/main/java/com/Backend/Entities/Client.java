package com.Backend.Entities;
import com.Backend.DAO.AccountDAO;

import java.sql.Date;
import java.time.LocalDate;

public class Client {

    private static int idCounter = 1;
    private int id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private LocalDate DOB ;

    public Client(String name, String email, String phone, String password, LocalDate DOB) {
        this.id = idCounter++;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.DOB = DOB;
        AccountDAO.add( new Account(this.id));
    }

    public Client(int id,String name, String email, String phone, String password, LocalDate DOB) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.DOB = DOB;
        AccountDAO.add( new Account(this.id));
    }

    public void update(String name, String email, String phone, String password, LocalDate DOB){
        this.name=name;
        this.email=email;
        this.phone=phone;
        this.password=password;
        this.DOB=DOB;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }
    public String getPassword() {
        return password;
    }
    public LocalDate getDOB() {
        return DOB;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setDOB(LocalDate DOB) {
        this.DOB = DOB;
    }

}