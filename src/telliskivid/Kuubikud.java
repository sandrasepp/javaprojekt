package telliskivid;

import java.awt.*;
//import java.awt.Graphics2D;

/**
 * Created by aleksandra on 02/01/2017.
 */
public class Kuubikud {
    public int mas[][];
    public int kuubikuLaius;
    public int kuubikuKõrgus;
    public Kuubikud(int rida, int tulp) {
        mas = new int[rida][tulp];
        for(int i = 0; i < mas.length; i++) {
            for(int j=0; j< mas[0].length; j++) {
                mas[i][j] = 1;
            }
        }
        kuubikuLaius = 540/tulp;
        kuubikuKõrgus = 75/rida;

    }
    public void joonista(Graphics2D g) {
        for(int i = 0; i < mas.length; i++) {
            for(int j=0; j< mas[0].length; j++) {
                if(mas[i][j] > 0){
                    g.setColor(Color.pink);
                    g.fillRect(j*kuubikuLaius + 80, i*kuubikuKõrgus+ 50, kuubikuLaius, kuubikuKõrgus);

                    g.setStroke(new BasicStroke(1));
                    g.setColor(Color.black);
                    g.drawRect(j*kuubikuLaius + 80, i*kuubikuKõrgus+ 50, kuubikuLaius, kuubikuKõrgus);
                }
            }
        }
    }
    public void setKuubikuArv(int arv, int rida, int tulp) {
        mas[rida][tulp] = arv;
    }
}
