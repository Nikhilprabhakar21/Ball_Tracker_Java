package Filters;

import Interfaces.PixelFilter;
import core.DImage;

public class Border implements PixelFilter {

    @Override
    public DImage processImage(DImage img) {
//        int border = 10;
//
//        short[][] pixels = img.getBWPixelGrid();
//        short[][] pi1 = new short[pixels.length + border*2][pixels[0].length + border*2];
//
//        int ir = 0;
//
//        for (int r = 0; r < pi1.length; r++) {
//            int ic = 0;  // Reset the column index for each row
//            for (int c = 0; c < pi1[0].length; c++) {
//                if (r >= border && r < pi1.length - border && c >= border && c < pi1[0].length - border) {
//                    pi1[r][c] = pixels[ir][ic];
//                    ic++;
//                }
//            }
//            // Update the row index only if it is within the range of the original pixel array
//            if (r >= border && r < pi1.length - border) {
//                ir++;
//            }f
//        }



        int border = 10;

        short[][] pixels = img.getBWPixelGrid();
        short[][] pi1 = new short[pixels.length + border*2][pixels[0].length + border*2];

        for (int r = 0; r < pixels.length; r++) {
            for (int c = 0; c < pixels[0].length; c++) {
                pi1[border + r][border + c] = pixels[r][c];
            }

        }

        img.setPixels(pi1);
        return img;
    }
}
