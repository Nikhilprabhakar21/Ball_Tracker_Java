package Filters;

import Interfaces.PixelFilter;
import core.DImage;

public class Swap implements PixelFilter {
    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();

        red = new short[img.getHeight()][img.getWidth()];

        short[][] tmp = new short[img.getHeight()][img.getWidth()];
        tmp = green;

        green = blue;
        blue = tmp;

//        for (int r = 0; r < red.length; r++) {
//            for (int c = 0; c < red[0].length; c++) {
//                red[r][c] = 0;
//            }
//
//        }

        // Do stuff with color channels here
        img.setColorChannels(red, green, blue);
        return img;
    }
}
