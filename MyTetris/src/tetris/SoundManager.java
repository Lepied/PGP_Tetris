package tetris;


import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.*;

public class SoundManager {
	public Clip clip;
	private FloatControl volumeControl;
	private FloatControl vfxControl;
	
	public SoundManager()
	{

	}
	
	public void play()
	{
		if(clip != null) {
			clip.start();
		}
	}
	public void stop()
	{
		if(clip !=null)
		{
			clip.stop();
			clip.setFramePosition(0); // 재생 위치를 처음으로
		}
	}
	public void pause()
	{
	    if (clip != null && clip.isRunning()) 
	    {
	        clip.stop();
	    }
	}

	public void setVolume(float volume) {
		//볼륨조절 (0.0f ~1.0f)
	    if (volumeControl != null && clip != null) {
	        if (volume < 0.0f) {
	            volume = 0.0f;
	        } else if (volume > 1.0f) {
	            volume = 1.0f;
	        }
	        float minVolume = volumeControl.getMinimum();
	        float maxVolume = volumeControl.getMaximum();
	        float adjustedVolume = minVolume + (maxVolume - minVolume) * volume;
	        volumeControl.setValue(adjustedVolume);
	    }
	}
	
	public void setVFXVolume(float volume) {
		//볼륨조절 (0.0f ~1.0f)
	    if (vfxControl != null && clip != null) {
	        if (volume < 0.0f) {
	            volume = 0.0f;
	        } else if (volume > 1.0f) {
	            volume = 1.0f;
	        }
	        float minVolume = vfxControl.getMinimum();
	        float maxVolume = vfxControl.getMaximum();
	        float adjustedVolume = minVolume + (maxVolume - minVolume) * volume;
	        vfxControl.setValue(adjustedVolume);
	    }
	}
	
	//사용할 음악파일 변경
	public void setMusic(String filePath)
	{
		try {
			if(clip != null)
			{
				clip.stop();
				clip.close();
			}
			
			AudioInputStream audioInputStream = 
					AudioSystem.getAudioInputStream(new File(filePath));
            clip = AudioSystem.getClip(); // 새로운 클립을 생성
            clip.open(audioInputStream);
            volumeControl = (FloatControl) clip.getControl
            		(FloatControl.Type.MASTER_GAIN); // 볼륨 컨트롤을 업데이트합니다.
            
            if (volumeControl != null)   //볼륨 기초값 설정
            {
                float minVolume = volumeControl.getMinimum();
                float maxVolume = volumeControl.getMaximum();
                float midVolume = (maxVolume - minVolume) / 2.0f + minVolume;
                volumeControl.setValue(midVolume);
            }
            clip.loop(Clip.LOOP_CONTINUOUSLY); // 반복 재생
            
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
	}
	
		public void setEffectSound(String filePath)
		{
			try {
				
				AudioInputStream audioInputStream = 
						AudioSystem.getAudioInputStream(new File(filePath));
	            clip = AudioSystem.getClip(); // 새로운 클립을 생성
	            clip.open(audioInputStream);
	            vfxControl = (FloatControl) clip.getControl
	            		(FloatControl.Type.MASTER_GAIN); // 볼륨 컨트롤을 업데이트합니다.
	            
	            if (vfxControl != null) { //볼륨 기초값 설정
	                float minVolume = vfxControl.getMinimum();
	                float maxVolume = vfxControl.getMaximum();
	                float midVolume = (maxVolume - minVolume) / 2.0f + minVolume;
	                vfxControl.setValue(midVolume);
	            }
	                
	        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
	            e.printStackTrace();
	        }
		}
	
    public float getVolume() {
        if (volumeControl != null && clip != null) {
            float minVolume = volumeControl.getMinimum();
            float maxVolume = volumeControl.getMaximum();
            float currentVolume = volumeControl.getValue();
            // 현재 볼륨 값을 0.0f ~ 1.0f 범위로 정규화하여 반환
            return (currentVolume - minVolume) / (maxVolume - minVolume);
        } else {
            return 0.0f;
        }
    }
    
    public float getVFXVolume() {
        if (vfxControl != null && clip != null) {
            float minVolume = vfxControl.getMinimum();
            float maxVolume = vfxControl.getMaximum();
            float currentVolume = vfxControl.getValue();
            // 현재 볼륨 값을 0.0f ~ 1.0f 범위로 정규화하여 반환
            return (currentVolume - minVolume) / (maxVolume - minVolume);
        } else {
            return 0.0f;
        }
    }
	
	public static void main(String[] args) {
		SoundManager soundManager = new SoundManager();
		soundManager.play();
		soundManager.getVolume();
		soundManager.setVolume(0.5f);
		soundManager.stop();
	}
}
