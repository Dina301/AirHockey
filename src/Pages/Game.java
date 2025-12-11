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

    public void draw() {
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        drawBackground();

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
        draw(57, -575, 325, 50, 50);
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
}