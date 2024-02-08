package core;
public class Point {
    private double r;
    private double g;
    private double b;
    private int row;
    private int column;
    private double Hue;
    private double saturation;
    private int xPos;
    private int yPos;
    public Point(int s, int p, int b, int posx, int posy){
        this.r = p;
        this.g = s;
        this.b = b;
        this.row = posx;
        this.column = posy;

        this.Hue = calcHue(this.r, this.g, this.b, "H");
        this.saturation = calcHue(this.r,this.g,this.b,"S");
    }

    public double getX() {
        return r;
    }

    public double getY() {
        return g;
    }

    public double getZ() {
        return b;
    }
    public int getRow(){return row;}
    public int getColumn(){return column;}

    public double getHue() {
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

    public double calcHue(double r, double g, double b, String type) {
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

        if (type.equals("H")) {
            return h;
        } else if (type.equals("S")) {
            return s;

        } else {
            return 0;
        }

    }
}
