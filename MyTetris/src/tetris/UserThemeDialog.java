package tetris;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import java.io.File;
import java.io.IOException;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JColorChooser;
import java.awt.Color;
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
import javax.swing.JToolBar;
import javax.swing.Timer;
import javax.swing.JDialog;
import javax.swing.JButton;

public class UserThemeDialog extends JDialog {
 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public UserThemeDialog(JFrame parentFrame) {
     super(parentFrame, "사용자 설정 테마", true);
     setSize(800, 200);
     setLayout(new GridLayout(2, 5, 10, 10)); // 2행 5열의 격자 레이아웃
     setLocationRelativeTo(parentFrame);
     
     JButton Teebutton = new JButton("Tee 블럭");
     Teebutton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             Color TeeColor = new Color(0, 255, 0); //녹색
     		 // JColorChooser를 통해 색을 선택합니다.
     		 Color selectedColor = JColorChooser.showDialog(UserThemeDialog.this, "Tee 버튼", TeeColor);
     		 if (selectedColor != null) {
     		     TeeColor = selectedColor;
     		     Constant.userColors[2] = TeeColor;
     		 }
     		 repaint();
         }
     });
     JButton Elbutton = new JButton("El 블럭");
     Elbutton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             Color ElColor = new Color(0, 200, 255); //노란색
     		 // JColorChooser를 통해 색을 선택합니다.
     		 Color selectedColor = JColorChooser.showDialog(UserThemeDialog.this, "El 설정", ElColor);
     		 if (selectedColor != null) {
     			ElColor = selectedColor;
     		     Constant.userColors[3] = ElColor;
     		 }
     		 repaint();
         }
     });
     JButton Erbutton = new JButton("Er 블럭");
     Erbutton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             Color ErColor = new Color(255, 150, 0); //황토색
        		 // JColorChooser를 통해 색을 선택합니다.
        		 Color selectedColor = JColorChooser.showDialog(UserThemeDialog.this, "Er 설정", ErColor);
        		 if (selectedColor != null) {
        			 ErColor = selectedColor;
        		     Constant.userColors[5] = ErColor;
        		 }
        		 repaint();
         }
     });
     JButton Klbutton = new JButton("Kl 블럭");
     Klbutton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             Color KlColor = new Color(210, 0, 240); //보라색
        		 // JColorChooser를 통해 색을 선택합니다.
        		 Color selectedColor = JColorChooser.showDialog(UserThemeDialog.this, "고스트 피스 색 설정", KlColor);
        		 if (selectedColor != null) {
        			 KlColor = selectedColor;
        		     Constant.userColors[6] = KlColor;
        		 }
        		 repaint();
         }
     });
     JButton Krbutton = new JButton("Kr 블럭");
     Krbutton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             Color KrColor =  new Color(40, 0, 240); //파란색
        		 // JColorChooser를 통해 색을 선택합니다.
        		 Color selectedColor = JColorChooser.showDialog(UserThemeDialog.this, "고스트 피스 색 설정", KrColor);
        		 if (selectedColor != null) {
        			 KrColor = selectedColor;
        		     Constant.userColors[7] = KrColor;
        		 }
        		 repaint();
         }
     });
     JButton Ohbutton = new JButton("Oh 블럭");
     Ohbutton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             Color OhColor =  new Color(255, 255, 0); //하늘색
        		 // JColorChooser를 통해 색을 선택합니다.
        		 Color selectedColor = JColorChooser.showDialog(UserThemeDialog.this, "고스트 피스 색 설정", OhColor);
        		 if (selectedColor != null) {
        			 OhColor = selectedColor;
        		     Constant.userColors[4] = OhColor;
        		 }
        		 repaint();
         }
     });
     JButton Barbutton = new JButton("Bar 블럭");
     Barbutton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             Color BarColor = new Color(255, 0, 0); //빨간색
        		 // JColorChooser를 통해 색을 선택합니다.
        		 Color selectedColor = JColorChooser.showDialog(UserThemeDialog.this, "Bar 설정", BarColor);
        		 if (selectedColor != null) {
        			 BarColor = selectedColor;
        		     Constant.userColors[1] = BarColor;
        		 }
        		 repaint();
         }
     });
     JButton Bgbutton = new JButton("배경 색");
     Bgbutton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             Color BgColor =  new Color(240, 240, 240); // 배경색
        		 // JColorChooser를 통해 색을 선택합니다.
        		 Color selectedColor = JColorChooser.showDialog(UserThemeDialog.this, "배경 색 설정", BgColor);
        		 if (selectedColor != null) {
        			 BgColor = selectedColor;
        		     Constant.userColors[0] = BgColor;
        		 }
        		 repaint();
         }
     });
     JButton outlinebutton = new JButton("테두리 색");
     outlinebutton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             Color outlineColor = new Color(80,80,80); //테두리색(검은회색) 
        		 // JColorChooser를 통해 색을 선택합니다.
        		 Color selectedColor = JColorChooser.showDialog(UserThemeDialog.this, "테두리 색 설정", outlineColor);
        		 if (selectedColor != null) {
        			 outlineColor = selectedColor;
        		     Constant.userColors[8] = outlineColor;
        		 }
        		 repaint();
         }
     });
     JButton confirmbutton = new JButton("확인");
     confirmbutton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
        	  dispose();
         }
     });
     
     add(Teebutton);
     add(Elbutton);
     add(Erbutton);
     add(Klbutton);
     add(Krbutton);
     add(Ohbutton);
     add(Barbutton);
     add(Bgbutton);
     add(outlinebutton);
     add(confirmbutton);
     
 }
}