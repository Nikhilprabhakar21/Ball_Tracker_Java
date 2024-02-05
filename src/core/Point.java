package core;
public class Point {
    private int x;
    private int y;
    private int z;
    private int row;
    private int column;
    private int heightval;
    public Point(int s, int p, int b){
        this.y = p;
        this.x = s;
        this.z = b;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setHeightval(int heightval) {
        this.heightval = heightval;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public double getDisplacement(Cluster p){
        double xC = p.getCenter().getX();
        double yC = p.getCenter().getY();
        double zC = p.getCenter().getZ();
        return (Math.sqrt(Math.pow((xC - this.x),2)+Math.pow((yC - this.y),2)+Math.pow((zC - this.z),2)));
    }
}
