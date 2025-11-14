# 🧾 JavaFX CV Builder

## 📖 Overview
**CV Builder** is a desktop application built using **JavaFX** that helps users create, preview, and manage their professional CVs in an interactive and user-friendly way.  
It allows users to input their personal details, education, experience, and skills, and then preview the formatted CV before exporting or saving it.

---

## 🏗️ Project Structure

```text
Ariful_2207051_CVBuilder/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── controllers/
│   │   │   │   ├── HomeController.java
│   │   │   │   ├── CVBuilderController.java
│   │   │   │   └── PreviewController.java
│   │   │   ├── model/
│   │   │   │   ├── CVModel.java
│   │   │   │   └── CVData.java
│   │   │   └── Main.java
│   │   │
│   │   └── resources/
│   │       └── fxml/
│   │           ├── Home.fxml
│   │           ├── CVBuilder.fxml
│   │           └── Preview.fxml
│
└── README.md


```

⚙️ Features

🏠 Home Page: Simple welcome screen with a “Create CV” button.

✍️ CV Builder Page:

Input personal details, education, experience, and skills.

Data is stored and managed through the CVModel class.

👁️ Preview Page:

Displays a formatted CV preview using the entered data.

Reflects real-time updates from the model.

---

🧠 Technologies Used

Java 17+

JavaFX (FXML, Scene Builder)

MVC Design Pattern

Model: CVModel.java

View: Home.fxml, CVBuilder.fxml, Preview.fxml

Controller: HomeController.java, CVBuilderController.java, PreviewController.java
