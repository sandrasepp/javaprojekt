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
public class Manguosa extends JPanel implements KeyListener, ActionListener{   //loome klassi, mida laiendame paneeliks ja implementeerime Listenerid
    private boolean mangib = false;   //mängu staatus - kas käib?
    private int punktid = 0;  //punktide arv mängu alguses

    private int kokkuKuubikuid = 14;

    private Timer timer;
    private int kiirus =8;  //palli liikumise kiirus

    private int mangijaX = 310;  //rula positsioon x teljel

    private int pallikorX =120;   //palli koordinaadid mängu alguses
    private int pallikorY = 350;
    private int pallisuundX = -1;   //palli liikumise suund mängu käivitamisel
    private int pallisuundY = -2;

    private Kuubikud mas;


    public Manguosa(){   //main objekti tarbeks väärtused
        mas = new Kuubikud(2, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(kiirus, this);
        timer.start();

    }
    public void paint(Graphics f) {  //antud meetodis joonistame objekte

        f.setColor(Color.white);       //tausta värv
        f.fillRect(1,1, 692, 592);

        mas.joonista((Graphics2D)f);      //joonistame kuubikud meetodiga teisest klassist

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

        if(kokkuKuubikuid <= 0) {     //mäng võidetud, kui kõik kuubikud on tabatud
            mangib = false;   //eelmise tingimuse täitumisel lõpetatakse mängimise protsess
            pallisuundX = 0;  // peatame palli liikumist
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

        f.dispose(); //ressursi vabastus mängu lõppus

    }


    public void actionPerformed(ActionEvent e){  // reflection on action
        timer.start();
        if(mangib){
            if(new Rectangle(pallikorX,pallikorY, 20, 20).intersects(new Rectangle(mangijaX, 550, 100, 8))){
                pallisuundY = -pallisuundY;  //muudab palli suunda kokkupuutumisel rulaga
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

                                        if (pallRect.intersects(kuubikuRect)){  //kui pall põrkab vastu kuubikut, 
                                            mas.setKuubikuArv(0, i, j);  //lülitame kuubiku välja
                                            kokkuKuubikuid--;  
                                            punktid +=5;  //...ning lisame 5 p. iga kuubiku eest

                                            if(pallikorX + 19 <= kuubikuRect.x || pallikorX + 1 >= kuubikuRect.x + kuubikuRect.width) {
                                                pallisuundX = -pallisuundX;  //muudab palli suunda kokkupuutumisel kuubikuga

                                            } else {
                                                pallisuundY = -pallisuundY;

                                            }
                                            break A;  //tagasi tsükli algusesse


                        }
                    }
                }
            }

            pallikorX += pallisuundX; // palli liikumine
            pallikorY += pallisuundY;
            if(pallikorX < 0){  //kokkupõrkes seinaga palli suuna muutmine
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
    public void keyTyped(KeyEvent e){}  //pole vajalikud, kuid puudumisel tuleb error (KeyListeneri jaoks)
    public void keyReleased(KeyEvent e){}

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {  //rula liikumine, kontrollib, et ei liiguks ekraanist välja
            if (mangijaX >= 600) {
                mangijaX = 600;
            } else {
                liiguParemale();  //liikumise meetod, vt.all
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

                    repaint();  // paint meetodi välja kutsumine (tsükli taasalustamine)
                }
            }


        }
        public void liiguParemale(){
            mangib = true;
            mangijaX+=20;  //mille võrra muutub esialgne asukoht klahvi vajutamisega
    }
        public void liiguVasakule(){
            mangib = true;
            mangijaX-=20;
    }

    }
