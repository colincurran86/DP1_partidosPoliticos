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
public class Main {

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) throws IOException {

		BufferedImage image = ImageIO.read(new File("C:/Users/Administrador/Desktop/Christian/9no/DP1/AlgoritmoChino/aa.png"));

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

		ImageIO.write(image, "png", new File("C:/Users/Administrador/Desktop/Christian/9no/DP1/AlgoritmoChino/2XBin.png"));

		ThinningService thinningService = new ThinningService();
		thinningService.doZhangSuenThinning(imageData, true);//esqueletización

		List<Point> minutiaeFound = new ArrayList<Point>(); //tendrá las coordenadas X Y de todas las minucias encontradas

		Minutiae.crossingNumber(imageData, minutiaeFound);//obtención de minucias

		for (int y = 0; y < imageData.length; y++) {

			for (int x = 0; x < imageData[y].length; x++) {

				if (imageData[y][x] == 1) {
					image.setRGB(x, y, Color.BLACK.getRGB());

				} else {
					image.setRGB(x, y, Color.WHITE.getRGB());
				}

			}
		}
		System.out.println(minutiaeFound.size());
		
		
		for (int i = 0; i < minutiaeFound.size(); i++) {
			image.setRGB(minutiaeFound.get(i).getX(), minutiaeFound.get(i).getY(), 0xFF00FF00);
			//System.out.println(minutiaeFound.get(i).getX() + " " + minutiaeFound.get(i).getY());
		}		

		ImageIO.write(image, "png", new File("C:/Users/Administrador/Desktop/Christian/9no/DP1/AlgoritmoChino/2XThin.png"));

		// Minutiae implementation basado en Rutovitz Crossing Number
		
		//FALTA ELIMINAR LAS FALSAS MINUCIAS
		
		//sacar las 10 tuplas de c/ minucia
		
		//IM: Input Image -> Planillon
		List<MinutiaeTuples> mTuplesIM = Minutiae.getTuplesFeatures(minutiaeFound);	
		
		//BM: Base Image -> Reniec
		List<MinutiaeTuples> mTuplesBM=mTuplesIM;// = Minutiae.getTuplesFeatures(minutiaeFound);
		
		//hallo la lista de minucias que serán posibles candidatos en ambas imagenes
		List<Integer> possibleCandidateIM=new ArrayList<Integer>();
		List<Integer> possibleCandidateBM=new ArrayList<Integer>();
		
		List<Integer> excludedMinutiaeIM=new ArrayList<Integer>();//se vuelve a armar la estructura con estos puntos despues
		List<Integer> excludedMinutiaeBM=new ArrayList<Integer>();
		
		Minutiae.gettingCandidates(mTuplesIM,mTuplesBM,possibleCandidateIM,possibleCandidateBM,
				excludedMinutiaeIM,excludedMinutiaeBM,minutiaeFound.size());
		
		Minutiae.orderTreeStructure(minutiaeFound,possibleCandidateIM);
		Minutiae.orderTreeStructure(minutiaeFound, possibleCandidateBM);
		
		Minutiae.comparisonAtTreeStructure(minutiaeFound,possibleCandidateIM,possibleCandidateBM,
				excludedMinutiaeIM,excludedMinutiaeBM);
	}
}