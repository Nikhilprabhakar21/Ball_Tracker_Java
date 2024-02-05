package Filters;

import Interfaces.PixelFilter;
import core.DImage;

public class Downsizing implements PixelFilter {


    @Override
    public DImage processImage(DImage img) {
        short[][] pixels = img.getBWPixelGrid();
        short[][] pi1 = new short[pixels.length/2][pixels[0].length/2];

        for (int r = 0; r < pi1.length; r++) {
            for (int c = 0; c < pi1[0].length; c++) {
                short average = (short) ((pixels[r*2][c*2] + pixels[r*2+1][c*2] + pixels[r*2][c*2+1] + pixels[r*2+1][c*2+1])/4);
                pi1[r][c] = average;
            }
        }

        img.setPixels(pi1);
        return img;
    }
}