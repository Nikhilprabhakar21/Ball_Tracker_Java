package Filters;

import Interfaces.Interactive;
import Interfaces.PixelFilter;
import core.DImage;
import core.Point;

import java.util.ArrayList;

public class ballTracking implements PixelFilter, Interactive {
    Point p;

    public ballTracking() {
        p = new Point(0,0,0,0,0);
    }
    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();

        ArrayList<Point> points = new ArrayList<>();


        for (int r = 0; r < red.length; r++) {
            for (int c = 0; c < red[0].length; c++) {
                points.add(new Point(red[r][c], blue[r][c], green[r][c], r, c));
            }
        }

        for (int i = 0; i < points.size(); i++) {
            if (points.get(i).getHue() == p.getHue()){
                red[points.get(i).getRow()][points.get(i).getColumn()] = 0;
                green[points.get(i).getRow()][points.get(i).getColumn()] = 0;
                blue[points.get(i).getRow()][points.get(i).getColumn()] = 0;
                System.out.println("set to black: " + points.get(i).getRow() + " " + points.get(i).getColumn());
            } else {
                red[points.get(i).getRow()][points.get(i).getColumn()] = 255;
                green[points.get(i).getRow()][points.get(i).getColumn()] = 255;
                blue[points.get(i).getRow()][points.get(i).getColumn()] = 255;
            }
        }

        // Do stuff with color channels here
        img.setColorChannels(red, green, blue);
        return img;
    }

    public void mouseClicked(int mouseX, int mouseY, DImage img){
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();
        p = new Point(red[mouseY][mouseX], green[mouseY][mouseX], blue[mouseY][mouseX], mouseY, mouseX);
    }
    public void keyPressed(char key){

    }
}
