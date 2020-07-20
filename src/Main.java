import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
//未完成
public class Main extends JPanel {
    public static BufferedImage hero0;
    public static BufferedImage hero1;
    public static BufferedImage beeimg;
    public static BufferedImage airplaneimg;
    public static BufferedImage bigplaneimg;
    public static BufferedImage bulletimg;
    public static BufferedImage[] airash = new BufferedImage[4];
    static {
        try {
            hero0 = ImageIO.read(Main.class.getResourceAsStream("shooter/hero0.png"));
            hero1 = ImageIO.read(Main.class.getResourceAsStream("shooter/hero1.png"));
            beeimg = ImageIO.read(Main.class.getResourceAsStream("shooter/bee.png"));
            airplaneimg = ImageIO.read(Main.class.getResourceAsStream("shooter/airplane.png"));
            bigplaneimg = ImageIO.read(Main.class.getResourceAsStream("shooter/bigplane.png"));
            bulletimg = ImageIO.read(Main.class.getResourceAsStream("shooter/bullet.png"));
            for (int i = 0; i < 4; i++) {
                airash[i] = ImageIO.read(Main.class.getResourceAsStream("shooter/airplane_ember"+i+".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Hero hero = new Hero();
    ArrayList<UFOClass> Flying = new ArrayList<UFOClass>();
    ArrayList<Bullet> bullet = new ArrayList<Bullet>();
    ArrayList<Ash> ashes = new ArrayList<Ash>();
    private final static int START = 0;
    private final static int PAUSE = 1;
    private final static int GAMEOVER = 2;
    private final static int RUNNING = 3;

    private static int STATE = START;

    public static final int WIDTH = 400;
    public static final int HEIGHT = 650;
    public static void main(String[] args) {
        JFrame window = new JFrame("飞机大战");
        Main panel = new Main();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(WIDTH,HEIGHT);
        window.add(panel);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        panel.action();
    }


    private  Timer timer = new Timer();
    public void action (){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(STATE == RUNNING) {
                    creatUFO();
                    creatBullet();
                    moveBullte();
                    moveUFO();
                    removebullet();
                    removeUFO();
                    crash();
                    herocrash();
                    Ashmove();
                }
                hero.move();
                repaint();
            }
        },0,20);
        MouseAdapter  adapter = new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                if(STATE == PAUSE){
                    STATE = RUNNING;
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(STATE == RUNNING){
                    STATE = PAUSE;
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if(STATE == RUNNING) {
                    hero.x = e.getX() - hero.width / 2;
                    hero.y = e.getY() - hero.heigh / 2;
                    repaint();
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if(STATE == START) {
                    STATE = RUNNING;
                }else if(STATE == GAMEOVER){
                    STATE = START;
                    hero = new Hero();
                    for (int i = Flying.size() - 1; i >= 0 ; i--) {
                        Flying.remove(i);
                    }
                    for (int i = bullet.size() - 1; i >= 0 ; i--) {
                        bullet.remove(i);
                    }
                }
            }
        };
        this.addMouseListener(adapter);
        this.addMouseMotionListener(adapter);
    }

    private void Ashmove() {
        for (int i = 0; i < ashes.size(); i++) {
            if(ashes.get(i).move() == 0){
                ashes.remove(i);
            }else{
                i --;
            }
        }
    }


    private void herocrash() {
        for (int i = 0; i < Flying.size(); i++) {
            int fx = Flying.get(i).x;
            int fy = Flying.get(i).y;
            x:for (int k = fx; k < fx + Flying.get(i).width; k++) {
                if (k > hero.x && k < hero.x + hero.width) {
                    for (int l = fy; l < fy + Flying.get(i).heigh; l++) {
                        if (l > hero.y && l < hero.y + hero.heigh) {
                            Flying.remove(i);
                            hero.life --;
                            i --;
                            break x;
                        }
                    }
                }
            }
            if (hero.life <= 0) {
                STATE = GAMEOVER;
            }
        }
    }

    private void crash() {
        for (int i = 0; i < bullet.size(); i++) {
            int bx = bullet.get(i).x;
            int by = bullet.get(i).y;
            for (int j = 0; j < Flying.size(); j++) {
                UFOClass fly = Flying.get(j);
                int fx = fly.x;
                int fy = fly.y;
                if(bx < fx + fly.width && bx > fx && by <= fy + fly.heigh){
                    bullet.remove(i);
                    Flying.get(j).life --;
                    if(fly.life == 0){
                        Ash ash = new Ash(fly);
                        ashes.add(ash);
                        if(fly instanceof Enamy){
                            Enamy enamy = (Enamy) fly;
                            hero.score += enamy.addScore();
                        }
                        if(fly instanceof Award){
                            Award award = (Award) fly;
                            if(award.getAward() == 0){
                                hero.awardcount = 5;
                            }else if(award.getAward() == 1){
                                hero.life ++;
                            }
                        }
                        Flying.remove(j);
                    }
                    i --;
                    break;
                }
            }
        }
    }

    private void removebullet() {
        for (int i = 0; i < bullet.size(); i++) {
                if (bullet.get(i).y < 50) {
                    bullet.remove(i);
                    i --;
            }
        }
    }

    private void moveBullte() {
        for (int i = 0; i < bullet.size(); i++) {
            bullet.get(i).move();
        }
    }

    private int bulletCount = 0;
//    private int bulletspeed = 20;
    private void creatBullet() {
        bulletCount ++;
        if(bulletCount % 20 == 0) {
            Bullet[] b = hero.shoot();
            for (int i = 0; i < b.length; i++) {
                bullet.add(b[i]);
            }
        }
    }

    private void removeUFO() {
        for (int i = 0; i < Flying.size(); i++) {
            if (Flying.get(i).y > Main.HEIGHT) {
                Flying.remove(i);
                i --;
            }
        }
    }

    private int movecount = 0;
    private void moveUFO() {
        movecount ++;
        if(movecount % 1 == 0) {
            for (int i = 0; i < Flying.size(); i++) {
                Flying.get(i).move();
            }
        }
    }

    private int UFOcount = 0;
    private void creatUFO() {
        UFOcount ++;
        if(UFOcount % 25 == 0){
            int ran = (int)(Math.random() * 20);
            UFOClass ufo;
            if(ran <= 1){
                ufo = new Bee();
            }else if(ran <= 3){
                ufo = new BigPlane();
            }else{
                ufo = new Airplane();
            }
            Flying.add(ufo);
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        BufferedImage background = null;
        try {
            background = ImageIO.read(Main.class.getResourceAsStream("shooter/background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(background, 0, 0, this);
        g.drawImage(hero.image, hero.x, hero.y, this);

        paintFlying(g);
        paintbullet(g);
        paintstate(g);
        paintash(g);
    }

    private void paintstate(Graphics g) {
        BufferedImage start = null;
        BufferedImage pause = null;
        BufferedImage gameover = null;
        try {
            start = ImageIO.read(Main.class.getResourceAsStream("shooter/start.png"));
            pause = ImageIO.read(Main.class.getResourceAsStream("shooter/pause.png"));
            gameover = ImageIO.read(Main.class.getResourceAsStream("shooter/gameover.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(STATE == START){
            g.drawImage(start,0,0,this);
        }else if(STATE == PAUSE){
            g.drawImage(pause,0,0,this);
        }else if(STATE == GAMEOVER){
            g.drawImage(gameover,0,0,this);
        }else if(STATE == RUNNING){
            g.setFont(new Font("宋体",Font.BOLD,20));
            g.drawString("血量： " + hero.life,5,30);
            g.drawString("积分： " + hero.score,5,60);
        }
    }

    private void paintbullet(Graphics g) {
        for (int i = 0; i < bullet.size(); i++) {
            g.drawImage(bullet.get(i).image,bullet.get(i).x,bullet.get(i).y,this);
        }
    }

    private void paintash(Graphics g) {
        for (int i = 0; i < ashes.size(); i++) {
            g.drawImage(ashes.get(i).image,ashes.get(i).x,ashes.get(i).y,this);
        }
    }

    private void paintFlying(Graphics g) {
        for (int i = 0; i < Flying.size(); i++) {
            g.drawImage(Flying.get(i).image,Flying.get(i).x,Flying.get(i).y,this);
        }
    }
}
