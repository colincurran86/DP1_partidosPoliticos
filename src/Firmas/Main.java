package Firmas;

import Catalano.Imaging.Corners.ICornersDetector;
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
 * Demonstration.
 * @author Diego Catalano
 */

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<FeaturePoint> l;
        List<FastRetinaKeypoint> lf;
        FastCornersDetector fac = new FastCornersDetector();
        //Load an image
        //FastBitmap fb = new FastBitmap("/home/wirox91/Escritorio/B- pc/Backup/Imagenes/1otsu.png");
        FastBitmap fb = new FastBitmap("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\Imagenes\\16.jpg");
        //FastBitmap fb = new FastBitmap("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\corte1.jpg");
        //Convert to RGB space.
        fb.toRGB();
        //Datos prueba, antes de los datos
           //Resize redimensionar = new Resize(190,100);
        //Datos para la prueba
        Resize redimensionar = new Resize(260,116);
        redimensionar.ApplyInPlace(fb);
    
        //Display the original image.
        //JOptionPane.showMessageDialog(null, fb.toIcon(), "Original image", JOptionPane.PLAIN_MESSAGE);
        
        //Convert to Grayscale space.
        fb.toGrayscale();
        
        //Apply the Bradley local threshold.
        BradleyLocalThreshold bradley = new BradleyLocalThreshold();
        bradley.applyInPlace(fb);
        
        
        //Display the result.
        JOptionPane.showMessageDialog(null, fb.toIcon(), "Result", JOptionPane.PLAIN_MESSAGE);
         l = fac.ProcessImage(fb);
         //lf = 
         //fac.ProcessImage(fb);
         
         for(int i = 0; i < l.size(); i++) {
            System.out.println(l.get(i).x+" "+l.get(i).y);
        }
         System.out.println("Size = "+l.size());
        
         
         ArrayList<FastRetinaKeypoint> af = new ArrayList<FastRetinaKeypoint>(); //Arreglos de puntos fast ha freak
         List<FastRetinaKeypoint> lista = new ArrayList<FastRetinaKeypoint>();
         
         for(int i = 0; i < l.size(); i++) {
             FastRetinaKeypoint nuevo = new FastRetinaKeypoint(l.get(i).x,l.get(i).y);
             lista.add(nuevo);
             //System.out.println(l.get(i).x+" "+l.get(i).y);
        }
         //Imprimir puntos, double
        // for(int i = 0; i < af.size(); i++) {
        //    System.out.println(af.get(i).x+" "+af.get(i).y);
       // }
         
         //OK - hastaca, bien

        FastRetinaKeypointPattern fkp = new FastRetinaKeypointPattern(8,1);
        IntegralImage Iimage = new IntegralImage(fb);
        
        
        //public interface ICornersDetector 
        //ICornersDetector myBox = new FastCornersDetector(); 
        FastRetinaKeypointDescriptor fDescrip = new FastRetinaKeypointDescriptor(fb,Iimage,fkp);         
        fDescrip.toString();
        //List<FastRetinaKeypoint> lf;
        //List<FastRetinaKeypoint> lista = new ArrayList<String>();
        
        fDescrip.Compute(af);
       
        fDescrip.toString();
  
        
        //Detector con puntos de fac (fast)
        FastRetinaKeypointDetector frd = new FastRetinaKeypointDetector(fac);
        List<FastRetinaKeypoint> g;
        g = frd.ProcessImage(fb);
        
         
        for(int i = 0; i < g.size(); i++) {
            System.out.println(g.get(i).x+" "+g.get(i).y);
        }
        
       //SampleApplication.FastBitmap fb2 = new SampleApplication.FastBitmap("/home/wirox91/Escritorio/B- pc/Backup/Imagenes/1otsu.png");
      Firmas.FastBitmap fb2 = new Firmas.FastBitmap("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\Imagenes\\16.jpg");
     
     
     //SampleApplication.FastBitmap fb2 = new SampleApplication.FastBitmap("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\corte1.jpg");     
     FastGraphics graficarPuntos = new FastGraphics(fb2);
     //graficarPuntos.setColor(Color.Blue);
     graficarPuntos.setColor(Color.Black);
        System.out.println("Puntos ha dibujar");
          JOptionPane.showMessageDialog(null, fb2.toIcon(), "Result sin Puntos", JOptionPane.PLAIN_MESSAGE);
        
          
        
        //Funcion 0 dibujar todos los puntos
          for(int i = 0; i < l.size(); i++) {
              graficarPuntos.DrawCircle(l.get(i).x, l.get(i).y, 2);
        }
        
          
          
        //Funcion 1 dibujar 1 solo punto, 1 por uno
          //: 33 144
          //Puntos: 34 134
          //Puntos: 35 38
          //Puntos: 36 42
     /*SampleApplication.FastBitmap fb22;
          for(int i = 0; i < l.size(); i++) {
            fb22= new SampleApplication.FastBitmap("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\Imagenes\\1otsu.png");            
               FastGraphics graficarPuntosPorUno = new FastGraphics(fb22);
               graficarPuntosPorUno.setColor(Color.Blue);
              graficarPuntosPorUno.DrawCircle(l.get(i).x, l.get(i).y, 2);
              JOptionPane.showMessageDialog(null, fb22.toIcon(), "Result con Puntos", JOptionPane.PLAIN_MESSAGE);
              System.out.println("Puntos: "+l.get(i).x+" "+l.get(i).y);
        }
      */
          
          
          /*
          //Funcion 2 dibujar un 4 puntos dados
          SampleApplication.FastBitmap fb22;
          for(int i = 0; i < l.size(); i++) {
              if((l.get(i).x==33 && l.get(i).y==144)||(l.get(i).x==34 && l.get(i).y==134)||(l.get(i).x==35 && l.get(i).y==38)
                      ||(l.get(i).x==36 && l.get(i).y==42)){
            fb22= new SampleApplication.FastBitmap("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\Imagenes\\1otsu.png");            
               FastGraphics graficarPuntosPorUno = new FastGraphics(fb22);
               graficarPuntosPorUno.setColor(Color.Blue);
              graficarPuntosPorUno.DrawCircle(l.get(i).x, l.get(i).y, 2);
              JOptionPane.showMessageDialog(null, fb22.toIcon(), "Result con Puntos", JOptionPane.PLAIN_MESSAGE);
              System.out.println("Puntos: "+l.get(i).x+" "+l.get(i).y);
              }
          }
          */
          
          /*
          //Funcion 3 , dibujar Punto ha probar 35, 38
                    SampleApplication.FastBitmap fb22;
          for(int i = 0; i < l.size(); i++) {
              if(l.get(i).x==35 && l.get(i).y==38){
            fb22= new SampleApplication.FastBitmap("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\Imagenes\\1otsu.png");            
               FastGraphics graficarPuntosPorUno = new FastGraphics(fb22);
               graficarPuntosPorUno.setColor(Color.Blue);
              graficarPuntosPorUno.DrawCircle(l.get(i).x, l.get(i).y, 2);
              JOptionPane.showMessageDialog(null, fb22.toIcon(), "Result con Puntos", JOptionPane.PLAIN_MESSAGE);
              System.out.println("Puntos: "+l.get(i).x+" "+l.get(i).y+" Indice: "+i);
              }
          }
         */
          
          
         JOptionPane.showMessageDialog(null, fb2.toIcon(), "Result con Puntos", JOptionPane.PLAIN_MESSAGE);
         //l = fac.ProcessImage(fb);
    
             List<FastRetinaKeypoint> listaPrubeKeyPoints = new ArrayList<FastRetinaKeypoint>();
            listaPrubeKeyPoints = frd.ProcessImage(fb);
            for(int i = 0; i < listaPrubeKeyPoints.size(); i++) {
               System.out.println(listaPrubeKeyPoints.get(i).x+" "+listaPrubeKeyPoints.get(i).y+" "+listaPrubeKeyPoints.get(i).getDescriptor().length);
            
               for(int j = 0; j < listaPrubeKeyPoints.get(i).getDescriptor().length; j++) 
                    System.out.print(listaPrubeKeyPoints.get(i).getDescriptor()[j]+" ");
                System.out.println("");
    }
                        for(int i = 0; i < listaPrubeKeyPoints.size(); i++) 
               System.out.println(listaPrubeKeyPoints.get(i).x+" "+listaPrubeKeyPoints.get(i).y+" "+listaPrubeKeyPoints.get(i).toBinary());
           
                        for(int i = 0; i < listaPrubeKeyPoints.size(); i++) 
               System.out.println(listaPrubeKeyPoints.get(i).x+" "+listaPrubeKeyPoints.get(i).y+" "+listaPrubeKeyPoints.get(i).toHex());
                        
                     for(int i = 0; i < listaPrubeKeyPoints.size(); i++) 
               System.out.println(listaPrubeKeyPoints.get(i).x+" "+listaPrubeKeyPoints.get(i).y+" "+listaPrubeKeyPoints.get(i).toString());   
    
   
    Distance d = new Distance();
    //HammingDistance hd = new HammingDistance();
    //System.out.println(d.Hamming(listaPrubeKeyPoints.get(0).toBinary(),listaPrubeKeyPoints.get(1).toBinary()));
    
    List<FeaturePoint> l3;
    
    
    
    
    
    
    //FastBitmap fb3 = new FastBitmap("/home/wirox91/Escritorio/B- pc/Backup/Imagenes/2otsu.png");
    //FastBitmap fb3 = new FastBitmap("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\Imagenes\\2otsu.png");
    //Imagen pequeÃ±a
    //FastBitmap fb3 = new FastBitmap("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\Imagenes\\4.png");
     //FastBitmap fb3 = new FastBitmap("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\Imagenes\\5.jpg");
    //Imagen similar
     FastBitmap fb3 = new FastBitmap("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\Imagenes\\6"
             + ".jpg");
    //FastBitmap fb3 = new FastBitmap("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\Imagenes\\6.jpg");
    // Sin redimensionar 79 la q es y la q no es; y las que son  sale 61
    //Firma que es 34 con la que no es 19 en 75
    
   // Resize redimensionar = new Resize(180,120);
   redimensionar.ApplyInPlace(fb3);
        //Convert to RGB space.
        fb3.toRGB();
        
        //Display the original image.
        //JOptionPane.showMessageDialog(null, fb.toIcon(), "Original image", JOptionPane.PLAIN_MESSAGE);
        
        //Convert to Grayscale space.
        fb3.toGrayscale();
        BradleyLocalThreshold bradley2 = new BradleyLocalThreshold();
        bradley2.applyInPlace(fb3);
    
    
        //Display the result.
        JOptionPane.showMessageDialog(null, fb3.toIcon(), "Result 2 Otsu", JOptionPane.PLAIN_MESSAGE);
         l3 = fac.ProcessImage(fb3);
    
    
           for(int i = 0; i < l3.size(); i++) {
            System.out.println(l3.get(i).x+" "+l3.get(i).y);
        }
         System.out.println("Size = "+l3.size());
         
        FastRetinaKeypointDetector frd2 = new FastRetinaKeypointDetector(fac);
        List<FastRetinaKeypoint> g2;
        g2 = frd2.ProcessImage(fb3);

        
    //SampleApplication.FastBitmap fb4 = new SampleApplication.FastBitmap("/home/wirox91/Escritorio/B- pc/Backup/Imagenes/2otsu.png");
    //SampleApplication.FastBitmap fb4 = new SampleApplication.FastBitmap("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\Imagenes\\2otsu.png"); 
    //SampleApplication.FastBitmap fb4 = new SampleApplication.FastBitmap("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\Imagenes\\4.png");  
    //SampleApplication.FastBitmap fb4 = new SampleApplication.FastBitmap("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\Imagenes\\5.jpg");
        //Imagen similar
       Firmas.FastBitmap fb4 = new Firmas.FastBitmap("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\Imagenes\\6.jpg");
       //SampleApplication.FastBitmap fb4 = new SampleApplication.FastBitmap("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\Imagenes\\6.jpg");
       //redimensionar.ApplyInPlace(fb4);
     
        
            // fb4.toRGB();


        
        
        
        
        FastGraphics graficarPuntos2 = new FastGraphics(fb4);
     
         System.out.println("Puntos ha dibujar");
     //graficarPuntos2.setColor(Color.Blue);     
  graficarPuntos2.setColor(Color.Black);     
          //JOptionPane.showMessageDialog(null, fb4.toIcon(), "Result sin Puntos", JOptionPane.PLAIN_MESSAGE);
        
          
     
     //Funcion 0
/*        
     for(int i = 0; i < l3.size(); i++) {
              graficarPuntos2.DrawCircle(l3.get(i).x, l3.get(i).y, 2);
        }
  */    
       
           //Funcion 1 dibujar 1 solo punto, 1 por uno
          //: 33 144
          //Puntos: 34 134
          //Puntos: 35 38
          //Puntos: 36 42
     
     //Funcion 1; punto por punto
     /*
     SampleApplication.FastBitmap fb22;
          for(int i = 0; i < l3.size(); i++) {
            fb22= new SampleApplication.FastBitmap("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\Imagenes\\2otsu.png");            
               FastGraphics graficarPuntosPorUno = new FastGraphics(fb22);
               graficarPuntosPorUno.setColor(Color.Blue);
              graficarPuntosPorUno.DrawCircle(l3.get(i).x, l3.get(i).y, 2);
              JOptionPane.showMessageDialog(null, fb22.toIcon(), "Result con Puntos", JOptionPane.PLAIN_MESSAGE);
              System.out.println("Puntos: "+l3.get(i).x+" "+l3.get(i).y);
        }
      */
           
           
               /*     
          //Funcion 3 , dibujar Punto ha probar 36, 64
                    SampleApplication.FastBitmap fb22;
          for(int i = 0; i < l3.size(); i++) {
              if(l3.get(i).x==36 && l3.get(i).y==64){
            fb22= new SampleApplication.FastBitmap("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\Imagenes\\2otsu.png");            
               FastGraphics graficarPuntosPorUno = new FastGraphics(fb22);
               graficarPuntosPorUno.setColor(Color.Blue);
              graficarPuntosPorUno.DrawCircle(l3.get(i).x, l3.get(i).y, 2);
              JOptionPane.showMessageDialog(null, fb22.toIcon(), "Result con Puntos", JOptionPane.PLAIN_MESSAGE);
              System.out.println("Puntos: "+l3.get(i).x+" "+l3.get(i).y+" Indice: "+i);
              }
          }
          */
           
         JOptionPane.showMessageDialog(null, fb4.toIcon(), "Result 2otsu con Puntos", JOptionPane.PLAIN_MESSAGE); 
    List<FastRetinaKeypoint> listaPrubeKeyPoints2 = new ArrayList<FastRetinaKeypoint>();
            listaPrubeKeyPoints2 = frd2.ProcessImage(fb3);
    
            for(int i = 0; i < listaPrubeKeyPoints2.size(); i++) 
               System.out.println(listaPrubeKeyPoints2.get(i).x+" "+listaPrubeKeyPoints2.get(i).y+" "+listaPrubeKeyPoints2.get(i).toBinary());
            
        System.out.println("Espacio lista 1 = "+listaPrubeKeyPoints.size()+" Espacio 2 lista = "+listaPrubeKeyPoints2.size());        
    System.out.println(d.Hamming(listaPrubeKeyPoints.get(0).toBinary(),listaPrubeKeyPoints2.get(0).toBinary()));
        
    
    //MEJORAR LEER MÃ�S SOBRE HAMMING DISTANCE Y LOS PARÃ�METROS QUE SE VAN HA USAR
    
    
    double total=0;
    //for(int i = 0; i < listaPrubeKeyPoints2.size(); i++) 
    //total = total +d.Hamming(listaPrubeKeyPoints.get(i).toBinary(),listaPrubeKeyPoints2.get(i).toBinary());
 
          System.out.println("total = "+total);
    double distancia=0;
    //75 da buenos resultados solo falla en el 8 posiblemente por el tipo de corte y el 4 que se empareja con la figura 42 mientras el 8 con la 23
    //double distanciaMinima=75;
    
    //Falla 1 ; el 4 con la figura 42 creo q esta mal cortado, figura 7 normalmente
    //double distanciaMinima=70;
    
    //Con esto si sale, los 8; 80 falla 1
    //65 , falla 7 
    //60, falla 7
    //55, falla 6 y 7
    double distanciaMinima=60;       
    
    //Falta probar con un umbrla de match mas grande = 150 o 200
    
    
    
    
    
    
    
    
    
    // 30 de distancia Minima = 0 matches
    // 50 =2
    // 55 = 8
    // 60 = 16 //Esta bien
    // 65 = 20
    // 70 = 24
    // 75 = 38 //Subio demasiado
    // 80 = 46 //Sigue subiendo
    // 85 = 53 //Aumento menos
    // 90 = 71 //Subio demasiado
    // 95 = 91 //Subio demasiado
    // 100 = 98 //Subio poco
    // 105 = 116 //Subio poco
    
    
    // 110 = 133 //Subio bastante
    
    
    //45 minimo entre 2 imagenes usando funcion 1 solo da 1 match
    // Total puntos figura 1 = 256
    // Total puntos figura 2 = 230
    
    int indiceDistanciaMinima=0;
    int j;
    
    
    /*
    //Funcion 1
    int indiceDistanciaMinima1,indiceDistanciaMinima2;
    indiceDistanciaMinima2=-1;
    indiceDistanciaMinima1=-1;
    //Hallar distancia mÃ­nimia entre todos los puntos
    distanciaMinima=9999999;
    for(int i = 0; i < listaPrubeKeyPoints.size(); i++){
        //distanciaMinima=0;
        //indiceDistanciaMinima=-1;
        for(j = 0; j < listaPrubeKeyPoints2.size(); j++)
        {   //if(listaPrubeKeyPoints2.get(j).getIndexMatch()==-1){
            distancia = d.Hamming(listaPrubeKeyPoints.get(i).toBinary(),listaPrubeKeyPoints2.get(j).toBinary());
            if (distancia<distanciaMinima){
                distanciaMinima=distancia;
                indiceDistanciaMinima1 = i;
                indiceDistanciaMinima2 = j;
            }
            //}
        }
  // listaPrubeKeyPoints.get(i).setIndexMatch(indiceDistanciaMinima);
   //listaPrubeKeyPoints2.get(indiceDistanciaMinima).setIndexMatch(i);
     }
        System.out.println("Primer punto: "+indiceDistanciaMinima1);
        System.out.println("Segundo punto: "+indiceDistanciaMinima2);
        System.out.println("Distancia minima: "+distanciaMinima);
    */
    
    
    
    /*
    //Funcion 2
    for(int i = 0; i < listaPrubeKeyPoints.size(); i++){
        distanciaMinima=0;
        indiceDistanciaMinima=-1;
        for(j = 0; j < listaPrubeKeyPoints2.size(); j++)
        {   if(listaPrubeKeyPoints2.get(j).getIndexMatch()==-1){
            distancia = d.Hamming(listaPrubeKeyPoints.get(i).toBinary(),listaPrubeKeyPoints2.get(j).toBinary());
            if (distancia<distanciaMinima){
                distanciaMinima=distancia;
                indiceDistanciaMinima = j;
            }
            }
        }
   listaPrubeKeyPoints.get(i).setIndexMatch(indiceDistanciaMinima);
   listaPrubeKeyPoints2.get(indiceDistanciaMinima).setIndexMatch(i);
     }
    int count =0;
    for(int i = 0; i < listaPrubeKeyPoints.size(); i++){
        if(listaPrubeKeyPoints.get(i).getIndexMatch()!=-1)
            count++;
    }
        System.out.println("Total matches:"+count);
    */
    
    
    
   
    //Funcion 3
    for(int i = 0; i < listaPrubeKeyPoints.size(); i++){
       // distanciaMinima=0;
        indiceDistanciaMinima=-1;
        for(j = 0; j < listaPrubeKeyPoints2.size(); j++)
        {   if(listaPrubeKeyPoints2.get(j).getIndexMatch()==-1){
            distancia = d.Hamming(listaPrubeKeyPoints.get(i).toBinary(),listaPrubeKeyPoints2.get(j).toBinary());
                    System.out.println("d = "+distancia);
            if (distancia<=distanciaMinima){
                //distanciaMinima=distancia;
                indiceDistanciaMinima = j;
                listaPrubeKeyPoints2.get(indiceDistanciaMinima).setIndexMatch(i);
                break;
            }
            }
        }
   listaPrubeKeyPoints.get(i).setIndexMatch(indiceDistanciaMinima);
   
     }
    int count =0;
    for(int i = 0; i < listaPrubeKeyPoints.size(); i++){
        if(listaPrubeKeyPoints.get(i).getIndexMatch()!=-1)
            count++;
    }
        System.out.println("Total matches:"+count);
        System.out.println("Total puntos figura 1: "+listaPrubeKeyPoints.size());
        System.out.println("Total puntos figura 2: "+listaPrubeKeyPoints2.size());
        System.out.println("Match entre 35 38 y 36 64: "+d.Hamming(listaPrubeKeyPoints.get(39).toBinary(),listaPrubeKeyPoints2.get(41).toBinary()));
        //Match = 190, de hamming distance
        System.out.println("P1 : "+listaPrubeKeyPoints.get(39).x+" "+listaPrubeKeyPoints.get(39).y);
        System.out.println("P1 : "+listaPrubeKeyPoints2.get(41).x+" "+listaPrubeKeyPoints2.get(41).y);
    
   for(int i = 0; i < listaPrubeKeyPoints.size(); i++){
     if(listaPrubeKeyPoints.get(i).x==35 && listaPrubeKeyPoints.get(i).y==38)
           System.out.println("Indice: "+i);
    }
   
   for(int i = 0; i < listaPrubeKeyPoints2.size(); i++){
     if(listaPrubeKeyPoints2.get(i).x==36 && listaPrubeKeyPoints2.get(i).y==64)
           System.out.println("Indice: "+i);
    }
        System.out.println("Punto descritor: "+listaPrubeKeyPoints.get(0).getDescriptor().length);
   
   
        
        
   // Funcion 0; cortar una sola firma
   /*
   // Imagen de un padron electoral
   FastBitmap fbCortar = new FastBitmap("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\padron.blanco.firmado.jpg");

        Rotate rotarImage = new Rotate(90.0, Rotate.Algorithm.BILINEAR);
        rotarImage.applyInPlace(fbCortar);
        rotarImage.applyInPlace(fbCortar);
        rotarImage.applyInPlace(fbCortar);
        JOptionPane.showMessageDialog(null, fbCortar.toIcon(), "Result 2otsu con Puntos", JOptionPane.PLAIN_MESSAGE);
   //Convert to RGB space.
        fbCortar.toRGB();
        
        //Display the original image.
        //JOptionPane.showMessageDialog(null, fb.toIcon(), "Original image", JOptionPane.PLAIN_MESSAGE);
        
        //Convert to Grayscale space.
        fbCortar.toGrayscale();
        
        //Apply the Bradley local threshold.
        BradleyLocalThreshold bradley4 = new BradleyLocalThreshold();
        bradley4.applyInPlace(fbCortar);     
   
        // Y = x
        //250 ancho
        Crop objetoCortar = new Crop(380,1508,250,115);
        objetoCortar.ApplyInPlace(fbCortar);     
        
        
       // fbCortar.toRGB();
        
        //Display the original image.
        //JOptionPane.showMessageDialog(null, fb.toIcon(), "Original image", JOptionPane.PLAIN_MESSAGE);
        
        //Convert to Grayscale space.
        //fbCortar.toGrayscale();
        JOptionPane.showMessageDialog(null, fbCortar.toIcon(), "Result 2otsu con Puntos", JOptionPane.PLAIN_MESSAGE);
        fbCortar.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\corte1.jpg");
*/     
 
        
        
        
        
        
    //Funcion 1; cortar todas las firmas
   FastBitmap fbCortar; 
   // Imagen de un padron electoral
   
   int x=380;
   int y=1508;
   int v=0;
   int aumento=0;
   int aumentoY=7;
   List<List<FastRetinaKeypoint>> listaPuntos = new ArrayList<List<FastRetinaKeypoint>>();
   Crop objetoCortar;
   while(true){
    fbCortar= new FastBitmap("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\padron.blanco.firmado.jpg");

        Rotate rotarImage = new Rotate(90.0, Rotate.Algorithm.BILINEAR);
        rotarImage.applyInPlace(fbCortar);
        rotarImage.applyInPlace(fbCortar);
        rotarImage.applyInPlace(fbCortar);
        JOptionPane.showMessageDialog(null, fbCortar.toIcon(), "Result 2otsu con Puntos", JOptionPane.PLAIN_MESSAGE);
   //Convert to RGB space.
       
        fbCortar.toRGB();
        
        //Display the original image.
        //JOptionPane.showMessageDialog(null, fb.toIcon(), "Original image", JOptionPane.PLAIN_MESSAGE);
        
        //Convert to Grayscale space.
        fbCortar.toGrayscale();
        
        //Apply the Bradley local threshold.
        BradleyLocalThreshold bradley4 = new BradleyLocalThreshold();
        bradley4.applyInPlace(fbCortar);     
   
        // Y = x
        //250 ancho
        //Coordenadas de la primera firma
        //Crop objetoCortar = new Crop(380,1508,250,115);
        if(v==0)
        objetoCortar = new Crop(x+aumento,y,260,116);
        else
        objetoCortar = new Crop(x+aumento,y+aumentoY,260,116);
        
        objetoCortar.ApplyInPlace(fbCortar);     
        aumento += 140;
       
        /*
        if(v==1)
            aumento = aumento + 310;
        if(v==2)
            aumento = aumento + 200;
        */
       // fbCortar.toRGB();
        
        //Display the original image.
        //JOptionPane.showMessageDialog(null, fb.toIcon(), "Original image", JOptionPane.PLAIN_MESSAGE);
        
        //Convert to Grayscale space.
        //fbCortar.toGrayscale();
       
        JOptionPane.showMessageDialog(null, fbCortar.toIcon(), "Result 2otsu con Puntos", JOptionPane.PLAIN_MESSAGE);
        fbCortar.saveAsJPG("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\corte1"+v+".jpg");
 
        
        FastRetinaKeypointDetector frdCorte = new FastRetinaKeypointDetector(fac);
        List<FastRetinaKeypoint> gCorte;
        gCorte = frdCorte.ProcessImage(fbCortar);

        
     //SampleApplication.FastBitmap fb4 = new SampleApplication.FastBitmap("/home/wirox91/Escritorio/B- pc/Backup/Imagenes/2otsu.png");
    Firmas.FastBitmap fb4Dibujar = new Firmas.FastBitmap("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\corte1"+v+".jpg"); 
    FastGraphics graficarPuntos2Corte = new FastGraphics(fb4Dibujar);
     
         System.out.println("Puntos ha dibujar");
     //graficarPuntos2Corte.setColor(Color.Blue);     
     graficarPuntos2Corte.setColor(Color.Black);     
     //Funcion 0
         
     for(int i = 0; i < gCorte.size(); i++) {
        int x1 = (int)gCorte.get(i).x;
        int y1 = (int)gCorte.get(i).y;
         graficarPuntos2Corte.DrawCircle(x1,y1, 2);
        }
        
    JOptionPane.showMessageDialog(null, fb4Dibujar.toIcon(), "Result con Puntos", JOptionPane.PLAIN_MESSAGE); 
    List<FastRetinaKeypoint> listaPrubeKeyPoints4Padron = new ArrayList<FastRetinaKeypoint>();
    listaPrubeKeyPoints4Padron = frdCorte.ProcessImage(fbCortar);
     
        
    //for(int i = 0; i < listaPrubeKeyPoints2.size(); i++) 
        //System.out.println(listaPrubeKeyPoints2.get(i).x+" "+listaPrubeKeyPoints2.get(i).y+" "+listaPrubeKeyPoints2.get(i).toBinary());
            
        System.out.println("Espacio lista de puntos = "+listaPrubeKeyPoints4Padron.size());               
        v++;
        if(listaPrubeKeyPoints4Padron.size()==0) break;
        listaPuntos.add(listaPrubeKeyPoints4Padron);
   }
   
        System.out.println("Total de lista de puntos"+listaPuntos.size());
        for (int i = 0; i < listaPuntos.size(); i++) {
            System.out.println("Total de la lista "+(i+1)+" : "+listaPuntos.get(i).size());
        }
                
        
        
       
       
     
        
   List<List<FastRetinaKeypoint>> listaPuntosBaseDatos = new ArrayList<List<FastRetinaKeypoint>>();        
   List<List<Resultado>> listaDistancia2Listas = new ArrayList<List<Resultado>>();     
   
        FastBitmap firmasBaseDatos;
        for (int i = 1; i < 59; i++) {
            if (i<10)
            firmasBaseDatos  = new FastBitmap("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\Imagenes\\firmas.jpg\\f00"+i+".jpg");
        else
            firmasBaseDatos  = new FastBitmap("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\Imagenes\\firmas.jpg\\f0"+i+".jpg");     
       //
            
            
            
            redimensionar.ApplyInPlace(firmasBaseDatos); 
        
        firmasBaseDatos.toRGB();
        
        //Display the original image.
        //JOptionPane.showMessageDialog(null, fb.toIcon(), "Original image", JOptionPane.PLAIN_MESSAGE);
        
        //Convert to Grayscale space.
        firmasBaseDatos.toGrayscale();
        
        //Apply the Bradley local threshold.
        BradleyLocalThreshold bradleyBase = new BradleyLocalThreshold();
        bradleyBase.applyInPlace(firmasBaseDatos);
        
        
        //Display the result.
        JOptionPane.showMessageDialog(null, firmasBaseDatos.toIcon(), "Result: "+i, JOptionPane.PLAIN_MESSAGE);

        
        
        
        
        
         
        
        FastRetinaKeypointDetector frdBase = new FastRetinaKeypointDetector(fac);
        List<FastRetinaKeypoint> gBaseDatos;
        gBaseDatos = frdBase.ProcessImage(firmasBaseDatos);

        
     //SampleApplication.FastBitmap fb4 = new SampleApplication.FastBitmap("/home/wirox91/Escritorio/B- pc/Backup/Imagenes/2otsu.png");
    //SampleApplication.FastBitmap fb4DibujarBase = new SampleApplication.FastBitmap("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\corte1"+v+".jpg"); 
   
        Firmas.FastBitmap fb4DibujarBase;
        if (i<10)
        fb4DibujarBase = new Firmas.FastBitmap("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\Imagenes\\firmas.jpg\\f00"+i+".jpg");
        else
        fb4DibujarBase = new Firmas.FastBitmap("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\Imagenes\\firmas.jpg\\f0"+i+".jpg");
                
      
    
    
    
    FastGraphics graficarPuntos2Base = new FastGraphics(fb4DibujarBase);
     
         System.out.println("Puntos ha dibujar");
     //graficarPuntos2Base.setColor(Color.Blue);     
     graficarPuntos2Base.setColor(Color.Black);
     
     //Funcion 0
         
     for(int ii = 0; ii < gBaseDatos.size(); ii++) {
        int x1 = (int)gBaseDatos.get(ii).x;
        int y1 = (int)gBaseDatos.get(ii).y;
         graficarPuntos2Base.DrawCircle(x1,y1, 2);
        }
        
    JOptionPane.showMessageDialog(null, fb4DibujarBase.toIcon(), "Result con Puntos", JOptionPane.PLAIN_MESSAGE); 
    List<FastRetinaKeypoint> listaPrubeKeyPoints4PadronBaseDatos = new ArrayList<FastRetinaKeypoint>();
    listaPrubeKeyPoints4PadronBaseDatos = frdBase.ProcessImage(firmasBaseDatos);
     
        
    //for(int i = 0; i < listaPrubeKeyPoints2.size(); i++) 
        //System.out.println(listaPrubeKeyPoints2.get(i).x+" "+listaPrubeKeyPoints2.get(i).y+" "+listaPrubeKeyPoints2.get(i).toBinary());
            
        System.out.println("Espacio lista de puntos "+(i+1)+" = "+listaPrubeKeyPoints4PadronBaseDatos.size());
        listaPuntosBaseDatos.add(listaPrubeKeyPoints4PadronBaseDatos);
         
        
        }

           
        System.out.println("Total de lista de puntos"+listaPuntosBaseDatos.size());
        for (int i = 0; i < listaPuntosBaseDatos.size()
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                ; i++) {
            System.out.println("Total de la lista "+(i+1)+" : "+listaPuntosBaseDatos.get(i).size());
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
        System.out.println("Total matches, la fiugra "+k+" :"+count);
        System.out.println("----------------------------------"+ "");
        System.out.println("Total puntos figura1: "+listaPuntos.get(ki).size());
        System.out.println("Total puntos figura2: "+listaPuntosBaseDatos.get(k).size());
        System.out.println("");
   
     }
         listaDistancia2Listas.add(listaResultadosPorImagen);

        
        
         }
           int [] personaNo;
           double [] distanciaPersonaNo;
           personaNo = new int[8];
           distanciaPersonaNo = new double[8];
           double menor;
           System.out.println("listaDistancia2Lista.get(0).size"+listaPuntosBaseDatos.get(0).size());
     double mayor =-999999;
     double menor2 = 99999;
     int indiceMayor=0;
        for (int i = 0; i < listaDistancia2Listas.size(); i++) {
            mayor = -99999;
            menor = 99999;
            menor2= 99999;
            indiceMayor=0;
            for (int k = 0; k < listaDistancia2Listas.get(i).size(); k++) {
                if(listaDistancia2Listas.get(i).get(k).indFirmaMatch >=mayor){
                   
                    
                    
                    
                    mayor =listaDistancia2Listas.get(i).get(k).indFirmaMatch; 
                   // menor = listaPuntosBaseDatos.get(k).size()-listaDistancia2Listas.get(i).get(k).resul;
                    indiceMayor=k;
                   // menor2 = listaPuntos.get(i).size()-listaPuntosBaseDatos.get(k).size();
                }
                
                
            }
            personaNo[i] = indiceMayor;
            distanciaPersonaNo[i] = mayor;
            System.out.println("Match imagen corte: "+(i+1)+" y la de la base de datos: "+(indiceMayor+1)+" con puntos: "+mayor);

        }
        
        for (int i = 0; i < listaDistancia2Listas.size(); i++) {
            System.out.println("Figura: "+i+" ");
            System.out.println("----------------------------------");
            for (int k = 0; k < listaDistancia2Listas.get(i).size(); k++) {
                System.out.println(k+" ) "+listaDistancia2Listas.get(i).get(k).indFirmaMatch);
            }
        }
        
        int []indiceNO;
        int cantN=0;
        indiceNO = new int[8];
        for (int i = 0; i < personaNo.length; i++) {
            if(distanciaPersonaNo[i]<30)
            {
                
                indiceNO[cantN]=i;
                        cantN++;
            }
                      
        }
        
        
        for (int i = 0; i < cantN; i++) {
            System.out.println("Indice no: "+indiceNO[i]);
        }
        
        
        
        
        
           
    }
    
   
   
}


