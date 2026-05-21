import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;


public class Player extends Entity {
    public boolean canFight = true;  
    public boolean hasSword = false;  
    public boolean hasBow = false;   
    public int arrowCount = 0;    
    public String currentWeapon = "hand";
    GamePanel gp;
    KeyHandler keyH;
    public BufferedImage currentImage;
    private final int GRAVITY = 1;
    private final int JUMP_STRENGTH = -15;
    private boolean onGround = false;
    boolean isAttacking = false;
    int attackCounter = 0;
    boolean isJumping = false;
    boolean isFalling = false;
    public boolean invincible = false;
    public int invincibleCounter = 0;
    public int life = 3;
    public BufferedImage punch1, punch2, punch3, punch4;
    public BufferedImage shoot1, shoot2, shoot3, shoot4, shoot5, shoot6;
    public BufferedImage attack1, attack2, attack3, attack4, roll1, roll2, roll3, roll4, jump1, jump2, jump3, jump4;
    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "right";
        currentImage = right1;
    }
    boolean isDashing = false;
    int dashCounter = 0;
    int dashCooldown = 0;
    ArrayList<AfterImage> afterImages = new ArrayList<>();
    public void shootArrow() {
    Arrow arrow = new Arrow(gp); 

    arrow.x = this.x;
    arrow.y = this.y + 15;

    arrow.direction = this.direction;

    arrow.active = true;

    gp.arrows.add(arrow); 
    }
    public Rectangle getBounds() {
        return new Rectangle(x + 8, y + 8, gp.tileSize - 16, gp.tileSize - 16);
    }
    public void checkItemPickup() {
    for (int i = 0; i < gp.obj.length; i++) {
        if (gp.obj[i] != null) {

            if (this.getBounds().intersects(gp.obj[i].getBounds())) {
                
                String itemName = gp.obj[i].name;

                if (itemName.equals("Sword")) {
                    hasSword = true;
                    currentWeapon = "sword";
                    gp.obj[i] = null;
                }
                else if (itemName.equals("Bow")) {
                    hasBow = true;
                    arrowCount += 5;
                    gp.obj[i] = null;
                }
                else if (itemName.equals("Arrow_Pickup")) {
                    arrowCount++;
                    gp.obj[i] = null;
                }
            }
        }
    }
}
    public void damageEnemyLogic() {
        for (int i = 0; i < gp.enemies.size(); i++) {
            Skeleton s = gp.enemies.get(i);
            
            if (s != null && !s.state.equals("dead")) {
                int xDist = Math.abs(this.x - s.x);
                int yDist = Math.abs(this.y - s.y);

                int attackRangeX = 60; 
                int attackRangeY = 30;

                if (hasSword) {
                    attackRangeX = 80;
                }

                if (xDist < attackRangeX && yDist < attackRangeY) {
                    if ((direction.equals("right") && s.x > this.x) || 
                        (direction.equals("left") && s.x < this.x)) {
                        s.damageEnemy(1);
                    }
                }
            }
        }
    }
    public Rectangle getAttackBounds() {
        Rectangle attackRect = new Rectangle();
        
        attackRect.x = this.x;
        attackRect.y = this.y;
        
        int attackRange = gp.tileSize; 
        attackRect.width = attackRange;
        attackRect.height = gp.tileSize;

        if (direction.equals("right")) {
            attackRect.x = this.x + gp.tileSize;
        } else if (direction.equals("left")) {
            attackRect.x = this.x - attackRange;
        }
        
        return attackRect;
    }


    public void update() {
    
    checkItemPickup();
    if (!invincible) {
        for (int i = 0; i < gp.enemies.size(); i++) {
            Skeleton s = gp.enemies.get(i);
            if (s != null && !s.state.equals("dead")) {

                if (this.getBounds().intersects(s.getBounds())) {
                    takeDamage(1);
                    break; 
                }
            }
        }
    }

    if (invincible) {
        invincibleCounter--;
        if (invincibleCounter <= 0) {
            invincible = false;
        }
    }
    if (gp.boss != null && !gp.boss.state.equals("death") && !invincible) {
        if (this.getBounds().intersects(gp.boss.getBounds())) {
            takeDamage(1);
        }
    }
    if (keyH.shiftPressed && dashCooldown == 0 && !isDashing && !isAttacking) {
        isDashing = true;
        dashCounter = 20; 
        dashCooldown = 60; 
        ySpeed = 0; 
    }

    if (isDashing) {
        int dashSpeed = speed * 3;
        if (direction.equals("right")) x += dashSpeed;
        else if (direction.equals("left")) x -= dashSpeed;

        afterImages.add(new AfterImage(x, y, currentImage));

        dashCounter--;
        if (dashCounter <= 0) isDashing = false;
        
    } else {
        if (dashCooldown > 0) dashCooldown--;
    }

    if (keyH.kPressed && hasBow && arrowCount > 0 && !isAttacking) {
        currentWeapon = "bow";
        isAttacking = true;
        attackCounter = 30;
        spriteNum = 1;
    }

    if (keyH.attackPressed && !isAttacking && onGround && canFight) {
        isAttacking = true;
        attackCounter = 20;
        spriteNum = 1;

        if (hasSword) currentWeapon = "sword";
        else currentWeapon = "hand";
    }

    if (isAttacking) {
        attackCounter--;
        
        if (currentWeapon.equals("bow")) {
            spriteNum = 6 - (attackCounter / 5);
            if (spriteNum > 6) spriteNum = 6;
            if (attackCounter == 5 && currentWeapon.equals("bow")) {
                shootArrow();
                arrowCount--;
            }
        } else {
            spriteNum = 4 - (attackCounter / 5); 
            if (spriteNum > 4) spriteNum = 4;
            
            if (attackCounter == 10) {
                damageEnemyLogic();
            }
        }

        if (attackCounter <= 0) isAttacking = false;
    }

        if (!isAttacking) {
            if (keyH.leftPressed) {
                direction = "left";
                x -= speed;
            } else if (keyH.rightPressed) {
                direction = "right";
                x += speed;
            }
            
            if (keyH.spacePressed && onGround) {
                ySpeed = JUMP_STRENGTH;
                onGround = false;
                gp.playSE(0);
            }
        }
        if (invincible) {
            invincibleCounter--;
            if (invincibleCounter <= 0) invincible = false;
        }
        if (isDashing) {
        int dashSpeed = speed * 3;
        if (direction.equals("right")) x += dashSpeed;
        else if (direction.equals("left")) x -= dashSpeed;

        afterImages.add(new AfterImage(x, y, currentImage));

        dashCounter--;
        if (dashCounter <= 0) isDashing = false;

    } else {
        if (dashCooldown > 0) dashCooldown--;
    }
    
    y += ySpeed;
    ySpeed += GRAVITY;

    if (!isAttacking && !isDashing) {
        spriteCounter++;
        if (spriteCounter > 12) {
            spriteNum = (spriteNum % 4) + 1;
            spriteCounter = 0;
        }
    }

    for (int i = 0; i < afterImages.size(); i++) {
        AfterImage ai = afterImages.get(i);
        ai.opacity -= 10;
        if (ai.opacity <= 0) {
            afterImages.remove(i);
            i--;
        }
    }
        spriteCounter++;
    if (spriteCounter > 15) {
        spriteNum++;
        if (spriteNum > 4) {
            spriteNum = 1;
        }
        spriteCounter = 0;
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
        if (x > gp.screenWidth) {
            if (gp.currentLevel < gp.MAX_LEVEL) {
                gp.currentLevel++;
                x = 10;
                gp.setupPlatforms();
                gp.setupEnemies();
                System.out.println("welcome to level " + gp.currentLevel);
            } else {
                x = gp.screenWidth - gp.tileSize; 
            }
        }
        
        if (x < -gp.tileSize) {
            if (gp.currentLevel > 1) {
                gp.currentLevel--;
                x = gp.screenWidth - 20;
                gp.setupPlatforms();
            } else {
                x = 0;
            }
        }
    }
    public void takeDamage(int damage) {
        life -= damage;
        invincible = true;
        invincibleCounter = 60;
        if (direction.equals("right")) x -= 30; else x += 30;
        System.out.println("Player'hp:" + life + " máu!");
    }
    public void getPlayerImage() {
        
        try {

            BufferedImage sheet = ImageIO.read(new java.io.File("res\\player\\adventurer-Sheet.png"));
            

            UtilityTool uTool = new UtilityTool();
            int w = 50; 
            int h = 37;

            idle1 = uTool.scaleImage(sheet.getSubimage(0, 0, w, h), gp.tileSize, gp.tileSize);
            idle2 = uTool.scaleImage(sheet.getSubimage(w, 0, w, h), gp.tileSize, gp.tileSize);
            idle3 = uTool.scaleImage(sheet.getSubimage(w*2, 0, w, h), gp.tileSize, gp.tileSize);
            idle4 = uTool.scaleImage(sheet.getSubimage(w*3, 0, w, h), gp.tileSize, gp.tileSize);

            right1 = uTool.scaleImage(sheet.getSubimage(w, h, w, h), gp.tileSize, gp.tileSize);
            right2 = uTool.scaleImage(sheet.getSubimage(w*2, h, w, h), gp.tileSize, gp.tileSize);
            right3 = uTool.scaleImage(sheet.getSubimage(w*3, h, w, h), gp.tileSize, gp.tileSize);
            right4 = uTool.scaleImage(sheet.getSubimage(w*4, h, w, h), gp.tileSize, gp.tileSize);

            roll1 = uTool.scaleImage(sheet.getSubimage(4 * w, 2 * h, w, h), gp.tileSize, gp.tileSize);
            roll2 = uTool.scaleImage(sheet.getSubimage(5 * w, 2 * h, w, h), gp.tileSize, gp.tileSize);
            roll3 = uTool.scaleImage(sheet.getSubimage(6 * w, 2 * h, w, h), gp.tileSize, gp.tileSize);
            roll4 = uTool.scaleImage(sheet.getSubimage(1 * w, 3 * h, w, h), gp.tileSize, gp.tileSize);

            attack1 = uTool.scaleImage(sheet.getSubimage(0 * w, 6 * h, w, h), gp.tileSize, gp.tileSize);
            attack2 = uTool.scaleImage(sheet.getSubimage(1 * w, 6 * h, w, h), gp.tileSize, gp.tileSize);
            attack3 = uTool.scaleImage(sheet.getSubimage(2 * w, 6 * h, w, h), gp.tileSize, gp.tileSize);
            attack4 = uTool.scaleImage(sheet.getSubimage(3 * w, 6 * h, w, h), gp.tileSize, gp.tileSize);

            jump1 = uTool.scaleImage(sheet.getSubimage(2 * w, 2 * h, w, h), gp.tileSize, gp.tileSize);
            jump2 = uTool.scaleImage(sheet.getSubimage(3 * w, 2 * h, w, h), gp.tileSize, gp.tileSize);
            jump3 = uTool.scaleImage(sheet.getSubimage(1 * w, 3 * h, w, h), gp.tileSize, gp.tileSize);
            jump4 = uTool.scaleImage(sheet.getSubimage(2 * w, 3 * h, w, h), gp.tileSize, gp.tileSize);

            BufferedImage handSheet = ImageIO.read(new File("res\\player\\adventurer-hand-combat-Sheet.png"));

            punch1 = uTool.scaleImage(handSheet.getSubimage(0, 0, 50, 37), gp.tileSize, gp.tileSize);
            punch2 = uTool.scaleImage(handSheet.getSubimage(50, 0, 50, 37), gp.tileSize, gp.tileSize);
            punch3 = uTool.scaleImage(handSheet.getSubimage(100, 0, 50, 37), gp.tileSize, gp.tileSize);
            punch4 = uTool.scaleImage(handSheet.getSubimage(150, 0, 50, 37), gp.tileSize, gp.tileSize);

            BufferedImage bowSheet = ImageIO.read(new File("res\\player\\adventurer-bow-Sheet.png"));

            shoot1 = uTool.scaleImage(bowSheet.getSubimage(0, 0, 50, 37), gp.tileSize, gp.tileSize);
            shoot2 = uTool.scaleImage(bowSheet.getSubimage(50, 0, 50, 37), gp.tileSize, gp.tileSize);
            shoot3 = uTool.scaleImage(bowSheet.getSubimage(100, 0, 50, 37), gp.tileSize, gp.tileSize);
            shoot4 = uTool.scaleImage(bowSheet.getSubimage(150, 0, 50, 37), gp.tileSize, gp.tileSize);
            shoot5 = uTool.scaleImage(bowSheet.getSubimage(0, 37, 50, 37), gp.tileSize, gp.tileSize);
            shoot6 = uTool.scaleImage(bowSheet.getSubimage(50, 37, 50, 37), gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            System.out.println("image not found:" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        if (isAttacking) {
        if (currentWeapon.equals("bow")) {
            if (spriteNum == 1) image = shoot1;
            if (spriteNum == 2) image = shoot2;
            if (spriteNum == 3) image = shoot3;
            if (spriteNum == 4) image = shoot4;
            if (spriteNum == 5) image = shoot5;
            if (spriteNum == 6) image = shoot6;
        } else if (currentWeapon.equals("sword")) {
            if (spriteNum == 1) image = attack1;
            if (spriteNum == 2) image = attack2;
            if (spriteNum == 3) image = attack3;
            if (spriteNum == 4) image = attack4;
        } else { 
            if (spriteNum == 1) image = punch1;
            if (spriteNum == 2) image = punch2;
            if (spriteNum == 3) image = punch3;
            if (spriteNum == 4) image = punch4;
        }
        } else if (isDashing) {
            if (spriteNum == 1) image = roll1;
            if (spriteNum == 2) image = roll2;
            if (spriteNum == 3) image = roll3;
            if (spriteNum == 4) image = roll4;
        } else if (isJumping) {
            if (spriteNum == 1) image = jump1;
            if (spriteNum == 2) image = jump2;
            if (spriteNum == 3) image = jump3;
            if (spriteNum == 4) image = jump4;
        } else if (isFalling) {
            if (spriteNum == 1) image = jump3;
            if (spriteNum == 2) image = jump4;
        } else if (keyH.leftPressed || keyH.rightPressed) {
            if (spriteNum == 1) image = right1;
            if (spriteNum == 2) image = right2;
            if (spriteNum == 3) image = right3;
            if (spriteNum == 4) image = right4;
        } else {

            if (spriteNum == 1) image = idle1;
            if (spriteNum == 2) image = idle2;
            if (spriteNum == 3) image = idle3;
            if (spriteNum == 4) image = idle4;
        }

        if (image == null) image = idle1;

        double scale = 1;
        int finalWidth = (int) (image.getWidth() * scale);
        int finalHeight = (int) (image.getHeight() * scale);
        int drawX = x;
        int drawY = y;

        if (direction.equals("left")) {
            drawX = x + finalWidth;
            finalWidth = -finalWidth;
        }

        g2.drawImage(image, drawX, drawY, finalWidth, finalHeight, null);
    }
}
