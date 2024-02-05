package Filters;

import Interfaces.PixelFilter;
import core.DImage;

public class Lighten implements PixelFilter {

@Override
public DImage processImage(DImage img) {
        short[][] pixels = img.getBWPixelGrid();
    for (int r = 0; r < pixels.length; r++) {
        for (int c = 0; c < pixels[0].length; c++) {
            float proportion = (float) pixels[r][c] / 255;
            int temp = (int) (50 * proportion);
            pixels[r][c] = (short) (temp + 205);
        }
    }


    img.setPixels(pixels);
    return img;
    }
}

