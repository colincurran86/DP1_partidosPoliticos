/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Firmas;

import Catalano.Core.DoublePoint;
import Catalano.Core.FloatPoint;
import Catalano.Core.IntPoint;


public class IntRectangle {
    
    public int x;
    public int y;
    public int width;
    public int height;

    /**
     * Get X axis coordinate.
     * @return X axis coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Set X axis coordinate.
     * @param x X axis coordinate.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Get Y axis coordinate.
     * @return Y axis coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Set Y axis coordinate.
     * @param y Y axis coordinate.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Get height of rectangle.
     * @return Height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Set height of rectangle.
     * @param height Height.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Get Width of rectangle.
     * @return Width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Set Width of rectangle.
     * @param width Width.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Initialize a new instance of the IntRectangle class.
     * @param x X axis coordinate.
     * @param y Y axis coordinate.
     * @param width Width.
     * @param height Height.
     */
    public IntRectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    /**
     * Initialize a new instance of the IntRectangle class.
     * @param x X axis coordinate.
     * @param y Y axis coordinate.
     * @param width Width.
     * @param height Height.
     */
    public IntRectangle(float x, float y, int width, int height) {
        this.x = (int)x;
        this.y = (int)y;
        this.width = width;
        this.height = height;
    }
    
    /**
     * Initialize a new instance of the IntRectangle class.
     * @param x X axis coordinate.
     * @param y Y axis coordinate.
     * @param width Width.
     * @param height Height.
     */
    public IntRectangle(double x, double y, int width, int height) {
        this.x = (int)x;
        this.y = (int)y;
        this.width = width;
        this.height = height;
    }
    
    /**
     * Initialize a new instance of the IntRectangle class.
     * @param p Point contains X and Y coordinate.
     * @param width Width.
     * @param height Height.
     */
    public IntRectangle(IntPoint p, int width, int height){
        this.x = p.x;
        this.y = p.y;
        this.width = width;
        this.height = height;
    }
    
    /**
     * Initialize a new instance of the IntRectangle class.
     * @param p Point contains X and Y coordinate.
     * @param width Width.
     * @param height Height.
     */
    public IntRectangle(FloatPoint p, int width, int height){
        this.x = (int)p.x;
        this.y = (int)p.y;
        this.width = width;
        this.height = height;
    }
    
    /**
     * Initialize a new instance of the IntRectangle class.
     * @param p Point contains X and Y coordinate.
     * @param width Width.
     * @param height Height.
     */
    public IntRectangle(DoublePoint p, int width, int height){
        this.x = (int)p.x;
        this.y = (int)p.y;
        this.width = width;
        this.height = height;
    }
    
    /**
     * Initialize a new instance of the IntRectangle class.
     * @param rectangle Ractangle.
     */
    public IntRectangle(IntRectangle rectangle){
        this.x = rectangle.x;
        this.y = rectangle.y;
        this.width = rectangle.width;
        this.height = rectangle.height;
    }
    
    /**
     * Verify if the point is inside of rectangle.
     * @param p IntPoint contains X and Y coordinate.
     * @return True if the rectangle contains, otherwise false.
     */
    public boolean isInside(IntPoint p){
        return isInside(p.x, p.y);
    }
    
    /**
     * Verify if the point is inside of rectangle.
     * @param x X axis coordinate.
     * @param y Y axis coordinate.
     * @return True if the rectangle contains, otherwise false.
     */
    public boolean isInside(int x, int y){
        
        if (x >= this.x && x <= this.height){
            if (y >= this.y && y <= this.width){
                return true;
            }
        }
        return false;
        
    }
    
    /**
     *  Verify if the rectangle is overlaped.
     * @param rect Rectangle.
     * @return True if is overlaped, otherwise false.
     */
    public boolean isOverlaped(IntRectangle rect){
        return isOverlaped(rect.x, rect.y, rect.width, rect.height);
    }
    
    /**
     * Verify if the rectangle is overlaped.
     * @param x X axis coordinate.
     * @param y Y axis coordinate.
     * @param width Width.
     * @param height Height.
     * @return True if is overlaped, otherwise false.
     */
    public boolean isOverlaped(int x, int y, int width, int height){
        
        if (this.x < x + height && this.x + this.height > x &&
            this.y < y + width && this.y + this.width > y) 
            return true;
        
        return false;
    }
    
    /**
     * Get edge points of rectangle.
     * @return Edge points.
     */
    public IntPoint[] getEdgePoints(){
        
        IntPoint[] p = new IntPoint[4];
        
        p[0] = new IntPoint(this.x, this.y);
        p[1] = new IntPoint(this.x, this.y + this.width);
        p[2] = new IntPoint(this.x + this.height, this.y);
        p[3] = new IntPoint(this.x + this.height, this.y + this.width);
        
        return p;
    }
    
    
}