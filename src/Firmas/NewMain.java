package Firmas;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.OtsuThreshold;
import models.PersonaReniec;



/**
 *
 * @author LUIS S
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
	private static int[] indicePersonaFallaron = null;
	
    /**
     * @param args
     * @throws IOException
     */
    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
       
       //Inicio
       AlgoritmoFirmas af = new AlgoritmoFirmas();
       ArrayList<FirmaRecortada>lbi = new ArrayList<FirmaRecortada>();
       String urlPlanillonesOriginales = new String("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\Otras_Resoluciones\\objs\\planillon");
       //String urlPlanillonesOriginales = new String("C:\\Users\\LUIS S\\git\\DP1_partidosPoliticos\\src\\Recorte\\kappa");
       
       String urlBaseDeDatos = new String("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\1.jpg");
       List<PersonaReniec> lista;
       List<List<PersonaReniec>> lper = new ArrayList<List<PersonaReniec>>();
       /*
       lbi = af.cortarFirmas(urlPlanillonesOriginales); 
       lper = af.llenarDatosPruebaListaDeLista();   
       lista = af.procesarFirmas(lbi,lper,urlBaseDeDatos);      
       
       //Resultados
       System.out.println("Resultados finales: ");
       for (int i = 0; i < lista.size(); i++) {
    	   //if(lista.get(i).getDni().equals("00000000")==false)
		System.out.println("dni :"+lista.get(i).getDni());
       }
       */
       
       /*
       lper = af.llenarDatosPruebaListaDeLista(); 
       lista = af.procesar(lper, urlPlanillonesOriginales, urlBaseDeDatos);
       System.out.println("Resultados finales: ");
       for (int i = 0; i < lista.size(); i++) {
    	   //if(lista.get(i).getDni().equals("00000000")==false)
		System.out.println("dni :"+lista.get(i).getDni());
       }
      */
       
  
      int indice=2;
      List<PersonaReniec> lper2 = new ArrayList<PersonaReniec>();
      lper2 = af.llenarDatosPruebaListaDeLista2();   

     // lista = af.procesarNuevo(lper2, indice, urlPlanillonesOriginales, urlBaseDeDatos);
//     List<Double>  resupuesta = af.procesarNuevo(lper2, indice, urlPlanillonesOriginales, urlBaseDeDatos);
  // for (int i = 0; i < lista.size(); i++) {
     
      //urlBaseDeDatos = new String("C:\\Users\\LUIS S\\Desktop\\bd");
    
      
      //urlPlanillonesOriginales = new String("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\Otras_Resoluciones\\objs\\planillon\\1.jpg");
    
      
      
      
      
      
      
      
      
      /*
      urlPlanillonesOriginales = new String("C:\\Users\\LUIS S\\Desktop\\part.d.original9.png");
      urlBaseDeDatos = new String("C:\\Users\\LUIS S\\Desktop\\Firmas Java\\1.jpg");
      //urlBaseDeDatos = new String("C:\\Users\\LUIS S\\Desktop\\Firmas.jpg\\dfi00"+(i+1)+".png");
      System.out.println("base datos: "+urlBaseDeDatos);
      indice=0;
      List<Double>  resupuesta = af.procesarNuevo(lper2, indice, urlPlanillonesOriginales, urlBaseDeDatos);     
      for (int i = 0; i < resupuesta.size(); i++) {
	//System.out.println("Indice: "+i+" DNI: "+lista.get(i).getDni());
	   System.out.println("Indice: "+i+" DNI: "+resupuesta.get(i));
    }
    */
     
      
      
      
      
      
      //af.procesar2();
      
      
      
      
      
      
      
      
       /*
     FastBitmap i3 = new FastBitmap("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\Imagenes\\5.jpg");
     urlBaseDeDatos = new String("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\Imagenes\\1otsu.png");
     FirmaRecortada ne = new FirmaRecortada();
     ne.alto = i3.getHeight();
     ne.ancho = i3.getHeight();
     ne.img  = i3.toBufferedImage();
     System.out.println("Alto: "+ne.alto);
     System.out.println("Ancho: "+ne.ancho);
     
     af.procesarFirmasNuevoNuevo(ne,urlBaseDeDatos);
   */
    
      
      //urlPlanillonesOriginales = new String("C:\\Users\\LUIS S\\Desktop\\part.d.original3.png");
     // urlPlanillonesOriginales = new String("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\Otras_Resoluciones\\objs\\planillon\\8.jpg"); 
     
     //urlPlanillonesOriginales = new String("C:\\Users\\LUIS S\\Desktop\\part.d.original3.png"); 
   //  urlPlanillonesOriginales = new String("C:\\Users\\LUIS S\\Desktop\\Padron1MenosCalidad.png"); 
   //  urlPlanillonesOriginales = new String("C:\\Users\\LUIS S\\Desktop\\part.d.original22.png"); 


     
     
     /*
      urlBaseDeDatos = new String("C:\\Users\\LUIS S\\Desktop\\Firmas.jpg\\dfi003.png");
      System.out.println("ULR: "+urlBaseDeDatos);
      // urlBaseDeDatos = new String("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\1.jpg");
       //urlPlanillonesOriginales = new String("C:\\Users\\LUIS S\\Desktop\\1.jpg");
       FirmaRecortada ne = new FirmaRecortada(); 
       ne = af.cortarFirma(urlPlanillonesOriginales,4);
       af.procesarFirmasNuevoNuevo(ne,urlBaseDeDatos);
     */
 
      
      
      
      
      
      
      
      
      
      
      
     // for (int i = 0; i < 8; i++) {
		
	
  
      
      //5f, ya no
      //8h
      //2,3,4,5,6 a 
      //b 7; linea de indice 3 salen 2 firmas
      //6 jpeg
      
      
      
      //2h exception
      //3h
      //caso especial; 1b
      //c 1,4,5,7,8 
      //g, 6 
      
      
      
      
      
      
      
      
      
      
      

      
    //  for (int i = 1; i < 72; i++) {
		
	
      
  	String url22 = null;
  	FirmaRecortada ne = new FirmaRecortada();
  	
  	/*
  	if(i<10)
  	urlBaseDeDatos = new String("C:\\Users\\LUIS S\\Desktop\\GRUPO06.ok\\Firmas\\ffi00"+i);
  	else
  		urlBaseDeDatos = new String("C:\\Users\\LUIS S\\Desktop\\GRUPO06.ok\\Firmas\\ffi0"+i);
  	*/
  	//urlPlanillonesOriginales = new String("C:\\Users\\LUIS S\\Desktop\\Nueva carpeta\\Firmas Java\\Otras_Resoluciones\\objs\\planillon\\13.png"); 
  // urlPlanillonesOriginales = new String("C:\\Users\\LUIS S\\Desktop\\PROBAR PROBAR\\Planillones\\part.d.original8.png");
     
  
  	/*for (int i = 0; i < 102; i++) {
  	Double p = AlgoritmoFirmas.normalizarPorentajes(4,i,22);
  	System.out.println("Porenta nuevo: "+p+" i:"+i);
  	}
  	*/
  	
  	
  	
	urlPlanillonesOriginales = new String("C:\\Users\\LUIS S\\Desktop\\PROBAR PROBAR\\Planillones\\f\\partForiginal006");
  //urlPlanillonesOriginales = new String("C:\\Users\\LUIS S\\Desktop\\GRUPO06.ok\\Planillones\\partForiginal006");
  	urlPlanillonesOriginales=probarRuta(urlPlanillonesOriginales);
  	
  	//  urlPlanillonesOriginales = new String("C:\\Users\\LUIS S\\Desktop\\PROBAR PROBAR\\Planillones\\part.d.original8.jpg");
   //   urlPlanillonesOriginales = new String("C:\\Users\\LUIS S\\Desktop\\datos Globales\\partForiginal005.jpg");   
    	urlBaseDeDatos = new String("C:\\Users\\LUIS S\\Desktop\\GRUPO06.ok\\Firmas\\ffi047");	
      System.out.println("ULR turno: ---------------------------------------------------"+urlBaseDeDatos);
      	ne = af.cortarFirmaHuellas(urlPlanillonesOriginales,0);
      	FastBitmap tm = new FastBitmap(ne.img);
		JOptionPane.showMessageDialog(null, tm.toIcon(), "Result ",JOptionPane.PLAIN_MESSAGE);
		ne = af.cortarFirmaHuellas(urlPlanillonesOriginales,7);
		urlBaseDeDatos=probarRuta(urlBaseDeDatos);
       List<Double> dl = af.procesarFirmasNuevoNuevo(ne,urlBaseDeDatos);
       
       System.out.println("Umbral: "+dl.get(1)+" i: "+"nueva similitud: "+dl.get(0));
    //  }
      
       
      
   //   }  
       
       
       //porceM - (34 - porcentaje anterior)
       
       
       
       /*
       
       String nombre ="dfi001";
       
       String sFichero1 = "C:\\Users\\LUIS S\\Desktop\\Firmas.jpg\\dfi001.gif";
       String sFichero2 = "C:\\Users\\LUIS S\\Desktop\\Firmas.jpg\\dfi001.bmp";

       File fichero1 = new File(sFichero1);
       File fichero2 = new File(sFichero2);

       String ruta = null;
       
       if (fichero1.exists())
       { ruta = sFichero1;
     	  System.out.println("El fichero " + sFichero1 + " existe");
       }else if (fichero2.exists())  
       { ruta = sFichero2;
     	  System.out.println("Si 2");
       }
       else
       {
     	  
     	  File dir = new File("C:\\Users\\LUIS S\\Desktop\\Firmas.jpg");
     	  String[] ficheros = dir.list();
     	    if (ficheros == null)
     	        System.out.println("No hay ficheros en el directorio especificado");
     	      else {
     	    	  System.out.println("1");
     
     	    	    for (int i = 0; i < ficheros.length; i++) {
     					
     	        	    
     	    	    	String[] parts =ficheros[i].split("\\.");
     	    	    	String part1 = parts[0];
     	    	    	String part2 = parts[1];
     	    	    	if(part1.equals(nombre))
     	    	    	{
     	    	    		System.out.println("El archivo es :"+nombre+part2);
     	    	    		ruta = "C:\\Users\\LUIS S\\Desktop\\Firmas.jpg\\"+nombre+"."+part2;
     	    	    		break;
     	    	    	}
     	    	    	
     				}
     	    	    
     	     
     	      }
     	    
       }
       */
      
      
      
      //Leer todas las firmas

      
    	String url2 = new String("C:\\Users\\LUIS S\\Desktop\\PROBAR PROBAR\\Firmas.jpg");
    	//url2 = url2 + "/" +listaDeListaPersonas.get(indicePersonaLista1).getIdFirma() +  ".png" ;

    	/*
    	
      	
      for (int i = 1; i < 65; i++) {
		System.out.println("D i:"+i);
		String imagen1;
		String imagen2;
		
		if(i<10){
			imagen2 = new String("\\dfi00"+i+".png");
			imagen1 = new String("\\dfi00"+i+".jpg");
		}else
		{
			imagen2 = new String("\\dfi0"+i+".png");
			imagen1 = new String("\\dfi0"+i+".jpg");
		}
		
	    File ficheroUrl2Png = new File(url2+imagen2);
	    File ficheroUrl2Jpg = new File(url2+imagen1);
	    String uf=null;
	    if (ficheroUrl2Png.exists())
	    { 
	    	uf = url2+imagen2;
	    }
	    else if (ficheroUrl2Jpg.exists())  
	    { 
	    	uf = url2+imagen1;
	    }
		
	    
	    FastBitmap nuevo = new FastBitmap(uf);
	        OtsuThreshold o = new OtsuThreshold();
			nuevo.toRGB();
			nuevo.toGrayscale();
			o.applyInPlace(nuevo);
		JOptionPane.showMessageDialog(null, nuevo.toIcon(), "Result " +i+ " ",JOptionPane.PLAIN_MESSAGE);
	    
		
	}
	
      
      
      

    
      
      for (int i = 1; i < 73; i++) {
		System.out.println("E i:"+i);
		String imagen1;
		String imagen2;
		
		if(i<10){
			imagen2 = new String("\\efi00"+i+".png");
			imagen1 = new String("\\efi00"+i+".jpg");
		}else
		{
			imagen2 = new String("\\efi0"+i+".png");
			imagen1 = new String("\\efi0"+i+".jpg");
		}
		
	    File ficheroUrl2Png = new File(url2+imagen2);
	    File ficheroUrl2Jpg = new File(url2+imagen1);
	    String uf=null;
	    if (ficheroUrl2Png.exists())
	    { 
	    	uf = url2+imagen2;
	    }
	    else if (ficheroUrl2Jpg.exists())  
	    { 
	    	uf = url2+imagen1;
	    }
		
	    
	    	
	    FastBitmap nuevo = new FastBitmap(uf);
	        OtsuThreshold o = new OtsuThreshold();
			nuevo.toRGB();
			nuevo.toGrayscale();
			o.applyInPlace(nuevo);
		JOptionPane.showMessageDialog(null, nuevo.toIcon(), "Result " +i+ " ",JOptionPane.PLAIN_MESSAGE);
	    
		
	}
     
      
      
    	
    	
        for (int i = 1; i < 71
        		; i++) {
    		System.out.println("G i:"+i);
    		String imagen1;
    		String imagen2;
    		
    		if(i<10){
    			imagen2 = new String("\\gfi00"+i+".png");
    			imagen1 = new String("\\gfi00"+i+".jpg");
    		}else
    		{
    			imagen2 = new String("\\gfi0"+i+".png");
    			imagen1 = new String("\\gfi0"+i+".jpg");
    		}
    		
    	    File ficheroUrl2Png = new File(url2+imagen2);
    	    File ficheroUrl2Jpg = new File(url2+imagen1);
    	    String uf=null;
    	    if (ficheroUrl2Png.exists())
    	    { 
    	    	uf = url2+imagen2;
    	    }
    	    else if (ficheroUrl2Jpg.exists())  
    	    { 
    	    	uf = url2+imagen1;
    	    }
    		
    	   
    	    FastBitmap nuevo = new FastBitmap(uf);
        
    	    OtsuThreshold o = new OtsuThreshold();
			nuevo.toRGB();
			nuevo.toGrayscale();
			o.applyInPlace(nuevo);
			
    		JOptionPane.showMessageDialog(null, nuevo.toIcon(), "Result " +i+ " ",JOptionPane.PLAIN_MESSAGE);
    	    
    		
    	}
          
    
        
        
        */
       
    	/*
      for (int i = 1; i < 73; i++) {
  		System.out.println("H i:"+i);
  		String imagen1;
  		String imagen2;
  		
  		if(i<10){
  			imagen2 = new String("\\hfi00"+i+".png");
  			imagen1 = new String("\\hfi00"+i+".jpg");
  		}else
  		{
  			imagen2 = new String("\\hfi0"+i+".png");
  			imagen1 = new String("\\hfi0"+i+".jpg");
  		}
  		
  	    File ficheroUrl2Png = new File(url2+imagen2);
  	    File ficheroUrl2Jpg = new File(url2+imagen1);
  	    String uf=null;
  	    if (ficheroUrl2Png.exists())
  	    { 
  	    	uf = url2+imagen2;
  	    }
  	    else if (ficheroUrl2Jpg.exists())  
  	    { 
  	    	uf = url2+imagen1;
  	    }
  		
  	    
  	    FastBitmap nuevo = new FastBitmap(uf);
  	        OtsuThreshold o = new OtsuThreshold();
			nuevo.toRGB();
			nuevo.toGrayscale();
			o.applyInPlace(nuevo);
  		JOptionPane.showMessageDialog(null, nuevo.toIcon(), "Result " +i+ " ",JOptionPane.PLAIN_MESSAGE);
  	    
  		
  	}
        */
      
      
     
    }
    
    public static String probarRuta(String r)
    {String url22 = null;
		String url2Png = r +".png" ;
		String url2Jpg = r +".jpg" ;
	    File ficheroUrl2Png = new File(url2Png);
	    File ficheroUrl2Jpg = new File(url2Jpg);
	    if (ficheroUrl2Png.exists())
	    {  url22 = url2Png;
	    }
	    else if (ficheroUrl2Jpg.exists())  
	    { url22 = url2Jpg;
	    }
	    return url22;
    }
}
