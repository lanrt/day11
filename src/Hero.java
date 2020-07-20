import java.awt.image.BufferedImage;

public class Hero extends UFOClass{
    protected int life;
    protected int score;
    protected int awardcount;

    Hero(){
        image = Main.hero0;
        width = image.getWidth();
        heigh = image.getHeight();
        life = 3;
        awardcount = 0;
        x = Main.WIDTH / 2 - width / 2;
        y = 500;
    }

    public Bullet[] shoot(){
        if(awardcount > 0){
            Bullet[] bullet = new Bullet[2];
            bullet[0] = new Bullet(x + 10,y - 10);
            bullet[1] = new Bullet(x + width - 20,y - 10);
            awardcount -- ;
            return  bullet;
        }else{
            Bullet[] bullet = new Bullet[1];
            bullet[0] = new Bullet(x + width / 2 - 3,y - 10);
            return  bullet;
        }
    }

    private int count = 0;
    @Override
    void move() {
        //两张图片轮换
        count ++;
        if(count % 2 == 0){
            image = Main.hero0;
        }else{
            image = Main.hero1;
        }
    }
}
