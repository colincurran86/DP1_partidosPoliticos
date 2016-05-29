/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Firmas;


import Catalano.Core.IntPoint;
//import Catalano.Imaging.Corners.FeaturePoint;
import Catalano.Imaging.Corners.ICornersDetector;
//import Catalano.Imaging.Corners.ICornersFeatureDetector;
import Firmas.FeaturePoint;
import Firmas.FastRetinaKeypointDescriptor;
import Catalano.Imaging.Corners.SusanCornersDetector;
import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Tools.IntegralImage;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author sony
 */
public class FastRetinaKeypointDetector {
    
    /**
     * FREAK Feature descriptor types.
     */
    public enum FastRetinaKeypointDescriptorType
    {
        /// <summary>
        ///   Do not compute descriptors.
        /// </summary>
        ///
        None,

        /// <summary>
        ///   Compute standard 512-bit descriptors.
        /// </summary>
        ///
        Standard,

        /// <summary>
        ///   Compute extended 1024-bit descriptors.
        /// </summary>
        ///
        Extended,
    }
    
    private FastRetinaKeypointDescriptorType featureType = FastRetinaKeypointDescriptorType.Standard;
    private float scale = 22.0f;
    private int octaves = 4;

    private IntegralImage integral;

    private FastBitmap grayImage;

    private FastRetinaKeypointPattern pattern;

    private FastRetinaKeypointDescriptor descriptor;

    public ICornersDetector Detector;
    
    public ICornersFeatureDetector FDetector;
        
    public FastRetinaKeypointDescriptor GetDescriptor() {
        if (descriptor == null || pattern == null){
            
            if (pattern == null){ //System.out.println("Seg√∫n tengo entendido entras ha esta parte");
                pattern = new FastRetinaKeypointPattern(octaves, scale);}

            descriptor = new FastRetinaKeypointDescriptor(grayImage, integral, pattern);
            descriptor.setExtended(featureType == FastRetinaKeypointDescriptorType.Extended);
        }

        return descriptor;
    }
    
    public FastRetinaKeypointDetector(ICornersDetector cornerDetector){
        this.Detector = cornerDetector;
    }
    
    public FastRetinaKeypointDetector(ICornersFeatureDetector cornerFeatureDetector){
        this.FDetector = cornerFeatureDetector;
    }

    public FastRetinaKeypointDetector() {
        this.Detector = new SusanCornersDetector();
    }
        
    public List<FastRetinaKeypoint> ProcessImage(FastBitmap fastBitmap){
        
        if (fastBitmap.isGrayscale()){
            grayImage = new FastBitmap(fastBitmap);
            //System.out.println("Sin escala de grisies");
        }
        else{
            grayImage = new FastBitmap(fastBitmap);
            grayImage.toGrayscale();
            //System.out.println("Con escala de grisies");
        }
        
        // 1. Extract corners points from the image.
        List<FastRetinaKeypoint> features = new ArrayList<FastRetinaKeypoint>();
        if(Detector != null){
            //System.out.println("Susan"); //Verificar que es susan detector
            List<IntPoint> corners = Detector.ProcessImage(grayImage);

            for (int i = 0; i < corners.size(); i++)
                features.add(new FastRetinaKeypoint(corners.get(i).x, corners.get(i).y));
        }
        else{
               //System.out.println("Fast"); //Verificar que es Fast detector; verificar, los cambios hechos en esta parte del cÛdigo
            List<FeaturePoint> corners = FDetector.ProcessImage(grayImage);

            for (int i = 0; i < corners.size(); i++)
                features.add(new FastRetinaKeypoint(corners.get(i).x, corners.get(i).y));
        }
        //System.out.println("Freak puntos imprimidios en freak antes de las caracteristicas");
        for(int i = 0; i < features.size(); i++) {
            //System.out.println(features.get(i).x+" "+features.get(i).y);
        }
        
        // 2. Compute the integral for the given image
        integral = IntegralImage.FromFastBitmap(grayImage);

        //System.out.println("Freak calcular caracteristicas");
        // 3. Compute feature descriptors if required
        descriptor = null;
        if (featureType != FastRetinaKeypointDescriptorType.None){
            //System.out.println("Si entra al feature descriptor");
            descriptor = GetDescriptor();
            descriptor.Compute(features);
        }
        return features;
    }
    
    
}
