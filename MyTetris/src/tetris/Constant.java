package tetris;
import java.awt.Color;

public class Constant {
	public static final int w = 25;
	public static final int margin = 20;
	public static final int     interval = 2000;
	public static int     	level = 2;
	private static Color[] colors = null;  // 테트리스 배경 및 조각 색
	private static int currentTheme = 1;
	
	public static Color basicColor(int index, int theme) {
		switch(theme)
		{
			case 1: // 기본 테마
				if(colors == null) {
					colors = new Color[9];
					colors[0] = new Color(240, 240, 240); // 배경색
					colors[1] = new Color(255, 0, 0); //빨간색
					colors[2] = new Color(0, 255, 0); //녹색
					colors[3] = new Color(0, 200, 255); //노란색
					colors[4] = new Color(255, 255, 0); //하늘색
					colors[5] = new Color(255, 150, 0); //황토색
					colors[6] = new Color(210, 0, 240); //보라색
					colors[7] = new Color(40, 0, 240); //파란색
					colors[8] = new Color(80,80,80); //테두리색(검은회색) 
				}
				return colors[index];
				
			case 2: // 흑백 테마
					if(colors == null) {
						colors = new Color[9];
						colors[0] = new Color(55, 55, 55); // 배경색(검정색)
						colors[1] = new Color(255, 255, 255); //흰색
						colors[2] = new Color(255, 255, 255); //흰색
						colors[3] = new Color(255, 255, 255); //흰색
						colors[4] = new Color(255, 255, 255); //흰색
						colors[5] = new Color(255, 255, 255); //흰색
						colors[6] = new Color(255, 255, 255); //흰색
						colors[7] = new Color(255, 255, 255); //흰색
						colors[8] = new Color(240, 240, 240); //테두리색(흰색) 
					}
					return colors[index];
			default: // 기본 테마
				if(colors == null) {
					colors = new Color[9];
					colors[0] = new Color(80, 80, 80); // 배경색(검은회색)
					colors[1] = new Color(255, 0, 0); //빨간색
					colors[2] = new Color(0, 255, 0); //녹색
					colors[3] = new Color(0, 200, 255); //노란색
					colors[4] = new Color(255, 255, 0); //하늘색
					colors[5] = new Color(255, 150, 0); //황토색
					colors[6] = new Color(210, 0, 240); //보라색
					colors[7] = new Color(40, 0, 240); //파란색
					colors[8] = new Color(80,80,80); //테두리색(검은회색) 
					
				}
				return colors[index];
				
		}
		

	}
	public static void setTheme(int theme)
	{
		currentTheme = theme;
		colors = null;
	}
	public static int getCurrentTheme()
	{
		return currentTheme;
	}

	
}
