/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Firmas;

/**
 *
 * @author wirox91
 */
public interface IApplyInPlace {
    /**
     * Apply filter to an image.
     * @param fastBitmap Image to be processed.
     */
    void applyInPlace(FastBitmap fastBitmap);
}
