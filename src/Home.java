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

            public HomeEventListener(Clip clip) {
                this.clip = clip;
            }
        }