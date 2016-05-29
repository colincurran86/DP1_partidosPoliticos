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
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import Catalano.Imaging.Filters.Crop;
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
                if(listaDistancia2Listas.get(i).get(k).resul>=mayor){
                  
                    mayor =listaDistancia2Listas.get(i).get(k).resul; 
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

}
