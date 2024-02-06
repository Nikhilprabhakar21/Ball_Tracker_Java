package core;
public class Point {
    private int r;
    private int g;
    private int b;
    private int row;
    private int column;
    private int heightval;
    private int Hue;
    private double saturation;
    private int xPos;
    private int yPos;
    public Point(int s, int p, int b, int posx, int posy){
        this.r = p;
        this.g = s;
        this.b = b;
        this.row = posx;
        this.column = posy;

        this.Hue = (int) calcHue(this.r, this.g, this.b);
        this.saturation = 0;
    }

    public int getX() {
        return r;
    }

    public int getY() {
        return g;
    }

    public int getZ() {
        return b;
    }
    public int getRow(){return row;}
    public int getColumn(){return column;}

    public int getHue() {
        return Hue;
    }

    public double getSaturation() {
        return saturation;
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
        this.r = x;
    }

    public void setY(int y) {
        this.g = y;
    }

    public void setZ(int z) {
        this.b = z;
    }

    public double getDisplacement(Cluster p){
        double xC = p.getCenter().getX();
        double yC = p.getCenter().getY();
        double zC = p.getCenter().getZ();
        return (Math.sqrt(Math.pow((xC - this.r),2)+Math.pow((yC - this.g),2)+Math.pow((zC - this.b),2)));
    }
    public void changeSaturation(double amtChange){
        this.saturation = amtChange;
    }

    public double calcHue(int red, int green, int blue){
        double red1 = (double) red /255;
        double blue1 = (double) blue /255;
        double green1 = (double) green /255;
        double returnhue = 0;
        if (red1 > blue1 && red1 > green1 /*red is max*/){
            returnhue = (green1 - blue1)/(red1 - Math.min(green1, blue1));
        } if (green > red && green1 > blue){
            returnhue = 2 + (blue1 - red1)/(green1 - Math.min(blue1, red1));
        } if (blue > red && blue1 > green){
            returnhue = 4 + (red1 - green1)/(green1 - Math.min(red1, green1));
        }

        if (returnhue < 0){
            returnhue += 360;
        }
        return returnhue*60;
    }
}
