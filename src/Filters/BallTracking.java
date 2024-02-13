package Filters;

import Interfaces.Interactive;
import Interfaces.PixelFilter;
import core.DImage;
import core.*;

import java.util.ArrayList;

public class BallTracking implements PixelFilter, Interactive {
    private ArrayList<Point> targetColors;
    private int sensitivity = 10;

    public BallTracking() {
        targetColors = new ArrayList<>();
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();

        ArrayList<Point> maskedPixels = new ArrayList<>();

        doMask(red, green, blue, maskedPixels, targetColors);
//        Point middle = calcMidP(maskedPixels);
//        makeSquare(middle.getRow(), middle.getColumn(), red, green, blue, 5);

        img.setColorChannels(red, green, blue);
        return img;
    }

    public void makeSquare(int r, int c, short[][] red, short[][] green, short[][] blue, int size){
        for (int i = -1; i < 1; i++) {
            for (int j = -1; j < 1; j++) {
                red[r + i][c + j] = 255;
                green[r + i][c + j] = 30;
                blue[r + i][c + j] = 255;
            }
        }

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

    private void doMask(short[][] red, short[][] green, short[][] blue, ArrayList<Point> maskedPixels, ArrayList<Point> targetColors) {
        for (int r = 0; r < red.length; r++) {
            for (int c = 0; c < red[0].length; c++) {
                if (isColorWithinRange(red[r][c], green[r][c], blue[r][c], targetColors)) {
                    red[r][c] = green[r][c] = blue[r][c] = 0; // Mark matching pixels
                    Point P = new Point(red[r][c], green[r][c], blue[r][c], c, r);
                    maskedPixels.add(P);
                } else {
                    red[r][c] = green[r][c] = blue[r][c] = 255; // Non-matching pixels
                }
            }
        }
    }

    private Point calcMidP(ArrayList<Point> maskedColors) {
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

    private boolean isColorWithinRange(int r, int g, int b, ArrayList<Point> targetColors) {
        if (targetColors.size() > 0){
            for (int i = 0; i < targetColors.size(); i++) {
                int redDiff = (int) Math.abs(r - targetColors.get(i).getX());
                int greenDiff = (int) Math.abs(g - targetColors.get(i).getY());
                int blueDiff = (int) Math.abs(b - targetColors.get(i).getZ());
                return redDiff <= sensitivity && greenDiff <= sensitivity && blueDiff <= sensitivity;
            }
            int redDiff = (int) Math.abs(r - targetColors.get(0).getX());
            int greenDiff = (int) Math.abs(g - targetColors.get(0).getY());
            int blueDiff = (int) Math.abs(b - targetColors.get(0).getZ());
            return redDiff <= sensitivity && greenDiff <= sensitivity && blueDiff <= sensitivity;
        } else {
            Point targetPoint = new Point(0,0,0,0,0);
            int redDiff = (int) Math.abs(r - targetPoint.getX());
            int greenDiff = (int) Math.abs(g - targetPoint.getY());
            int blueDiff = (int) Math.abs(b - targetPoint.getZ());
            return redDiff <= sensitivity && greenDiff <= sensitivity && blueDiff <= sensitivity;
        }

    }

    public void mouseClicked(int mouseX, int mouseY, DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();
        targetColors.add(new Point(red[mouseY][mouseX], green[mouseY][mouseX], blue[mouseY][mouseX], mouseY, mouseX));
    }

    public void keyPressed(char key) {
        if (key == '+') sensitivity += 5;
        if (key == '-') sensitivity -= 5;
        if (key == 'r') targetColors.remove(targetColors.size()-1);

    }
}
