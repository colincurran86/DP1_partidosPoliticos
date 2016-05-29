/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Firmas;


import Catalano.Core.IntPoint;

/**
 *
 * @author sony
 */
public class FeaturePoint implements Comparable<FeaturePoint>{
    
    /**
     * X axis coordinate.
     */
    public int x;
    
    /**
     * Y axis coordinate.
     */
    public int y;
    
    /**
     * Score.
     */
    public int score;

    /**
     * Initializes a new instance of the FeaturePoint class.
     */
    public FeaturePoint() {}

    /**
     * Initializes a new instance of the FeaturePoint class.
     * @param x X axis coordinate.
     * @param y Y axis coordinate.
     */
    public FeaturePoint(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Initializes a new instance of the FeaturePoint class.
     * @param x X axis coordinate.
     * @param y Y axis coordinate.
     * @param score Score.
     */
    public FeaturePoint(int x, int y, int score) {
        this.x = x;
        this.y = y;
        this.score = score;
    }

    /**
     * Convert to IntPoint.
     * @return IntPoint.
     */
    public IntPoint toIntPoint(){
        return new IntPoint(x, y);
    }

    @Override
    public int compareTo(FeaturePoint o) {
        if (o.score < this.score) return 1;
        else if (o.score == this.score) return 0;
        else return -1;
    }
    
}
