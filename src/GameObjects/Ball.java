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
        public int playerscore = 0;
        public String player1;
        public String player2;

        Timer timer;


        public Ball(int[] textures, int x, int y, Hand handRight, Hand handLeft, String[] playerName, Timer timer,
                    GL gl) {
            super(textures[38], x, y, gl);//r
            this.textures = textures;
            this.handRight = handRight;
            this.handLeft = handLeft;
            handRight.ball = this;
            handLeft.ball = this;
            this.timer = timer;
            this.playerName = playerName;
        }

        public void draw() {//b
            super.draw();
            checkCollide();
            move();
            checkWinner();


        }
        public void move() {
            if (!this.move)
                return;

            super.x += dx;
            super.y += dy;

            moveTo();

            if (handLeft.AI) {
                if (x <= 0) {
                    double ddx = (x - handLeft.x), ddy = (y - handLeft.y);
                    double dis = Math.sqrt((ddx * ddx) + (ddy * ddy));
                    handLeft.x += levels[handLeft.level] * Math.atan(ddx / dis);
                    handLeft.y += levels[handLeft.level] * Math.atan(ddy / dis);

                } else if (handLeft.level <= 2) {
                    if (handLeft.x <= -300 && handLeft.y == 0)
                        return;
                    double ddx = (-440 - handLeft.x), ddy = (0 - handLeft.y);
                    double dis = Math.sqrt((ddx * ddx) + (ddy * ddy));
                    handLeft.x += levels[handLeft.level] * Math.atan(ddx / dis);
                    handLeft.y += levels[handLeft.level] * Math.atan(ddy / dis);

                } else {
                    if (handLeft.x <= -150 && handLeft.y == 0)
                        return;
                    double ddx = (-350 - handLeft.x), ddy = (0 - handLeft.y);
                    double dis = Math.sqrt((ddx * ddx) + (ddy * ddy));
                    handLeft.x += levels[handLeft.level] * Math.atan(ddx / dis);
                    handLeft.y += levels[handLeft.level] * Math.atan(ddy / dis);

                }
            }
        }

        public void moveTo() {
            if (this.x < -530)
                this.x = -530;
            if (this.x > 530)
                this.x = 530;

            if (this.y > 280)
                this.y = 280;
            if (this.y < -280)
                this.y = -280;
        }
        private void checkCollide() {//b
            if ((super.x >= 530 || super.x <= -530)) {
                if (y >= 100 || y <= -100) {
                    if (left_wallFlag) {
                        dx = -dx;
                        // dy = -dy;
                        left_wallFlag = false;
                    } else {
                        left_wallFlag = true;
                    }
                } else {
                    if (x > 0) {
                        x = 90;
                        y = 0;
                        dy = 0;
                        dx = 0;
                        handLeft.score++;
                    } else if (x < 0) {
                        x = -90;
                        y = 0;
                        dy = 0;
                        dx = 0;
                        handRight.score++;
                        playerscore+=100;
                    }

                }

            }
            if ((super.y >= 280 || super.y <= -280) && up_wallFlag) {
                up_wallFlag = false;
                dy = -dy;
            } else {
                up_wallFlag = true;
            }

            double d1 = GameObjects.distance(this, handRight);
            double d2 = GameObjects.distance(this, handLeft);
            if ((d1 <= 7000 && flag2)) {
                if (x - handRight.x == 0) {
                    if (y < handRight.y) {
                        dx = 0;
                        dy = -10;
                    } else {
                        dx = 0;
                        dy = 10;
                    }
                } else if (y - handRight.y == 0) {
                    if (x < handRight.x) {
                        dx = -10;
                        dy = 0;
                    } else {
                        dx = 10;
                        dy = 0;
                    }
                } else {
                    int p1 = handRight.x - this.x > 0 ? -1 : 1;
                    int p2 = handRight.y - this.y > 0 ? -1 : 1;
                    double angle = Math.atan(-1 * (y - handRight.y) / (x - handRight.x));

                    dx = p1 * Math.abs(Math.cos(angle) * 10);
                    dy = p2 * Math.abs(Math.sin(angle) * 10);
                }
                flag2 = false;
            }
            if ((d2 <= 7000 && flag3)) {
                if (x - handLeft.x == 0) {
                    // check if the ball hit the player from behind
                    if (y < handLeft.y) {
                        dx = 0;
                        dy = -10;
                    } else {
                        dx = 0;
                        dy = 10;
                    }
                } else if (y - handLeft.y == 0) {
                    if (x < handLeft.x) {
                        dx = -10;
                        dy = 0;
                    } else {
                        dx = 10;
                        dy = 0;
                    }
                } else {
                    int p1 = handLeft.x - this.x > 0 ? -1 : 1;
                    int p2 = handLeft.y - this.y > 0 ? -1 : 1;
                    double angle = Math.atan(-1 * (y - handLeft.y) / (x - handLeft.x));
                    dx = p1 * Math.abs(Math.cos(angle) * 10);
                    dy = p2 * Math.abs(Math.sin(angle) * 10);
                }
                flag3 = false;
            }
            if (d1 > 7000) {
                flag2 = true;
            } else {
                dy *= 1.1;
                dx *= 1.1;
            }
            if (d2 > 7000) {
                flag3 = true;
            } else {
                dy *= 1.1;
                dx *= 1.1;
            }

        }
        public void checkCollapse() {
            if (distance(this, handRight) <= 7000)
                flag = true;
        }

        public void checkWinner() {//b

            final int TIME_LIMIT = 10; // 60 ثانية

            if (timer.getSeconds() < TIME_LIMIT)
                return;


            move = false;
            timer.stop();

            int winnerIndex = -1;

            if (handLeft.score > handRight.score) {
                winnerIndex = 0; // handLeft فاز
            } else if (handRight.score > handLeft.score) {
                winnerIndex = 1; // handRight فاز
            }

            //tie
            tieWindowVisible = true;
            draw(textures[63], 0, 0, 350, 300);
            if (winnerIndex == -1) {
                draw(textures[64],0,130,200,60);
                drawResultButtons();
            }
            //
            int x = winnerIndex == 0 ? -120 : 40;  // عدلي الأرقام حسب مكانك

            //
            String winnerText ="";
            if (!handLeft.AI) {
                //
                if(winnerIndex==0) drawText(player1+" wins",-30,110);
                else if(winnerIndex==1)
                    drawText(player2+" wins",-30,110);
            } else {
                if (winnerIndex == -1) {
                    draw(textures[64],0,130,200,60);
                    drawResultButtons();
                }

                else if (winnerIndex == 0) {
                    draw(textures[61], 0, 130, 200, 60);//sign you lost
                } else {
                    draw(textures[62], 0, 130, 200, 60);//sign you win
                }

                drawText(winnerText, x, 80);
            }

            // نفس الزرارين بتوع التعادل
            drawResultButtons();

            if (!handLeft.AI)      // 2-Players → ما نحفظش
                return;

            if (scoreAdded)        // لو حفظنا قبل كده ما نكررش
                return;

            try {
                // taking the exists high scores from the file
                File file = new File("data\\score.txt");
                Scanner in = new Scanner(file);
                String str = "";
                while (in.hasNext()) {
                    str += in.nextLine() + '\n';
                }
                in.close();

                // update the high scores to the new player
                int score = playerscore;
                str += playerName[0] + " " + score;
                PrintWriter print = new PrintWriter(file);
                print.print(str);
                print.close();
                scoreAdded = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }



        }
        public void drawText(String text, int x, int y) {
            GLUT glut = new GLUT();
            gl.glRasterPos2f(x, y);
            for (int i = 0; i < text.length(); i++) {
                glut.glutBitmapCharacter(GLUT.BITMAP_HELVETICA_18, text.charAt(i));
            }
        }//sh
        private void drawResultButtons() {

            draw(textures[59], 0, 10, 200, 60);    //Play Again
            draw(textures[60], 0, -50, 200, 60); // Menu / Back
        }
        public void reset() {
            super.x = 0;
            super.y = 0;
            this.dx = 0;
            this.dy = 0;
            this.scoreAdded = false;
            this.move = true;
        }


















    }


