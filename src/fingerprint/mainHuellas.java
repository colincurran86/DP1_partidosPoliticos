
package fingerprint;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 *
 * @author nayef
 */
public class mainHuellas {

	/**
	 * @param args
	 *            the command line arguments
	 */


	public static double principal(BufferedImage imageInput, BufferedImage imageBase) throws IOException {

		int totalMatching;
		// BufferedImage imageBase = buscarimageBase(dni);

		//BufferedImage imageBase = ImageIO.read(new File(
			//	"C:/Users/Administrador/Desktop/Christian/9no/DP1/GIT GIT GIT GIT/adb-installer.log/src/fingerprint/aa.png"));
		// BufferedImage imageInput = ImageIO.read(new
		// File("C:/Users/Administrador/Desktop/Christian/9no/DP1/GIT GIT GIT
		// GIT/adb-installer.log/src/fingerprint/input.png"));

		int[][] imageDataBase = new int[imageBase.getHeight()][imageBase.getWidth()];
		int[][] imageDataInput = new int[imageInput.getHeight()][imageInput.getWidth()];

		Color c;
		// binarización hecha a la mala
		for (int y = 0; y < imageDataBase.length; y++) {
			for (int x = 0; x < imageDataBase[y].length; x++) {

				if (imageBase.getRGB(x, y) <= Color.DARK_GRAY.getRGB()) {
					imageDataBase[y][x] = 1;
					imageBase.setRGB(x, y, Color.BLACK.getRGB());
				} else {
					imageDataBase[y][x] = 0;
					imageBase.setRGB(x, y, Color.WHITE.getRGB());

				}
			}
		}

		for (int y = 0; y < imageDataInput.length; y++) {
			for (int x = 0; x < imageDataInput[y].length; x++) {

				if (imageInput.getRGB(x, y) <= Color.DARK_GRAY.getRGB()) {
					imageDataInput[y][x] = 1;
					imageInput.setRGB(x, y, Color.BLACK.getRGB());
				} else {
					imageDataInput[y][x] = 0;
					imageInput.setRGB(x, y, Color.WHITE.getRGB());

				}
			}
		}

		// ImageIO.write(image, "png", new
		// File("C:/Users/lenovo/Desktop/PUCP/DP1/AlgoritmoChino/2XBin.png"));

		ThinningService thinningService = new ThinningService();
		thinningService.doZhangSuenThinning(imageDataBase, true);// esqueletización
		thinningService.doZhangSuenThinning(imageDataInput, true);// esqueletización

		List<Point> minutiaeFoundBase = new ArrayList<Point>(); // tendrá las
																// coordenadas X
																// Y de todas
																// las minucias
																// encontradas
		List<Point> minutiaeFoundInput = new ArrayList<Point>(); // tendrá las
																	// coordenadas
																	// X Y de
																	// todas las
																	// minucias
																	// encontradas

		// List<Point> minutiaeTermination = new ArrayList<Point>();
		// List<Point> minutiaeBifurcation = new ArrayList<Point>();

		// Minutiae implementation basado en Rutovitz Crossing Number

		Minutiae.crossingNumber(imageDataBase, minutiaeFoundBase);// obtención
																	// de
																	// minucias
		Minutiae.crossingNumber(imageDataInput, minutiaeFoundInput);// obtención
																	// de
																	// minucias
		
		

		
			// System.out.println(minutiaeFound.size()); // total de minucias

			// System.out.println("Total de terminaciones: " +
			// minutiaeTermination.size());
			// System.out.println("Total de bifurcaciones: " +
			// minutiaeBifurcation.size());
			// sacar promedio de distancias entre bifurcaciones y terminaciones
			// double DT = Minutiae.AverageSameMinutiae(minutiaeTermination);
			// double DB = Minutiae.AverageSameMinutiae(minutiaeBifurcation);
			// int DT =
			// Minutiae.AverageDifferentMinutiae(minutiaeTermination,minutiaeBifurcation);
			// System.out.println( " DT PE PAPU:" + DT + " " + DB);
			// distance = distance + (float) Math.sqrt( (p1.getX() - p2.getX())
			// * (p1.getX() - p2.getX()) + (p1.getY() - p2.getY()) * (p1.getY()
			// - p2.getY()));

			for (int y = 0; y < imageDataBase.length; y++) {

				for (int x = 0; x < imageDataBase[y].length; x++) {

					if (imageDataBase[y][x] == 1) {
						imageBase.setRGB(x, y, Color.BLACK.getRGB());

					} else {
						imageBase.setRGB(x, y, Color.WHITE.getRGB());
					}
				}
			}

			for (int y = 0; y < imageDataInput.length; y++) {
				for (int x = 0; x < imageDataInput[y].length; x++) {

					if (imageDataInput[y][x] == 1) {
						imageInput.setRGB(x, y, Color.BLACK.getRGB());
					} else {
						imageInput.setRGB(x, y, Color.WHITE.getRGB());
					}
				}
			}

			// ELIMINANDO LAS FALSAS MINUCIAS USANDO
			Minutiae.removeFalseMinutiae(minutiaeFoundBase);
			//Minutiae.removeFalseMinutiae(minutiaeFoundInput);
			
			if (minutiaeFoundInput.size() < 6) {
				//VALIDACION SI NO SE LOGRA TENER LOS PUNTOS SUFICIENTES
				//totalMatching = -1;
				return -1;
			}
			else {

			System.out.println("Minucias en la base " + minutiaeFoundBase.size());
			System.out.println("Minucias en el Input " + minutiaeFoundInput.size());

			// ELIMINANDO LOS BORRDES
			// Minutiae.removeBorder(imageData,minutiaeFound);

			// System.out.println(minutiaeFound.size());

			for (int i = 0; i < minutiaeFoundBase.size(); i++)
				imageBase.setRGB(minutiaeFoundBase.get(i).getX(), minutiaeFoundBase.get(i).getY(), 0xFF00FF00);

			for (int i = 0; i < minutiaeFoundInput.size(); i++)
				imageInput.setRGB(minutiaeFoundInput.get(i).getX(), minutiaeFoundInput.get(i).getY(), 0xFF00FF00);

			

			// BM: Base Image -> Reniec
			List<MinutiaeTuples> mTuplesBM = Minutiae.getTuplesFeatures(minutiaeFoundBase);

			// IM: Input Image -> Planillon
			List<MinutiaeTuples> mTuplesIM = Minutiae.getTuplesFeatures(minutiaeFoundInput);
			

			// = Minutiae.getTuplesFeatures(minutiaeFound);


			totalMatching = Minutiae.matchingMinutiae(mTuplesIM, mTuplesBM);
			System.out.println("colooooooooooo" + totalMatching);
			int menor = 0;
			if (minutiaeFoundInput.size()< minutiaeFoundBase.size()) menor = minutiaeFoundInput.size();
			else menor = minutiaeFoundBase.size();
			double porcentaje = (totalMatching * (1.0)) /  menor;
			return porcentaje * 100;

			
			}

	}
}

