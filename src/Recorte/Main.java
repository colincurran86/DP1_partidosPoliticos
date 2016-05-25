package Recorte;

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
		padronJ = IJ.openImage("C:/Users/inf250/git/DP1_partidosPoliticos/src/Recorte/padron.rayas.firmado.2.jpg");
	
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
		
		int i,r = 0,g = 0,b = 0;
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

		new FileSaver(padronJ).saveAsPng("C:/Users/inf250/git/DP1_partidosPoliticos/src/Recorte/recorteCostado.jpg");
		Prefs.blackBackground = false;
		
		//double proporcion=widthPar/width;
		
		//IJ.run(padronJ, "Scale...", "y= "+ proporcion + " width=" + widthPar + " interpolation=Bilinear average create");
		//IJ.run(padronJ, "Scale...", "x=- y=- width="+ widthPar +" interpolation=Bilinear average create");
		//new FileSaver(padronJ).saveAsPng("C:\\Users\\Administrador\\Desktop\\Christian\\9no\\DP1\\Entrega de padrones\\aaa.png");
		
		
		//Cropeamos las firmas y las huellas para cada persona
		List<ImagePlus>usuariosFirma = new ArrayList<ImagePlus>();
		List<ImagePlus>usuariosHuella = new ArrayList<ImagePlus>();

		for (int m = 0; m<8; m++){
			ImagePlus firmaUser,huellasUser = new ImagePlus();
			firmaUser = IJ.openImage("C:/Users/inf250/git/DP1_partidosPoliticos/src/Recorte/recorteCostado.jpg");
			huellasUser = IJ.openImage("C:/Users/inf250/git/DP1_partidosPoliticos/src/Recorte/recorteCostado.jpg");
			usuariosFirma.add(firmaUser); 	usuariosHuella.add(huellasUser);
		}	
		//for (int m = 0; m < 8; m++) usuarios.get(m).show();
		int distanceBetweenSquares = 86, widthSquare = 150, heightSquare = 75;
		
		// obtenerCoordenadasFirma = funcionParaObtenerla;
		// por el momento se utilizara un valor predeterminado donde se encuentra la firma
		
		//para cada imagen leída de la carpeta de imagenes realizar lo de abajo. 
		for (int m = 0; m<8; m++){
			//firma
				ImagePlus Copia1,Copia2;
				Copia1 = usuariosFirma.get(m); Copia2 = usuariosHuella.get(m);
				Copia1.setRoi(862, 207 + distanceBetweenSquares*m , widthSquare , heightSquare);
			    IJ.run(Copia1, "Crop", ""); 
			    Copia1.show();
			//huella
				Copia2.setRoi(1029, 205+ distanceBetweenSquares*m , widthSquare + 12, heightSquare+4);
				IJ.run(Copia2, "Crop", ""); 
				Copia2.show();		
		}
		

	}

}
