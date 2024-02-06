package Filters;

import Interfaces.PixelFilter;
import core.Cluster;
import core.DImage;
import core.Point;

import java.util.ArrayList;
import java.util.List;

public class Kgrouping implements PixelFilter {
    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();

        int amt = 5;

        ArrayList<Point> allPoints = new ArrayList<>();

        for (int r = 0; r < red.length; r++) {
            for (int c = 0; c < red[0].length; c++) {
                allPoints.add(new Point(red[r][c], green[r][c], blue[r][c], r, c));
            }
        }

        ArrayList<Cluster> clusters = new ArrayList<>();

        for (int i = 0; i < amt; i++) {
            Cluster p = new Cluster(new Point((int) (Math.random() * 256), (int) (Math.random() * 256),(int)(Math.random() * 256), 0, 0));
            p.assignPoints(allPoints,p);
            clusters.add(p);
        }

        for (int i = 0; i < 10; i++) {
            Cluster c = clusters.get(i);
            c.recalculateCentroid();
        }

        // Do stuff with color channels here
        img.setColorChannels(red, green, blue);
        return img;
    }
}
