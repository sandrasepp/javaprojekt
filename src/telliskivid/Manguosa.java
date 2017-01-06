package telliskivid;


import javax.swing.*;
//import java.awt.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;


/**
 * Created by aleksandra on 02/01/2017.
 */
public class Manguosa extends JPanel implements KeyListener, ActionListener{
    private boolean mangib = false;
    private int punktid = 0;

    private int kokkuKuubikuid = 14;

    private Timer timer;
    private int kiirus =8;

    private int mangijaX = 310;  //rula positsioon x teljel

    private int pallikorX =120;
    private int pallikorY = 350;
    private int pallisuundX = -1;
    private int pallisuundY = -2;

    private Kuubikud mas;


    public Manguosa(){
        mas = new Kuubikud(2, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(kiirus, this);
        timer.start();

    }
    public void paint(Graphics f) {

        f.setColor(Color.white);       //taust
        f.fillRect(1,1, 692, 592);

        mas.joonista((Graphics2D)f);       //joonistame kuubikud

        f.setColor(Color.red);         // ääred
        f.fillRect(0, 0, 3, 592);
        f.fillRect(0, 0, 692, 3);
        f.fillRect(691, 0, 3, 592);

        f.setColor(Color.black);         //punktid
        f.setFont(new Font("serif", Font.BOLD, 25));
        f.drawString(""+punktid, 590, 30);

        f.setColor(Color.green);             // rula
        f.fillRect(mangijaX, 550, 100, 8);

        f.setColor(Color.blue);                   // pall
        f.fillOval(pallikorX, pallikorY, 20, 20);

        if(kokkuKuubikuid <= 0) {     //mäng võidetud
            mangib = false;
            pallisuundX = 0;
            pallisuundY = 0;
            f.setColor(Color.GREEN);
            f.setFont(new Font("serif", Font.BOLD, 30));
            f.drawString("Sa võitsid. Sinu tulemus: "+punktid , 230, 300);

            f.setFont(new Font("serif", Font.BOLD, 20));
            f.drawString("Vajuta Enter uuesti mängimiseks", 230, 350);

        }

        if(pallikorY > 570) {      //mäng kaotatud
            mangib = false;
            pallisuundX = 0;
            pallisuundY = 0;
            f.setColor(Color.RED);
            f.setFont(new Font("serif", Font.BOLD, 30));
            f.drawString("Seekord ei vedanud =) Sinu tulemus: "+punktid, 130, 300);

            f.setFont(new Font("serif", Font.BOLD, 20));
            f.drawString("Vajuta Enter uuesti mängimiseks", 230, 350);
        }

        f.dispose();

    }


    public void actionPerformed(ActionEvent e){
        timer.start();
        if(mangib){
            if(new Rectangle(pallikorX,pallikorY, 20, 20).intersects(new Rectangle(mangijaX, 550, 100, 8))){
                pallisuundY = -pallisuundY;
            }

           A: for(int i = 0; i< mas.mas.length; i++) {
                                for (int j =0; j<mas.mas[0].length; j++){
                                    if(mas.mas[i][j] > 0){
                                        int kuubikuX = j*mas.kuubikuLaius + 80;
                                        int kuubikuY = i* mas.kuubikuKõrgus +50;
                                        int kuubikuLaius = mas.kuubikuLaius;
                                        int kuubikuHeigth = mas.kuubikuKõrgus;

                                        Rectangle rect = new Rectangle(kuubikuX, kuubikuY, kuubikuLaius, kuubikuHeigth);
                                        Rectangle pallRect = new Rectangle(pallikorX, pallikorY, 20, 20);
                                        Rectangle kuubikuRect = rect;

                                        if (pallRect.intersects(kuubikuRect)){
                                            mas.setKuubikuArv(0, i, j);
                                            kokkuKuubikuid--;
                                            punktid +=5;

                                            if(pallikorX + 19 <= kuubikuRect.x || pallikorX + 1 >= kuubikuRect.x + kuubikuRect.width) {
                                                pallisuundX = -pallisuundX;

                                            } else {
                                                pallisuundY = -pallisuundY;

                                            }
                                            break A;


                        }
                    }
                }
            }

            pallikorX += pallisuundX;
            pallikorY += pallisuundY;
            if(pallikorX < 0){
                pallisuundX = -pallisuundX;
            }
            if(pallikorY < 0){
                pallisuundY = -pallisuundY;
            }
            if(pallikorX > 670){
                pallisuundX = -pallisuundX;
            }

        }

        repaint();

    }
    public void keyTyped(KeyEvent e){}
    public void keyReleased(KeyEvent e){}

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (mangijaX >= 600) {
                mangijaX = 600;
            } else {
                liiguParemale();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (mangijaX < 10) {
                mangijaX = 10;
            } else {
                liiguVasakule();
                }
            }
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                if(!mangib){
                    mangib = true;
                    pallikorX = 120;
                    pallikorY = 350;
                    pallisuundX = -1;
                    pallisuundY = -2;
                    mangijaX = 310;
                    punktid = 0;
                    kokkuKuubikuid = 14;
                    mas = new Kuubikud(2, 7);

                    repaint();
                }
            }


        }
        public void liiguParemale(){
            mangib = true;
            mangijaX+=20;
    }
        public void liiguVasakule(){
            mangib = true;
            mangijaX-=20;
    }

    }
