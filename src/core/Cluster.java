package core;

import java.util.ArrayList;
import java.util.List;

public class Cluster {
    private Point centroid;
    private ArrayList<Point> points;

    public Cluster(Point centroid) {
        this.centroid = centroid;
        this.points = new ArrayList<>();
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void clearPoints() {
        points.clear();
    }

    public void assignPoints(ArrayList<Point> n, Cluster c) {
        for(Point p : n){
            if (p.getDisplacement(this) < 30){
                c.points.add(p);
            }
        }
    }



    public Point getCenter(){
        return this.centroid;
    }

    public void recalculateCentroid() {

        double sum1 = 0;
        double sum2 = 0;
        double sum3 = 0;
        for (int i = 0; i < points.size(); i++) {
            sum1 += points.get(i).getX();
            sum2 += points.get(i).getY();
            sum3 += points.get(i).getZ();
        }
        double average1 = sum1/points.size();
        double average2 = sum2/points.size();
        double average3 = sum3/points.size();

        centroid.setX((int)average1);
        centroid.setY((int)average2);
        centroid.setZ((int)average3);
        // Implementation logic to recalculate the centroid
    }
}

