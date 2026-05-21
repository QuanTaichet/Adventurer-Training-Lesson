import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Boss_Laser extends Entity {
    GamePanel gp;
    int duration = 40; 
    int damage = 2;    
    public boolean active = true;
    BufferedImage laserImage;

    public Boss_Laser(GamePanel gp, int x, int y, String direction) {
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.direction = direction;
        getLaserImage();
    }

    public void getLaserImage() {
        try {

            BufferedImage sheet = ImageIO.read(new File("res\\Mecha-stone Golem 0.1\\weapon PNG\\Laser_sheet.png"));
            
            int frameWidth = 300;
            int frameHeight = 100;

            int startY = 800; 

            UtilityTool uTool = new UtilityTool();
            laserImage = uTool.scaleImage(sheet.getSubimage(0, 900, frameWidth, frameHeight), gp.tileSize * 8, gp.tileSize * 2);
            
        } catch (Exception e) {
            System.out.println("error not found:" + e.getMessage());
        }
    }

    public void update() {
        if (!active) return;

        duration--;

        Rectangle laserHitbox;
        int laserLength = gp.tileSize * 8;
        int laserHeight = gp.tileSize;

        if (direction.equals("right")) {
            laserHitbox = new Rectangle(x + gp.tileSize, y + 25, laserLength, laserHeight);
        } else {
            laserHitbox = new Rectangle(x - laserLength, y + 25, laserLength, laserHeight);
        }

        if (gp.player.getBounds().intersects(laserHitbox) && !gp.player.invincible) {
            gp.player.takeDamage(damage);
        }

        if (duration <= 0) active = false;
    }

    public void draw(Graphics2D g2) {
        if (!active || laserImage == null) return;

        int drawY = y + 10; 
        int width = gp.tileSize * 8;
        int height = gp.tileSize * 2;

        if (direction.equals("left")) {
            g2.drawImage(laserImage, x - width + gp.tileSize, drawY, width, height, null);
        } else {
            g2.drawImage(laserImage, x + gp.tileSize, drawY, width, height, null);
        }
    }
}