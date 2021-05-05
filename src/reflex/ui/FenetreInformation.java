package reflex.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;

public class FenetreInformation extends JFrame {

    private static final Color foregroundColor = new Color(243, 135, 37);
    private static final Color backgroundColor = new Color(16, 31, 64);
    private static final Color btnBackground = new Color(64, 76, 98);
    private final JPanel panelPresentation;
    private final JPanel panelExplications;
    private final JButton btnCompris;
    private final JButton btnExplications;

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
        btnExplications.setBackground(btnBackground);
        btnExplications.setForeground(foregroundColor);
        btnExplications.setFont(police);
        btnExplications.setBounds(getWidth() / 4, 50, getWidth() / 2, 40);

        //image de fond
        ImageIcon imageFond = null;
        try {
            imageFond = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("fond projet.jpg")).getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH));
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
        panelExplications.setBackground(backgroundColor);
        panelExplications.setLayout(null);

        btnCompris = new JButton("Compris !");
        btnCompris.setBackground(btnBackground);
        btnCompris.setForeground(foregroundColor);
        btnCompris.setFont(police);
        btnCompris.setBounds(250, 480, 100, 60);
        btnCompris.setBounds(3 * getWidth() / 8, getHeight() - 100, getWidth() / 4, 40);

        panelExplications.add(btnCompris);

        JLabel infos = new JLabel();
        infos.setFont(police);
        infos.setForeground(foregroundColor);
        infos.setBounds(20, 20, 560, 140);
        infos.setText("<html>Reflex est un simulateur de visualisation d'optique. <br>En effet, grâce à Reflex, vous allez pouvoir visualiser les phénomènes <br> de réflexion et de diffraction par différents objets couramment <br>utilisés dans ce domaine. <br> Il vous sera possible de changer totalement les configurations<br>en déplaçant les objets et en les faisant pivoter.<br>Voici les commandes à votre disposition:</html>");

        panelExplications.add(infos);

        JLabel com1 = new JLabel();
        com1.setFont(police);
        com1.setForeground(foregroundColor);
        com1.setBounds(20, 170, 280, 340);
        com1.setText("<html><ul>" +
                         "<li>Sélectionner un objet (pointillets) : Clic gauche</li>" +
                         "<li>Déplacer :<br>Maintenir click gauche souris & flèches du clavier</li>" +
                         "<li>Pivoter : molette</li>" +
                         "<li>Modifier le premier paramètre (taille, longueur d'onde) :<br>CTRL + Molette</li>" +
                         "<li>Modifier le deuxième paramètre (taille, intensité) :<br>SHIFT + Molette</li>" +
                         "<li>Supprimer : SUPPR/DEL</li>" +
                         "</ul></html>");

        JLabel com2 = new JLabel();
        com2.setFont(police);
        com2.setForeground(foregroundColor);
        com2.setBounds(300, 170, 280, 340);
        com2.setText("<html><ul>" +
                         "<li>Choisir une configuration prédéfinie :<br>touche 1, 2, 3, 4 du clavier</li>" +
                         "<li>Rafraichir l'affichage :<br>touche R</li><li>Augmenter la limite de calcul des rayons :<br>CTRL + Molette</li>" +
                         "<li>Augmenter la limite d'affichage des rayons (intensité) :<br>SHIFT + Molette</li>" +
                         "</ul></html>");

        panelExplications.add(com1);
        panelExplications.add(com2);

        btnExplications.addActionListener(e -> setContentPane(panelExplications));
        btnCompris.addActionListener(e -> setContentPane(panelPresentation));

        setContentPane(panelPresentation);
        setVisible(true);
    }
}
