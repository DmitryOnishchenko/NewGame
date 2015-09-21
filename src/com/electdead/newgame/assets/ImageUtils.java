package com.electdead.newgame.assets;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ImageUtils {
    private ImageUtils() {}

    public static BufferedImage loadImage(Class<?> clazz, String path) {
        BufferedImage img = null;
        URL url = clazz.getResource(path);

        try {
            img = ImageIO.read(url);
        } catch (IOException ex) { ex.printStackTrace(); }

        return img;
    }

    public static void flipHorizontally(BufferedImage[] sprites) {
        for (int i = 0; i < sprites.length; i++) {
            BufferedImage image = sprites[i];
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-image.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            sprites[i] = op.filter(image, null);
        }
    }
}
