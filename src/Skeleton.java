import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Skeleton extends Entity {
    public String state = "idle"; 
    private final int GRAVITY = 1;
    private boolean onGround = false;
    public BufferedImage idle5, idle6, idle7, idle8, idle9, idle10, idle11, attack1, attack2, attack3, attack4, attack5, attack6, attack7,attack8,attack9,attack10,attack11,attack12,attack13,attack14,attack15,attack16,attack17,attack18,walk1,walk2,walk3,walk4,walk5,walk6,walk7,walk8,walk9,walk10,walk11,walk12,walk13,dead1,dead2,dead3,dead4,dead5,dead6,dead7,dead8,dead9,dead10,dead11,dead12,dead13,dead14,dead15;
    public int spriteIndex = 0;
    GamePanel gp;
    public int health = 3;
    private int actionLockCounter = 0;
    public boolean isHurt = false;
    public int hurtCounter = 0;
    public boolean invincible = false;
    public int invincibleCounter = 0;
    public Skeleton(GamePanel gp, int x, int y) {
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.speed = 1;
        this.state = "idle";
        getIdleImage();
        getWalkImage();
        getAttackImage();
        getDeadImage();
    }

    public void damageEnemy(int damage) {
        if (!invincible) {
            health -= damage;
            isHurt = true;
            hurtCounter = 15;
            invincible = true;
            invincibleCounter = 40;

            if (gp.player.direction.equals("right")) x += 20;
            else x -= 20;
            
            if (health <= 0) state = "dead";
        }
    }
    public boolean isPlayerNear() {

        int xDistance = Math.abs(x - gp.player.x);
        
        int yDistance = Math.abs(y - gp.player.y);

        if (xDistance < 100 && yDistance < 50) {
            return true;
        }
        return false;
    }

    public void getIdleImage() {
        try {
            BufferedImage sheet = ImageIO.read(new File("res\\Skeleton\\Sprite Sheets\\Skeleton Idle.png"));

             UtilityTool uTool = new UtilityTool();
            int w = 24; 
            int h = 32;

            idle1 = uTool.scaleImage(sheet.getSubimage(0, 0, w, h), gp.tileSize, gp.tileSize);
            idle2 = uTool.scaleImage(sheet.getSubimage(w, 0, w, h), gp.tileSize, gp.tileSize);
            idle3 = uTool.scaleImage(sheet.getSubimage(w*2, 0, w, h), gp.tileSize, gp.tileSize);
            idle4 = uTool.scaleImage(sheet.getSubimage(w*3, 0, w, h), gp.tileSize, gp.tileSize);
            idle5 = uTool.scaleImage(sheet.getSubimage(w*4, 0, w, h), gp.tileSize, gp.tileSize);
            idle6 = uTool.scaleImage(sheet.getSubimage(w*5, 0, w, h), gp.tileSize, gp.tileSize);
            idle7 = uTool.scaleImage(sheet.getSubimage(w*6, 0, w, h), gp.tileSize, gp.tileSize);
            idle8 = uTool.scaleImage(sheet.getSubimage(w*7, 0, w, h), gp.tileSize, gp.tileSize);
            idle9 = uTool.scaleImage(sheet.getSubimage(w*8, 0, w, h), gp.tileSize, gp.tileSize);
            idle10 = uTool.scaleImage(sheet.getSubimage(w*9, 0, w, h), gp.tileSize, gp.tileSize);
            idle11 = uTool.scaleImage(sheet.getSubimage(w*10, 0, w, h), gp.tileSize, gp.tileSize);
        } catch (Exception e) { e.printStackTrace(); }
    }
    public void getAttackImage() {
        try {
            BufferedImage sheet = ImageIO.read(new File("res\\Skeleton\\Sprite Sheets\\Skeleton Attack.png"));

            UtilityTool uTool = new UtilityTool();
            int w = 43; 
            int h = 37;

            attack1 = uTool.scaleImage(sheet.getSubimage(0, 0, w, h), gp.tileSize, gp.tileSize);
            attack2 = uTool.scaleImage(sheet.getSubimage(w, 0, w, h), gp.tileSize, gp.tileSize);
            attack3 = uTool.scaleImage(sheet.getSubimage(w*2, 0, w, h), gp.tileSize, gp.tileSize);
            attack4 = uTool.scaleImage(sheet.getSubimage(w*3, 0, w, h), gp.tileSize, gp.tileSize);
            attack5 = uTool.scaleImage(sheet.getSubimage(w*4, 0, w, h), gp.tileSize, gp.tileSize);
            attack6 = uTool.scaleImage(sheet.getSubimage(w*5, 0, w, h), gp.tileSize, gp.tileSize);
            attack7 = uTool.scaleImage(sheet.getSubimage(w*6, 0, w, h), gp.tileSize, gp.tileSize);
            attack8 = uTool.scaleImage(sheet.getSubimage(w*7, 0, w, h), gp.tileSize, gp.tileSize);
            attack9 = uTool.scaleImage(sheet.getSubimage(w*8, 0, w, h), gp.tileSize, gp.tileSize);
            attack10 = uTool.scaleImage(sheet.getSubimage(w*9, 0, w, h), gp.tileSize, gp.tileSize);
            attack11 = uTool.scaleImage(sheet.getSubimage(w*10, 0, w, h), gp.tileSize, gp.tileSize);
            attack12 = uTool.scaleImage(sheet.getSubimage(w*11, 0, w, h), gp.tileSize, gp.tileSize);
            attack13 = uTool.scaleImage(sheet.getSubimage(w*12, 0, w, h), gp.tileSize, gp.tileSize);
            attack14 = uTool.scaleImage(sheet.getSubimage(w*13, 0, w, h), gp.tileSize, gp.tileSize);
            attack15 = uTool.scaleImage(sheet.getSubimage(w*14, 0, w, h), gp.tileSize, gp.tileSize);
            attack16 = uTool.scaleImage(sheet.getSubimage(w*15, 0, w, h), gp.tileSize, gp.tileSize);
            attack17 = uTool.scaleImage(sheet.getSubimage(w*16, 0, w, h), gp.tileSize, gp.tileSize);
            attack18 = uTool.scaleImage(sheet.getSubimage(w*17, 0, w, h), gp.tileSize, gp.tileSize);
        } catch (Exception e) { e.printStackTrace(); }
    }
    public void getDeadImage() {
        try {
            BufferedImage sheet = ImageIO.read(new File("res\\Skeleton\\Sprite Sheets\\Skeleton Dead.png"));

             UtilityTool uTool = new UtilityTool();
            int w = 33; 
            int h = 32;

            dead1 = uTool.scaleImage(sheet.getSubimage(0, 0, w, h), gp.tileSize, gp.tileSize);
            dead2 = uTool.scaleImage(sheet.getSubimage(w, 0, w, h), gp.tileSize, gp.tileSize);
            dead3 = uTool.scaleImage(sheet.getSubimage(w*2, 0, w, h), gp.tileSize, gp.tileSize);
            dead4 = uTool.scaleImage(sheet.getSubimage(w*3, 0, w, h), gp.tileSize, gp.tileSize);
            dead5 = uTool.scaleImage(sheet.getSubimage(w*4, 0, w, h), gp.tileSize, gp.tileSize);
            dead6 = uTool.scaleImage(sheet.getSubimage(w*5, 0, w, h), gp.tileSize, gp.tileSize);
            dead7 = uTool.scaleImage(sheet.getSubimage(w*6, 0, w, h), gp.tileSize, gp.tileSize);
            dead8 = uTool.scaleImage(sheet.getSubimage(w*7, 0, w, h), gp.tileSize, gp.tileSize);
            dead9 = uTool.scaleImage(sheet.getSubimage(w*8, 0, w, h), gp.tileSize, gp.tileSize);
            dead10 = uTool.scaleImage(sheet.getSubimage(w*9, 0, w, h), gp.tileSize, gp.tileSize);
            dead11 = uTool.scaleImage(sheet.getSubimage(w*10, 0, w, h), gp.tileSize, gp.tileSize);
            dead12 = uTool.scaleImage(sheet.getSubimage(w*11, 0, w, h), gp.tileSize, gp.tileSize);
            dead13 = uTool.scaleImage(sheet.getSubimage(w*12, 0, w, h), gp.tileSize, gp.tileSize);
            dead14 = uTool.scaleImage(sheet.getSubimage(w*13, 0, w, h), gp.tileSize, gp.tileSize);
            dead15 = uTool.scaleImage(sheet.getSubimage(w*14, 0, w, h), gp.tileSize, gp.tileSize);
        } catch (Exception e) { e.printStackTrace(); }
    }
    
    public void getWalkImage() {
        try {
            BufferedImage sheet = ImageIO.read(new File("res\\Skeleton\\Sprite Sheets\\Skeleton Walk.png"));

             UtilityTool uTool = new UtilityTool();
            int w = 22; 
            int h = 33;

            walk1 = uTool.scaleImage(sheet.getSubimage(0, 0, w, h), gp.tileSize, gp.tileSize);
            walk2 = uTool.scaleImage(sheet.getSubimage(w, 0, w, h), gp.tileSize, gp.tileSize);
            walk3 = uTool.scaleImage(sheet.getSubimage(w*2, 0, w, h), gp.tileSize, gp.tileSize);
            walk4 = uTool.scaleImage(sheet.getSubimage(w*3, 0, w, h), gp.tileSize, gp.tileSize);
            walk5 = uTool.scaleImage(sheet.getSubimage(w*4, 0, w, h), gp.tileSize, gp.tileSize);
            walk6 = uTool.scaleImage(sheet.getSubimage(w*5, 0, w, h), gp.tileSize, gp.tileSize);
            walk7 = uTool.scaleImage(sheet.getSubimage(w*6, 0, w, h), gp.tileSize, gp.tileSize);
            walk8 = uTool.scaleImage(sheet.getSubimage(w*7, 0, w, h), gp.tileSize, gp.tileSize);
            walk9 = uTool.scaleImage(sheet.getSubimage(w*8, 0, w, h), gp.tileSize, gp.tileSize);
            walk10 = uTool.scaleImage(sheet.getSubimage(w*9, 0, w, h), gp.tileSize, gp.tileSize);
            walk11 = uTool.scaleImage(sheet.getSubimage(w*10, 0, w, h), gp.tileSize, gp.tileSize);
            walk12 = uTool.scaleImage(sheet.getSubimage(w*11, 0, w, h), gp.tileSize, gp.tileSize);
            walk13 = uTool.scaleImage(sheet.getSubimage(w*12, 0, w, h), gp.tileSize, gp.tileSize);
        } catch (Exception e) { e.printStackTrace(); }
    }
    public Rectangle getBounds() {
        return new Rectangle(x, y, 24, 32);
    }
    public void setAction() {
        actionLockCounter++;
        if (actionLockCounter == 120) {
            double i = Math.random() * 100;
            if (i <= 50) direction = "left";
            else direction = "right";
            actionLockCounter = 0;
        }
    }

    public void update() {

    if (invincible) {
        invincibleCounter--;
        if (invincibleCounter <= 0) invincible = false;
    }
    if (isHurt) {
        hurtCounter--;
        if (hurtCounter <= 0) isHurt = false;
    }

    if (!state.equals("dead")) {

        setAction();
        
        String oldState = state;
        if (isPlayerNear()) state = "attack";
        else if (speed != 0) state = "walk";
        else state = "idle";
        
        if (!state.equals(oldState)) {
            spriteNum = 1;
            spriteCounter = 0;
        }

        if (state.equals("walk")) {
            if (onGround) {
                boolean floorAhead = false;

                int sensorX = (direction.equals("left")) ? x - 5 : x + (int)(24 * 1.5) + 5;
                int sensorY = y + (int)(32 * 1.5) + 5;

                for (Platform p : gp.platforms) {
                    if (p != null && sensorX >= p.x && sensorX <= p.x + p.width &&
                        sensorY >= p.y && sensorY <= p.y + p.height) {
                        floorAhead = true;
                        break;
                    }
                }
                if (!floorAhead) {
                    direction = direction.equals("left") ? "right" : "left";
                }
            }
            
            if (direction.equals("left")) x -= speed;
            if (direction.equals("right")) x += speed;
        }


        spriteCounter++;
        if (spriteCounter > 10) {
            int maxFrame = 11;
            if (state.equals("walk")) maxFrame = 13;
            if (state.equals("attack")) maxFrame = 18;

            spriteNum++;
            if (spriteNum > maxFrame) spriteNum = 1;
            spriteCounter = 0;
        }
    }

    y += ySpeed;
    ySpeed += GRAVITY;
    if (y >= 500) { 
        y = 500;
        ySpeed = 0;
        onGround = true;
    }
        for (Platform p : gp.platforms) {
            if (p != null) {
                
                if (x + gp.tileSize > p.x && x < p.x + p.width) {
                    if (y + gp.tileSize <= p.y && y + gp.tileSize + ySpeed >= p.y) {
                        
                        y = p.y - gp.tileSize; 
                        ySpeed = 0;            
                        onGround = true;       
                    }
                }
            }
        }
    }
    

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        double scale = 1;
        if (image == null) image = idle1;
        int finalWidth = (int)(image.getWidth() * scale); 
        int finalHeight = (int)(image.getHeight() * scale);
        int drawY = y - (finalHeight - (int)(70 * scale)); 
        int drawX = x;

        switch (state) {
            case "dead":
                BufferedImage[] dead = {dead1, dead2, dead3, dead4, dead5, dead6, dead7, dead8, dead9, dead10, dead11, dead12, dead13,dead14,dead15};
                int frameIndex = Math.min(spriteNum - 1, dead.length - 1);
                if (frameIndex >= 0) image = dead[frameIndex];
                scale = 0.7;
                finalHeight = (int)(image.getHeight() * scale);
                drawY = y - (finalHeight - (int)(70 * scale)); 
            break;
            case "walk":
                BufferedImage[] walks = {walk1, walk2, walk3, walk4, walk5, walk6, walk7, walk8, walk9, walk10, walk11, walk12, walk13};
                if (spriteNum > 0 && spriteNum <= walks.length) image = walks[spriteNum - 1];
                scale = 0.65;
                finalHeight = (int)(image.getHeight() * scale);
                drawY = y - (finalHeight - (int)(70 * scale)); 
                break;
            case "attack":
                BufferedImage[] attacks = {attack1, attack2, attack3, attack4, attack5, attack6, attack7, attack8, attack9, attack10, attack11, attack12, attack13, attack14, attack15, attack16, attack17, attack18};
                if (spriteNum > 0 && spriteNum <= attacks.length) image = attacks[spriteNum - 1];
                scale = 1;
                finalHeight = (int)(image.getHeight() * scale);
                drawY = y - (finalHeight - (int)(50 * scale));
                break;
            case "idle":
                BufferedImage[] idles = {idle1, idle2, idle3, idle4, idle5, idle6, idle7, idle8, idle9, idle10, idle11};
                if (spriteNum > 0 && spriteNum <= idles.length) image = idles[spriteNum - 1];
                scale = 0.65;
                finalHeight = (int)(image.getHeight() * scale);
                drawY = y - (finalHeight - (int)(70 * scale));
                break;
        }

        if (image == null) image = idle1; 

        if (direction.equals("left")) { 
            drawX = x + finalWidth;
            finalWidth = -finalWidth;
        }

        if (invincible && (invincibleCounter % 10 < 5)) {
        } else {
            g2.drawImage(image, drawX,drawY, finalWidth, finalHeight, null);
        }
    }
}