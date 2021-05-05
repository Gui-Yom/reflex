import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

public class FenetreInformation extends JFrame {

    private JPanel panelPresentation;
    private JPanel panelExplications;
    private JButton btnCompris;
    private JButton btnExplications;

    public FenetreInformation() {
        super("Manuel d'utilisation");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(850, 50, 600, 600);
        setResizable(false);
        setLayout(null);

        panelPresentation = new JPanel();
        panelPresentation.setBounds(0, 0, getWidth(), getHeight());

        //paramètre du bouton

        Font police = new Font("Norwester", Font.BOLD, 15);
		Font police2 = new Font("Norwester", Font.BOLD, 12);

		
		
        btnExplications = new JButton("Informations");
        btnExplications.setBackground(new Color(64, 76, 98));
        btnExplications.setForeground(new Color(243, 135, 37));
        btnExplications.setFont(police);
        btnExplications.setBounds(getWidth() / 4, 50, getWidth() / 2, 40);

        //image de fond
        ImageIcon imageFond = null;
        try {
            imageFond = new ImageIcon(ImageIO.read(new File("fond projet.jpg")).getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Can't load image.");
            System.exit(-1);
        }

        JLabel fond = new JLabel(imageFond);
        fond.setBounds(0, 0, getWidth(), getHeight());
        fond.add(btnExplications);
        panelPresentation.add(fond);

        // Panel d'explications
        panelExplications = new JPanel();
        panelExplications.setLayout(null);
        panelExplications.setBounds(0, 0, getWidth(), getHeight());
        panelExplications.setBackground(new Color(16, 31, 64));
		panelExplications.setLayout(null);
		
        btnCompris = new JButton("Compris !");
        btnCompris.setBackground(new Color(64, 76, 98));
        btnCompris.setForeground(new Color(243, 135, 37));
        btnCompris.setFont(police);
<<<<<<< HEAD
        btnCompris.setBounds(250, 480, 100, 60);
=======
        btnCompris.setBounds(getWidth() / 4, 50, getWidth() / 2, 40);
>>>>>>> 815aa3e369e8d61453f2ba8f92dfc663f400413d

        panelExplications.add(btnCompris);
        
        JLabel infos = new JLabel();
        infos.setFont(police);
        infos.setForeground(new Color(243, 135, 37));
        infos.setBackground(new Color(16,31,64));
        infos.setBounds(20,20,560,140);
        infos.setText("<html>Reflex est un simulateur de visualisation d'optique. <br>En effet, grâce à Reflex, vous allez pouvoir visualiser les phénomènes <br> de réflexion et de diffraction par différents objets couramment <br>utilisés dans ce domaine. <br> Il vous sera possible de changer totalement les configurations <br> en déplaçant les objets et en les faisant pivoter.<br>Voici les commandes à votre disposition:<html>");
        
        panelExplications.add(infos);

		JLabel com1 = new JLabel();
		com1.setFont(police);
        com1.setForeground(new Color(243, 135, 37));
        com1.setBackground(new Color(16,31,64));
        com1.setBounds(60,170,140,360);
        com1.setText("<html>-Sélectionner un objet (passe en pointillets) :<br> Click gauche sur l'objet<br><br> Commandes pour l'objet sélectionné :<br>-Déplacer: <br> Maintenir click gauche souris<br> flèches du clavier <br> -Pivoter : <br> molette souris <br> -Agrandir : <br> CTRL+Molette <br> -Supprimer : <br> touche SUPPR <html>");
        
        JLabel com2 = new JLabel();
        com2.setFont(police);
        com2.setForeground(new Color(243,135,37));
        com2.setBackground(new Color(16,31,64));
        com2.setBounds(400,170,140,340);
        com2.setText("<html>-Choisir une configuration prédéfinie : <br> touche 1, 2 ou 3 du clavier <br> -Rafraichir l'affichage : <br> touche R <br> - Augmenter la limite de calcul des rayons : <br> CTRL + Molette <br> -Augmenter la limite d'affichage des rayons : <br> SHIFT + Molette<html>");
        
        panelExplications.add(com1);
        panelExplications.add(com2);
        
		

        btnExplications.addActionListener(e -> setContentPane(panelExplications));
        btnCompris.addActionListener(e -> setContentPane(panelPresentation));

        setContentPane(panelPresentation);
        setVisible(true);
    }
}
