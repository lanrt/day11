import java.awt.image.BufferedImage;

public class Ash{
    private BufferedImage[] images;
    private int index;
    protected BufferedImage image;
    private int interval = 1000000, i;
    protected int x, y;

    Ash(UFOClass ufoClass){
        images = Main.airash;
        image = Main.airash[0];
        this.x = ufoClass.x;
        this.y = ufoClass.y;
        index = 0;
        i = 0;
    }

    int move() {
        i ++;
        if (i % interval == 0) {
            if (index == images.length) {
                return 0;
            }
            image = images[index++];
        }
        return 1;
    }
}
