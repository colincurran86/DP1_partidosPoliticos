/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Firmas;

//import Catalano.Imaging.Corners.ICornersDetector;
import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.BradleyLocalThreshold;
import Catalano.Imaging.Tools.IntegralImage;
import ij.plugin.ZAxisProfiler;
import models.PersonaReniec;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.resizers.Resizers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;

import pantallas.Procesando;
import pantallas.ProcesandoSeg;
import Catalano.Imaging.Filters.Crop;
import Catalano.Imaging.Filters.OtsuThreshold;
import Catalano.Imaging.Filters.Rotate;
import Catalano.Imaging.Filters.Resize;
import Catalano.Imaging.Filters.ZhangSuenThinning;

/**
 * @author LUIS S
 */
public class AlgoritmoFirmas {
	
	public static BufferedImage huella=null;
	private String rutaCarpetaImagenes;
	private String rutaImagen1;
	private String rutaImagen2;
	private boolean corte;
	private boolean dibujarTodasImagenes;
	private boolean dibujarImagenCorte;
	private boolean dibujarImagenesBaseDatos;
	private int umbralMatch;

	public AlgoritmoFirmas(String ruta, String rutaImagen1, String rutaImagen2, boolean corte,
			boolean dibujarTodasImagenes, boolean dibujarImagenCorte, boolean dibujarImagenesBaseDatos,
			int umbralMatch) {
		this.rutaCarpetaImagenes = ruta;
		this.rutaImagen1 = rutaImagen1;
		this.rutaImagen2 = rutaImagen2;
		this.corte = corte;
		this.dibujarTodasImagenes = dibujarTodasImagenes;
		this.dibujarImagenCorte = dibujarImagenCorte;
		this.dibujarImagenesBaseDatos = dibujarImagenesBaseDatos;
		this.umbralMatch = umbralMatch;
	}

	public AlgoritmoFirmas(String ruta, boolean corte, boolean dibujarTodasImagenes, boolean dibujarImagenCorte,
			boolean dibujarImagenesBaseDatos, int umbralMatch) {
		this.rutaCarpetaImagenes = ruta;
		this.corte = corte;
		this.dibujarTodasImagenes = dibujarTodasImagenes;
		this.dibujarImagenCorte = dibujarImagenCorte;
		this.dibujarImagenesBaseDatos = dibujarImagenesBaseDatos;
		this.umbralMatch = umbralMatch;
	}

	public AlgoritmoFirmas() {
	};

	/**
	 * @return the ruta
	 */
	public String getRuta() {
		return rutaCarpetaImagenes;
	}

	/**
	 * @param ruta
	 *            the ruta to set
	 */
	public void setRuta(String ruta) {
		this.rutaCarpetaImagenes = ruta;
	}

	/**
	 * @return the rutaImagen1
	 */
	public String getRutaImagen1() {
		return rutaImagen1;
	}

	/**
	 * @param rutaImagen1
	 *            the rutaImagen1 to set
	 */
	public void setRutaImagen1(String rutaImagen1) {
		this.rutaImagen1 = rutaImagen1;
	}

	/**
	 * @return the rutaImagen2
	 */
	public String getRutaImagen2() {
		return rutaImagen2;
	}

	/**
	 * @param rutaImagen2
	 *            the rutaImagen2 to set
	 */
	public void setRutaImagen2(String rutaImagen2) {
		this.rutaImagen2 = rutaImagen2;
	}

	/**
	 * @return the corte
	 */
	public boolean isCorte() {
		return corte;
	}

	/**
	 * @param corte
	 *            the corte to set
	 */
	public void setCorte(boolean corte) {
		this.corte = corte;
	}

	/**
	 * @return the dibujarTodasImagenes
	 */
	public boolean isDibujarTodasImagenes() {
		return dibujarTodasImagenes;
	}

	/**
	 * @param dibujarTodasImagenes
	 *            the dibujarTodasImagenes to set
	 */
	public void setDibujarTodasImagenes(boolean dibujarTodasImagenes) {
		this.dibujarTodasImagenes = dibujarTodasImagenes;
	}

	/**
	 * @return the dibujarImagenCorte
	 */
	public boolean isDibujarImagenCorte() {
		return dibujarImagenCorte;
	}

	/**
	 * @param dibujarImagenCorte
	 *            the dibujarImagenCorte to set
	 */
	public void setDibujarImagenCorte(boolean dibujarImagenCorte) {
		this.dibujarImagenCorte = dibujarImagenCorte;
	}

	/**
	 * @return the dibujarImagenesBaseDatos
	 */
	public boolean isDibujarImagenesBaseDatos() {
		return dibujarImagenesBaseDatos;
	}

	/**
	 * @param dibujarImagenesBaseDatos
	 *            the dibujarImagenesBaseDatos to set
	 */
	public void setDibujarImagenesBaseDatos(boolean dibujarImagenesBaseDatos) {
		this.dibujarImagenesBaseDatos = dibujarImagenesBaseDatos;
	}

	/**
	 * @return the umbralMatch
	 */
	public int getUmbralMatch() {
		return umbralMatch;
	}

	/**
	 * @param umbralMatch
	 *            the umbralMatch to set
	 */
	public void setUmbralMatch(int umbralMatch) {
		this.umbralMatch = umbralMatch;
	}

	public int[] procesar(boolean dibujarCorte, boolean almacenarCorte, boolean dibujarPuntosCorte,
			boolean dibujarBaseDatos, boolean dibujarPuntosBaseDatos, double umbral) {
		int[] idPersonasFalladas;
		int cantidadPersonasFalladas = 0;
		FastBitmap imagenCortada;
		int inicioX = 380;
		int inicioY = 1508;
		int cantidadPersonasProcesadas = 0;
		int aumentoX = 0;
		int aumentoY = 7;
		int indiceDistanciaMinima;
		int j;
		double distancia;
		double distanciaMinima = umbral;
		int count;
		Resize redimensionar = new Resize(260, 116);

		List<List<FastRetinaKeypoint>> listaPuntos = new ArrayList<List<FastRetinaKeypoint>>();
		Crop objetoCortador;
		FastCornersDetector fac = new FastCornersDetector(); // Inicio del
																// algoritmo
																// Fast
		FastRetinaKeypointDetector frdCorte = new FastRetinaKeypointDetector(fac); // Incio
																					// del
																					// algoritmo
																					// Freak
		List<FastRetinaKeypoint> listaPrubeKeyPoints4Padron;

		List<List<FastRetinaKeypoint>> listaPuntosBaseDatos = new ArrayList<List<FastRetinaKeypoint>>();
		List<List<Resultado>> listaDistancia2Listas = new ArrayList<List<Resultado>>();
		FastBitmap firmasBaseDatos;

		FastRetinaKeypointDetector frdBase = new FastRetinaKeypointDetector(fac);
		List<FastRetinaKeypoint> listaPrubeKeyPoints4PadronBaseDatos = new ArrayList<FastRetinaKeypoint>();
		Distance d = new Distance();

		while (true) {
			imagenCortada = new FastBitmap(rutaCarpetaImagenes + "\\padron.blanco.firmado.jpg");
			Rotate rotarImage = new Rotate(90.0, Rotate.Algorithm.BILINEAR);
			rotarImage.applyInPlace(imagenCortada);
			rotarImage.applyInPlace(imagenCortada);
			rotarImage.applyInPlace(imagenCortada);
			imagenCortada.toRGB();
			imagenCortada.toGrayscale();
			// Apply the Bradley local threshold.

			BradleyLocalThreshold bradley4 = new BradleyLocalThreshold();
			bradley4.applyInPlace(imagenCortada);
			// Y = x
			// 250 ancho
			// Coordenadas de la primera firma
			// Crop objetoCortar = new Crop(380,1508,250,115);
			if (cantidadPersonasProcesadas == 0) {
				objetoCortador = new Crop(inicioX + aumentoX, inicioY, 260, 116);
			} else {
				objetoCortador = new Crop(inicioX + aumentoX, inicioY + aumentoY, 260, 116);
			}

			objetoCortador.ApplyInPlace(imagenCortada);
			aumentoX += 140;

			if (dibujarCorte)
				JOptionPane.showMessageDialog(null, imagenCortada.toIcon(), "Resultado con Puntos",
						JOptionPane.PLAIN_MESSAGE);
			if (almacenarCorte)
				imagenCortada.saveAsJPG(
						"C:\\Users\\LUIS S\\Desktop\\Firmas Java\\corte1" + cantidadPersonasProcesadas + ".jpg");

			listaPrubeKeyPoints4Padron = new ArrayList<FastRetinaKeypoint>();
			listaPrubeKeyPoints4Padron = frdCorte.ProcessImage(imagenCortada);

			if (dibujarPuntosCorte) {
				// SampleApplication.FastBitmap fb4 = new
				// SampleApplication.FastBitmap("/home/wirox91/Escritorio/B-
				// pc/Backup/Imagenes/2otsu.png");
				Firmas.FastBitmap fb4Dibujar = new Firmas.FastBitmap(
						"C:\\Users\\LUIS S\\Desktop\\Firmas Java\\corte1" + cantidadPersonasProcesadas + ".jpg");
				FastGraphics graficarPuntos2Corte = new FastGraphics(fb4Dibujar);
				// graficarPuntos2Corte.setColor(Color.Blue);
				graficarPuntos2Corte.setColor(Color.Black);
				// Funcion 0
				for (int i = 0; i < listaPrubeKeyPoints4Padron.size(); i++) {
					int x1 = (int) listaPrubeKeyPoints4Padron.get(i).x;
					int y1 = (int) listaPrubeKeyPoints4Padron.get(i).y;
					graficarPuntos2Corte.DrawCircle(x1, y1, 2);
				}
				JOptionPane.showMessageDialog(null, fb4Dibujar.toIcon(), "Puntos en la imagen cortada",
						JOptionPane.PLAIN_MESSAGE);
			}

			cantidadPersonasProcesadas++;

			// Termina de procesar la imagen
			if (listaPrubeKeyPoints4Padron.size() == 0) {
				break;
			}
			listaPuntos.add(listaPrubeKeyPoints4Padron);
		}

		// Procesa las imagenes de la base de datos
		for (int i = 1; i < 59; i++) {
			if (i < 10) {
				firmasBaseDatos = new FastBitmap(
						"C:\\Users\\LUIS S\\Desktop\\Firmas Java\\Imagenes\\firmas.jpg\\f00" + i + ".jpg");
			} else {
				firmasBaseDatos = new FastBitmap(
						"C:\\Users\\LUIS S\\Desktop\\Firmas Java\\Imagenes\\firmas.jpg\\f0" + i + ".jpg");
			}
			redimensionar.ApplyInPlace(firmasBaseDatos);
			firmasBaseDatos.toRGB();
			firmasBaseDatos.toGrayscale();

			// Apply the Bradley local threshold.
			BradleyLocalThreshold bradleyBase = new BradleyLocalThreshold();
			bradleyBase.applyInPlace(firmasBaseDatos);

			if (dibujarBaseDatos)
				JOptionPane.showMessageDialog(null, firmasBaseDatos.toIcon(), "Result: " + i,
						JOptionPane.PLAIN_MESSAGE);

			listaPrubeKeyPoints4PadronBaseDatos = frdBase.ProcessImage(firmasBaseDatos);
			if (dibujarPuntosBaseDatos) {
				Firmas.FastBitmap fb4DibujarBase;
				if (i < 10) {
					fb4DibujarBase = new Firmas.FastBitmap(
							"C:\\Users\\LUIS S\\Desktop\\Firmas Java\\Imagenes\\firmas.jpg\\f00" + i + ".jpg");
				} else {
					fb4DibujarBase = new Firmas.FastBitmap(
							"C:\\Users\\LUIS S\\Desktop\\Firmas Java\\Imagenes\\firmas.jpg\\f0" + i + ".jpg");
				}

				FastGraphics graficarPuntos2Base = new FastGraphics(fb4DibujarBase);
				// graficarPuntos2Base.setColor(Color.Blue);
				graficarPuntos2Base.setColor(Color.Black);

				// Funcion 0
				for (int ii = 0; ii < listaPrubeKeyPoints4PadronBaseDatos.size(); ii++) {
					int x1 = (int) listaPrubeKeyPoints4PadronBaseDatos.get(ii).x;
					int y1 = (int) listaPrubeKeyPoints4PadronBaseDatos.get(ii).y;
					graficarPuntos2Base.DrawCircle(x1, y1, 2);
				}

				JOptionPane.showMessageDialog(null, fb4DibujarBase.toIcon(), "Result con Puntos",
						JOptionPane.PLAIN_MESSAGE);
			}
			listaPuntosBaseDatos.add(listaPrubeKeyPoints4PadronBaseDatos);
		}

		// Matching entre las dos listas, 8
		for (int ki = 0; ki < listaPuntos.size(); ki++) {
			System.out.println("Turno de la figura " + (ki + 1) + " :");
			System.out.println("----------------------------------------------------------------------------");
			List<Resultado> listaResultadosPorImagen = new ArrayList<Resultado>();
			for (int k = 0; k < listaPuntosBaseDatos.size(); k++) { // 58

				for (int i = 0; i < listaPuntos.get(ki).size(); i++) {
					// distanciaMinima=0;
					indiceDistanciaMinima = -1;

					for (j = 0; j < listaPuntosBaseDatos.get(k).size(); j++) {
						if (listaPuntosBaseDatos.get(k).get(j).getIndexMatch() == -1) {
							distancia = d.Hamming(listaPuntos.get(ki).get(i).toBinary(),
									listaPuntosBaseDatos.get(k).get(j).toBinary());
							// System.out.println("d = "+distancia);
							if (distancia <= distanciaMinima) {
								// distanciaMinima=distancia;
								indiceDistanciaMinima = j;
								listaPuntosBaseDatos.get(k).get(indiceDistanciaMinima).setIndexMatch(i);
								listaPuntos.get(ki).get(i).setIndexMatch(indiceDistanciaMinima);
								break;
							}
						}
					}

				}

				for (int iii = 0; iii < listaPuntosBaseDatos.get(k).size(); iii++) {
					if (listaPuntosBaseDatos.get(k).get(iii).getIndexMatch() != -1) {
						listaPuntosBaseDatos.get(k).get(iii).setIndexMatch(-1);
					}
				}

				count = 0;
				for (int iii = 0; iii < listaPuntos.get(ki).size(); iii++) {
					if (listaPuntos.get(ki).get(iii).getIndexMatch() != -1) {
						{
							count++;
							listaPuntos.get(ki).get(iii).setIndexMatch(-1);
						}
					}

				}

				Resultado rc = new Resultado(count);
				listaResultadosPorImagen.add(rc);
				/*
				 * System.out.println("Total matches, la fiugra "+k+" :"+count);
				 * System.out.println("----------------------------------"+ "");
				 * System.out.println("Total puntos figura1: "
				 * +listaPuntos.get(ki).size()); System.out.println(
				 * "Total puntos figura2: "+listaPuntosBaseDatos.get(k).size());
				 * System.out.println("");
				 */
			}
			listaDistancia2Listas.add(listaResultadosPorImagen);

		}

		int[] personaNo;
		double[] distanciaPersonaNo;
		personaNo = new int[listaPuntos.size()];
		distanciaPersonaNo = new double[listaPuntos.size()];

		double mayor = -999999;
		int indiceMayor = 0;
		for (int i = 0; i < listaDistancia2Listas.size(); i++) {
			mayor = -99999;

			indiceMayor = 0;
			for (int k = 0; k < listaDistancia2Listas.get(i).size(); k++) {
				if (listaDistancia2Listas.get(i).get(k).indFirmaMatch >= mayor) {

					mayor = listaDistancia2Listas.get(i).get(k).indFirmaMatch;
					indiceMayor = k;
				}

			}
			personaNo[i] = indiceMayor;
			distanciaPersonaNo[i] = mayor;
			// System.out.println("Match imagen corte: "+(i+1)+" y la de la base
			// de datos: "+(indiceMayor+1)+" con puntos: "+mayor);
		}

		/*
		 * for (int i = 0; i < listaDistancia2Listas.size(); i++) {
		 * System.out.println("Figura: "+i+" ");
		 * System.out.println("----------------------------------"); for (int k
		 * = 0; k < listaDistancia2Listas.get(i).size(); k++) {
		 * System.out.println(k+" ) "
		 * +listaDistancia2Listas.get(i).get(k).resul); } }
		 */

		int[] indiceNO;
		int cantN = 0;

		for (int i = 0; i < personaNo.length; i++) {
			if (distanciaPersonaNo[i] < 30) {
				cantN++;
			}

		}
		indiceNO = new int[cantN];

		int m = 0;
		for (int i = 0; i < personaNo.length; i++) {
			if (distanciaPersonaNo[i] < 30) {

				indiceNO[m] = i;
				m++;
			}

		}

		return indiceNO;
	}

	
	void procesar2() throws IOException { // double umbral=85;
		double umbral = 5;
		int d1 = 0, d2 = 0;

		Resize redimensionar = new Resize(260, 116);
		FastCornersDetector fast = new FastCornersDetector(); // Inicio del
																// algoritmo
																// Fast

		List<List<Resultado>> arre = new ArrayList<List<Resultado>>();
		ArrayList<Resultado> arr;

		for (int isuperior = 1; isuperior < 58; isuperior++) {

			FastBitmap imagen1;
			FastBitmap imagen2;
			FastRetinaKeypointDetector freak1 = new FastRetinaKeypointDetector(fast); // Incio
																						// del
																						// algoritmo
																						// Freak
			FastRetinaKeypointDetector freak2 = new FastRetinaKeypointDetector(fast);
			List<FastRetinaKeypoint> descriptores1;
			List<FastRetinaKeypoint> descriptores2;
			Distance distancia = new Distance();
			// String url1 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
			// carpeta\\Firmas Java\\"+isuperior+"j.jpg");
			// String url1 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
			// carpeta\\Firmas Java\\cc\\1\\c1"+isuperior+".jpg");
			// String url1 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
			// carpeta\\Firmas Java\\cc\\2\\c2"+isuperior+".jpg");
			// String url1 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
			// carpeta\\Firmas Java\\cc\\3\\c3"+isuperior+".jpg");
			// String url1 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
			// carpeta\\Firmas Java\\cc\\4\\c4"+isuperior+".jpg");
			// String url1 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
			// carpeta\\Firmas Java\\cc\\5\\c5"+isuperior+".jpg");
			// String url1 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
			// carpeta\\Firmas Java\\cc\\6\\c6"+isuperior+".jpg");
			// String url1 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
			// carpeta\\Firmas Java\\cc\\7\\c7"+isuperior+".jpg");
			// String url1 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
			// carpeta\\Firmas Java\\cc\\8\\c8"+isuperior+".jpg");
			// String url1 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
			// carpeta\\Firmas Java\\cc\\5\\c5"+isuperior+".jpg");
			// String url1 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
			// carpeta\\Firmas Java\\cc\\10\\c10"+isuperior+".jpg");
			// String url1 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
			// carpeta\\Firmas Java\\cc\\6\\c61.jpg");

			// String url1 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
			// carpeta\\Firmas Java\\42r.jpg");
			// String url1 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
			// carpeta\\Firmas Java\\"+isuperior+"r.jpg");

			// String url1 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
			// carpeta\\Firmas Java\\c1"+isuperior+".jpg");

			// String url1 = new String("C:\\Users\\LUIS
			// S\\Desktop\\f\\"+isuperior+".jpg");

			String url1 = new String("C:\\Users\\LUIS S\\Desktop\\c" + isuperior + ".jpg");

			OtsuThreshold o = new OtsuThreshold();

			imagen1 = new FastBitmap(url1);
			imagen1.toRGB();
			imagen1.toGrayscale();
			// BradleyLocalThreshold bradley4 = new BradleyLocalThreshold();
			// bradley4.applyInPlace(imagen1);
			o.applyInPlace(imagen1);
			// imagen1.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\Nueva
			// carpeta\\Firmas Java\\imagen_primera_procesada.jpg");
			descriptores1 = new ArrayList<FastRetinaKeypoint>();
			descriptores1 = freak1.ProcessImage(imagen1);

			/*
			 * Firmas.FastBitmap fb4DibujarBase; fb4DibujarBase = new
			 * Firmas.FastBitmap(url1); FastGraphics graficarPuntos2Base = new
			 * FastGraphics(fb4DibujarBase);
			 * //graficarPuntos2Base.setColor(Color.Blue);
			 * graficarPuntos2Base.setColor(Color.Black);
			 * 
			 * //Funcion 0 for (int ii = 0; ii < descriptores1.size(); ii++) {
			 * int x1 = (int) descriptores1.get(ii).x; int y1 = (int)
			 * descriptores1.get(ii).y; graficarPuntos2Base.DrawCircle(x1, y1,
			 * 2); }
			 * 
			 * JOptionPane.showMessageDialog(null, fb4DibujarBase.toIcon(),
			 * "Result con Puntos", JOptionPane.PLAIN_MESSAGE);
			 */
			System.out.println("A) Figura ejemplo: " + url1);
			// System.out.println("Tamaï¿½o: "+descriptores1.size());
			// System.out.println("Tamaï¿½o 1: "+imagen1.getWidth()+"
			// "+imagen1.getHeight());
			// System.out.println("Umbral: "+umbral);
			arr = new ArrayList<Resultado>();
			int ind;
			for (ind = 1; ind < 58; ind++) {

				String url2 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\" + ind + ".jpg");
				// String url2 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
				// carpeta\\Firmas Java\\37.jpg");
				// String url2 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
				// carpeta\\Firmas Java\\42.jpg");
				// String url2 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
				// carpeta\\Firmas Java\\nueva_imagen_redimensionada.jpg");
				// ind=42;
				// System.out.println("Figura: "+ind);
				System.out.println("Figura 1: " + url1);
				System.out.println("Figura 2: " + url2);
				imagen2 = new FastBitmap(url2);

				// redimensionar.ApplyInPlace(imagen2);
				// System.out.println("Imagen 1: "+imagen1.getWidth()+"
				// "+imagen1.getHeight());
				// System.out.println("Imagen 2: "+imagen2.getWidth()+"
				// "+imagen2.getHeight());

				// Resize redimensionar2 = new
				// Resize((imagen1.getWidth()),(imagen1.getHeight()),Algorithm.NEAREST_NEIGHBOR);
				// redimensionar2.ApplyInPlace(imagen2);
				// System.out.println("resize: "+imagen2.getWidth()+"
				// "+imagen2.getHeight());

				// ProgressiveBilinearResizer pr = new
				// ProgressiveBilinearResizer();

				// BufferedImage aBufferedImage = null;

				// pr.resize(imagen2.toBufferedImage(),aBufferedImage);

				// imagen2 = new FastBitmap(aBufferedImage);

				// BufferedImage a =
				// getScaledInstance(imagen2.toBufferedImage(),imagen1.getWidth(),imagen1.getHeight(),null,true);

				// Image a = resizeToBig(imagen2.toImage(),imagen1.getWidth(),
				// imagen1.getHeight());

				// imagen2.setImage((BufferedImage) a);
				//

				// BufferedImage img = imagen2.toBufferedImage(); // load image
				// BufferedImage scaledImg = Scalr.resize(img, Method.BALANCED,
				// 210, 95);
				// imagen2 = new FastBitmap(scaledImg);

				// Resizers a = new Resizers() ;
				// Antialiasing an = null;
				// 220 105

				/*
				 * BufferedImage bi = Thumbnails.of(new File(url2))
				 * .size(imagen1.getWidth(), imagen1.getHeight())
				 * .outputFormat("JPEG") .outputQuality(1)
				 * .resizer(Resizers.PROGRESSIVE) .antialiasing(Antialiasing.ON)
				 * .dithering(Dithering.ENABLE)
				 * //.scalingMode(ScalingMode.PROGRESSIVE_BILINEAR)
				 * .asBufferedImage();
				 */

				/*
				 * BufferedImage bbi = new BufferedImage(210,
				 * 105,BufferedImage.TYPE_INT_RGB); Graphics2D g =
				 * bbi.createGraphics(); //Graphics g = imagen2.getGraphics();
				 * Graphics2D g2 = (Graphics2D)g; RenderingHints rh = new
				 * RenderingHints( RenderingHints.KEY_TEXT_ANTIALIASING,
				 * RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				 * g2.setRenderingHints(rh); g2.drawImage(bbi, null, 0, 0);
				 */

				/*
				 * BufferedImage dbi = null; dbi = new BufferedImage(210,
				 * 105,BufferedImage.TYPE_INT_RGB); Graphics2D g =
				 * dbi.createGraphics(); AffineTransform at =
				 * AffineTransform.getScaleInstance(210, 105);
				 * g.drawRenderedImage(sbi, at);
				 */

				/*
				 * 
				 * Image ai = imagen2.toImage(); Image rescaled =
				 * ai.getScaledInstance(210, 105, Image.SCALE_AREA_AVERAGING);
				 * 
				 * 
				 * BufferedImage bbi = new BufferedImage(210,
				 * 105,BufferedImage.TYPE_INT_RGB); Graphics2D g =
				 * bbi.createGraphics();
				 */

				// .toFile(new File("C:\\Users\\LUIS S\\Desktop\\Nueva
				// carpeta\\Firmas Java\\thumbnail.jpg"));

				// Metodo en uso

				/*
				 * if((imagen2.getWidth()>=300 ||
				 * imagen2.getHeight()>=300)||(imagen1.getWidth()>=320 ||
				 * imagen1.getHeight()>=320)){ //System.out.println("todos");
				 * BufferedImage img = imagen2.toBufferedImage(); // load image
				 * 220 105 BufferedImage scaledImg = Scalr.resize(img,
				 * Method.AUTOMATIC, imagen1.getWidth(),
				 * imagen1.getHeight(),Scalr.OP_BRIGHTER); // BufferedImage
				 * scaledImg = Scalr.resize(img, Method.BALANCED, 300,
				 * 156,Scalr.OP_BRIGHTER); // BufferedImage scaledImg =
				 * Scalr.resize(img, Method.BALANCED, imagen1.getWidth(),
				 * imagen2.getHeight(),Scalr.OP_ANTIALIAS); imagen2 = new
				 * FastBitmap(scaledImg); } imagen2.saveAsJPG(
				 * "C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\"+ind+"
				 * nuevoresize.jpg");
				 */

				// ind=36;

				if (ind == 36 || ind == 37 || ind == 38 || ind == 39 || ind == 40 || ind == 41 || ind == 41 || ind == 42
						|| ind == 43) {

					// if((imagen2.getWidth()>=300 ||
					// imagen2.getHeight()>=300)||(imagen1.getWidth()>=320 ||
					// imagen1.getHeight()>=320)){

					BufferedImage bi = Thumbnails
							.of(new File("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\" + ind + ".jpg"))
							// BufferedImage bi = Thumbnails.of(new
							// File("C:\\Users\\LUIS S\\Desktop\\Nueva
							// carpeta\\Firmas Java\\37.jpg"))
							.size(imagen1.getWidth(), imagen1.getHeight()).outputFormat("JPG").outputQuality(1)
							.resizer(Resizers.PROGRESSIVE).asBufferedImage();

					imagen2 = new FastBitmap(bi);

					// }
					System.out.println("1Image1: " + imagen1.getWidth() + " " + imagen1.getHeight());
					System.out.println("1Image2: " + imagen2.getWidth() + " " + imagen2.getHeight());
				} else {
					System.out.println("i: " + ind);
					BufferedImage img = imagen2.toBufferedImage(); // load image
																	// 220 105
					BufferedImage scaledImg = Scalr.resize(img, Method.AUTOMATIC, imagen1.getWidth(),
							imagen1.getHeight(), Scalr.OP_BRIGHTER);
					// BufferedImage scaledImg = Scalr.resize(img,
					// Method.BALANCED, 300, 156,Scalr.OP_BRIGHTER);
					// BufferedImage scaledImg = Scalr.resize(img,
					// Method.BALANCED, imagen1.getWidth(),
					// imagen2.getHeight(),Scalr.OP_ANTIALIAS);
					imagen2 = new FastBitmap(scaledImg);
					System.out.println("Image1: " + imagen1.getWidth() + " " + imagen1.getHeight());
					System.out.println("Image2: " + imagen2.getWidth() + " " + imagen2.getHeight());

				}

				// imagen2.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\Nueva
				// carpeta\\Firmas Java\\37Thumbnails.jpg");

				// BufferedImage scaledImg = Scalr.resize(img, Method.QUALITY,
				// 150, 100, Scalr.OP_ANTIALIAS);

				// imagen2.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\Nueva
				// carpeta\\Firmas Java\\nueva_metodo.jpg");

				// imagen2 = new FastBitmap(bi);
				// imagen2.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\Nueva
				// carpeta\\Firmas Java\\nueva_metodo.jpg");
				imagen2.toRGB();
				imagen2.toGrayscale();
				// BradleyLocalThreshold bradleyBase = new
				// BradleyLocalThreshold();
				// bradleyBase.applyInPlace(imagen2);
				// redimensionar2.ApplyInPlace(imagen2);
				o.applyInPlace(imagen2);
				// BradleyLocalThreshold bradleyBase = new
				// BradleyLocalThreshold();
				// bradleyBase.applyInPlace(imagen2);

				// imagen2.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\Nueva
				// carpeta\\Firmas Java\\nueva_imagen_redimensionada.jpg");

				descriptores2 = new ArrayList<FastRetinaKeypoint>();
				descriptores2 = freak2.ProcessImage(imagen2);
				// System.out.println("Tamaï¿½o de 2: "+imagen2.getWidth()+"
				// "+imagen2.getHeight());
				// System.out.println("tamaï¿½o 2:"+descriptores2.size());
				// System.out.println();
				System.out.println(descriptores1.size());
				System.out.println(descriptores2.size());

				/*
				 * Firmas.FastBitmap fb4DibujarBase2; fb4DibujarBase2 = new
				 * Firmas.FastBitmap(url2); FastGraphics graficarPuntos2Base2 =
				 * new FastGraphics(fb4DibujarBase2);
				 * //graficarPuntos2Base.setColor(Color.Blue);
				 * graficarPuntos2Base.setColor(Color.Black);
				 * 
				 * 
				 * //Funcion 0 for (int ii = 0; ii < descriptores2.size(); ii++)
				 * { int x1 = (int) descriptores2.get(ii).x; int y1 = (int)
				 * descriptores2.get(ii).y; graficarPuntos2Base2.DrawCircle(x1,
				 * y1, 2); }
				 * 
				 * 
				 * JOptionPane.showMessageDialog(null, fb4DibujarBase2.toIcon(),
				 * "Result con Puntos", JOptionPane.PLAIN_MESSAGE);
				 */

				int contadorMatching = 0;

				double distanciaResultado;
				double porcentaje = 0;

				/*
				 * for (int i = 0; i < descriptores1.size(); i++) {
				 * 
				 * for (int j = 0; j < descriptores2.size(); j++) {
				 * distanciaResultado=distancia.Hamming(descriptores1.get(i).
				 * toBinary(), descriptores2.get(j).toBinary());
				 * //System.out.println(distanciaResultado);
				 * if(distanciaResultado<=umbral)
				 * contadorMatching=contadorMatching+1; break; } }
				 */

				Distance d = new Distance();
				int j;
				double distancia1; // 45
				int distanciaMinima = 45; // 41 default 45 y 20 jpg , no puede
											// subir mas , por ahora, DEFAULT =
											// 45
				// Funcion 3, 20
				for (int i = 0; i < descriptores1.size(); i++) {
					// distanciaMinima=0;
					int indiceDistanciaMinima = -1;
					for (j = 0; j < descriptores2.size(); j++) {
						if (descriptores1.get(i).primerosBits(descriptores2.get(j)) >= 14) { // 20
																								// no
																								// falla
																								// 34,
																								// 5
																								// DEFAULT
																								// ,
							// 15, falla 14
							// 10, falla 10
							// 5, falla 10
							// 14 , falla 12 en total

							if (descriptores2.get(j).getIndexMatch() == -1) {

								distancia1 = d.Hamming(descriptores1.get(i).toBinary(),
										descriptores2.get(j).toBinary());
								// System.out.println("d = "+distancia1);
								if (distancia1 <= distanciaMinima) {
									// distanciaMinima=distancia;
									indiceDistanciaMinima = j;
									descriptores2.get(indiceDistanciaMinima).setIndexMatch(i);
									break;
								}

							}
						}
					}
					descriptores1.get(i).setIndexMatch(indiceDistanciaMinima);
				}

				int c = 0;
				for (int i = 0; i < descriptores1.size(); i++) {
					if (descriptores1.get(i).getIndexMatch() != -1)
						c++;
				}
				int cor = 0;
				// System.out.println("Similitud: "+contadorMatching);
				System.out.println("Similitud: " + c);
				// System.out.println(descriptores1.get(0).toBinary());
				// System.out.println(descriptores2.get(0).toBinary());

				if (descriptores1.size() > 0) {
					// porcentaje de 1 imagen
					porcentaje = ((c * 100) / descriptores1.size());
					// porcentaje de 2 imagen
					// porcentaje = ((c*100)/descriptores2.size());
					System.out.println("Porcentaje: " + porcentaje);
				}
				// 28, 20
				// 55, ok
				// 20 , ok
				// 10 pp ok
				if (porcentaje >= 23) { // 20 defualt;
					// default 35 pero probaremos baja resoulucion 30
					// esta entre 34 y 20
					Resultado r1 = new Resultado(ind);
					r1.porcentaje = porcentaje;
					arr.add(r1);

					System.out.println("Ejemplo " + url1);
					System.out.println("Esta es la imagen conincide:(1xImagen) " + url2);
					System.out.println("------------------------------------------------------------------");
				}
				System.out.println();
				d2 = descriptores2.get(0).getDescriptor().length;
			}
			for (int i = 0; i < descriptores1.size(); i++) {
				if (descriptores1.get(i).getIndexMatch() != -1)
					descriptores1.get(i).setIndexMatch(-1);
			}
			arre.add(arr);

		}

		int kk = 0;
		System.out.println("Lista:");
		for (int i = 0; i < arre.size(); i++) {
			System.out.println("i :" + (i));
			if (arre.get(i).size() >= 2 || arre.get(i).size() == 0)
				kk++;
			for (int k = 0; k < arre.get(i).size(); k++) {

				System.out.println(arre.get(i).get(k).indFirmaMatch + "  p: " + arre.get(i).get(k).porcentaje);
			}
			System.out.println();
		}
		System.out.println("Fallas: " + kk);
		// System.out.println(" "+d1+" "+d2);
	}

	



	public ArrayList<FirmaRecortada> cortarFirmas(String urlPlanillonesOriginales) {

		int anchos = 0;
		int altos = 0;
		int indiceFinal = 0;
		ArrayList<FirmaRecortada> listaFirmasCortadas = new ArrayList<FirmaRecortada>();

			
		
		
		// Cantidad de planillones, cargar
		for (int i = 1; i < 3; i++) {
			ArrayList<FirmaRecortada> listaTemporal = new ArrayList<FirmaRecortada>();
			System.out.println("Planillon: " + i);
			FastBitmap imagenPlanillon = new FastBitmap(urlPlanillonesOriginales +"\\"+ i + ".jpg");
			Crop cortadorImagenes;
			int factorPixel = 0;
			int multiplicarFactor = 0;
			
			// Comprueba horizontal o vertical
			if (imagenPlanillon.getWidth() < imagenPlanillon.getHeight()) {
				Rotate rotarImage = new Rotate(90.0, Rotate.Algorithm.BICUBIC);
				int despejarLineasNegras = 0;
				
				rotarImage.applyInPlace(imagenPlanillon);
				rotarImage.applyInPlace(imagenPlanillon);
				rotarImage.applyInPlace(imagenPlanillon);

				imagenPlanillon.toRGB();
				imagenPlanillon.toGrayscale();
				OtsuThreshold o = new OtsuThreshold();
				o.applyInPlace(imagenPlanillon);

				
				for (int j = imagenPlanillon.getHeight() - 1; j > 0; j--) {
					if (imagenPlanillon.getGray(j, imagenPlanillon.getWidth() / 2) == 255) {
						despejarLineasNegras = j;
						break;
					}
				}
				cortadorImagenes = new Crop(0, imagenPlanillon.getWidth() / 2,
				imagenPlanillon.getWidth() / 2 + imagenPlanillon.getWidth() / 3, despejarLineasNegras - 10);

			} else {
				cortadorImagenes = new Crop(0, imagenPlanillon.getWidth() / 2, imagenPlanillon.getWidth() / 3,
				imagenPlanillon.getHeight());
				imagenPlanillon.toRGB();
				imagenPlanillon.toGrayscale();
				OtsuThreshold o = new OtsuThreshold();
				o.applyInPlace(imagenPlanillon);
			}

			cortadorImagenes.ApplyInPlace(imagenPlanillon);
			anchos = imagenPlanillon.getWidth() - 1;
			altos = imagenPlanillon.getHeight() - 1;


			//Factor Pixel
			if (altos >= 0 && altos <= 900)
				factorPixel = 5;
			else {
				multiplicarFactor = altos / 900;
				factorPixel = 5 * multiplicarFactor;
			}



			int mitadPlanillon = imagenPlanillon.getWidth() / 2;
			int indiceNegro = 0;
			int indiceBlanco = 0;
			
			//Negro
			for (int j = 0; j < imagenPlanillon.getHeight() - 1; j++) {
				if (imagenPlanillon.getGray(j, mitadPlanillon) == 0) {
					indiceNegro = j;
					break;
				}
			}
			
			//Blanco
			for (int r = imagenPlanillon.getWidth() / 2; r < imagenPlanillon.getWidth() - 1; r++) {
				if (imagenPlanillon.getGray(indiceNegro, r) == 255) {
					indiceBlanco = r;
					break;
				}
			}
			
			

			int ind = imagenPlanillon.getWidth() - 1;
			ind = indiceBlanco;

			List<List<Resultado>> a3 = new ArrayList<List<Resultado>>();
			Resultado d;
			int cont = 0;
			int c = 0;
			int llego = 0;
			int dos = 0;

			int j;
			int tmp1 = 0;
			int tmp2 = 0;
			int nollego = 0;
			int pasoPrimerNegro = 0;

			//Busca la primera linea negra
			while (true) {
				c = 0;
				llego = 0;
				nollego = 0;
				pasoPrimerNegro = 0;
				
				for (j = imagenPlanillon.getHeight() - 1; j > 0; j--) {
					if (imagenPlanillon.getGray(j, ind) == 0) {
						c++;
						pasoPrimerNegro = j;

					}
					if (c > imagenPlanillon.getHeight() / 2) {
						llego = 1;
						tmp1 = ind;
						break;
					}
				}
				if (llego == 1)
					break;
				ind--;
			}

			

			
			j = 0;
			llego = 0;
			ind = ind - 50;
			//Segunda line negra
			while (true) {
				c = 0;
				llego = 0;
				pasoPrimerNegro = 0;
				// for (j = inn+10; j > 0 ; j--) {
				for (j = imagenPlanillon.getHeight() - 1; j > 0; j--) {
		
					if (imagenPlanillon.getGray(j, ind) == 0) { 
						c++;
					}
	

					if (c > imagenPlanillon.getHeight() / 2) {
						llego = 1;
						tmp2 = ind;
						break;
					}
				}
		
				if (llego == 1)
					break;
				ind--;

			}

			
			int ultimoYLineaNegra1 = 0;
			int ultimoYLineaNegra2 = 0;

			for (int k = imagenPlanillon.getHeight() - 1; k > 0; k--) {
				if (imagenPlanillon.getGray(k, tmp1) == 0) {
					ultimoYLineaNegra1 = k;
					break;
				}

			}

			for (int k = imagenPlanillon.getHeight() - 1; k > 0; k--) {
				if (imagenPlanillon.getGray(k, tmp2) == 0) {
					ultimoYLineaNegra2 = k;
					break;
				}

			}


			int veces = 0;
			int ancho = tmp1 - tmp2;
			int contador = 0;
			int hizobreak = 0;
			int malbreak = 0;
			ArrayList<Integer> listaLineas = new ArrayList<Integer>();
			int band1 = 0;

	
			for (int k = ultimoYLineaNegra2 - factorPixel; k > 0; k--) {
				contador = 0;
				hizobreak = 0;
				malbreak = 0;

				if (veces == 8)
					break;
				for (int h = tmp2; h < tmp1; h++) {
					if (imagenPlanillon.getGray(k, h) == 0)
						contador++;
					else {
						malbreak = 1;
						break;
					}
					if (contador >= ancho / 2) {
						hizobreak = 1;
						break;
					}

				}

				if (malbreak != 1) {
					k = k - factorPixel;
					veces++;
					listaLineas.add(k);
				}
			}

			int alto;
			for (int k = 0; k < 8; k++) {
				System.out.println("Linea actual ..........." + k);
				if (k == 0) {
					alto = ultimoYLineaNegra2 - listaLineas.get(k);
					cortadorImagenes = new Crop(listaLineas.get(k), tmp2, ancho, alto);
				} else if (k == 7) {
					alto = listaLineas.get(k - 1) - listaLineas.get(k);
					cortadorImagenes = new Crop(listaLineas.get(k), tmp2, ancho, alto);

				} else {
					alto = listaLineas.get(k) - listaLineas.get(k + 1);
					cortadorImagenes = new Crop(listaLineas.get(k), tmp2, ancho, alto);
				}
				FastBitmap i3 = new FastBitmap(imagenPlanillon.toBufferedImage());
				/*
				imagenPlanillon.saveAsJPG(
				"C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\Otras_Resoluciones\\objs\\planillon\\"
								+ i + "rr.jpg");
				FastBitmap i3 = new FastBitmap(
				"C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\Otras_Resoluciones\\objs\\planillon\\"
								+ i + "rr.jpg");
				*/
				cortadorImagenes.ApplyInPlace(i3);
				//JOptionPane.showMessageDialog(null, i3.toIcon(), "Result"+i+" ", JOptionPane.PLAIN_MESSAGE);

				FirmaRecortada fr = new FirmaRecortada();
				fr.img = i3.toBufferedImage();
				fr.ancho = anchos;
				fr.alto = altos;
				listaTemporal.add(fr);

			}


			int indiceListaTemporal = 7;
			while (indiceListaTemporal >= 0) {
				listaFirmasCortadas.add(listaTemporal.get(indiceListaTemporal));
				indiceListaTemporal--;
			}

		}

		return listaFirmasCortadas;
	}

	
	
	
	public ArrayList<FirmaRecortada> cortarFirmas2(String urlPlanillonesOriginales) {

		int anchos = 0;
		int altos = 0;
		int indiceFinal = 0;
		ArrayList<FirmaRecortada> listaFirmasCortadas = new ArrayList<FirmaRecortada>();

			
	      
		File f = new File(urlPlanillonesOriginales);
		//	if (f.exists()){ // Directorio existe 
		File[] ficheros = f.listFiles();
		//	}
		
		
		// Cantidad de planillones, cargar
		for (int i = 1; i < ficheros.length; i++) {
			String cadenaTemporal = ficheros[i].getName();
			if(cadenaTemporal.contains(".jpg")==true){
			ArrayList<FirmaRecortada> listaTemporal = new ArrayList<FirmaRecortada>();
			System.out.println("Planillon: " + ficheros[i].getName());
			FastBitmap imagenPlanillon = new FastBitmap(urlPlanillonesOriginales+"\\"+ficheros[i].getName());
			
			Crop cortadorImagenes;
			int factorPixel = 0;
			int multiplicarFactor = 0;
			
			// Comprueba horizontal o vertical
			if (imagenPlanillon.getWidth() < imagenPlanillon.getHeight()) {
				Rotate rotarImage = new Rotate(90.0, Rotate.Algorithm.BICUBIC);
				int despejarLineasNegras = 0;
				
				rotarImage.applyInPlace(imagenPlanillon);
				rotarImage.applyInPlace(imagenPlanillon);
				rotarImage.applyInPlace(imagenPlanillon);

				imagenPlanillon.toRGB();
				imagenPlanillon.toGrayscale();
				OtsuThreshold o = new OtsuThreshold();
				o.applyInPlace(imagenPlanillon);

				
				for (int j = imagenPlanillon.getHeight() - 1; j > 0; j--) {
					if (imagenPlanillon.getGray(j, imagenPlanillon.getWidth() / 2) == 255) {
						despejarLineasNegras = j;
						break;
					}
				}
				cortadorImagenes = new Crop(0, imagenPlanillon.getWidth() / 2,
				imagenPlanillon.getWidth() / 2 + imagenPlanillon.getWidth() / 3, despejarLineasNegras - 10);

			} else {
				cortadorImagenes = new Crop(0, imagenPlanillon.getWidth() / 2, imagenPlanillon.getWidth() / 3,
				imagenPlanillon.getHeight());
				imagenPlanillon.toRGB();
				imagenPlanillon.toGrayscale();
				OtsuThreshold o = new OtsuThreshold();
				o.applyInPlace(imagenPlanillon);
			}

			cortadorImagenes.ApplyInPlace(imagenPlanillon);
			imagenPlanillon.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\Otras_Resoluciones\\objs\\planillon\\probar" + i
			+ "r.jpg");
			anchos = imagenPlanillon.getWidth() - 1;
			altos = imagenPlanillon.getHeight() - 1;

			
			
			//Factor Pixel
			if (altos >= 0 && altos <= 900)
				factorPixel = 5;
			else {
				multiplicarFactor = altos / 900;
				factorPixel = 5 * multiplicarFactor;
			}



			int mitadPlanillon = imagenPlanillon.getWidth() / 2;
			int indiceNegro = 0;
			int indiceBlanco = 0;
			
			//Negro
			for (int j = 0; j < imagenPlanillon.getHeight() - 1; j++) {
				if (imagenPlanillon.getGray(j, mitadPlanillon) == 0) {
					indiceNegro = j;
					break;
				}
			}
			
			//Blanco
			for (int r = imagenPlanillon.getWidth() / 2; r < imagenPlanillon.getWidth() - 1; r++) {
				if (imagenPlanillon.getGray(indiceNegro, r) == 255) {
					indiceBlanco = r;
					break;
				}
			}
			
			

			int ind = imagenPlanillon.getWidth() - 1;
			ind = indiceBlanco;

			List<List<Resultado>> a3 = new ArrayList<List<Resultado>>();
			Resultado d;
			int cont = 0;
			int c = 0;
			int llego = 0;
			int dos = 0;

			int j;
			int tmp1 = 0;
			int tmp2 = 0;
			int nollego = 0;
			int pasoPrimerNegro = 0;

			//Busca la primera linea negra
			while (true) {
				c = 0;
				llego = 0;
				nollego = 0;
				pasoPrimerNegro = 0;
				
				for (j = imagenPlanillon.getHeight() - 1; j > 0; j--) {
					if (imagenPlanillon.getGray(j, ind) == 0) {
						c++;
						pasoPrimerNegro = j;

					}
					if (c > imagenPlanillon.getHeight() / 2) {
						llego = 1;
						tmp1 = ind;
						break;
					}
				}
				if (llego == 1)
					break;
				ind--;
			}

			

			
			j = 0;
			llego = 0;
			ind = ind - 50;
			//Segunda line negra
			while (true) {
				c = 0;
				llego = 0;
				pasoPrimerNegro = 0;
				// for (j = inn+10; j > 0 ; j--) {
				for (j = imagenPlanillon.getHeight() - 1; j > 0; j--) {
		
					if (imagenPlanillon.getGray(j, ind) == 0) { 
						c++;
					}
	

					if (c > imagenPlanillon.getHeight() / 2) {
						llego = 1;
						tmp2 = ind;
						break;
					}
				}
		
				if (llego == 1)
					break;
				ind--;

			}

			
			int ultimoYLineaNegra1 = 0;
			int ultimoYLineaNegra2 = 0;

			for (int k = imagenPlanillon.getHeight() - 1; k > 0; k--) {
				if (imagenPlanillon.getGray(k, tmp1) == 0) {
					ultimoYLineaNegra1 = k;
					break;
				}

			}

			for (int k = imagenPlanillon.getHeight() - 1; k > 0; k--) {
				if (imagenPlanillon.getGray(k, tmp2) == 0) {
					ultimoYLineaNegra2 = k;
					break;
				}

			}


			int veces = 0;
			int ancho = tmp1 - tmp2;
			int contador = 0;
			int hizobreak = 0;
			int malbreak = 0;
			ArrayList<Integer> listaLineas = new ArrayList<Integer>();
			int band1 = 0;

	
			for (int k = ultimoYLineaNegra2 - factorPixel; k > 0; k--) {
				contador = 0;
				hizobreak = 0;
				malbreak = 0;

				if (veces == 8)
					break;
				for (int h = tmp2; h < tmp1; h++) {
					if (imagenPlanillon.getGray(k, h) == 0)
						contador++;
					else {
						malbreak = 1;
						break;
					}
					if (contador >= ancho / 2) {
						hizobreak = 1;
						break;
					}

				}

				if (malbreak != 1) {
					k = k - factorPixel;
					veces++;
					listaLineas.add(k);
				}
			}

			int alto;
			for (int k = 0; k < 8; k++) {
				System.out.println("Linea actual ..........." + k);
				if (k == 0) {
					alto = ultimoYLineaNegra2 - listaLineas.get(k);
					cortadorImagenes = new Crop(listaLineas.get(k), tmp2, ancho, alto);
				} else if (k == 7) {
					alto = listaLineas.get(k - 1) - listaLineas.get(k);
					cortadorImagenes = new Crop(listaLineas.get(k), tmp2, ancho, alto);

				} else {
					alto = listaLineas.get(k) - listaLineas.get(k + 1);
					cortadorImagenes = new Crop(listaLineas.get(k), tmp2, ancho, alto);
				}
				FastBitmap i3 = new FastBitmap(imagenPlanillon.toBufferedImage());
				/*
				imagenPlanillon.saveAsJPG(
				"C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\Otras_Resoluciones\\objs\\planillon\\"
								+ i + "rr.jpg");
				FastBitmap i3 = new FastBitmap(
				"C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\Otras_Resoluciones\\objs\\planillon\\"
								+ i + "rr.jpg");
				*/
				cortadorImagenes.ApplyInPlace(i3);
				JOptionPane.showMessageDialog(null, i3.toIcon(), "Result"+i+" ", JOptionPane.PLAIN_MESSAGE);

				FirmaRecortada fr = new FirmaRecortada();
				fr.img = i3.toBufferedImage();
				fr.ancho = anchos;
				fr.alto = altos;
				listaTemporal.add(fr);

			}


			int indiceListaTemporal = 7;
			while (indiceListaTemporal >= 0) {
				listaFirmasCortadas.add(listaTemporal.get(indiceListaTemporal));
				indiceListaTemporal--;
			}
		}
		}

		return listaFirmasCortadas;
	}
	
	
	
	
	void procesar3(ArrayList<FirmaRecortada> lbi) throws IOException { // double
																		// umbral=85;
		double umbral = 5;
		int d1 = 0, d2 = 0;
		int porcentajeMinimo = 35;
		// 16
		Resize redimensionar = new Resize(260, 116);
		FastCornersDetector fast = new FastCornersDetector(); // Inicio del
																// algoritmo
																// Fast

		List<List<Resultado>> arre = new ArrayList<List<Resultado>>();
		ArrayList<Resultado> arr;

		int distanciaMinima = 45; // 41 default 45 y 20 jpg , no puede subir mas
									// , por ahora, DEFAULT = 45
		int distanciaFiltro = 14;
		for (int isuperior = 0; isuperior < 8; isuperior++) {

			float nuevaEscala = 16;

			FastBitmap imagen1;
			FastBitmap imagen2;
			FastRetinaKeypointDetector freak1 = new FastRetinaKeypointDetector(fast); // Incio
																						// del
																						// algoritmo
																						// Freak
			FastRetinaKeypointDetector freak2 = new FastRetinaKeypointDetector(fast);
			List<FastRetinaKeypoint> descriptores1;
			List<FastRetinaKeypoint> descriptores2;
			Distance distancia = new Distance();
			// String url1 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
			// carpeta\\Firmas Java\\"+isuperior+"j.jpg");
			// String url1 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
			// carpeta\\Firmas Java\\cc\\1\\c1"+isuperior+".jpg");
			// String url1 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
			// carpeta\\Firmas Java\\cc\\2\\c2"+isuperior+".jpg");
			// String url1 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
			// carpeta\\Firmas Java\\cc\\3\\c3"+isuperior+".jpg");
			// String url1 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
			// carpeta\\Firmas Java\\cc\\4\\c4"+isuperior+".jpg");
			// String url1 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
			// carpeta\\Firmas Java\\cc\\5\\c5"+isuperior+".jpg");
			// String url1 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
			// carpeta\\Firmas Java\\cc\\6\\c6"+isuperior+".jpg");
			// String url1 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
			// carpeta\\Firmas Java\\cc\\7\\c7"+isuperior+".jpg");
			// String url1 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
			// carpeta\\Firmas Java\\cc\\8\\c8"+isuperior+".jpg");
			// String url1 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
			// carpeta\\Firmas Java\\cc\\5\\c5"+isuperior+".jpg");
			// String url1 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
			// carpeta\\Firmas Java\\cc\\10\\c10"+isuperior+".jpg");
			// String url1 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
			// carpeta\\Firmas Java\\cc\\6\\c61.jpg");

			// String url1 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
			// carpeta\\Firmas Java\\42r.jpg");
			// String url1 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
			// carpeta\\Firmas Java\\"+isuperior+"r.jpg");

			// String url1 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
			// carpeta\\Firmas Java\\c1"+isuperior+".jpg");

			// String url1 = new String("C:\\Users\\LUIS
			// S\\Desktop\\f\\"+isuperior+".jpg");

			// String url1 = new String("C:\\Users\\LUIS
			// S\\Desktop\\c"+isuperior+".jpg");

			OtsuThreshold o = new OtsuThreshold();

			// imagen1 = new FastBitmap(url1);
			// imagen1.toRGB();
			// imagen1.toGrayscale();
			// BradleyLocalThreshold bradley4 = new BradleyLocalThreshold();
			// bradley4.applyInPlace(imagen1);
			// o.applyInPlace(imagen1);
			// imagen1.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\Nueva
			// carpeta\\Firmas Java\\imagen_primera_procesada.jpg");

			imagen1 = new FastBitmap(lbi.get(isuperior).img);
			int ancho = lbi.get(isuperior).ancho;
			int alto = lbi.get(isuperior).alto;
			descriptores1 = new ArrayList<FastRetinaKeypoint>();
			if (alto <= 1300 && ancho <= 1300) {

				freak1.scale = nuevaEscala;
				freak1.scale = 16;
				porcentajeMinimo = 16;
				distanciaMinima = 60;
				distanciaFiltro = 4;
				System.out.println("scale: si1 " + freak1.scale);
			} else {
				freak1.scale = 18.0f;
				System.out.println("scale: si1111111 " + freak1.scale);
			}
			descriptores1 = freak1.ProcessImage(imagen1);
			JOptionPane.showMessageDialog(null, imagen1.toIcon(), "Result " + isuperior + " ",
					JOptionPane.PLAIN_MESSAGE);

			// imagen1.saveAsJPG("/home/wirox91/Escritorio/P/"+isuperior+".jpg");

			/*
			 * Firmas.FastBitmap fb4DibujarBase; fb4DibujarBase = new
			 * Firmas.FastBitmap(url1); FastGraphics graficarPuntos2Base = new
			 * FastGraphics(fb4DibujarBase);
			 * //graficarPuntos2Base.setColor(Color.Blue);
			 * graficarPuntos2Base.setColor(Color.Black);
			 * 
			 * //Funcion 0 for (int ii = 0; ii < descriptores1.size(); ii++) {
			 * int x1 = (int) descriptores1.get(ii).x; int y1 = (int)
			 * descriptores1.get(ii).y; graficarPuntos2Base.DrawCircle(x1, y1,
			 * 2); }
			 * 
			 * JOptionPane.showMessageDialog(null, fb4DibujarBase.toIcon(),
			 * "Result con Puntos", JOptionPane.PLAIN_MESSAGE);
			 */
			// System.out.println("A) Figura ejemplo: "+url1);
			// System.out.println("Tamaï¿½o: "+descriptores1.size());
			// System.out.println("Tamaï¿½o 1: "+imagen1.getWidth()+"
			// "+imagen1.getHeight());
			// System.out.println("Umbral: "+umbral);
			arr = new ArrayList<Resultado>();
			int ind;
			for (ind = 1; ind < 58; ind++) {

				String url2 = new String(
						"C:\\Users\\alulab14.INF.001\\Desktop\\Nueva carpeta\\Firmas Java\\" + ind + ".jpg");

				// String url2 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
				// carpeta\\Firmas Java\\37.jpg");
				// String url2 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
				// carpeta\\Firmas Java\\42.jpg");
				// String url2 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva
				// carpeta\\Firmas Java\\nueva_imagen_redimensionada.jpg");
				// ind=42;
				// System.out.println("Figura: "+ind);
				// System.out.println("Figura 1: "+url1);
				System.out.println("Figura 2: " + url2);
				imagen2 = new FastBitmap(url2);

				// redimensionar.ApplyInPlace(imagen2);
				// System.out.println("Imagen 1: "+imagen1.getWidth()+"
				// "+imagen1.getHeight());
				// System.out.println("Imagen 2: "+imagen2.getWidth()+"
				// "+imagen2.getHeight());

				// Resize redimensionar2 = new
				// Resize((imagen1.getWidth()),(imagen1.getHeight()),Algorithm.NEAREST_NEIGHBOR);
				// redimensionar2.ApplyInPlace(imagen2);
				// System.out.println("resize: "+imagen2.getWidth()+"
				// "+imagen2.getHeight());

				// ProgressiveBilinearResizer pr = new
				// ProgressiveBilinearResizer();

				// BufferedImage aBufferedImage = null;

				// pr.resize(imagen2.toBufferedImage(),aBufferedImage);

				// imagen2 = new FastBitmap(aBufferedImage);

				// BufferedImage a =
				// getScaledInstance(imagen2.toBufferedImage(),imagen1.getWidth(),imagen1.getHeight(),null,true);

				// Image a = resizeToBig(imagen2.toImage(),imagen1.getWidth(),
				// imagen1.getHeight());

				// imagen2.setImage((BufferedImage) a);
				//

				// BufferedImage img = imagen2.toBufferedImage(); // load image
				// BufferedImage scaledImg = Scalr.resize(img, Method.BALANCED,
				// 210, 95);
				// imagen2 = new FastBitmap(scaledImg);

				// Resizers a = new Resizers() ;
				// Antialiasing an = null;
				// 220 105

				/*
				 * BufferedImage bi = Thumbnails.of(new File(url2))
				 * .size(imagen1.getWidth(), imagen1.getHeight())
				 * .outputFormat("JPEG") .outputQuality(1)
				 * .resizer(Resizers.PROGRESSIVE) .antialiasing(Antialiasing.ON)
				 * .dithering(Dithering.ENABLE)
				 * //.scalingMode(ScalingMode.PROGRESSIVE_BILINEAR)
				 * .asBufferedImage();
				 */

				/*
				 * BufferedImage bbi = new BufferedImage(210,
				 * 105,BufferedImage.TYPE_INT_RGB); Graphics2D g =
				 * bbi.createGraphics(); //Graphics g = imagen2.getGraphics();
				 * Graphics2D g2 = (Graphics2D)g; RenderingHints rh = new
				 * RenderingHints( RenderingHints.KEY_TEXT_ANTIALIASING,
				 * RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				 * g2.setRenderingHints(rh); g2.drawImage(bbi, null, 0, 0);
				 */

				/*
				 * BufferedImage dbi = null; dbi = new BufferedImage(210,
				 * 105,BufferedImage.TYPE_INT_RGB); Graphics2D g =
				 * dbi.createGraphics(); AffineTransform at =
				 * AffineTransform.getScaleInstance(210, 105);
				 * g.drawRenderedImage(sbi, at);
				 */

				/*
				 * 
				 * Image ai = imagen2.toImage(); Image rescaled =
				 * ai.getScaledInstance(210, 105, Image.SCALE_AREA_AVERAGING);
				 * 
				 * 
				 * BufferedImage bbi = new BufferedImage(210,
				 * 105,BufferedImage.TYPE_INT_RGB); Graphics2D g =
				 * bbi.createGraphics();
				 */

				// .toFile(new File("C:\\Users\\LUIS S\\Desktop\\Nueva
				// carpeta\\Firmas Java\\thumbnail.jpg"));

				// Metodo en uso

				/*
				 * if((imagen2.getWidth()>=300 ||
				 * imagen2.getHeight()>=300)||(imagen1.getWidth()>=320 ||
				 * imagen1.getHeight()>=320)){ //System.out.println("todos");
				 * BufferedImage img = imagen2.toBufferedImage(); // load image
				 * 220 105 BufferedImage scaledImg = Scalr.resize(img,
				 * Method.AUTOMATIC, imagen1.getWidth(),
				 * imagen1.getHeight(),Scalr.OP_BRIGHTER); // BufferedImage
				 * scaledImg = Scalr.resize(img, Method.BALANCED, 300,
				 * 156,Scalr.OP_BRIGHTER); // BufferedImage scaledImg =
				 * Scalr.resize(img, Method.BALANCED, imagen1.getWidth(),
				 * imagen2.getHeight(),Scalr.OP_ANTIALIAS); imagen2 = new
				 * FastBitmap(scaledImg); } imagen2.saveAsJPG(
				 * "C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\"+ind+"
				 * nuevoresize.jpg");
				 */

				// ind=36;

				if (ind == 36 || ind == 37 || ind == 38 || ind == 39 || ind == 40 || ind == 41 || ind == 41 || ind == 42
						|| ind == 43) {

					// if((imagen2.getWidth()>=300 ||
					// imagen2.getHeight()>=300)||(imagen1.getWidth()>=320 ||
					// imagen1.getHeight()>=320)){
					BufferedImage bi;
					if (ancho <= 1300 && alto <= 1300) {
						bi = Thumbnails
								.of(new File("C:\\Users\\alulab14.INF.001\\Desktop\\Nueva carpeta\\Firmas Java\\" + ind
										+ ".jpg"))
								// BufferedImage bi = Thumbnails.of(new
								// File("C:\\Users\\LUIS S\\Desktop\\Nueva
								// carpeta\\Firmas Java\\37.jpg"))
								.size(imagen1.getWidth() - 22, imagen1.getHeight() - 22).outputFormat("JPG")
								.outputQuality(1).resizer(Resizers.PROGRESSIVE).asBufferedImage();
					} else {
						bi = Thumbnails
								.of(new File("C:\\Users\\alulab14.INF.001\\Desktop\\Nueva carpeta\\Firmas Java\\" + ind
										+ ".jpg"))
								// BufferedImage bi = Thumbnails.of(new
								// File("C:\\Users\\LUIS S\\Desktop\\Nueva
								// carpeta\\Firmas Java\\37.jpg"))
								.size(imagen1.getWidth(), imagen1.getHeight()).outputFormat("JPG").outputQuality(1)
								.resizer(Resizers.PROGRESSIVE).asBufferedImage();
					}
					imagen2 = new FastBitmap(bi);

					// }

					// JOptionPane.showMessageDialog(null, imagen2.toIcon(),
					// "Result "+isuperior+" ", JOptionPane.PLAIN_MESSAGE);
					System.out.println("1Image1: " + imagen1.getWidth() + " " + imagen1.getHeight());
					System.out.println("1Image2: " + imagen2.getWidth() + " " + imagen2.getHeight());
				} else {
					System.out.println("i: " + ind);
					BufferedImage img = imagen2.toBufferedImage(); // load image
																	// 220 105
					BufferedImage scaledImg = Scalr.resize(img, Method.AUTOMATIC, imagen1.getWidth(),
							imagen1.getHeight(), Scalr.OP_BRIGHTER);
					// BufferedImage scaledImg = Scalr.resize(img,
					// Method.BALANCED, 300, 156,Scalr.OP_BRIGHTER);
					// BufferedImage scaledImg = Scalr.resize(img,
					// Method.BALANCED, imagen1.getWidth(),
					// imagen2.getHeight(),Scalr.OP_ANTIALIAS);
					imagen2 = new FastBitmap(scaledImg);
					System.out.println("Image1: " + imagen1.getWidth() + " " + imagen1.getHeight());
					System.out.println("Image2: " + imagen2.getWidth() + " " + imagen2.getHeight());

				}

				// imagen2.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\Nueva
				// carpeta\\Firmas Java\\37Thumbnails.jpg");

				// BufferedImage scaledImg = Scalr.resize(img, Method.QUALITY,
				// 150, 100, Scalr.OP_ANTIALIAS);

				// imagen2.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\Nueva
				// carpeta\\Firmas Java\\nueva_metodo.jpg");

				// imagen2 = new FastBitmap(bi);
				// imagen2.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\Nueva
				// carpeta\\Firmas Java\\nueva_metodo.jpg");
				imagen2.toRGB();
				imagen2.toGrayscale();
				// BradleyLocalThreshold bradleyBase = new
				// BradleyLocalThreshold();
				// bradleyBase.applyInPlace(imagen2);
				// redimensionar2.ApplyInPlace(imagen2);
				o.applyInPlace(imagen2);
				// BradleyLocalThreshold bradleyBase = new
				// BradleyLocalThreshold();
				// bradleyBase.applyInPlace(imagen2);

				// imagen2.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\Nueva
				// carpeta\\Firmas Java\\nueva_imagen_redimensionada.jpg");

				descriptores2 = new ArrayList<FastRetinaKeypoint>();
				if (alto <= 1300 && ancho <= 1300) {
					// freak2.scale=nuevaEscala;
					freak2.scale = 16;
					// porcentajeMinimo = 2;
					porcentajeMinimo = 16;
					distanciaMinima = 60;
					distanciaFiltro = 4;
					System.out.println("scale: si2 " + freak2.scale);
				} else {
					freak2.scale = 18.0f;
					System.out.println("scale: si2222222 " + freak2.scale);
				}

				descriptores2 = freak2.ProcessImage(imagen2);
				// System.out.println("Tamaï¿½o de 2: "+imagen2.getWidth()+"
				// "+imagen2.getHeight());
				// System.out.println("tamaï¿½o 2:"+descriptores2.size());
				// System.out.println();
				System.out.println(descriptores1.size());
				System.out.println(descriptores2.size());

				/*
				 * Firmas.FastBitmap fb4DibujarBase2; fb4DibujarBase2 = new
				 * Firmas.FastBitmap(url2); FastGraphics graficarPuntos2Base2 =
				 * new FastGraphics(fb4DibujarBase2);
				 * //graficarPuntos2Base.setColor(Color.Blue);
				 * graficarPuntos2Base.setColor(Color.Black);
				 * 
				 * 
				 * //Funcion 0 for (int ii = 0; ii < descriptores2.size(); ii++)
				 * { int x1 = (int) descriptores2.get(ii).x; int y1 = (int)
				 * descriptores2.get(ii).y; graficarPuntos2Base2.DrawCircle(x1,
				 * y1, 2); }
				 * 
				 * 
				 * JOptionPane.showMessageDialog(null, fb4DibujarBase2.toIcon(),
				 * "Result con Puntos", JOptionPane.PLAIN_MESSAGE);
				 */

				int contadorMatching = 0;

				double distanciaResultado;
				double porcentaje = 0;

				/*
				 * for (int i = 0; i < descriptores1.size(); i++) {
				 * 
				 * for (int j = 0; j < descriptores2.size(); j++) {
				 * distanciaResultado=distancia.Hamming(descriptores1.get(i).
				 * toBinary(), descriptores2.get(j).toBinary());
				 * //System.out.println(distanciaResultado);
				 * if(distanciaResultado<=umbral)
				 * contadorMatching=contadorMatching+1; break; } }
				 */

				Distance d = new Distance();
				int j;
				double distancia1; // 45

				// Funcion 3, 20
				for (int i = 0; i < descriptores1.size(); i++) {
					// distanciaMinima=0;
					int indiceDistanciaMinima = -1;
					for (j = 0; j < descriptores2.size(); j++) {
						if (descriptores1.get(i).primerosBits(descriptores2.get(j)) >= distanciaFiltro) { // 20
																											// no
																											// falla
																											// 34,
																											// 5
																											// DEFAULT
																											// ,
																											// 14
																											// default
							// 15, falla 14
							// 10, falla 10
							// 5, falla 10
							// 14 , falla 12 en total

							if (descriptores2.get(j).getIndexMatch() == -1) {

								distancia1 = d.Hamming(descriptores1.get(i).toBinary(),
										descriptores2.get(j).toBinary());
								// System.out.println("d = "+distancia1);
								if (distancia1 <= distanciaMinima) {
									// distanciaMinima=distancia;
									indiceDistanciaMinima = j;
									descriptores2.get(indiceDistanciaMinima).setIndexMatch(i);
									break;
								}

							}
						}
					}
					descriptores1.get(i).setIndexMatch(indiceDistanciaMinima);
				}

				int c = 0;
				for (int i = 0; i < descriptores1.size(); i++) {
					if (descriptores1.get(i).getIndexMatch() != -1)
						c++;
				}
				int cor = 0;
				// System.out.println("Similitud: "+contadorMatching);
				System.out.println("Similitud: " + c);
				// System.out.println(descriptores1.get(0).toBinary());
				// System.out.println(descriptores2.get(0).toBinary());

				if (descriptores1.size() > 0) {
					// porcentaje de 1 imagen
					porcentaje = ((c * 100) / descriptores1.size());
					// porcentaje de 2 imagen
					// porcentaje = ((c*100)/descriptores2.size());
					System.out.println("Porcentaje: " + porcentaje);
				}
				// 28, 20
				// 55, ok
				// 20 , ok
				// 10 pp ok
				if (porcentaje >= porcentajeMinimo) { // 20 defualt;
					// default 35 pero probaremos baja resoulucion 30
					// esta entre 34 y 20
					Resultado r1 = new Resultado(ind);
					r1.porcentaje = porcentaje;
					arr.add(r1);

					System.out.println("Ejemplo isuperior: " + isuperior);
					System.out.println("Esta es la imagen conincide:(1xImagen) " + url2);
					System.out.println("------------------------------------------------------------------");
				}
				System.out.println();

			}
			for (int i = 0; i < descriptores1.size(); i++) {
				if (descriptores1.get(i).getIndexMatch() != -1)
					descriptores1.get(i).setIndexMatch(-1);
			}
			arre.add(arr);

			System.out.println("ancho " + ancho + " alto " + alto);
		}

		int kk = 0;
		System.out.println("Lista:");
		for (int i = 0; i < arre.size(); i++) {
			System.out.println("i :" + (i));
			if (arre.get(i).size() >= 2 || arre.get(i).size() == 0)
				kk++;
			for (int k = 0; k < arre.get(i).size(); k++) {

				System.out.println(arre.get(i).get(k).indFirmaMatch + "  p: " + arre.get(i).get(k).porcentaje);

			}
			System.out.println();

		}

		System.out.println("Fallas: " + kk);
		// System.out.println(" "+d1+" "+d2);

	}

	public ArrayList<FirmaRecortada> verificarFirmas11(List<List<PersonaReniec>> lper) {
		ArrayList<FirmaRecortada> limage = new ArrayList<FirmaRecortada>();
		int anchos = 0;
		int altos = 0;
		// 11
		for (int i = 1; i < 2; i++) {

			System.out.println("1111..................." + i);
			// FastBitmap imagen2 = new FastBitmap("C:\\Users\\LUIS
			// S\\Desktop\\Nueva carpeta\\Firmas
			// Java\\Otras_Resoluciones\\objs\\planillon\\rayado_Modificados_v1.jpg");
			FastBitmap imagen2 = new FastBitmap(
					"C:\\Users\\alulab14.INF.001\\Desktop\\Nueva carpeta\\Firmas Java\\Otras_Resoluciones\\objs\\planillon\\"
							+ i + ".jpg");

			// FastBitmap imagen2 = new FastBitmap("C:\\Users\\LUIS
			// S\\Desktop\\Nueva carpeta\\Firmas
			// Java\\Otras_Resoluciones\\objs\\planillon\\2.jpg");

			// Firmas.FastBitmap pp= new FastBitmap("C:\\Users\\LUIS
			// S\\Desktop\\Nueva carpeta\\Firmas
			// Java\\Otras_Resoluciones\\objs\\planillon\\5.jpg");
			// pp.getGrayData();
			// 0 negro; 255 blanco
			Crop objetoCortador;

			// if(i==7 || i == 10)
			if (imagen2.getWidth() < imagen2.getHeight()) {
				Rotate rotarImage = new Rotate(90.0, Rotate.Algorithm.BICUBIC);
				rotarImage.applyInPlace(imagen2);
				rotarImage.applyInPlace(imagen2);
				rotarImage.applyInPlace(imagen2);

				imagen2.toRGB();
				imagen2.toGrayscale();
				OtsuThreshold o = new OtsuThreshold();
				o.applyInPlace(imagen2);

				int ig = 0;
				for (int j = imagen2.getHeight() - 1; j > 0; j--) {
					if (imagen2.getGray(j, imagen2.getWidth() / 2) == 255) {
						ig = j;
						break;
					}
				}
				objetoCortador = new Crop(0, imagen2.getWidth() / 2, imagen2.getWidth() / 2 + imagen2.getWidth() / 3,
						ig - 10);

			} else {

				objetoCortador = new Crop(0, imagen2.getWidth() / 2, imagen2.getWidth() / 3, imagen2.getHeight());
				imagen2.toRGB();
				imagen2.toGrayscale();
				OtsuThreshold o = new OtsuThreshold();
				o.applyInPlace(imagen2);

				// objetoCortador = new
				// Crop(0,0,imagen2.getWidth(),imagen2.getHeight()/5);

			}

			// imagen2.toRGB();
			// imagen2.toGrayscale();
			// OtsuThreshold o = new OtsuThreshold();
			// o.applyInPlace(imagen2);

			// JOptionPane.showMessageDialog(null, imagen2.toIcon(), "Result con
			// Puntos", JOptionPane.PLAIN_MESSAGE);

			objetoCortador.ApplyInPlace(imagen2);
			// imagen2.saveAsJPG("//home//wirox91//Escritorio//Nueva
			// carpeta//Firmas
			// Java//Otras_Resoluciones//objs//planillon//"+i+"r.jpg");
			imagen2.saveAsJPG(
					"C:\\Users\\alulab14.INF.001\\Desktop\\Nueva carpeta\\Firmas Java\\Otras_Resoluciones\\objs\\planillon"
							+ i + "r.jpg");

			System.out.println(imagen2.getHeight() + " " + imagen2.getWidth());

			anchos = imagen2.getWidth() - 1;
			altos = imagen2.getHeight() - 1;

			int factorPixel = 0;
			int vecess = 0;
			if (altos >= 0 && altos <= 900)
				factorPixel = 5;
			else {
				vecess = altos / 900;
				System.out.println("vecesss :" + vecess);
				factorPixel = 5 * vecess;
				System.out.println("factor pixels :" + factorPixel);
			}

			ArrayList<Resultado> al = new ArrayList<Resultado>();
			int x1;
			int y1;
			ArrayList<Integer> a2 = new ArrayList<Integer>();
			int x2;
			int y2;

			/*
			 * int inn=0;
			 * 
			 * for (int k = imagen2.getHeight()-1; k >0; k--) {
			 * //System.out.println("k: "+k); if(imagen2.getGray(k,
			 * imagen2.getWidth()/2)==0){ inn=k; //break; } }
			 */

			// System.out.println("in "+inn);

			int aa = imagen2.getWidth() / 2;
			// int aa=imagen2.getWidth()/3;
			int indice = 0;
			for (int j = 0; j < imagen2.getHeight() - 1; j++) {
				if (imagen2.getGray(j, aa) == 0) {
					indice = j;
					break;
				}
			}
			System.out.println("indice: " + indice);
			int t1 = 0;
			for (int r = imagen2.getWidth() / 2; r < imagen2.getWidth() - 1; r++) {
				if (imagen2.getGray(indice, r) == 255) {
					t1 = r;
					break;
				}
			}
			System.out.println("r: " + t1);
			/*
			 * int es=0; for (int j = imagen2.getHeight()-1; j > 0; j--) {
			 * if(imagen2.getGray(j,mitad)==0) es=j; } System.out.println(
			 * "es:  "+es);
			 */

			/*
			 * 
			 * int in = imagen2.getWidth()-1; int cf=0; for (int k = 0; k <
			 * imagen2.getHeight()-1; k++) { if(imagen2.getGray(k, in)==0) cf++;
			 * System.out.println("k "+k+" "+imagen2.getGray(k, in));
			 * 
			 * } System.out.println("cf: "+cf);
			 */

			// System.out.println("jj : "+indiceA);
			// int ind = imagen2.getWidth()-50;
			/*
			 * int ig=0; for (int j = imagen2.getHeight()-1; j >0 ; j--) {
			 * if(imagen2.getGray(j, imagen2.getWidth()/2)==255) { ig=j; break;}
			 * } ig = ig -10; System.out.println("ig "+ig);
			 */

			int ind = imagen2.getWidth() - 1;
			ind = t1;
			// ArrayList<ArrayList<Resultado>> a3 = new
			// ArrayList<ArrayList<Resultado>();

			List<List<Resultado>> a3 = new ArrayList<List<Resultado>>();
			Resultado d;
			int cont = 0;

			int c = 0;
			int llego = 0;
			int dos = 0;
			// ArrayList<Integer>

			int j;
			int tmp1 = 0;
			int tmp2 = 0;
			int nollego = 0;
			int pasoPrimerNegro = 0;

			while (true) {
				c = 0;
				llego = 0;
				nollego = 0;
				pasoPrimerNegro = 0;
				// for (j = inn+10; j > 0 ; j--) {
				// for (j = imagen2.getHeight()-1; j > 0 ; j--) {
				for (j = imagen2.getHeight() - 1; j > 0; j--) {
					if (imagen2.getGray(j, ind) == 0) {
						c++;
						pasoPrimerNegro = j;

					}
					// else{
					// System.out.println("inn "+inn);
					// System.out.println("k "+ind);
					// break;
					// }

					// else
					// {
					// nollego=1;
					// break;
					// }

					// if(c>imagen2.getHeight()/2){
					if (c > imagen2.getHeight() / 2) {
						llego = 1;
						tmp1 = ind;
						break;
					}
				}
				// System.out.println("ind "+ind+" "+j+" :"+c+" llego:"+llego);
				if (llego == 1)
					break;
				ind--;

			}

			j = 0;
			llego = 0;
			ind = ind - 50;

			while (true) {
				c = 0;
				llego = 0;
				pasoPrimerNegro = 0;
				// for (j = inn+10; j > 0 ; j--) {
				for (j = imagen2.getHeight() - 1; j > 0; j--) {
					// for (j = ig-1; j > 0 ; j--) {
					/*
					 * if (imagen2.getGray(j, ind)==255 && c>20) {
					 * //pasoPrimerNegro=j; c++; break; } else if
					 * (imagen2.getGray(j, ind)==0) { //pasoPrimerNegro=j; c++;
					 * }
					 */

					if (imagen2.getGray(j, ind) == 0) { // pasoPrimerNegro=j;
						c++;
					}
					// else
					// {
					// break;
					// }

					if (c > imagen2.getHeight() / 2) {
						llego = 1;
						tmp2 = ind;
						break;
					}
				}
				// System.out.println("ind "+ind+" "+j+" :"+c+" llego:"+llego);
				if (llego == 1)
					break;
				ind--;

			}

			int talto1 = 0;
			int talto2 = 0;

			for (int k = imagen2.getHeight() - 1; k > 0; k--) {
				// for (int k = ig-1; k >0 ; k--) {
				if (imagen2.getGray(k, tmp1) == 0) {
					// System.out.println("ind1: "+k);
					talto1 = k;
					break;
				}

			}

			for (int k = imagen2.getHeight() - 1; k > 0; k--) {
				// for (int k = ig-1; k >0 ; k--) {
				if (imagen2.getGray(k, tmp2) == 0) {
					// System.out.println("ind2: "+k);
					talto2 = k;
					break;
				}

			}

			System.out.println("i1: " + tmp1 + " " + talto1);
			System.out.println("i2: " + tmp2 + " " + talto2);
			int veces = 0;
			int ancho = tmp1 - tmp2;
			int contador = 0;
			int hizobreak = 0;
			int malbreak = 0;
			ArrayList<Integer> listaLineas = new ArrayList<Integer>();
			System.out.println("Anchi " + ancho);
			int band1 = 0;

			System.out.println("tmp1 :" + tmp1 + " " + tmp2);

			// Extra
			/*
			 * for (int k = talto2; k >0; k--) { int cgg=0; for (int k2 = 0; k2
			 * < ancho; k2++) { if(imagen2.getGray(k, k2)==0) cgg++;
			 * if(cgg>=ancho/2) break; } if(cgg>=ancho/2){ talto2=k; break;
			 * 
			 * } }
			 */
			System.out.println("t alto: " + talto2);
			// talto2-10
			for (int k = talto2 - factorPixel; k > 0; k--) {
				contador = 0;
				hizobreak = 0;
				malbreak = 0;

				if (veces == 8)
					break;
				for (int h = tmp2; h < tmp1; h++) {
					// System.out.println("ancho"+h+" alto "+k);
					if (imagen2.getGray(k, h) == 0)
						contador++;
					else {
						malbreak = 1;
						break;
					}
					if (contador >= ancho / 2) {
						hizobreak = 1;
						break;
					}

				}

				if (malbreak != 1) {
					if (hizobreak == 1)
						System.out.println("linea: " + k);
					// k=k-10;
					System.out.println("factor: " + factorPixel);
					k = k - factorPixel;
					veces++;

					listaLineas.add(k);
					// band1=1;
				}

				// Extra
				/*
				 * if(band1==1) for (int ij = k; ij > 0 ; ij--) { int cv=0; int
				 * pasoFor=0; for(int jh=tmp1;tmp1<tmp2;jh++) {
				 * if(imagen2.getGray(ij, jh)==0) cv++; if(cv>=ancho/2) {
				 * pasoFor=1; break;} pasoFor=0; } if(pasoFor==0){ k=ij; break;
				 * } } band1=0; System.out.println("k actual: "+k);
				 */

			}

			System.out.println("tam�o: " + listaLineas.size());
			int alto;
			for (int k = 0; k < 8; k++) {

				// alto = listaLineas.get(k)-listaLineas.get(k+1);

				System.out.println("Linea actual ..........." + k);
				if (k == 0) {
					System.out.println("11");
					alto = talto2 - listaLineas.get(k);
					objetoCortador = new Crop(listaLineas.get(k), tmp2, ancho, alto);
				} else if (k == 7) {
					System.out.println("223");
					alto = listaLineas.get(k - 1) - listaLineas.get(k);
					objetoCortador = new Crop(listaLineas.get(k), tmp2, ancho, alto);

				} else {
					System.out.println("22");
					alto = listaLineas.get(k) - listaLineas.get(k + 1);
					objetoCortador = new Crop(listaLineas.get(k), tmp2, ancho, alto);
				}

				imagen2.saveAsJPG(
						"C:\\Users\\alulab14.INF.001\\Desktop\\Nueva carpeta\\Firmas Java\\Otras_Resoluciones\\objs\\planillon\\"
								+ i + "rr.jpg");
				FastBitmap i3 = new FastBitmap(
						"C:\\Users\\alulab14.INF.001\\Desktop\\Nueva carpeta\\Firmas Java\\Otras_Resoluciones\\objs\\planillon\\"
								+ i + "rr.jpg");
				// i3 = imagen2;
				objetoCortador.ApplyInPlace(i3);
				JOptionPane.showMessageDialog(null, i3.toIcon(), "Result " + i + " ", JOptionPane.PLAIN_MESSAGE);
				FirmaRecortada fr = new FirmaRecortada();
				fr.img = i3.toBufferedImage();
				fr.ancho = anchos;
				fr.alto = altos;
				limage.add(fr);
				// limage.add(veces, fr);

				System.out.println("alto: " + alto);
			}

			/*
			 * for (int k = ind; k > imagen2.getWidth()-206; k--) { // al = new
			 * ArrayList<Resultado>(); cont=0; for (int j = 0; j <
			 * imagen2.getHeight()-1; j++) {
			 * 
			 * // if(imagen2.getGray(j,ind)==0) // { // d= new Resultado(k); //
			 * d.porcentaje=j; // al.add(d); // }
			 * 
			 * if(imagen2.getGray(j,ind)==0) cont++; } //a3.add(al);
			 * System.out.println("cont maximo:"+cont); }
			 */

			/*
			 * int mayor=-99999; int indmayor=0; for (int j = 0; j < a3.size();
			 * j++) { if(a3.get(j).size()>mayor) { mayor = a3.get(j).size();
			 * indmayor=j; }
			 * 
			 * 
			 * }
			 */
			// System.out.println("mayor numero: "+mayor);
			// System.out.println("mayor: "+indmayor);

		}

		return limage;
	}

	public ArrayList<PersonaReniec> procesar4(ArrayList<FirmaRecortada> lbi, List<List<PersonaReniec>> lper)
			throws IOException {
		ArrayList<PersonaReniec> lnuevo = null;
		// double umbral=85;
		double umbral = 5;
		int d1 = 0, d2 = 0;
		int porcentajeMinimo = 34;
		// 16
		Resize redimensionar = new Resize(260, 116);
		FastCornersDetector fast = new FastCornersDetector(); // Inicio del
																// algoritmo
																// Fast

		List<List<Resultado>> arre = new ArrayList<List<Resultado>>();

		ArrayList<Resultado> arr;

		ArrayList<ArrayList<Double>> e1 = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> e2 = null;
		int band = 0;
		int distanciaMinima = 45; // 41 default 45 y 20 jpg , no puede subir mas
									// , por ahora, DEFAULT = 45
		int distanciaFiltro = 14;
		int ind = 0;
		for (int isuperior = 7; isuperior > (8 - (lper.size() + 1)); isuperior--) {
			band = 0;
			System.out.println(
					"3333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333");
			System.out.println("i spueroprrr : " + isuperior);
			float nuevaEscala = 16;
			FastBitmap imagen1;
			FastBitmap imagen2;
			FastRetinaKeypointDetector freak1 = new FastRetinaKeypointDetector(fast); // Incio
																						// del
																						// algoritmo
																						// Freak
			FastRetinaKeypointDetector freak2 = new FastRetinaKeypointDetector(fast);
			List<FastRetinaKeypoint> descriptores1;
			List<FastRetinaKeypoint> descriptores2;
			Distance distancia = new Distance();

			OtsuThreshold o = new OtsuThreshold();

			// imagen1 = new FastBitmap(url1);
			// imagen1.toRGB();
			// imagen1.toGrayscale();
			// BradleyLocalThreshold bradley4 = new BradleyLocalThreshold();
			// bradley4.applyInPlace(imagen1);
			// o.applyInPlace(imagen1);
			// imagen1.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\Nueva
			// carpeta\\Firmas Java\\imagen_primera_procesada.jpg");

			System.out.println("ISUPERIOR : " + isuperior);

			imagen1 = new FastBitmap(lbi.get(isuperior).img);
			int ancho = lbi.get(isuperior).ancho;
			int alto = lbi.get(isuperior).alto;
			descriptores1 = new ArrayList<FastRetinaKeypoint>();
			if (alto <= 1300 && ancho <= 1300) {

				freak1.scale = nuevaEscala;
				freak1.scale = 16;
				porcentajeMinimo = 16;
				distanciaMinima = 60;
				distanciaFiltro = 4;
				System.out.println("scale: si1 " + freak1.scale);
			} else {
				freak1.scale = 18.0f;
				System.out.println("scale: si1111111 " + freak1.scale);
			}
			descriptores1 = freak1.ProcessImage(imagen1);
			JOptionPane.showMessageDialog(null, imagen1.toIcon(), "Result " + isuperior + " ",
					JOptionPane.PLAIN_MESSAGE);

			arr = new ArrayList<Resultado>();

			// descriptores 1 limpios;

			// for (ind = 0; ind < lper.size(); ind++) { //total de la lista de
			// lista

			for (int ii = 0; ii < lper.get(ind).size(); ii++) { // dentro de 1
																// lista X
																// persona

				String url2 = new String(
						"C:\\Users\\alulab14.INF.001\\Desktop\\Nueva carpeta\\Firmas Java\\Imagenes\\firmas.jpg\\"
								+ lper.get(ind).get(ii).getIdFirma());

				System.out.println("Figura 2: " + url2);
				imagen2 = new FastBitmap(url2);

				JOptionPane.showMessageDialog(null, imagen2.toIcon(), "Result " + isuperior + " ",
						JOptionPane.PLAIN_MESSAGE);
				// ind=36;
				// Muestro la firma

				if (ind == 36 || ind == 37 || ind == 38 || ind == 39 || ind == 40 || ind == 41 || ind == 41 || ind == 42
						|| ind == 43) {

					// if((imagen2.getWidth()>=300 ||
					// imagen2.getHeight()>=300)||(imagen1.getWidth()>=320 ||
					// imagen1.getHeight()>=320)){
					BufferedImage bi;
					if (ancho <= 1300 && alto <= 1300) {
						bi = Thumbnails
								.of(new File(
										"C:\\Users\\alulab14.INF.001\\Desktop\\Nueva carpeta\\Firmas Java\\Imagenes\\firmas.jpg\\"
												+ lper.get(ind).get(ii).getIdFirma()))
								// BufferedImage bi = Thumbnails.of(new
								// File("C:\\Users\\LUIS S\\Desktop\\Nueva
								// carpeta\\Firmas Java\\37.jpg"))
								.size(imagen1.getWidth() - 22, imagen1.getHeight() - 22).outputFormat("JPG")
								.outputQuality(1).resizer(Resizers.PROGRESSIVE).asBufferedImage();
					} else {
						bi = Thumbnails
								.of(new File(
										"C:\\Users\\alulab14.INF.001\\Desktop\\Nueva carpeta\\Firmas Java\\Imagenes\\firmas.jpg\\"
												+ lper.get(ind).get(ii).getIdFirma()))
								// BufferedImage bi = Thumbnails.of(new
								// File("C:\\Users\\LUIS S\\Desktop\\Nueva
								// carpeta\\Firmas Java\\37.jpg"))
								.size(imagen1.getWidth(), imagen1.getHeight()).outputFormat("JPG").outputQuality(1)
								.resizer(Resizers.PROGRESSIVE).asBufferedImage();
					}
					imagen2 = new FastBitmap(bi);

					// }

					// JOptionPane.showMessageDialog(null, imagen2.toIcon(),
					// "Result "+isuperior+" ", JOptionPane.PLAIN_MESSAGE);
					System.out.println("1Image1: " + imagen1.getWidth() + " " + imagen1.getHeight());
					System.out.println("1Image2: " + imagen2.getWidth() + " " + imagen2.getHeight());
				} else {
					System.out.println("i: " + ind);
					BufferedImage img = imagen2.toBufferedImage(); // load image
																	// 220 105
					BufferedImage scaledImg = Scalr.resize(img, Method.AUTOMATIC, imagen1.getWidth(),
							imagen1.getHeight(), Scalr.OP_BRIGHTER);
					// BufferedImage scaledImg = Scalr.resize(img,
					// Method.BALANCED, 300, 156,Scalr.OP_BRIGHTER);
					// BufferedImage scaledImg = Scalr.resize(img,
					// Method.BALANCED, imagen1.getWidth(),
					// imagen2.getHeight(),Scalr.OP_ANTIALIAS);
					imagen2 = new FastBitmap(scaledImg);
					System.out.println("Image1: " + imagen1.getWidth() + " " + imagen1.getHeight());
					System.out.println("Image2: " + imagen2.getWidth() + " " + imagen2.getHeight());

				}

				imagen2.toGrayscale();
				// BradleyLocalThreshold bradleyBase = new
				// BradleyLocalThreshold();
				// bradleyBase.applyInPlace(imagen2);
				// redimensionar2.ApplyInPlace(imagen2);
				o.applyInPlace(imagen2);
				// BradleyLocalThreshold bradleyBase = new
				// BradleyLocalThreshold();
				// bradleyBase.applyInPlace(imagen2);

				descriptores2 = new ArrayList<FastRetinaKeypoint>();
				if (alto <= 1300 && ancho <= 1300) {
					// freak2.scale=nuevaEscala;
					freak2.scale = 16;
					// porcentajeMinimo = 2;
					porcentajeMinimo = 16;
					distanciaMinima = 60;
					distanciaFiltro = 4;
					System.out.println("scale: si2 " + freak2.scale);
				} else {
					freak2.scale = 18.0f;
					System.out.println("scale: si2222222 " + freak2.scale);
				}

				descriptores2 = freak2.ProcessImage(imagen2);
				// System.out.println("Tama�o de 2: "+imagen2.getWidth()+"
				// "+imagen2.getHeight());
				// System.out.println("tama�o 2:"+descriptores2.size());
				// System.out.println();
				System.out.println(descriptores1.size());
				System.out.println(descriptores2.size());

				int contadorMatching = 0;

				double distanciaResultado;
				double porcentaje = 0;

				Distance d = new Distance();
				int j;
				double distancia1; // 45

				// Funcion 3, 20
				for (int i = 0; i < descriptores1.size(); i++) {
					// distanciaMinima=0;
					int indiceDistanciaMinima = -1;
					for (j = 0; j < descriptores2.size(); j++) {
						if (descriptores1.get(i).primerosBits(descriptores2.get(j)) >= distanciaFiltro) { // 20
																											// no
																											// falla
																											// 34,
																											// 5
																											// DEFAULT
																											// ,
																											// 14
																											// default
							// 15, falla 14
							// 10, falla 10
							// 5, falla 10
							// 14 , falla 12 en total

							if (descriptores2.get(j).getIndexMatch() == -1) {

								distancia1 = d.Hamming(descriptores1.get(i).toBinary(),
										descriptores2.get(j).toBinary());
								// System.out.println("d = "+distancia1);
								if (distancia1 <= distanciaMinima) {
									// distanciaMinima=distancia;
									indiceDistanciaMinima = j;
									descriptores2.get(indiceDistanciaMinima).setIndexMatch(i);
									break;
								}

							}
						}
					}
					descriptores1.get(i).setIndexMatch(indiceDistanciaMinima);
				}
				// Matching de imagen candidatos vs iomagen tomada

				int c = 0;
				for (int i = 0; i < descriptores1.size(); i++) {
					if (descriptores1.get(i).getIndexMatch() != -1)
						c++;
				}
				int cor = 0;
				// System.out.println("Similitud: "+contadorMatching);
				System.out.println("Similitud: " + c);
				// System.out.println(descriptores1.get(0).toBinary());
				// System.out.println(descriptores2.get(0).toBinary());

				if (descriptores1.size() > 0) {
					// porcentaje de 1 imagen
					porcentaje = ((c * 100) / descriptores1.size());
					// porcentaje de 2 imagen
					// porcentaje = ((c*100)/descriptores2.size());
					System.out.println("Porcentaje: " + porcentaje);
					if (band == 0) {
						e2 = new ArrayList<Double>();
						e2.add(porcentaje);
						band = 1;
					} else
						e2.add(porcentaje);
				}
				// 28, 20
				// 55, ok
				// 20 , ok
				// 10 pp ok
				if (porcentaje >= porcentajeMinimo) { // 20 defualt;
					// default 35 pero probaremos baja resoulucion 30
					// esta entre 34 y 20
					Resultado r1 = new Resultado(ind);
					r1.porcentaje = porcentaje;
					arr.add(r1);

					System.out.println("Ejemplo isuperior: " + isuperior);
					System.out.println("Esta es la imagen conincide:(1xImagen) " + url2);
					System.out.println(
							"------------------------------------------------------------------------------------------");
				}
				System.out.println();

				for (int i = 0; i < descriptores1.size(); i++) {
					if (descriptores1.get(i).getIndexMatch() != -1)
						descriptores1.get(i).setIndexMatch(-1);
				}

				arre.add(arr); // firmas q pasaron el umbral son n de una
								// persona

			}
			e1.add(e2);
			// Fin for 1

			// }
			// Fin for 2

			System.out.println("ancho " + ancho + " alto " + alto);
			ind++;

		}

		int kk = 0;
		System.out.println("Lista:");
		for (int i = 0; i < arre.size(); i++) {
			System.out.println("i :" + (i));
			if (arre.get(i).size() >= 2 || arre.get(i).size() == 0)
				kk++;
			for (int k = 0; k < arre.get(i).size(); k++) {

				System.out.println(arre.get(i).get(k).indFirmaMatch + "  p: " + arre.get(i).get(k).porcentaje);

			}
			System.out.println();

		}

		System.out.println("Fallas: " + kk);
		// System.out.println(" "+d1+" "+d2);

		double mayor = -9999;
		int indi1 = 0;
		int indi2 = 0;
		lnuevo = new ArrayList<PersonaReniec>();
		for (int i = 0; i < e1.size(); i++) {
			mayor = -9999;
			for (int jl = 0; jl < e1.get(i).size(); jl++) {

				if (mayor < e1.get(i).get(jl)) {
					mayor = e1.get(i).get(jl);
					indi1 = i;
					indi2 = jl;
				}

				/*
				 * if(e1.get(i).get(jl)>=34){ lnuevo.add(lper.get(i).get(jl)); }
				 * else{ PersonaReniec pe = new PersonaReniec();
				 * pe.setDni("00000000"); lnuevo.add(pe); }
				 */

				System.out.println("porcentaje " + e1.get(i).get(jl));

			}
			if (mayor >= 21) // 34
			{
				lnuevo.add(lper.get(indi1).get(indi2));
			} else {
				PersonaReniec pe = new PersonaReniec();
				pe.setDni("00000000");
				lnuevo.add(pe);
			}
		}

		// System.out.println("size: "+e1.size());

		return lnuevo;
	}

	
	List<PersonaReniec> procesarFirmas(ArrayList<FirmaRecortada> listaFirmas, List<List<PersonaReniec>> listaDeListaPersonas, String urlBaseDeDatos)
			throws IOException {

		double umbral = 5;
		int d1 = 0, d2 = 0;
		int porcentajeMinimo = 38; // 35 16
		Resize redimensionar = new Resize(260, 116);
		FastCornersDetector fast = new FastCornersDetector(); // Inicio del algoritmo Fast
		
		List<List<Resultado>> arre = new ArrayList<List<Resultado>>();
		ArrayList<Resultado> arr = null;
		arre = new ArrayList<List<Resultado>>();
		
		int distanciaMinima = 45; 
		int distanciaFiltro = 14;

		List<List<PersonaReniecPorcentaje>> listaDeListasPersonasReniecPorcentaje = new ArrayList<List<PersonaReniecPorcentaje>>();
		ArrayList<PersonaReniecPorcentaje> listaPorcentajesCandidato = null;

		
		// Por persona
		int indiceFirmas = 0;
		for (int indicePersonaLista1 = 0; indicePersonaLista1 < listaDeListaPersonas.size(); indicePersonaLista1++) {
			
			float escalaActual = 20;

			FastBitmap imagen1;
			FastBitmap imagen2;
			FastRetinaKeypointDetector freak1 = new FastRetinaKeypointDetector(fast); // Incio del algoritmo Freak
			FastRetinaKeypointDetector freak2 = new FastRetinaKeypointDetector(fast);
			List<FastRetinaKeypoint> descriptores1;
			List<FastRetinaKeypoint> descriptores2;
			Distance distancia = new Distance();
			OtsuThreshold o = new OtsuThreshold();
			
			imagen1 = new FastBitmap(listaFirmas.get(indiceFirmas).img);
			int ancho = listaFirmas.get(indiceFirmas).ancho;
			int alto = listaFirmas.get(indiceFirmas).alto;
			descriptores1 = new ArrayList<FastRetinaKeypoint>();
			if (alto <= 1300 && ancho <= 1300) {
				freak1.scale = escalaActual;
				// freak1.scale=16;
				porcentajeMinimo = 16;
				distanciaMinima = 60;
				distanciaFiltro = 4;
			} else {
				freak1.scale = 18.0f;
			}
			descriptores1 = freak1.ProcessImage(imagen1);
			//JOptionPane.showMessageDialog(null, imagen1.toIcon(), "Result " + indiceFirmas + " ",
			//JOptionPane.PLAIN_MESSAGE);

			int indiceCandidatos;
			System.out.println("Registro persona: " + indicePersonaLista1);
			listaPorcentajesCandidato = new ArrayList<PersonaReniecPorcentaje>();
	
			
			for (indiceCandidatos = 0; indiceCandidatos < listaDeListaPersonas.get(indicePersonaLista1).size(); indiceCandidatos++) {

				arr = new ArrayList<Resultado>();
				
				//Direccion base de datos
				String url2 = new String(urlBaseDeDatos+"\\"
				+ listaDeListaPersonas.get(indicePersonaLista1).get(indiceCandidatos).getIdFirma());
				imagen2 = new FastBitmap(url2);

				
				String cadena = new String(listaDeListaPersonas.get(indicePersonaLista1).get(indiceCandidatos).getIdFirma());
				if (cadena.equals("f036.jpg") || cadena.equals("f037.jpg") || cadena.equals("f038.jpg")
						|| cadena.equals("f039.jpg") || cadena.equals("f040.jpg") || cadena.equals("f041.jpg")
						|| cadena.equals("f042.jpg") || cadena.equals("f043.jpg")) {
					BufferedImage bufferImageTemporal = null;
					if (ancho <= 1300 && alto <= 1300) {
						bufferImageTemporal = Thumbnails
								.of(new File(url2))
								.size(imagen1.getWidth() - 22, imagen1.getHeight() - 22).outputFormat("JPG")
								.outputQuality(1).resizer(Resizers.PROGRESSIVE).asBufferedImage();
					} else {
						bufferImageTemporal = Thumbnails
								.of(new File(url2))
								.size(imagen1.getWidth(), imagen1.getHeight()).outputFormat("JPG").outputQuality(1)
								.resizer(Resizers.PROGRESSIVE).asBufferedImage();
					}
					imagen2 = new FastBitmap(bufferImageTemporal);
					// JOptionPane.showMessageDialog(null, imagen2.toIcon(),
					// "Result "+isuperior+" ", JOptionPane.PLAIN_MESSAGE);
				} else {
					BufferedImage img = imagen2.toBufferedImage(); 
					BufferedImage scaledImg = Scalr.resize(img, Method.AUTOMATIC, imagen1.getWidth(),
							imagen1.getHeight(), Scalr.OP_BRIGHTER);
					imagen2 = new FastBitmap(scaledImg);
				}

			
				imagen2.toRGB();
				imagen2.toGrayscale();
				o.applyInPlace(imagen2);
				
				descriptores2 = new ArrayList<FastRetinaKeypoint>();
				if (alto <= 1300 && ancho <= 1300) {
					freak2.scale = escalaActual;
					porcentajeMinimo = 16;
					distanciaMinima = 60;
					distanciaFiltro = 4;
				} else {
					freak2.scale = 18.0f;
				}

				descriptores2 = freak2.ProcessImage(imagen2);
				int contadorMatching = 0;
				double distanciaResultado;
				double porcentaje = 0;
				Distance d = new Distance();
				int j;
				
				double distancia1; 
				for (int i = 0; i < descriptores1.size(); i++) {
					int indiceDistanciaMinima = -1;
					for (j = 0; j < descriptores2.size(); j++) {
						if (descriptores1.get(i).primerosBits(descriptores2.get(j)) >= distanciaFiltro) { 
							if (descriptores2.get(j).getIndexMatch() == -1) {
								distancia1 = d.Hamming(descriptores1.get(i).toBinary(),
										descriptores2.get(j).toBinary());
								if (distancia1 <= distanciaMinima) {
									indiceDistanciaMinima = j;
									descriptores2.get(indiceDistanciaMinima).setIndexMatch(i);
									break;
								}

							}
						}
					}
					descriptores1.get(i).setIndexMatch(indiceDistanciaMinima);
				}

				int c = 0;
				for (int i = 0; i < descriptores1.size(); i++) {
					if (descriptores1.get(i).getIndexMatch() != -1)
						c++;
				}
				int cor = 0;
				if (descriptores1.size() > 0) {
					porcentaje = ((c * 100) / descriptores1.size());
				}

				if (porcentaje >= porcentajeMinimo) { 
					//Porcentaje paso umbral
					Resultado r1 = new Resultado(indiceCandidatos);
					r1.porcentaje = porcentaje;
					arr.add(r1);
					PersonaReniecPorcentaje personaReniecPorcentajeTemporal;
					personaReniecPorcentajeTemporal = new PersonaReniecPorcentaje();
					personaReniecPorcentajeTemporal.pe = listaDeListaPersonas.get(indicePersonaLista1).get(indiceCandidatos);
					personaReniecPorcentajeTemporal.porcentaje = porcentaje;
					listaPorcentajesCandidato.add(personaReniecPorcentajeTemporal);
					System.out.println("id: "+indicePersonaLista1);
					System.out.println("url: "+url2);
					System.out.println("porcentaje: "+porcentaje);
				} else {
					//Porcentaje 0
					PersonaReniecPorcentaje personaReniecPorcentajeTemporal;
					personaReniecPorcentajeTemporal = new PersonaReniecPorcentaje();
					personaReniecPorcentajeTemporal.pe = listaDeListaPersonas.get(indicePersonaLista1).get(indiceCandidatos);
					personaReniecPorcentajeTemporal.porcentaje = 0;
					listaPorcentajesCandidato.add(personaReniecPorcentajeTemporal);
				}

				for (int i = 0; i < descriptores1.size(); i++) {
					if (descriptores1.get(i).getIndexMatch() != -1)
						descriptores1.get(i).setIndexMatch(-1);
				}

				// Fin for grupo de personas
			}

			arre.add(arr);
			listaDeListasPersonasReniecPorcentaje.add(listaPorcentajesCandidato);
			// Fin del for por persona
			indiceFirmas++;
		}

		
		ArrayList<PersonaReniec> lrestorno = new ArrayList<PersonaReniec>();
		double mayor = -9999;
		int indice1 = 0;
		int indice2 = 0;
		int nunca = 0;


		for (int i = 0; i < listaDeListasPersonasReniecPorcentaje.size(); i++) {
			//System.out.println("Registro:  " + (i + 1));
			nunca = 0;
			mayor = -9999;
			for (int j = 0; j < listaDeListasPersonasReniecPorcentaje.get(i).size(); j++) {

				if (listaDeListasPersonasReniecPorcentaje.get(i).get(j).porcentaje > 0) {
					nunca = 1;
					if (mayor < listaDeListasPersonasReniecPorcentaje.get(i).get(j).porcentaje) {
						mayor = listaDeListasPersonasReniecPorcentaje.get(i).get(j).porcentaje;
						indice1 = i;
						indice2 = j;
					
					}
				}

			}

			if (nunca == 0) {
				PersonaReniec persona;
				persona = new PersonaReniec();
				persona.setDni("00000000");
				lrestorno.add(persona);
			} else
				lrestorno.add(listaDeListasPersonasReniecPorcentaje.get(indice1).get(indice2).pe);

		}

		return lrestorno;
	}
	

List<List<PersonaReniec>> llenarDatosPruebaListaDeLista() {

		List<List<PersonaReniec>> lper = new ArrayList<List<PersonaReniec>>();

		ArrayList<PersonaReniec> le = new ArrayList<PersonaReniec>(); // 1

		PersonaReniec per = new PersonaReniec();
		per.setNombre("Paola");
		per.setApellidos("Zapata Garcia");
		per.setDni("8237514");
		per.setIdFirma("f002.jpg");

		le.add(per);

		per = new PersonaReniec();
		per.setNombre("Maria Elenea");
		per.setApellidos("Durand Novoa");
		per.setDni("6114817");
		per.setIdFirma("f003.jpg");

		le.add(per);
		lper.add(le);

		le = new ArrayList<PersonaReniec>(); // 2

		per = new PersonaReniec();
		per.setNombre("Juan Carlos");
		per.setApellidos("Deza Porcel");
		per.setDni("8596098");
		per.setIdFirma("f018.jpg");

		le.add(per);

		per = new PersonaReniec();
		per.setNombre("Ian Paul");
		per.setApellidos("Guzman Castañeda");
		per.setDni("46515871");
		per.setIdFirma("f019.jpg");

		le.add(per);

		lper.add(le);

		le = new ArrayList<PersonaReniec>(); // 3

		per = new PersonaReniec();
		per.setNombre("Jose");
		per.setApellidos("Calixto Aiquipa");
		per.setDni("10198266");
		per.setIdFirma("f025.jpg");

		le.add(per);

		per = new PersonaReniec();
		per.setNombre("Crisostomo");
		per.setApellidos("Santiago Monteagudo");
		per.setDni("69384231");
		per.setIdFirma("f043.jpg");

		le.add(per);

		lper.add(le);

		le = new ArrayList<PersonaReniec>(); // 4

		per = new PersonaReniec();
		per.setNombre("Armando");
		per.setApellidos("Pacheco Hijon");
		per.setDni("7968967");
		per.setIdFirma("f030.jpg");

		le.add(per);

		per = new PersonaReniec();
		per.setNombre("Juan Daniel");
		per.setApellidos("Codarlupo Gomez");
		per.setDni("8501853");
		per.setIdFirma("f029.jpg");

		le.add(per);

		lper.add(le);

		le = new ArrayList<PersonaReniec>(); // 5

		per = new PersonaReniec();
		per.setNombre("Rosaura Manuela");
		per.setApellidos("Carmeline Guerrero");
		per.setDni("10132273");
		per.setIdFirma("f058.jpg");

		le.add(per);

		per = new PersonaReniec();
		per.setNombre("Luis");
		per.setApellidos("Silvestre Garro");
		per.setDni("40896756");
		per.setIdFirma("f057.jpg");

		le.add(per);

		lper.add(le);

		le = new ArrayList<PersonaReniec>(); // 6

		per = new PersonaReniec();
		per.setNombre("Wally Esteban");
		per.setApellidos("Trinidad Collazos");
		per.setDni("55443322");
		per.setIdFirma("f041.jpg");

		le.add(per);

		per = new PersonaReniec();
		per.setNombre("Juana Josefina");
		per.setApellidos("Vargas Pelucho");
		per.setDni("78901234");
		per.setIdFirma("f042.jpg");

		le.add(per);

		lper.add(le);

		le = new ArrayList<PersonaReniec>(); // 7

		per = new PersonaReniec();
		per.setNombre("Hermes");
		per.setApellidos("Salas Rodriguez");
		per.setDni("43026536");
		per.setIdFirma("f049.jpg");

		le.add(per);

		per = new PersonaReniec();
		per.setNombre("Juana Josefina");
		per.setApellidos("Vargas Pelucho");
		per.setDni("78901234");
		per.setIdFirma("f042.jpg");

		le.add(per);

		lper.add(le);

		
		le = new ArrayList<PersonaReniec>(); // 8

		per = new PersonaReniec();
		per.setNombre("Wally Esteban");
		per.setApellidos("Trinidad Collazos");
		per.setDni("55443322");
		per.setIdFirma("f041.jpg");

		le.add(per);

		per = new PersonaReniec();
		per.setNombre("Luis");
		per.setApellidos("Silvestre Garro");
		per.setDni("40896756");
		per.setIdFirma("f057" + "" + "" + ".jpg");

		le.add(per);

		lper.add(le);
/*
		// Prueba con 16
		per = new PersonaReniec();
		per.setNombre("Paola");
		per.setApellidos("Zapata Garcia");
		per.setDni("8237514");
		per.setIdFirma("f002.jpg");

		le.add(per);

		per = new PersonaReniec();
		per.setNombre("Maria Elenea");
		per.setApellidos("Durand Novoa");
		per.setDni("6114817");
		per.setIdFirma("f003.jpg");

		le.add(per);
		lper.add(le);

		le = new ArrayList<PersonaReniec>(); // 2

		per = new PersonaReniec();
		per.setNombre("Juan Carlos");
		per.setApellidos("Deza Porcel");
		per.setDni("8596098");
		per.setIdFirma("f018.jpg");

		le.add(per);

		per = new PersonaReniec();
		per.setNombre("Ian Paul");
		per.setApellidos("Guzman Castañeda");
		per.setDni("46515871");
		per.setIdFirma("f019.jpg");

		le.add(per);

		lper.add(le);

		le = new ArrayList<PersonaReniec>(); // 3

		per = new PersonaReniec();
		per.setNombre("Jose");
		per.setApellidos("Calixto Aiquipa");
		per.setDni("10198266");
		per.setIdFirma("f025.jpg");

		le.add(per);

		per = new PersonaReniec();
		per.setNombre("Crisostomo");
		per.setApellidos("Santiago Monteagudo");
		per.setDni("69384231");
		per.setIdFirma("f043.jpg");

		le.add(per);

		lper.add(le);

		le = new ArrayList<PersonaReniec>(); // 4

		per = new PersonaReniec();
		per.setNombre("Armando");
		per.setApellidos("Pacheco Hijon");
		per.setDni("7968967");
		per.setIdFirma("f030.jpg");

		le.add(per);

		per = new PersonaReniec();
		per.setNombre("Juan Daniel");
		per.setApellidos("Codarlupo Gomez");
		per.setDni("8501853");
		per.setIdFirma("f029.jpg");

		le.add(per);

		lper.add(le);

		le = new ArrayList<PersonaReniec>(); // 5

		per = new PersonaReniec();
		per.setNombre("Rosaura Manuela");
		per.setApellidos("Carmeline Guerrero");
		per.setDni("10132273");
		per.setIdFirma("f058.jpg");

		le.add(per);

		per = new PersonaReniec();
		per.setNombre("Luis");
		per.setApellidos("Silvestre Garro");
		per.setDni("40896756");
		per.setIdFirma("f057.jpg");

		le.add(per);

		lper.add(le);

		le = new ArrayList<PersonaReniec>(); // 6

		per = new PersonaReniec();
		per.setNombre("Wally Esteban");
		per.setApellidos("Trinidad Collazos");
		per.setDni("55443322");
		per.setIdFirma("f041.jpg");

		le.add(per);

		per = new PersonaReniec();
		per.setNombre("Juana Josefina");
		per.setApellidos("Vargas Pelucho");
		per.setDni("78901234");
		per.setIdFirma("f042.jpg");

		le.add(per);

		lper.add(le);

		le = new ArrayList<PersonaReniec>(); // 7

		per = new PersonaReniec();
		per.setNombre("Hermes");
		per.setApellidos("Salas Rodriguez");
		per.setDni("43026536");
		per.setIdFirma("f049.jpg");

		le.add(per);

		per = new PersonaReniec();
		per.setNombre("Juana Josefina");
		per.setApellidos("Vargas Pelucho");
		per.setDni("78901234");
		per.setIdFirma("f042.jpg");

		le.add(per);

		lper.add(le);

		le = new ArrayList<PersonaReniec>(); // 8

		per = new PersonaReniec();
		per.setNombre("Wally Esteban");
		per.setApellidos("Trinidad Collazos");
		per.setDni("55443322");
		per.setIdFirma("f041.jpg");

		le.add(per);

		per = new PersonaReniec();
		per.setNombre("Luis");
		per.setApellidos("Silvestre Garro");
		per.setDni("40896756");
		per.setIdFirma("f057" + "" + "" + ".jpg");

		le.add(per);

		lper.add(le);
*/
		return lper;
	}



List<PersonaReniec> llenarDatosPruebaListaDeLista2() {

		//List<List<PersonaReniec>> lper = new ArrayList<List<PersonaReniec>>();

		ArrayList<PersonaReniec> le = new ArrayList<PersonaReniec>(); // 1

		PersonaReniec per = new PersonaReniec();
		per.setNombre("Paola");
		per.setApellidos("Zapata Garcia");
		per.setDni("8237514");
		per.setIdFirma("f002.jpg");

		le.add(per);


		per = new PersonaReniec();
		per.setNombre("Juan Carlos");
		per.setApellidos("Deza Porcel");
		per.setDni("8596098");
		per.setIdFirma("f018.jpg");

		le.add(per);



		per = new PersonaReniec();
		per.setNombre("Jose");
		per.setApellidos("Calixto Aiquipa");
		per.setDni("10198266");
		per.setIdFirma("f025.jpg");

		le.add(per);

		per = new PersonaReniec();
		per.setNombre("Crisostomo");
		per.setApellidos("Santiago Monteagudo");
		per.setDni("69384231");
		per.setIdFirma("f043.jpg");

		le.add(per);


		per = new PersonaReniec();
		per.setNombre("Juana Josefina");
		per.setApellidos("Vargas Pelucho");
		per.setDni("78901234");
		per.setIdFirma("f042.jpg");

		le.add(per);


		per = new PersonaReniec();
		per.setNombre("Hermes");
		per.setApellidos("Salas Rodriguez");
		per.setDni("43026536");
		per.setIdFirma("f049.jpg");

		le.add(per);


		per = new PersonaReniec();
		per.setNombre("Luis");
		per.setApellidos("Silvestre Garro");
		per.setDni("40896756");
		per.setIdFirma("f057" + "" + "" + ".jpg");

		le.add(per);
		
		per = new PersonaReniec();
		per.setNombre("Luis");
		per.setApellidos("Silvestre Garro");
		per.setDni("40896756");
		per.setIdFirma("f057" + "" + "" + ".jpg");

		le.add(per);

/*
		// Prueba con 16
		per = new PersonaReniec();
		per.setNombre("Paola");
		per.setApellidos("Zapata Garcia");
		per.setDni("8237514");
		per.setIdFirma("f002.jpg");

		le.add(per);

		per = new PersonaReniec();
		per.setNombre("Maria Elenea");
		per.setApellidos("Durand Novoa");
		per.setDni("6114817");
		per.setIdFirma("f003.jpg");

		le.add(per);
		lper.add(le);

		le = new ArrayList<PersonaReniec>(); // 2

		per = new PersonaReniec();
		per.setNombre("Juan Carlos");
		per.setApellidos("Deza Porcel");
		per.setDni("8596098");
		per.setIdFirma("f018.jpg");

		le.add(per);

		per = new PersonaReniec();
		per.setNombre("Ian Paul");
		per.setApellidos("Guzman Castañeda");
		per.setDni("46515871");
		per.setIdFirma("f019.jpg");

		le.add(per);

		lper.add(le);

		le = new ArrayList<PersonaReniec>(); // 3

		per = new PersonaReniec();
		per.setNombre("Jose");
		per.setApellidos("Calixto Aiquipa");
		per.setDni("10198266");
		per.setIdFirma("f025.jpg");

		le.add(per);

		per = new PersonaReniec();
		per.setNombre("Crisostomo");
		per.setApellidos("Santiago Monteagudo");
		per.setDni("69384231");
		per.setIdFirma("f043.jpg");

		le.add(per);

		lper.add(le);

		le = new ArrayList<PersonaReniec>(); // 4

		per = new PersonaReniec();
		per.setNombre("Armando");
		per.setApellidos("Pacheco Hijon");
		per.setDni("7968967");
		per.setIdFirma("f030.jpg");

		le.add(per);

		per = new PersonaReniec();
		per.setNombre("Juan Daniel");
		per.setApellidos("Codarlupo Gomez");
		per.setDni("8501853");
		per.setIdFirma("f029.jpg");

		le.add(per);

		lper.add(le);

		le = new ArrayList<PersonaReniec>(); // 5

		per = new PersonaReniec();
		per.setNombre("Rosaura Manuela");
		per.setApellidos("Carmeline Guerrero");
		per.setDni("10132273");
		per.setIdFirma("f058.jpg");

		le.add(per);

		per = new PersonaReniec();
		per.setNombre("Luis");
		per.setApellidos("Silvestre Garro");
		per.setDni("40896756");
		per.setIdFirma("f057.jpg");

		le.add(per);

		lper.add(le);

		le = new ArrayList<PersonaReniec>(); // 6

		per = new PersonaReniec();
		per.setNombre("Wally Esteban");
		per.setApellidos("Trinidad Collazos");
		per.setDni("55443322");
		per.setIdFirma("f041.jpg");

		le.add(per);

		per = new PersonaReniec();
		per.setNombre("Juana Josefina");
		per.setApellidos("Vargas Pelucho");
		per.setDni("78901234");
		per.setIdFirma("f042.jpg");

		le.add(per);

		lper.add(le);

		le = new ArrayList<PersonaReniec>(); // 7

		per = new PersonaReniec();
		per.setNombre("Hermes");
		per.setApellidos("Salas Rodriguez");
		per.setDni("43026536");
		per.setIdFirma("f049.jpg");

		le.add(per);

		per = new PersonaReniec();
		per.setNombre("Juana Josefina");
		per.setApellidos("Vargas Pelucho");
		per.setDni("78901234");
		per.setIdFirma("f042.jpg");

		le.add(per);

		lper.add(le);

		le = new ArrayList<PersonaReniec>(); // 8

		per = new PersonaReniec();
		per.setNombre("Wally Esteban");
		per.setApellidos("Trinidad Collazos");
		per.setDni("55443322");
		per.setIdFirma("f041.jpg");

		le.add(per);

		per = new PersonaReniec();
		per.setNombre("Luis");
		per.setApellidos("Silvestre Garro");
		per.setDni("40896756");
		per.setIdFirma("f057" + "" + "" + ".jpg");

		le.add(per);

		lper.add(le);
*/
		return le;
	}




List<PersonaReniec> procesar(List<List<PersonaReniec>> listaDeListasPersonasReniec,String urlPlanillonesOriginales,String urlBaseDeDatos) throws IOException
{	List<PersonaReniec> listaFinal;
    ArrayList<FirmaRecortada> liistaFirmas;    
    liistaFirmas = cortarFirmas(urlPlanillonesOriginales);  
    listaFinal = procesarFirmas(liistaFirmas,listaDeListasPersonasReniec,urlBaseDeDatos);    
    return listaFinal;
    
}







public static FirmaRecortada cortarFirma(String urlPlanillonesOriginales, int indice) {
try{
	int anchos = 0;
	int altos = 0;
	int indiceFinal = 0;
	FirmaRecortada listaFirmasCortadas = new FirmaRecortada();


		ArrayList<FirmaRecortada> listaTemporal = new ArrayList<FirmaRecortada>();
		//System.out.println("Planillon: " + urlPlanillonesOriginales);
		FastBitmap imagenPlanillon = new FastBitmap(urlPlanillonesOriginales );
		Crop cortadorImagenes;
		int factorPixel = 0;
		int multiplicarFactor = 0;
		
	
		
		if (imagenPlanillon.getWidth() < imagenPlanillon.getHeight()) {
			Rotate rotarImage = new Rotate(90.0,Rotate.Algorithm.BICUBIC);
		
			int despejarLineasNegras = 0;
			
			rotarImage.applyInPlace(imagenPlanillon);
			rotarImage.applyInPlace(imagenPlanillon);
			rotarImage.applyInPlace(imagenPlanillon);
		//	rotarImage.applyInPlace(imagenPlanillon);
	//		rotarImage.applyInPlace(imagenPlanillon);
		//	rotarImage.applyInPlace(imagenPlanillon);
			imagenPlanillon.toRGB();
			imagenPlanillon.toGrayscale();
			OtsuThreshold o = new OtsuThreshold();
			o.applyInPlace(imagenPlanillon);

			
			for (int j = imagenPlanillon.getHeight() - 1; j > 0; j--) {
				if (imagenPlanillon.getGray(j, imagenPlanillon.getWidth() / 2) == 255) {
					despejarLineasNegras = j;
					break;
				}
			}
			cortadorImagenes = new Crop(0, imagenPlanillon.getWidth() / 2,
			imagenPlanillon.getWidth() / 2 + imagenPlanillon.getWidth() / 3, despejarLineasNegras - 10);

		} else {//47
			cortadorImagenes = new Crop(0, imagenPlanillon.getWidth() / 2, (imagenPlanillon.getWidth() / 3)+36,
				imagenPlanillon.getHeight());
				imagenPlanillon.toRGB();
				imagenPlanillon.toGrayscale();
				OtsuThreshold o = new OtsuThreshold();
				o.applyInPlace(imagenPlanillon);
				
		
		}
		 
		
		//imagenPlanillon.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\333.jpg");
				
		cortadorImagenes.ApplyInPlace(imagenPlanillon);
		anchos = imagenPlanillon.getWidth() - 1;
		altos = imagenPlanillon.getHeight() - 1;
		//	imagenPlanillon.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\p1Cortado.jpg");

		//imagenPlanillon.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\333.jpg");
		
		
		
		//Factor Pixel
		if (altos >= 0 && altos <= 900)
			factorPixel = 5;
		else {
			multiplicarFactor = altos / 900;
			factorPixel = 5 * multiplicarFactor;
		}



		int mitadPlanillon = (imagenPlanillon.getWidth() / 2) + (factorPixel/2)+2;
		int indiceNegro = 0;
		int indiceBlanco = 0;
		int nuevoInicioNegro = 0;

		
		for (int i = 0; i < imagenPlanillon.getHeight()-1; i++) {
			if (imagenPlanillon.getGray(i, mitadPlanillon) == 255) {
				nuevoInicioNegro = i;
				break;
			}
			
		}
		
		//System.out.println("nuevo: "+nuevoInicioNegro);
		
		
		//Negro
		for (int j = nuevoInicioNegro; j < imagenPlanillon.getHeight() - 1; j++) {
			if (imagenPlanillon.getGray(j, mitadPlanillon) == 0) {
				indiceNegro = j;
				break;
			}
		}
		
		int veces2=0;
		for (int i = indiceNegro; i < imagenPlanillon.getHeight()-1; i++) {
			if (imagenPlanillon.getGray(i, mitadPlanillon) == 255) {
				//indiceNegro = i;
				break;
			}	
			veces2++;
		}
		

	//	System.out.println("Veces: "+veces2);
		//System.out.println("factor pixel: "+factorPixel/2 );
		indiceNegro = indiceNegro+(veces2/2);
		//Blanco
		for (int r = imagenPlanillon.getWidth() / 2; r < imagenPlanillon.getWidth() - 1; r++) {
			if (imagenPlanillon.getGray(indiceNegro, r) == 255) {
				indiceBlanco = r;
				break;
			}
		}

		//System.out.println("factor p: "+factorPixel);
	
		
		while(true){
			
		if(indiceBlanco==0) break;
		int cantidadBlancosDespues=0;
		int paso=0;
		int primerNegroEncontrado=indiceBlanco;
		int primeroEntrar = 0;
		//System.out.println("111");;
		for (int i = indiceBlanco; i < (indiceBlanco+factorPixel); i++) {
			//System.out.println("deberia ser 1 :"+imagenPlanillon.getGray(indiceNegro, i));
			if(imagenPlanillon.getGray(indiceNegro, i)==255)
			{	cantidadBlancosDespues++;
				
			}
			else
			{
				//System.out.println("prn :"+i);
				paso=1;
			}
			
			if(paso==1 && primeroEntrar==0){
				primeroEntrar=1;
				primerNegroEncontrado=i;
				
			}
			
			//else{
			//	paso=1;
			//	break;
			//	}
		}
		
		//System.out.println("222");
		//System.out.println("Cantidad blancos despues: "+cantidadBlancosDespues);
		//System.out.println("Indice blanco previo: "+indiceBlanco);
		//System.out.println("Primer negro: "+primerNegroEncontrado);
		//if(paso==0){
		//JOptionPane.showMessageDialog(null, imagenPlanillon.toIcon(), "Result , indice", JOptionPane.PLAIN_MESSAGE); 

		if (cantidadBlancosDespues<factorPixel){
			//Blanco
			for (int r = primerNegroEncontrado; r < imagenPlanillon.getWidth() - 1; r++) {
				if (imagenPlanillon.getGray(indiceNegro, r) == 255) {
					indiceBlanco = r;
					break;
				}
			}

		}
		//}
		else{
			indiceBlanco=primerNegroEncontrado;
			break;
		}
		
		
		
		}
		
		
		
		/*
		int veces3=0;
		for (int i = indiceBlanco; i < imagenPlanillon.getHeight()-1; i++) {
			if (imagenPlanillon.getGray(indiceBlanco,i) == 0) {
			//	indiceNegro = i;
				break;
			}	
			veces3++;
		}
		
		System.out.println("Veces 3: "+veces3);
		*/
		
		//System.out.println("Negro: "+indiceNegro);
		//System.out.println("Blanco: "+indiceBlanco);

		/*
		Firmas.FastBitmap dibujar = new Firmas.FastBitmap(urlPlanillonesOriginales);
		FastGraphics graficarPuntos2Base = new FastGraphics(dibujar);
		// graficarPuntos2Base.setColor(Color.Blue);
		graficarPuntos2Base.setColor(Color.Black);
		graficarPuntos2Base.DrawCircle(indiceNegro, indiceBlanco, 40);
		imagenPlanillon.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\p1Dibujados.jpg");
		//JOptionPane.showMessageDialog(null, dibujar.toIcon(), "Result con Puntos",JOptionPane.PLAIN_MESSAGE);
		*/
		
		
		int ind = imagenPlanillon.getWidth() - 1;
		ind = indiceBlanco+veces2;
	//	System.out.println("Veces 2:"+veces2);
		List<List<Resultado>> a3 = new ArrayList<List<Resultado>>();
		Resultado d;
		int cont = 0;
		int c = 0;
		int llego = 0;
		int dos = 0;

		int j;
		int tmp1 = 0;
		int tmp2 = 0;
		int nollego = 0;
		int pasoPrimerNegro = 0;
		
		if(ind==imagenPlanillon.getWidth())
			ind--;

		//Busca la primera linea negra
		//System.out.println("ind "+ind);
		

		while (true) {
			c = 0;
			llego = 0;
			nollego = 0;
			pasoPrimerNegro = 0;
			
			for (j = imagenPlanillon.getHeight() - 1; j > 0; j--) {
				if (imagenPlanillon.getGray(j, ind) == 0) {
					c++;
					pasoPrimerNegro = j;

				}
				if (c > (imagenPlanillon.getHeight() / 2)) {
					llego = 1;
					tmp1 = ind;
					break;
				}
			}
			if (llego == 1)
				break;
			ind--;
		}

		

		
		j = 0;
		llego = 0;
		//ind = ind - (veces2+factorPixel);
		ind = ind - 50;
		//System.out.println("factor "+(veces2+factorPixel));
		
		//Segunda line negra
		while (true) {
			c = 0;
			llego = 0;
			pasoPrimerNegro = 0;
			// for (j = inn+10; j > 0 ; j--) {
			for (j = imagenPlanillon.getHeight() - 1; j > 0; j--) {
	
				if (imagenPlanillon.getGray(j, ind) == 0) { 
					c++;
				}


				if (c > imagenPlanillon.getHeight() / 2) {
					llego = 1;
					tmp2 = ind;
					break;
				}
			}
	
			if (llego == 1)
				break;
			ind--;

		}

		
		int ultimoYLineaNegra1 = 0;
		int ultimoYLineaNegra2 = 0;

		
			for (int k = imagenPlanillon.getHeight() -(veces2+factorPixel); k > 0; k--) {
				if (imagenPlanillon.getGray(k, tmp1) == 0) {
					ultimoYLineaNegra1 = k;
					break;
				}

			}
		
		

	
			
		for (int k = imagenPlanillon.getHeight() - (veces2+factorPixel); k > 0; k--) {
			if (imagenPlanillon.getGray(k, tmp2) == 0) {
				ultimoYLineaNegra2 = k;
				break;
			}

		}
		
		

		//System.out.println("tmp1:"+tmp1);
		//System.out.println("tmp2:"+tmp2);
		int veces = 0;
		int ancho = tmp1 - tmp2;
		int contador = 0;
		int hizobreak = 0;
		int malbreak = 0;
		ArrayList<Integer> listaLineas = new ArrayList<Integer>();
		int band1 = 0;
	
		
		//System.out.println("ultimo y1 : "+ultimoYLineaNegra1);
		//System.out.println("ultimo y2 : "+ultimoYLineaNegra2);
		//System.out.println("Ancho lineas: "+ancho);
		
		//ultimoYLineaNegra2=1339;
		int contadorBlancos=0;
		for (int k = ultimoYLineaNegra2 - (factorPixel); k > 0; k--) {
			contador = 0;
			hizobreak = 0;
			malbreak = 0;
			
			if (veces == 8)
				break;
			contadorBlancos=0;
			for (int h = tmp2; h < tmp1; h++) {
				
				if (imagenPlanillon.getGray(k, h) == 0)
					contador++;
				else {
				  	contadorBlancos++;
				  //	System.out.println("contador blancos: "+contadorBlancos+" factor p: "+factorPixel);
				  //	System.out.println("h :"+h+" k:"+k);
				  	//JOptionPane.showMessageDialog(null, imagenPlanillon.toIcon(), "Result , indice", JOptionPane.PLAIN_MESSAGE); 
				  	if(contadorBlancos>factorPixel){
				  	//	System.out.println("entraste ??");
					malbreak = 1;
					break;
				  	}
				}
				if (contador >= ancho / 2) {
					hizobreak = 1;
					//JOptionPane.showMessageDialog(null, imagenPlanillon.toIcon(), "Result , indice", JOptionPane.PLAIN_MESSAGE); 
					break;
					
				}
				//JOptionPane.showMessageDialog(null, imagenPlanillon.toIcon(), "Result , indice", JOptionPane.PLAIN_MESSAGE); 
			}

			if (malbreak != 1) {
				k = k - (factorPixel);
				veces++;
				//System.out.println("Lineas: "+k+" factor: " +factorPixel);
				listaLineas.add(k);
			}
		}

		int alto;
		int k=0;
		//for (int k = 0; k < 8; k++) {
		if(indice==0)
			k=7;
		else if(indice==1)
			k=6;
		else if(indice==2)
			k=5;
		else if(indice==3)
			k=4;
		else if(indice==4)
			k=3;
		else if(indice==5)
			k=2;
		else if(indice==6)
			k=1;
		else if(indice==7)
			k=0;
		
		//System.out.println("ultima negra: "+ultimoYLineaNegra2);
		//System.out.println("linsta lenas k: "+listaLineas.get(k));
	  	//JOptionPane.showMessageDialog(null, imagenPlanillon.toIcon(), "Result , indice", JOptionPane.PLAIN_MESSAGE); 
	  	
		
		//int k=indice;
			//System.out.println("Linea actual ..........." + k);
			if (k == 0) {
				alto = ultimoYLineaNegra2 - listaLineas.get(k);
				cortadorImagenes = new Crop(listaLineas.get(k), tmp2, ancho, alto);
			} else if (k == 7) {
				alto = listaLineas.get(k - 1) - listaLineas.get(k);
				cortadorImagenes = new Crop(listaLineas.get(k), tmp2, ancho, alto);
			

			} else {
				alto = listaLineas.get(k) - listaLineas.get(k + 1);
				cortadorImagenes = new Crop(listaLineas.get(k), tmp2, ancho, alto);
			}
			FastBitmap i3 = new FastBitmap(imagenPlanillon.toBufferedImage());
			/*
			imagenPlanillon.saveAsJPG(
			"C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\Otras_Resoluciones\\objs\\planillon\\"
							+ i + "rr.jpg");
			FastBitmap i3 = new FastBitmap(
			"C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\Otras_Resoluciones\\objs\\planillon\\"
							+ i + "rr.jpg");
			*/
			cortadorImagenes.ApplyInPlace(i3);
			//JOptionPane.showMessageDialog(null, i3.toIcon(), "Result , indice", JOptionPane.PLAIN_MESSAGE); 

			FirmaRecortada fr = new FirmaRecortada();
			fr.img = i3.toBufferedImage();
			
			fr.ancho = anchos;
			fr.alto = altos;
			
			listaTemporal.add(fr);

	//	}


			listaFirmasCortadas = listaTemporal.get(0);


	

	return listaFirmasCortadas;
	}catch(Exception e){
		
		ArrayList<FirmaRecortada> listaTemporal = new ArrayList<FirmaRecortada>();
		//System.out.println("Planillon: " + urlPlanillonesOriginales);
		FastBitmap imagenPlanillon = new FastBitmap(urlPlanillonesOriginales );
		Crop cortadorImagenes;
	
		
		if (imagenPlanillon.getWidth() < imagenPlanillon.getHeight()) {
			Rotate rotarImage = new Rotate(90.0,Rotate.Algorithm.BICUBIC);
		
			int despejarLineasNegras = 0;
			
			rotarImage.applyInPlace(imagenPlanillon);
			rotarImage.applyInPlace(imagenPlanillon);
			rotarImage.applyInPlace(imagenPlanillon);
		//	rotarImage.applyInPlace(imagenPlanillon);
	//		rotarImage.applyInPlace(imagenPlanillon);
		//	rotarImage.applyInPlace(imagenPlanillon);
			imagenPlanillon.toRGB();
			imagenPlanillon.toGrayscale();
			OtsuThreshold o = new OtsuThreshold();
			o.applyInPlace(imagenPlanillon);

			
			for (int j = imagenPlanillon.getHeight() - 1; j > 0; j--) {
				if (imagenPlanillon.getGray(j, imagenPlanillon.getWidth() / 2) == 255) {
					despejarLineasNegras = j;
					break;
				}
			}
			cortadorImagenes = new Crop(0,0,250,150);

		} else {//47
			cortadorImagenes = new Crop(0,0,250,150);
				imagenPlanillon.toRGB();
				imagenPlanillon.toGrayscale();
				OtsuThreshold o = new OtsuThreshold();
				o.applyInPlace(imagenPlanillon);
				
		
		}
		 
		
		//imagenPlanillon.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\333.jpg");
				
		cortadorImagenes.ApplyInPlace(imagenPlanillon);
		//anchos = imagenPlanillon.getWidth() - 1;
		//altos = imagenPlanillon.getHeight() - 1;
		
		

		FirmaRecortada fr = new FirmaRecortada();
		fr.img = imagenPlanillon.toBufferedImage();
		listaTemporal.add(fr);
		return listaTemporal.get(0);
		



	}

}












public static FirmaRecortada cortarFirmaHuellas(String urlPlanillonesOriginales, int indice) {
try{
	int anchos = 0;
	int altos = 0;
	int indiceFinal = 0;
	FirmaRecortada listaFirmasCortadas = new FirmaRecortada();
	Crop cortadorImagenesHuellas;

		ArrayList<FirmaRecortada> listaTemporal = new ArrayList<FirmaRecortada>();
		//System.out.println("Planillon: " + urlPlanillonesOriginales);
		FastBitmap imagenPlanillon = new FastBitmap(urlPlanillonesOriginales );
		Crop cortadorImagenes;
		int factorPixel = 0;
		int multiplicarFactor = 0;
		
	
		
		if (imagenPlanillon.getWidth() < imagenPlanillon.getHeight()) {
			Rotate rotarImage = new Rotate(90.0,Rotate.Algorithm.BICUBIC);
		
			int despejarLineasNegras = 0;
			
			rotarImage.applyInPlace(imagenPlanillon);
			rotarImage.applyInPlace(imagenPlanillon);
			rotarImage.applyInPlace(imagenPlanillon);
		//	rotarImage.applyInPlace(imagenPlanillon);
	//		rotarImage.applyInPlace(imagenPlanillon);
		//	rotarImage.applyInPlace(imagenPlanillon);
			imagenPlanillon.toRGB();
			imagenPlanillon.toGrayscale();
			OtsuThreshold o = new OtsuThreshold();
			o.applyInPlace(imagenPlanillon);

			
			for (int j = imagenPlanillon.getHeight() - 1; j > 0; j--) {
				if (imagenPlanillon.getGray(j, imagenPlanillon.getWidth() / 2) == 255) {
					despejarLineasNegras = j;
					break;
				}
			}
			cortadorImagenes = new Crop(0, imagenPlanillon.getWidth() / 2,
			imagenPlanillon.getWidth() / 2 , despejarLineasNegras - 10);

		} else {//47
			
			cortadorImagenes = new Crop(0, imagenPlanillon.getWidth() / 2, (imagenPlanillon.getWidth() / 2),
				imagenPlanillon.getHeight());
				imagenPlanillon.toRGB();
				imagenPlanillon.toGrayscale();
				OtsuThreshold o = new OtsuThreshold();
				o.applyInPlace(imagenPlanillon);
//				cortadorImagenes = new Crop(0, imagenPlanillon.getWidth() / 2, (imagenPlanillon.getWidth() / 3)+36,
	//					imagenPlanillon.getHeight());

		
		}
		 
		
		
				
		cortadorImagenes.ApplyInPlace(imagenPlanillon);
		anchos = imagenPlanillon.getWidth() - 1;
		altos = imagenPlanillon.getHeight() - 1;
		//	imagenPlanillon.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\p1Cortado.jpg");

		//imagenPlanillon.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\333.jpg");
		
		//imagenPlanillon.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\333.jpg");
		
		//Factor Pixel
		if (altos >= 0 && altos <= 900)
			factorPixel = 5;
		else {
			multiplicarFactor = altos / 900;
			factorPixel = 5 * multiplicarFactor;
		}



		int mitadPlanillon = (imagenPlanillon.getWidth() / 2) + (factorPixel/2)+2;
		int indiceNegro = 0;
		int indiceBlanco = 0;
		int nuevoInicioNegro = 0;

		
		for (int i = 0; i < imagenPlanillon.getHeight()-1; i++) {
			if (imagenPlanillon.getGray(i, mitadPlanillon) == 255) {
				nuevoInicioNegro = i;
				break;
			}
			
		}
		
		//System.out.println("nuevo: "+nuevoInicioNegro);
		
		
		//Negro
		for (int j = nuevoInicioNegro; j < imagenPlanillon.getHeight() - 1; j++) {
			if (imagenPlanillon.getGray(j, mitadPlanillon) == 0) {
				indiceNegro = j;
				break;
			}
		}
		
		int veces2=0;
		for (int i = indiceNegro; i < imagenPlanillon.getHeight()-1; i++) {
			if (imagenPlanillon.getGray(i, mitadPlanillon) == 255) {
				//indiceNegro = i;
				break;
			}	
			veces2++;
		}
		

	//	System.out.println("Veces: "+veces2);
		//System.out.println("factor pixel: "+factorPixel/2 );
		indiceNegro = indiceNegro+(veces2/2);
		//Blanco
		for (int r = imagenPlanillon.getWidth() / 2; r < imagenPlanillon.getWidth() - 1; r++) {
			if (imagenPlanillon.getGray(indiceNegro, r) == 255) {
				indiceBlanco = r;
				break;
			}
		}

		//System.out.println("factor p: "+factorPixel);
	
		
		while(true){
			
		if(indiceBlanco==0) break;
		int cantidadBlancosDespues=0;
		int paso=0;
		int primerNegroEncontrado=indiceBlanco;
		int primeroEntrar = 0;
		int ultimoNegroArriba=0;
		//System.out.println("111");;
		for (int i = indiceBlanco; i < (indiceBlanco+factorPixel+6); i++) {
			//System.out.println("deberia ser 1 :"+imagenPlanillon.getGray(indiceNegro, i));
			if(imagenPlanillon.getGray(indiceNegro, i)==255)
			{	cantidadBlancosDespues++;
				
			}
			else
			{
				//System.out.println("prn :"+i);
				paso=1;
			}
			
			if(paso==1 && primeroEntrar==0){
				primeroEntrar=1;
				primerNegroEncontrado=i;
				
			}
			
			//else{
			//	paso=1;
			//	break;
			//	}
		}
		
		//System.out.println("222");
		//System.out.println("Cantidad blancos despues: "+cantidadBlancosDespues);
		//System.out.println("Indice blanco previo: "+indiceBlanco);
		//System.out.println("Primer negro: "+primerNegroEncontrado);
		//if(paso==0){
		//JOptionPane.showMessageDialog(null, imagenPlanillon.toIcon(), "Result , indice", JOptionPane.PLAIN_MESSAGE); 

		if (cantidadBlancosDespues<factorPixel+6){
			//Blanco
			for (int r = primerNegroEncontrado; r < imagenPlanillon.getWidth() - 1; r++) {
				if (imagenPlanillon.getGray(indiceNegro, r) == 255) {
					indiceBlanco = r;
					break;
				}
			}

		}
		//}
		else{
			for (int i = (indiceNegro-1); i > indiceNegro-(veces2); i--) {
				if (imagenPlanillon.getGray(i, primerNegroEncontrado) == 0)
				{	//System.out.println("i "+i+" veces:"+(veces2/2));
					ultimoNegroArriba=1;
					break;
				}
			}
			
			if(ultimoNegroArriba==0){
			indiceBlanco=primerNegroEncontrado;
			break;
			}
			else
			{
				indiceBlanco+=1;
			}
		}
		
		
		
		}
		
		
		
		/*
		int veces3=0;
		for (int i = indiceBlanco; i < imagenPlanillon.getHeight()-1; i++) {
			if (imagenPlanillon.getGray(indiceBlanco,i) == 0) {
			//	indiceNegro = i;
				break;
			}	
			veces3++;
		}
		
		System.out.println("Veces 3: "+veces3);
		*/
		
		//System.out.println("Negro: "+indiceNegro);
		//System.out.println("Blanco: "+indiceBlanco);

		/*
		Firmas.FastBitmap dibujar = new Firmas.FastBitmap(urlPlanillonesOriginales);
		FastGraphics graficarPuntos2Base = new FastGraphics(dibujar);
		// graficarPuntos2Base.setColor(Color.Blue);
		graficarPuntos2Base.setColor(Color.Black);
		graficarPuntos2Base.DrawCircle(indiceNegro, indiceBlanco, 40);
		imagenPlanillon.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\p1Dibujados.jpg");
		//JOptionPane.showMessageDialog(null, dibujar.toIcon(), "Result con Puntos",JOptionPane.PLAIN_MESSAGE);
		*/
		
		
		int ind = imagenPlanillon.getWidth() - 1;
		ind = indiceBlanco+veces2;
	//	System.out.println("Veces 2:"+veces2);
		List<List<Resultado>> a3 = new ArrayList<List<Resultado>>();
		Resultado d;
		int cont = 0;
		int c = 0;
		int llego = 0;
		int dos = 0;

		int j;
		int tmp1 = 0;
		int tmp2 = 0;
		int nollego = 0;
		int pasoPrimerNegro = 0;
		
		if(ind==imagenPlanillon.getWidth())
			ind--;

		//Busca la primera linea negra
		//System.out.println("ind "+ind);
		

		while (true) {
			c = 0;
			llego = 0;
			nollego = 0;
			pasoPrimerNegro = 0;
			
			for (j = imagenPlanillon.getHeight() - 1; j > 0; j--) {
				if (imagenPlanillon.getGray(j, ind) == 0) {
					c++;
					pasoPrimerNegro = j;

				}
				if (c > (imagenPlanillon.getHeight() / 2)) {
					llego = 1;
					tmp1 = ind;
					break;
				}
			}
			if (llego == 1)
				break;
			ind--;
		}

		

		
		j = 0;
		llego = 0;
		//ind = ind - (veces2+factorPixel);
		ind = ind - 50;
		//System.out.println("factor "+(veces2+factorPixel));
		
		//Segunda line negra
		while (true) {
			c = 0;
			llego = 0;
			pasoPrimerNegro = 0;
			// for (j = inn+10; j > 0 ; j--) {
			for (j = imagenPlanillon.getHeight() - 1; j > 0; j--) {
	
				if (imagenPlanillon.getGray(j, ind) == 0) { 
					c++;
				}


				if (c > imagenPlanillon.getHeight() / 2) {
					llego = 1;
					tmp2 = ind;
					break;
				}
			}
	
			if (llego == 1)
				break;
			ind--;

		}

		
		int ultimoYLineaNegra1 = 0;
		int ultimoYLineaNegra2 = 0;

		
			for (int k = imagenPlanillon.getHeight() -(veces2+factorPixel); k > 0; k--) {
				if (imagenPlanillon.getGray(k, tmp1) == 0) {
					ultimoYLineaNegra1 = k;
					break;
				}

			}
		
		

	
			
		for (int k = imagenPlanillon.getHeight() - (veces2+factorPixel); k > 0; k--) {
			if (imagenPlanillon.getGray(k, tmp2) == 0) {
				ultimoYLineaNegra2 = k;
				break;
			}

		}
		
		

		//System.out.println("tmp1:"+tmp1);
		//System.out.println("tmp2:"+tmp2);
		int veces = 0;
		int ancho = tmp1 - tmp2;
		int contador = 0;
		int hizobreak = 0;
		int malbreak = 0;
		ArrayList<Integer> listaLineas = new ArrayList<Integer>();
		int band1 = 0;
	
		
		//System.out.println("ultimo y1 : "+ultimoYLineaNegra1);
		//System.out.println("ultimo y2 : "+ultimoYLineaNegra2);
		//System.out.println("Ancho lineas: "+ancho);
		
		//ultimoYLineaNegra2=1339;
		int contadorBlancos=0;
		for (int k = ultimoYLineaNegra2 - (factorPixel+(veces2/2+1)); k > 0; k--) {
			contador = 0;
			hizobreak = 0;
			malbreak = 0;
			
			if (veces == 8)
				break;
			contadorBlancos=0;
			for (int h = tmp2; h < tmp1; h++) {
				
				if (imagenPlanillon.getGray(k, h) == 0)
					contador++;
				else {
				  	contadorBlancos++;
				  //	System.out.println("contador blancos: "+contadorBlancos+" factor p: "+factorPixel);
				  //	System.out.println("h :"+h+" k:"+k);
				  	//JOptionPane.showMessageDialog(null, imagenPlanillon.toIcon(), "Result , indice", JOptionPane.PLAIN_MESSAGE); 
				  	if(contadorBlancos>factorPixel){
				  	//	System.out.println("entraste ??");
					malbreak = 1;
					break;
				  	}
				}
				if (contador >= ancho / 2) {
					hizobreak = 1;
					//JOptionPane.showMessageDialog(null, imagenPlanillon.toIcon(), "Result , indice", JOptionPane.PLAIN_MESSAGE); 
					break;
					
				}
				//JOptionPane.showMessageDialog(null, imagenPlanillon.toIcon(), "Result , indice", JOptionPane.PLAIN_MESSAGE); 
			}

			if (malbreak != 1) {
				k = k - (factorPixel);
				veces++;
				//System.out.println("Lineas: "+k+" factor: " +factorPixel);
				listaLineas.add(k);
			}
		}

		
		
		

		FastBitmap imagenOriginal = new FastBitmap(urlPlanillonesOriginales );
		
		Crop cortadorImagenes2 = new Crop(0, imagenOriginal.getWidth() / 2, (imagenOriginal.getWidth() / 2),
		imagenOriginal.getHeight());
		cortadorImagenes2.ApplyInPlace(imagenOriginal);
		//imagenOriginal.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\333.jpg");
	
		
		
		
		
		
		
		
		int alto;
		int k=0;
		//for (int k = 0; k < 8; k++) {
		if(indice==0)
			k=7;
		else if(indice==1)
			k=6;
		else if(indice==2)
			k=5;
		else if(indice==3)
			k=4;
		else if(indice==4)
			k=3;
		else if(indice==5)
			k=2;
		else if(indice==6)
			k=1;
		else if(indice==7)
			k=0;
		
		//System.out.println("ultima negra: "+ultimoYLineaNegra2);
		//System.out.println("linsta lenas k: "+listaLineas.get(k));
	  	//JOptionPane.showMessageDialog(null, imagenPlanillon.toIcon(), "Result , indice", JOptionPane.PLAIN_MESSAGE); 
	  	
		
		//int k=indice;
			//System.out.println("Linea actual ..........." + k);
			if (k == 0) {
				alto = ultimoYLineaNegra2 - listaLineas.get(k);
				cortadorImagenes = new Crop(listaLineas.get(k), tmp2, ancho, alto);
				cortadorImagenesHuellas =new Crop(listaLineas.get(k), tmp2+(ancho), ancho, alto);
			} else if (k == 7) {
				alto = listaLineas.get(k - 1) - listaLineas.get(k);
				cortadorImagenes = new Crop(listaLineas.get(k), tmp2, ancho, alto);
				cortadorImagenesHuellas = new Crop(listaLineas.get(k), tmp2+(ancho), ancho, alto);

			} else {
				alto = listaLineas.get(k) - listaLineas.get(k + 1);
				cortadorImagenes = new Crop(listaLineas.get(k), tmp2, ancho, alto);
				cortadorImagenesHuellas = new Crop(listaLineas.get(k), tmp2+(ancho), ancho, alto);
			
			}
			FastBitmap i3 = new FastBitmap(imagenPlanillon.toBufferedImage());
			FastBitmap i4 = new FastBitmap(imagenOriginal.toBufferedImage());
			/*
			imagenPlanillon.saveAsJPG(
			"C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\Otras_Resoluciones\\objs\\planillon\\"
							+ i + "rr.jpg");
			FastBitmap i3 = new FastBitmap(
			"C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\Otras_Resoluciones\\objs\\planillon\\"
							+ i + "rr.jpg");
			*/
			cortadorImagenes.ApplyInPlace(i3);
			cortadorImagenesHuellas.ApplyInPlace(i4);

			//JOptionPane.showMessageDialog(null, i4.toIcon(), "Result , indice", JOptionPane.PLAIN_MESSAGE); 

		
			huella = i4.toBufferedImage();
			FirmaRecortada fr = new FirmaRecortada();
			fr.img = i3.toBufferedImage();
			
			fr.ancho = anchos;
			fr.alto = altos;
			
			listaTemporal.add(fr);

	//	}


			listaFirmasCortadas = listaTemporal.get(0);


	

	return listaFirmasCortadas;
	}catch(Exception e){
		
		ArrayList<FirmaRecortada> listaTemporal = new ArrayList<FirmaRecortada>();
		//System.out.println("Planillon: " + urlPlanillonesOriginales);
		FastBitmap imagenPlanillon = new FastBitmap(urlPlanillonesOriginales );
		Crop cortadorImagenes;
	
		
		if (imagenPlanillon.getWidth() < imagenPlanillon.getHeight()) {
			Rotate rotarImage = new Rotate(90.0,Rotate.Algorithm.BICUBIC);
		
			int despejarLineasNegras = 0;
			
			rotarImage.applyInPlace(imagenPlanillon);
			rotarImage.applyInPlace(imagenPlanillon);
			rotarImage.applyInPlace(imagenPlanillon);
		//	rotarImage.applyInPlace(imagenPlanillon);
	//		rotarImage.applyInPlace(imagenPlanillon);
		//	rotarImage.applyInPlace(imagenPlanillon);
			imagenPlanillon.toRGB();
			imagenPlanillon.toGrayscale();
			OtsuThreshold o = new OtsuThreshold();
			o.applyInPlace(imagenPlanillon);

			
			for (int j = imagenPlanillon.getHeight() - 1; j > 0; j--) {
				if (imagenPlanillon.getGray(j, imagenPlanillon.getWidth() / 2) == 255) {
					despejarLineasNegras = j;
					break;
				}
			}
			cortadorImagenes = new Crop(0,0,250,150);

		} else {//47
			cortadorImagenes = new Crop(0,0,250,150);
				imagenPlanillon.toRGB();
				imagenPlanillon.toGrayscale();
				OtsuThreshold o = new OtsuThreshold();
				o.applyInPlace(imagenPlanillon);
				
		
		}
		 
		
		//imagenPlanillon.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\333.jpg");
				
		cortadorImagenes.ApplyInPlace(imagenPlanillon);
		//anchos = imagenPlanillon.getWidth() - 1;
		//altos = imagenPlanillon.getHeight() - 1;
		
		

		FirmaRecortada fr = new FirmaRecortada();
		fr.img = imagenPlanillon.toBufferedImage();
		huella = imagenPlanillon.toBufferedImage();
		listaTemporal.add(fr);
		return listaTemporal.get(0);
		



	}

}















public static List<Double> procesarFirmasNuevo(FirmaRecortada listaFirmas, List<PersonaReniec> listaDeListaPersonas, String urlBaseDeDatos)
		throws IOException {
	
	Procesando.setearImagenFirma( listaFirmas.img );
	ProcesandoSeg.setearImagenFirma(listaFirmas.img);
	Procesando.setearImagenHuella(huella);
	ProcesandoSeg.setearImagenHuella(huella);
	
	
	double umbral = 5;
	int d1 = 0, d2 = 0;
	int porcentajeMinimo = 26; // 35 16
	Resize redimensionar = new Resize(260, 116);
	FastCornersDetector fast = new FastCornersDetector(); // Inicio del algoritmo Fast
	
	List<List<Resultado>> arre = new ArrayList<List<Resultado>>();
	ArrayList<Resultado> arr = null;
	arre = new ArrayList<List<Resultado>>();
	
	int distanciaMinima = 45; 
	int distanciaFiltro = 14;
	int valorEntrada=0;

	List<PersonaReniecPorcentaje> listaDeListasPersonasReniecPorcentaje = new ArrayList<PersonaReniecPorcentaje>();
	ArrayList<PersonaReniecPorcentaje> listaPorcentajesCandidato = null;

	
	// Por persona
	int indiceFirmas = 0;
	listaPorcentajesCandidato = new ArrayList<PersonaReniecPorcentaje>();

	for (int indicePersonaLista1 = 0; indicePersonaLista1 < listaDeListaPersonas.size(); indicePersonaLista1++) {
		
		float escalaActual = 20;

		FastBitmap imagen1;
		FastBitmap imagen2;
		FastRetinaKeypointDetector freak1 = new FastRetinaKeypointDetector(fast); // Incio del algoritmo Freak
		FastRetinaKeypointDetector freak2 = new FastRetinaKeypointDetector(fast);
		List<FastRetinaKeypoint> descriptores1;
		List<FastRetinaKeypoint> descriptores2;
		Distance distancia = new Distance();
		OtsuThreshold o = new OtsuThreshold();
		float sc=0;
		imagen1 = new FastBitmap(listaFirmas.img);
		int ancho = listaFirmas.ancho;
		int alto = listaFirmas.alto;
		descriptores1 = new ArrayList<FastRetinaKeypoint>();
		int entroNuevoSize=0;

		String url2 = urlBaseDeDatos;
		//url2 = url2 + "/" +listaDeListaPersonas.get(indicePersonaLista1).getIdFirma() +  ".png" ;
		String url2Png = url2 + "/" +listaDeListaPersonas.get(indicePersonaLista1).getIdFirma() +  ".png" ;
		String url2Jpg = url2 + "/" +listaDeListaPersonas.get(indicePersonaLista1).getIdFirma() +  ".jpg" ;
	    File ficheroUrl2Png = new File(url2Png);
	    File ficheroUrl2Jpg = new File(url2Jpg);

	    if (ficheroUrl2Png.exists())
	    {  url2 = url2Png;
	    }
	    else if (ficheroUrl2Jpg.exists())  
	    { url2 = url2Jpg;
	    }
		
	
			
		
		//url2 = url2 +".jpg" ;;
		//System.out.println(url2);
		//System.out.println("firma q abrira" + url2);
	   // System.out.println("URL" + url2);
		imagen2 = new FastBitmap(url2);
		//System.out.println("Alto i1 "+imagen1.getHeight()+" Ancho: "+imagen1.getWidth());
		//System.out.println("Alto i2 "+imagen2.getHeight()+" Ancho: "+imagen2.getWidth());
		/*
		if(imagen1.getHeight()>(imagen2.getHeight()+200) && imagen1.getWidth()>(imagen2.getWidth()+600))		
		{		BufferedImage img = imagen1.toBufferedImage(); 
				BufferedImage scaledImg = Scalr.resize(img, Method.AUTOMATIC, imagen2.getWidth(),
				imagen2.getHeight(), Scalr.OP_BRIGHTER);
				imagen1 = new FastBitmap(scaledImg);
				entroNuevoSize=1;
				//System.out.println("Nuevo size");
		}
		*/
		if(imagen1.getHeight()>(imagen2.getHeight()+200) && imagen1.getWidth()>(imagen2.getWidth()+550))		
		{		
				entroNuevoSize=1;
				//System.out.println("Nuevo size");
		}
		
		
		if(entroNuevoSize==0){
			if (alto <= 1300 && ancho <= 1300) {
				freak1.scale = escalaActual;
				valorEntrada=1;
				// freak1.scale=16;
				porcentajeMinimo = 17; //16
				distanciaMinima = 60;
				distanciaFiltro = 4;
				//System.out.println("1");
			} else if(alto >= 5000 && ancho >= 2000) {
				valorEntrada=2;
				freak1.scale = 23.0f;
				porcentajeMinimo = 42; //45
				distanciaMinima = 42;
				distanciaFiltro = 11;
				
			}
			
			
		/*	
		 * 	else if(alto >= 4000 && ancho >= 2000) {
				freak1.scale = 15.0f;
				porcentajeMinimo = 8;
				distanciaMinima = 30;
				distanciaFiltro = 25;
				System.out.println("2");
				
		}
		*/	
			else {
				valorEntrada=3;
				freak1.scale = 18.0f;
				porcentajeMinimo = 35; //26
			}
			}
			else
			{
				if(alto >= 4000 && ancho >= 2000) {
					//	freak1.scale = 15;
					//	porcentajeMinimo = 10;
					//	distanciaMinima = 57;
					//	distanciaFiltro = 3;
					//	System.out.println("2 nuevo");
						freak1.scale = (float) 17.4;
						valorEntrada=4;
						sc=(float) 17.4;
						porcentajeMinimo = 22; //26, 21
						distanciaFiltro = 17;
					
				}
				
			}
			//System.out.println("4"+" alto:"+alto+" ancho:"+ancho);
			
			
		
		descriptores1 = freak1.ProcessImage(imagen1);
		//JOptionPane.showMessageDialog(null, imagen1.toIcon(), "Result " + indiceFirmas + " ",
		//JOptionPane.PLAIN_MESSAGE);

		int indiceCandidatos;
		//System.out.println("Registro persona: " + indicePersonaLista1);

			arr = new ArrayList<Resultado>();	
			//url2 = url2 +".jpg" ;
			//DESCOMENTAR LUEGO
			
			
			//System.out.println("firma q abrira" + url2);

			BufferedImage img = imagen2.toBufferedImage(); 
			BufferedImage scaledImg = Scalr.resize(img, Method.AUTOMATIC, imagen1.getWidth(),
			imagen1.getHeight(), Scalr.OP_BRIGHTER);
			imagen2 = new FastBitmap(scaledImg);

				
			
			imagen2.toRGB();
			imagen2.toGrayscale();
			o.applyInPlace(imagen2);
			
			descriptores2 = new ArrayList<FastRetinaKeypoint>();
			
			if(entroNuevoSize==0){
				if (alto <= 1300 && ancho <= 1300) {
					freak2.scale = escalaActual;
					
				} else if(alto >= 5000 && ancho >= 2000) {
					freak2.scale = 23.0f;
					
				}
				/*
				else if(alto >= 4000 && ancho >= 2000) {
					freak2.scale = 15.0f;
					
				}
				*/
				else {
					freak2.scale = 18.0f;
				}
				}
				else
				{
					if(alto >= 4000 && ancho >= 2000) {
						freak2.scale = sc;
						//System.out.println("nuevo sise siese sc:"+sc);
						
					}
				}

			descriptores2 = freak2.ProcessImage(imagen2);
			int contadorMatching = 0;
			double distanciaResultado;
			double porcentaje = 0;
			Distance d = new Distance();
			int j;
			
			double distancia1; 
			for (int i = 0; i < descriptores1.size(); i++) {
				int indiceDistanciaMinima = -1;
				for (j = 0; j < descriptores2.size(); j++) {
					if (descriptores1.get(i).primerosBits(descriptores2.get(j)) >= distanciaFiltro) { 
						if (descriptores2.get(j).getIndexMatch() == -1) {
							distancia1 = d.Hamming(descriptores1.get(i).toBinary(),
									descriptores2.get(j).toBinary());
							if (distancia1 <= distanciaMinima) {
								indiceDistanciaMinima = j;
								descriptores2.get(indiceDistanciaMinima).setIndexMatch(i);
								break;
							}

						}
					}
				}
				descriptores1.get(i).setIndexMatch(indiceDistanciaMinima);
			}

			int c = 0;
			for (int i = 0; i < descriptores1.size(); i++) {
				if (descriptores1.get(i).getIndexMatch() != -1)
					c++;
			}
			int cor = 0;
			if (descriptores1.size() > 0) {
				porcentaje = ((c * 100) / descriptores1.size());
			}

			if (porcentaje >= porcentajeMinimo) { 
				//Porcentaje paso umbral
				Resultado r1 = new Resultado(indicePersonaLista1);
				r1.porcentaje = porcentaje;
				arr.add(r1);
				PersonaReniecPorcentaje personaReniecPorcentajeTemporal;
				personaReniecPorcentajeTemporal = new PersonaReniecPorcentaje();
				personaReniecPorcentajeTemporal.pe = listaDeListaPersonas.get(indicePersonaLista1);
				porcentaje = normalizarPorentajes(valorEntrada,porcentaje,porcentajeMinimo);
				personaReniecPorcentajeTemporal.porcentaje = porcentaje;
				personaReniecPorcentajeTemporal.match=true;
				listaPorcentajesCandidato.add(personaReniecPorcentajeTemporal);
				//System.out.println("id: "+indicePersonaLista1);
				//System.out.println("url: "+url2);
				//System.out.println("Porcentaje: "+porcentaje);
			
			} else {
				
				//Porcentaje 0
				PersonaReniecPorcentaje personaReniecPorcentajeTemporal;
				personaReniecPorcentajeTemporal = new PersonaReniecPorcentaje();
				personaReniecPorcentajeTemporal.pe = listaDeListaPersonas.get(indicePersonaLista1);
				porcentaje = normalizarPorentajes(valorEntrada,porcentaje,porcentajeMinimo);
				personaReniecPorcentajeTemporal.porcentaje = porcentaje;
				personaReniecPorcentajeTemporal.match=false;
				listaPorcentajesCandidato.add(personaReniecPorcentajeTemporal);
			}

			Procesando.escribirTextArea( "porcentaje de similitud en la firma : "+porcentaje + "%" ); 
			//Limpian sus descriptores de 1
			for (int i = 0; i < descriptores1.size(); i++) {
				if (descriptores1.get(i).getIndexMatch() != -1)
					descriptores1.get(i).setIndexMatch(-1);
			}

			// Fin for grupo de personas
		

		arre.add(arr);
	
		
		// Fin del for por persona
		indiceFirmas++;
	}

	
	

	
	ArrayList<PersonaReniec> lrestorno = new ArrayList<PersonaReniec>();
	double mayor = -9999;
	int indice1 = 0;
	int indice2 = 0;
	int nunca = 0;

	
	nunca = 0;
	ArrayList<Double> porcentajeFinales = new ArrayList<Double>();
	
	for (int i = 0; i < listaPorcentajesCandidato.size(); i++) {
		
		if(listaPorcentajesCandidato.get(i).match==true){
		
		lrestorno.add(listaPorcentajesCandidato.get(i).pe);
		//porcentajeFinales.add(listaPorcentajesCandidato.get(i).porcentaje);
		//System.out.println("Similar; indice: "+i+" Porcentaje:"+listaPorcentajesCandidato.get(i).porcentaje+" porcetaje lista:");
		}//else
			//System.out.println(" indice: "+i+" Porcentaje:"+listaPorcentajesCandidato.get(i).porcentaje);	
	
		porcentajeFinales.add(listaPorcentajesCandidato.get(i).porcentaje);
	}
	

	double p = 42;
	porcentajeFinales.add(p);
	return porcentajeFinales;
	
}




public static List<Double> procesarFirmasNuevoNuevo(FirmaRecortada listaFirmas, String urlBaseDeDatos)
		throws IOException {

	Procesando.setearImagenFirma( listaFirmas.img );
	double umbral = 5;
	int d1 = 0, d2 = 0;
	int porcentajeMinimo = 26; // 35 16
	Resize redimensionar = new Resize(260, 116);
	FastCornersDetector fast = new FastCornersDetector(); // Inicio del algoritmo Fast
	
	List<List<Resultado>> arre = new ArrayList<List<Resultado>>();
	ArrayList<Resultado> arr = null;
	arre = new ArrayList<List<Resultado>>();
	
	int distanciaMinima = 45; 
	int distanciaFiltro = 14;

	List<PersonaReniecPorcentaje> listaDeListasPersonasReniecPorcentaje = new ArrayList<PersonaReniecPorcentaje>();
	ArrayList<PersonaReniecPorcentaje> listaPorcentajesCandidato = null;

	
	// Por persona
	int indiceFirmas = 0;
	listaPorcentajesCandidato = new ArrayList<PersonaReniecPorcentaje>();

	for (int indicePersonaLista1 = 0; indicePersonaLista1 < 1; indicePersonaLista1++) {
		
		float escalaActual = 20;

		FastBitmap imagen1;
		FastBitmap imagen2;
		FastRetinaKeypointDetector freak1 = new FastRetinaKeypointDetector(fast); // Incio del algoritmo Freak
		FastRetinaKeypointDetector freak2 = new FastRetinaKeypointDetector(fast);
		List<FastRetinaKeypoint> descriptores1;
		List<FastRetinaKeypoint> descriptores2;
		Distance distancia = new Distance();
		OtsuThreshold o = new OtsuThreshold();
		float sc=0;
		imagen1 = new FastBitmap(listaFirmas.img);
		int ancho = listaFirmas.ancho;
		int alto = listaFirmas.alto;
		descriptores1 = new ArrayList<FastRetinaKeypoint>();
		int entroNuevoSize=0;
		//Direccion base de datos
		//String url2 = new String(urlBaseDeDatos+"\\"
		//+ listaDeListaPersonas.get(indicePersonaLista1).getIdFirma());
		String url2 = urlBaseDeDatos;
//		url2 = url2 +".jpg" ;
		//System.out.println("firma q abrira" + url2);
		imagen2 = new FastBitmap(url2);
		System.out.println("Alto i1 "+imagen1.getHeight()+" Ancho: "+imagen1.getWidth());
		System.out.println("Alto i2 "+imagen2.getHeight()+" Ancho: "+imagen2.getWidth());
		//if(imagen1.getHeight()>(imagen2.getHeight()+200) && imagen1.getWidth()>(imagen2.getWidth()+50))
		
		/*	
		if(imagen1.getHeight()>(imagen2.getHeight()+200) && imagen1.getWidth()>(imagen2.getWidth()+550))		
		{		BufferedImage img = imagen1.toBufferedImage(); 
				BufferedImage scaledImg = Scalr.resize(img, Method.AUTOMATIC, imagen2.getWidth(),
				imagen2.getHeight(), Scalr.OP_BRIGHTER);
				imagen1 = new FastBitmap(scaledImg);
				entroNuevoSize=1;
				System.out.println("Nuevo size");
		}
		*/
		
		if(imagen1.getHeight()>(imagen2.getHeight()+200) && imagen1.getWidth()>(imagen2.getWidth()+550))		
		{		
				entroNuevoSize=1;
				System.out.println("Nuevo size");
		}
		
		JOptionPane.showMessageDialog(null, imagen1.toIcon(), "Result " + indiceFirmas + " ",JOptionPane.PLAIN_MESSAGE);
		int valorEntrada=0;
		
		
		if(entroNuevoSize==0){
		if (alto <= 1300 && ancho <= 1300) {
			valorEntrada=1;
			freak1.scale = escalaActual;
			// freak1.scale=16;
			porcentajeMinimo = 16;
			distanciaMinima = 60;
			distanciaFiltro = 4;
			System.out.println("1");
		} else if(alto >= 5000 && ancho >= 2000) {
			valorEntrada=2;
			freak1.scale = 23.0f;
			porcentajeMinimo = 42; //45
			distanciaMinima = 42;
			distanciaFiltro = 11;
			System.out.println("2");
			
		}
		
		
	/*	
	 * 	else if(alto >= 4000 && ancho >= 2000) {
			freak1.scale = 15.0f;
			porcentajeMinimo = 8;
			distanciaMinima = 30;
			distanciaFiltro = 25;
			System.out.println("2");
			
	}
	*/	
		else {
			valorEntrada=3;
			freak1.scale = 18.0f;
			porcentajeMinimo = 34; //26
		}
		}
		else
		{
			if(alto >= 4000 && ancho >= 2000) {
			//	freak1.scale = 15;
			//	porcentajeMinimo = 10;
			//	distanciaMinima = 57;
			//	distanciaFiltro = 3;
			//	System.out.println("2 nuevo");
				freak1.scale = (float) 17.4;
				valorEntrada=4;
				sc=(float) 17.4;
				porcentajeMinimo = 22; //26, 21
				distanciaFiltro = 17;
			}
			
		}
		System.out.println("4"+" alto:"+alto+" ancho:"+ancho);
		
		descriptores1 = freak1.ProcessImage(imagen1);


		int indiceCandidatos;
		//System.out.println("Registro persona: " + indicePersonaLista1);

		
		
			arr = new ArrayList<Resultado>();
			
		

			
			//String cadena = new String(listaDeListaPersonas.get(indicePersonaLista1).getIdFirma());
			
			/*
			if (cadena.equals("f036.jpg") || cadena.equals("f037.jpg") || cadena.equals("f038.jpg")
					|| cadena.equals("f039.jpg") || cadena.equals("f040.jpg") || cadena.equals("f041.jpg")
					|| cadena.equals("f042.jpg") || cadena.equals("f043.jpg")) {
				BufferedImage bufferImageTemporal = null;
				if (ancho <= 1300 && alto <= 1300) {
					bufferImageTemporal = Thumbnails
							.of(new File(url2))
							.size(imagen1.getWidth() - 22, imagen1.getHeight() - 22).outputFormat("JPG")
							.outputQuality(1).resizer(Resizers.PROGRESSIVE).asBufferedImage();
				} else {
					bufferImageTemporal = Thumbnails
							.of(new File(url2))
							.size(imagen1.getWidth(), imagen1.getHeight()).outputFormat("JPG").outputQuality(1)
							.resizer(Resizers.PROGRESSIVE).asBufferedImage();
				}
				imagen2 = new FastBitmap(bufferImageTemporal);
				// JOptionPane.showMessageDialog(null, imagen2.toIcon(),
				// "Result "+isuperior+" ", JOptionPane.PLAIN_MESSAGE);
			}
			*/
			
	//		else {
			
			
		/*		BufferedImage img = imagen2.toBufferedImage(); 
				BufferedImage scaledImg = Scalr.resize(img, Method.AUTOMATIC, imagen1.getWidth(),
						imagen1.getHeight(), Scalr.OP_BRIGHTER);
			imagen2 = new FastBitmap(scaledImg);
			*/	
			
			
//			}


		//	if(entroNuevoSize==0){
				System.out.println("Antiguo size");
			BufferedImage img = imagen2.toBufferedImage(); 
			BufferedImage scaledImg = Scalr.resize(img, Method.AUTOMATIC, imagen1.getWidth(),
			imagen1.getHeight(), Scalr.OP_BRIGHTER);
			imagen2 = new FastBitmap(scaledImg);
	//		}
				
			imagen2.toRGB();
			imagen2.toGrayscale();
			o.applyInPlace(imagen2);
		
			JOptionPane.showMessageDialog(null, imagen2.toIcon(), "Result " + indiceFirmas + " ",
					JOptionPane.PLAIN_MESSAGE);
			
			descriptores2 = new ArrayList<FastRetinaKeypoint>();
		
			if(entroNuevoSize==0){
			if (alto <= 1300 && ancho <= 1300) {
				freak2.scale = escalaActual;
				
			} else if(alto >= 5000 && ancho >= 2000) {
				freak2.scale = 23.0f;
				
			}
			/*
			else if(alto >= 4000 && ancho >= 2000) {
				freak2.scale = 15.0f;
				
			}
			*/
			else {
				freak2.scale = 18.0f;
			}
			}
			else
			{
				if(alto >= 4000 && ancho >= 2000) {
					freak2.scale = sc;
					System.out.println("nuevo sise siese sc:"+sc);
					
				}
			}

			descriptores2 = freak2.ProcessImage(imagen2);
			int contadorMatching = 0;
			double distanciaResultado;
			double porcentaje = 0;
			Distance d = new Distance();
			int j;
			
			double distancia1; 
			for (int i = 0; i < descriptores1.size(); i++) {
				int indiceDistanciaMinima = -1;
				for (j = 0; j < descriptores2.size(); j++) {
					if (descriptores1.get(i).primerosBits(descriptores2.get(j)) >= distanciaFiltro) { 
						if (descriptores2.get(j).getIndexMatch() == -1) {
							distancia1 = d.Hamming(descriptores1.get(i).toBinary(),
									descriptores2.get(j).toBinary());
							if (distancia1 <= distanciaMinima) {
								indiceDistanciaMinima = j;
								descriptores2.get(indiceDistanciaMinima).setIndexMatch(i);
								break;
							}

						}
					}
				}
				descriptores1.get(i).setIndexMatch(indiceDistanciaMinima);
			}

			int c = 0;
			for (int i = 0; i < descriptores1.size(); i++) {
				if (descriptores1.get(i).getIndexMatch() != -1)
					c++;
			}
			int cor = 0;
			if (descriptores1.size() > 0) {
				porcentaje = ((c * 100) / descriptores1.size());
			}

			if (porcentaje >= porcentajeMinimo) { 
				//Porcentaje paso umbral
				Resultado r1 = new Resultado(indicePersonaLista1);
				r1.porcentaje = porcentaje;
				arr.add(r1);
				PersonaReniecPorcentaje personaReniecPorcentajeTemporal;
				personaReniecPorcentajeTemporal = new PersonaReniecPorcentaje();
				
				//personaReniecPorcentajeTemporal.pe = listaDeListaPersonas.get(indicePersonaLista1);
			//	porcentaje = normalizarPorentajes(valorEntrada,porcentaje,porcentajeMinimo);
				personaReniecPorcentajeTemporal.porcentaje = porcentaje;
				personaReniecPorcentajeTemporal.match=true;
				listaPorcentajesCandidato.add(personaReniecPorcentajeTemporal);
				System.out.println("id: Paso el umbral "+indicePersonaLista1);
				System.out.println("url: "+url2);
				System.out.println("Porcentaje: "+porcentaje);
			
			} else {
				
				//Porcentaje 0
				PersonaReniecPorcentaje personaReniecPorcentajeTemporal;
				personaReniecPorcentajeTemporal = new PersonaReniecPorcentaje();
				//personaReniecPorcentajeTemporal.pe = listaDeListaPersonas.get(indicePersonaLista1);
			//	porcentaje = normalizarPorentajes(valorEntrada,porcentaje,porcentajeMinimo);
				personaReniecPorcentajeTemporal.porcentaje = porcentaje;
				personaReniecPorcentajeTemporal.match=false;
				listaPorcentajesCandidato.add(personaReniecPorcentajeTemporal);
			}
			
			
			//aca va
	

			
			 
			
			Procesando.escribirTextArea( "porcentaje de similitud en la firma : "+porcentaje + "%" ); 
			//Limpian sus descriptores de 1
			for (int i = 0; i < descriptores1.size(); i++) {
				if (descriptores1.get(i).getIndexMatch() != -1)
					descriptores1.get(i).setIndexMatch(-1);
			}

			// Fin for grupo de personas
		

		arre.add(arr);
	
		
		// Fin del for por persona
		indiceFirmas++;
	}

	
	

	
	ArrayList<PersonaReniec> lrestorno = new ArrayList<PersonaReniec>();
	double mayor = -9999;
	int indice1 = 0;
	int indice2 = 0;
	int nunca = 0;

	
	nunca = 0;
	ArrayList<Double> porcentajeFinales = new ArrayList<Double>();
	
	for (int i = 0; i < listaPorcentajesCandidato.size(); i++) {
		
		if(listaPorcentajesCandidato.get(i).match==true){
		
		lrestorno.add(listaPorcentajesCandidato.get(i).pe);
		//porcentajeFinales.add(listaPorcentajesCandidato.get(i).porcentaje);
		System.out.println("Similar; indice: "+i+" Porcentaje:"+listaPorcentajesCandidato.get(i).porcentaje+" porcetaje lista:");
		}else
			System.out.println(" indice: "+i+" Porcentaje:"+listaPorcentajesCandidato.get(i).porcentaje);	
	
		porcentajeFinales.add(listaPorcentajesCandidato.get(i).porcentaje);
	}
	
	/*
	for (int i = 0; i < listaPorcentajesCandidato.size(); i++) {
		//System.out.println("Registro:  " + (i + 1));
		System.out.println("solo importo toyoyo  smeot eos "+i+" pORCENTAJE: "+listaPorcentajesCandidato.get(i).porcentaje);
	
		mayor = -9999;		
		
		if (listaPorcentajesCandidato.get(i).porcentaje > 0) {
				nunca = 1;
				if (mayor < listaDeListasPersonasReniecPorcentaje.get(i).porcentaje) {
					mayor = listaDeListasPersonasReniecPorcentaje.get(i).porcentaje;
					indice1 = i;
				
				}
			}
		else{
			PersonaReniec persona;
			persona = new PersonaReniec();
			persona.setDni("00000000");
			lrestorno.add(persona);
		}

		System.out.println("Fin: "+i);
	}
	
	
	if(nunca==0){
		PersonaReniec persona;
		persona = new PersonaReniec();
		persona.setDni("00000000");
		lrestorno.add(persona);
	}else
		lrestorno.add(listaDeListasPersonasReniecPorcentaje.get(indice1).pe);		
	System.out.println("kkkkkkkkkkkkkkkkkkkk");
*/
	
	
//	return lrestorno;
double p = porcentajeMinimo;
//	double p = 42;
	porcentajeFinales.add(p);
	return porcentajeFinales;
	
}



static double normalizarPorentajes(int valorEntrada,double porcentaje,double porM)
{
   
		if(valorEntrada==1 || valorEntrada==4 || valorEntrada==3){
			
			if(porM!=42){
			
			if(porcentaje>0 && porcentaje<(100-42))
			{
			if(porcentaje>porM)
			porcentaje =42+(porcentaje-porM); 
			else if(porcentaje<porM)
				porcentaje =42-(porM-porcentaje); 			
			else if(porcentaje==porM)
			porcentaje=42;
			}
			else if(porcentaje>=(100-42))
			{
				//cualq
				if(valorEntrada==1){
				if(porcentaje>=58 && porcentaje<74)
					porcentaje=porcentaje+25;
				else if(porcentaje>=74)
					porcentaje=99;
				}
				else if(valorEntrada==3)
				{
					if(porcentaje>=58 && porcentaje<93)
						porcentaje=porcentaje+7;
					else if(porcentaje>=93)
						porcentaje=99;
					
				}
				else if(valorEntrada==4)
				{
					if(porcentaje>=58 && porcentaje<79)
						porcentaje=porcentaje+20;
					else if(porcentaje>=79)
						porcentaje=99;
					
				}
				
	 
				
			}
			else if(porcentaje<=0)
			{
				porcentaje=0;
			}
			
		}
  }
    
    //System.out.println("por: "+porcentaje);
    return porcentaje;
}





 public static List<Double> procesarNuevo(List<PersonaReniec> listaDeListasPersonasReniec,int indice,String urlPlanillonesOriginales,String urlBaseDeDatos) throws IOException
{	List<PersonaReniec> listaFinal = null;
    FirmaRecortada liistaFirmas;    
    List<Double> resultados;
    //liistaFirmas = cortarFirma(urlPlanillonesOriginales,indice); 
    liistaFirmas = cortarFirmaHuellas(urlPlanillonesOriginales,indice); 
    //JOptionPane.showMessageDialog(null, liistaFirmas.toIcon(), "Result ", JOptionPane.PLAIN_MESSAGE);
    //listaFinal = procesarFirmasNuevo(liistaFirmas,listaDeListasPersonasReniec,urlBaseDeDatos);       
    resultados = procesarFirmasNuevo(liistaFirmas,listaDeListasPersonasReniec,urlBaseDeDatos);          
    //return listaFinal;
    return resultados;
}

    
    
	
}

