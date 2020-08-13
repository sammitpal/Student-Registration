package com.example.student_registration;

public class UserProfile
{
    String Student_Name;
    String Gurdian_Name;
    String School;
    String Student_Roll;
    String Student_DOB;
    String Student_Phone;
    String rem;

    public UserProfile() {
    }

    public UserProfile(String student_Name, String gurdian_Name, String school, String student_Roll, String student_DOB, String student_Phone, String Rem) {
        Student_Name = student_Name;
        Gurdian_Name = gurdian_Name;
        School = school;
        Student_Roll = student_Roll;
        Student_DOB = student_DOB;
        Student_Phone = student_Phone;
        rem = Rem;
    }

    public String getStudent_Name() {
        return Student_Name;
    }

    public void setStudent_Name(String student_Name) {
        Student_Name = student_Name;
    }

    public String getGurdian_Name() {
        return Gurdian_Name;
    }

    public void setGurdian_Name(String gurdian_Name) {
        Gurdian_Name = gurdian_Name;
    }

    public String getSchool() {
        return School;
    }

    public void setSchool(String school) {
        School = school;
    }

    public String getStudent_Roll() {
        return Student_Roll;
    }

    public void setStudent_Roll(String student_Roll) {
        Student_Roll = student_Roll;
    }

    public String getStudent_DOB() {
        return Student_DOB;
    }

    public void setStudent_DOB(String student_DOB) {
        Student_DOB = student_DOB;
    }

    public String getStudent_Phone() {
        return Student_Phone;
    }

    public void setStudent_Phone(String student_Phone) {
        Student_Phone = student_Phone;
    }

    public String getRem() {
        return rem;
    }

    public void setRem(String rem) {
        this.rem = rem;
    }

}
