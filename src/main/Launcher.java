package main;

import states.Menu;

import javax.swing.JFrame;

public class Launcher implements Runnable {

    private Thread gameThread;
    private Menu menu;

    public Launcher() {
        menu = new Menu();
        JFrame frame = new JFrame("Aplicación Bancaria");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(menu);
        frame.pack();
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);
    }

    @Override
    public void run() {
       
    }

    public static void main(String[] args) {
        new Launcher();
    }
}
