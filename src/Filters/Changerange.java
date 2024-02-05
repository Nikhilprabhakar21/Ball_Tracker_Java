package Filters;

import Interfaces.PixelFilter;
import core.DImage;
import java.util.Scanner;  // Import the Scanner class


public class Changerange implements PixelFilter {

    int numgsval;

    @Override
    public DImage processImage(DImage img) {
        short[][] pixels = img.getBWPixelGrid();
        int regionsize = 255/numgsval;

        for (int r = 0; r < pixels.length; r++) {
            for (int c = 0; c < pixels[0].length; c++) {
                for (int i = 0; i < numgsval; i++) {
                    if (pixels[r][c] >= i * regionsize && pixels[r][c] < (i+1) * regionsize){
                        pixels[r][c] = (short) (regionsize * (i + 0.5));
                    }
                }
            }
        }

        img.setPixels(pixels);
        return img;
    }
    public Changerange(){
        Scanner myObj = new Scanner(System.in);
        System.out.println("enter a number");
        int n = myObj.nextInt();
        this.numgsval = n;
    }
}