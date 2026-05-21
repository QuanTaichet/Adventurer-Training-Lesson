import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Arrow extends Entity {
    GamePanel gp;
    BufferedImage image;
    public boolean active = true;
    public boolean isDropped = false;
    
    public Arrow(GamePanel gp) {
        this.gp = gp;
        speed = 10;
        
        solidArea = new Rectangle(0, 0, 16, 16); 
        
        getArrowImage();
    }

    public void getArrowImage() {
        try {
            image = ImageIO.read(new File("res\\item\\Arrow02.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {

        if (direction.equals("right")) x += speed;
        else if (direction.equals("left")) x -= speed;

        for (Platform p : gp.platforms) {
            if (p != null) {
                
                if (getBounds().intersects(p.getBounds())) {
                    stopAndDrop();
                    return;
                }
            }
        }

        for (Skeleton s : gp.enemies) {
            if (s != null && !s.state.equals("dead")) {
                if (getBounds().intersects(s.getBounds())) {
                    s.damageEnemy(1);
                    stopAndDrop();
                    return;
                }
            }
        }

        if (gp.boss != null && !gp.boss.state.equals("death")) {
            if (getBounds().intersects(gp.boss.getBounds())) {

                gp.boss.damageEnemy(1); 

                stopAndDrop(); 
                
                return;
            }
        }

        if (x < 0 || x > gp.screenWidth) {
            active = false;
        }
    }

    private void stopAndDrop() {
        active = false; 

        gp.spawnArrowItem(x, y); 
    }

    public void draw(Graphics2D g2) {
        if (active) {
            int finalWidth = 32; 
            int finalHeight = 32;
            int drawX = x;

            if (direction.equals("left")) {
                drawX = x + finalWidth;
                finalWidth = -finalWidth;
            }
            g2.drawImage(image, drawX, y, finalWidth, finalHeight, null);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 16, 16);
    }
}