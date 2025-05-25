package com.example.careercraft.Model;

public class JobSeeker {
    String name,email,phone,skill,location,degree;
    String role="JobSeeker";
    public JobSeeker(){
        this.name=null;
        this.email=null;
        this.skill=null;
        this.degree=null;
        this.location=null;
        this.phone=null;
    }
    public JobSeeker(String name, String email, String phone, String skill, String location, String degree) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.skill = skill;
        this.location = location;
        this.degree = degree;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
}
