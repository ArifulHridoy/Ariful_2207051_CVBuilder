package com.example.sheikh_2207051_cvbuilder.util;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageUtils {
    public static byte[] imageToBytes(Image image) throws IOException {
        if (image == null) return null;
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(bImage, "png", baos);
            return baos.toByteArray();
        }
    }

    public static Image bytesToImage(byte[] data) throws IOException {
        if (data == null) return null;
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data)) {
            BufferedImage bimg = ImageIO.read(bais);
            if (bimg == null) return null;
            return SwingFXUtils.toFXImage(bimg, null);
        }
    }

    public static String imageToBase64(Image image) throws IOException {
        byte[] b = imageToBytes(image);
        if (b == null) return null;
        return Base64.getEncoder().encodeToString(b);
    }

    public static Image base64ToImage(String base64) throws IOException {
        if (base64 == null) return null;
        byte[] b = Base64.getDecoder().decode(base64);
        return bytesToImage(b);
    }
}
