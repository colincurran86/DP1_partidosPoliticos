package Binarizacion;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
		BufferedImage image = ImageIO.read(new File("C:\\temp\\Jose\\alternado_ok_Blanco_v1.jpg"));

		int[][] imageData = new int[image.getHeight()][image.getWidth()];
		Color c;
		for (int y = 0; y < imageData.length; y++) {//binarización hecha a la mala
			for (int x = 0; x < imageData[y].length; x++) {

				if (image.getRGB(x, y) <= Color.DARK_GRAY.getRGB()) {
					imageData[y][x] = 1;
					image.setRGB(x, y, Color.BLACK.getRGB());
				} else {
					imageData[y][x] = 0;
					image.setRGB(x, y, Color.WHITE.getRGB());

				}
			}
		}
		

		ImageIO.write(image, "png", new File("C:\\temp\\Jose\\SALIDA.jpg"));

		
		
	}

}
