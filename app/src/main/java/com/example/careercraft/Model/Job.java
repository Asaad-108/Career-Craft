package com.example.careercraft.Model;

public class Job {
    String name;
    String location;
    String desc;
    String company;
    String salary;
    String jobtype;
    String jobId;
    String recruiterId;
    public Job() {
        this.name=null;
        this.location=null;
        this.desc=null;
        this.company=null;
        this.salary=null;
        this.jobtype=null;
        this.jobId=null;
        this.recruiterId=null;
    }

    public Job(String name, String location, String desc, String company, String salary, String jobtype) {
        this.name = name;
        this.location = location;
        this.desc = desc;
        this.company = company;
        this.salary = salary;
        this.jobtype = jobtype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getJobtype() {
        return jobtype;
    }

    public void setJobtype(String jobtype) {
        this.jobtype = jobtype;
    }
    public String getRecruiterId() {
        return recruiterId;
    }

    public void setRecruiterId(String recruiterId) {
        this.recruiterId = recruiterId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }
}
