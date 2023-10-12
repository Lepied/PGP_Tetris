package tetris;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.io.*;
import java.net.*;
import java.util.*;

public class MyTetris extends JFrame{

	private static final long serialVersionUID = 1L;
	private TetrisClient client = null;
	private SoundManager soundManager = new SoundManager();
	private TetrisData data;
	private TetrisCanvas tetrisCanvas = new TetrisCanvas(this);
	private TetrisNetworkPreview netPreview = null;
	
	private JPanel labelPanel; // 라벨 패널
	private JPanel gamePanel; // 전체 패널
	private JPanel canvasPanel; // 캔버스 패널
	private JLabel scoreLabel;
    private JLabel levelLabel;
    
    private int newScore;
    
	public MyTetris() {
		setTitle("테트리스");
		setSize(1280, 600);
		data = new TetrisData();

		labelPanel = new JPanel(new BorderLayout());
		gamePanel = new JPanel(new BorderLayout());
		TetrisCanvas tetrisCanvas = new TetrisCanvas(this);
		TetrisNetworkCanvas netCanvas = new TetrisNetworkCanvas();
		TetrisPreview preview = new TetrisPreview(tetrisCanvas.getData());
		netPreview = new TetrisNetworkPreview(netCanvas.getData());
		createMenu(tetrisCanvas, netCanvas);
		tetrisCanvas.setTetrisPreview(preview);
		//netCanvas.TetrisNetworkPreview(netPreview);
			
		scoreLabel = new JLabel("Score : " + data.getScore());
		levelLabel = new JLabel("Level : "+ (Constant.level-1)+"   ");
		
		labelPanel.add(levelLabel, BorderLayout.WEST);
		labelPanel.add(scoreLabel, BorderLayout.CENTER);
		gamePanel.add(labelPanel, BorderLayout.SOUTH);
		canvasPanel = new JPanel(new GridLayout(1,4));
		canvasPanel.add(tetrisCanvas);
		canvasPanel.add(preview);
		canvasPanel.add(netCanvas);
		canvasPanel.add(netPreview);
		gamePanel.add(canvasPanel,BorderLayout.CENTER);
        add(gamePanel);
        
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);

		//노트북 <-> PC바뀔때마다 경로바뀌어야함.
		soundManager.setMusic("C:\\Users\\USER\\AppData\\Roaming\\SPB_16.6\\git\\PGP_Tetris\\MyTetris\\src\\Sound\\BGM Tetris Bradinsky.wav");
		soundManager.play();
		soundManager.setVolume(0.85f);

	}
    public void updateScore(int newScore) {
        scoreLabel.setText("Score : " + newScore);
    }

    public void updateLevel() {
        levelLabel.setText("Level : " + (Constant.level-1));
    }
	
	public void createMenu(TetrisCanvas tetrisCanvas, TetrisNetworkCanvas netCanvas) {
		JMenuBar mb = new JMenuBar();
		setJMenuBar(mb);
		JMenu gameMenu = new JMenu("게임");
		mb.add(gameMenu);
		
		JMenuItem startItem = new JMenuItem("시작");
		JMenuItem exitItem = new JMenuItem("종료");
		gameMenu.add(startItem);
		gameMenu.add(exitItem);
		startItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tetrisCanvas.start();
				netCanvas.start();
				soundManager.setMusic("C:\\Users\\gang0\\OneDrive\\바탕 화면\\MyTetris20230922\\src\\Sound\\BGM Tetris_Nintendo music.wav");
				soundManager.play();
				soundManager.setVolume(0.8f);
			}
		});
		
		exitItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tetrisCanvas.stop();
				netCanvas.stop();
			}
		});
		
		JMenu networkMenu = new JMenu("네트워크");
		mb.add(networkMenu);
		
		JMenuItem serverItem = new JMenuItem("서버 생성...");
		networkMenu.add(serverItem);
		JMenuItem clientItem = new JMenuItem("서버 접속...");
		networkMenu.add(clientItem);
		
		serverItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ServerDialog dialog = new ServerDialog();
				dialog.setVisible(true);
				if(dialog.userChoice == ServerDialog.Choice.OK)
				{
					TetrisServer server = new TetrisServer(dialog.getPortNumber());
					server.start();	
					serverItem.setEnabled(false);
				}
			}
		});
		
		clientItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ClientDialog dialog = new ClientDialog();
				dialog.setVisible(true);
				if(dialog.userChoice == ClientDialog.Choice.OK)
				{
					client = new TetrisClient(tetrisCanvas, netCanvas, netPreview ,dialog.getHost(), dialog.getPortNumber());
					client.start();
					clientItem.setEnabled(false);
				}
				
			}
		});
		JMenu soundMenu = new JMenu("볼륨");
		mb.add(soundMenu);
		
		JMenuItem volumeSetting = new JMenuItem("볼륨 설정");
		soundMenu.add(volumeSetting);
		
		JSlider volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, (int)(soundManager.getVolume() * 100));
	    volumeSlider.setMajorTickSpacing(10);
	    volumeSlider.setMinorTickSpacing(1);
	    volumeSlider.setPaintTicks(true);
	    volumeSlider.setPaintLabels(true);

	    volumeSetting.add(volumeSlider);
	    volumeSlider.addChangeListener(new ChangeListener() {
	        @Override
	        public void stateChanged(ChangeEvent e) {
	            int volumeValue = volumeSlider.getValue();
	            float volume = volumeValue / 100.0f;
	            soundManager.setVolume(volume);
	        }
	    });
	}
	public static void main(String[] args) {
		new MyTetris();
	}
	
	public void refresh() {
		if(client != null)
			client.send();
	}
}
