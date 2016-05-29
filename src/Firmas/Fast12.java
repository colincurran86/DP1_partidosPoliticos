/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Firmas;


//import Catalano.Imaging.Corners.ICornersFeatureDetector;
import Catalano.Imaging.FastBitmap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 *
 * @author sony
 */
public class Fast12 implements ICornersFeatureDetector{
    
    private int threshold = 20;
    private boolean suppress = true;

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
     * Initializes a new instance of the FastCornersDetector class.
     */
    public Fast12() {}
    
    /**
     * Initializes a new instance of the FastCornersDetector class.
     * @param threshold Threshold.
     */
    public Fast12(int threshold){
        this.threshold = threshold;
    }
    
    /**
     * Initializes a new instance of the FastCornersDetector class.
     * @param threshold Threshold.
     * @param suppress Supress.
     */
    public Fast12(int threshold, boolean suppress){
        this.threshold = threshold;
        this.suppress = suppress;
    }
    
    @Override
    public List<FeaturePoint> ProcessImage(FastBitmap fastBitmap){
        
        FastBitmap gray;
        if (fastBitmap.isGrayscale()){
            gray = fastBitmap;
        }
        else{
            gray = new FastBitmap(fastBitmap);
            gray.toGrayscale();
        }
        
        if (isSuppressed()){
            List<FeaturePoint> lst = detect(gray, threshold);
            lst = nonMaxSuppression(fastBitmap.getWidth(), fastBitmap.getHeight(), lst);
            return lst;
        }
        
        return detect(gray, threshold);
    }
    
    private List<FeaturePoint> detect(FastBitmap fb, int threshold){
        
        ArrayList<FeaturePoint> corners = new ArrayList<FeaturePoint>();
        
        int width = fb.getWidth();
        int height = fb.getHeight();
        int count = 0;
        
        
        for (int i = 4; i < height - 4; ++i) {
                for (int j = 4; j < width - 4; ++j) {
                        int cb = fb.getGray(i, j) + threshold;
                        int c_b = fb.getGray(i, j) - threshold;

                        if (fb.getGray(i+3, j+0) > cb)
                         if (fb.getGray(i+3, j+1) > cb)
                          if (fb.getGray(i+2, j+2) > cb)
                           if (fb.getGray(i+1, j+3) > cb)
                            if (fb.getGray(i+0, j+3) > cb)
                             if (fb.getGray(i+-1, j+3) > cb)
                              if (fb.getGray(i+-2, j+2) > cb)
                               if (fb.getGray(i+-3, j+1) > cb)
                                if (fb.getGray(i+-3, j+0) > cb)
                                 if (fb.getGray(i+-3, j+-1) > cb)
                                  if (fb.getGray(i+-2, j+-2) > cb)
                                   if (fb.getGray(i+-1, j+-3) > cb)
                                    {;}
                                   else
                                    if (fb.getGray(i+3, j+-1) > cb) 
                                     {;}
                                    else
                                     continue;
                                  else
                                   if (fb.getGray(i+2, j+-2) > cb) 
                                    if (fb.getGray(i+3, j+-1) > cb) 
                                     {;}
                                    else
                                     continue;
                                   else
                                    continue;
                                 else
                                  if (fb.getGray(i+1, j+-3) > cb) 
                                   if (fb.getGray(i+2, j+-2) > cb) 
                                    if (fb.getGray(i+3, j+-1) > cb) 
                                     {;}
                                    else
                                     continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                else
                                 if (fb.getGray(i+0, j+-3) > cb) 
                                  if (fb.getGray(i+1, j+-3) > cb) 
                                   if (fb.getGray(i+2, j+-2) > cb) 
                                    if (fb.getGray(i+3, j+-1) > cb) 
                                     {;}
                                    else
                                     continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                 else
                                  continue;
                               else
                                if (fb.getGray(i+-1, j+-3) > cb) 
                                 if (fb.getGray(i+0, j+-3) > cb) 
                                  if (fb.getGray(i+1, j+-3) > cb) 
                                   if (fb.getGray(i+2, j+-2) > cb) 
                                    if (fb.getGray(i+3, j+-1) > cb) 
                                     {;}
                                    else
                                     continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                              else
                               if (fb.getGray(i+-2, j+-2) > cb) 
                                if (fb.getGray(i+-1, j+-3) > cb) 
                                 if (fb.getGray(i+0, j+-3) > cb) 
                                  if (fb.getGray(i+1, j+-3) > cb) 
                                   if (fb.getGray(i+2, j+-2) > cb) 
                                    if (fb.getGray(i+3, j+-1) > cb) 
                                     {;}
                                    else
                                     continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                             else
                              if (fb.getGray(i+-3, j+-1) > cb) 
                               if (fb.getGray(i+-2, j+-2) > cb) 
                                if (fb.getGray(i+-1, j+-3) > cb) 
                                 if (fb.getGray(i+0, j+-3) > cb) 
                                  if (fb.getGray(i+1, j+-3) > cb) 
                                   if (fb.getGray(i+2, j+-2) > cb) 
                                    if (fb.getGray(i+3, j+-1) > cb) 
                                     {;}
                                    else
                                     continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                            else if (fb.getGray(i+0, j+3) < c_b) 
                             if (fb.getGray(i+-3, j+0) > cb) 
                              if (fb.getGray(i+-3, j+-1) > cb) 
                               if (fb.getGray(i+-2, j+-2) > cb) 
                                if (fb.getGray(i+-1, j+-3) > cb) 
                                 if (fb.getGray(i+0, j+-3) > cb) 
                                  if (fb.getGray(i+1, j+-3) > cb) 
                                   if (fb.getGray(i+2, j+-2) > cb) 
                                    if (fb.getGray(i+3, j+-1) > cb) 
                                     {;}
                                    else
                                     continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                             else if (fb.getGray(i+-3, j+0) < c_b) 
                              if (fb.getGray(i+-1, j+3) < c_b)
                               if (fb.getGray(i+-2, j+2) < c_b)
                                if (fb.getGray(i+-3, j+1) < c_b)
                                 if (fb.getGray(i+-3, j+-1) < c_b)
                                  if (fb.getGray(i+-2, j+-2) < c_b)
                                   if (fb.getGray(i+-1, j+-3) < c_b)
                                    if (fb.getGray(i+0, j+-3) < c_b)
                                     if (fb.getGray(i+1, j+-3) < c_b)
                                      if (fb.getGray(i+2, j+-2) < c_b)
                                       if (fb.getGray(i+3, j+-1) < c_b)
                                        {;}
                                       else
                                        continue;
                                      else
                                       continue;
                                     else
                                      continue;
                                    else
                                     continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                             else
                              continue;
                            else
                             if (fb.getGray(i+-3, j+0) > cb) 
                              if (fb.getGray(i+-3, j+-1) > cb) 
                               if (fb.getGray(i+-2, j+-2) > cb) 
                                if (fb.getGray(i+-1, j+-3) > cb) 
                                 if (fb.getGray(i+0, j+-3) > cb) 
                                  if (fb.getGray(i+1, j+-3) > cb) 
                                   if (fb.getGray(i+2, j+-2) > cb) 
                                    if (fb.getGray(i+3, j+-1) > cb) 
                                     {;}
                                    else
                                     continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                             else
                              continue;
                           else if (fb.getGray(i+1, j+3) < c_b) 
                            if (fb.getGray(i+3, j+-1) > cb) 
                             if (fb.getGray(i+-3, j+1) > cb) 
                              if (fb.getGray(i+-3, j+0) > cb) 
                               if (fb.getGray(i+-3, j+-1) > cb) 
                                if (fb.getGray(i+-2, j+-2) > cb) 
                                 if (fb.getGray(i+-1, j+-3) > cb) 
                                  if (fb.getGray(i+0, j+-3) > cb) 
                                   if (fb.getGray(i+1, j+-3) > cb) 
                                    if (fb.getGray(i+2, j+-2) > cb) 
                                     {;}
                                    else
                                     continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                             else if (fb.getGray(i+-3, j+1) < c_b) 
                              if (fb.getGray(i+0, j+3) < c_b)
                               if (fb.getGray(i+-1, j+3) < c_b)
                                if (fb.getGray(i+-2, j+2) < c_b)
                                 if (fb.getGray(i+-3, j+0) < c_b)
                                  if (fb.getGray(i+-3, j+-1) < c_b)
                                   if (fb.getGray(i+-2, j+-2) < c_b)
                                    if (fb.getGray(i+-1, j+-3) < c_b)
                                     if (fb.getGray(i+0, j+-3) < c_b)
                                      if (fb.getGray(i+1, j+-3) < c_b)
                                       if (fb.getGray(i+2, j+-2) < c_b)
                                        {;}
                                       else
                                        continue;
                                      else
                                       continue;
                                     else
                                      continue;
                                    else
                                     continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                             else
                              continue;
                            else
                             if (fb.getGray(i+0, j+3) < c_b)
                              if (fb.getGray(i+-1, j+3) < c_b)
                               if (fb.getGray(i+-2, j+2) < c_b)
                                if (fb.getGray(i+-3, j+1) < c_b)
                                 if (fb.getGray(i+-3, j+0) < c_b)
                                  if (fb.getGray(i+-3, j+-1) < c_b)
                                   if (fb.getGray(i+-2, j+-2) < c_b)
                                    if (fb.getGray(i+-1, j+-3) < c_b)
                                     if (fb.getGray(i+0, j+-3) < c_b)
                                      if (fb.getGray(i+1, j+-3) < c_b)
                                       if (fb.getGray(i+2, j+-2) < c_b)
                                        {;}
                                       else
                                        continue;
                                      else
                                       continue;
                                     else
                                      continue;
                                    else
                                     continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                             else
                              continue;
                           else
                            if (fb.getGray(i+-3, j+1) > cb) 
                             if (fb.getGray(i+-3, j+0) > cb) 
                              if (fb.getGray(i+-3, j+-1) > cb) 
                               if (fb.getGray(i+-2, j+-2) > cb) 
                                if (fb.getGray(i+-1, j+-3) > cb) 
                                 if (fb.getGray(i+0, j+-3) > cb) 
                                  if (fb.getGray(i+1, j+-3) > cb) 
                                   if (fb.getGray(i+2, j+-2) > cb) 
                                    if (fb.getGray(i+3, j+-1) > cb) 
                                     {;}
                                    else
                                     continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                             else
                              continue;
                            else if (fb.getGray(i+-3, j+1) < c_b) 
                             if (fb.getGray(i+0, j+3) < c_b)
                              if (fb.getGray(i+-1, j+3) < c_b)
                               if (fb.getGray(i+-2, j+2) < c_b)
                                if (fb.getGray(i+-3, j+0) < c_b)
                                 if (fb.getGray(i+-3, j+-1) < c_b)
                                  if (fb.getGray(i+-2, j+-2) < c_b)
                                   if (fb.getGray(i+-1, j+-3) < c_b)
                                    if (fb.getGray(i+0, j+-3) < c_b)
                                     if (fb.getGray(i+1, j+-3) < c_b)
                                      if (fb.getGray(i+2, j+-2) < c_b)
                                       if (fb.getGray(i+3, j+-1) < c_b)
                                        {;}
                                       else
                                        continue;
                                      else
                                       continue;
                                     else
                                      continue;
                                    else
                                     continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                             else
                              continue;
                            else
                             continue;
                          else if (fb.getGray(i+2, j+2) < c_b) 
                           if (fb.getGray(i+-2, j+2) > cb) 
                            if (fb.getGray(i+-3, j+1) > cb) 
                             if (fb.getGray(i+-3, j+0) > cb) 
                              if (fb.getGray(i+-3, j+-1) > cb) 
                               if (fb.getGray(i+-2, j+-2) > cb) 
                                if (fb.getGray(i+-1, j+-3) > cb) 
                                 if (fb.getGray(i+0, j+-3) > cb) 
                                  if (fb.getGray(i+1, j+-3) > cb) 
                                   if (fb.getGray(i+2, j+-2) > cb) 
                                    if (fb.getGray(i+3, j+-1) > cb) 
                                     {;}
                                    else
                                     if (fb.getGray(i+1, j+3) > cb) 
                                      if (fb.getGray(i+0, j+3) > cb) 
                                       if (fb.getGray(i+-1, j+3) > cb) 
                                        {;}
                                       else
                                        continue;
                                      else
                                       continue;
                                     else
                                      continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                             else
                              continue;
                            else
                             continue;
                           else if (fb.getGray(i+-2, j+2) < c_b) 
                            if (fb.getGray(i+0, j+3) < c_b)
                             if (fb.getGray(i+-1, j+3) < c_b)
                              if (fb.getGray(i+-3, j+1) < c_b)
                               if (fb.getGray(i+-3, j+0) < c_b)
                                if (fb.getGray(i+-3, j+-1) < c_b)
                                 if (fb.getGray(i+-2, j+-2) < c_b)
                                  if (fb.getGray(i+-1, j+-3) < c_b)
                                   if (fb.getGray(i+0, j+-3) < c_b)
                                    if (fb.getGray(i+1, j+-3) < c_b)
                                     if (fb.getGray(i+1, j+3) < c_b)
                                      {;}
                                     else
                                      if (fb.getGray(i+2, j+-2) < c_b)
                                       if (fb.getGray(i+3, j+-1) < c_b)
                                        {;}
                                       else
                                        continue;
                                      else
                                       continue;
                                    else
                                     continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                             else
                              continue;
                            else
                             continue;
                           else
                            continue;
                          else
                           if (fb.getGray(i+-2, j+2) > cb) 
                            if (fb.getGray(i+-3, j+1) > cb) 
                             if (fb.getGray(i+-3, j+0) > cb) 
                              if (fb.getGray(i+-3, j+-1) > cb) 
                               if (fb.getGray(i+-2, j+-2) > cb) 
                                if (fb.getGray(i+-1, j+-3) > cb) 
                                 if (fb.getGray(i+0, j+-3) > cb) 
                                  if (fb.getGray(i+1, j+-3) > cb) 
                                   if (fb.getGray(i+2, j+-2) > cb) 
                                    if (fb.getGray(i+3, j+-1) > cb) 
                                     {;}
                                    else
                                     if (fb.getGray(i+1, j+3) > cb) 
                                      if (fb.getGray(i+0, j+3) > cb) 
                                       if (fb.getGray(i+-1, j+3) > cb) 
                                        {;}
                                       else
                                        continue;
                                      else
                                       continue;
                                     else
                                      continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                             else
                              continue;
                            else
                             continue;
                           else if (fb.getGray(i+-2, j+2) < c_b) 
                            if (fb.getGray(i+0, j+3) < c_b)
                             if (fb.getGray(i+-1, j+3) < c_b)
                              if (fb.getGray(i+-3, j+1) < c_b)
                               if (fb.getGray(i+-3, j+0) < c_b)
                                if (fb.getGray(i+-3, j+-1) < c_b)
                                 if (fb.getGray(i+-2, j+-2) < c_b)
                                  if (fb.getGray(i+-1, j+-3) < c_b)
                                   if (fb.getGray(i+0, j+-3) < c_b)
                                    if (fb.getGray(i+1, j+-3) < c_b)
                                     if (fb.getGray(i+2, j+-2) < c_b)
                                      if (fb.getGray(i+1, j+3) < c_b)
                                       {;}
                                      else
                                       if (fb.getGray(i+3, j+-1) < c_b)
                                        {;}
                                       else
                                        continue;
                                     else
                                      continue;
                                    else
                                     continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                             else
                              continue;
                            else
                             continue;
                           else
                            continue;
                         else if (fb.getGray(i+3, j+1) < c_b) 
                          if (fb.getGray(i+-1, j+3) > cb) 
                           if (fb.getGray(i+-2, j+2) > cb) 
                            if (fb.getGray(i+-3, j+1) > cb) 
                             if (fb.getGray(i+-3, j+0) > cb) 
                              if (fb.getGray(i+-3, j+-1) > cb) 
                               if (fb.getGray(i+-2, j+-2) > cb) 
                                if (fb.getGray(i+-1, j+-3) > cb) 
                                 if (fb.getGray(i+0, j+-3) > cb) 
                                  if (fb.getGray(i+1, j+-3) > cb) 
                                   if (fb.getGray(i+2, j+-2) > cb) 
                                    if (fb.getGray(i+3, j+-1) > cb) 
                                     {;}
                                    else
                                     if (fb.getGray(i+1, j+3) > cb) 
                                      if (fb.getGray(i+0, j+3) > cb) 
                                       {;}
                                      else
                                       continue;
                                     else
                                      continue;
                                   else
                                    if (fb.getGray(i+2, j+2) > cb) 
                                     if (fb.getGray(i+1, j+3) > cb) 
                                      if (fb.getGray(i+0, j+3) > cb) 
                                       {;}
                                      else
                                       continue;
                                     else
                                      continue;
                                    else
                                     continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                             else
                              continue;
                            else
                             continue;
                           else
                            continue;
                          else if (fb.getGray(i+-1, j+3) < c_b) 
                           if (fb.getGray(i+0, j+3) < c_b)
                            if (fb.getGray(i+-2, j+2) < c_b)
                             if (fb.getGray(i+-3, j+1) < c_b)
                              if (fb.getGray(i+-3, j+0) < c_b)
                               if (fb.getGray(i+-3, j+-1) < c_b)
                                if (fb.getGray(i+-2, j+-2) < c_b)
                                 if (fb.getGray(i+-1, j+-3) < c_b)
                                  if (fb.getGray(i+0, j+-3) < c_b)
                                   if (fb.getGray(i+1, j+3) < c_b)
                                    if (fb.getGray(i+2, j+2) < c_b)
                                     {;}
                                    else
                                     if (fb.getGray(i+1, j+-3) < c_b)
                                      if (fb.getGray(i+2, j+-2) < c_b)
                                       {;}
                                      else
                                       continue;
                                     else
                                      continue;
                                   else
                                    if (fb.getGray(i+1, j+-3) < c_b)
                                     if (fb.getGray(i+2, j+-2) < c_b)
                                      if (fb.getGray(i+3, j+-1) < c_b)
                                       {;}
                                      else
                                       continue;
                                     else
                                      continue;
                                    else
                                     continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                             else
                              continue;
                            else
                             continue;
                           else
                            continue;
                          else
                           continue;
                         else
                          if (fb.getGray(i+-1, j+3) > cb) 
                           if (fb.getGray(i+-2, j+2) > cb) 
                            if (fb.getGray(i+-3, j+1) > cb) 
                             if (fb.getGray(i+-3, j+0) > cb) 
                              if (fb.getGray(i+-3, j+-1) > cb) 
                               if (fb.getGray(i+-2, j+-2) > cb) 
                                if (fb.getGray(i+-1, j+-3) > cb) 
                                 if (fb.getGray(i+0, j+-3) > cb) 
                                  if (fb.getGray(i+1, j+-3) > cb) 
                                   if (fb.getGray(i+2, j+-2) > cb) 
                                    if (fb.getGray(i+3, j+-1) > cb) 
                                     {;}
                                    else
                                     if (fb.getGray(i+1, j+3) > cb) 
                                      if (fb.getGray(i+0, j+3) > cb) 
                                       {;}
                                      else
                                       continue;
                                     else
                                      continue;
                                   else
                                    if (fb.getGray(i+2, j+2) > cb) 
                                     if (fb.getGray(i+1, j+3) > cb) 
                                      if (fb.getGray(i+0, j+3) > cb) 
                                       {;}
                                      else
                                       continue;
                                     else
                                      continue;
                                    else
                                     continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                             else
                              continue;
                            else
                             continue;
                           else
                            continue;
                          else if (fb.getGray(i+-1, j+3) < c_b) 
                           if (fb.getGray(i+0, j+3) < c_b)
                            if (fb.getGray(i+-2, j+2) < c_b)
                             if (fb.getGray(i+-3, j+1) < c_b)
                              if (fb.getGray(i+-3, j+0) < c_b)
                               if (fb.getGray(i+-3, j+-1) < c_b)
                                if (fb.getGray(i+-2, j+-2) < c_b)
                                 if (fb.getGray(i+-1, j+-3) < c_b)
                                  if (fb.getGray(i+0, j+-3) < c_b)
                                   if (fb.getGray(i+1, j+-3) < c_b)
                                    if (fb.getGray(i+1, j+3) < c_b)
                                     if (fb.getGray(i+2, j+2) < c_b)
                                      {;}
                                     else
                                      if (fb.getGray(i+2, j+-2) < c_b)
                                       {;}
                                      else
                                       continue;
                                    else
                                     if (fb.getGray(i+2, j+-2) < c_b)
                                      if (fb.getGray(i+3, j+-1) < c_b)
                                       {;}
                                      else
                                       continue;
                                     else
                                      continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                             else
                              continue;
                            else
                             continue;
                           else
                            continue;
                          else
                           continue;
                        else if (fb.getGray(i+3, j+0) < c_b) 
                         if (fb.getGray(i+3, j+1) > cb) 
                          if (fb.getGray(i+-1, j+3) > cb) 
                           if (fb.getGray(i+0, j+3) > cb) 
                            if (fb.getGray(i+-2, j+2) > cb) 
                             if (fb.getGray(i+-3, j+1) > cb) 
                              if (fb.getGray(i+-3, j+0) > cb) 
                               if (fb.getGray(i+-3, j+-1) > cb) 
                                if (fb.getGray(i+-2, j+-2) > cb) 
                                 if (fb.getGray(i+-1, j+-3) > cb) 
                                  if (fb.getGray(i+0, j+-3) > cb) 
                                   if (fb.getGray(i+1, j+3) > cb) 
                                    if (fb.getGray(i+2, j+2) > cb) 
                                     {;}
                                    else
                                     if (fb.getGray(i+1, j+-3) > cb) 
                                      if (fb.getGray(i+2, j+-2) > cb) 
                                       {;}
                                      else
                                       continue;
                                     else
                                      continue;
                                   else
                                    if (fb.getGray(i+1, j+-3) > cb) 
                                     if (fb.getGray(i+2, j+-2) > cb) 
                                      if (fb.getGray(i+3, j+-1) > cb) 
                                       {;}
                                      else
                                       continue;
                                     else
                                      continue;
                                    else
                                     continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                             else
                              continue;
                            else
                             continue;
                           else
                            continue;
                          else if (fb.getGray(i+-1, j+3) < c_b) 
                           if (fb.getGray(i+-2, j+2) < c_b)
                            if (fb.getGray(i+-3, j+1) < c_b)
                             if (fb.getGray(i+-3, j+0) < c_b)
                              if (fb.getGray(i+-3, j+-1) < c_b)
                               if (fb.getGray(i+-2, j+-2) < c_b)
                                if (fb.getGray(i+-1, j+-3) < c_b)
                                 if (fb.getGray(i+0, j+-3) < c_b)
                                  if (fb.getGray(i+1, j+-3) < c_b)
                                   if (fb.getGray(i+2, j+-2) < c_b)
                                    if (fb.getGray(i+3, j+-1) < c_b)
                                     {;}
                                    else
                                     if (fb.getGray(i+1, j+3) < c_b)
                                      if (fb.getGray(i+0, j+3) < c_b)
                                       {;}
                                      else
                                       continue;
                                     else
                                      continue;
                                   else
                                    if (fb.getGray(i+2, j+2) < c_b)
                                     if (fb.getGray(i+1, j+3) < c_b)
                                      if (fb.getGray(i+0, j+3) < c_b)
                                       {;}
                                      else
                                       continue;
                                     else
                                      continue;
                                    else
                                     continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                             else
                              continue;
                            else
                             continue;
                           else
                            continue;
                          else
                           continue;
                         else if (fb.getGray(i+3, j+1) < c_b) 
                          if (fb.getGray(i+2, j+2) > cb) 
                           if (fb.getGray(i+-2, j+2) > cb) 
                            if (fb.getGray(i+0, j+3) > cb) 
                             if (fb.getGray(i+-1, j+3) > cb) 
                              if (fb.getGray(i+-3, j+1) > cb) 
                               if (fb.getGray(i+-3, j+0) > cb) 
                                if (fb.getGray(i+-3, j+-1) > cb) 
                                 if (fb.getGray(i+-2, j+-2) > cb) 
                                  if (fb.getGray(i+-1, j+-3) > cb) 
                                   if (fb.getGray(i+0, j+-3) > cb) 
                                    if (fb.getGray(i+1, j+-3) > cb) 
                                     if (fb.getGray(i+1, j+3) > cb) 
                                      {;}
                                     else
                                      if (fb.getGray(i+2, j+-2) > cb) 
                                       if (fb.getGray(i+3, j+-1) > cb) 
                                        {;}
                                       else
                                        continue;
                                      else
                                       continue;
                                    else
                                     continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                             else
                              continue;
                            else
                             continue;
                           else if (fb.getGray(i+-2, j+2) < c_b) 
                            if (fb.getGray(i+-3, j+1) < c_b)
                             if (fb.getGray(i+-3, j+0) < c_b)
                              if (fb.getGray(i+-3, j+-1) < c_b)
                               if (fb.getGray(i+-2, j+-2) < c_b)
                                if (fb.getGray(i+-1, j+-3) < c_b)
                                 if (fb.getGray(i+0, j+-3) < c_b)
                                  if (fb.getGray(i+1, j+-3) < c_b)
                                   if (fb.getGray(i+2, j+-2) < c_b)
                                    if (fb.getGray(i+3, j+-1) < c_b)
                                     {;}
                                    else
                                     if (fb.getGray(i+1, j+3) < c_b)
                                      if (fb.getGray(i+0, j+3) < c_b)
                                       if (fb.getGray(i+-1, j+3) < c_b)
                                        {;}
                                       else
                                        continue;
                                      else
                                       continue;
                                     else
                                      continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                             else
                              continue;
                            else
                             continue;
                           else
                            continue;
                          else if (fb.getGray(i+2, j+2) < c_b) 
                           if (fb.getGray(i+1, j+3) > cb) 
                            if (fb.getGray(i+3, j+-1) < c_b)
                             if (fb.getGray(i+-3, j+1) > cb) 
                              if (fb.getGray(i+0, j+3) > cb) 
                               if (fb.getGray(i+-1, j+3) > cb) 
                                if (fb.getGray(i+-2, j+2) > cb) 
                                 if (fb.getGray(i+-3, j+0) > cb) 
                                  if (fb.getGray(i+-3, j+-1) > cb) 
                                   if (fb.getGray(i+-2, j+-2) > cb) 
                                    if (fb.getGray(i+-1, j+-3) > cb) 
                                     if (fb.getGray(i+0, j+-3) > cb) 
                                      if (fb.getGray(i+1, j+-3) > cb) 
                                       if (fb.getGray(i+2, j+-2) > cb) 
                                        {;}
                                       else
                                        continue;
                                      else
                                       continue;
                                     else
                                      continue;
                                    else
                                     continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                             else if (fb.getGray(i+-3, j+1) < c_b) 
                              if (fb.getGray(i+-3, j+0) < c_b)
                               if (fb.getGray(i+-3, j+-1) < c_b)
                                if (fb.getGray(i+-2, j+-2) < c_b)
                                 if (fb.getGray(i+-1, j+-3) < c_b)
                                  if (fb.getGray(i+0, j+-3) < c_b)
                                   if (fb.getGray(i+1, j+-3) < c_b)
                                    if (fb.getGray(i+2, j+-2) < c_b)
                                     {;}
                                    else
                                     continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                             else
                              continue;
                            else
                             if (fb.getGray(i+0, j+3) > cb) 
                              if (fb.getGray(i+-1, j+3) > cb) 
                               if (fb.getGray(i+-2, j+2) > cb) 
                                if (fb.getGray(i+-3, j+1) > cb) 
                                 if (fb.getGray(i+-3, j+0) > cb) 
                                  if (fb.getGray(i+-3, j+-1) > cb) 
                                   if (fb.getGray(i+-2, j+-2) > cb) 
                                    if (fb.getGray(i+-1, j+-3) > cb) 
                                     if (fb.getGray(i+0, j+-3) > cb) 
                                      if (fb.getGray(i+1, j+-3) > cb) 
                                       if (fb.getGray(i+2, j+-2) > cb) 
                                        {;}
                                       else
                                        continue;
                                      else
                                       continue;
                                     else
                                      continue;
                                    else
                                     continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                             else
                              continue;
                           else if (fb.getGray(i+1, j+3) < c_b) 
                            if (fb.getGray(i+0, j+3) > cb) 
                             if (fb.getGray(i+-3, j+0) > cb) 
                              if (fb.getGray(i+-1, j+3) > cb) 
                               if (fb.getGray(i+-2, j+2) > cb) 
                                if (fb.getGray(i+-3, j+1) > cb) 
                                 if (fb.getGray(i+-3, j+-1) > cb) 
                                  if (fb.getGray(i+-2, j+-2) > cb) 
                                   if (fb.getGray(i+-1, j+-3) > cb) 
                                    if (fb.getGray(i+0, j+-3) > cb) 
                                     if (fb.getGray(i+1, j+-3) > cb) 
                                      if (fb.getGray(i+2, j+-2) > cb) 
                                       if (fb.getGray(i+3, j+-1) > cb) 
                                        {;}
                                       else
                                        continue;
                                      else
                                       continue;
                                     else
                                      continue;
                                    else
                                     continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                             else if (fb.getGray(i+-3, j+0) < c_b) 
                              if (fb.getGray(i+-3, j+-1) < c_b)
                               if (fb.getGray(i+-2, j+-2) < c_b)
                                if (fb.getGray(i+-1, j+-3) < c_b)
                                 if (fb.getGray(i+0, j+-3) < c_b)
                                  if (fb.getGray(i+1, j+-3) < c_b)
                                   if (fb.getGray(i+2, j+-2) < c_b)
                                    if (fb.getGray(i+3, j+-1) < c_b)
                                     {;}
                                    else
                                     continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                             else
                              continue;
                            else if (fb.getGray(i+0, j+3) < c_b) 
                             if (fb.getGray(i+-1, j+3) < c_b)
                              if (fb.getGray(i+-2, j+2) < c_b)
                               if (fb.getGray(i+-3, j+1) < c_b)
                                if (fb.getGray(i+-3, j+0) < c_b)
                                 if (fb.getGray(i+-3, j+-1) < c_b)
                                  if (fb.getGray(i+-2, j+-2) < c_b)
                                   if (fb.getGray(i+-1, j+-3) < c_b)
                                    {;}
                                   else
                                    if (fb.getGray(i+3, j+-1) < c_b)
                                     {;}
                                    else
                                     continue;
                                  else
                                   if (fb.getGray(i+2, j+-2) < c_b)
                                    if (fb.getGray(i+3, j+-1) < c_b)
                                     {;}
                                    else
                                     continue;
                                   else
                                    continue;
                                 else
                                  if (fb.getGray(i+1, j+-3) < c_b)
                                   if (fb.getGray(i+2, j+-2) < c_b)
                                    if (fb.getGray(i+3, j+-1) < c_b)
                                     {;}
                                    else
                                     continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                else
                                 if (fb.getGray(i+0, j+-3) < c_b)
                                  if (fb.getGray(i+1, j+-3) < c_b)
                                   if (fb.getGray(i+2, j+-2) < c_b)
                                    if (fb.getGray(i+3, j+-1) < c_b)
                                     {;}
                                    else
                                     continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                 else
                                  continue;
                               else
                                if (fb.getGray(i+-1, j+-3) < c_b)
                                 if (fb.getGray(i+0, j+-3) < c_b)
                                  if (fb.getGray(i+1, j+-3) < c_b)
                                   if (fb.getGray(i+2, j+-2) < c_b)
                                    if (fb.getGray(i+3, j+-1) < c_b)
                                     {;}
                                    else
                                     continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                              else
                               if (fb.getGray(i+-2, j+-2) < c_b)
                                if (fb.getGray(i+-1, j+-3) < c_b)
                                 if (fb.getGray(i+0, j+-3) < c_b)
                                  if (fb.getGray(i+1, j+-3) < c_b)
                                   if (fb.getGray(i+2, j+-2) < c_b)
                                    if (fb.getGray(i+3, j+-1) < c_b)
                                     {;}
                                    else
                                     continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                             else
                              if (fb.getGray(i+-3, j+-1) < c_b)
                               if (fb.getGray(i+-2, j+-2) < c_b)
                                if (fb.getGray(i+-1, j+-3) < c_b)
                                 if (fb.getGray(i+0, j+-3) < c_b)
                                  if (fb.getGray(i+1, j+-3) < c_b)
                                   if (fb.getGray(i+2, j+-2) < c_b)
                                    if (fb.getGray(i+3, j+-1) < c_b)
                                     {;}
                                    else
                                     continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                            else
                             if (fb.getGray(i+-3, j+0) < c_b)
                              if (fb.getGray(i+-3, j+-1) < c_b)
                               if (fb.getGray(i+-2, j+-2) < c_b)
                                if (fb.getGray(i+-1, j+-3) < c_b)
                                 if (fb.getGray(i+0, j+-3) < c_b)
                                  if (fb.getGray(i+1, j+-3) < c_b)
                                   if (fb.getGray(i+2, j+-2) < c_b)
                                    if (fb.getGray(i+3, j+-1) < c_b)
                                     {;}
                                    else
                                     continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                             else
                              continue;
                           else
                            if (fb.getGray(i+-3, j+1) > cb) 
                             if (fb.getGray(i+0, j+3) > cb) 
                              if (fb.getGray(i+-1, j+3) > cb) 
                               if (fb.getGray(i+-2, j+2) > cb) 
                                if (fb.getGray(i+-3, j+0) > cb) 
                                 if (fb.getGray(i+-3, j+-1) > cb) 
                                  if (fb.getGray(i+-2, j+-2) > cb) 
                                   if (fb.getGray(i+-1, j+-3) > cb) 
                                    if (fb.getGray(i+0, j+-3) > cb) 
                                     if (fb.getGray(i+1, j+-3) > cb) 
                                      if (fb.getGray(i+2, j+-2) > cb) 
                                       if (fb.getGray(i+3, j+-1) > cb) 
                                        {;}
                                       else
                                        continue;
                                      else
                                       continue;
                                     else
                                      continue;
                                    else
                                     continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                             else
                              continue;
                            else if (fb.getGray(i+-3, j+1) < c_b) 
                             if (fb.getGray(i+-3, j+0) < c_b)
                              if (fb.getGray(i+-3, j+-1) < c_b)
                               if (fb.getGray(i+-2, j+-2) < c_b)
                                if (fb.getGray(i+-1, j+-3) < c_b)
                                 if (fb.getGray(i+0, j+-3) < c_b)
                                  if (fb.getGray(i+1, j+-3) < c_b)
                                   if (fb.getGray(i+2, j+-2) < c_b)
                                    if (fb.getGray(i+3, j+-1) < c_b)
                                     {;}
                                    else
                                     continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                             else
                              continue;
                            else
                             continue;
                          else
                           if (fb.getGray(i+-2, j+2) > cb) 
                            if (fb.getGray(i+0, j+3) > cb) 
                             if (fb.getGray(i+-1, j+3) > cb) 
                              if (fb.getGray(i+-3, j+1) > cb) 
                               if (fb.getGray(i+-3, j+0) > cb) 
                                if (fb.getGray(i+-3, j+-1) > cb) 
                                 if (fb.getGray(i+-2, j+-2) > cb) 
                                  if (fb.getGray(i+-1, j+-3) > cb) 
                                   if (fb.getGray(i+0, j+-3) > cb) 
                                    if (fb.getGray(i+1, j+-3) > cb) 
                                     if (fb.getGray(i+2, j+-2) > cb) 
                                      if (fb.getGray(i+1, j+3) > cb) 
                                       {;}
                                      else
                                       if (fb.getGray(i+3, j+-1) > cb) 
                                        {;}
                                       else
                                        continue;
                                     else
                                      continue;
                                    else
                                     continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                             else
                              continue;
                            else
                             continue;
                           else if (fb.getGray(i+-2, j+2) < c_b) 
                            if (fb.getGray(i+-3, j+1) < c_b)
                             if (fb.getGray(i+-3, j+0) < c_b)
                              if (fb.getGray(i+-3, j+-1) < c_b)
                               if (fb.getGray(i+-2, j+-2) < c_b)
                                if (fb.getGray(i+-1, j+-3) < c_b)
                                 if (fb.getGray(i+0, j+-3) < c_b)
                                  if (fb.getGray(i+1, j+-3) < c_b)
                                   if (fb.getGray(i+2, j+-2) < c_b)
                                    if (fb.getGray(i+3, j+-1) < c_b)
                                     {;}
                                    else
                                     if (fb.getGray(i+1, j+3) < c_b)
                                      if (fb.getGray(i+0, j+3) < c_b)
                                       if (fb.getGray(i+-1, j+3) < c_b)
                                        {;}
                                       else
                                        continue;
                                      else
                                       continue;
                                     else
                                      continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                             else
                              continue;
                            else
                             continue;
                           else
                            continue;
                         else
                          if (fb.getGray(i+-1, j+3) > cb) 
                           if (fb.getGray(i+0, j+3) > cb) 
                            if (fb.getGray(i+-2, j+2) > cb) 
                             if (fb.getGray(i+-3, j+1) > cb) 
                              if (fb.getGray(i+-3, j+0) > cb) 
                               if (fb.getGray(i+-3, j+-1) > cb) 
                                if (fb.getGray(i+-2, j+-2) > cb) 
                                 if (fb.getGray(i+-1, j+-3) > cb) 
                                  if (fb.getGray(i+0, j+-3) > cb) 
                                   if (fb.getGray(i+1, j+-3) > cb) 
                                    if (fb.getGray(i+1, j+3) > cb) 
                                     if (fb.getGray(i+2, j+2) > cb) 
                                      {;}
                                     else
                                      if (fb.getGray(i+2, j+-2) > cb) 
                                       {;}
                                      else
                                       continue;
                                    else
                                     if (fb.getGray(i+2, j+-2) > cb) 
                                      if (fb.getGray(i+3, j+-1) > cb) 
                                       {;}
                                      else
                                       continue;
                                     else
                                      continue;
                                   else
                                    continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                             else
                              continue;
                            else
                             continue;
                           else
                            continue;
                          else if (fb.getGray(i+-1, j+3) < c_b) 
                           if (fb.getGray(i+-2, j+2) < c_b)
                            if (fb.getGray(i+-3, j+1) < c_b)
                             if (fb.getGray(i+-3, j+0) < c_b)
                              if (fb.getGray(i+-3, j+-1) < c_b)
                               if (fb.getGray(i+-2, j+-2) < c_b)
                                if (fb.getGray(i+-1, j+-3) < c_b)
                                 if (fb.getGray(i+0, j+-3) < c_b)
                                  if (fb.getGray(i+1, j+-3) < c_b)
                                   if (fb.getGray(i+2, j+-2) < c_b)
                                    if (fb.getGray(i+3, j+-1) < c_b)
                                     {;}
                                    else
                                     if (fb.getGray(i+1, j+3) < c_b)
                                      if (fb.getGray(i+0, j+3) < c_b)
                                       {;}
                                      else
                                       continue;
                                     else
                                      continue;
                                   else
                                    if (fb.getGray(i+2, j+2) < c_b)
                                     if (fb.getGray(i+1, j+3) < c_b)
                                      if (fb.getGray(i+0, j+3) < c_b)
                                       {;}
                                      else
                                       continue;
                                     else
                                      continue;
                                    else
                                     continue;
                                  else
                                   continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                             else
                              continue;
                            else
                             continue;
                           else
                            continue;
                          else
                           continue;
                        else
                         if (fb.getGray(i+0, j+3) > cb) 
                          if (fb.getGray(i+-1, j+3) > cb) 
                           if (fb.getGray(i+-2, j+2) > cb) 
                            if (fb.getGray(i+-3, j+1) > cb) 
                             if (fb.getGray(i+-3, j+0) > cb) 
                              if (fb.getGray(i+-3, j+-1) > cb) 
                               if (fb.getGray(i+-2, j+-2) > cb) 
                                if (fb.getGray(i+-1, j+-3) > cb) 
                                 if (fb.getGray(i+0, j+-3) > cb) 
                                  if (fb.getGray(i+1, j+3) > cb) 
                                   if (fb.getGray(i+2, j+2) > cb) 
                                    if (fb.getGray(i+3, j+1) > cb) 
                                     {;}
                                    else
                                     if (fb.getGray(i+1, j+-3) > cb) 
                                      {;}
                                     else
                                      continue;
                                   else
                                    if (fb.getGray(i+1, j+-3) > cb) 
                                     if (fb.getGray(i+2, j+-2) > cb) 
                                      {;}
                                     else
                                      continue;
                                    else
                                     continue;
                                  else
                                   if (fb.getGray(i+1, j+-3) > cb) 
                                    if (fb.getGray(i+2, j+-2) > cb) 
                                     if (fb.getGray(i+3, j+-1) > cb) 
                                      {;}
                                     else
                                      continue;
                                    else
                                     continue;
                                   else
                                    continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                             else
                              continue;
                            else
                             continue;
                           else
                            continue;
                          else
                           continue;
                         else if (fb.getGray(i+0, j+3) < c_b) 
                          if (fb.getGray(i+-1, j+3) < c_b)
                           if (fb.getGray(i+-2, j+2) < c_b)
                            if (fb.getGray(i+-3, j+1) < c_b)
                             if (fb.getGray(i+-3, j+0) < c_b)
                              if (fb.getGray(i+-3, j+-1) < c_b)
                               if (fb.getGray(i+-2, j+-2) < c_b)
                                if (fb.getGray(i+-1, j+-3) < c_b)
                                 if (fb.getGray(i+0, j+-3) < c_b)
                                  if (fb.getGray(i+1, j+3) < c_b)
                                   if (fb.getGray(i+2, j+2) < c_b)
                                    if (fb.getGray(i+3, j+1) < c_b)
                                     {;}
                                    else
                                     if (fb.getGray(i+1, j+-3) < c_b)
                                      {;}
                                     else
                                      continue;
                                   else
                                    if (fb.getGray(i+1, j+-3) < c_b)
                                     if (fb.getGray(i+2, j+-2) < c_b)
                                      {;}
                                     else
                                      continue;
                                    else
                                     continue;
                                  else
                                   if (fb.getGray(i+1, j+-3) < c_b)
                                    if (fb.getGray(i+2, j+-2) < c_b)
                                     if (fb.getGray(i+3, j+-1) < c_b)
                                      {;}
                                     else
                                      continue;
                                    else
                                     continue;
                                   else
                                    continue;
                                 else
                                  continue;
                                else
                                 continue;
                               else
                                continue;
                              else
                               continue;
                             else
                              continue;
                            else
                             continue;
                           else
                            continue;
                          else
                           continue;
                         else
                          continue;
                        corners.add(new FeaturePoint(i, j));
                        count++;
                }
        }
        for (int i = 0; i < count; ++i) {
                int x = corners.get(i).x;
                int y = corners.get(i).y;
                
                corners.get(i).score = (cornerScore(fb, x, y));
        }
        Collections.sort(corners, Collections.reverseOrder());
        return corners;
    }
    
    private int cornerScore(FastBitmap fastBitmap, int posx, int posy){
            int bmin = 0;
            int bmax = 255;
            int b = (bmax + bmin)/2;

            while (true){
                    if (isCorner(fastBitmap, posx, posy, b)) {
                            bmin = b;
                    } else {
                            bmax = b;
                    }

                    if (bmin == bmax - 1 || bmin == bmax) {
                            return bmin;
                    }

                    b = (bmin + bmax) / 2;
            }
    }
    
    private boolean isCorner(FastBitmap fb, int i, int j, int threshold){	
            
        int cb = fb.getGray(i, j) + threshold;
        int c_b = fb.getGray(i, j) - threshold;
            
            if (fb.getGray(i+3, j+0) > cb) 
             if (fb.getGray(i+3, j+1) > cb) 
              if (fb.getGray(i+2, j+2) > cb) 
               if (fb.getGray(i+1, j+3) > cb) 
                if (fb.getGray(i+0, j+3) > cb) 
                 if (fb.getGray(i+-1, j+3) > cb) 
                  if (fb.getGray(i+-2, j+2) > cb) 
                   if (fb.getGray(i+-3, j+1) > cb) 
                    if (fb.getGray(i+-3, j+0) > cb) 
                     if (fb.getGray(i+-3, j+-1) > cb) 
                      if (fb.getGray(i+-2, j+-2) > cb) 
                       if (fb.getGray(i+-1, j+-3) > cb) 
                        return true;
                       else
                        if (fb.getGray(i+3, j+-1) > cb) 
                         return true;
                        else
                         return false;
                      else
                       if (fb.getGray(i+2, j+-2) > cb) 
                        if (fb.getGray(i+3, j+-1) > cb) 
                         return true;
                        else
                         return false;
                       else
                        return false;
                     else
                      if (fb.getGray(i+1, j+-3) > cb) 
                       if (fb.getGray(i+2, j+-2) > cb) 
                        if (fb.getGray(i+3, j+-1) > cb) 
                         return true;
                        else
                         return false;
                       else
                        return false;
                      else
                       return false;
                    else
                     if (fb.getGray(i+0, j+-3) > cb) 
                      if (fb.getGray(i+1, j+-3) > cb) 
                       if (fb.getGray(i+2, j+-2) > cb) 
                        if (fb.getGray(i+3, j+-1) > cb) 
                         return true;
                        else
                         return false;
                       else
                        return false;
                      else
                       return false;
                     else
                      return false;
                   else
                    if (fb.getGray(i+-1, j+-3) > cb) 
                     if (fb.getGray(i+0, j+-3) > cb) 
                      if (fb.getGray(i+1, j+-3) > cb) 
                       if (fb.getGray(i+2, j+-2) > cb) 
                        if (fb.getGray(i+3, j+-1) > cb) 
                         return true;
                        else
                         return false;
                       else
                        return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                  else
                   if (fb.getGray(i+-2, j+-2) > cb) 
                    if (fb.getGray(i+-1, j+-3) > cb) 
                     if (fb.getGray(i+0, j+-3) > cb) 
                      if (fb.getGray(i+1, j+-3) > cb) 
                       if (fb.getGray(i+2, j+-2) > cb) 
                        if (fb.getGray(i+3, j+-1) > cb) 
                         return true;
                        else
                         return false;
                       else
                        return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                 else
                  if (fb.getGray(i+-3, j+-1) > cb) 
                   if (fb.getGray(i+-2, j+-2) > cb) 
                    if (fb.getGray(i+-1, j+-3) > cb) 
                     if (fb.getGray(i+0, j+-3) > cb) 
                      if (fb.getGray(i+1, j+-3) > cb) 
                       if (fb.getGray(i+2, j+-2) > cb) 
                        if (fb.getGray(i+3, j+-1) > cb) 
                         return true;
                        else
                         return false;
                       else
                        return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                else if (fb.getGray(i+0, j+3) < c_b) 
                 if (fb.getGray(i+-3, j+0) > cb) 
                  if (fb.getGray(i+-3, j+-1) > cb) 
                   if (fb.getGray(i+-2, j+-2) > cb) 
                    if (fb.getGray(i+-1, j+-3) > cb) 
                     if (fb.getGray(i+0, j+-3) > cb) 
                      if (fb.getGray(i+1, j+-3) > cb) 
                       if (fb.getGray(i+2, j+-2) > cb) 
                        if (fb.getGray(i+3, j+-1) > cb) 
                         return true;
                        else
                         return false;
                       else
                        return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                 else if (fb.getGray(i+-3, j+0) < c_b) 
                  if (fb.getGray(i+-1, j+3) < c_b)
                   if (fb.getGray(i+-2, j+2) < c_b)
                    if (fb.getGray(i+-3, j+1) < c_b)
                     if (fb.getGray(i+-3, j+-1) < c_b)
                      if (fb.getGray(i+-2, j+-2) < c_b)
                       if (fb.getGray(i+-1, j+-3) < c_b)
                        if (fb.getGray(i+0, j+-3) < c_b)
                         if (fb.getGray(i+1, j+-3) < c_b)
                          if (fb.getGray(i+2, j+-2) < c_b)
                           if (fb.getGray(i+3, j+-1) < c_b)
                            return true;
                           else
                            return false;
                          else
                           return false;
                         else
                          return false;
                        else
                         return false;
                       else
                        return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                 else
                  return false;
                else
                 if (fb.getGray(i+-3, j+0) > cb) 
                  if (fb.getGray(i+-3, j+-1) > cb) 
                   if (fb.getGray(i+-2, j+-2) > cb) 
                    if (fb.getGray(i+-1, j+-3) > cb) 
                     if (fb.getGray(i+0, j+-3) > cb) 
                      if (fb.getGray(i+1, j+-3) > cb) 
                       if (fb.getGray(i+2, j+-2) > cb) 
                        if (fb.getGray(i+3, j+-1) > cb) 
                         return true;
                        else
                         return false;
                       else
                        return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                 else
                  return false;
               else if (fb.getGray(i+1, j+3) < c_b) 
                if (fb.getGray(i+3, j+-1) > cb) 
                 if (fb.getGray(i+-3, j+1) > cb) 
                  if (fb.getGray(i+-3, j+0) > cb) 
                   if (fb.getGray(i+-3, j+-1) > cb) 
                    if (fb.getGray(i+-2, j+-2) > cb) 
                     if (fb.getGray(i+-1, j+-3) > cb) 
                      if (fb.getGray(i+0, j+-3) > cb) 
                       if (fb.getGray(i+1, j+-3) > cb) 
                        if (fb.getGray(i+2, j+-2) > cb) 
                         return true;
                        else
                         return false;
                       else
                        return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                 else if (fb.getGray(i+-3, j+1) < c_b) 
                  if (fb.getGray(i+0, j+3) < c_b)
                   if (fb.getGray(i+-1, j+3) < c_b)
                    if (fb.getGray(i+-2, j+2) < c_b)
                     if (fb.getGray(i+-3, j+0) < c_b)
                      if (fb.getGray(i+-3, j+-1) < c_b)
                       if (fb.getGray(i+-2, j+-2) < c_b)
                        if (fb.getGray(i+-1, j+-3) < c_b)
                         if (fb.getGray(i+0, j+-3) < c_b)
                          if (fb.getGray(i+1, j+-3) < c_b)
                           if (fb.getGray(i+2, j+-2) < c_b)
                            return true;
                           else
                            return false;
                          else
                           return false;
                         else
                          return false;
                        else
                         return false;
                       else
                        return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                 else
                  return false;
                else
                 if (fb.getGray(i+0, j+3) < c_b)
                  if (fb.getGray(i+-1, j+3) < c_b)
                   if (fb.getGray(i+-2, j+2) < c_b)
                    if (fb.getGray(i+-3, j+1) < c_b)
                     if (fb.getGray(i+-3, j+0) < c_b)
                      if (fb.getGray(i+-3, j+-1) < c_b)
                       if (fb.getGray(i+-2, j+-2) < c_b)
                        if (fb.getGray(i+-1, j+-3) < c_b)
                         if (fb.getGray(i+0, j+-3) < c_b)
                          if (fb.getGray(i+1, j+-3) < c_b)
                           if (fb.getGray(i+2, j+-2) < c_b)
                            return true;
                           else
                            return false;
                          else
                           return false;
                         else
                          return false;
                        else
                         return false;
                       else
                        return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                 else
                  return false;
               else
                if (fb.getGray(i+-3, j+1) > cb) 
                 if (fb.getGray(i+-3, j+0) > cb) 
                  if (fb.getGray(i+-3, j+-1) > cb) 
                   if (fb.getGray(i+-2, j+-2) > cb) 
                    if (fb.getGray(i+-1, j+-3) > cb) 
                     if (fb.getGray(i+0, j+-3) > cb) 
                      if (fb.getGray(i+1, j+-3) > cb) 
                       if (fb.getGray(i+2, j+-2) > cb) 
                        if (fb.getGray(i+3, j+-1) > cb) 
                         return true;
                        else
                         return false;
                       else
                        return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                 else
                  return false;
                else if (fb.getGray(i+-3, j+1) < c_b) 
                 if (fb.getGray(i+0, j+3) < c_b)
                  if (fb.getGray(i+-1, j+3) < c_b)
                   if (fb.getGray(i+-2, j+2) < c_b)
                    if (fb.getGray(i+-3, j+0) < c_b)
                     if (fb.getGray(i+-3, j+-1) < c_b)
                      if (fb.getGray(i+-2, j+-2) < c_b)
                       if (fb.getGray(i+-1, j+-3) < c_b)
                        if (fb.getGray(i+0, j+-3) < c_b)
                         if (fb.getGray(i+1, j+-3) < c_b)
                          if (fb.getGray(i+2, j+-2) < c_b)
                           if (fb.getGray(i+3, j+-1) < c_b)
                            return true;
                           else
                            return false;
                          else
                           return false;
                         else
                          return false;
                        else
                         return false;
                       else
                        return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                 else
                  return false;
                else
                 return false;
              else if (fb.getGray(i+2, j+2) < c_b) 
               if (fb.getGray(i+-2, j+2) > cb) 
                if (fb.getGray(i+-3, j+1) > cb) 
                 if (fb.getGray(i+-3, j+0) > cb) 
                  if (fb.getGray(i+-3, j+-1) > cb) 
                   if (fb.getGray(i+-2, j+-2) > cb) 
                    if (fb.getGray(i+-1, j+-3) > cb) 
                     if (fb.getGray(i+0, j+-3) > cb) 
                      if (fb.getGray(i+1, j+-3) > cb) 
                       if (fb.getGray(i+2, j+-2) > cb) 
                        if (fb.getGray(i+3, j+-1) > cb) 
                         return true;
                        else
                         if (fb.getGray(i+1, j+3) > cb) 
                          if (fb.getGray(i+0, j+3) > cb) 
                           if (fb.getGray(i+-1, j+3) > cb) 
                            return true;
                           else
                            return false;
                          else
                           return false;
                         else
                          return false;
                       else
                        return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                 else
                  return false;
                else
                 return false;
               else if (fb.getGray(i+-2, j+2) < c_b) 
                if (fb.getGray(i+0, j+3) < c_b)
                 if (fb.getGray(i+-1, j+3) < c_b)
                  if (fb.getGray(i+-3, j+1) < c_b)
                   if (fb.getGray(i+-3, j+0) < c_b)
                    if (fb.getGray(i+-3, j+-1) < c_b)
                     if (fb.getGray(i+-2, j+-2) < c_b)
                      if (fb.getGray(i+-1, j+-3) < c_b)
                       if (fb.getGray(i+0, j+-3) < c_b)
                        if (fb.getGray(i+1, j+-3) < c_b)
                         if (fb.getGray(i+1, j+3) < c_b)
                          return true;
                         else
                          if (fb.getGray(i+2, j+-2) < c_b)
                           if (fb.getGray(i+3, j+-1) < c_b)
                            return true;
                           else
                            return false;
                          else
                           return false;
                        else
                         return false;
                       else
                        return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                 else
                  return false;
                else
                 return false;
               else
                return false;
              else
               if (fb.getGray(i+-2, j+2) > cb) 
                if (fb.getGray(i+-3, j+1) > cb) 
                 if (fb.getGray(i+-3, j+0) > cb) 
                  if (fb.getGray(i+-3, j+-1) > cb) 
                   if (fb.getGray(i+-2, j+-2) > cb) 
                    if (fb.getGray(i+-1, j+-3) > cb) 
                     if (fb.getGray(i+0, j+-3) > cb) 
                      if (fb.getGray(i+1, j+-3) > cb) 
                       if (fb.getGray(i+2, j+-2) > cb) 
                        if (fb.getGray(i+3, j+-1) > cb) 
                         return true;
                        else
                         if (fb.getGray(i+1, j+3) > cb) 
                          if (fb.getGray(i+0, j+3) > cb) 
                           if (fb.getGray(i+-1, j+3) > cb) 
                            return true;
                           else
                            return false;
                          else
                           return false;
                         else
                          return false;
                       else
                        return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                 else
                  return false;
                else
                 return false;
               else if (fb.getGray(i+-2, j+2) < c_b) 
                if (fb.getGray(i+0, j+3) < c_b)
                 if (fb.getGray(i+-1, j+3) < c_b)
                  if (fb.getGray(i+-3, j+1) < c_b)
                   if (fb.getGray(i+-3, j+0) < c_b)
                    if (fb.getGray(i+-3, j+-1) < c_b)
                     if (fb.getGray(i+-2, j+-2) < c_b)
                      if (fb.getGray(i+-1, j+-3) < c_b)
                       if (fb.getGray(i+0, j+-3) < c_b)
                        if (fb.getGray(i+1, j+-3) < c_b)
                         if (fb.getGray(i+2, j+-2) < c_b)
                          if (fb.getGray(i+1, j+3) < c_b)
                           return true;
                          else
                           if (fb.getGray(i+3, j+-1) < c_b)
                            return true;
                           else
                            return false;
                         else
                          return false;
                        else
                         return false;
                       else
                        return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                 else
                  return false;
                else
                 return false;
               else
                return false;
             else if (fb.getGray(i+3, j+1) < c_b) 
              if (fb.getGray(i+-1, j+3) > cb) 
               if (fb.getGray(i+-2, j+2) > cb) 
                if (fb.getGray(i+-3, j+1) > cb) 
                 if (fb.getGray(i+-3, j+0) > cb) 
                  if (fb.getGray(i+-3, j+-1) > cb) 
                   if (fb.getGray(i+-2, j+-2) > cb) 
                    if (fb.getGray(i+-1, j+-3) > cb) 
                     if (fb.getGray(i+0, j+-3) > cb) 
                      if (fb.getGray(i+1, j+-3) > cb) 
                       if (fb.getGray(i+2, j+-2) > cb) 
                        if (fb.getGray(i+3, j+-1) > cb) 
                         return true;
                        else
                         if (fb.getGray(i+1, j+3) > cb) 
                          if (fb.getGray(i+0, j+3) > cb) 
                           return true;
                          else
                           return false;
                         else
                          return false;
                       else
                        if (fb.getGray(i+2, j+2) > cb) 
                         if (fb.getGray(i+1, j+3) > cb) 
                          if (fb.getGray(i+0, j+3) > cb) 
                           return true;
                          else
                           return false;
                         else
                          return false;
                        else
                         return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                 else
                  return false;
                else
                 return false;
               else
                return false;
              else if (fb.getGray(i+-1, j+3) < c_b) 
               if (fb.getGray(i+0, j+3) < c_b)
                if (fb.getGray(i+-2, j+2) < c_b)
                 if (fb.getGray(i+-3, j+1) < c_b)
                  if (fb.getGray(i+-3, j+0) < c_b)
                   if (fb.getGray(i+-3, j+-1) < c_b)
                    if (fb.getGray(i+-2, j+-2) < c_b)
                     if (fb.getGray(i+-1, j+-3) < c_b)
                      if (fb.getGray(i+0, j+-3) < c_b)
                       if (fb.getGray(i+1, j+3) < c_b)
                        if (fb.getGray(i+2, j+2) < c_b)
                         return true;
                        else
                         if (fb.getGray(i+1, j+-3) < c_b)
                          if (fb.getGray(i+2, j+-2) < c_b)
                           return true;
                          else
                           return false;
                         else
                          return false;
                       else
                        if (fb.getGray(i+1, j+-3) < c_b)
                         if (fb.getGray(i+2, j+-2) < c_b)
                          if (fb.getGray(i+3, j+-1) < c_b)
                           return true;
                          else
                           return false;
                         else
                          return false;
                        else
                         return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                 else
                  return false;
                else
                 return false;
               else
                return false;
              else
               return false;
             else
              if (fb.getGray(i+-1, j+3) > cb) 
               if (fb.getGray(i+-2, j+2) > cb) 
                if (fb.getGray(i+-3, j+1) > cb) 
                 if (fb.getGray(i+-3, j+0) > cb) 
                  if (fb.getGray(i+-3, j+-1) > cb) 
                   if (fb.getGray(i+-2, j+-2) > cb) 
                    if (fb.getGray(i+-1, j+-3) > cb) 
                     if (fb.getGray(i+0, j+-3) > cb) 
                      if (fb.getGray(i+1, j+-3) > cb) 
                       if (fb.getGray(i+2, j+-2) > cb) 
                        if (fb.getGray(i+3, j+-1) > cb) 
                         return true;
                        else
                         if (fb.getGray(i+1, j+3) > cb) 
                          if (fb.getGray(i+0, j+3) > cb) 
                           return true;
                          else
                           return false;
                         else
                          return false;
                       else
                        if (fb.getGray(i+2, j+2) > cb) 
                         if (fb.getGray(i+1, j+3) > cb) 
                          if (fb.getGray(i+0, j+3) > cb) 
                           return true;
                          else
                           return false;
                         else
                          return false;
                        else
                         return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                 else
                  return false;
                else
                 return false;
               else
                return false;
              else if (fb.getGray(i+-1, j+3) < c_b) 
               if (fb.getGray(i+0, j+3) < c_b)
                if (fb.getGray(i+-2, j+2) < c_b)
                 if (fb.getGray(i+-3, j+1) < c_b)
                  if (fb.getGray(i+-3, j+0) < c_b)
                   if (fb.getGray(i+-3, j+-1) < c_b)
                    if (fb.getGray(i+-2, j+-2) < c_b)
                     if (fb.getGray(i+-1, j+-3) < c_b)
                      if (fb.getGray(i+0, j+-3) < c_b)
                       if (fb.getGray(i+1, j+-3) < c_b)
                        if (fb.getGray(i+1, j+3) < c_b)
                         if (fb.getGray(i+2, j+2) < c_b)
                          return true;
                         else
                          if (fb.getGray(i+2, j+-2) < c_b)
                           return true;
                          else
                           return false;
                        else
                         if (fb.getGray(i+2, j+-2) < c_b)
                          if (fb.getGray(i+3, j+-1) < c_b)
                           return true;
                          else
                           return false;
                         else
                          return false;
                       else
                        return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                 else
                  return false;
                else
                 return false;
               else
                return false;
              else
               return false;
            else if (fb.getGray(i+3, j+0) < c_b) 
             if (fb.getGray(i+3, j+1) > cb) 
              if (fb.getGray(i+-1, j+3) > cb) 
               if (fb.getGray(i+0, j+3) > cb) 
                if (fb.getGray(i+-2, j+2) > cb) 
                 if (fb.getGray(i+-3, j+1) > cb) 
                  if (fb.getGray(i+-3, j+0) > cb) 
                   if (fb.getGray(i+-3, j+-1) > cb) 
                    if (fb.getGray(i+-2, j+-2) > cb) 
                     if (fb.getGray(i+-1, j+-3) > cb) 
                      if (fb.getGray(i+0, j+-3) > cb) 
                       if (fb.getGray(i+1, j+3) > cb) 
                        if (fb.getGray(i+2, j+2) > cb) 
                         return true;
                        else
                         if (fb.getGray(i+1, j+-3) > cb) 
                          if (fb.getGray(i+2, j+-2) > cb) 
                           return true;
                          else
                           return false;
                         else
                          return false;
                       else
                        if (fb.getGray(i+1, j+-3) > cb) 
                         if (fb.getGray(i+2, j+-2) > cb) 
                          if (fb.getGray(i+3, j+-1) > cb) 
                           return true;
                          else
                           return false;
                         else
                          return false;
                        else
                         return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                 else
                  return false;
                else
                 return false;
               else
                return false;
              else if (fb.getGray(i+-1, j+3) < c_b) 
               if (fb.getGray(i+-2, j+2) < c_b)
                if (fb.getGray(i+-3, j+1) < c_b)
                 if (fb.getGray(i+-3, j+0) < c_b)
                  if (fb.getGray(i+-3, j+-1) < c_b)
                   if (fb.getGray(i+-2, j+-2) < c_b)
                    if (fb.getGray(i+-1, j+-3) < c_b)
                     if (fb.getGray(i+0, j+-3) < c_b)
                      if (fb.getGray(i+1, j+-3) < c_b)
                       if (fb.getGray(i+2, j+-2) < c_b)
                        if (fb.getGray(i+3, j+-1) < c_b)
                         return true;
                        else
                         if (fb.getGray(i+1, j+3) < c_b)
                          if (fb.getGray(i+0, j+3) < c_b)
                           return true;
                          else
                           return false;
                         else
                          return false;
                       else
                        if (fb.getGray(i+2, j+2) < c_b)
                         if (fb.getGray(i+1, j+3) < c_b)
                          if (fb.getGray(i+0, j+3) < c_b)
                           return true;
                          else
                           return false;
                         else
                          return false;
                        else
                         return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                 else
                  return false;
                else
                 return false;
               else
                return false;
              else
               return false;
             else if (fb.getGray(i+3, j+1) < c_b) 
              if (fb.getGray(i+2, j+2) > cb) 
               if (fb.getGray(i+-2, j+2) > cb) 
                if (fb.getGray(i+0, j+3) > cb) 
                 if (fb.getGray(i+-1, j+3) > cb) 
                  if (fb.getGray(i+-3, j+1) > cb) 
                   if (fb.getGray(i+-3, j+0) > cb) 
                    if (fb.getGray(i+-3, j+-1) > cb) 
                     if (fb.getGray(i+-2, j+-2) > cb) 
                      if (fb.getGray(i+-1, j+-3) > cb) 
                       if (fb.getGray(i+0, j+-3) > cb) 
                        if (fb.getGray(i+1, j+-3) > cb) 
                         if (fb.getGray(i+1, j+3) > cb) 
                          return true;
                         else
                          if (fb.getGray(i+2, j+-2) > cb) 
                           if (fb.getGray(i+3, j+-1) > cb) 
                            return true;
                           else
                            return false;
                          else
                           return false;
                        else
                         return false;
                       else
                        return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                 else
                  return false;
                else
                 return false;
               else if (fb.getGray(i+-2, j+2) < c_b) 
                if (fb.getGray(i+-3, j+1) < c_b)
                 if (fb.getGray(i+-3, j+0) < c_b)
                  if (fb.getGray(i+-3, j+-1) < c_b)
                   if (fb.getGray(i+-2, j+-2) < c_b)
                    if (fb.getGray(i+-1, j+-3) < c_b)
                     if (fb.getGray(i+0, j+-3) < c_b)
                      if (fb.getGray(i+1, j+-3) < c_b)
                       if (fb.getGray(i+2, j+-2) < c_b)
                        if (fb.getGray(i+3, j+-1) < c_b)
                         return true;
                        else
                         if (fb.getGray(i+1, j+3) < c_b)
                          if (fb.getGray(i+0, j+3) < c_b)
                           if (fb.getGray(i+-1, j+3) < c_b)
                            return true;
                           else
                            return false;
                          else
                           return false;
                         else
                          return false;
                       else
                        return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                 else
                  return false;
                else
                 return false;
               else
                return false;
              else if (fb.getGray(i+2, j+2) < c_b) 
               if (fb.getGray(i+1, j+3) > cb) 
                if (fb.getGray(i+3, j+-1) < c_b)
                 if (fb.getGray(i+-3, j+1) > cb) 
                  if (fb.getGray(i+0, j+3) > cb) 
                   if (fb.getGray(i+-1, j+3) > cb) 
                    if (fb.getGray(i+-2, j+2) > cb) 
                     if (fb.getGray(i+-3, j+0) > cb) 
                      if (fb.getGray(i+-3, j+-1) > cb) 
                       if (fb.getGray(i+-2, j+-2) > cb) 
                        if (fb.getGray(i+-1, j+-3) > cb) 
                         if (fb.getGray(i+0, j+-3) > cb) 
                          if (fb.getGray(i+1, j+-3) > cb) 
                           if (fb.getGray(i+2, j+-2) > cb) 
                            return true;
                           else
                            return false;
                          else
                           return false;
                         else
                          return false;
                        else
                         return false;
                       else
                        return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                 else if (fb.getGray(i+-3, j+1) < c_b) 
                  if (fb.getGray(i+-3, j+0) < c_b)
                   if (fb.getGray(i+-3, j+-1) < c_b)
                    if (fb.getGray(i+-2, j+-2) < c_b)
                     if (fb.getGray(i+-1, j+-3) < c_b)
                      if (fb.getGray(i+0, j+-3) < c_b)
                       if (fb.getGray(i+1, j+-3) < c_b)
                        if (fb.getGray(i+2, j+-2) < c_b)
                         return true;
                        else
                         return false;
                       else
                        return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                 else
                  return false;
                else
                 if (fb.getGray(i+0, j+3) > cb) 
                  if (fb.getGray(i+-1, j+3) > cb) 
                   if (fb.getGray(i+-2, j+2) > cb) 
                    if (fb.getGray(i+-3, j+1) > cb) 
                     if (fb.getGray(i+-3, j+0) > cb) 
                      if (fb.getGray(i+-3, j+-1) > cb) 
                       if (fb.getGray(i+-2, j+-2) > cb) 
                        if (fb.getGray(i+-1, j+-3) > cb) 
                         if (fb.getGray(i+0, j+-3) > cb) 
                          if (fb.getGray(i+1, j+-3) > cb) 
                           if (fb.getGray(i+2, j+-2) > cb) 
                            return true;
                           else
                            return false;
                          else
                           return false;
                         else
                          return false;
                        else
                         return false;
                       else
                        return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                 else
                  return false;
               else if (fb.getGray(i+1, j+3) < c_b) 
                if (fb.getGray(i+0, j+3) > cb) 
                 if (fb.getGray(i+-3, j+0) > cb) 
                  if (fb.getGray(i+-1, j+3) > cb) 
                   if (fb.getGray(i+-2, j+2) > cb) 
                    if (fb.getGray(i+-3, j+1) > cb) 
                     if (fb.getGray(i+-3, j+-1) > cb) 
                      if (fb.getGray(i+-2, j+-2) > cb) 
                       if (fb.getGray(i+-1, j+-3) > cb) 
                        if (fb.getGray(i+0, j+-3) > cb) 
                         if (fb.getGray(i+1, j+-3) > cb) 
                          if (fb.getGray(i+2, j+-2) > cb) 
                           if (fb.getGray(i+3, j+-1) > cb) 
                            return true;
                           else
                            return false;
                          else
                           return false;
                         else
                          return false;
                        else
                         return false;
                       else
                        return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                 else if (fb.getGray(i+-3, j+0) < c_b) 
                  if (fb.getGray(i+-3, j+-1) < c_b)
                   if (fb.getGray(i+-2, j+-2) < c_b)
                    if (fb.getGray(i+-1, j+-3) < c_b)
                     if (fb.getGray(i+0, j+-3) < c_b)
                      if (fb.getGray(i+1, j+-3) < c_b)
                       if (fb.getGray(i+2, j+-2) < c_b)
                        if (fb.getGray(i+3, j+-1) < c_b)
                         return true;
                        else
                         return false;
                       else
                        return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                 else
                  return false;
                else if (fb.getGray(i+0, j+3) < c_b) 
                 if (fb.getGray(i+-1, j+3) < c_b)
                  if (fb.getGray(i+-2, j+2) < c_b)
                   if (fb.getGray(i+-3, j+1) < c_b)
                    if (fb.getGray(i+-3, j+0) < c_b)
                     if (fb.getGray(i+-3, j+-1) < c_b)
                      if (fb.getGray(i+-2, j+-2) < c_b)
                       if (fb.getGray(i+-1, j+-3) < c_b)
                        return true;
                       else
                        if (fb.getGray(i+3, j+-1) < c_b)
                         return true;
                        else
                         return false;
                      else
                       if (fb.getGray(i+2, j+-2) < c_b)
                        if (fb.getGray(i+3, j+-1) < c_b)
                         return true;
                        else
                         return false;
                       else
                        return false;
                     else
                      if (fb.getGray(i+1, j+-3) < c_b)
                       if (fb.getGray(i+2, j+-2) < c_b)
                        if (fb.getGray(i+3, j+-1) < c_b)
                         return true;
                        else
                         return false;
                       else
                        return false;
                      else
                       return false;
                    else
                     if (fb.getGray(i+0, j+-3) < c_b)
                      if (fb.getGray(i+1, j+-3) < c_b)
                       if (fb.getGray(i+2, j+-2) < c_b)
                        if (fb.getGray(i+3, j+-1) < c_b)
                         return true;
                        else
                         return false;
                       else
                        return false;
                      else
                       return false;
                     else
                      return false;
                   else
                    if (fb.getGray(i+-1, j+-3) < c_b)
                     if (fb.getGray(i+0, j+-3) < c_b)
                      if (fb.getGray(i+1, j+-3) < c_b)
                       if (fb.getGray(i+2, j+-2) < c_b)
                        if (fb.getGray(i+3, j+-1) < c_b)
                         return true;
                        else
                         return false;
                       else
                        return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                  else
                   if (fb.getGray(i+-2, j+-2) < c_b)
                    if (fb.getGray(i+-1, j+-3) < c_b)
                     if (fb.getGray(i+0, j+-3) < c_b)
                      if (fb.getGray(i+1, j+-3) < c_b)
                       if (fb.getGray(i+2, j+-2) < c_b)
                        if (fb.getGray(i+3, j+-1) < c_b)
                         return true;
                        else
                         return false;
                       else
                        return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                 else
                  if (fb.getGray(i+-3, j+-1) < c_b)
                   if (fb.getGray(i+-2, j+-2) < c_b)
                    if (fb.getGray(i+-1, j+-3) < c_b)
                     if (fb.getGray(i+0, j+-3) < c_b)
                      if (fb.getGray(i+1, j+-3) < c_b)
                       if (fb.getGray(i+2, j+-2) < c_b)
                        if (fb.getGray(i+3, j+-1) < c_b)
                         return true;
                        else
                         return false;
                       else
                        return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                else
                 if (fb.getGray(i+-3, j+0) < c_b)
                  if (fb.getGray(i+-3, j+-1) < c_b)
                   if (fb.getGray(i+-2, j+-2) < c_b)
                    if (fb.getGray(i+-1, j+-3) < c_b)
                     if (fb.getGray(i+0, j+-3) < c_b)
                      if (fb.getGray(i+1, j+-3) < c_b)
                       if (fb.getGray(i+2, j+-2) < c_b)
                        if (fb.getGray(i+3, j+-1) < c_b)
                         return true;
                        else
                         return false;
                       else
                        return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                 else
                  return false;
               else
                if (fb.getGray(i+-3, j+1) > cb) 
                 if (fb.getGray(i+0, j+3) > cb) 
                  if (fb.getGray(i+-1, j+3) > cb) 
                   if (fb.getGray(i+-2, j+2) > cb) 
                    if (fb.getGray(i+-3, j+0) > cb) 
                     if (fb.getGray(i+-3, j+-1) > cb) 
                      if (fb.getGray(i+-2, j+-2) > cb) 
                       if (fb.getGray(i+-1, j+-3) > cb) 
                        if (fb.getGray(i+0, j+-3) > cb) 
                         if (fb.getGray(i+1, j+-3) > cb) 
                          if (fb.getGray(i+2, j+-2) > cb) 
                           if (fb.getGray(i+3, j+-1) > cb) 
                            return true;
                           else
                            return false;
                          else
                           return false;
                         else
                          return false;
                        else
                         return false;
                       else
                        return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                 else
                  return false;
                else if (fb.getGray(i+-3, j+1) < c_b) 
                 if (fb.getGray(i+-3, j+0) < c_b)
                  if (fb.getGray(i+-3, j+-1) < c_b)
                   if (fb.getGray(i+-2, j+-2) < c_b)
                    if (fb.getGray(i+-1, j+-3) < c_b)
                     if (fb.getGray(i+0, j+-3) < c_b)
                      if (fb.getGray(i+1, j+-3) < c_b)
                       if (fb.getGray(i+2, j+-2) < c_b)
                        if (fb.getGray(i+3, j+-1) < c_b)
                         return true;
                        else
                         return false;
                       else
                        return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                 else
                  return false;
                else
                 return false;
              else
               if (fb.getGray(i+-2, j+2) > cb) 
                if (fb.getGray(i+0, j+3) > cb) 
                 if (fb.getGray(i+-1, j+3) > cb) 
                  if (fb.getGray(i+-3, j+1) > cb) 
                   if (fb.getGray(i+-3, j+0) > cb) 
                    if (fb.getGray(i+-3, j+-1) > cb) 
                     if (fb.getGray(i+-2, j+-2) > cb) 
                      if (fb.getGray(i+-1, j+-3) > cb) 
                       if (fb.getGray(i+0, j+-3) > cb) 
                        if (fb.getGray(i+1, j+-3) > cb) 
                         if (fb.getGray(i+2, j+-2) > cb) 
                          if (fb.getGray(i+1, j+3) > cb) 
                           return true;
                          else
                           if (fb.getGray(i+3, j+-1) > cb) 
                            return true;
                           else
                            return false;
                         else
                          return false;
                        else
                         return false;
                       else
                        return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                 else
                  return false;
                else
                 return false;
               else if (fb.getGray(i+-2, j+2) < c_b) 
                if (fb.getGray(i+-3, j+1) < c_b)
                 if (fb.getGray(i+-3, j+0) < c_b)
                  if (fb.getGray(i+-3, j+-1) < c_b)
                   if (fb.getGray(i+-2, j+-2) < c_b)
                    if (fb.getGray(i+-1, j+-3) < c_b)
                     if (fb.getGray(i+0, j+-3) < c_b)
                      if (fb.getGray(i+1, j+-3) < c_b)
                       if (fb.getGray(i+2, j+-2) < c_b)
                        if (fb.getGray(i+3, j+-1) < c_b)
                         return true;
                        else
                         if (fb.getGray(i+1, j+3) < c_b)
                          if (fb.getGray(i+0, j+3) < c_b)
                           if (fb.getGray(i+-1, j+3) < c_b)
                            return true;
                           else
                            return false;
                          else
                           return false;
                         else
                          return false;
                       else
                        return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                 else
                  return false;
                else
                 return false;
               else
                return false;
             else
              if (fb.getGray(i+-1, j+3) > cb) 
               if (fb.getGray(i+0, j+3) > cb) 
                if (fb.getGray(i+-2, j+2) > cb) 
                 if (fb.getGray(i+-3, j+1) > cb) 
                  if (fb.getGray(i+-3, j+0) > cb) 
                   if (fb.getGray(i+-3, j+-1) > cb) 
                    if (fb.getGray(i+-2, j+-2) > cb) 
                     if (fb.getGray(i+-1, j+-3) > cb) 
                      if (fb.getGray(i+0, j+-3) > cb) 
                       if (fb.getGray(i+1, j+-3) > cb) 
                        if (fb.getGray(i+1, j+3) > cb) 
                         if (fb.getGray(i+2, j+2) > cb) 
                          return true;
                         else
                          if (fb.getGray(i+2, j+-2) > cb) 
                           return true;
                          else
                           return false;
                        else
                         if (fb.getGray(i+2, j+-2) > cb) 
                          if (fb.getGray(i+3, j+-1) > cb) 
                           return true;
                          else
                           return false;
                         else
                          return false;
                       else
                        return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                 else
                  return false;
                else
                 return false;
               else
                return false;
              else if (fb.getGray(i+-1, j+3) < c_b) 
               if (fb.getGray(i+-2, j+2) < c_b)
                if (fb.getGray(i+-3, j+1) < c_b)
                 if (fb.getGray(i+-3, j+0) < c_b)
                  if (fb.getGray(i+-3, j+-1) < c_b)
                   if (fb.getGray(i+-2, j+-2) < c_b)
                    if (fb.getGray(i+-1, j+-3) < c_b)
                     if (fb.getGray(i+0, j+-3) < c_b)
                      if (fb.getGray(i+1, j+-3) < c_b)
                       if (fb.getGray(i+2, j+-2) < c_b)
                        if (fb.getGray(i+3, j+-1) < c_b)
                         return true;
                        else
                         if (fb.getGray(i+1, j+3) < c_b)
                          if (fb.getGray(i+0, j+3) < c_b)
                           return true;
                          else
                           return false;
                         else
                          return false;
                       else
                        if (fb.getGray(i+2, j+2) < c_b)
                         if (fb.getGray(i+1, j+3) < c_b)
                          if (fb.getGray(i+0, j+3) < c_b)
                           return true;
                          else
                           return false;
                         else
                          return false;
                        else
                         return false;
                      else
                       return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                 else
                  return false;
                else
                 return false;
               else
                return false;
              else
               return false;
            else
             if (fb.getGray(i+0, j+3) > cb) 
              if (fb.getGray(i+-1, j+3) > cb) 
               if (fb.getGray(i+-2, j+2) > cb) 
                if (fb.getGray(i+-3, j+1) > cb) 
                 if (fb.getGray(i+-3, j+0) > cb) 
                  if (fb.getGray(i+-3, j+-1) > cb) 
                   if (fb.getGray(i+-2, j+-2) > cb) 
                    if (fb.getGray(i+-1, j+-3) > cb) 
                     if (fb.getGray(i+0, j+-3) > cb) 
                      if (fb.getGray(i+1, j+3) > cb) 
                       if (fb.getGray(i+2, j+2) > cb) 
                        if (fb.getGray(i+3, j+1) > cb) 
                         return true;
                        else
                         if (fb.getGray(i+1, j+-3) > cb) 
                          return true;
                         else
                          return false;
                       else
                        if (fb.getGray(i+1, j+-3) > cb) 
                         if (fb.getGray(i+2, j+-2) > cb) 
                          return true;
                         else
                          return false;
                        else
                         return false;
                      else
                       if (fb.getGray(i+1, j+-3) > cb) 
                        if (fb.getGray(i+2, j+-2) > cb) 
                         if (fb.getGray(i+3, j+-1) > cb) 
                          return true;
                         else
                          return false;
                        else
                         return false;
                       else
                        return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                 else
                  return false;
                else
                 return false;
               else
                return false;
              else
               return false;
             else if (fb.getGray(i+0, j+3) < c_b) 
              if (fb.getGray(i+-1, j+3) < c_b)
               if (fb.getGray(i+-2, j+2) < c_b)
                if (fb.getGray(i+-3, j+1) < c_b)
                 if (fb.getGray(i+-3, j+0) < c_b)
                  if (fb.getGray(i+-3, j+-1) < c_b)
                   if (fb.getGray(i+-2, j+-2) < c_b)
                    if (fb.getGray(i+-1, j+-3) < c_b)
                     if (fb.getGray(i+0, j+-3) < c_b)
                      if (fb.getGray(i+1, j+3) < c_b)
                       if (fb.getGray(i+2, j+2) < c_b)
                        if (fb.getGray(i+3, j+1) < c_b)
                         return true;
                        else
                         if (fb.getGray(i+1, j+-3) < c_b)
                          return true;
                         else
                          return false;
                       else
                        if (fb.getGray(i+1, j+-3) < c_b)
                         if (fb.getGray(i+2, j+-2) < c_b)
                          return true;
                         else
                          return false;
                        else
                         return false;
                      else
                       if (fb.getGray(i+1, j+-3) < c_b)
                        if (fb.getGray(i+2, j+-2) < c_b)
                         if (fb.getGray(i+3, j+-1) < c_b)
                          return true;
                         else
                          return false;
                        else
                         return false;
                       else
                        return false;
                     else
                      return false;
                    else
                     return false;
                   else
                    return false;
                  else
                   return false;
                 else
                  return false;
                else
                 return false;
               else
                return false;
              else
               return false;
             else
              return false;
    }
    
    private static List<FeaturePoint> nonMaxSuppression(int width, int height, List<FeaturePoint> features){
            int[][] pixels = new int[height][width];
            ArrayList<FeaturePoint> nonMaxFeatures = new ArrayList<FeaturePoint>();
            
            for (int i = 0; i < features.size(); ++i) {
                    FeaturePoint fp = features.get(i);
                    pixels[fp.x][fp.y] = fp.score;
            }
            
            for (int i = 0; i < features.size(); ++i) {
                    FeaturePoint fp = features.get(i);
                    int x = fp.x;
                    int y = fp.y;
                    int score = fp.score;
                    if (score >= pixels[x-1][y+1] && score >= pixels[x-1][y] &&
                        score >= pixels[x-1][y-1] && score >= pixels[x][y+1] && 
                        score >= pixels[x][y-1] && score >= pixels[x+1][y+1] && 
                        score >= pixels[x+1][y] && score >= pixels[x+1][y-1]) {
                            nonMaxFeatures.add(fp);
                    }
            }
            return nonMaxFeatures;
    }
}