package fingerprint;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Minutiae {

	static int closerPoints = 5;
	static double angleRange = 3.5;
	static double relativeDistance = 0.02;
	
	public static void crossingNumber(final int[][] givenImage,
			List<Point> minutiaeFound) throws FileNotFoundException,
			UnsupportedEncodingException {

		/*
		 * PrintWriter writer = new PrintWriter("bandolero.txt", "UTF-8");
		 * 
		 * for (int y = 0; y < givenImage.length; y++) { for (int x = 0; x <
		 * givenImage[y].length; x++) { writer.print(givenImage[y][x]); }
		 * writer.println("\n"); } writer.close();
		 */
		int CN = 0;
		int[][] newMatrix = new int[givenImage.length][givenImage[0].length];
		for (int y = 0; y < givenImage.length; y++) {
			for (int x = 0; x < givenImage[y].length; x++) {

				if (givenImage[y][x] == 1) {
					CN = rutovitz(y, x, givenImage);
					if (CN == 1 || CN == 3)
						newMatrix[y][x] = CN;
				} else
					newMatrix[y][x] = 0;

				if (newMatrix[y][x] != 0 && (CN == 1 || CN == 3)) {

					Point p = new Point(x, y);
					minutiaeFound.add(p);
					// if (CN == 1) minutiaeT.add(p);
					// if (CN == 3) minutiaeB.add(p);

				}
			}
		}

		PrintWriter writer = new PrintWriter("bandolero.txt", "UTF-8");

		for (int y = 0; y < newMatrix.length; y++) {
			for (int x = 0; x < newMatrix[y].length; x++) {
				writer.print(newMatrix[y][x]);
			}
			writer.println("\n");
		}
		writer.close();

	}

	public static void removeFalseMinutiae(List<Point> minutiae) {

		int average = 0;
		float distance = 0;
		Point p1, p2;

		for (int i = 0; i < minutiae.size(); i++) {
			p1 = minutiae.get(i);
			for (int j = i + 1; j < minutiae.size(); j++) {
				p2 = minutiae.get(j);
				// getting the distance between the points
				distance = (float) Math.sqrt((p1.getX() - p2.getX())
						* (p1.getX() - p2.getX()) + (p1.getY() - p2.getY())
						* (p1.getY() - p2.getY()));
				if (distance < 6) {
					minutiae.remove(i);
					i = i - 1;
					break;
				}
			}
		}
	}

	public static void removeBorder(int[][] imageData, List<Point> minutiae) {

	}

	public static double AverageSameMinutiae(List<Point> minutiae) {

		int average = 0, cont = 0;
		float distance = 0, a = 0;
		Point p1, p2;

		for (int i = 0; i < minutiae.size(); i++) {
			p1 = minutiae.get(i);
			for (int j = i + 1; j < minutiae.size(); j++) {
				p2 = minutiae.get(j);
				// getting the distance between the points
				distance = a
						+ (float) Math.sqrt((p1.getX() - p2.getX())
								* (p1.getX() - p2.getX())
								+ (p1.getY() - p2.getY())
								* (p1.getY() - p2.getY()));
				cont++;
				System.out.println(a);

			}
		}

		System.out.println("DISTANCE" + distance);
		System.out.println("CONT" + cont);
		return distance / cont;

	}

	public static int rutovitz(int y, int x, int[][] givenImage) {

		int acum = 0;
		int valor1 = 0, valor2 = 0, valor3 = 0, valor4 = 0, valor5 = 0, valor6 = 0, valor7 = 0, valor8 = 0, valor9 = 0;
		/*
		
		if (x == 149) {
			valor7 = 0;
			valor8 = 0;
		} else {
			valor1 = givenImage[y][x + 1];
			valor9 = givenImage[y][x + 1];
		}

		if (y == 76) {
			valor7 = 0;
			valor8 = 0;
		} else if(x == 149) {
			valor7 = givenImage[y + 1][x];
			valor8 = 0;
		} else {
			valor7 = givenImage[y + 1][x];
			valor8 = givenImage[y + 1][x + 1];
		}

		if (x == 0 || (y == 0 || y == 76)) {
			if (x == 0 && (y == 0 || y == 76))
				valor4 = 0;
			else if (x == 0) {
				valor5 = 0;
				valor6 = 0;
			} else if (y == 0 || y == 76) {
				valor2 = 0;
				valor3 = 0;
			} else {
				valor2 = givenImage[y - 1][x + 1];
				valor3 = givenImage[y - 1][x];
				valor4 = givenImage[y - 1][x - 1];
				valor5 = givenImage[y][x - 1];
				valor6 = givenImage[y + 1][x - 1];
			}

		}
		
		
	
		
		if (y < (givenImage.length ) )
			if ( (x + 1) < (givenImage[y].length))  valor1 = givenImage[y][x + 1]; 
			else valor1 = 0;
		else  valor1 = 0;
		
		if ( (y -1)  < (givenImage.length ) )
			if ( (x + 1) < (givenImage[y-1].length))  valor2 =  givenImage[y - 1][x + 1];
			else valor2 = 0;
		else  valor2 = 0;
		
		if ( (y - 1)   < (givenImage.length ) )
			if ( (x ) < (givenImage[y-1].length))  valor3 =  givenImage[y - 1][x];
			else valor3 = 0;
		else  valor3 = 0;
		
		
		if ( (y - 1)   < (givenImage.length ) )
			if ( (x-1) < (givenImage[y-1].length))  valor4 =  givenImage[y - 1][x-1];
			else valor4 = 0;
		else  valor4 = 0;
		
		if ( (y )   < (givenImage.length ) )
			if ( (x-1) < (givenImage[y].length))  valor5 =  givenImage[y][x-1];
			else valor5 = 0;
		else  valor5 = 0;
		
		if ( (y + 1 )   < (givenImage.length ) )
			if ( (x-1) < (givenImage[y+1].length))  valor6 =  givenImage[y+1][x-1];
			else valor6 = 0;
		else  valor6 = 0;

		
		if ( (y + 1 )   < (givenImage.length ) )
			if ( (x) < (givenImage[y+1].length))  valor7 =  givenImage[y+1][x];
			else valor7 = 0;
		else  valor7 = 0;
		
		if ( (y + 1 )   < (givenImage.length ) )
			if ( (x + 1 ) < (givenImage[y+1].length))  valor8 =  givenImage[y+1][x + 1];
			else valor8 = 0;
		else  valor8 = 0;
		
		if ( ( y )   < (givenImage.length ) )
			if ( (x + 1 ) < (givenImage[y].length))  valor9 =  givenImage[y][x + 1];
			else valor9 = 0;
		else  valor9 = 0;
		
		*/
		
		 try {
			 valor1 = givenImage[y][x + 1]; 
		 } catch (ArrayIndexOutOfBoundsException e) {
		     valor1 = 0;
		 }
		 try {
			 valor2 = givenImage[y - 1][x + 1];
		 } catch (ArrayIndexOutOfBoundsException e) {
		     valor2 = 0;
		 }
		 try {
			 valor3 = givenImage[y - 1][x];
		 } catch (ArrayIndexOutOfBoundsException e) {
		     valor3 = 0;
		 }
		 try {
			 valor4 = givenImage[y - 1][x - 1];
		 } catch (ArrayIndexOutOfBoundsException e) {
		     valor4 = 0;
		 }
		 try {
			 valor5 =  givenImage[y][x - 1]; 
		 } catch (ArrayIndexOutOfBoundsException e) {
		     valor5 = 0;
		 }
		 try {
			 valor6 =  givenImage[y + 1][x - 1];
		 } catch (ArrayIndexOutOfBoundsException e) {
		     valor6 = 0;
		 }
		 try {
			 valor7 = givenImage[y][x + 1]; 
		 } catch (ArrayIndexOutOfBoundsException e) {
		     valor7 = 0;
		 }
		 
		 try {
			 valor8 =  givenImage[y + 1][x + 1];
		 } catch (ArrayIndexOutOfBoundsException e) {
		     valor8 = 0;
		 }
		 try {
			 valor9 = givenImage[y][x + 1];
		 } catch (ArrayIndexOutOfBoundsException e) {
		     valor9 = 0;
		 }
		 

		 

		  
		 
		acum = acum + Math.abs(valor1 - valor2);
		acum = acum + Math.abs(valor2 - valor3);
		acum = acum + Math.abs(valor3 - valor4);
		acum = acum + Math.abs(valor4 - valor5);
		acum = acum + Math.abs(valor5 - valor6);
		acum = acum + Math.abs(valor6 - valor7);
		acum = acum + Math.abs(valor7 - valor8);
		acum = acum + Math.abs(valor8 - valor9);
		acum = (int) (acum * 0.5);

		return acum;

	}

	public static List<MinutiaeTuples> getTuplesFeatures(
			List<Point> minutiaeFound) {
		List<MinutiaeTuples> lista = new ArrayList<MinutiaeTuples>();

		for (int i = 0; i < minutiaeFound.size(); i++) {
			List<Double> bestDistances = new ArrayList<Double>();
			List<Integer> bestIndexes = new ArrayList<Integer>();
			// saca solo las distancias con todos los demás puntos
			for (int j = 0; j < minutiaeFound.size(); j++) {
				if (i != j) {
					int dif = minutiaeFound.get(i).getX()
							- minutiaeFound.get(j).getX();
					int valX = dif * dif;
					dif = minutiaeFound.get(i).getY()
							- minutiaeFound.get(j).getY();
					int valY = dif * dif;
					double distance = Math.sqrt(valX + valY);
					bestDistances.add(distance);
					bestIndexes.add(j);
				}
			}

			ordenar(bestDistances, bestIndexes);

			// de los 5 primeros, sacar ratios y angulos

			MinutiaeTuples mTuple = new MinutiaeTuples();
			for (int j = 0; j < closerPoints - 1; j++) {
				for (int k = j + 1; k < closerPoints; k++) {
					double ab = bestDistances.get(j); // center -> currently
					double ac = bestDistances.get(k); // center -> previous
					Tupla t = new Tupla();

					BigDecimal bd = new BigDecimal(Double.toString(ac / ab));
					bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
					t.setRatio(bd.doubleValue());

					// Math.toDegrees(Math.atan2(current.x - center.x,current.y
					// - center.y)-
					// Math.atan2(previous.x- center.x,previous.y- center.y));

					int curCenX = minutiaeFound.get(bestIndexes.get(j)).getX()
							- minutiaeFound.get(i).getX();
					int curCenY = minutiaeFound.get(bestIndexes.get(j)).getY()
							- minutiaeFound.get(i).getY();
					int preCenX = minutiaeFound.get(bestIndexes.get(k)).getX()
							- minutiaeFound.get(i).getX();
					int preCenY = minutiaeFound.get(bestIndexes.get(k)).getY()
							- minutiaeFound.get(i).getY();

					double angle = Math.abs(Math.toDegrees(Math.atan2(curCenX,
							curCenY) - Math.atan2(preCenX, preCenY)));
					bd = new BigDecimal(Double.toString(angle));
					bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);
					t.setAngle(bd.doubleValue());
					// Como las huellas se obtienen del mismo dispositivo,
					// tendrpa +- 1.5 de ángulo de diferencia

					mTuple.add(t);
				}
			}
			lista.add(mTuple);
		}

		return lista;
	}

	private static void ordenar(List<Double> bestDistances,
			List<Integer> bestIndexes) {
		for (int i = 0; i < bestDistances.size() - 1; i++)
			for (int j = i + 1; j < bestDistances.size(); j++)
				if (bestDistances.get(i) > bestDistances.get(j)) {
					double aux = bestDistances.get(i);
					bestDistances.set(i, bestDistances.get(j));
					bestDistances.set(j, aux);

					int auxIndex = bestIndexes.get(i);
					bestIndexes.set(i, bestIndexes.get(j));
					bestIndexes.set(j, auxIndex);
				}

	}

	private static void orderList(List<Integer> possibleCandidate) {
		for (int i = 0; i < possibleCandidate.size() - 1; i++)
			for (int j = i + 1; j < possibleCandidate.size(); j++)
				if (possibleCandidate.get(i) > possibleCandidate.get(j)) {
					int auxIndex = possibleCandidate.get(i);
					possibleCandidate.set(i, possibleCandidate.get(j));
					possibleCandidate.set(j, auxIndex);
				}

	}

	public static int matchingMinutiae(List<MinutiaeTuples> mTuplesIM,
			List<MinutiaeTuples> mTuplesBM) {

		List<MinutiaeTuples> mTuplesBMAux = mTuplesBM;
		int totalRelations = 0;

		if (mTuplesBM.size() > mTuplesIM.size()) {

			for (int i = 0; i < mTuplesIM.size(); i++) {
				// int cantRepitencia=0;
				int maxPairs = 0;
				int valJ = -1;

				for (int j = 0; j < mTuplesBMAux.size(); j++) {
					List<Tupla> tIM = mTuplesIM.get(i).getFeatures();
					List<Tupla> tBM = mTuplesBMAux.get(j).getFeatures();

					int val = analizePairListTuple(tIM, tBM);

					if (val > 2) {

						totalRelations = totalRelations + 1;
						// mTuplesBMAux.remove(j);
						break;
						// si ambos puntos tienen como mínimo 2 tuplas iguales,
						// se
						// añaden a la lista de posibles candidatos
						// maxPairs = val;
						// valJ = j;
						// cantRepitencia++;

						/*
						 * if(cantRepitencia==1) possibleCandidateIM.add(i);
						 * //añade el punto i una sola vez
						 * 
						 * if(i==0) possibleCandidateBM.add(j);//añade el indice
						 * else{ //buscar, si esta = pasa, si no esta = añade
						 * boolean found=false; for (int
						 * k=0;k<possibleCandidateBM.size();k++)
						 * if(possibleCandidateBM.get(k)==j) found=true;
						 * if(!found) possibleCandidateBM.add(j); }
						 */
					}
				}
				// if (maxPairs != 0) {
				// possibleCandidateIM.add(i);
				// possibleCandidateBM.add(valJ);
				// mTuplesBMAux.remove(valJ);
				// }
			}

		} else {
			for (int i = 0; i < mTuplesBMAux.size(); i++) {
				// int cantRepitencia=0;
				int maxPairs = 0;
				int valJ = -1;
				
				
				for (int j = 0; j < mTuplesIM.size(); j++) {
					List<Tupla> tIM = mTuplesIM.get(j).getFeatures();
					List<Tupla> tBM = mTuplesBMAux.get(i).getFeatures();

					int val = analizePairListTuple(tIM, tBM);

					if (val > 1) {

						totalRelations = totalRelations + 1;
						// mTuplesBMAux.remove(j);
						break;
						
					}
				}
				
			}
		}
		return totalRelations;
		/*
		 * orderList(possibleCandidateIM); orderList(possibleCandidateBM);
		 * 
		 * // Se separa los indices de los candidatos y de los excluidos!
		 * List<Integer> indexListIM = new ArrayList<Integer>(); for (int i = 0;
		 * i < minutiaeFound; i++) indexListIM.add(i); for (int i = 0; i <
		 * possibleCandidateIM.size(); i++)
		 * indexListIM.remove(possibleCandidateIM.get(i) - i);
		 * 
		 * excludedMinutiaeIM = indexListIM;
		 * 
		 * List<Integer> indexListBM = new ArrayList<Integer>(); for (int i = 0;
		 * i < minutiaeFound; i++) indexListBM.add(i); for (int i = 0; i <
		 * possibleCandidateBM.size(); i++)
		 * indexListBM.remove(possibleCandidateBM.get(i) - i);
		 * 
		 * excludedMinutiaeBM = indexListBM;
		 */
	}

	private static int analizePairListTuple(List<Tupla> tIM, List<Tupla> tBM) {
		int cant = 0;
		boolean doubt;
		for (int i = 0; i < tIM.size(); i++) {
			doubt = false;// si al menos 2 tuplas son iguales
			for (int j = 0; j < tBM.size(); j++) {

				Tupla im = tIM.get(i);
				Tupla bm = tBM.get(j);
				
				if((Math.abs(im.getRatio()-bm.getRatio())<relativeDistance)
				//if ((im.getRatio() == bm.getRatio())
						&& (im.getAngle() > (bm.getAngle() - angleRange) && // el
																			// rango
																			// aceptable
																			// en
																			// el
																			// que
																			// es
																			// posible
																			// aceptar
						(im.getAngle() < (bm.getAngle() + angleRange)))) {// como
																			// un
																			// candidato
					doubt = true;
					break;
				}
			}
			if (doubt)
				cant++;
			// if(cant>=2) return true;
		}
		if (cant >= 2)
			return cant;
		else
			return 0;
	}

	public static void orderTreeStructure(List<Point> minutiaeFound,
			List<Integer> possibleCandidate) {

		for (int i = 0; i < possibleCandidate.size() - 1; i++)
			for (int j = i + 1; j < possibleCandidate.size(); j++) {
				int valIY = minutiaeFound.get(possibleCandidate.get(i)).getY();
				int valJY = minutiaeFound.get(possibleCandidate.get(j)).getY();
				int valIX = minutiaeFound.get(possibleCandidate.get(i)).getX();
				int valJX = minutiaeFound.get(possibleCandidate.get(j)).getX();
				// si son iguales sus Y, ordenar por sus X
				if (valIY == valJY)
					if (valIX > valJX) {
						int auxPoint = possibleCandidate.get(i);
						possibleCandidate.set(i, possibleCandidate.get(j));
						possibleCandidate.set(j, auxPoint);
					}
				if (valIY < valJY) {
					int auxPoint = possibleCandidate.get(i);
					possibleCandidate.set(i, possibleCandidate.get(j));
					possibleCandidate.set(j, auxPoint);
				}
			}
	}

	public static void comparisonAtTreeStructure(List<Point> minutiaeFound,
			List<Integer> possibleCandidateIM,
			List<Integer> possibleCandidateBM,
			List<Integer> excludedMinutiaeIM, List<Integer> excludedMinutiaeBM) {

		List<Integer> possibleCandidateIMAux = possibleCandidateIM;
		List<Integer> possibleCandidateBMAux = possibleCandidateBM;

		if (possibleCandidateIM.size() > 2 && possibleCandidateBM.size() > 2) {
			int tam = possibleCandidateIM.size();
			for (int i = 1; i < tam - 1; i++) {
				// Que halla un siguiente punto
				if ((i + 1) < possibleCandidateIMAux.size()
						&& (i + 1) < possibleCandidateBMAux.size()) {
					int cuadranteIM = 0, cuadranteBM = 0;
					double ratioIM = 0, ratioBM = 0, angleIM = 0, angleBM = 0;

					sacarCaracteristicas(
							minutiaeFound
									.get(possibleCandidateIMAux.get(i - 1)),
							minutiaeFound.get(possibleCandidateIMAux.get(i)),
							minutiaeFound.get(possibleCandidateIMAux.get(i + 1)),
							cuadranteIM, ratioIM, angleIM);
					sacarCaracteristicas(
							minutiaeFound
									.get(possibleCandidateBMAux.get(i - 1)),
							minutiaeFound.get(possibleCandidateBMAux.get(i)),
							minutiaeFound.get(possibleCandidateBMAux.get(i + 1)),
							cuadranteBM, ratioBM, angleBM);
					if (cuadranteIM == cuadranteBM && ratioIM == ratioBM
							&& (angleIM > angleBM - angleRange)
							&& (angleIM < angleBM + angleRange))
						continue;
					else {
						int cuadranteIMAux = 0, cuadranteBMAux = 0;
						double ratioIMAux = 0, angleIMAux = 0, ratioBMAux = 0, angleBMAux = 0;

						if ((i + 2) < possibleCandidateIMAux.size())// existe
																	// siguiente
																	// punto
																	// tras
																	// eliminar
							sacarCaracteristicas(
									minutiaeFound.get(possibleCandidateIMAux
											.get(i - 1)),
									minutiaeFound.get(possibleCandidateIMAux
											.get(i)),
									minutiaeFound.get(possibleCandidateIMAux
											.get(i + 2)), cuadranteIMAux,
									ratioIMAux, angleIMAux);
						if ((i + 2) < possibleCandidateIMAux.size())// existe
																	// siguiente
																	// punto
																	// tras
																	// eliminar
							sacarCaracteristicas(
									minutiaeFound.get(possibleCandidateBMAux
											.get(i - 1)),
									minutiaeFound.get(possibleCandidateBMAux
											.get(i)),
									minutiaeFound.get(possibleCandidateBMAux
											.get(i + 2)), cuadranteBMAux,
									ratioBMAux, angleBMAux);

						if (cuadranteIMAux != 0)// elimina en IM
							if (cuadranteIMAux == cuadranteBM
									&& ratioIMAux == ratioBM
									&& (angleIMAux > angleBM - angleRange)
									&& (angleIMAux < angleBM + angleRange)) {
								possibleCandidateIMAux.remove(i + 1);
								continue;
							}
						if (cuadranteBMAux != 0)// elimina en BM
							if (cuadranteIM == cuadranteBMAux
									&& ratioIM == ratioBMAux
									&& (angleIM > angleBMAux - angleRange)
									&& (angleIM < angleBMAux + angleRange)) {
								possibleCandidateBMAux.remove(i + 1);
								continue;
							}
						if (cuadranteIMAux != 0 && cuadranteBMAux != 0) { // elimina
																			// en
																			// ambos
							possibleCandidateIMAux.remove(i + 1);
							possibleCandidateBMAux.remove(i + 1);
							continue;
						}
					}
				} else
					break;

			}
		}
	}

	private static void sacarCaracteristicas(Point anterior, Point actual,
			Point siguiente, int cuadrante, double ratio, double angle) {
		// CUADRANTE
		if (siguiente.getX() > actual.getX())// 1 o 4
			if (siguiente.getY() > actual.getY())
				cuadrante = 4;
			else
				cuadrante = 1;
		else // 2 o 3
		if (siguiente.getY() > actual.getY())
			cuadrante = 3;
		else
			cuadrante = 2;
		// ANGLE
		// Math.toDegrees(Math.atan2(current.x - center.x,current.y - center.y)-
		// Math.atan2(previous.x- center.x,previous.y- center.y));

		int curCenX = siguiente.getX() - actual.getX();
		int curCenY = siguiente.getY() - actual.getY();
		int preCenX = anterior.getX() - actual.getX();
		int preCenY = anterior.getY() - actual.getY();

		double a = Math.abs(Math.toDegrees(Math.atan2(curCenX, curCenY)
				- Math.atan2(preCenX, preCenY)));
		BigDecimal bd = new BigDecimal(Double.toString(a));
		bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);
		angle = bd.doubleValue();
		// RATIO
		int dif = anterior.getX() - actual.getX();
		int valX = dif * dif;
		dif = anterior.getY() - actual.getY();
		int valY = dif * dif;
		double distanceAA = Math.sqrt(valX + valY);
		dif = siguiente.getX() - actual.getX();
		valX = dif * dif;
		dif = siguiente.getY() - actual.getY();
		valY = dif * dif;
		double distanceAS = Math.sqrt(valX + valY);
		if (distanceAA > distanceAS)
			ratio = distanceAA / distanceAS;
		else
			ratio = distanceAS / distanceAA;
		bd = new BigDecimal(Double.toString(ratio));
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		ratio = bd.doubleValue();
	}
}
