package GameObjects;

import Pages.Timer;

import javax.media.opengl.GL;

import Pages.Timer;
import com.sun.opengl.util.GLUT;
    import javax.media.opengl .*;
    import java.io .*;
    import java.util.Scanner;
    public class Ball extends GameObjects {//b
        public double dx = 0, dy = 0;
        public boolean move = true;
        private Hand handRight;
        private Hand handLeft;
        String[] playerName;
        boolean flag, flag2 = true, flag3 = true, up_wallFlag = true, left_wallFlag = true, scoreAdded = false;
        int[] textures;
        int[] levels = {5, 5, 7, 9};
        public boolean tieWindowVisible;
        /*---->*/public int playerscore = 0;
        public String player1;
        public String player2;

        Timer timer;


        public Ball(int[] textures, int x, int y, Hand handRight, Hand handLeft, String[] playerName, Timer timer,
                    GL gl) {
            this.handRight = handRight;
            this.handLeft = handLeft;
            handRight.ball = this;
            handLeft.ball = this;
            this.timer = timer;
            this.playerName = playerName;
        }

        public void draw() {//b
            super.draw();


        }
















    }
}

