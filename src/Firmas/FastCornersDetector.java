/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Firmas;



import Catalano.Imaging.FastBitmap;
import java.util.List;

/**
 *
 * @author sony
 */
//import Catalano.Imaging.Corners.ICornersFeatureDetector;

public class FastCornersDetector implements ICornersFeatureDetector{

    public static enum Algorithm {FAST_9, FAST_12};
    private int threshold = 20;
    private boolean suppress = true;
    private Algorithm algorithm = Algorithm.FAST_9;

    /**
     * Get Threshold.
     * 
     * A number denoting how much brighter or darker the pixels surrounding the pixel in question
     * should be in order to be considered a corner.
     * 
     * @return Threshold.
     */
    public int getThreshold() {
        return threshold;
    }

    /**
     * Set Threshold.
     * 
     * A number denoting how much brighter or darker the pixels surrounding the pixel in question
     * should be in order to be considered a corner.
     * 
     * @param threshold Threshold.
     */
    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    /**
     * Check if needs apply a non-maximum suppression algorithm on the results, to allow only maximal corners.
     * @return If true, allow only maximal corners, otherwise false.
     */
    public boolean isSuppressed() {
        return suppress;
    }

    /**
     * Set suppression if needs apply a non-maximum suppression algorithm on the results, to allow only maximal corners.
     * @param suppress If true, allow only maximal corners, otherwise false.
     */
    public void setSuppression(boolean suppress) {
        this.suppress = suppress;
    }

    /**
     * Get Fast algorithm.
     * @return Fast algorithm.
     */
    public Algorithm getAlgorithm() {
        return algorithm;
    }

    /**
     * Set Fast algorithm.
     * @param algorithm Fast algorithm.
     */
    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * Initializes a new instance of the FastCornersDetector class.
     */
    public FastCornersDetector() {}
    
    /**
     * Initializes a new instance of the FastCornersDetector class.
     * @param threshold Threshold.
     */
    public FastCornersDetector(int threshold){
        this.threshold = threshold;
    }
    
    /**
     * Initializes a new instance of the FastCornersDetector class.
     * @param algorithm Algorithm.
     */
    public FastCornersDetector(Algorithm algorithm){
        this.algorithm = algorithm;
    }
    
    /**
     * Initializes a new instance of the FastCornersDetector class.
     * @param threshold Threshold.
     * @param suppress Supress.
     */
    public FastCornersDetector(int threshold, boolean suppress){
        this.threshold = threshold;
        this.suppress = suppress;
    }
    
    /**
     * Initializes a new instance of the FastCornersDetector class.
     * @param threshold Threshold.
     * @param suppress Supress.
     * @param algorithm Algorithm.
     */
    public FastCornersDetector(int threshold, boolean suppress, Algorithm algorithm){
        this.threshold = threshold;
        this.suppress = suppress;
        this.algorithm = algorithm;
    }
    
    @Override
    public List<FeaturePoint> ProcessImage(FastBitmap fastBitmap){
        //System.out.println("Si usa el proceso de Fast (processimage)");
        switch (algorithm){
            case FAST_9:
                Fast9 fast9 = new Fast9(threshold, suppress);
                return fast9.ProcessImage(fastBitmap);
            case FAST_12:
                Fast12 fast12 = new Fast12(threshold, suppress);
                return fast12.ProcessImage(fastBitmap);
        }
        return null;
        
    }
}