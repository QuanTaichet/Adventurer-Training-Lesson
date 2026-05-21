import java.awt.Rectangle;
import java.io.File;
import javax.imageio.ImageIO;

public class OBJ_ArrowItem extends SuperObject {

    public OBJ_ArrowItem(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Arrow_Pickup";
        
        try {
            image = ImageIO.read(new File("res\\item\\Arrow01.png")); 
            
            UtilityTool uTool = new UtilityTool();
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
    }
}