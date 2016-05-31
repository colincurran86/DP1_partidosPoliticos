package Recorte;

import ij.IJ;
import ij.ImagePlus;
import ij.Prefs;
import ij.io.FileSaver;

public class recorteFunctions {
	
	private int x, yHuellas, yFirmas;
	private ImagePlus padronJ = new ImagePlus();
	
	public void recortarCostadosProcesarPadron(String workingDir){
		
		int widthPar=2073;
		int heightPar=972;
		int personasxPadron = 8;
		String ruta1 = workingDir +"/src/Recorte/padron.rayas.firmado.2.jpg";
		String ruta2 = workingDir + "/src/Recorte/recorteCostado.jpg";
		
		
		ImagePlus padronJ = new ImagePlus();
		padronJ = IJ.openImage(ruta1);
		Prefs.blackBackground = false;
		IJ.run(padronJ, "Make Binary", "");

		
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

		new FileSaver(padronJ).saveAsPng(ruta2);
		Prefs.blackBackground = false;
		this.padronJ = padronJ;
		
	}
	
	public void coordenadasHuella(ImagePlus padronJ){
		
		int width1=padronJ.getWidth();
		int height1=padronJ.getHeight();
		int m, i, r , g , b , cont = 0 , alturaX = 0, anchoY = 0;

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
							 alturaX = i+1; 
							 anchoY = m+1;
							 break;
						 }
					}
					break;
				}
			 }
		}
		
		this.x = alturaX;
		this.yHuellas = anchoY;
	}
	
	public void coordenadasFirma(ImagePlus padronJ){
		
		int width1=padronJ.getWidth();
		int height1=padronJ.getHeight();
		int m, i, r , g , b , cont = 0 , alturaX = this.x - 3 , anchoY = this.yHuellas - 2;
		//System.out.println("altura x " + alturaX);
		//System.out.println("altura y " +anchoY);
		//System.out.println(padronJ.getPixel(887, 201)[0]);
		//System.out.println(padronJ.getPixel(1026, 201)[0]);
		for(i=anchoY;i>0;i--){
			 r = padronJ.getPixel(i, alturaX)[0];
			 if (r != 0){
				 this.yFirmas = i + 1;
				 break;
			 }
		}
		
	}
	
	public ImagePlus getPadron(){
		return this.padronJ;
	}
	
	
	
	public int getX (){
		return this.x;
	}
	
	public int getYHuellas (){
		return this.yHuellas;
	}
	
	public int getYFirmas (){
		return this.yFirmas;
	}
	
}
