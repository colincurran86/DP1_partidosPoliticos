
package Recorte;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Catalano.Imaging.FastBitmap;
import ij.IJ;
import ij.ImagePlus;
import ij.Prefs;
import ij.gui.PolygonRoi;
import ij.gui.Roi;
import ij.io.FileSaver;

public class recorteFunctions {
    
    private int x, yHuellas, yFirmas, yDNI, yNombre, yApellido;
    private int PX1, PY1, PX2, PY2, PX3, PY3;
    private ImagePlus padronJ = new ImagePlus();
    public static List<Integer>coordX = new ArrayList<Integer>();
    public static List<Integer>coordY = new ArrayList<Integer>();
    public static int alto;
    public static int ancho;



    public int contarPadrones(String rutaPadrones){
        String workingDir = System.getProperty("user.dir"); // nos evitamos el problema de las rutas :'v
        return new File(workingDir + "/src/Recorte/padrones/").list().length;
    }
    
    public void recortesHuellasOficial(String rutaPlanillon){
    	
    	// TODO Auto-generated method stub
    			//Mat padron=Imgcodecs.imread("C:/Users/Administrador/Desktop/Christian/9no/DP1/Entrega de Padrones/padron.blanco.firmado.2.jpg");
	
    			
    			
    			
    				ImagePlus padronJ = new ImagePlus();
       				padronJ = IJ.openImage(rutaPlanillon);
    		        //padronJ.show();
    				Prefs.blackBackground = false;
    				IJ.run(padronJ, "Make Binary", "");
    				
    				int width=padronJ.getWidth();
    				int height=padronJ.getHeight();

    				
    		        //Verificamos el lado izquierdo
    		        for (int i = 0; i< width; i++ ){
    		            int R = padronJ.getPixel(i, height/2)[0];
    		            if ( R == 0 ){
    		                if (i != 0){
    		                   padronJ.setRoi(i+3,0,width-i-1,height-1);
    		                   IJ.run(padronJ, "Crop", "");    
    		                  // padronJ.show();
    		                }
    		                break;
    		            }
    		        }
    		        
    		      //  System.out.println("*****MOSTRANDO IMAGEEEEEEEEEEN******");
                  //  new FileSaver(padronJ).saveAsPng("C:\\Users\\inf250\\Documents\\DP1 MATER\\Kappa\\"+ "blinbineo.jpg");

    				//////////////
    				//ELIMINA LA PARTE DE LA IZQUIERDA
    				//////////////
    				
    		
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
    					if(r!=0)
    						break;	
    				}
    				
    				y+=13;
    				
    				
    				//hallar el x por la izquierda		
    				int xIzq;
    				for(xIzq=width;xIzq>0;xIzq--){
    					int r = padronJ.getPixel(xIzq, y)[0];	
    					if(r!=0)
    						break;
    				}
    				x--;
				
    				/*
    				int xIzq;
                    int yBlanco=y+13;
                    //hallar el x por la izquierda     
                    for(xIzq=width-1;xIzq>0;xIzq--){
                        int r = padronJ.getPixel(xIzq, yBlanco)[0];
                     
                        if(r!=0)
                            break;
                    }
                    //xIzq-=3;
                    int extremX;
                    for(extremX=xIzq;extremX>0;extremX--){
                        int r = padronJ.getPixel(extremX, yBlanco)[0];
                        if(r==0)
                            break;
                    }
                    int distMedia=(xIzq-extremX)/2;
                    System.out.println("DISTANCIA MED" + distMedia);
                    xIzq-=distMedia;
                    */
    				//sumar borde de la grilla
    				
    				int k,cantNegros=0;
    				System.out.println("XIZQ :" + xIzq);
    				System.out.println("Y: " + y);
    				for(k=y;k<height;k++){
    					int r = padronJ.getPixel(xIzq  - 1 , k)[0];
    					if(r==0){
    						System.out.println("PRIMER VALOR: " + (xIzq- 2 ));
    						System.out.println("SEGUNDO VALOR: " + (k));
    						break;
    					}
    						
    					cantNegros++;			
    				}
    				
    				//volar la cabecera!
                    int volarCabecera=xIzq-25,f;
                    for(f=0;f<height;f++){//blancos!
                        int r = padronJ.getPixel(volarCabecera, f)[0];
                        if(r!=0)break;
                    }
                    for(f=f;f<height;f++){//negros!
                        int r = padronJ.getPixel(volarCabecera, f)[0];
                        if(r==0)break;
                    }
                    
                    System.out.println("F NEGROS " + f);
    				
                    for(f=f;f<height;f++){//blancos!
                        int r = padronJ.getPixel(volarCabecera, f)[0];
                        if(r!=0)break;
                    }
                    
                    System.out.println("F blancos " + f);
                    
                    for(f=f;f<height;f++){//negros!
                        int r = padronJ.getPixel(volarCabecera, f)[0];
                        if(r==0)break;
                    }
                    
                    System.out.println("F Negros22 " + f);

                    
    				/*
                    int volarCabecera=xIzq-25,f;
                   
                    for(f=0;f<height;f++){//blancos!
                        int r = padronJ.getPixel(volarCabecera, f)[0];
                        if(r!=0)break;
                    }
                    for(f=f;f<height;f++){//negros!
                        int r = padronJ.getPixel(volarCabecera, f)[0];
                        if(r==0)break;
                    }
                    for(f=f;f<height;f++){//blancos!
                        int r = padronJ.getPixel(volarCabecera, f)[0];
                        if(r!=0)break;
                    }
                    for(f=f;f<height;f++){//negros!
                        int r = padronJ.getPixel(volarCabecera, f)[0];
                        if(r==0)break;
                    }
                    for(f=f;f<height;f++){//blancos!
                        int r = padronJ.getPixel(volarCabecera, f)[0];	
                        if(r!=0)break;
                    }
                    */
    				

    				/*
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
                    */
    				
    				System.out.println("VALOR DE F : " + f);
    				System.out.println("VALOR DE Y : " + y);
    				System.out.println("NEGROS ANTER : " + cantNegros);

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
    				System.out.println("Extremo derecho : X " + xIzq + " " + f  + " Extremo izquierdo: X "+ p + " " + f);
    				
    				alto=cantNegros/8;
    				ancho=xIzq-p;
    				
    				//System.out.println("Alto: " + alto + " Ancho: " + ancho);
    				
    				int vecesEntro=0;
    				for(int i=f;i<f+cantNegros;i+=alto){
    					
    					vecesEntro++;
    					if(vecesEntro>8) break;
    					coordX.add(p);
    					coordY.add(i);
    					//System.out.println("Posicion X: "+ p + " Y " + i);
    					//padronJ = IJ.openImage("C:\\Users\\Administrador\\Desktop\\Christian\\9no\\DP1\\Nuestros planillones\\Planillones\\part.d.original8.png");
    					///padronJ.setRoi(p,i,ancho,alto);
    					//IJ.run(padronJ,"Crop","");
    					//padronJ.show();
    					
    				}
    			
    }
    
    public void tamanhoEstandar(String directorioPadrones){
    	BufferedImage image = null;
    	File folder = new File(directorioPadrones);
        String workingDir = System.getProperty("user.dir"); // nos evitamos el problema de las rutas :'
        String rutaAlmacenar = workingDir+ "/Copia";

    	for (final File fileEntry : folder.listFiles()){
    		String ruta1 = rutaAlmacenar + "/" + fileEntry.getName();
    		ImageIcon ii = new ImageIcon(ruta1);
    		BufferedImage bi = new BufferedImage(1280, 965, BufferedImage.TYPE_INT_RGB);
    		Graphics2D g2d = (Graphics2D)bi.createGraphics();
    		g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY));
    		boolean b = g2d.drawImage(ii.getImage(), 0, 0, 1280, 965, null);
    		FastBitmap fb = new FastBitmap(bi);
    		fb.saveAsPNG(ruta1);
    	}
    }
    
    public void guardarImagenes(String directorioPadrones){
    	BufferedImage image = null;
    	System.out.println(directorioPadrones);
    	File folder = new File(directorioPadrones);
    	
        String workingDir = System.getProperty("user.dir"); // nos evitamos el problema de las rutas :'
        String rutaAlmacenar = workingDir+ "/Copia";
        File file = new File(rutaAlmacenar);  
        file.mkdirs();


    	for (final File fileEntry : folder.listFiles()){
    		String ruta1 = directorioPadrones + "/" + fileEntry.getName();
    		System.out.println(ruta1);
            ImagePlus padronJ = new ImagePlus();
            padronJ = IJ.openImage(ruta1); 
            new FileSaver(padronJ).saveAsPng(rutaAlmacenar + "/" + fileEntry.getName() );
    	}
    	
    	
    }
     
    public void eliminarLineasNegras(){
        
        int width=padronJ.getWidth();
        int height=padronJ.getHeight();
        int pixels = 10;

        //Verificamos el lado izquierdo
        for (int i = 0; i< width; i++ ){
            int R = padronJ.getPixel(i, height/2)[0];
            if ( R == 0 ){
                if (i != 0){
                   padronJ.setRoi(i,0,width-i-1,height-1);
                    IJ.run(padronJ, "Crop", "");    
                    //padronJ.show();
                }
                break;
            }
        }
        
        

        
        //Verificamos el lado derecho
        /*
        for(int j=width;j>0;j--){
             int R = padronJ.getPixel(j, pixels)[0];
            if(R == 0){
                if (j !=0){
                    padronJ.setRoi(0,j,width-j-1,height-1);
                    IJ.run(padronJ, "Crop", "");    
                    break;    
                }
            }
        }

        
        //Verificamos abajo

        for(int i=height; i>0 ; i--){
             int R = padronJ.getPixel(pixels, i)[0];
            if(R==0){//r=255 , g=0 , b=0 
                if (i != 0){
                    padronJ.setRoi(i,0,width-i-1,height-1);
                    IJ.run(padronJ, "Crop", "");    
                    //padronJ.show();
                }                
                break;            
            }
        }
        
        

        //Verificamos abajo

        
                
    */
    }
    

    
    public void recortarCostadosProcesarPadron(String ruta1, String ruta2, String ruta3,String rutaPadrones){
        
        int widthPar=2073;
        int heightPar=972;
        int personasxPadron = 8; // kappa
        
        
        //verificamos que la imagen se encuentre correctamente alineada

        ImagePlus padronJ = new ImagePlus();
        padronJ = IJ.openImage(ruta1); 
        this.padronJ = padronJ;
        Prefs.blackBackground = false;        
        IJ.run(padronJ, "Make Binary", "");
        
        
     
        //Verificamos si es que no existe lineas negras en la imagen
        eliminarLineasNegras(); 
        //alinearPadron();

        
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
            if(r!=0)//r=255 , g=0 , b=0 
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
             r = padronJ.getPixel(width/2, i)[0];
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
            if(r!=0)//r=255 , g=0 , b=0 
                break;        
        }
                
        i++; 	
        int j;
        for(j=width;j>0;j--){
             r = padronJ.getPixel(j, height/2)[0];
            if(r!=0)//r=255 , g=0 , b=0 
                break;    
        }
        
        padronJ.setRoi(0,0,j,height);
        IJ.run(padronJ, "Crop", "");
        
        //padronJ.show();
        

        
        String rutaAlmacenar = rutaPadrones+ "/Auxiliar";
        File file = new File(rutaAlmacenar);  
        file.mkdirs();

        new FileSaver(padronJ).saveAsPng(rutaAlmacenar + "/recorteCostado.jpg");
        IJ.run(padronJ, "Skeletonize", "");
        new FileSaver(padronJ).saveAsPng(rutaAlmacenar + "/recorteBN.jpg");
        this.padronJ = padronJ;
          
    }
    



    public void alinearPadron(){
    
        
        int width=padronJ.getWidth();
        int height=padronJ.getHeight();
        int i,r = 0,g = 0,b = 0 , m = 0;
        
        for(i=0;i<width;i++){
            r = padronJ.getPixel(i, height/2)[0];
            if(r!=0){//r=255 , g=0 , b=0 
                this.PX1 = i;
                this.PY1 = height/2;
                break;    
            }
        }
        
        // Ahora situamos X, Y-40
        
        this.PX2 = i;
        this.PY2 = this.PY1-40;
        
        //obtenemos el ultimo punto para formar el angulo
        
        for(i=this.PX2;i>0;i--){
            r = padronJ.getPixel(i, this.PY2)[0];
            if(r!=0){//r=255 , g=0 , b=0 
                this.PX3 = i;
                this.PY3 = this.PY2;
                break;    
            }
        }
        
        int[] xpoints1 = {this.PX3,this.PX1, this.PX2};
        int[] ypoints1 = {this.PY3,this.PY1, this.PY2};
        double anguloDouble = new PolygonRoi(xpoints1,ypoints1,3,Roi.ANGLE).getAngle();
        int anguloInt = (int) Math.round(anguloDouble);
        String str1 = "angle=" + anguloInt + " grid=0 interpolation=None";
        IJ.run(this.padronJ, "Rotate... ", str1);
        //this.padronJ.show();


    }
    
    public int obtenerSiguienteEspacioDNI(int yDNI,int alturaX){
        
        ImagePlus imagen = this.padronJ;
        int condicion = 0, siguientePunto = 0;
        for (int i = yDNI; ;i++){
             int r = padronJ.getPixel(i, alturaX)[0];
             if (r != 0){
                 for (int m = i; ; m++){	
                     int h = padronJ.getPixel(m, alturaX)[0];
                     if (h == 0){
                        condicion = 1;
                        siguientePunto = m;
                        break;
                     }
                 }
             }
             if (condicion == 1) break;
        }
        
        return siguientePunto;
        
    }

    public void coordenadasHuella(ImagePlus padronJ){
        
        int width1=this.padronJ.getWidth();
        int height1=this.padronJ.getHeight();
       // this.padronJ.show();
        int m, i, r , g , b , cont = 0 , alturaX = 0, anchoY = 0;
        for(i=0;i<1000;i++){
             //r = padronJ.getPixel(width1-7, i)[0];
        	r = padronJ.getPixel(width1-13, i)[0];
             if (r != 0){
                cont++;
                if (cont == 2) {
                    for (m = width1-13; m > 0; m--){
                         r = padronJ.getPixel(m, i-1)[0];
                         if (r != 0){
                             // esquina izquierda superior del cuadrado de las huellas
                             alturaX = i; 
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
        for(i=anchoY;i>0;i--){
             r = padronJ.getPixel(i, alturaX)[0];
             if (r != 0){
                 this.yFirmas = i + 1;
                 break;
                 
             }
        }
        
    }
    
    
    public void coordenadaDNIyNombre(ImagePlus padronJ){
        
        int width1=padronJ.getWidth();
        int height1=padronJ.getHeight();
        int m, i, r , g , b , cont = 0 , alturaX = this.x - 3 , anchoY = this.yFirmas - 2;
        int condVeri = 1;
        for(i=anchoY;i>0;i--){
             r = padronJ.getPixel(i, alturaX)[0];
             if (r != 0){
                 cont++;
                 /*
                 if (cont == 1) {
                     this.yApellido = i+1;
                     this.yNombre = i-1 + (25*15);
                 }
                 if (cont == 2){
                     this.yDNI = i + 1;
                     break;     
                 }
                 */
                 if (condVeri == 1 && i>=128  && i<=136) {
                     this.yApellido = i+1;
                     this.yNombre = i-1 + (25*15);
                     condVeri = 0;
                 }
                 if (i>=16  && i<=24){
                     this.yDNI = i + 1;
                     break;     
                 }
                 
             }
        }
        
    }
    
    public int getAnchoDNI(int yDNI, int xDNI){
    	
    	for (int i = yDNI; ;i++){
	
    		   int r = padronJ.getPixel(i, xDNI)[0];
               if (r != 0){
               	 return i-yDNI;	
               }   	
    	}
    }
    
    public int obtenerSiguienteEspacioFirmas(int yFirmas, int alturaX){
		    	
        int condicion = 0, siguientePunto = 0;
        for (int i = alturaX; ;i++){
             int r = padronJ.getPixel(yFirmas, i)[0];
             if (r != 0){
                 for (int m = i; ; m++){
                     int h = padronJ.getPixel(yFirmas, m)[0];
                     if (h == 0){
                        condicion = 1;
                        siguientePunto = m;
                        break;
                     }
                 }
             }
             if (condicion == 1) break;
        }        
        return siguientePunto;    	    	
    	
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
    
    public int getYDNI (){
        return this.yDNI;
    }
    
    public int getYApellido(){
        return this.yApellido;
    }
    
    public int getYNombre(){
        return this.yNombre;
    }
    
}