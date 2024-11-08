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

import javax.swing.JColorChooser;

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

public class SoloPlay extends JFrame{

	private static final long serialVersionUID = 1L;
	private SoundManager soundManager = new SoundManager();
	private TetrisData data;
	private TetrisCanvas tetrisCanvas = new TetrisCanvas(this);
	
	private JPanel labelPanel; // 라벨 패널
	private JPanel gamePanel; // 전체 패널
	private JPanel canvasPanel; // 캔버스 패널
	private JLabel scoreLabel;
    private JLabel levelLabel;
    
    public static int width = 1280;
    public static int height = 600;
    public static boolean isWindowOn = false;
    
    
	public SoloPlay() {
		setTitle("테트리스");
		setSize(width, height);
		data = new TetrisData();
		isWindowOn = true;

	    setLocationRelativeTo(null);
		
		labelPanel = new JPanel(new BorderLayout());
		gamePanel = new JPanel(new BorderLayout());

		TetrisPreview preview = new TetrisPreview(tetrisCanvas.getData());
		createMenu(tetrisCanvas);
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
		gamePanel.add(canvasPanel,BorderLayout.CENTER);
        add(gamePanel);
        
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//pack(); 자동으로 컴포넌트들에 맞게 크기수정
		setVisible(true);

		soundManager.setMusic("Sound/BGM Tetris Bradinsky.wav");
		soundManager.play();
		soundManager.setVolume(0.85f);
		
		pack();

	}
	
	public int getWidth()
	{
		return width;
	}
	
	public void setWidth(int newWidth)
	{
		width = newWidth;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void setHeight(int newHeight)
	{
		height = newHeight;
	}
	
    public void updateScore(int newScore) {
        scoreLabel.setText("Score : " + newScore);
    }

    public void updateLevel() {
        levelLabel.setText("Level : " + (Constant.level-1)+"   ");
    }
	
	public void createMenu(TetrisCanvas tetrisCanvas) {
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
				soundManager.setMusic("Sound/BGM Tetris_Nintendo music.wav");
				soundManager.play();
				soundManager.setVolume(0.8f);
				soundManager.setVFXVolume(1.0f);
			}
		});
		
		exitItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tetrisCanvas.stop();
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
	    volumeSlider.addChangeListener(new ChangeListener() { 
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
		        Color selectedColor = JColorChooser.showDialog(SoloPlay.this, "고스트 피스 색 설정", shadowPieceColor);
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
				UserThemeDialog dialog = new UserThemeDialog(SoloPlay.this);
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
		
		JMenu OptionMenu = new JMenu("옵션");
		mb.add(OptionMenu);

        JMenuItem optionItem = new JMenuItem("옵션 화면 열기");
        OptionMenu.add(optionItem);
        optionItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OptionMenu optionMenu = new OptionMenu(soundManager, SoloPlay.this);
                optionMenu.setVisible(true);
            }
        });
	}
    public void changeResolution(int width, int height) {
        setSize(width, height);
        repaint();
    }

	public static void main(String[] args) {

		new SoloPlay();
	}
	
}

