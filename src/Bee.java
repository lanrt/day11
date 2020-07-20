public class Bee extends UFOClass implements Award{
    private int speedx;
    private int speedy;
    private  int awardcount;
    Bee(){
        image = Main.beeimg;
        heigh = image.getHeight();
        width = image.getWidth();
        x = (int)(Math.random() * (Main.WIDTH - width));
        y = - heigh;
        life = 1;
        awardcount = (int)(Math.random() * 3);
        speedx = 4;
        speedy = 2;
    }
    @Override
    void move() {
        x += speedx;
        y += speedy;
        if(x >= Main.WIDTH - width){
            speedx = -5;
        }else if(x <= 0){
            speedx = 5;
        }
    }

    @Override
    public int getAward() {
        return awardcount;
    }
}
