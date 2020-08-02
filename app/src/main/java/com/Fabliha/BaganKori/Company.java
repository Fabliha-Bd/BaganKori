package com.Fabliha.BaganKori;

public class Company {
    String name;
    String otherdetails;

    public Company(String name, String otherdetails) {
        this.name = name;
        this.otherdetails = otherdetails;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", otherdetails='" + otherdetails + '\'' +
                '}';
    }
    //change later
    public String getCompanyInfo(){
        return name;
    }

}
