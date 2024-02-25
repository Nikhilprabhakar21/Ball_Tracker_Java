package Filters;

import Interfaces.Interactive;
import Interfaces.PixelFilter;
import core.DImage;
import core.Point;

import java.util.ArrayList;

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

        short[][] red1 = img.getRedChannel();
        short[][] green1 = img.getGreenChannel();
        short[][] blue1 = img.getBlueChannel();

        ArrayList<Point> maskedColors = new ArrayList<>();

        doMask(red, green, blue, maskedColors);
        Point middle = calcMidP(maskedColors);
        makeSquare(middle.getRow(), middle.getColumn(), red, green, blue, 5);
        //bringImageBack(red, green, blue, red1, green1, blue1);

        img.setColorChannels(red, green, blue);
        return img;
    }

    public void bringImageBack(short[][] red, short[][] green, short[][] blue, short[][] redO, short[][] greenO, short[][] blueO){
        for (int r = 0; r < red.length; r++) {
            for (int c = 0; c < red[0].length; c++) {
                if (red[r][c] == blue[r][c] && blue[r][c] == green[r][c]){
                    red[r][c] = redO[r][c];
                    blue[r][c]  = blueO[r][c];
                    green[r][c] = greenO[r][c];
                }
            }
        }
    }

    public void makeSquare(int r, int c, short[][] red, short[][] green, short[][] blue, int size){

        if (r - size > 0 && r + size < red.length && c - size > 0 && c + size < red[0].length) {
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
        for (int r = 0; r < red.length; r++) {
            for (int c = 0; c < red[0].length; c++) {
                if (isColorWithinRange(red[r][c], green[r][c], blue[r][c])) {
                    //red[r][c] = green[r][c] = blue[r][c] = 0; // Mark matching pixels
                    Point P = new Point(red[r][c], green[r][c], blue[r][c], c, r);
                    maskedColors.add(P);
                } else {
                    //red[r][c] = green[r][c] = blue[r][c] = 255; // Non-matching pixels
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
