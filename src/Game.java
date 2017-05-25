import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.Runnable;
import java.lang.Thread;
import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class Game extends JFrame implements Runnable
{
    //not using transparency so this color will represent no pixel when rendering
    public static int alpha = 0xFFFF00DC;
    private Canvas canvas = new Canvas();
    public RenderHandler renderer;    //I made this public to implement maximize/minimize screen

    private SpriteSheet sheet;
    private SpriteSheet playerSheet;

    private Rectangle testRectangle = new Rectangle(30,90,100,100);
    private Tiles tiles;
    private Map map;
    private GameObject[] objects;
    private Player player;
    private KeyBoardListener keyListener = new KeyBoardListener(this);
    private MouseEventListener mouseListener = new MouseEventListener(this);


    public Dimension screenSize;
    public int minimizedWidth = 800;
    public int minimizedHeigth = 800;


    private int xZoom = 2;
    private int yZoom = 2;

    public Game()
    {
        //Make our program shutdown when we exit out.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set the position and size of our frame.
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0,0, minimizedWidth, minimizedHeigth); // this works too setExtendedState(JFrame.MAXIMIZED_BOTH);



        //Put our frame in the center of the screen.
        setLocationRelativeTo(null);

        //Add our graphics component
        add(canvas);

        //Make our frame visible.
        setVisible(true);

        //Create our object for buffer strategy.
        canvas.createBufferStrategy(3);

        renderer = new RenderHandler(getWidth(), getHeight());

        requestFocus(true);

        //Load assets
        BufferedImage sheetImage = loadImage("Sprites/Tiles1.png");
        BufferedImage myTiles = loadImage("Sprites/MyTiles.png");
        sheet = new SpriteSheet(sheetImage);
        sheet.loadSprites(16,16);

        BufferedImage playerSheetImage = loadImage("Sprites/Player.png");
        playerSheet = new SpriteSheet(playerSheetImage);
        playerSheet.loadSprites(20,26);

        BufferedImage downTest = loadImage("Sprites/DownTest.png");
        SpriteSheet downTestSheet = new SpriteSheet(downTest);
        downTestSheet.loadSprites(14,16);
        AnimatedSprite downTestAnimation = new AnimatedSprite(downTestSheet, 10);

        //Player animated sprites
        AnimatedSprite playerAnimations = new AnimatedSprite(playerSheet, 5);

        //Load Tiles
        tiles = new Tiles(new File("Tiles.txt"),sheet);

        //Load map
        map = new Map(new File("Map.txt"), tiles);

        //Load SDK GUI
        GUIButton[] buttons = new GUIButton[tiles.size()];
        Sprite[] tileSprites = tiles.getSprites();

        for (int i = 0; i < buttons.length; i++) {
            Rectangle tileRectangle = new Rectangle(0, i*(16*xZoom), 16, 16);
            buttons[i] = new SDKButton(tileSprites[i], tileRectangle);
        }

        GUI gui = new GUI(buttons, 5, 5, true);

        //Load Objects
        objects = new GameObject[2];
        player = new Player(playerAnimations);
        objects[0] = player;
        objects[1] = gui;

        //Add Listeners
        canvas.addKeyListener(keyListener);
        canvas.addFocusListener(keyListener);
        canvas.addMouseListener(mouseListener);
        canvas.addMouseMotionListener(mouseListener);


        //testImage = loadImage("GrassTile.png");
        //testSprite = sheet.getSprite(4,2);
        //testRectangle.generateGraphics(4,1000);

    }


    public void update() {
        for (int i = 0; i < objects.length; i++) {
            objects[i].update(this);
        }


    }

    //renders pixel array to screen
    public void render() {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();
        super.paint(graphics);

        map.render(renderer, xZoom,yZoom);
        player.render(renderer, xZoom, yZoom);
        objects[1].render(renderer, xZoom, yZoom);

        //renderer.renderRectangle(testRectangle, 1,1);
        //renderer.renderSprite(testSprite, 0, 0, 5, 5);
        //tiles.renderTile(0,renderer,0,0,5,5);
        //tiles.renderTile(1,renderer,5*16,0,5,5);
        renderer.render(graphics);

        graphics.dispose();
        bufferStrategy.show();
        renderer.clear();
    }

    private BufferedImage loadImage(String path){
        try
        {
            BufferedImage loadedImage = ImageIO.read(Game.class.getResource(path));
            BufferedImage formattedImage = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            formattedImage.getGraphics().drawImage(loadedImage,0,0,null);
            return formattedImage;

        }catch(IOException exception){
            exception.printStackTrace();
            return null;
        }
    }
    public void leftClick(int x, int y){
        Rectangle mouseRectangle = new Rectangle(x,y,1,1);
        for (int i = 0; i < objects.length; i++) {
            objects[i].handleMouseClick(mouseRectangle, renderer.getCamera(), xZoom, yZoom);
        }
        x = (int)Math.floor((x + renderer.getCamera().x)/(16.0*xZoom));
        y = (int)Math.floor((y + renderer.getCamera().y)/(16.0*yZoom));
        map.setTile(x,y,2);
    }
    public void rightClick(int x, int y){
        x = (int)Math.floor((x + renderer.getCamera().x)/(16.0*xZoom));
        y = (int)Math.floor((y + renderer.getCamera().y)/(16.0*yZoom));
        map.removeTile(x,y);
    }

    public void handleCTRL(boolean[] keys){
        if(keys[KeyEvent.VK_S]){
            map.saveMap();
            System.out.println("ctrl s");
        }
    }

    public void run() {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        int i = 0;
        int x = 0;

        long lastTime = System.nanoTime(); //long 2^63
        double nanoSecondConversion = 1000000000.0 / 60; //60 frames per second
        double changeInSeconds = 0;

        while(true) {
            long now = System.nanoTime();

            changeInSeconds += (now - lastTime) / nanoSecondConversion;
            while(changeInSeconds >= 1) {
                update();
                changeInSeconds = 0;
            }

            render();
            lastTime = now;
        }

        //Bad loop
        // while(true) {
        // 	bufferStrategy = canvas.getBufferStrategy();
        // 	Graphics graphics = bufferStrategy.getDrawGraphics();
        // 	super.paint(graphics);

        // 	//Painting the Backround
        // 	graphics.setColor(Color.black);
        // 	graphics.fillRect(0, 0, getWidth(), getHeight());

        // 	//Painting the Oval
        // 	graphics.setColor(Color.red);
        // 	graphics.fillOval(x, 200, 50, 100);

        // 	graphics.dispose();
        // 	bufferStrategy.show();
        // }
    }

    public static void main(String[] args)
    {
        Game game = new Game();
        Thread gameThread = new Thread(game);
        gameThread.start();

    }

    public KeyBoardListener getKeyListener(){
        return keyListener;
    }
    public MouseEventListener eventListener(){
        return mouseListener;
    }
    public RenderHandler getRenderer(){
        return renderer;
    }

}