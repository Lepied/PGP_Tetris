package tetris;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.AudioFormat; // 
import javax.sound.sampled.AudioInputStream; //
import javax.sound.sampled.AudioSystem; //
import javax.sound.sampled.Clip; //
import javax.sound.sampled.DataLine; //
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Timer;
import javax.swing.JDialog;
import javax.swing.JButton;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("테트리스");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainMenuPanel = new JPanel();
        getContentPane().add(mainMenuPanel, BorderLayout.CENTER);
        
        JPanel imagePanel = new JPanel();
        try {
        	ImageIcon tetrisImage = new ImageIcon("images/tetrislogo.png");
            double scale = 0.3;
            int width = (int)(tetrisImage.getIconWidth()*scale);
            int height = (int)(tetrisImage.getIconHeight()*scale);
      
            Image img = tetrisImage.getImage().getScaledInstance
            		(width, height, DO_NOTHING_ON_CLOSE);
            ImageIcon resizedTetrisImage = new ImageIcon(img);

            imagePanel.setLayout(new BorderLayout(0, 0));
            
            JLabel imageLabel = new JLabel(resizedTetrisImage);
            imagePanel.add(imageLabel);
            getContentPane().add(imagePanel, BorderLayout.NORTH);
        }
        catch(Exception ex)
        {
        	ex.printStackTrace();
        	ImageIcon tetrisImage = new ImageIcon("C:\\Users\\gang0\\git\\PGP_Tetris\\MyTetris\\images\\tetrislogo.png");
            double scale = 0.3;
            int width = (int)(tetrisImage.getIconWidth()*scale);
            int height = (int)(tetrisImage.getIconHeight()*scale);
      
            Image img = tetrisImage.getImage().getScaledInstance
            		(width, height, DO_NOTHING_ON_CLOSE);
            ImageIcon resizedTetrisImage = new ImageIcon(img);

            imagePanel.setLayout(new BorderLayout(0, 0));
            
            JLabel imageLabel = new JLabel(resizedTetrisImage);
            imagePanel.add(imageLabel);
            getContentPane().add(imagePanel, BorderLayout.NORTH);
        }


        JButton soloButton = new JButton("혼자하기");
        soloButton.setBounds(125, 91, 120, 30);
        
        JButton multiplayerButton = new JButton("같이하기");
        multiplayerButton.setBounds(125, 131, 120, 30);
        
        JButton exitButton = new JButton("종료");
        exitButton.setBounds(125, 211, 120, 30);
        
        mainMenuPanel.setLayout(null);

        mainMenuPanel.add(soloButton);
        mainMenuPanel.add(multiplayerButton);
        mainMenuPanel.add(exitButton);
        

        soloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoloPlay.main(new String[0]);
                MainMenu.this.setExtendedState(JFrame.NORMAL); // 원래 크기로 복원

            }
        });

        multiplayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//new MyTetris();
            	MyTetris.main(new String[0]);
            	

            }
        });


        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 종료 버튼 클릭 시 프로그램 종료
                System.exit(0);
            }
        });

        setLocationRelativeTo(null); // 화면 중앙에 표시
        setVisible(true);
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainMenu();
        });
    }
}