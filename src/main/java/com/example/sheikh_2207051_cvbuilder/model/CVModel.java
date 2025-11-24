package com.example.sheikh_2207051_cvbuilder.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class CVModel{
    private final IntegerProperty id = new SimpleIntegerProperty(-1);
    private final StringProperty fullName = new SimpleStringProperty("");
    private final StringProperty email = new SimpleStringProperty("");
    private final StringProperty phone = new SimpleStringProperty("");
    private final StringProperty address = new SimpleStringProperty("");
    private final StringProperty education = new SimpleStringProperty("");
    private final StringProperty skills = new SimpleStringProperty("");
    private final StringProperty experience = new SimpleStringProperty("");
    private final StringProperty projects = new SimpleStringProperty("");
    private final ObjectProperty<Image> photo = new SimpleObjectProperty<>();

    public CVModel() {}

    public int getId() { return id.get(); }
    public void setId(int value) { id.set(value); }
    public IntegerProperty idProperty() { return id; }

    public String getFullName() { return fullName.get(); }
    public void setFullName(String v) { fullName.set(v); }
    public StringProperty fullNameProperty() { return fullName; }

    public String getEmail() { return email.get(); }
    public void setEmail(String v) { email.set(v); }
    public StringProperty emailProperty() { return email; }

    public String getPhone() { return phone.get(); }
    public void setPhone(String v) { phone.set(v); }
    public StringProperty phoneProperty() { return phone; }

    public String getAddress() { return address.get(); }
    public void setAddress(String v) { address.set(v); }
    public StringProperty addressProperty() { return address; }

    public String getEducation() { return education.get(); }
    public void setEducation(String v) { education.set(v); }
    public StringProperty educationProperty() { return education; }

    public String getSkills() { return skills.get(); }
    public void setSkills(String v) { skills.set(v); }
    public StringProperty skillsProperty() { return skills; }

    public String getExperience() { return experience.get(); }
    public void setExperience(String v) { experience.set(v); }
    public StringProperty experienceProperty() { return experience; }

    public String getProjects() { return projects.get(); }
    public void setProjects(String v) { projects.set(v); }
    public StringProperty projectsProperty() { return projects; }

    public Image getPhoto() { return photo.get(); }
    public void setPhoto(Image img) { photo.set(img); }
    public ObjectProperty<Image> photoProperty() { return photo; }
}
