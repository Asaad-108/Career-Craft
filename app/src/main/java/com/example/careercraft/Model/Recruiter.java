package com.example.careercraft.Model;

public class Recruiter {
    String name,email,companyName,designation,location,phone;

    String role="Recruiter";

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public Recruiter(){
        this.name=null;
        this.email=null;
        this.companyName=null;
        this.designation=null;
        this.location=null;
        this.phone=null;
    }
    public Recruiter(String name, String email, String companyName, String designation, String location, String phone) {
        this.name = name;
        this.email = email;
        this.companyName = companyName;
        this.designation = designation;
        this.location = location;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
