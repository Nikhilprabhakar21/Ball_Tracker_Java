package Filters;

import Interfaces.PixelFilter;
import core.DImage;

public class ColorMasking implements PixelFilter {
    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();

        int threshold = 15;
        int target = 348;

//        for (int r = 0; r < red.length; r++) {
//            for (int c = 0; c < red[0].length; c++) {
//                int dist = calculateDistance(red[r][c], green[r][c], blue[r][c], targetR, targetG, targetB);
//
//                if (dist < threshold) {
//                    red[r][c] = 255;
//                    green[r][c] = 255;
//                    blue[r][c] = 255;
//                } else {
//                    red[r][c] = 0;
//                    green[r][c] = 0;
//                    blue[r][c] = 0;
//                }
//            }
//        }

        System.out.println(findHue(175, 78, 97));

        for (int r = 0; r < red.length; r++) {
            for (int c = 0; c < red[0].length; c++) {
                int hue = (int)findHue(red[r][c], green[r][c], blue[r][c]);
                if (hue > target + threshold || hue < target - threshold){
                    red[r][c] = 0;
                    green[r][c] = 0;
                    blue[r][c] = 0;
                } else {
                    red[r][c] = 255;
                    blue[r][c] = 255;
                    green[r][c] = 255;
                }
            }
        }

        // Do stuff with color channels here
        img.setColorChannels(red, green, blue);
        return img;
    }


    //equation source: https://stackoverflow.com/questions/23090019/fastest-formula-to-get-hue-from-rgb
    public double findHue(int r, int g, int b){

        double hue = 0;
        if (r > g && r > b){
            int min = Math.min(g,b);
            hue = (double) (g - b) /(r-min);
        }
        if (g > r && g > b){
            int min = Math.min(r,b);
            hue = 2.0 + (double) (b - r) /(g-min);
        }
        if (b > r && b > g){
            int min = Math.min(g,r);
            hue = 4.0 + (double) (r - g) /(b-min);
        }
        if (hue < 0){
            hue *= 60;
            hue += 360.0;
            return hue;
        }
        
        return hue*60;
    }

    public int calculateDistance(int r, int g, int b, int r1, int g1, int b1) {
        return (int) Math.sqrt((r1 - r) * (r1 - r) + (g1 - g) * (g1 - g) + (b1 - b) * (b1 - b));
    }
}