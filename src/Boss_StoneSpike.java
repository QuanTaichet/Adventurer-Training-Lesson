import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Boss_StoneSpike extends SuperObject {
    public boolean active = true;
    private int lifeCounter = 80;
    private int damage = 1;

    public Boss_StoneSpike(GamePanel gp, int x, int y) {
        super(gp);
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.name = "StoneSpike";

        this.solidArea = new Rectangle(20, 20, gp.tileSize - 40, gp.tileSize - 20);
        
        getSpikeImage();
    }

    public void getSpikeImage() {
        try {
            UtilityTool uTool = new UtilityTool();
            BufferedImage originalImage = ImageIO.read(new File("res\\Mecha-stone Golem 0.1\\weapon PNG\\arm_projectile.png")); 
            this.image = uTool.scaleImage(originalImage, gp.tileSize * 3, gp.tileSize * 3);
        } catch (Exception e) {
            System.out.println("error not found");

        }
    }

    public void update() {
        if (!active) return;

        lifeCounter--;

        if (this.getBounds().intersects(gp.player.getBounds())) {
            if (!gp.player.invincible) {
                gp.player.takeDamage(damage);
            }
        }

        if (lifeCounter <= 0) {
            active = false;
        }
    }

    public void draw(Graphics2D g2) {
        if (active && image != null) {

            g2.drawImage(image, x, y, gp.tileSize*2, gp.tileSize*2, null);
            
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x + solidArea.x, y + solidArea.y, solidArea.width, solidArea.height);
    }
}