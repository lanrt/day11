public class Airplane extends UFOClass implements Enamy{
    private int speed;
    private int score;
    Airplane(){
        image = Main.airplaneimg;
        heigh = image.getHeight();
        width = image.getWidth();
        x = (int)(Math.random() * (Main.WIDTH - width));
        y = - heigh;
        speed = 4;
        score = 3;
        life = 1;
    }
    @Override
    void move() {
        y += speed;
    }

    @Override
    public int addScore() {
        return score;
    }
}
