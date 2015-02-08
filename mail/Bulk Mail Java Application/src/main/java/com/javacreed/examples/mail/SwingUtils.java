package com.javacreed.examples.mail;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 * Created by Albert on 08/02/2015.
 */
public class SwingUtils {

    private SwingUtils(){}


    public static Icon getIcon(final String name) {
        try (InputStream is = SwingUtils.class.getResourceAsStream("/icons/" + name + ".png")) {
            if (is != null) {
                return new ImageIcon(ImageIO.read(is));
            }
        } catch (final Exception e) {}

        return new ImageIcon();
    }

    public static Image getImage(final String name) {
        try (InputStream is = SwingUtils.class.getResourceAsStream("/images/" + name+".png")) {
            if (is != null) {
                return ImageIO.read(is);
            }
        } catch (final Exception e) {}

        return new BufferedImage(0, 0, BufferedImage.TYPE_INT_BGR);
    }

}
