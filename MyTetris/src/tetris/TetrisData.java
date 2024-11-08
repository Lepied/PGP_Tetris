package tetris;

import javax.sound.sampled.AudioFormat; 
import javax.sound.sampled.AudioInputStream; //
import javax.sound.sampled.AudioSystem; //
import javax.sound.sampled.Clip; //
import javax.sound.sampled.DataLine; //
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class TetrisData {
	public static final int ROW = 20;
	public static final int COL = 10;
	public int data[][];  // ROW x COL 의 배열
	private int line;      // 지운 줄 수
	private int score;
	private int lineRiser; // 레벨 비례로 지워야하는 줄수 증가 시키기 
	public boolean levelUP = false;
	
	private SoundManager soundManager = new SoundManager();

	public TetrisData() {
		data = new int[ROW][COL];
		lineRiser = 5;
		clear();
	}
	
	public int getAt(int x, int y) {
		if(x <0 || x >= ROW || y < 0 || y >= COL)
			return 0;
		return data[x][y];
	}
	
	public void setAt(int x, int y, int v) {
		data[x][y] = v;
	}
	
	public int getLine() {
		return line;
	}
	public int getScore()
	{
		return score;
	}
	
	public void setScore(int num)
	{
		score = score + num;
	}
	
	public synchronized void removeLines() {
		
		NEXT:
		for(int i = ROW-1; i >= 0; i--){
			boolean done = true;
			for(int k = 0; k < COL; k++){
				if(data[i][k] == 0) {
					done = false;
					continue NEXT;
				}
			}
			if(done){
				soundManager.setEffectSound("Sound/lineSound.wav");
				soundManager.setVolume(1f);
	            soundManager.play();
				line++;
				for(int x = i; x > 0; x--) {
					for(int y = 0; y < COL; y++){
						data[x][y] = data[x-1][y];
					}
				}
				if(i != 0){
					for(int y = 0; y < COL; y++){
						data[0][y] = 0;
					}
				}
				i++;
				levelSetting();
				
			}
		}
	}
	
	public void clear() {   // data 배열 초기화
		for(int i=0; i < ROW; i++){
			for(int k = 0; k < COL; k++){
				data[i][k] = 0;
			}
		}
	}
	
	public void dump() {   // data 배열 내용 출력
		for(int i=0; i < ROW; i++) {
			for(int k = 0; k < COL; k++) {
				System.out.print(data[i][k] + " ");
			}
			System.out.println();
		}
	}
	public synchronized void levelSetting()
	{
		levelUP = true;
		if(line>lineRiser)
		{
			
			Constant.level = Constant.level+1;
			lineRiser = lineRiser + 10;
		}

	}
	
	public void loadNetworkData(String input) {
		clear();
		String[] dataArray = input.split(",");
		int count = 0;
		for(int i = 0; i < TetrisData.ROW; i++) {
			for(int k = 0; k < TetrisData.COL; k++) {
				setAt(i, k, Integer.parseInt(dataArray[count]));
				count++;
			}
		}
	}
	
	public String saveNetworkData() {
		StringBuilder output = new StringBuilder("");
		for(int i = 0; i < TetrisData.ROW; i++) {
			for(int k = 0; k < TetrisData.COL; k++) {
				String data = String.valueOf(getAt(i, k));
				output.append(data+",");
			}
		}
		String result = output.toString();
		return result;
	}
}
