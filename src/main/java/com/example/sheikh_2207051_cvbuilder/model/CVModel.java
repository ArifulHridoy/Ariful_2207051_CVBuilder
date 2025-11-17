package com.example.sheikh_2207051_cvbuilder.model;
import javafx.scene.image.Image;

public class CVModel{
    private String fullName,email,phone,address,education,skills,experience,projects;
    private Image photo;
    public CVModel() {}

    public String getFullName() {return fullName;}
    public void setFullName(String fullName) {this.fullName=fullName;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email=email;}
    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone=phone;}
    public String getAddress() {return address;}
    public void setAddress(String address) {this.address=address;}
    public String getEducation() {return education;}
    public void setEducation(String education) {this.education=education;}
    public String getSkills() {return skills;}
    public void setSkills(String skills) {this.skills=skills;}
    public String getExperience() {return experience;}
    public void setExperience(String experience) {this.experience=experience;}
    public String getProjects() {return projects;}
    public void setProjects(String projects) {this.projects=projects;}
    public Image getPhoto() {return photo;}
    public void setPhoto(Image photo) {this.photo=photo;}
}
