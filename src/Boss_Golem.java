import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Boss_Golem extends Entity {
    GamePanel gp;
    public int life = 10;
    public String state = "idle"; 
    public boolean isEnraged = false;

    int skillDelayCounter = 0;
    boolean isAiming = false;
    int iFrameCounter = 0; 
    boolean invincible = false; 

    public BufferedImage glowIdle, glowAttack1, glowAttack2, death1;

    int actionCounter = 0;
    int attackCounter = 0;
    boolean isAttacking = false;

    public Rectangle getBounds() {
        return new Rectangle(x + 30, y, gp.tileSize * 2 - 60, gp.tileSize * 2);
    }

    public Boss_Golem(GamePanel gp, int x, int y) {
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.speed = 1;
        this.solidArea = new Rectangle(20, 20, gp.tileSize*2 - 40, gp.tileSize*2 - 20);
        getBossImage();
    }

    public void getBossImage() {
        try {
            UtilityTool uTool = new UtilityTool();
            BufferedImage sheet = ImageIO.read(new File("res/Mecha-stone Golem 0.1/PNG sheet/Character_sheet.png"));
            int w = 100, h = 100;

            idle1 = uTool.scaleImage(sheet.getSubimage(0, 0, w, h), gp.tileSize*2, gp.tileSize*2);
            idle2 = uTool.scaleImage(sheet.getSubimage(w, 0, w, h), gp.tileSize*2, gp.tileSize*2);
            attack1 = uTool.scaleImage(sheet.getSubimage(0, h*2, w, h), gp.tileSize*2, gp.tileSize*2);
            attack2 = uTool.scaleImage(sheet.getSubimage(w, h*2, w, h), gp.tileSize*2, gp.tileSize*2);

            glowIdle = uTool.scaleImage(sheet.getSubimage(0, h*4, w, h), gp.tileSize*2, gp.tileSize*2);
            glowAttack1 = uTool.scaleImage(sheet.getSubimage(0, h*5, w, h), gp.tileSize*2, gp.tileSize*2);
            glowAttack2 = uTool.scaleImage(sheet.getSubimage(w, h*5, w, h), gp.tileSize*2, gp.tileSize*2);
            
            death1 = uTool.scaleImage(sheet.getSubimage(0, h*7, w, h), gp.tileSize*2, gp.tileSize*2);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void update() {
        if (state.equals("death")) return;

        if (gp.player.isAttacking && !invincible) {
            if (gp.player.getAttackBounds().intersects(this.getBounds())) {
                damageEnemy(1);
            }
        }

        if (invincible) {
            iFrameCounter--;
            if (iFrameCounter <= 0) invincible = false;
        }

        boolean onGround = false;
        int fallSpeed = 5; 
        for (Platform p : gp.platforms) {
            if (p != null && x + gp.tileSize * 2 > p.x && x < p.x + p.width) {
                if (y + gp.tileSize * 2 <= p.y + 5 && y + gp.tileSize * 2 + fallSpeed >= p.y) {
                    y = p.y - gp.tileSize * 2; 
                    onGround = true;
                    break;
                }
            }
        }
        if (!onGround && y < 500) y += fallSpeed;
        else if (y >= 500) { y = 500; onGround = true; }

        if (life <= 5) {
            isEnraged = true;
            speed = 2;
        }

        if (onGround && !isAttacking && !isAiming) {
            if (gp.player.x > this.x) direction = "right";
            else direction = "left";

            if (Math.abs(gp.player.x - this.x) > 60) {
                if (direction.equals("right")) x += speed;
                else x -= speed;
            }

            actionCounter++;
            if (actionCounter >= 180) {
                isAiming = true;
                skillDelayCounter = 50;
            }
        }

        if (isAiming) {
            skillDelayCounter--;
            if (skillDelayCounter <= 0) {
                isAiming = false;
                executeSkill();
                actionCounter = 0;
            }
        }

        spriteCounter++;
        if (spriteCounter > 10) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }

        if (isAttacking) {
            attackCounter--;
            if (attackCounter <= 0) { 
                isAttacking = false; 
                state = "idle"; 
            }
        }
    }

    private void executeSkill() {
        isAttacking = true;
        state = "attack";
        attackCounter = 40;

        if (!isEnraged) gp.spawnStoneSpike(this.x, this.y, this.direction);
        else gp.fireBossLaser(this.x, this.y, this.direction);
    }

    public void damageEnemy(int damage) {
        if (!state.equals("death") && !invincible) {
            life -= damage;
            invincible = true;
            iFrameCounter = 25; 
            if (life <= 0) state = "death";
        }
    }

    public void draw(Graphics2D g2) {
        if (invincible) {
            g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 0.7f));
        }

        BufferedImage image = null;

        if (state.equals("death")) {
            image = death1;
        } else if (isAiming || isAttacking || state.equals("attack")) {

            if (isEnraged) {
                image = (spriteNum == 1) ? glowAttack1 : glowAttack2;
            } else {
                image = (spriteNum == 1) ? attack1 : attack2;
            }
        } else {

            if (isEnraged) {
                image = glowIdle; 
            } else {
                image = (spriteNum == 1) ? idle1 : idle2;
            }
        }

        int drawSize = gp.tileSize * 2;
        if (direction.equals("left")) {
            g2.drawImage(image, x + drawSize, y, -drawSize, drawSize, null);
        } else {
            g2.drawImage(image, x, y, drawSize, drawSize, null);
        }
        
        g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 1f));
    }
}