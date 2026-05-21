
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
    public int x, y;
    public int speed;
    public int ySpeed = 0;
    public BufferedImage idle1, idle2, idle3, idle4;
    public BufferedImage run1, run2, run3, run4;
    public BufferedImage right1, right2, right3, right4;
    public BufferedImage left1, left2, left3, left4;
    public BufferedImage attack1, attack2, attack3, attack4,dead1,dead2,dead3,dead4;
    public String direction = "idle";
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea; 
    public boolean collisionOn = false;
}