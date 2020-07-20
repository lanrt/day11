public class BigPlane extends UFOClass implements  Enamy,Award{
    private int blood;
    private int speed;
    private int score;
    private int awardcount;
    BigPlane(){
        image = Main.bigplaneimg;
        heigh = image.getHeight();
        width = image.getWidth();
        x = (int)(Math.random() * (Main.WIDTH - width));
        y = - heigh;
        life = 5;
        score = 10;
        awardcount = (int)(Math.random() * 3);
        speed = 2;
    }
    @Override
    void move() {
        y += speed;
    }

    public int getBlood() {
        return blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }

    @Override
    public int addScore() {
        return score;
    }

    @Override
    public int getAward() {
        return awardcount;
    }
}
