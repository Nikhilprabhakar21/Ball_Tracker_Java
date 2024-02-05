package Filters;

import Interfaces.PixelFilter;
import core.DImage;

import java.awt.image.Kernel;

public class Convolutions implements PixelFilter {
    // horizontal line detection
    private static int[][] oneKernel = { {-1,-1,-1}, {2,2,2}, {-1,-1,-1} };

    // vertical line detection
    private static int[][] twoKernal = { {-1,2,-1}, {-1,2,-1}, {-1,2,-1} };
    private static int[][] threeKernal = { {-1,-1,2}, {-1,2,-1}, {2,-1,-1} };
    private static int[][] fourKernal = { {2,-1,-1}, {-1,2,-1}, {-1,-1,2} };
    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();

        short[][] red2 = img.getRedChannel();
        short[][] blue2 = img.getBlueChannel();
        short[][] green2 = img.getGreenChannel();

        for (int r = 1; r < red.length-1; r++) {
            for (int c = 1; c < red[0].length-1; c++) {
                red2[r][c] = calcAVG(r,c,red);
                blue2[r][c] = calcAVG(r,c,blue);
                green2[r][c] = calcAVG(r,c,green);
            }
        }

        // Do stuff with color channels here
        img.setColorChannels(red2, green2, blue2);
        return img;
    }

    public static short calcAVG(int r, int c, short img[][]){
        double sum = 0;

        // sum up all the kernel * image values
        // BUT: we don't want the sum, we want the average
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sum += twoKernal[i][j]*img[r+i-1][c+j-1] + oneKernel[i][j]*img[r+i-1][c+j-1] + threeKernal[i][j]*img[r+i-1][c+j-1] + fourKernal[i][j]*img[r+i-1][c+j-1];
            }
        }

        // sums up the kernel values
        int kernalSum = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                kernalSum += oneKernel[i][j] + twoKernal[i][j] + threeKernal[i][j] + fourKernal[i][j];
            }
        }
        sum = sum/kernalSum;
        if (sum < 0) sum = 0;
        if (sum > 255) sum = 255;

        return(short)(sum/kernalSum);
    }
}
