import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

public class FenetreInformation extends JFrame {

    private JFrame winExplications;
    private JButton btnCompris;
    private JButton btnExplications;

    public FenetreInformation() {
        super("Manuel d'utilisation");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(850, 50, 420, 600);
        setResizable(false);
        setLayout(null);

        JPanel panFond = new JPanel();
        panFond.setBounds(0, 0, getWidth(), getHeight());

        //paramètre du bouton

        Font police = new Font("Norwester", Font.BOLD, 15);

        btnExplications = new JButton("Informations");
        btnExplications.setBackground(new Color(64, 76, 98));
        btnExplications.setForeground(new Color(243, 135, 37));
        btnExplications.setFont(police);
        btnExplications.setBounds(getWidth() / 4, 50, getWidth() / 2, 40);
        btnExplications.addActionListener(e -> winExplications.setVisible(true));

        //image de fond
        ImageIcon imageFond = null;
        try {
            imageFond = new ImageIcon(ImageIO.read(new File("src/fond projet.jpg")).getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Can't load image.");
            System.exit(-1);
        }

        JLabel fond = new JLabel(imageFond);
        fond.setBounds(0, 0, getWidth(), getHeight());
        fond.add(btnExplications);
        panFond.add(fond);

        setContentPane(panFond);
        setVisible(true);

        winExplications = new JFrame("Explications");
        winExplications.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        winExplications.setBounds(1000, 50, 350, 500);
        winExplications.setLayout(null);

        JPanel expFond = new JPanel();
        expFond.setBounds(0, 0, winExplications.getWidth(), winExplications.getHeight());
        expFond.setBackground(new Color(16, 31, 64));

        btnCompris = new JButton("Compris !");
        btnCompris.setBackground(new Color(64, 76, 98));
        btnCompris.setForeground(new Color(243, 135, 37));
        btnCompris.setFont(police);
        btnCompris.setBounds(250, 820, 150, 40);
        btnCompris.addActionListener(e -> winExplications.setVisible(false));

        expFond.add(btnCompris);
        winExplications.add(expFond);
    }
}
