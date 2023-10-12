package tetris;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {
	public Clip clip;
	private FloatControl volumeControl;
	
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
	
	//사용할 음악파일 변경
	public void setMusic(String filePath)
	{
		try {
			if(clip != null)
			{
				clip.close();
			}
			
			AudioInputStream audioInputStream = 
					AudioSystem.getAudioInputStream(new File(filePath));
            clip = AudioSystem.getClip(); // 새로운 클립을 생성
            clip.open(audioInputStream);
            volumeControl = (FloatControl) clip.getControl
            		(FloatControl.Type.MASTER_GAIN); // 볼륨 컨트롤을 업데이트합니다.
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
	
	public static void main(String[] args) {
		SoundManager soundManager = new SoundManager();
		soundManager.play();
		soundManager.setVolume(0.5f);
		soundManager.stop();
	}
}
