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
    int windowWidth = 2 * orthoX, windowHight = 2 * orthoY, flag[] = {0};

            GL gl; // global gl drawable to use in the class
            int[] mouse = new int[2]; // tracking mouse position
            boolean[] mouseClicked = { false }; // tracking mouseClicked
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
            public void mousePressed(MouseEvent e) {
                windowHight = e.getComponent().getHeight();
                windowWidth = e.getComponent().getWidth();
                mouse[0] = (int) convertX(e.getX()); // set the mouse position
                mouse[1] = (int) convertY(e.getY()); // set the mouse position
                mouseClicked[0] = true; // set the mouse clicked

                if (flag[0] == 0) {
                    if (mouse[0] > -390 && mouse[0] < -130) {
                        if (mouse[1] > -100 && mouse[1] < 0) {


                            playSound("Assets\\sound\\LetsGo.wav");
                        }
                        if (mouse[1] > -250 && mouse[1] < -150) {


                            playSound("Assets\\sound\\LetsGo.wav");
                        }
                    }
                    else if (mouse[0] > 130 && mouse[0] < 390) {
                        if (mouse[1] > -100 && mouse[1] < 0) {
                            flag[0] = 3;
                            playSound("Assets\\sound\\letsGo.wav");
                        }
                        if (mouse[1] > -250 && mouse[1] < -150) {
                            flag[0] = 4;
                            playSound("Assets\\sound\\letsGo.wav");
                        }
                    }
                    if (mouse[1] > 275 && mouse[1] < 325) {

                        // Music toggle button
                        if (mouse[0] > 525 && mouse[0] < 575) {
                            // control music when click (music toggle)
                            if(clip.isActive()){
                                clip.stop();
                            } else {
                                clip.start();
                            }
                        }

                        if (mouse[0] < -525 && mouse[0] > -575) {
                            System.exit(0);
                        }
                    }

                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                windowHight = e.getComponent().getHeight();
                windowWidth = e.getComponent().getWidth();

                mouseClicked[0] = false; // set the mouse clicked
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                windowHight = e.getComponent().getHeight();
                windowWidth = e.getComponent().getWidth();

                mouse[0] = (int) convertX(e.getX()); // set the mouse position
                mouse[1] = (int) convertY(e.getY()); // set the mouse position
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                windowHight = e.getComponent().getHeight();
                windowWidth = e.getComponent().getWidth();

                mouse[0] = (int) convertX(e.getX()); // set the mouse position
                mouse[1] = (int) convertY(e.getY()); // set the mouse position
            }
            public void mouseClicked(MouseEvent e) {
                windowHight = e.getComponent().getHeight();
                windowWidth = e.getComponent().getWidth();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                windowHight = e.getComponent().getHeight();
                windowWidth = e.getComponent().getWidth();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                windowHight = e.getComponent().getHeight();
                windowWidth = e.getComponent().getWidth();
            }
            private double convertX(double x) {
                return x * (2 * orthoX) / windowWidth - orthoX;
            }

            private double convertY(double y) {
                return orthoY - ((2f * orthoY) / windowHight * y);
            }
        }
