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
class FastRetinaKeypoint {
 
    
    /**
     * Gets or sets the x-coordinate of this point.
     */
    public double x,
    /**
     * Gets or sets the y-coordinate of this point.
     */
    y,
    /**
     * Gets or sets the scale of the point.
     */
    scale,
    /**
     * Gets or sets the orientation of this point in angles.
     */
    orientation;
    private byte[] descriptor;
    private int indexMatch;
    /**
     * Get the orientation of this point in angles.
     * @return Orientation.
     */
    public double getOrientation() {
        return orientation;
    }

    /**
     * Set the orientation of this point in angles.
     * @param orientation Orientation.
     */
    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }

    /**
     * Get the scale of this point.
     * @return Scale.
     */
    public double getScale() {
        return scale;
    }
    

    /**
     * Set the scale of this point.
     * @param scale Scale.
     */
    public void setScale(double scale) {
        this.scale = scale;
    }

    /**
     * Gets the descriptor vector associated with this point.
     * @return Descriptor.
     */
    public byte[] getDescriptor() {
        return descriptor;
    }

    /**
     * Sets the descriptor vector associated with this point.
     * @param descriptor Descriptor.
     */
    public void setDescriptor(byte[] descriptor) {
        this.descriptor = descriptor;
    }
    
    /**
     * @return the indexMatch
     */
    public int getIndexMatch() {
        return indexMatch;
    }

    /**
     * @param indexMatch the indexMatch to set
     */
    public void setIndexMatch(int indexMatch) {
        this.indexMatch = indexMatch;
    }
    

    /**
     * Initializes a new instance of the FastRetinaKeypoint class.
     * @param x The x-coordinate of the point in the image.
     * @param y The y-coordinate of the point in the image.
     */
    public FastRetinaKeypoint(double x, double y) {
        this.x = x;
        this.y = y;
        indexMatch = -1;
    }
    
    /**
     * Convert the binary descriptor to a string of binary values.
     * @return A string containing a binary value representing this point's descriptor.
     */
    public String toBinary(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < descriptor.length; i++) {
            for (int j = 0; j < 8; j++)
            {
                boolean set = (descriptor[i] & (1 << j)) != 0;
                sb.append(set ? "1" : "0");
            }
        }
        return sb.toString();
    }
    
    /**
     * Convert the binary descriptor to a string of hexadecimal values.
     * @return A string containing a hexadecimal value representing this point's descriptor.
     */
    public String toHex(){
        StringBuilder sb = new StringBuilder(descriptor.length*2);
        for (int i = 0; i < descriptor.length; i++) {
            sb.append(String.format("%02X ",descriptor[i]));
        }
        return sb.toString();
    }
    
    /**
     * Convert Fast Retina Keypoint to Int Point.
     * @return IntPoint.
     */
    public IntPoint toIntPoint(){
        return new IntPoint(x, y);
    }
    

    @Override
    public boolean equals(Object obj) {
        FastRetinaKeypoint p = (FastRetinaKeypoint)obj;
        
        byte[] d = p.descriptor;
        for (int i = 0; i < d.length; i++)
            if(descriptor[i] != d[i])
                return false;
        
        return true;
        
    }
    
    
    public int primerosBits(Object obj)
    {int count=0;
       FastRetinaKeypoint p = (FastRetinaKeypoint)obj;
        //Distance d1 = new Distance();
        byte[] d = p.descriptor;
        for (int i = 0; i < 32; i++){
            
            if(descriptor[i] == d[i])
                {count++;         
                }
        }
        
        return count;
    }



    
}
