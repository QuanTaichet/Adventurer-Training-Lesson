import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    public ArrayList<Arrow> arrows = new ArrayList<>();
    Sound se = new Sound();
    Sound music = new Sound();
    public int currentLevel = 1;
    public final int MAX_LEVEL = 4;
    public ArrayList<Skeleton> enemies = new ArrayList<>();
    final int originalTileSize = 16;
    final int scale = 3; 
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    BufferedImage backgroundImage;
    public ArrayList<Boss_Laser> bossLasers = new ArrayList<>();
    public ArrayList<Boss_StoneSpike> bossSpikes = new ArrayList<>();
    public SuperObject[] obj = new SuperObject[20];
    public Boss_Golem boss;
    public final int playState = 1;
    public final int gameOverState = 2; 
    public final int gameWinState = 3;  
    public int gameState = playState;

    int FPS = 60;

    Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(keyH);
        playMusic(1);
        setupPlatforms();
        loadBackgroundImage();
        setupEnemies();
    }
    public void spawnArrowItem(int x, int y) {
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] == null) {
                obj[i] = new OBJ_ArrowItem(this); 
                obj[i].x = x;
                obj[i].y = y-20;
                break;
            }
        }
    }
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / 35; 
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();   
                repaint();  
                delta--;    
                drawCount++;
            }

            if (timer >= 1000000000) {
                drawCount = 0;
                timer = 0;
            }
        }
    }
    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic() {
        music.stop();
    }
    public void playSE(int i) {
    se.setFile(i); 
    se.play();   
    }
    KeyHandler keyH = new KeyHandler();
    public Player player = new Player(this, keyH);

    public void update() {
        player.update();
        if (gameState == playState) {

            player.update();
            if (boss != null) {
                boss.update();
                
                if (boss.life <= 0 || boss.state.equals("death")) {
                    gameState = gameWinState;
                }
            }

            if (player.life <= 0) {
                gameState = gameOverState;
            }
        }
        

        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].update(); 
            }
        }
        for (int i = 0; i < enemies.size(); i++) {
            Skeleton s = enemies.get(i);
            if (s != null) {
                s.update();
                if(s.state.equals("dead") && s.spriteNum == 15) enemies.remove(i);
            }
        }
        if (player.y > 600) {
            player.life = 0;
            gameState = gameOverState;
        }

        for (int i = 0; i < bossLasers.size(); i++) {
            if (bossLasers.get(i).active) {
                bossLasers.get(i).update();
            } else {
                bossLasers.remove(i);
                i--;
            }
        }

        for (int i = 0; i < bossSpikes.size(); i++) {
            if (bossSpikes.get(i).active) {
                bossSpikes.get(i).update();
            } else {
                bossSpikes.remove(i);
                i--;
            }
        }

        for (int i = 0; i < arrows.size(); i++) {
            Arrow ar = arrows.get(i);
            if (ar != null && ar.active) {
                ar.update();
            } else {
                arrows.remove(i);
                i--;
            }
        }
    }
    public void fireBossLaser(int x, int y, String direction) {
        bossLasers.add(new Boss_Laser(this, x, y, direction));
    }

    public void spawnStoneSpike(int bossX, int bossY, String direction) {
        int spikeDistance = tileSize * 2;
        
        for (int i = 1; i <= 3; i++) {
            int spawnX;
            if (direction.equals("right")) {
                spawnX = bossX + (i * spikeDistance);
            } else {
                spawnX = bossX - (i * spikeDistance);
            }
            
            Boss_StoneSpike spike = new Boss_StoneSpike(this, spawnX, bossY);
            bossSpikes.add(spike);
        }
        System.out.println("Boss summon stone spike");
    }
    public Platform[] platforms = new Platform[10]; 
    BufferedImage platformSheet;
    public void setupEnemies() {

        enemies.clear();
        boss = null;
        if (currentLevel == 4) {

            boss = new Boss_Golem(this, 500, 400);
        } else if (currentLevel == 2) {
            enemies.add(new Skeleton(this, 400, 350));
        } else if (currentLevel == 3) {
            enemies.add(new Skeleton(this, 200, 400));
            enemies.add(new Skeleton(this, 500, 200));
        }

    }
    public void setupPlatforms() {
        try {
            platformSheet = ImageIO.read(new File("res\\platforms\\platforms.png"));
            BufferedImage fullPlatform = platformSheet.getSubimage(0, 0, 48, 16);
            
            for (int i = 0; i < obj.length; i++) obj[i] = null;
            for (int i = 0; i < platforms.length; i++) platforms[i] = null;

            switch(currentLevel) {
                case 1:
                    platforms[0] = new Platform(100, 410, tileSize * 3, tileSize, fullPlatform);
                    platforms[1] = new Platform(300, 410, tileSize * 4, tileSize, fullPlatform);
                    platforms[2] = new Platform(600, 410, tileSize * 3, tileSize, fullPlatform);
                    break;
                case 2:
                    platforms[0] = new Platform(50, 450, tileSize * 5, tileSize, fullPlatform);
                    platforms[1] = new Platform(350, 300, tileSize * 3, tileSize, fullPlatform);
                    platforms[2] = new Platform(300, 410, tileSize * 9, tileSize, fullPlatform);
                    obj[0] = new OBJ_Sword(this);
                    obj[0].x = 400; obj[0].y = 250;
                    break;
                case 3:
                    platforms[0] = new Platform(0, 500, screenWidth, tileSize, fullPlatform);
                    obj[1] = new OBJ_Bow(this);
                    obj[1].x = 200; obj[1].y = 450;
                    break;
                case 4:
                    platforms[0] = new Platform(0, 500, screenWidth, tileSize, fullPlatform);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
   
    public void loadBackgroundImage() {
    try {
            
            File file = new File("res\\background\\074.large.png"); 
            backgroundImage = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight, null);
        }

        for (Platform p : platforms) {
            if (p != null) p.draw(g2);
        }

        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }
        if (boss != null) {
            boss.draw(g2);
        }

        for (Skeleton s : enemies) {
            if (s != null) s.draw(g2);
        }

        for (Arrow ar : arrows) {
            if (ar != null) ar.draw(g2);
        }
        for (Boss_Laser bl : bossLasers) {
            bl.draw(g2);
        }

        for (int i = 0; i < bossSpikes.size(); i++) {
            if (bossSpikes.get(i) != null) {
                bossSpikes.get(i).draw(g2); 
            }
        }

        player.draw(g2);
        
        if (gameState == gameOverState) {
            drawEndScreen(g2, "GAME OVER", Color.RED);
        }
        
        if (gameState == gameWinState) {
            drawEndScreen(g2, "VICTORY! BOSS DEFEATED", Color.YELLOW);
        }
        g2.dispose();
    }

    public void drawEndScreen(Graphics2D g2, String text, Color color) {
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRect(0, 0, screenWidth, screenHeight);

        g2.setFont(new Font("Arial", Font.BOLD, 60));
        
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = screenWidth / 2 - length / 2;
        int y = screenHeight / 2;

        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 4, y + 4);
        
        g2.setColor(color);
        g2.drawString(text, x, y);

        g2.setFont(new Font("Arial", Font.PLAIN, 25));
        g2.setColor(Color.WHITE);
        String sub = "Press X to Exit";
        int subX = screenWidth / 2 - (int)g2.getFontMetrics().getStringBounds(sub, g2).getWidth() / 2;
        g2.drawString(sub, subX, y + 60);
    }
    
}