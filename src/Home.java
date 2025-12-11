import javax.media.opengl.*;
import javax.swing.*;
import com.sun.opengl.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.media.opengl.glu.GLU;
import Texture.TextureReader;

// java packages
import java.io.*;
import java.util.*;
import javax.sound.sampled.*;

// our own packages
import Pages.*;

public class Home extends JFrame {
    AudioInputStream audioStream;
    Clip clip;


    public Home() {
        try {
            audioStream = AudioSystem.getAudioInputStream(new File("Assets\\sound\\game.wav"));
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HomeEventListener listener = new HomeEventListener(clip);
        GLCanvas glcanvas = new GLCanvas();
        glcanvas.addKeyListener(listener);
        glcanvas.addGLEventListener(listener);
        glcanvas.addMouseListener(listener);
        glcanvas.addMouseMotionListener(listener);
        getContentPane().add(glcanvas, BorderLayout.CENTER);
        Animator animator = new FPSAnimator(60);
        animator.add(glcanvas);
        animator.start();
        setTitle("Hokey Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);
        glcanvas.requestFocus();

        // Play background music or sound effect
        // clip.start();

    }
}
        class HomeEventListener implements GLEventListener, MouseMotionListener, MouseListener, KeyListener {

    final static String ASSETS_PATH = "Assets\\Sprites";
    final static String[] textureNames = new File(ASSETS_PATH).list();
    final TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
    final int textures[] = new int[textureNames.length];
    final int orthoX = 600, orthoY = 350;
    int windowWidth = 2 * orthoX, windowHeight = 2 * orthoY, flag[] = {0};

            GL gl; // global gl drawable to use in the class
            Clip clip;

            public HomeEventListener(Clip clip) {
                this.clip = clip;
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
            private void playSound(String filePath) {
                try {
                    File soundFile = new File(filePath);
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    clip.start();
                } catch (Exception e) {
                    System.err.println("Error playing sound: " + e.getMessage());
                }
            }

            @Override
            public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
            }

            @Override
            public void reshape(GLAutoDrawable drawable, int x, int y, int width, int high) {

            }
        }
