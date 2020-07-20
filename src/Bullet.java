import java.awt.image.BufferedImage;

public class Bullet extends UFOClass{
    private int speed;
    Bullet(int x, int y){
        this.x = x;
        this.y = y;
        image = Main.bulletimg;
        speed = 4;
    }
    @Override
    void move() {
        y -= speed;
    }
}
