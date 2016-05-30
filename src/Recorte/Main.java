package Recorte;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ij.IJ;
import ij.ImagePlus;
import ij.Prefs;
import ij.io.FileSaver;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Mat padron=Imgcodecs.imread("C:/Users/Administrador/Desktop/Christian/9no/DP1/Entrega de Padrones/padron.blanco.firmado.2.jpg");
		
		int widthPar=2073;
		int heightPar=972;
		
		
		ImagePlus padronJ = new ImagePlus();
		/*IJ.run(padronJ, "Undo", "");
		padronJ.close();*/
		padronJ = IJ.openImage("D:/Users/a20101616/git/DP1_partidosPoliticos/src/Recorte/padron.rayas.firmado.2.jpg");
		Prefs.blackBackground = false;
		IJ.run(padronJ, "Make Binary", "");
		//padronJ.show();
		//new FileSaver(padronJ).saveAsPng("C:\\Users\\Administrador\\Desktop\\Christian\\9no\\DP1\\Entrega de padrones\\aaa.png"); 
		//FileInfo fi= padronJ.getFileInfo();
		//imp.setRoi(10,158,1246,748);
		//IJ.run(imp, "Crop", "");
		
		//////////////
		//ELIMINA LA PARTE DE LA IZQUIERDA
		//////////////
		
		int width=padronJ.getWidth();
		int height=padronJ.getHeight();
		
		int i,r = 0,g = 0,b = 0 , m = 0;
		for(i=0;i<width;i++){
			 r = padronJ.getPixel(i, height/2)[0];
			 g = padronJ.getPixel(i, height/2)[1];
			 b = padronJ.getPixel(i, height/2)[2];
			//System.out.println(i);
			//System.out.println(r + " "+ g + " " + b);
			if(r!=0)//r=255 , g=0 , b=0 
				//System.out.println(r + " "+ g + " " + b);
				break;											
		}

		padronJ.setRoi(i,0,width-i-1,height-1);
		IJ.run(padronJ, "Crop", "");
		//padronJ.show();
		//new FileSaver(padronJ).saveAsPng("C:\\Users\\Administrador\\Desktop\\Christian\\9no\\DP1\\Entrega de padrones\\aaa.png");
		//IJ.write("C:\\Users\\Administrador\\Desktop\\Christian\\9no\\DP1\\Entrega de padrones\\aaaaaaa.jpg");

		//////////////
		//ELIMINA LA PARTE DE ABAJO
		//////////////
		width=padronJ.getWidth();
		height=padronJ.getHeight();
		
		int pixels=10;
		for(i=height;i>0;i--){
			 r = padronJ.getPixel(pixels, i)[0];
			 g = padronJ.getPixel(pixels, i)[1];
			 b = padronJ.getPixel(pixels, i)[2];
			//System.out.println(r + " "+ g + " " + b);
			if(r!=0)//r=255 , g=0 , b=0 
				//System.out.println(r + " "+ g + " " + b);
				break;		
		}
		
		padronJ.setRoi(0,0,width-1,i-1);
		IJ.run(padronJ, "Crop", "");
		//padronJ.show();
		//new FileSaver(padronJ).saveAsPng("C:\\Users\\Administrador\\Desktop\\Christian\\9no\\DP1\\Entrega de padrones\\aaa.png");
		
		//////////////
		//ELIMINA LA PARTE DE LA DERECHA
		//////////////
		width=padronJ.getWidth();
		height=padronJ.getHeight();
		
		for(i=0;i<height;i++){
			 r = padronJ.getPixel(pixels, i)[0];
			 g = padronJ.getPixel(pixels, i)[1];
			 b = padronJ.getPixel(pixels, i)[2];
			//System.out.println(r + " "+ g + " " + b);
			if(r!=0)//r=255 , g=0 , b=0 
				//System.out.println(r + " "+ g + " " + b);
				break;		
		}
		i++;
		int j;
		for(j=width;j>0;j--){
			 r = padronJ.getPixel(j, i)[0];
			 g = padronJ.getPixel(j, i)[1];
			 b = padronJ.getPixel(j, i)[2];
			//System.out.println(r + " "+ g + " " + b);
			if(r!=0)//r=255 , g=0 , b=0 
				//System.out.println(r + " "+ g + " " + b);
				break;	
		}
		
		padronJ.setRoi(0,0,j,height);
		IJ.run(padronJ, "Crop", "");
		//padronJ.show();
		IJ.run(padronJ, "Skeletonize", "");

		new FileSaver(padronJ).saveAsPng("D:/Users/a20101616/git/DP1_partidosPoliticos/src/Recorte/recorteCostado.jpg");
		Prefs.blackBackground = false;
		
		int width1=padronJ.getWidth();
		int height1=padronJ.getHeight();
		int cont = 0, xHuellas = 0 , yHuellas = 0;
		
		//obtienes esquina izquierda superior de una huella imp.setRoi(1027,204,28,24); imp.setRoi(y,x,cuadrado1,cuadrado2);
		for(i=0;i<1000;i++){
			 r = padronJ.getPixel(width1-3, i)[0];
 			 g = padronJ.getPixel(width1-3, i)[1];
			 b = padronJ.getPixel(width1-3, i)[2];
			 if (r != 0){
				cont++;
				if (cont == 2) {
					for (m = width1-3; m > 0; m--){
						 r = padronJ.getPixel(m, i-1)[0];
						 g = padronJ.getPixel(m, i-1)[1];
						 b = padronJ.getPixel(m, i-1)[2];
						 if (r != 0){
							 // esquina izquierda superior del cuadrado de las huellas
							 xHuellas = i+1; 
							 yHuellas = m+1;
							 break;
						 }
					}
					break;
				}
			 }
		}
		
		//System.out.println(xHuellas); System.out.println(yHuellas);
		System.out.println("Procesando");
		//cropeamos los digitos del DNI

		int distanceBetweenSquaresH = 85 ,distanceBetweenSquares = 15, widthSquare = 14, heightSquare = 84;

		for (int n  = 0; n<8; n++){
			ImagePlus Copia1;
			String rutaAlmacenar = "D:/Users/a20101616/git/DP1_partidosPoliticos/src/Recorte/Resultado/Persona"
			+ String.valueOf(n+1);
			File file = new File(rutaAlmacenar); 
			file.mkdir();
			//alguna razon rara tengo q hacer esto otra vez para que cree la carpeta DNI :S
			String rutaAlmacenar2 = "D:/Users/a20101616/git/DP1_partidosPoliticos/src/Recorte/Resultado/Persona"
			+ String.valueOf(n+1)  + "/DNI/";
			File file2 = new File(rutaAlmacenar2); 
			file2.mkdir();
			for (int h = 0; h<8 ; h++) {
				Copia1 = IJ.openImage("D:/Users/a20101616/git/DP1_partidosPoliticos/src/Recorte/recorteCostado.jpg");
				if (n<4) Copia1.setRoi(26 + distanceBetweenSquares*h, 206 + distanceBetweenSquaresH * n  , 11 , 82);
				else Copia1.setRoi(26 + distanceBetweenSquares*h, 209 + distanceBetweenSquaresH * n  , 11 , 82);
			   // Copia1.show();
				IJ.run(Copia1, "Crop", ""); int k = h+1;
			    String rutaDNI = "D:/Users/a20101616/git/DP1_partidosPoliticos/src/Recorte/Resultado/Persona"
			    + String.valueOf(n+1) + "/DNI/" + k + ".jpg";
				new FileSaver(Copia1).saveAsPng(rutaDNI);
				Prefs.blackBackground = false;
			}
		}	
		
		System.out.println("Finalizado");

		
		//Cropeamos el Nombre
		
		
		
		
		//Cropeamos el Apellido
		
		
		
		//Cropeamos las firmas y las huellas para cada persona
		List<ImagePlus>usuariosFirma = new ArrayList<ImagePlus>();
		List<ImagePlus>usuariosHuella = new ArrayList<ImagePlus>();

		for (int n  = 0; n<8; n++){
			ImagePlus firmaUser,huellasUser = new ImagePlus();
			firmaUser = IJ.openImage("D:/Users/a20101616/git/DP1_partidosPoliticos/src/Recorte/recorteCostado.jpg");
			huellasUser = IJ.openImage("D:/Users/a20101616/git/DP1_partidosPoliticos/src/Recorte/recorteCostado.jpg");
			usuariosFirma.add(firmaUser); 	usuariosHuella.add(huellasUser);
		}	
		//for (int m = 0; m < 8; m++) usuarios.get(m).show();
	    distanceBetweenSquares = 86; widthSquare = 150; heightSquare = 75;
		
		// obtenerCoordenadasFirma = funcionParaObtenerla;
		// por el momento se utilizara un valor predeterminado donde se encuentra la firma
		
		//para cada imagen leída de la carpeta de imagenes realizar lo de abajo. 
		for (int n = 0; n<8; n++){
			//firma
				ImagePlus Copia1,Copia2;
				Copia1 = usuariosFirma.get(n); Copia2 = usuariosHuella.get(n);
				Copia1.setRoi(862, 207 + distanceBetweenSquares*n , widthSquare , heightSquare);
			    IJ.run(Copia1, "Crop", ""); 
				//new FileSaver(padronJ).saveAsPng("D:/Users/a20101616/git/DP1_partidosPoliticos/src/Recorte/recorteCostado.jpg");
				//Prefs.blackBackground = false;
			    //Copia1.show();
			//huella
				Copia2.setRoi(1029, 205+ distanceBetweenSquares*n , widthSquare + 12, heightSquare+4);
				IJ.run(Copia2, "Crop", ""); 
				//new FileSaver(padronJ).saveAsPng("D:/Users/a20101616/git/DP1_partidosPoliticos/src/Recorte/recorteCostado.jpg");
				//Prefs.blackBackground = false;
				//Copia2.show();		
		}	
		

	}

}
