package Filters;

import Interfaces.PixelFilter;
import core.DImage;

public class Opposite implements PixelFilter {

    @Override
    public DImage processImage(DImage img) {
        short[][] pixels = img.getBWPixelGrid();
        for (int r = 0; r < pixels.length; r++) {
            for (int c = 0; c < pixels[0].length; c++) {
                pixels[r][c] = (short) (255 - pixels[r][c]);
            }
        }

        img.setPixels(pixels);
        return img;
    }
}

