package tetris;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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

public class OptionMenu extends JFrame  {
	 private SoundManager soundManager;
	 private JComboBox<String> resolutionComboBox; // 드롭다운 목록
	 
	 public OptionMenu() {
	        setTitle("옵션 메뉴");
	        setSize(300, 400);
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창을 닫을 때만 창을 숨김

	        SoundManager soundManager = new SoundManager();
	       
	        JPanel optionsPanel = new JPanel(new GridBagLayout());
	        getContentPane().add(optionsPanel, BorderLayout.CENTER);

	        GridBagConstraints constraints = new GridBagConstraints();
	        constraints.insets = new Insets(5, 5, 5, 5);

	        // 볼륨 슬라이더 추가
	        JLabel volumeLabel = new JLabel("- 볼륨 -");
	        constraints.gridx = 0;
	        constraints.gridy = 0;
	        optionsPanel.add(volumeLabel, constraints);
	        
	        JSlider volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, (int)(soundManager.getVolume() * 100));
		    volumeSlider.setMajorTickSpacing(10);
		    volumeSlider.setMinorTickSpacing(1);
		    volumeSlider.setPaintTicks(true);
		    volumeSlider.setPaintLabels(true);
		    constraints.gridy = 1;
		    optionsPanel.add(volumeSlider, constraints);
		    
		    
		    volumeSlider.addChangeListener(new ChangeListener() { 
		        @Override
		        public void stateChanged(ChangeEvent e) {
		            int volumeValue = volumeSlider.getValue();
		            float volume = volumeValue / 100.0f;
		            System.out.println("볼륨 : "+volume);
		            soundManager.setVolume(volume);
		        }
		    });
		    
		    //창 크기 드롭다운 목록 추가
	        JLabel resolutionLabel = new JLabel("- 창 크기 -");
	        constraints.gridy = 2;
	        optionsPanel.add(resolutionLabel, constraints);
	        
	        String[] resolutions = {"800x600", "1024x768", "1280x720", "1920x1080"}; // 정해진 해상도 목록
	        resolutionComboBox = new JComboBox<>(resolutions);
	        /*
	        // 드롭다운 목록의 선택 이벤트 처리
	        resolutionComboBox.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                String selectedResolution = (String) resolutionComboBox.getSelectedItem();
	                if (selectedResolution.equals("800x600")) {
	                    soloPlay.setSize(800, 600);
	                } else if (selectedResolution.equals("1024x768")) {
	                    soloPlay.setSize(1024, 768);
	                } else if (selectedResolution.equals("1280x720")) {
	                    soloPlay.setSize(1280, 720);
	                } else if (selectedResolution.equals("1920x1080")) {
	                    soloPlay.setSize(1920, 1080);
	                }
	            }
	        });
	        */
	        constraints.gridy = 3;
	        optionsPanel.add(resolutionComboBox, constraints);
	        

	        JButton saveButton = new JButton("저장");
	        saveButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // 설정을 저장하고 창을 닫음
	                // 설정을 저장하는 코드를 여기에 추가
	                dispose(); // 창을 닫음
	            }
	        });
	        
	        constraints.gridy = 4;
	        optionsPanel.add(saveButton, constraints);
	        

	        setLocationRelativeTo(null);
	        setVisible(true);
	  }
}

