package Filters;

import Interfaces.Interactive;
import Interfaces.PixelFilter;
import core.DImage;
import core.Point;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BallTracking implements PixelFilter, Interactive {
    private ArrayList<Point> targetPoints;
    private ArrayList<ArrayList<Point>> maskedPointList;
    private int sensitivity = 10;

    public BallTracking() {
        targetPoints = new ArrayList<>();
        maskedPointList = new ArrayList<>();
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();

        doMask(red, green, blue);
        for (int i = 0; i < maskedPointList.size(); i++) {
            Point middle = calcMidP(maskedPointList.get(i));
            makeSquare(middle.getRow(), middle.getColumn(), red, green, blue, 5);
        }

        img.setColorChannels(red, green, blue);
        return img;
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

    private void doMask(short[][] red, short[][] green, short[][] blue) {
        for (int r = 0; r < red.length; r++) {
            for (int c = 0; c < red[0].length; c++) {
                for (int i = 0; i < targetPoints.size(); i++) {
                    if (isColorWithinRange(red[r][c], green[r][c], blue[r][c], targetPoints.get(i))) {
                        //red[r][c] = green[r][c] = blue[r][c] = 0; // Mark matching pixels
                        Point P = new Point(red[r][c], green[r][c], blue[r][c], c, r);
                        maskedPointList.get(i).add(P);
                    } else {
                        //red[r][c] = green[r][c] = blue[r][c] = 255; // Non-matching pixels
                    }
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

    private boolean isColorWithinRange(int r, int g, int b, Point targetPoint) {
        int redDiff = (int) Math.abs(r - targetPoint.getX());
        int greenDiff = (int) Math.abs(g - targetPoint.getY());
        int blueDiff = (int) Math.abs(b - targetPoint.getZ());
        return redDiff <= sensitivity && greenDiff <= sensitivity && blueDiff <= sensitivity;
    }

    public void mouseClicked(int mouseX, int mouseY, DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();
        targetPoints.add(new Point(red[mouseY][mouseX], green[mouseY][mouseX], blue[mouseY][mouseX], mouseY, mouseX));
        maskedPointList.add(new ArrayList<Point>());
    }

    public void keyPressed(char key) {
        if (key == '+') sensitivity += 5;
        if (key == '-') sensitivity -= 5;
        if (key == 'r') {
            targetPoints.remove(targetPoints.size()-1);
            maskedPointList.remove(maskedPointList.size()-1);
        }
    }
}
