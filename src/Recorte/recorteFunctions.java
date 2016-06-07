package Recorte;

import java.io.File;

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
    
    public int contarPadrones(){
        String workingDir = System.getProperty("user.dir"); // nos evitamos el problema de las rutas :'v
        return new File(workingDir + "/src/Recorte/padrones/").list().length;
    }
    
    public void eliminarLineasNegras(){
        
        int width=padronJ.getWidth();
        int height=padronJ.getHeight();
        int pixels = 10;

        //Verificamos el lado izquierdo
        for (int i = 0; i< width; i++ ){
            int R = padronJ.getPixel(i, height/2)[0];
            if ( R == 0 ){
                //System.out.println("entre aka xd");
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
    

    
    public void recortarCostadosProcesarPadron(String ruta1, String ruta2, String ruta3){
        
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
        alinearPadron();


        
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
        new FileSaver(padronJ).saveAsPng(ruta3);
        IJ.run(padronJ, "Skeletonize", "");
        new FileSaver(padronJ).saveAsPng(ruta2);
        
    //    Prefs.blackBackground = false;
        this.padronJ = padronJ;
    //    padronJ.show();
        
        
    }
    


    public void alinearPadron(){
    
        
        int width=padronJ.getWidth();
        int height=padronJ.getHeight();
        int i,r = 0,g = 0,b = 0 , m = 0;
        
        //System.out.println(height);
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
                // System.out.println("holas" + r);
                // System.out.println(i);
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
             r = padronJ.getPixel(width1-7, i)[0];
             if (r != 0){
                cont++;
                if (cont == 2) {
                    for (m = width1-7; m > 0; m--){
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
        for(i=anchoY;i>0;i--){
             r = padronJ.getPixel(i, alturaX)[0];
             if (r != 0){
                 cont++;
                 if (cont == 1) {
                     this.yApellido = i+1;
                     this.yNombre = i-1 + (25*15);
                 }
                 if (cont == 2){
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