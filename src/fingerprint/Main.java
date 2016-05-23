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

		BufferedImage imageBase = ImageIO.read(new File("C:/Users/lenovo/Desktop/PUCP/DP1/AlgoritmoChino/input2.png"));
		BufferedImage imageInput = ImageIO.read(new File("C:/Users/lenovo/Desktop/PUCP/DP1/AlgoritmoChino/input2.png"));

		int[][] imageDataBase = new int[imageBase.getHeight()][imageBase.getWidth()];
		int[][] imageDataInput = new int[imageInput.getHeight()][imageInput.getWidth()];

		Color c;
		//binarización hecha a la mala
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

		//ImageIO.write(image, "png", new File("C:/Users/lenovo/Desktop/PUCP/DP1/AlgoritmoChino/2XBin.png"));

		ThinningService thinningService = new ThinningService();
		thinningService.doZhangSuenThinning(imageDataBase, true);//esqueletización
		thinningService.doZhangSuenThinning(imageDataInput, true);//esqueletización

		List<Point> minutiaeFoundBase = new ArrayList<Point>(); //tendrá las coordenadas X Y de todas las minucias encontradas
		List<Point> minutiaeFoundInput = new ArrayList<Point>(); //tendrá las coordenadas X Y de todas las minucias encontradas

		//List<Point> minutiaeTermination = new ArrayList<Point>();
		//List<Point> minutiaeBifurcation = new ArrayList<Point>();

		// Minutiae implementation basado en Rutovitz Crossing Number		
		
		 Minutiae.crossingNumber(imageDataBase, minutiaeFoundBase);//obtención de minucias
		 Minutiae.crossingNumber(imageDataInput, minutiaeFoundInput);//obtención de minucias

//		 System.out.println(minutiaeFound.size()); // total de minucias

		//System.out.println("Total de terminaciones: " + minutiaeTermination.size());
		//System.out.println("Total de bifurcaciones: " + minutiaeBifurcation.size());		
		// sacar promedio de distancias entre bifurcaciones y terminaciones
		//double DT = Minutiae.AverageSameMinutiae(minutiaeTermination);
		//double DB = Minutiae.AverageSameMinutiae(minutiaeBifurcation);
		//int DT = Minutiae.AverageDifferentMinutiae(minutiaeTermination,minutiaeBifurcation);
		//	System.out.println( " DT PE PAPU:" + DT  + " " + DB);
		//distance = distance + (float) Math.sqrt( (p1.getX() - p2.getX()) * (p1.getX() - p2.getX()) + (p1.getY() - p2.getY()) * (p1.getY() - p2.getY()));

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
		
		
		//ELIMINANDO LAS FALSAS MINUCIAS USANDO 
		Minutiae.removeFalseMinutiae(minutiaeFoundBase);
		Minutiae.removeFalseMinutiae(minutiaeFoundInput);
		
		System.out.println("Minucias en la base " + minutiaeFoundBase.size());
		System.out.println("Minucias en el Input " + minutiaeFoundInput.size());


		//ELIMINANDO LOS BORRDES
		//Minutiae.removeBorder(imageData,minutiaeFound);
		
		
	    //System.out.println(minutiaeFound.size());

	    
		for (int i = 0; i < minutiaeFoundBase.size(); i++) 
			imageBase.setRGB(minutiaeFoundBase.get(i).getX(), minutiaeFoundBase.get(i).getY(), 0xFF00FF00);

		for (int i = 0; i < minutiaeFoundInput.size(); i++) 
			imageInput.setRGB(minutiaeFoundInput.get(i).getX(), minutiaeFoundInput.get(i).getY(), 0xFF00FF00);
		
		//MOSTRANDO LAS MINUCIAS ENCONTRADAS
			
	    ImageIO.write(imageBase, "png", new File("C:/Users/lenovo/Desktop/PUCP/DP1/AlgoritmoChino/MinuciasBase.png"));
	    ImageIO.write(imageInput, "png", new File("C:/Users/lenovo/Desktop/PUCP/DP1/AlgoritmoChino/MinuciasInput.png"));


		//sacar las 10 tuplas de c/ minucia
		
		//IM: Input Image -> Planillon
		List<MinutiaeTuples> mTuplesIM = Minutiae.getTuplesFeatures(minutiaeFoundInput);	
		
		//BM: Base Image -> Reniec
		List<MinutiaeTuples> mTuplesBM= Minutiae.getTuplesFeatures(minutiaeFoundBase);	;// = Minutiae.getTuplesFeatures(minutiaeFound);
		
		int totalMatching = Minutiae.matchingMinutiae(mTuplesIM,mTuplesBM);
		System.out.println("Total de matcheo "  + totalMatching);
		if (totalMatching > minutiaeFoundBase.size()/2) System.out.println("True"); // total de minucias
		else System.out.println("False");
	}
}




/*
//hallo la lista de minucias que serán posibles candidatos en ambas imagenes
List<Integer> possibleCandidateIM=new ArrayList<Integer>();
List<Integer> possibleCandidateBM=new ArrayList<Integer>();

List<Integer> excludedMinutiaeIM=new ArrayList<Integer>();//se vuelve a armar la estructura con estos puntos despues
List<Integer> excludedMinutiaeBM=new ArrayList<Integer>();

Minutiae.matchingMinutiae(mTuplesIM,mTuplesBM,possibleCandidateIM,possibleCandidateBM,
		excludedMinutiaeIM,excludedMinutiaeBM,minutiaeFound.size());

Minutiae.orderTreeStructure(minutiaeFound,possibleCandidateIM);
Minutiae.orderTreeStructure(minutiaeFound, possibleCandidateBM);

Minutiae.comparisonAtTreeStructure(minutiaeFound,possibleCandidateIM,possibleCandidateBM,
		excludedMinutiaeIM,excludedMinutiaeBM);
*/