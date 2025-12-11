package Pages;
import javax.media.opengl.*;
import java.io.*;
// import javax.swing.*;
// import com.sun.opengl.util.*;
// import java.awt.*;
// import java.awt.event.*;

public class Levels {
    int index;
    int textures[];
    GL gl;
    int[] mouse;
    Game game;
    public int levelChosen;
    UserName userName;
    int[] flag;

    public Levels(int[] textures, int[] mouse, int[] flag, UserName userName, Game game, GL gl) throws FileNotFoundException {
        this.textures = textures;
        this.gl = gl;
        this.mouse = mouse;
        this.game = game;
        this.userName = userName;
        this.flag = flag;
    }

    private void drawBackGround() {
        draw(40, 0, 0, 1200, 700);
    }

    private void draw(int index, double x, double y, double width, double height) {
        // draw the character
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