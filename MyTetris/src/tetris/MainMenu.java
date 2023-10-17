package tetris;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("테트리스");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainMenuPanel = new JPanel();
        getContentPane().add(mainMenuPanel, BorderLayout.CENTER);
        
        JPanel imagePanel = new JPanel();
        ImageIcon tetrisImage = new ImageIcon("C:\\Users\\USER\\AppData\\Roaming\\SPB_16.6\\git\\PGP_Tetris\\MyTetris\\src\\images\\tetrislogo.png");
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

        JButton soloButton = new JButton("혼자하기");
        soloButton.setBounds(125, 91, 120, 30);
        JButton multiplayerButton = new JButton("같이하기");
        multiplayerButton.setBounds(125, 131, 120, 30);
        JButton optionsButton = new JButton("옵션");
        optionsButton.setBounds(125, 171, 120, 30);
        JButton exitButton = new JButton("종료");
        exitButton.setBounds(125, 211, 120, 30);
        mainMenuPanel.setLayout(null);

        mainMenuPanel.add(soloButton);
        mainMenuPanel.add(multiplayerButton);
        mainMenuPanel.add(optionsButton);
        mainMenuPanel.add(exitButton);
        
        //soloButton.setPreferredSize(buttonSize);
        //multiplayerButton.setPreferredSize(buttonSize);
        //optionsButton.setPreferredSize(buttonSize);
        //exitButton.setPreferredSize(buttonSize);

        soloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 혼자하기 버튼 클릭 시 실행할 코드
                // TetrisCanvas와 Preview 캔버스를 화면에 표시
                // 예: new SoloTetrisGame();
            }
        });

        multiplayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 같이하기 버튼 클릭 시 실행할 코드
                // 위에서 구현한 MyTetris 클래스의 기능과 UI를 활용
                // 예: MyTetris의 인스턴스를 만들어 show 메서드 호출
            }
        });

        optionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 옵션 버튼 클릭 시 실행할 코드
                // 옵션 화면을 표시
                // 예: new OptionsMenu();
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