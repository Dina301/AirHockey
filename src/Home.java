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
    }
}
        class HomeEventListener implements GLEventListener, MouseMotionListener, MouseListener, KeyListener {

        getContentPane().add(glcanvas, BorderLayout.CENTER);//m

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