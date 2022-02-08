package com.example;

public class User {
    private String email;
    private String password;
    private String fullName;
    private String college;
    private String course;
    private String passions;
    private String phone;

    public User(){

    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;

    }

    public User(String email, String password, String fullName, String college, String course, String passions, String phone){
        this.fullName = fullName;
        this.college = college;
        this.course = course;
        this.passions = passions;
        this.phone = phone;
    }

    public User(String fullName){
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword(){
        return password;
    }

    public String getFullName(){
        return fullName;
    }

    public String getCollege(){
        return college;
    }

    public String getCourse(){
        return course;
    }

    public  String getPassions(){
        return passions;
    }

    public String getPhone(){
        return phone;
    }
}
