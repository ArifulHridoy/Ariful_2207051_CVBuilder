package com.example.sheikh_2207051_cvbuilder.util;

import com.example.sheikh_2207051_cvbuilder.model.CVModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonUtil {
    private static final ObjectMapper M = new ObjectMapper();

    public static void exportToFile(CVModel m, File out) throws IOException {
        CVDto dto = CVDto.fromModel(m);
        M.writerWithDefaultPrettyPrinter().writeValue(out, dto);
    }

    public static CVModel importFromFile(File in) throws IOException {
        CVDto dto = M.readValue(in, CVDto.class);
        return dto.toModel();
    }

    // Simple DTO that maps Image as Base64
    public static class CVDto {
        public int id;
        public String fullName;
        public String email;
        public String phone;
        public String address;
        public String education;
        public String skills;
        public String experience;
        public String projects;
        public String photoBase64;

        public static CVDto fromModel(CVModel m) {
            CVDto d = new CVDto();
            d.id = m.getId();
            d.fullName = m.getFullName();
            d.email = m.getEmail();
            d.phone = m.getPhone();
            d.address = m.getAddress();
            d.education = m.getEducation();
            d.skills = m.getSkills();
            d.experience = m.getExperience();
            d.projects = m.getProjects();
            try {
                d.photoBase64 = m.getPhoto() == null ? null : ImageUtils.imageToBase64(m.getPhoto());
            } catch (IOException e) {
                d.photoBase64 = null;
            }
            return d;
        }

        public CVModel toModel() {
            CVModel m = new CVModel();
            m.setId(this.id);
            m.setFullName(this.fullName);
            m.setEmail(this.email);
            m.setPhone(this.phone);
            m.setAddress(this.address);
            m.setEducation(this.education);
            m.setSkills(this.skills);
            m.setExperience(this.experience);
            m.setProjects(this.projects);
            try {
                if (this.photoBase64 != null) m.setPhoto(ImageUtils.base64ToImage(this.photoBase64));
            } catch (IOException e) {
                // ignore
            }
            return m;
        }
    }
}
