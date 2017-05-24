import java.awt.image.BufferedImage;

/**
 * Created by Victor on 5/6/2017.
 */
public class Player implements GameObject {

    private Rectangle playerRectangle;
    private int speed = 10;
    private  Sprite sprite;
    private int animationImages = 8;
    AnimatedSprite animatedSprite = null;


    //0,1,2,3: right left up down respectively
    private int direction = 0;

    public Player(Sprite sprite){
        this.sprite = sprite;
        if(sprite != null && sprite instanceof AnimatedSprite) animatedSprite = (AnimatedSprite) sprite;
        updateDirection();


        playerRectangle = new Rectangle(32,16,16,32);
        //playerRectangle.generateGraphics(3,100);

//        Rectangle[] spritePositions = new Rectangle[8];
//        for (int i = 0; i < spritePositions.length; i++) {
//            spritePositions[i] = new Rectangle(i*20,0,20,26);
//        }
//
//        animatedSprite = new AnimatedSprite(sheet, spritePositions, 5);

    }
    private void updateDirection(){
        if(animatedSprite != null){
            //animatedSprite.setAnimationRange(1, 3);  //(direction*3, direction*3+(animationImages));
            animatedSprite.setAnimationRange(direction*8, direction*8+7);
        }
    }

    public void render(RenderHandler renderer, int xZoom, int yZoom) {
        if(animatedSprite != null)
            renderer.renderSprite(animatedSprite,playerRectangle.x, playerRectangle.y, xZoom, yZoom, false);

        else if (sprite != null)
            renderer.renderSprite(sprite, playerRectangle.x, playerRectangle.y, xZoom, yZoom, false);

        else
            renderer.renderRectangle(playerRectangle, xZoom, yZoom, false);

    }

    public void update(Game game) {
        KeyBoardListener keyListener = game.getKeyListener();

        boolean didMove = false;
        int newDirection = direction;

        if(keyListener.left()){
            playerRectangle.x -= speed;
            newDirection = 1;
            didMove = true;
        }
        if(keyListener.right()){
            playerRectangle.x += speed;
            newDirection = 0;
            didMove = true;
        }
        if(keyListener.up()){
            playerRectangle.y -= speed;
            newDirection = 2;
            didMove = true;
        }
        if(keyListener.down()){
            playerRectangle.y += speed;
            newDirection = 3;
            didMove = true;
        }

        if(keyListener.maximize()){
            game.setBounds(0,0, game.screenSize.width, game.screenSize.height);
            game.renderer = new RenderHandler(game.getWidth(), game.getHeight());
        }
        if(keyListener.minimize()){
            game.setBounds(0,0, 500, 500);
            game.renderer = new RenderHandler(game.getWidth(), game.getHeight());
            game.setLocationRelativeTo(null);
        }

        if(newDirection != direction){
            direction = newDirection;
            updateDirection();
        }

        if(!didMove){
            animatedSprite.reset();
        }
        if(didMove){
            animatedSprite.update(game);
        }

        updateCamera(game.getRenderer().getCamera());
    }

    public void handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) {

    }

    public void updateCamera(Rectangle camera){
        camera.x = playerRectangle.x - (camera.w/2);
        camera.y = playerRectangle.y - (camera.h/2);
    }
}
