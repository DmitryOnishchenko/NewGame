package com.electdead.newgame.assets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class ImageLoader {
	private ImageLoader() {}
	
	public static BufferedImage loadImage(Class<?> clazz, String path) {
		BufferedImage img = null;
		URL url = clazz.getResource(path);
		
		try {
			img = ImageIO.read(url);
		} catch (IOException ex) { ex.printStackTrace(); }
		
		return img;
	}
}
