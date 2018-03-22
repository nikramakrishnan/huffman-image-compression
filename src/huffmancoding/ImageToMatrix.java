/*
 * Copyright Nikhil Ramakrishnan
 */
package huffmancoding;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
/**
 *
 * @author river
 */
public class ImageToMatrix {
    BufferedImage  image;
   BufferedImage  gray_image;
   int width;
   int height;
   
   /**
    * Convert the given image to grayscale and return the matrix
    * @param imgpath the relative path to the image file
    * @return Grayscale Image matrix representation
    */
   public int[][] convert(String imgpath) {
      int img_arr[][] = new int[1][1];
      try {
	
	 String input_img_path = imgpath; //Input image here
	 String gray_img_path = "grayscale.jpg";
         File input = new File(input_img_path);	
         // Reading image
	 image = ImageIO.read(input);
         width = image.getWidth();
         height = image.getHeight();
	 
         // RGB to Gray scale conversion
         for(int i=0; i<height; i++){         
            for(int j=0; j<width; j++){            
               Color c = new Color(image.getRGB(j, i));
               int red = (int)(c.getRed() * 0.299);
               int green = (int)(c.getGreen() * 0.587);
               int blue = (int)(c.getBlue() *0.114);
               Color newColor = new Color(red+green+blue,               
               red+green+blue,red+green+blue);               
               image.setRGB(j,i,newColor.getRGB());
            }
         }
         // Writing converted image to file
         File ouptut = new File(gray_img_path);
         ImageIO.write(image, "jpg", ouptut);
	 
 
	 // Original image
	 JFrame frame = new JFrame();
	  ImageIcon icon = new ImageIcon(input_img_path);
	  JLabel label = new JLabel(icon);
	  frame.add(label);
	  frame.setDefaultCloseOperation
		 (JFrame.EXIT_ON_CLOSE);
	  frame.pack();
	  frame.setVisible(true);
	  
	 // Converted image
	 JFrame frame1 = new JFrame();
	  ImageIcon icon1 = new ImageIcon(gray_img_path);
	  JLabel label1 = new JLabel(icon1);
	  frame1.add(label1);
	  frame1.setDefaultCloseOperation
		 (JFrame.EXIT_ON_CLOSE);
	  frame1.pack();
	  frame1.setVisible(true);
	 
	  // Grayscale image manipulation
	 gray_image = ImageIO.read(new File(gray_img_path));
         width = gray_image.getWidth();
         height = gray_image.getHeight();
	 Raster raster = gray_image.getData();
	 img_arr = new int[width][height];
         
	 
	 for(int i=0; i<height; i++){         
            for(int j=0; j<width; j++){            
		img_arr[j][i] = raster.getSample(j, i, 0);
                //if(j!=width-1) System.out.print(img_arr[j][i]+" ");
                //else System.out.print(img_arr[j][i]);
            }
             //System.out.println();
         }
         
	 
      } catch (Exception e) {System.out.println("Image conversion error");}
      return img_arr;
   }
   
   /**
    * Get Height of image
    * @return height of image
    */
   public int getHeight(){
       return height;
   }
   
    /**
    * Get width of image
    * @return width of image
    */
   public int getWidth(){
       return width;
   }
}
