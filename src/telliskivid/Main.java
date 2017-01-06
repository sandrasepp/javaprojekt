package telliskivid;

import javax.swing.JFrame; //JFrame, impordin paketi

/**
 * Created by aleksandra on 02/01/2017.
 */
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Manguosa manguOsa = new Manguosa();
        frame.setBounds(10, 10, 700, 600);
        frame.setTitle("Aleksandra mäng");
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(manguOsa);
    }

}
