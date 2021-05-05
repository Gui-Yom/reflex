package reflex.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;

public class FenetreInformation extends JFrame {

    private final JPanel panelPresentation;
    private final JPanel panelExplications;
    private final JButton btnCompris;
    private final JButton btnExplications;

    public FenetreInformation() {
        super("Manuel d'utilisation");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(850, 50, 420, 600);
        setResizable(false);
        setLayout(null);

        panelPresentation = new JPanel();
        panelPresentation.setBounds(0, 0, getWidth(), getHeight());

        //paramètre du bouton

        Font police = new Font("Norwester", Font.BOLD, 15);

        btnExplications = new JButton("Informations");
        btnExplications.setBackground(new Color(64, 76, 98));
        btnExplications.setForeground(new Color(243, 135, 37));
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
        panelExplications.setBounds(0, 0, getWidth(), getHeight());
        panelExplications.setBackground(new Color(16, 31, 64));

        btnCompris = new JButton("Compris !");
        btnCompris.setBackground(new Color(64, 76, 98));
        btnCompris.setForeground(new Color(243, 135, 37));
        btnCompris.setFont(police);
        btnCompris.setBounds(250, 820, 150, 40);

        panelExplications.add(btnCompris);

        btnExplications.addActionListener(e -> setContentPane(panelExplications));
        btnCompris.addActionListener(e -> setContentPane(panelPresentation));

        setContentPane(panelPresentation);
        setVisible(true);
    }
}
