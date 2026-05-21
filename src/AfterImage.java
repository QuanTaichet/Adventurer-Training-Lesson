import java.awt.image.BufferedImage;

public class AfterImage {
    public int x, y;
    public int opacity = 150;
    public BufferedImage image;

    public AfterImage(int x, int y, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.image = image;
    }
}