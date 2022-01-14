package com.example.alahsaafforestation.model;

public class User {

    private int id;
    private String name;//
    private String email;//
    private String address;//
    private String phone;//
    private int user_type;//
    private String types_of_products; //for seller
    private int age; //for volunteer
    private String career_skills; // for volunteer
    private String previous_jobs; // for volunteer

    //cutomer
    public User(int id, String name, String email, String address, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    //cutomer
    public User(int id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    //seller
    public User(int id, String name, String email, String address, String phone, String types_of_products) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.types_of_products = types_of_products;
    }

    //volunteer
    public User(int id, String name, String email, String address, String phone, int age, String career_skills, String previous_jobs) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.age = age;
        this.career_skills = career_skills;
        this.previous_jobs = previous_jobs;
    }

    public User(int id, String name, String email, String address, String phone, int user_type, String types_of_products, int age, String career_skills, String previous_jobs) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.user_type = user_type;
        this.types_of_products = types_of_products;
        this.age = age;
        this.career_skills = career_skills;
        this.previous_jobs = previous_jobs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public String getTypes_of_products() {
        return types_of_products;
    }

    public void setTypes_of_products(String types_of_products) {
        this.types_of_products = types_of_products;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCareer_skills() {
        return career_skills;
    }

    public void setCareer_skills(String career_skills) {
        this.career_skills = career_skills;
    }

    public String getPrevious_jobs() {
        return previous_jobs;
    }

    public void setPrevious_jobs(String previous_jobs) {
        this.previous_jobs = previous_jobs;
    }
}
