package com.example.sheikh_2207051_cvbuilder.model;
import javafx.scene.image.Image;
public class CVData
{
    private static CVData instance;
    private CVData(){}

    public static CVData getInstance()
    {
        if (instance==null) instance=new CVData();
        return instance;
    }

    public String fullName="";
    public String email="";
    public String phone="";
    public String address="";
    public String education="";
    public String skills="";
    public String experience="";
    public String projects="";
    public Image photo;
}
