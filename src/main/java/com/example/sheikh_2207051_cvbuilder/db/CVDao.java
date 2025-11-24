package com.example.sheikh_2207051_cvbuilder.db;

import com.example.sheikh_2207051_cvbuilder.model.CVModel;
import com.example.sheikh_2207051_cvbuilder.util.ImageUtils;
import javafx.scene.image.Image;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CVDao {

    public List<CVModel> getAll() throws SQLException {
        List<CVModel> list = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM cv ORDER BY id DESC")) {
            while (rs.next()) {
                CVModel m = mapRow(rs);
                list.add(m);
            }
        }
        return list;
    }

    public int insert(CVModel model) throws SQLException, IOException {
        String sql = "INSERT INTO cv(fullName,email,phone,address,education,skills,experience,projects,photo) VALUES(?,?,?,?,?,?,?,?,?)";
        try (Connection conn = Database.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            bindModel(ps, model);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        }
        return -1;
    }

    public void update(CVModel model) throws SQLException, IOException {
        String sql = "UPDATE cv SET fullName=?,email=?,phone=?,address=?,education=?,skills=?,experience=?,projects=?,photo=? WHERE id=?";
        try (Connection conn = Database.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            bindModel(ps, model);
            ps.setInt(10, model.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM cv WHERE id=?";
        try (Connection conn = Database.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private void bindModel(PreparedStatement ps, CVModel m) throws SQLException, IOException {
        ps.setString(1, m.getFullName());
        ps.setString(2, m.getEmail());
        ps.setString(3, m.getPhone());
        ps.setString(4, m.getAddress());
        ps.setString(5, m.getEducation());
        ps.setString(6, m.getSkills());
        ps.setString(7, m.getExperience());
        ps.setString(8, m.getProjects());
        byte[] photoBytes = null;
        Image img = m.getPhoto();
        if (img != null) {
            photoBytes = ImageUtils.imageToBytes(img);
        }
        if (photoBytes != null) ps.setBytes(9, photoBytes); else ps.setNull(9, Types.BLOB);
    }

    private CVModel mapRow(ResultSet rs) throws SQLException {
        try {
            CVModel m = new CVModel();
            m.setId(rs.getInt("id"));
            m.setFullName(rs.getString("fullName"));
            m.setEmail(rs.getString("email"));
            m.setPhone(rs.getString("phone"));
            m.setAddress(rs.getString("address"));
            m.setEducation(rs.getString("education"));
            m.setSkills(rs.getString("skills"));
            m.setExperience(rs.getString("experience"));
            m.setProjects(rs.getString("projects"));
            byte[] b = rs.getBytes("photo");
            if (b != null) {
                Image img = ImageUtils.bytesToImage(b);
                m.setPhoto(img);
            }
            return m;
        } catch (IOException ex) {
            throw new SQLException("Unable to read image", ex);
        }
    }
}
