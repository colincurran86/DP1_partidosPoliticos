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

import Catalano.Imaging.Filters.Crop;
import Catalano.Imaging.Filters.OtsuThreshold;
import Catalano.Imaging.Filters.Rotate;
import Catalano.Imaging.Filters.Resize;

/**
 * @author LUIS S
 */
public class AlgoritmoFirmas {

  

    private String rutaCarpetaImagenes;
    private String rutaImagen1;
    private String rutaImagen2;
    private boolean corte;
    private boolean dibujarTodasImagenes;
    private boolean dibujarImagenCorte;
    private boolean dibujarImagenesBaseDatos;
    private int umbralMatch;
    
    
    public AlgoritmoFirmas(String ruta, String rutaImagen1, String rutaImagen2, boolean corte, boolean dibujarTodasImagenes, boolean dibujarImagenCorte, boolean dibujarImagenesBaseDatos, int umbralMatch) {
        this.rutaCarpetaImagenes = ruta;
        this.rutaImagen1 = rutaImagen1;
        this.rutaImagen2 = rutaImagen2;
        this.corte = corte;
        this.dibujarTodasImagenes = dibujarTodasImagenes;
        this.dibujarImagenCorte = dibujarImagenCorte;
        this.dibujarImagenesBaseDatos = dibujarImagenesBaseDatos;
        this.umbralMatch = umbralMatch;
    }

    public AlgoritmoFirmas(String ruta, boolean corte, boolean dibujarTodasImagenes, boolean dibujarImagenCorte, boolean dibujarImagenesBaseDatos, int umbralMatch) {
        this.rutaCarpetaImagenes = ruta;
        this.corte = corte;
        this.dibujarTodasImagenes = dibujarTodasImagenes;
        this.dibujarImagenCorte = dibujarImagenCorte;
        this.dibujarImagenesBaseDatos = dibujarImagenesBaseDatos;
        this.umbralMatch = umbralMatch;
    }

    public AlgoritmoFirmas(){};
    
    /**
     * @return the ruta
     */
    public String getRuta() {
        return rutaCarpetaImagenes;
    }

    /**
     * @param ruta the ruta to set
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
     * @param rutaImagen1 the rutaImagen1 to set
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
     * @param rutaImagen2 the rutaImagen2 to set
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
     * @param corte the corte to set
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
     * @param dibujarTodasImagenes the dibujarTodasImagenes to set
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
     * @param dibujarImagenCorte the dibujarImagenCorte to set
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
     * @param dibujarImagenesBaseDatos the dibujarImagenesBaseDatos to set
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
     * @param umbralMatch the umbralMatch to set
     */
    public void setUmbralMatch(int umbralMatch) {
        this.umbralMatch = umbralMatch;
    }

    public int[] procesar(boolean dibujarCorte,boolean  almacenarCorte,boolean dibujarPuntosCorte,boolean dibujarBaseDatos, boolean  dibujarPuntosBaseDatos,double umbral) {
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
        Resize redimensionar = new Resize(260,116);
        
        List<List<FastRetinaKeypoint>> listaPuntos = new ArrayList<List<FastRetinaKeypoint>>();
        Crop objetoCortador;
        FastCornersDetector fac = new FastCornersDetector(); //Inicio del algoritmo Fast
        FastRetinaKeypointDetector frdCorte = new FastRetinaKeypointDetector(fac); //Incio del algoritmo Freak
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
            //Apply the Bradley local threshold.
            BradleyLocalThreshold bradley4 = new BradleyLocalThreshold();
            bradley4.applyInPlace(imagenCortada);
             // Y = x
            //250 ancho
            //Coordenadas de la primera firma
            //Crop objetoCortar = new Crop(380,1508,250,115);
            if (cantidadPersonasProcesadas == 0) {
                objetoCortador = new Crop(inicioX + aumentoX, inicioY, 260, 116);
            } else {
                objetoCortador = new Crop(inicioX + aumentoX, inicioY + aumentoY, 260, 116);
            }

            objetoCortador.ApplyInPlace(imagenCortada);
            aumentoX += 140;

      
            
            if(dibujarCorte)
            JOptionPane.showMessageDialog(null, imagenCortada.toIcon(), "Resultado con Puntos", JOptionPane.PLAIN_MESSAGE);
            if(almacenarCorte)
            imagenCortada.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\corte1" + cantidadPersonasProcesadas + ".jpg");
            
            listaPrubeKeyPoints4Padron = new ArrayList<FastRetinaKeypoint>();
            listaPrubeKeyPoints4Padron = frdCorte.ProcessImage(imagenCortada);
            
            if(dibujarPuntosCorte)
            { 
            //SampleApplication.FastBitmap fb4 = new SampleApplication.FastBitmap("/home/wirox91/Escritorio/B- pc/Backup/Imagenes/2otsu.png");
            Firmas.FastBitmap fb4Dibujar = new Firmas.FastBitmap("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\corte1" + cantidadPersonasProcesadas + ".jpg");
            FastGraphics graficarPuntos2Corte = new FastGraphics(fb4Dibujar);
            //graficarPuntos2Corte.setColor(Color.Blue);     
            graficarPuntos2Corte.setColor(Color.Black);
            //Funcion 0
            for (int i = 0; i < listaPrubeKeyPoints4Padron.size(); i++) {
                int x1 = (int) listaPrubeKeyPoints4Padron.get(i).x;
                int y1 = (int) listaPrubeKeyPoints4Padron.get(i).y;
                graficarPuntos2Corte.DrawCircle(x1, y1, 2);
            }
            JOptionPane.showMessageDialog(null, fb4Dibujar.toIcon(), "Puntos en la imagen cortada", JOptionPane.PLAIN_MESSAGE);
            }
            
            

            cantidadPersonasProcesadas++;
            
            //Termina de procesar la imagen
            if (listaPrubeKeyPoints4Padron.size() == 0) {
                break;
            }
            listaPuntos.add(listaPrubeKeyPoints4Padron);
        }


        //Procesa las imagenes de la base de datos
        for (int i = 1; i < 59; i++) {
            if (i < 10) {
                firmasBaseDatos = new FastBitmap("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\Imagenes\\firmas.jpg\\f00" + i + ".jpg");
            } else {
                firmasBaseDatos = new FastBitmap("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\Imagenes\\firmas.jpg\\f0" + i + ".jpg");
            }
        redimensionar.ApplyInPlace(firmasBaseDatos);
        firmasBaseDatos.toRGB();
        firmasBaseDatos.toGrayscale();

        //Apply the Bradley local threshold.
        BradleyLocalThreshold bradleyBase = new BradleyLocalThreshold();
        bradleyBase.applyInPlace(firmasBaseDatos);

         if(dibujarBaseDatos) 
         JOptionPane.showMessageDialog(null, firmasBaseDatos.toIcon(), "Result: " + i, JOptionPane.PLAIN_MESSAGE);

                
        listaPrubeKeyPoints4PadronBaseDatos = frdBase.ProcessImage(firmasBaseDatos);
        if(dibujarPuntosBaseDatos)   
        {
            Firmas.FastBitmap fb4DibujarBase;
            if (i < 10) {
                fb4DibujarBase = new Firmas.FastBitmap("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\Imagenes\\firmas.jpg\\f00" + i + ".jpg");
            } else {
                fb4DibujarBase = new Firmas.FastBitmap("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\Imagenes\\firmas.jpg\\f0" + i + ".jpg");
            }

            FastGraphics graficarPuntos2Base = new FastGraphics(fb4DibujarBase);
            //graficarPuntos2Base.setColor(Color.Blue);     
            graficarPuntos2Base.setColor(Color.Black);

             //Funcion 0
            for (int ii = 0; ii < listaPrubeKeyPoints4PadronBaseDatos.size(); ii++) {
                int x1 = (int) listaPrubeKeyPoints4PadronBaseDatos.get(ii).x;
                int y1 = (int) listaPrubeKeyPoints4PadronBaseDatos.get(ii).y;
                graficarPuntos2Base.DrawCircle(x1, y1, 2);
            }

            JOptionPane.showMessageDialog(null, fb4DibujarBase.toIcon(), "Result con Puntos", JOptionPane.PLAIN_MESSAGE);
        }
            listaPuntosBaseDatos.add(listaPrubeKeyPoints4PadronBaseDatos);
        }
    
    
        
            //Matching entre las dos listas, 8
           for (int ki = 0; ki < listaPuntos.size(); ki++) {
               System.out.println("Turno de la figura "+(ki+1)+" :");
               System.out.println("----------------------------------------------------------------------------");
               List<Resultado> listaResultadosPorImagen = new ArrayList<Resultado>();
         for (int k = 0; k < listaPuntosBaseDatos.size(); k++) { //58
          
                 
             
 
            for(int i = 0; i < listaPuntos.get(ki).size(); i++){
       // distanciaMinima=0;
        indiceDistanciaMinima=-1;
        
       
                    
                
     
        for(j = 0; j < listaPuntosBaseDatos.get(k).size(); j++)
        {   if(listaPuntosBaseDatos.get(k).get(j).getIndexMatch()==-1){
            distancia = d.Hamming(listaPuntos.get(ki).get(i).toBinary(),listaPuntosBaseDatos.get(k).get(j).toBinary());
                    //System.out.println("d = "+distancia);
            if (distancia<=distanciaMinima){
                //distanciaMinima=distancia;
                indiceDistanciaMinima = j;
                listaPuntosBaseDatos.get(k).get(indiceDistanciaMinima).setIndexMatch(i);
                listaPuntos.get(ki).get(i).setIndexMatch(indiceDistanciaMinima);
                break;
            }
            }
        }
                  
       }

       for(int iii = 0; iii < listaPuntosBaseDatos.get(k).size(); iii++){
        if(listaPuntosBaseDatos.get(k).get(iii).getIndexMatch()!=-1){
                listaPuntosBaseDatos.get(k).get(iii).setIndexMatch(-1);
          }
    }
       
       
        
        
      count =0;
    for(int iii = 0; iii < listaPuntos.get(ki).size(); iii++){
        if(listaPuntos.get(ki).get(iii).getIndexMatch()!=-1){
            {    count++;
                listaPuntos.get(ki).get(iii).setIndexMatch(-1);
          }
    }
        
    }
    
        Resultado rc = new Resultado(count);
        listaResultadosPorImagen.add(rc);
    /*    System.out.println("Total matches, la fiugra "+k+" :"+count);
        System.out.println("----------------------------------"+ "");
        System.out.println("Total puntos figura1: "+listaPuntos.get(ki).size());
        System.out.println("Total puntos figura2: "+listaPuntosBaseDatos.get(k).size());
        System.out.println("");
   */
     }
         listaDistancia2Listas.add(listaResultadosPorImagen);

        
        
         }
           
    

           
           
           int [] personaNo;
           double [] distanciaPersonaNo;
           personaNo = new int[listaPuntos.size()];
           distanciaPersonaNo = new double[listaPuntos.size()];
         
          
            double mayor =-999999;
             int indiceMayor=0;
        for (int i = 0; i < listaDistancia2Listas.size(); i++) {
            mayor = -99999;
         
            
            indiceMayor=0;
            for (int k = 0; k < listaDistancia2Listas.get(i).size(); k++) {
                if(listaDistancia2Listas.get(i).get(k).indFirmaMatch>=mayor){
                  
                    mayor =listaDistancia2Listas.get(i).get(k).indFirmaMatch; 
                    indiceMayor=k;
                }
                
            }
            personaNo[i] = indiceMayor;
            distanciaPersonaNo[i] = mayor;
            //System.out.println("Match imagen corte: "+(i+1)+" y la de la base de datos: "+(indiceMayor+1)+" con puntos: "+mayor);
        }
        
        /*
        for (int i = 0; i < listaDistancia2Listas.size(); i++) {
            System.out.println("Figura: "+i+" ");
            System.out.println("----------------------------------");
            for (int k = 0; k < listaDistancia2Listas.get(i).size(); k++) {
                System.out.println(k+" ) "+listaDistancia2Listas.get(i).get(k).resul);
            }
        }
        */
        
        
        int []indiceNO;
        int cantN=0;
      
        for (int i = 0; i < personaNo.length; i++) {
            if(distanciaPersonaNo[i]<30)
            {
                        cantN++;
            }
                      
        }           
        indiceNO = new int[cantN];
     
        int m=0;
        for (int i = 0; i < personaNo.length; i++) {
            if(distanciaPersonaNo[i]<30)
            {
          
                indiceNO[m]=i;
                m++;
            }
                      
        }  
          
      
    return indiceNO;
    }

    
    

    void procesar2() throws IOException
    {	//double umbral=85;
    	double umbral=5;
    	int d1 = 0,d2=0;
    	
        Resize redimensionar = new Resize(260,116);
        FastCornersDetector fast = new FastCornersDetector(); //Inicio del algoritmo Fast
        
        List<List<Resultado>> arre = new ArrayList<List<Resultado>>();  
        ArrayList<Resultado> arr;
        
        
        for (int isuperior = 1; isuperior <2; isuperior++) {
			
		
        FastBitmap imagen1;
        FastBitmap imagen2;
        FastRetinaKeypointDetector freak1 = new FastRetinaKeypointDetector(fast); //Incio del algoritmo Freak
        FastRetinaKeypointDetector freak2 = new FastRetinaKeypointDetector(fast);
        List<FastRetinaKeypoint> descriptores1;
        List<FastRetinaKeypoint> descriptores2;
        Distance distancia = new Distance();
  // String url1 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\"+isuperior+"cccn.jpg");
  String url1 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\42r.jpg");
   //String url1 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\"+isuperior+"r.jpg");
        
        //String url1 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\c1"+isuperior+".jpg");
        OtsuThreshold o = new OtsuThreshold();
        
        imagen1 = new FastBitmap(url1);
        imagen1.toRGB();
        imagen1.toGrayscale();
        //BradleyLocalThreshold bradley4 = new BradleyLocalThreshold();
        //bradley4.applyInPlace(imagen1);
        o.applyInPlace(imagen1);
        //imagen1.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\imagen_primera_procesada.jpg");
        descriptores1 = new ArrayList<FastRetinaKeypoint>();
        descriptores1 = freak1.ProcessImage(imagen1);
         
        
        

  /*      
        Firmas.FastBitmap fb4DibujarBase;
        fb4DibujarBase = new Firmas.FastBitmap(url1);
        FastGraphics graficarPuntos2Base = new FastGraphics(fb4DibujarBase);
        //graficarPuntos2Base.setColor(Color.Blue);     
        graficarPuntos2Base.setColor(Color.Black);

         //Funcion 0
        for (int ii = 0; ii < descriptores1.size(); ii++) {
            int x1 = (int) descriptores1.get(ii).x;
            int y1 = (int) descriptores1.get(ii).y;
            graficarPuntos2Base.DrawCircle(x1, y1, 2);
        }

        JOptionPane.showMessageDialog(null, fb4DibujarBase.toIcon(), "Result con Puntos", JOptionPane.PLAIN_MESSAGE);
    */
        System.out.println("A) Figura ejemplo: "+url1);
        //System.out.println("Tamaño: "+descriptores1.size());
        //System.out.println("Tamaño 1: "+imagen1.getWidth()+" "+imagen1.getHeight());
        //System.out.println("Umbral: "+umbral);
        arr = new ArrayList<Resultado>();
        int ind;
       for (ind = 1; ind < 2; ind++) {
	
       //String url2 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\"+ind+".jpg");
     String url2 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\42.jpg");
      //String url2 = new String("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\nueva_imagen_redimensionada.jpg");
        	ind=42;
        //System.out.println("Figura: "+ind);
        System.out.println("Figura: "+url2);
        imagen2 = new FastBitmap(url2);
        //redimensionar.ApplyInPlace(imagen2);
        //System.out.println("Imagen 1: "+imagen1.getWidth()+" "+imagen1.getHeight());
        //System.out.println("Imagen 2: "+imagen2.getWidth()+" "+imagen2.getHeight());
        
        //Resize redimensionar2 = new Resize((imagen1.getWidth()),(imagen1.getHeight()),Algorithm.NEAREST_NEIGHBOR);
        //redimensionar2.ApplyInPlace(imagen2);
        //System.out.println("resize: "+imagen2.getWidth()+" "+imagen2.getHeight());
        
        
   
        //ProgressiveBilinearResizer pr = new ProgressiveBilinearResizer();
    	
        //BufferedImage aBufferedImage = null;

        //pr.resize(imagen2.toBufferedImage(),aBufferedImage);

        //imagen2 = new FastBitmap(aBufferedImage);
        
        //BufferedImage a = getScaledInstance(imagen2.toBufferedImage(),imagen1.getWidth(),imagen1.getHeight(),null,true);
        
        //Image a = resizeToBig(imagen2.toImage(),imagen1.getWidth(), imagen1.getHeight());
        
        //imagen2.setImage((BufferedImage) a);
        //

        
        
        //BufferedImage img = imagen2.toBufferedImage(); // load image
        //BufferedImage scaledImg = Scalr.resize(img, Method.BALANCED, 210, 95);       
        //imagen2 = new FastBitmap(scaledImg);

        
        //Resizers a = new Resizers() ;
        //Antialiasing an = null;
        //220 105
        
        
        /*
        BufferedImage bi = Thumbnails.of(new File(url2))
        .size(imagen1.getWidth(), imagen1.getHeight())
        .outputFormat("JPEG")
        .outputQuality(1)
        .resizer(Resizers.PROGRESSIVE)
        .antialiasing(Antialiasing.ON)
        .dithering(Dithering.ENABLE)
        //.scalingMode(ScalingMode.PROGRESSIVE_BILINEAR)
        .asBufferedImage();
         */
        
       
/*
    	BufferedImage bbi = new BufferedImage(210, 105,BufferedImage.TYPE_INT_RGB);
    	Graphics2D g = bbi.createGraphics();        
        //Graphics g = imagen2.getGraphics();
        Graphics2D g2 = (Graphics2D)g;
        RenderingHints rh = new RenderingHints(
                     RenderingHints.KEY_TEXT_ANTIALIASING,
                     RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setRenderingHints(rh);
              g2.drawImage(bbi, null, 0, 0);
*/
        
         
        /*
        BufferedImage dbi = null;
            dbi = new BufferedImage(210, 105,BufferedImage.TYPE_INT_RGB);
            Graphics2D g = dbi.createGraphics();
            AffineTransform at = AffineTransform.getScaleInstance(210, 105);
            g.drawRenderedImage(sbi, at);
        */
              
        
        /*
        
        Image ai = imagen2.toImage();
        Image rescaled = ai.getScaledInstance(210, 105, Image.SCALE_AREA_AVERAGING);


        	BufferedImage bbi = new BufferedImage(210, 105,BufferedImage.TYPE_INT_RGB);
        	Graphics2D g = bbi.createGraphics();
        	*/
        
        
      
            
        
        //.toFile(new File("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\thumbnail.jpg"));
       
        
        //Metodo en uso
        
        
        
        
        
        
     /*   
        if((imagen2.getWidth()>=300 || imagen2.getHeight()>=300)||(imagen1.getWidth()>=320 || imagen1.getHeight()>=320)){
        //System.out.println("todos");
        BufferedImage img = imagen2.toBufferedImage(); // load image 220 105
       BufferedImage scaledImg = Scalr.resize(img, Method.AUTOMATIC, imagen1.getWidth(), imagen1.getHeight(),Scalr.OP_BRIGHTER);       
       // BufferedImage scaledImg = Scalr.resize(img, Method.BALANCED, 300, 156,Scalr.OP_BRIGHTER);       
//        BufferedImage scaledImg = Scalr.resize(img, Method.BALANCED, imagen1.getWidth(), imagen2.getHeight(),Scalr.OP_ANTIALIAS);       
        imagen2 = new FastBitmap(scaledImg);
        }
        imagen2.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\"+ind+"nuevoresize.jpg");
       */ 
        
        
        
        
        if(ind==36 || ind==37 || ind==38 || ind==39 || ind ==40 || ind==41 || ind==41 ||ind==42 ||ind==43){
        
        	if((imagen2.getWidth()>=300 || imagen2.getHeight()>=300)||(imagen1.getWidth()>=320 || imagen1.getHeight()>=320)){	
        		
        		BufferedImage bi = Thumbnails.of(new File("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\"+ind+".jpg"))
        .size(imagen1.getWidth(), imagen1.getHeight())
        .outputFormat("JPG")
        .outputQuality(1)
        .resizer(Resizers.PROGRESSIVE)
        .asBufferedImage();
       
        imagen2 = new FastBitmap(bi);
        }
        }
        else
        {
        
        	BufferedImage img = imagen2.toBufferedImage(); // load image 220 105
            BufferedImage scaledImg = Scalr.resize(img, Method.AUTOMATIC, imagen1.getWidth(), imagen1.getHeight(),Scalr.OP_BRIGHTER);       
            // BufferedImage scaledImg = Scalr.resize(img, Method.BALANCED, 300, 156,Scalr.OP_BRIGHTER);       
//             BufferedImage scaledImg = Scalr.resize(img, Method.BALANCED, imagen1.getWidth(), imagen2.getHeight(),Scalr.OP_ANTIALIAS);       
             imagen2 = new FastBitmap(scaledImg);
        	
        }
        
        //imagen2.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\37Thumbnails.jpg");
        
        
        
      //  BufferedImage scaledImg = Scalr.resize(img, Method.QUALITY, 
        //        150, 100, Scalr.OP_ANTIALIAS);
        
        //imagen2.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\nueva_metodo.jpg");

        //imagen2 = new FastBitmap(bi);
        //imagen2.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\nueva_metodo.jpg");
        imagen2.toRGB();
        imagen2.toGrayscale();
        //BradleyLocalThreshold bradleyBase = new BradleyLocalThreshold();
        //bradleyBase.applyInPlace(imagen2);
        //redimensionar2.ApplyInPlace(imagen2);
        o.applyInPlace(imagen2);
        //BradleyLocalThreshold bradleyBase = new BradleyLocalThreshold();
        //bradleyBase.applyInPlace(imagen2);
        
        //imagen2.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\nueva_imagen_redimensionada.jpg");

        descriptores2 = new ArrayList<FastRetinaKeypoint>();
        descriptores2 = freak2.ProcessImage(imagen2);
        //System.out.println("Tamaño de 2: "+imagen2.getWidth()+" "+imagen2.getHeight());
        //System.out.println("tamaño 2:"+descriptores2.size());  
        //System.out.println();
      System.out.println(descriptores1.size());
      System.out.println(descriptores2.size());
        

/*        
        Firmas.FastBitmap fb4DibujarBase2;
        fb4DibujarBase2 = new Firmas.FastBitmap(url2);
        FastGraphics graficarPuntos2Base2 = new FastGraphics(fb4DibujarBase2);
        //graficarPuntos2Base.setColor(Color.Blue);     
        graficarPuntos2Base.setColor(Color.Black);

        
        //Funcion 0
        for (int ii = 0; ii < descriptores2.size(); ii++) {
            int x1 = (int) descriptores2.get(ii).x;
            int y1 = (int) descriptores2.get(ii).y;
            graficarPuntos2Base2.DrawCircle(x1, y1, 2);
        }

        
        JOptionPane.showMessageDialog(null, fb4DibujarBase2.toIcon(), "Result con Puntos", JOptionPane.PLAIN_MESSAGE);
*/    
        
        
        int contadorMatching=0;
        
        double distanciaResultado;
        double porcentaje = 0;
        
        /*
        for (int i = 0; i < descriptores1.size(); i++) {
		  
       	 for (int j = 0; j < descriptores2.size(); j++) {
       		 distanciaResultado=distancia.Hamming(descriptores1.get(i).toBinary(), descriptores2.get(j).toBinary());
       		 //System.out.println(distanciaResultado);
       		 if(distanciaResultado<=umbral)
       			 contadorMatching=contadorMatching+1;
       		 	 break;
        	 }	
		}
        */
  
        
        Distance d = new Distance();
        int j;
        double distancia1;
        int distanciaMinima = 45; //41 default 45 y 20 jpg ,  no puede subir mas , por ahora, DEFAULT = 45
        //Funcion 3, 20
        for(int i = 0; i < descriptores1.size(); i++){
           // distanciaMinima=0;
            int indiceDistanciaMinima=-1;
            for(j = 0; j < descriptores2.size(); j++)
            {  
            	if(descriptores1.get(i).primerosBits(descriptores2.get(j))>=14){ //20 no falla 34, 5 DEFAULT ,
            		//15, falla 14
            		//10, falla 10
            		//5, falla 10
            		//14 , falla 12 en total
            		
            	if(descriptores2.get(j).getIndexMatch()==-1){
            	
            
                distancia1 = d.Hamming(descriptores1.get(i).toBinary(),descriptores2.get(j).toBinary());
                       // System.out.println("d = "+distancia1);
                if (distancia1<=distanciaMinima){
                    //distanciaMinima=distancia;
                    indiceDistanciaMinima = j;
                    descriptores2.get(indiceDistanciaMinima).setIndexMatch(i);
                    break;
                }
                
                }
            	}
            }
        descriptores1.get(i).setIndexMatch(indiceDistanciaMinima);
         }
         
        
        
       
        
        
        
        int c=0;
        for (int i = 0; i < descriptores1.size(); i++) {
			if(descriptores1.get(i).getIndexMatch()!=-1)
				c++;
		}
        int cor=0;
        //System.out.println("Similitud: "+contadorMatching);
        System.out.println("Similitud: "+c);
        //System.out.println(descriptores1.get(0).toBinary());
        //System.out.println(descriptores2.get(0).toBinary());
       
        if(descriptores1.size()>0){
         //porcentaje de 1 imagen
        porcentaje = ((c*100)/descriptores1.size());
        //porcentaje de 2 imagen
        //porcentaje = ((c*100)/descriptores2.size());
        System.out.println("Porcentaje: "+porcentaje);
        }
        //28, 20
        //55, ok
        //20 , ok
        //10 pp ok
        if (porcentaje>=35){ //20 defualt;   
        	//esta entre 34 y 20
        	Resultado r1 = new Resultado(ind);
        	r1.porcentaje = porcentaje;
            arr.add(r1);
        		
        	
        	System.out.println("Ejemplo "+url1);
        	System.out.println("Esta es la imagen conincide:(1xImagen) "+url2);
        	System.out.println("------------------------------------------------------------------");
        	}
        System.out.println();
        d2=descriptores2.get(0).getDescriptor().length;
       }
        for (int i = 0; i < descriptores1.size(); i++) {
			if(descriptores1.get(i).getIndexMatch()!=-1)
				descriptores1.get(i).setIndexMatch(-1);
		}
        arre.add(arr);
        d1=descriptores1.get(0).getDescriptor().length;
        
        }
        

        int kk=0;
        System.out.println("Lista:");
        for (int i = 0; i < arre.size(); i++) {
        	System.out.println("i :"+(i+1));
        	if (arre.get(i).size()>=2 || arre.get(i).size()==0) kk++;
        	for (int k = 0; k < arre.get(i).size(); k++) {
				
				System.out.println(arre.get(i).get(k).indFirmaMatch+"  p: "+arre.get(i).get(k).porcentaje);
			}
        	System.out.println();
		}
       System.out.println("Fallas: "+kk);
       System.out.println(" "+d1+" "+d2);
    }

    
    
    
    
    
    
    
    public List<Integer> verificarFirmas1(List<Integer> idPersonas,List<Integer> idFirmas,String direccionPersonaPorPlanillon, String direccionBaseDatosFirmas) throws IOException
    {
    	ArrayList<Integer> listaFirmasAutentificadas = new ArrayList<Integer>();
        FastCornersDetector fastDetector = new FastCornersDetector(); 
        List<List<Resultado>> listaTemporalPersona = new ArrayList<List<Resultado>>();  
        ArrayList<Resultado> idFirmasValidadas;
        int indicePersona;
        int indiceBaseDatos;
        double distanciaMinimaHamming = 45; 
        
        for (indicePersona = 1; indicePersona < idFirmas.size(); indicePersona++)
	        {
	        FastBitmap imagen1;
	        FastBitmap imagen2;
	        FastRetinaKeypointDetector freak1 = new FastRetinaKeypointDetector(fastDetector); 
	        FastRetinaKeypointDetector freak2 = new FastRetinaKeypointDetector(fastDetector);
	        List<FastRetinaKeypoint> descriptores1;
	        List<FastRetinaKeypoint> descriptores2;
	        Distance distancia = new Distance();
	        String url1 = new String(direccionPersonaPorPlanillon+"\\Persona"+idPersonas.get(indicePersona)+"\\Firma\\firma.jpg");        												
	        OtsuThreshold o = new OtsuThreshold();
	        imagen1 = new FastBitmap(url1);
	        imagen1.toRGB();
	        imagen1.toGrayscale();
	        //BradleyLocalThreshold bradley4 = new BradleyLocalThreshold();
	        //bradley4.applyInPlace(imagen1);
	        o.applyInPlace(imagen1);
	        //imagen1.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\imagen_primera_procesada.jpg");
	        descriptores1 = new ArrayList<FastRetinaKeypoint>();
	        descriptores1 = freak1.ProcessImage(imagen1);
	
	        idFirmasValidadas = new ArrayList<Resultado>();
	        
	        for (indiceBaseDatos = 1; indiceBaseDatos < 59; indiceBaseDatos++) 
	        {
	        	String url2 = null;
		        if(indiceBaseDatos<10)
		        	url2 = new String(direccionBaseDatosFirmas+"\\f00"+indiceBaseDatos+".jpg");
		        else
		        	url2 = new String(direccionBaseDatosFirmas+"\\f0"+indiceBaseDatos+".jpg");
		        
		        imagen2 = new FastBitmap(url2);
		        Distance d = new Distance();
		        double porcentaje = 0;
		        double distanciaResultado;
		        int puntosSimilares=0;
		        int j;
		        if(indiceBaseDatos==36 || indiceBaseDatos==37 || indiceBaseDatos==38 || indiceBaseDatos==39 || indiceBaseDatos ==40 || indiceBaseDatos==41 || indiceBaseDatos==41 ||indiceBaseDatos==42 ||indiceBaseDatos==43){
		        
		        	if((imagen2.getWidth()>=300 || imagen2.getHeight()>=300)||(imagen1.getWidth()>=320 || imagen1.getHeight()>=320)){	
		       
		        		BufferedImage bufferTmp = Thumbnails.of(new File(url2+"\\f0"+indiceBaseDatos+".jpg"))
		        .size(imagen1.getWidth(), imagen1.getHeight())
		        .outputFormat("JPG")
		        .outputQuality(1)
		        .resizer(Resizers.PROGRESSIVE)
		        .asBufferedImage();
		       
		        imagen2 = new FastBitmap(bufferTmp);
		        }
		        }
		        else
		        {
		        	BufferedImage bufferTmp = imagen2.toBufferedImage(); 
		            BufferedImage imagenRedimensionada = Scalr.resize(bufferTmp, Method.AUTOMATIC, imagen1.getWidth(), imagen1.getHeight(),Scalr.OP_BRIGHTER);       
		            imagen2 = new FastBitmap(imagenRedimensionada);	
		        }
		        
		        imagen2.toRGB();
		        imagen2.toGrayscale();
		        //BradleyLocalThreshold bradleyBase = new BradleyLocalThreshold();
		        //bradleyBase.applyInPlace(imagen2);
		        //redimensionar2.ApplyInPlace(imagen2);
		        o.applyInPlace(imagen2);
		        //BradleyLocalThreshold bradleyBase = new BradleyLocalThreshold();
		        //bradleyBase.applyInPlace(imagen2);
		        
		        descriptores2 = new ArrayList<FastRetinaKeypoint>();
		        descriptores2 = freak2.ProcessImage(imagen2);
		         
		        for(int i = 0; i < descriptores1.size(); i++)
		        {
		            int indiceDistanciaMinima=-1;
		            for(j = 0; j < descriptores2.size(); j++)
		            {  
		            	if(descriptores1.get(i).primerosBits(descriptores2.get(j))>=14)
		            	{    		
			            	if(descriptores2.get(j).getIndexMatch()==-1){
			            		distanciaResultado = d.Hamming(descriptores1.get(i).toBinary(),descriptores2.get(j).toBinary());
				                if (distanciaResultado<=distanciaMinimaHamming)
				                {
				                    indiceDistanciaMinima = j;
				                    descriptores2.get(indiceDistanciaMinima).setIndexMatch(i);
				                    break;
				                }
			                
			                }
		            	}
		            }
		        descriptores1.get(i).setIndexMatch(indiceDistanciaMinima);
		         }
		         
		        for (int i = 0; i < descriptores1.size(); i++) {
					if(descriptores1.get(i).getIndexMatch()!=-1)
						puntosSimilares++;
				}
		
		        if(descriptores1.size()>0)
		        {
		        	porcentaje = ((puntosSimilares*100)/descriptores1.size());
		        }
		        else
		        {
		        	porcentaje = 0;
		        }
		        
		        if (porcentaje>=35)
		        { 
		        	Resultado datosResultadoTemporal = new Resultado(indiceBaseDatos);
		        	datosResultadoTemporal.porcentaje = porcentaje;
		            idFirmasValidadas.add(datosResultadoTemporal);
		        }
	       }
	        
	       for (int i = 0; i < descriptores1.size(); i++) 
	       {
				if(descriptores1.get(i).getIndexMatch()!=-1)
					descriptores1.get(i).setIndexMatch(-1);
	       }
	       
	       listaTemporalPersona.add(idFirmasValidadas);
        }
        
        for (int i = 0; i < listaTemporalPersona.size(); i++) {	
        	for (int k = 0; k < listaTemporalPersona.get(i).size(); k++) {
				listaFirmasAutentificadas.add((int)listaTemporalPersona.get(i).get(k).indFirmaMatch);
			}
		}    	
    	  	
    	return listaFirmasAutentificadas;
    }
   
    
    
    
    public List<List<Resultado>> verificarFirmas2(List<Integer> idPersonas,List<Integer> idFirmas,String direccionPersonaPorPlanillon, String direccionBaseDatosFirmas) throws IOException
    {
    	ArrayList<Integer> listaFirmasAutentificadas = new ArrayList<Integer>();
        FastCornersDetector fastDetector = new FastCornersDetector(); 
        List<List<Resultado>> listaTemporalPersona = new ArrayList<List<Resultado>>();  
        ArrayList<Resultado> idFirmasValidadas;
        int indicePersona;
        int indiceBaseDatos;
        double distanciaMinimaHamming = 45; 
        
        for (indicePersona = 0; indicePersona < idFirmas.size(); indicePersona++)
	        {
	        FastBitmap imagen1;
	        FastBitmap imagen2;
	        FastRetinaKeypointDetector freak1 = new FastRetinaKeypointDetector(fastDetector); 
	        FastRetinaKeypointDetector freak2 = new FastRetinaKeypointDetector(fastDetector);
	        List<FastRetinaKeypoint> descriptores1;
	        List<FastRetinaKeypoint> descriptores2;
	        Distance distancia = new Distance();
	        String url1 = new String(direccionPersonaPorPlanillon+"\\Persona"+idPersonas.get(indicePersona)+"\\Firma\\firma.jpg");        												
	        OtsuThreshold o = new OtsuThreshold();
	        imagen1 = new FastBitmap(url1);
	        imagen1.toRGB();
	        imagen1.toGrayscale();
	        //BradleyLocalThreshold bradley4 = new BradleyLocalThreshold();
	        //bradley4.applyInPlace(imagen1);
	        o.applyInPlace(imagen1);
	        //imagen1.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\imagen_primera_procesada.jpg");
	        descriptores1 = new ArrayList<FastRetinaKeypoint>();
	        descriptores1 = freak1.ProcessImage(imagen1);
	
	        idFirmasValidadas = new ArrayList<Resultado>();
	        
	        for (indiceBaseDatos = 1; indiceBaseDatos < 59; indiceBaseDatos++) 
	        {
	        	String url2 = null;
		        if(indiceBaseDatos<10)
		        	url2 = new String(direccionBaseDatosFirmas+"\\f00"+indiceBaseDatos+".jpg");
		        else
		        	url2 = new String(direccionBaseDatosFirmas+"\\f0"+indiceBaseDatos+".jpg");
		        
		        imagen2 = new FastBitmap(url2);
		        Distance d = new Distance();
		        double porcentaje = 0;
		        double distanciaResultado;
		        int puntosSimilares=0;
		        int j;
		        if(indiceBaseDatos==36 || indiceBaseDatos==37 || indiceBaseDatos==38 || indiceBaseDatos==39 || indiceBaseDatos ==40 || indiceBaseDatos==41 || indiceBaseDatos==41 ||indiceBaseDatos==42 ||indiceBaseDatos==43){
		        
		        	if((imagen2.getWidth()>=300 || imagen2.getHeight()>=300)||(imagen1.getWidth()>=320 || imagen1.getHeight()>=320))
		        	{	
		        		BufferedImage bufferTmp = Thumbnails.of(new File(url2+"\\f0"+indiceBaseDatos+".jpg"))
		        				.size(imagen1.getWidth(), imagen1.getHeight())
		        				.outputFormat("JPG")
		        				.outputQuality(1)
		        				.resizer(Resizers.PROGRESSIVE)
		        				.asBufferedImage();
		        		imagen2 = new FastBitmap(bufferTmp);
		        }
		        }
		        else
		        {
		        	BufferedImage bufferTmp = imagen2.toBufferedImage(); 
		            BufferedImage imagenRedimensionada = Scalr.resize(bufferTmp, Method.AUTOMATIC, imagen1.getWidth(), imagen1.getHeight(),Scalr.OP_BRIGHTER);       
		            imagen2 = new FastBitmap(imagenRedimensionada);	
		        }
		        
		        imagen2.toRGB();
		        imagen2.toGrayscale();
		        //BradleyLocalThreshold bradleyBase = new BradleyLocalThreshold();
		        //bradleyBase.applyInPlace(imagen2);
		        //redimensionar2.ApplyInPlace(imagen2);
		        o.applyInPlace(imagen2);
		        //BradleyLocalThreshold bradleyBase = new BradleyLocalThreshold();
		        //bradleyBase.applyInPlace(imagen2);
		        
		        descriptores2 = new ArrayList<FastRetinaKeypoint>();
		        descriptores2 = freak2.ProcessImage(imagen2);
		         
		        for(int i = 0; i < descriptores1.size(); i++)
		        {
		            int indiceDistanciaMinima=-1;
		            for(j = 0; j < descriptores2.size(); j++)
		            {  
		            	if(descriptores1.get(i).primerosBits(descriptores2.get(j))>=14)
		            	{    		
			            	if(descriptores2.get(j).getIndexMatch()==-1){
			            		distanciaResultado = d.Hamming(descriptores1.get(i).toBinary(),descriptores2.get(j).toBinary());
				                if (distanciaResultado<=distanciaMinimaHamming)
				                {
				                    indiceDistanciaMinima = j;
				                    descriptores2.get(indiceDistanciaMinima).setIndexMatch(i);
				                    break;
				                }
			                
			                }
		            	}
		            }
		        descriptores1.get(i).setIndexMatch(indiceDistanciaMinima);
		         }
		         
		        for (int i = 0; i < descriptores1.size(); i++) {
					if(descriptores1.get(i).getIndexMatch()!=-1)
						puntosSimilares++;
				}
		
		        if(descriptores1.size()>0)
		        {
		        	porcentaje = ((puntosSimilares*100)/descriptores1.size());
		        }
		        else
		        {
		        	porcentaje = 0;
		        }
		        
		        if (porcentaje>=35)
		        { 
		        	Resultado datosResultadoTemporal = new Resultado(indiceBaseDatos);
		        	datosResultadoTemporal.porcentaje = porcentaje;
		            idFirmasValidadas.add(datosResultadoTemporal);
		        }
	       }
	        
	       for (int i = 0; i < descriptores1.size(); i++) 
	       {
				if(descriptores1.get(i).getIndexMatch()!=-1)
					descriptores1.get(i).setIndexMatch(-1);
	       }
	       
	       listaTemporalPersona.add(idFirmasValidadas);
        }
        
    	return listaTemporalPersona;
    }
    
    
    
    /*
    public ArrayList<Resultado> verificarFirmas4(List<Integer> idPersonas,List<Integer> idFirmas,String direccionPersonaPorPlanillon, String direccionBaseDatosFirmas) throws IOException
    {
    	ArrayList<Resultado> listaFirmasAutentificadas = new ArrayList<Resultado>();
        FastCornersDetector fastDetector = new FastCornersDetector(); 
        List<List<Resultado>> listaTemporalPersona = new ArrayList<List<Resultado>>();  
        ArrayList<Resultado> idFirmasValidadas;
        int indicePersona;
        int indiceBaseDatos;
        double distanciaMinimaHamming = 45; 
        
        for (indicePersona = 1; indicePersona < idFirmas.size(); indicePersona++)
	        {
	        FastBitmap imagen1;
	        FastBitmap imagen2;
	        FastRetinaKeypointDetector freak1 = new FastRetinaKeypointDetector(fastDetector); 
	        FastRetinaKeypointDetector freak2 = new FastRetinaKeypointDetector(fastDetector);
	        List<FastRetinaKeypoint> descriptores1;
	        List<FastRetinaKeypoint> descriptores2;
	        Distance distancia = new Distance();
	        String url1 = new String(direccionPersonaPorPlanillon+"\\Persona"+idPersonas.get(indicePersona)+"\\Firma\\firma.jpg");        												
	        OtsuThreshold o = new OtsuThreshold();
	        imagen1 = new FastBitmap(url1);
	        imagen1.toRGB();
	        imagen1.toGrayscale();
	        //BradleyLocalThreshold bradley4 = new BradleyLocalThreshold();
	        //bradley4.applyInPlace(imagen1);
	        o.applyInPlace(imagen1);
	        //imagen1.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\imagen_primera_procesada.jpg");
	        descriptores1 = new ArrayList<FastRetinaKeypoint>();
	        descriptores1 = freak1.ProcessImage(imagen1);
	
	        idFirmasValidadas = new ArrayList<Resultado>();
	        
	        for (indiceBaseDatos = 1; indiceBaseDatos < 59; indiceBaseDatos++) 
	        {
	        	String url2 = null;
		        if(indiceBaseDatos<10)
		        	url2 = new String(direccionBaseDatosFirmas+"\\f00"+indiceBaseDatos+".jpg");
		        else
		        	url2 = new String(direccionBaseDatosFirmas+"\\f0"+indiceBaseDatos+".jpg");
		        
		        imagen2 = new FastBitmap(url2);
		        Distance d = new Distance();
		        double porcentaje = 0;
		        double distanciaResultado;
		        int puntosSimilares=0;
		        int j;
		        if(indiceBaseDatos==36 || indiceBaseDatos==37 || indiceBaseDatos==38 || indiceBaseDatos==39 || indiceBaseDatos ==40 || indiceBaseDatos==41 || indiceBaseDatos==41 ||indiceBaseDatos==42 ||indiceBaseDatos==43){
		        
		        	if((imagen2.getWidth()>=300 || imagen2.getHeight()>=300)||(imagen1.getWidth()>=320 || imagen1.getHeight()>=320)){	
		       
		        		BufferedImage bufferTmp = Thumbnails.of(new File(url2+"\\f0"+indiceBaseDatos+".jpg"))
		        .size(imagen1.getWidth(), imagen1.getHeight())
		        .outputFormat("JPG")
		        .outputQuality(1)
		        .resizer(Resizers.PROGRESSIVE)
		        .asBufferedImage();
		       
		        imagen2 = new FastBitmap(bufferTmp);
		        }
		        }
		        else
		        {
		        	BufferedImage bufferTmp = imagen2.toBufferedImage(); 
		            BufferedImage imagenRedimensionada = Scalr.resize(bufferTmp, Method.AUTOMATIC, imagen1.getWidth(), imagen1.getHeight(),Scalr.OP_BRIGHTER);       
		            imagen2 = new FastBitmap(imagenRedimensionada);	
		        }
		        
		        imagen2.toRGB();
		        imagen2.toGrayscale();
		        //BradleyLocalThreshold bradleyBase = new BradleyLocalThreshold();
		        //bradleyBase.applyInPlace(imagen2);
		        //redimensionar2.ApplyInPlace(imagen2);
		        o.applyInPlace(imagen2);
		        //BradleyLocalThreshold bradleyBase = new BradleyLocalThreshold();
		        //bradleyBase.applyInPlace(imagen2);
		        
		        descriptores2 = new ArrayList<FastRetinaKeypoint>();
		        descriptores2 = freak2.ProcessImage(imagen2);
		         
		        for(int i = 0; i < descriptores1.size(); i++)
		        {
		            int indiceDistanciaMinima=-1;
		            for(j = 0; j < descriptores2.size(); j++)
		            {  
		            	if(descriptores1.get(i).primerosBits(descriptores2.get(j))>=14)
		            	{    		
			            	if(descriptores2.get(j).getIndexMatch()==-1){
			            		distanciaResultado = d.Hamming(descriptores1.get(i).toBinary(),descriptores2.get(j).toBinary());
				                if (distanciaResultado<=distanciaMinimaHamming)
				                {
				                    indiceDistanciaMinima = j;
				                    descriptores2.get(indiceDistanciaMinima).setIndexMatch(i);
				                    break;
				                }
			                
			                }
		            	}
		            }
		        descriptores1.get(i).setIndexMatch(indiceDistanciaMinima);
		         }
		         
		        for (int i = 0; i < descriptores1.size(); i++) {
					if(descriptores1.get(i).getIndexMatch()!=-1)
						puntosSimilares++;
				}
		
		        if(descriptores1.size()>0)
		        {
		        	porcentaje = ((puntosSimilares*100)/descriptores1.size());
		        }
		        else
		        {
		        	porcentaje = 0;
		        }
		        
		        if (porcentaje>=35)
		        { 
		        	Resultado datosResultadoTemporal = new Resultado(indiceBaseDatos);
		        	datosResultadoTemporal.porcentaje = porcentaje;
		            idFirmasValidadas.add(datosResultadoTemporal);
		        }
	       }
	        
	       for (int i = 0; i < descriptores1.size(); i++) 
	       {
				if(descriptores1.get(i).getIndexMatch()!=-1)
					descriptores1.get(i).setIndexMatch(-1);
	       }
	       
	       listaTemporalPersona.add(idFirmasValidadas);
        }
        
        for (int i = 0; i < listaTemporalPersona.size(); i++) {	
        	//System.out.println("1");
        	//if (listaTemporalPersona.get(i).size()>=1){
        		listaFirmasAutentificadas.add(listaTemporalPersona.get(i).get(0));
	//	}    	
    	  	
    	
    }
        return listaFirmasAutentificadas;
		
   
  }
   */
    
    
    public List<Resultado> verificarFirmas4(List<Integer> idPersonas,List<Integer> idFirmas,String direccionPersonaPorPlanillon, String direccionBaseDatosFirmas) throws IOException
    {
    	ArrayList<Resultado> listaFirmasAutentificadas = new ArrayList<Resultado>();
        FastCornersDetector fastDetector = new FastCornersDetector(); 
        List<List<Resultado>> listaTemporalPersona = new ArrayList<List<Resultado>>();  
        ArrayList<Resultado> idFirmasValidadas;
        int indicePersona;
        int indiceBaseDatos;
        double distanciaMinimaHamming = 45; 
        
        for (indicePersona = 0; indicePersona < idFirmas.size(); indicePersona++)
	        {
	        FastBitmap imagen1;
	        FastBitmap imagen2;
	        FastRetinaKeypointDetector freak1 = new FastRetinaKeypointDetector(fastDetector); 
	        FastRetinaKeypointDetector freak2 = new FastRetinaKeypointDetector(fastDetector);
	        List<FastRetinaKeypoint> descriptores1;
	        List<FastRetinaKeypoint> descriptores2;
	        Distance distancia = new Distance();
	        String url1 = new String(direccionPersonaPorPlanillon+"\\Persona"+idPersonas.get(indicePersona)+"\\Firma\\firma.jpg");        												
	        OtsuThreshold o = new OtsuThreshold();
	        imagen1 = new FastBitmap(url1);
	        imagen1.toRGB();
	        imagen1.toGrayscale();
	        //BradleyLocalThreshold bradley4 = new BradleyLocalThreshold();
	        //bradley4.applyInPlace(imagen1);
	        o.applyInPlace(imagen1);
	        //imagen1.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\imagen_primera_procesada.jpg");
	        descriptores1 = new ArrayList<FastRetinaKeypoint>();
	        descriptores1 = freak1.ProcessImage(imagen1);
	
	        idFirmasValidadas = new ArrayList<Resultado>();
	        
	        for (indiceBaseDatos = 1; indiceBaseDatos < 59; indiceBaseDatos++) 
	        {
	        	String url2 = null;
		        if(indiceBaseDatos<10)
		        	url2 = new String(direccionBaseDatosFirmas+"\\f00"+indiceBaseDatos+".jpg");
		        else
		        	url2 = new String(direccionBaseDatosFirmas+"\\f0"+indiceBaseDatos+".jpg");
		        
		        imagen2 = new FastBitmap(url2);
		        Distance d = new Distance();
		        double porcentaje = 0;
		        double distanciaResultado;
		        int puntosSimilares=0;
		        int j;
		        if(indiceBaseDatos==36 || indiceBaseDatos==37 || indiceBaseDatos==38 || indiceBaseDatos==39 || indiceBaseDatos ==40 || indiceBaseDatos==41 || indiceBaseDatos==41 ||indiceBaseDatos==42 ||indiceBaseDatos==43){
		        
		        	if((imagen2.getWidth()>=300 || imagen2.getHeight()>=300)||(imagen1.getWidth()>=320 || imagen1.getHeight()>=320))
		        	{	
		        		BufferedImage bufferTmp = Thumbnails.of(new File(url2+"\\f0"+indiceBaseDatos+".jpg"))
		        				.size(imagen1.getWidth(), imagen1.getHeight())
		        				.outputFormat("JPG")
		        				.outputQuality(1)
		        				.resizer(Resizers.PROGRESSIVE)
		        				.asBufferedImage();
		        		imagen2 = new FastBitmap(bufferTmp);
		        }
		        }
		        else
		        {
		        	BufferedImage bufferTmp = imagen2.toBufferedImage(); 
		            BufferedImage imagenRedimensionada = Scalr.resize(bufferTmp, Method.AUTOMATIC, imagen1.getWidth(), imagen1.getHeight(),Scalr.OP_BRIGHTER);       
		            imagen2 = new FastBitmap(imagenRedimensionada);	
		        }
		        
		        imagen2.toRGB();
		        imagen2.toGrayscale();
		        //BradleyLocalThreshold bradleyBase = new BradleyLocalThreshold();
		        //bradleyBase.applyInPlace(imagen2);
		        //redimensionar2.ApplyInPlace(imagen2);
		        o.applyInPlace(imagen2);
		        //BradleyLocalThreshold bradleyBase = new BradleyLocalThreshold();
		        //bradleyBase.applyInPlace(imagen2);
		        
		        descriptores2 = new ArrayList<FastRetinaKeypoint>();
		        descriptores2 = freak2.ProcessImage(imagen2);
		         
		        for(int i = 0; i < descriptores1.size(); i++)
		        {
		            int indiceDistanciaMinima=-1;
		            for(j = 0; j < descriptores2.size(); j++)
		            {  
		            	if(descriptores1.get(i).primerosBits(descriptores2.get(j))>=14)
		            	{    		
			            	if(descriptores2.get(j).getIndexMatch()==-1){
			            		distanciaResultado = d.Hamming(descriptores1.get(i).toBinary(),descriptores2.get(j).toBinary());
				                if (distanciaResultado<=distanciaMinimaHamming)
				                {
				                    indiceDistanciaMinima = j;
				                    descriptores2.get(indiceDistanciaMinima).setIndexMatch(i);
				                    break;
				                }
			                
			                }
		            	}
		            }
		        descriptores1.get(i).setIndexMatch(indiceDistanciaMinima);
		         }
		         
		        for (int i = 0; i < descriptores1.size(); i++) {
					if(descriptores1.get(i).getIndexMatch()!=-1)
						puntosSimilares++;
				}
		
		        if(descriptores1.size()>0)
		        {
		        	porcentaje = ((puntosSimilares*100)/descriptores1.size());
		        }
		        else
		        {
		        	porcentaje = 0;
		        }
		        
		        if (porcentaje>=35)
		        { 
		        	Resultado datosResultadoTemporal = new Resultado(indiceBaseDatos);
		        	datosResultadoTemporal.porcentaje = porcentaje;
		            idFirmasValidadas.add(datosResultadoTemporal);
		        }
	       }
	        
	       for (int i = 0; i < descriptores1.size(); i++) 
	       {
				if(descriptores1.get(i).getIndexMatch()!=-1)
					descriptores1.get(i).setIndexMatch(-1);
	       }
	       
	       listaTemporalPersona.add(idFirmasValidadas);
        }
        
        
        for (int i = 0; i < listaTemporalPersona.size(); i++){ 
        //{	
       
        	if (listaTemporalPersona.get(i).size()>=1){
        		listaFirmasAutentificadas.add(listaTemporalPersona.get(i).get(0));
        	}  
        }
        
    	return listaFirmasAutentificadas;
    }
    
    
    public List<Resultado> verificarFirmas5(List<Integer> idPersonas,List<Integer> idFirmas,String direccionPersonaPorPlanillon, String direccionBaseDatosFirmas) throws IOException
    {
    	ArrayList<Resultado> listaFirmasAutentificadas = new ArrayList<Resultado>();
        FastCornersDetector fastDetector = new FastCornersDetector(); 
        List<List<Resultado>> listaTemporalPersona = new ArrayList<List<Resultado>>();  
        ArrayList<Resultado> idFirmasValidadas;
        int indicePersona;
        int indiceBaseDatos;
        double distanciaMinimaHamming = 45; 
        
        for (indicePersona = 0; indicePersona < idPersonas.size(); indicePersona++)
	        {
	        FastBitmap imagen1;
	        FastBitmap imagen2;
	        FastRetinaKeypointDetector freak1 = new FastRetinaKeypointDetector(fastDetector); 
	        FastRetinaKeypointDetector freak2 = new FastRetinaKeypointDetector(fastDetector);
	        List<FastRetinaKeypoint> descriptores1;
	        List<FastRetinaKeypoint> descriptores2;
	        Distance distancia = new Distance();
	        String url1 = new String(direccionPersonaPorPlanillon+"\\Persona"+idPersonas.get(indicePersona)+"\\Firma\\firma.jpg");        												
	        OtsuThreshold o = new OtsuThreshold();
	        imagen1 = new FastBitmap(url1);
	        imagen1.toRGB();
	        imagen1.toGrayscale();
	        //BradleyLocalThreshold bradley4 = new BradleyLocalThreshold();
	        //bradley4.applyInPlace(imagen1);
	        o.applyInPlace(imagen1);
	        //imagen1.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\imagen_primera_procesada.jpg");
	        descriptores1 = new ArrayList<FastRetinaKeypoint>();
	        descriptores1 = freak1.ProcessImage(imagen1);
	
	        idFirmasValidadas = new ArrayList<Resultado>();
	        
	        for (indiceBaseDatos = 1; indiceBaseDatos < 59; indiceBaseDatos++) 
	        {
	        	String url2 = null;
		        if(indiceBaseDatos<10)
		        	url2 = new String(direccionBaseDatosFirmas+"\\f00"+indiceBaseDatos+".jpg");
		        else
		        	url2 = new String(direccionBaseDatosFirmas+"\\f0"+indiceBaseDatos+".jpg");
		        
		        imagen2 = new FastBitmap(url2);
		        Distance d = new Distance();
		        double porcentaje = 0;
		        double distanciaResultado;
		        int puntosSimilares=0;
		        int j;
		    
		        //  if(indiceBaseDatos==36 || indiceBaseDatos==37 || indiceBaseDatos==38 || indiceBaseDatos==39 || indiceBaseDatos ==40 || indiceBaseDatos==41 || indiceBaseDatos==41 ||indiceBaseDatos==42 ||indiceBaseDatos==43)
		        if(imagen2.getWidth()<=272 && imagen2.getHeight()<=134)
		        {
		        	if((imagen2.getWidth()>=300 || imagen2.getHeight()>=300)||(imagen1.getWidth()>=320 || imagen1.getHeight()>=320))
		        	{	
		        		BufferedImage bufferTmp = Thumbnails.of(new File(url2+"\\f0"+indiceBaseDatos+".jpg"))
		        				.size(imagen1.getWidth(), imagen1.getHeight())
		        				.outputFormat("JPG")
		        				.outputQuality(1)
		        				.resizer(Resizers.PROGRESSIVE)
		        				.asBufferedImage();
		        		imagen2 = new FastBitmap(bufferTmp);
		        }
		        }
		        else
		        {
		        	BufferedImage bufferTmp = imagen2.toBufferedImage(); 
		            BufferedImage imagenRedimensionada = Scalr.resize(bufferTmp, Method.AUTOMATIC, imagen1.getWidth(), imagen1.getHeight(),Scalr.OP_BRIGHTER);       
		            imagen2 = new FastBitmap(imagenRedimensionada);	
		        }
		        
		        
		        
		        imagen2.toRGB();
		        imagen2.toGrayscale();
		        //BradleyLocalThreshold bradleyBase = new BradleyLocalThreshold();
		        //bradleyBase.applyInPlace(imagen2);
		        //redimensionar2.ApplyInPlace(imagen2);
		        o.applyInPlace(imagen2);
		        //BradleyLocalThreshold bradleyBase = new BradleyLocalThreshold();
		        //bradleyBase.applyInPlace(imagen2);
		        
		        descriptores2 = new ArrayList<FastRetinaKeypoint>();
		        descriptores2 = freak2.ProcessImage(imagen2);
		         
		        for(int i = 0; i < descriptores1.size(); i++)
		        {
		            int indiceDistanciaMinima=-1;
		            for(j = 0; j < descriptores2.size(); j++)
		            {  
		            	if(descriptores1.get(i).primerosBits(descriptores2.get(j))>=14)
		            	{    		
			            	if(descriptores2.get(j).getIndexMatch()==-1){
			            		distanciaResultado = d.Hamming(descriptores1.get(i).toBinary(),descriptores2.get(j).toBinary());
				                if (distanciaResultado<=distanciaMinimaHamming)
				                {
				                    indiceDistanciaMinima = j;
				                    descriptores2.get(indiceDistanciaMinima).setIndexMatch(i);
				                    break;
				                }
			                
			                }
		            	}
		            }
		        descriptores1.get(i).setIndexMatch(indiceDistanciaMinima);
		         }
		         
		        for (int i = 0; i < descriptores1.size(); i++) {
					if(descriptores1.get(i).getIndexMatch()!=-1)
						puntosSimilares++;
				}
		
		        if(descriptores1.size()>0)
		        {
		        	porcentaje = ((puntosSimilares*100)/descriptores1.size());
		        }
		        else
		        {
		        	porcentaje = 0;
		        }
		        
		        if (porcentaje>=35)
		        { 
		        	Resultado datosResultadoTemporal = new Resultado(indiceBaseDatos);
		        	datosResultadoTemporal.porcentaje = porcentaje;
		        	datosResultadoTemporal.idPersona = indicePersona;
		        	idFirmasValidadas.add(datosResultadoTemporal);
		        	//System.out.println("Firma: "+indiceBaseDatos+" Ancho:"+imagen2.getWidth()+" Alto:"+imagen2.getHeight());
		        }
	       }
	        
	       for (int i = 0; i < descriptores1.size(); i++) 
	       {
				if(descriptores1.get(i).getIndexMatch()!=-1)
					descriptores1.get(i).setIndexMatch(-1);
	       }
	       
	       listaTemporalPersona.add(idFirmasValidadas);
        }
        
        
        for (int i = 0; i < listaTemporalPersona.size(); i++){ 
        //{	
       
        	if (listaTemporalPersona.get(i).size()>=1){
        		listaFirmasAutentificadas.add(listaTemporalPersona.get(i).get(0));
        	}  
        }
        
    	return listaFirmasAutentificadas;
    }
       
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
 
    
}
    

    
    



