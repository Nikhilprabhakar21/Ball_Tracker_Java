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
        int redDiff = (int) Math.abs(r - targetPoint.getX());
        int greenDiff = (int) Math.abs(g - targetPoint.getY());
        int blueDiff = (int) Math.abs(b - targetPoint.getZ());
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
