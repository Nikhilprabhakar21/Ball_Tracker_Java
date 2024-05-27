package Filters;


import Interfaces.Interactive;
import Interfaces.PixelFilter;
import core.DImage;
import core.Point;

import java.util.ArrayList;

public class BallTrackingHSV implements PixelFilter, Interactive {
    private Point targetPoint;
    private int sensitivity = 10;

    public BallTrackingHSV() {
        targetPoint = new Point(0, 0, 0, 0, 0);
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();

        ArrayList<Point> maskedColors = new ArrayList<>();

        doMask(red, green, blue, maskedColors);
        Point middle = calcMidP(maskedColors);
        makeSquare(middle.getRow(), middle.getColumn(), red, green, blue, 10);



        img.setColorChannels(red, green, blue);
        return img;
    }

    // called on the midpoint of the masked area
    public void makeSquare(int r, int c, short[][] red, short[][] green, short[][] blue, int size){
        if (r - size > size && r + size < red.length && c - size > 0 && c + size < red[0].length) {
            for (int i = -size; i < size; i++) {
                for (int j = -size; j < size; j++) {
                    red[r + i][c + j] = 255;
                    green[r + i][c + j] = 30;
                    blue[r + i][c + j] = 255;
                }
            }
        }
    }

    private void doMask(short[][] red, short[][] green, short[][] blue, ArrayList<Point> maskedColors) {
        // Compares the HSV values of all pixels in the image against the clicked targetPoint. Add pixels that are
        // close to maskedColors and mark them as black. Mark all other pixels as white.

        // red, green, blue -> RGB channels of the entire image
        // targetPoint -> set to point where mouse clicked
        float[] targetHSV = rgbToHsv((int)targetPoint.getX(), (int)targetPoint.getY(), (int)targetPoint.getZ());

        for (int r = 0; r < red.length; r++) {
            for (int c = 0; c < red[0].length; c++) {
                float[] hsv = rgbToHsv(red[r][c], green[r][c], blue[r][c]);
                if (Math.abs(hsv[0] - targetHSV[0]) <= sensitivity) {
                    red[r][c] = green[r][c] = blue[r][c] = 0; // Mark matching pixels
                    Point P = new Point(red[r][c], green[r][c], blue[r][c], c, r);
                    maskedColors.add(P);
                } else {
                    red[r][c] = green[r][c] = blue[r][c] = 255; // Non-matching pixels
                }
            }
        }
    }

    private Point calcMidP(ArrayList<Point> maskedColors) {
        // Return a white point at the center of the masked area.
        int row = 0;
        int col = 0;
        for (int i = 0; i < maskedColors.size(); i++) {
            row += maskedColors.get(i).getColumn();
            col += maskedColors.get(i).getRow();
        }
        if (maskedColors.size() == 0){
            row = 1;
            col = 1;
        } else {
            row = row/maskedColors.size();
            col = col/maskedColors.size();
        }

        Point P = new Point(255,255,255,row,col);
        return P;
    }


    public void mouseClicked(int mouseX, int mouseY, DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();
        targetPoint = new Point(red[mouseY][mouseX], green[mouseY][mouseX], blue[mouseY][mouseX], mouseY, mouseX);
    }

    public void keyPressed(char key) {
        if (key == '+') sensitivity += 5;
        if (key == '-') sensitivity -= 5;
    }

    private float[] rgbToHsv(double r, double g, double b) {
        // R, G, B values are divided by 255
        // to change the range from 0..255 to 0..1
        r = r / 255.0;
        g = g / 255.0;
        b = b / 255.0;

        // h, s, v = hue, saturation, value
        double cmax = Math.max(r, Math.max(g, b)); // maximum of r, g, b
        double cmin = Math.min(r, Math.min(g, b)); // minimum of r, g, b
        double diff = cmax - cmin; // diff of cmax and cmin.
        double h = -1, s = -1;

        // if cmax and cmax are equal then h = 0
        if (cmax == cmin)
            h = 0;

            // if cmax equal r then compute h
        else if (cmax == r)
            h = (60 * ((g - b) / diff) + 360) % 360;

            // if cmax equal g then compute h
        else if (cmax == g)
            h = (60 * ((b - r) / diff) + 120) % 360;

            // if cmax equal b then compute h
        else if (cmax == b)
            h = (60 * ((r - g) / diff) + 240) % 360;

        // if cmax equal zero
        if (cmax == 0)
            s = 0;
        else
            s = (diff / cmax) * 100;

        // compute v
        double v = cmax * 100;


        return new float[]{(float) h, (float) s, (float) v}; // Placeholder
    }
}
