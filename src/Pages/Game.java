package Pages;
import java.awt.*;
import java.awt.event.*;
import javax.media.opengl.*;
import GameObjects.Ball;
import GameObjects.Hand;
import java.util.BitSet;
import com.sun.opengl.util.GLUT;


public class Game {

    GL gl;
    int[] textures;
    BitSet keyBits;
    int[] mouse;
    boolean[] mouseClicked;

    public Hand handRight, handLeft;
    Timer timer;
    public Ball ball;


    public static String player1 = "";
    public static String player2 = "";
    public static boolean isMultiplayer = false;


    public Game(GL gl, int[] textures, int[] mouse, boolean[] mouseClicked, String[] playerName, BitSet keyBits) {
        this.gl = gl;
        this.textures = textures;
        this.keyBits = keyBits;
        this.mouse = mouse;
        this.mouseClicked = mouseClicked;
        handRight = new Hand(textures[39], 440, 0, true, textures, gl);
        handLeft = new Hand(textures[39], -440, 0, false, textures, gl);
        timer = new Timer(60, textures, gl);
        ball = new Ball(textures, 0, 0, handRight, handLeft, playerName, timer, gl);

    }


    public void draw() {
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        drawBackground();
        handleKeyPress();
        timer.add();
        timer.draw();
        ball.draw();

        handLeft.draw();
        handRight.draw();

        if (mouseClicked[0]) {

            if (isMouseOnRight(mouse[0], mouse[1])) {
                handRight.moveTo(mouse[0], mouse[1]);
            }

            if (!handLeft.AI && isMouseOnLeft(mouse[0], mouse[1])) {
                handLeft.moveTo(mouse[0], mouse[1]);
            }

        }

        if(isMultiplayer) {
            drawText(player1,-540,230);
            drawText(player2,480,230);
        }

        draw(57, -575, 325, 50, 50);
    }

    public void drawText(String text, int x, int y) {
        GLUT glut = new GLUT();
        gl.glRasterPos2f(x, y);
        for (int i = 0; i < text.length(); i++) {
            glut.glutBitmapCharacter(GLUT.BITMAP_HELVETICA_18, text.charAt(i));
        }
    }
    public boolean isMouseOnRight(int mouseX, int mouseY) {
        return mouseX > handRight.x - 50 && mouseX < handRight.x + 50 &&
                mouseY > handRight.y - 50 && mouseY < handRight.y + 50;
    }

    public boolean isMouseOnLeft(int mouseX, int mouseY) {
        return mouseX > handLeft.x - 50 && mouseX < handLeft.x + 50 &&
                mouseY > handLeft.y - 50 && mouseY < handLeft.y + 50;
    }

    public void drawBackground() {
        draw(37, 0, 0, 1200, 700);
    }
    public void setBot(int level) {
        handLeft.level = level;
        handLeft.AI = true;
    }
    public void draw(int index, double x, double y, double width, double height) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]); // Turn Blending On

        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f); // bottom left point
        gl.glVertex3f((float) (x - 0.5 * width), (float) (y - 0.5 * height), -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f); // top left point
        gl.glVertex3f((float) (x + 0.5 * width), (float) (y - 0.5 * height), -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f); // top right point
        gl.glVertex3f((float) (x + 0.5 * width), (float) (y + 0.5 * height), -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f); // bottom right point
        gl.glVertex3f((float) (x - 0.5 * width), (float) (y + 0.5 * height), -1.0f);
        gl.glEnd();

        gl.glDisable(GL.GL_BLEND);
    }


    public boolean isKeyPressed(final int keyCode) {
        return keyBits.get(keyCode);
    }

    public void handleKeyPress() {

        if (isKeyPressed(KeyEvent.VK_LEFT)) {
            handRight.move(-10, 0);
        }
        if (isKeyPressed(KeyEvent.VK_RIGHT)) {
            handRight.move(10, 0);
        }
        if (isKeyPressed(KeyEvent.VK_UP)) {
            handRight.move(0, 10);
        }
        if (isKeyPressed(KeyEvent.VK_DOWN)) {
            handRight.move(0, -10);
        }
        if (!handLeft.AI){
            if (isKeyPressed(KeyEvent.VK_A) ) {
                handLeft.move(-10, 0);
            }
            if (isKeyPressed(KeyEvent.VK_D)) {
                handLeft.move(10, 0);
            }
            if (isKeyPressed(KeyEvent.VK_W)) {
                handLeft.move(0, 10);
            }
            if (isKeyPressed(KeyEvent.VK_S)) {
                handLeft.move(0, -10);
            }
        }

    }

    public void reset() {
        timer.reset();
        handLeft.reset();
        handRight.reset();
        ball.reset();
    }

    public void start() {
        ball.move = true;
        timer.start();
    }

    public void startTwoPlayers(String p1, String p2) {
        this.player1 = p1;
        this.player2 = p2;
        ball.player1=p1;
        ball.player2=p2;
    }

}