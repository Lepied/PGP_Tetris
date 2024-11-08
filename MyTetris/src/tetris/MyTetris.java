package tetris;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;

import java.io.File; //

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

public class MyTetris extends JFrame{

	private static final long serialVersionUID = 1L;
	private TetrisClient client = null;
	private SoundManager soundManager = new SoundManager();
	private TetrisData data;
	private TetrisCanvas tetrisCanvas = new TetrisCanvas(this);
	private TetrisNetworkCanvas netCanvas = new TetrisNetworkCanvas();
	private TetrisNetworkPreview netPreview = null;
	
	private JPanel labelPanel; // 라벨 패널
	private JPanel gamePanel; // 전체 패널
	private JPanel canvasPanel; // 캔버스 패널
	private JLabel scoreLabel;
    private JLabel levelLabel;
    

    
	public MyTetris() {
		setTitle("테트리스");
		setSize(1280, 600);
		data = new TetrisData();
		
	    setLocationRelativeTo(null);
	    
		labelPanel = new JPanel(new BorderLayout());
		gamePanel = new JPanel(new BorderLayout());

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

		soundManager.setMusic("Sound/BGM Tetris Bradinsky.wav");
		soundManager.play();
		soundManager.setVolume(0.85f);
		soundManager.setVFXVolume(1.0f);

	}
    public void updateScore(int newScore) {
        scoreLabel.setText("Score : " + newScore);
    }

    public void updateLevel() {
        levelLabel.setText("Level : " + (Constant.level-1) +"   ");
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
				soundManager.setMusic("Sound/BGM Tetris_Nintendo music.wav");
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
		
		JMenuItem pauseMenu = new JMenuItem("BGM 일시정지");
		soundMenu.add(pauseMenu);
		
		
		pauseMenu.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	if(soundManager.clip.isRunning())
		    	{
		    		soundManager.pause();
		    	}
		    	else
		    	{
		    		soundManager.play();
		    	}
		    	
		    }
		});
		
		
		JMenuItem volumeSetting = new JMenuItem("BGM 볼륨 설정");
		soundMenu.add(volumeSetting);
		
		JMenuItem VFXvolumeSetting = new JMenuItem("효과음 볼륨 설정");
		soundMenu.add(VFXvolumeSetting);
		
		JSlider volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, (int)(soundManager.getVolume() * 100));
	    volumeSlider.setMajorTickSpacing(10);
	    volumeSlider.setMinorTickSpacing(1);
	    volumeSlider.setPaintTicks(true);
	    volumeSlider.setPaintLabels(true);
	    
	    JSlider VFXvolumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, (int)(soundManager.getVFXVolume() * 100));
	    volumeSlider.setMajorTickSpacing(10);
	    volumeSlider.setMinorTickSpacing(1);
	    volumeSlider.setPaintTicks(true);
	    volumeSlider.setPaintLabels(true);

	    volumeSetting.add(volumeSlider);
	    volumeSlider.addChangeListener(new ChangeListener() { //현재 슬라이더를 통해 볼륨을 조절할 수 있으나, 슬라이더의 모습과 실제 볼륨간에 괴리감이 있음. <-기능상 문제 x
	        @Override
	        public void stateChanged(ChangeEvent e) {
	            int volumeValue = volumeSlider.getValue();
	            float volume = volumeValue / 100.0f;
	            System.out.println("볼륨 : "+volume);
	            soundManager.setVolume(volume);
	        }
	    });
	    
	    VFXvolumeSetting.add(VFXvolumeSlider);
	    VFXvolumeSlider.addChangeListener(new ChangeListener() { 
	        @Override
	        public void stateChanged(ChangeEvent e) {
	            int volumeValue = VFXvolumeSlider.getValue();
	            float volume = volumeValue / 100.0f;
	            System.out.println("볼륨 : "+volume);
	            soundManager.setVFXVolume(volume);
	        }
	    });
	    
		JMenu themeMenu = new JMenu("테마");
		mb.add(themeMenu);
		
		JMenuItem basicThemeItem = new JMenuItem("기본 테마");
		themeMenu.add(basicThemeItem);
		JMenuItem monochromeThemeItem = new JMenuItem("모노크롬 테마");
		themeMenu.add(monochromeThemeItem);
		JMenuItem usersThemeItem = new JMenuItem("사용자 설정 테마");
		themeMenu.add(usersThemeItem);
		JMenuItem resetThemeItem = new JMenuItem("테마 초기화");
		themeMenu.add(resetThemeItem);
		
		JMenuItem chooseColorItem = new JMenuItem("고스트피스");
		themeMenu.add(chooseColorItem);
		
		chooseColorItem.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        Color shadowPieceColor = Color.GRAY;
				// JColorChooser를 통해 색을 선택합니다.
		        Color selectedColor = JColorChooser.showDialog(MyTetris.this, "고스트 피스 색 설정", shadowPieceColor);
		        if (selectedColor != null) {
		            shadowPieceColor = selectedColor;
		            tetrisCanvas.setShadowPieceColor(selectedColor);
		            repaint();
		        }
		    }
		});
		
		
		basicThemeItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Constant.setTheme(1);
				repaint();
			}
		});
		
		
		
		monochromeThemeItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Constant.setTheme(2);
				repaint();
			}
		});
		
		usersThemeItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Constant.setTheme(3);
				UserThemeDialog dialog = new UserThemeDialog(MyTetris.this);
			    dialog.setVisible(true);
				repaint();
			}
		});
		resetThemeItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Constant.setTheme(9999);
				repaint();
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
