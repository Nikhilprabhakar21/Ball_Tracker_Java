package Filters;

import Interfaces.Interactive;
import Interfaces.PixelFilter;
import core.DImage;
import core.Point;

public class BallTracking implements PixelFilter, Interactive {
    private Point targetPoint;
    private int sensitivity = 10;

    public BallTracking() {
        targetPoint = new Point(0, 0, 0, 0, 0);
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();

        // Convert target RGB to HSV for comparison
        float[] targetHSV = rgbToHsv((int)targetPoint.getX(), (int)targetPoint.getY(), (int)targetPoint.getZ());

        for (int r = 0; r < red.length; r++) {
            for (int c = 0; c < red[0].length; c++) {
                float[] hsv = rgbToHsv(red[r][c], green[r][c], blue[r][c]);
                if (Math.abs(hsv[0] - targetHSV[0]) <= sensitivity) {
                    red[r][c] = green[r][c] = blue[r][c] = 0; // Mark matching pixels
                } else {
                    red[r][c] = green[r][c] = blue[r][c] = 255; // Non-matching pixels
                }
            }
        }

        img.setColorChannels(red, green, blue);
        return img;
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

/*package Filters;

import Interfaces.Interactive;
import Interfaces.PixelFilter;
import core.DImage;
import core.Point;

public class BallTrackingRGB implements PixelFilter, Interactive {
    private Point targetPoint;
    private int sensitivity = 10;

    public BallTrackingRGB() {
        targetPoint = new Point(0, 0, 0, 0, 0);
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();

        for (int r = 0; r < red.length; r++) {
            for (int c = 0; c < red[0].length; c++) {
                if (isColorWithinRange(red[r][c], green[r][c], blue[r][c])) {
                    red[r][c] = green[r][c] = blue[r][c] = 0; // Mark matching pixels
                } else {
                    red[r][c] = green[r][c] = blue[r][c] = 255; // Non-matching pixels
                }
            }
        }

        img.setColorChannels(red, green, blue);
        return img;
    }

    private boolean isColorWithinRange(int r, int g, int b) {
        int redDiff = Math.abs(r - targetPoint.getRed());
        int greenDiff = Math.abs(g - targetPoint.getGreen());
        int blueDiff = Math.abs(b - targetPoint.getBlue());
        return redDiff <= sensitivity && greenDiff <= sensitivity && blueDiff <= sensitivity;
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
}
*/