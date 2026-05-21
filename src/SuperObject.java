import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class SuperObject {
    public GamePanel gp;
    public BufferedImage image;
    public String name;
    public int x, y;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); 
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;

    public SuperObject(GamePanel gp) {
        this.gp = gp;
        solidArea.width = gp.tileSize;
        solidArea.height = gp.tileSize;
    }

    public Rectangle getBounds() {
        return new Rectangle(x + solidArea.x, y + solidArea.y, solidArea.width, solidArea.height);
    }

    public void update() {
        boolean onGround = false;

        for (Platform p : gp.platforms) {
            if (p != null) {
                if (x + solidArea.width > p.x && x < p.x + p.width) {
                    if (y + gp.tileSize <= p.y && y + gp.tileSize + 5 >= p.y) {
                        y = p.y - gp.tileSize;
                        onGround = true;
                        break;
                    }
                }
            }
        }
        
        if (!onGround && y < 500) { 
            y += 5; 
        }

        if (y > 500) {
            y = 500;
        }
    }
    
    public void draw(Graphics2D g2, GamePanel gp) {
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
} 