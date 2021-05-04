import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main {

    private static JFrame frame;
    private static Simulation sim;
    private static Canvas canvas;
    private static JFrame accueil;

    public static void main(String[] args) {

        // Force l'utilisation du gpu pour le rendu
        System.setProperty("sun.java2d.opengl", "true");

        frame = new JFrame("Reflex");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                //super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    frame.dispose();
                }
            }
        });
        frame.setLayout(new BorderLayout());

        sim = new Simulation();
        canvas = new Canvas(sim);
        frame.add(canvas, BorderLayout.CENTER);

        JPanel menu = new JPanel();
        frame.add(menu, BorderLayout.NORTH);
        menu.setLayout(new FlowLayout());

        JButton reinit = new JButton("Reinitialiser");
        reinit.addActionListener(e -> {
            sim.clear();
            canvas.recalc();
        });
        menu.add(reinit);
        JButton btnDemidisque = new JButton("Demi-disque");
        btnDemidisque.addActionListener(e -> {
            sim.add(new DemiDisque(new Vec2d(500, 400), 0, Constants.REFRAC_GLASS, 100));
            canvas.recalc();
        });
        menu.add(btnDemidisque);
        JButton btnMiroir = new JButton("Miroir");
        btnMiroir.addActionListener(e -> {
            sim.add(new Mirror(new Vec2d(250, 300), 100, 0));
            canvas.recalc();
        });
        menu.add(btnMiroir);
        JButton btnLamesP = new JButton("Lame a faces paralleles");
        btnLamesP.addActionListener(e -> {
            sim.add(new LamesP(new Vec2d(200f, 300f), 200, 100, Constants.REFRAC_GLASS));
            canvas.recalc();
        });
        menu.add(btnLamesP);

        frame.setVisible(true);
        
        accueil = new JFrame("accueil - Reflex");
        accueil.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        accueil.setBounds(1000,50,800,1000);
        accueil.setLayout(null);
        
        JPanel panFond = new JPanel();
        panFond.setBounds(0,0,accueil.getWidth(),accueil.getHeight());
        
        Font police = new Font ("Norwester", Font.BOLD,15);
        
        JButton explication = new JButton("Explications - à lire - IMPORTANT");
        explication.setBackground(new Color(64,76,98));
        explication.setForeground(new Color(243,135,37));
        explication.setFont(police);
        explication.setBounds(250,50,300,80);
		//panFond.add(explication);
        
        
        //image de fond
		ImageIcon fondMenu = new ImageIcon ("fond projet.jpg");
		ImageIcon resultat = new ImageIcon ( fondMenu.getImage().getScaledInstance(800,1000, Image.SCALE_DEFAULT));
		
		JLabel fond = new JLabel (resultat);
		fond.setBounds(0,0,800,1000);
		fond.add(explication);
		panFond.add(fond);
		
		
		accueil.setContentPane(panFond);
        
        accueil.setVisible(true);
        
        
    }
}
