/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Firmas;


import java.io.Serializable;

/**
 * Common interface for divergence measure.
 * @author Diego Catalano
 * @param <T> Type.
 */
public interface IDivergence <T> extends Serializable{

    /**
     * Compute the distance between the vectors.
     * @param u Vector.
     * @param v Vector.
     * @return Distance.
     */
    double Compute(T u, T v);
    
}