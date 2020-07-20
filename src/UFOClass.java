import java.awt.image.BufferedImage;

public abstract class UFOClass {
    protected int x;
    protected int y;
    protected BufferedImage image;
    protected int width;
    protected int heigh;
    protected int life;

    abstract void move();
}
