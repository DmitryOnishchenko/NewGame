package com.electdead.newgame.assets;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
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

    public static VolatileImage convertToVolatileImage(BufferedImage bimage) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();

        VolatileImage vimage = createVolatileImage(bimage.getWidth(), bimage.getHeight(), Transparency.TRANSLUCENT);

        Graphics2D g = null;

        // This uses the same code as from Code Example 5, but replaces the try block.
        try {
            g = vimage.createGraphics();

            // These commands cause the Graphics2D object to clear to (0,0,0,0).
            g.setComposite(AlphaComposite.Src);
            g.setColor(Color.BLACK);
            g.clearRect(0, 0, vimage.getWidth(), vimage.getHeight()); // Clears the image.

            g.drawImage(bimage,null,0,0);
        } finally {
            // It's always best to dispose of your Graphics objects.
            g.dispose();
        }

        return vimage;
    }

    private static VolatileImage createVolatileImage(int width, int height, int transparency) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
        VolatileImage image = null;

        image = gc.createCompatibleVolatileImage(width, height, transparency);

        int valid = image.validate(gc);

        if (valid == VolatileImage.IMAGE_INCOMPATIBLE) {
            image = createVolatileImage(width, height, transparency);
            return image;
        }

        return image;
    }

    public static void flipHorizontally(VolatileImage[] sprites) {
        for (int i = 0; i < sprites.length; i++) {
            VolatileImage image = sprites[i];
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-image.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
//            sprites[i] = op.filter(image, null);
        }
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, float scale){
        int resizedWidth = (int) (originalImage.getWidth() * scale);
        int resizedHeight = (int) (originalImage.getWidth() * scale);

        BufferedImage resizedImage = new BufferedImage(resizedWidth, resizedHeight, BufferedImage.TYPE_INT_ARGB);
//        BufferedImage resizedImage = new BufferedImage(resizedWidth, resizedHeight, BufferedImage.TYPE_4BYTE_ABGR_PRE);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, resizedWidth, resizedHeight, null);
        g.dispose();

        return resizedImage;
    }
}
