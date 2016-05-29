/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Firmas;

/**
 *
 * @author sony
 */
import Catalano.Imaging.*;
import Firmas.FeaturePoint;
import java.util.List;


public interface ICornersFeatureDetector {
    /**
     * Process image.
     * @param grayImage Image to be processed
     * @return List of feature points.
     */
    List<FeaturePoint> ProcessImage(Catalano.Imaging.FastBitmap grayImage);
}
