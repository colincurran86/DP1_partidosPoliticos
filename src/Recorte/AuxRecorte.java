package Recorte;

import ij.IJ;
import ij.ImagePlus;
import ij.Prefs;

public class AuxRecorte {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Mat padron=Imgcodecs.imread("C:/Users/Administrador/Desktop/Christian/9no/DP1/Entrega de Padrones/padron.blanco.firmado.2.jpg");
		
		int widthPar=2073;
		int heightPar=972;
		
		
			ImagePlus padronJ = new ImagePlus();
			/*IJ.run(padronJ, "Undo", "");
			padronJ.close();*/
			padronJ = IJ.openImage("C:\\Users\\Administrador\\Desktop\\Christian\\9no\\DP1\\Nuestros planillones\\Planillones\\part.d.original8.png");
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
			
			int x;
			for(x=0;x<width;x++){
				int r = padronJ.getPixel(x, height/2)[0];
				int g = padronJ.getPixel(x, height/2)[1];
				int b = padronJ.getPixel(x, height/2)[2];
				//System.out.println(r + " "+ g + " " + b);
				if(r!=0)//r=255 , g=0 , b=0 
					//System.out.println(r + " "+ g + " " + b);
					break;											
			}
			//System.out.println("XXXX" + x);
			
			int y;
			for(y=0;y<height;y++){
				int r = padronJ.getPixel(x, y)[0];
				int g = padronJ.getPixel(x, y)[1];
				int b = padronJ.getPixel(x, y)[2];
				if(r!=0)
					break;	
			}
			y+=5;
			
			//hallar el x por la izquierda		
			int xIzq;
			for(xIzq=width-1;xIzq>0;xIzq--){
				int r = padronJ.getPixel(xIzq, y)[0];
				int g = padronJ.getPixel(xIzq, y)[1];
				int b = padronJ.getPixel(xIzq, y)[2];
				if(r!=0)
					break;
			}
			x--;
			//System.out.println("XXXX" + y);
			//System.out.println("XXXX" + xIzq);
			
			//sumar borde de la grilla
			int k,cantNegros=0;
			for(k=y;k<height;k++){
				int r = padronJ.getPixel(xIzq, k)[0];
				int g = padronJ.getPixel(xIzq, k)[1];
				int b = padronJ.getPixel(xIzq, k)[2];
				if(r==0)
					break;
				cantNegros++;			
			}
			
			//volar la cabecera!
			int volarCabecera=xIzq-25,f;
			for(f=y;f<height;f++){
				int r = padronJ.getPixel(volarCabecera, f)[0];
				int g = padronJ.getPixel(volarCabecera, f)[1];
				int b = padronJ.getPixel(volarCabecera, f)[2];
				if(r==0)
					break;
			}
			//dentro de la cabecera
			for(f=f;f<height;f++){
				int r = padronJ.getPixel(volarCabecera, f)[0];
				int g = padronJ.getPixel(volarCabecera, f)[1];
				int b = padronJ.getPixel(volarCabecera, f)[2];
				if(r!=0)
					break;
			}
			//el Y al terminar la cabecera
			for(f=f;f<height;f++){
				int r = padronJ.getPixel(volarCabecera, f)[0];
				int g = padronJ.getPixel(volarCabecera, f)[1];
				int b = padronJ.getPixel(volarCabecera, f)[2];
				if(r==0)
					break;
			}
			System.out.println("Y Y : " + f);
			cantNegros=cantNegros-(f-y);
			
			
			
			
			int p;
			for(p=xIzq-50;p>0;p--){
				int sumNegros=0;
				for(int q=0;q<cantNegros;q++){
					int r = padronJ.getPixel(p, f+q)[0];
					int g = padronJ.getPixel(p, f+q)[1];
					int b = padronJ.getPixel(p, f+q)[2];
					if(r!=0) sumNegros++;
				}
				if(sumNegros==cantNegros) break;
			}
			System.out.println("Cant negros: " + cantNegros);
			System.out.println("Extremo derecho : X " + xIzq + " " + f  + "Extremo izquierdo: X "+ p + " " + f);
			
			int alto=cantNegros/8;
			int ancho=xIzq-p;
			
			System.out.println("Alto: " + alto + " Ancho: " + ancho);
			
			int vecesEntro=0;
			for(int i=f;i<f+cantNegros;i+=alto){
				
				vecesEntro++;
				if(vecesEntro>8) break;
				System.out.println("Posicion X: "+ p + " Y " + i);
				padronJ = IJ.openImage("C:\\Users\\Administrador\\Desktop\\Christian\\9no\\DP1\\Nuestros planillones\\Planillones\\part.d.original8.png");
				padronJ.setRoi(p,i,ancho,alto);
				IJ.run(padronJ,"Crop","");
				padronJ.show();
				
			}
			
			
			
			
			
		
		
		
		
		
		
		/*				
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
		 * 
		 * 
		 */
	}

}
