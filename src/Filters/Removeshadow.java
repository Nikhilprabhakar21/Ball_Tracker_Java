package Filters;

import Interfaces.PixelFilter;
import core.DImage;

public class Removeshadow implements PixelFilter {

    @Override
    public DImage processImage(DImage img) {
        short[][] pixels = img.getBWPixelGrid();
        for (int r = 0; r < pixels.length; r++) {
            for (int c = 0; c < pixels[0].length; c++) {
                if (pixels[r][c] < 60){
                    pixels[r][c] = 0;
                }
            }
        }


        img.setPixels(pixels);
        return img;
    }
}

