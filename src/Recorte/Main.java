package Recorte;

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
		padronJ = IJ.openImage("C:\\Users\\Administrador\\Desktop\\Christian\\9no\\DP1\\Entrega de padrones\\padron.rayas.firmado.2.jpg");
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
		
		int i;
		for(i=0;i<width;i++){
			int r = padronJ.getPixel(i, height/2)[0];
			int g = padronJ.getPixel(i, height/2)[1];
			int b = padronJ.getPixel(i, height/2)[2];
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
			int r = padronJ.getPixel(pixels, i)[0];
			int g = padronJ.getPixel(pixels, i)[1];
			int b = padronJ.getPixel(pixels, i)[2];
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
			int r = padronJ.getPixel(pixels, i)[0];
			int g = padronJ.getPixel(pixels, i)[1];
			int b = padronJ.getPixel(pixels, i)[2];
			//System.out.println(r + " "+ g + " " + b);
			if(r!=0)//r=255 , g=0 , b=0 
				//System.out.println(r + " "+ g + " " + b);
				break;		
		}
		i++;
		int j;
		for(j=width;j>0;j--){
			int r = padronJ.getPixel(j, i)[0];
			int g = padronJ.getPixel(j, i)[1];
			int b = padronJ.getPixel(j, i)[2];
			//System.out.println(r + " "+ g + " " + b);
			if(r!=0)//r=255 , g=0 , b=0 
				//System.out.println(r + " "+ g + " " + b);
				break;	
		}
		
		padronJ.setRoi(0,0,j,height);
		IJ.run(padronJ, "Crop", "");
		//padronJ.show();
		new FileSaver(padronJ).saveAsPng("C:\\Users\\Administrador\\Desktop\\Christian\\9no\\DP1\\Entrega de padrones\\aaa.png");
		
		
		//double proporcion=widthPar/width;
		
		//IJ.run(padronJ, "Scale...", "y= "+ proporcion + " width=" + widthPar + " interpolation=Bilinear average create");
		//IJ.run(padronJ, "Scale...", "x=- y=- width="+ widthPar +" interpolation=Bilinear average create");
		//new FileSaver(padronJ).saveAsPng("C:\\Users\\Administrador\\Desktop\\Christian\\9no\\DP1\\Entrega de padrones\\aaa.png");

	}

}
