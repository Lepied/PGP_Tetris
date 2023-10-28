package tetris;
import java.awt.Point;
import java.util.Arrays;

public abstract class Piece {
	final int DOWN = 0;  // 방향 지정
	final int LEFT = 1;
	final int RIGHT = 2;
	protected int r[];   // Y축 좌표 배열
	protected int c[];   // X축 좌표 배열
	protected TetrisData data;  // 테트리스 내부 데이터
	protected Point center; // 조각의 중심 좌표
	protected int type; // 조각들의 색깔 타입
	protected int rotatedCount;
	
	public Piece(TetrisData data, int type) {
		r = new int[4];
		c = new int[4];
		this.data = data;
		this.type = type;
		center = new Point(5,0);
		rotatedCount = 0;
	}
	
	public abstract int getType();
	public abstract int roteType();
 
	public int getX() { return center.x; }
	public int getY() { return center.y; }
	
	public boolean copy(){  // 값 복사
		boolean value = false;
		int x = getX();
		int y = getY();
		if(getMinY() + y <= 0) 
		{ // 게임 종료 상황
			value = true;
		}
 
		for(int i=0; i < 4; i++)
		{
			data.setAt(y + r[i], x + c[i], getType());
		}
		return value;
	}
	
	public void resetPosition()
	{
		if(center.y + getMaxY()+1 < TetrisData.ROW)
		{
			center.y = data.data[0][4];
			center.x = 4;
		}
	}
 
	public boolean isOverlap(int dir){ // 다른 조각과 겹치는지 파악
		int x = getX();
		int y = getY();
		switch(dir) {
		case 0 : // 아래
			for(int i=0; i < r.length; i++)
			{
				if(data.getAt(y+r[i]+1, x+c[i]) != 0) 
				{
					return true;
				}
			}
			break;
		case 1 : // 왼쪽
			for(int i=0; i < r.length; i++) 
			{
				if(data.getAt(y+r[i], x+c[i]-1) != 0)
				{
					return true;
				}
			}
			break;
		case 2 : // 오른쪽
			for(int i=0; i < r.length; i++)
			{
				if(data.getAt(y+r[i], x+c[i] + 1) != 0) 
				{
					return true;
				}
			}
			break;
		}
		return false;
	}
 
	public int getMinX() 
	{
		int min = c[0];
		for(int i=1; i < c.length; i++) {
			if(c[i] < min) {
				min = c[i];
			}
		}
		return min;
	}
 
	public int getMaxX() 
	{
		int max = c[0];
		for(int i=1; i < c.length; i++)
		{
			if(c[i] > max) {
				max = c[i];
			}
		}
		return max;
	}
 
	public int getMinY() 
	{
		int min = r[0];
		for(int i=1; i < r.length; i++)
		{
			if(r[i] < min) {
				min = r[i];
			}
		}
		return min;
	}
 
	public int getMaxY() 
	{
		int max = r[0];
		for(int i=1; i < r.length; i++) 
		{
			if(r[i] > max) {
				max = r[i];
			}
		}
		return max;
	}
	public String extractor()
	{
		String str =
		r[0] + "." + r[1] + "." + r[2] + "." + r[3] + "." + //0~3 -> r
		c[0] + "." + c[1] + "." + c[2] + "." + c[3] + "." + //4~7 -> c
		center.x + "." + center.y + "." + type;							//8,9 -> center.x,y
		return str;
	}
	public void combinator(String str)
	{
		String[] resultStr=
				str.split("\\.");
		for(int i = 0; i < 11; i++)
		{
			if(i<4)
			{
				r[i] = Integer.parseInt(resultStr[i]);
			}
			else if(i<8)
			{
				c[i%4] = Integer.parseInt(resultStr[i]);
			}
			else if(i==8)
			{
				center.x = Integer.parseInt(resultStr[i]);
			}
			else if(i==9)
			{
				center.y = Integer.parseInt(resultStr[i]);
			}
			else
			{
				type = Integer.parseInt(resultStr[i]);
			}
		}
	}
	public Piece clone() {
		Piece clonedPiece = null;
		switch(type)
		{
			case 1:
				clonedPiece = new Bar(data);
				break;
			case 2:
				clonedPiece = new Tee(data);
				break;
			case 3:
				clonedPiece = new El(data);
				break;
			case 4:
				clonedPiece  = new Oh(data);
				break;
				// 추가로 작성할 내용
			case 5:
				clonedPiece  = new Er(data);
				break;
				// 추가로 작성할 내용
			case 6:
				clonedPiece  = new Kl(data);
				break;
				// 추가로 작성할 내용
			case 7:
				clonedPiece  = new Kr(data);
				break;
			default:
				 break;
			}
	    // 다음으로 복제된 블록의 상태를 현재 블록의 상태로 복사합니다.
	    clonedPiece.center = new Point(center);
	    clonedPiece.type = type;
	    clonedPiece.r = Arrays.copyOf(r, r.length);
	    clonedPiece.c = Arrays.copyOf(c, c.length);

	    return clonedPiece;
	}

	public boolean moveDown() { // 아래로 이동
		if(center.y + getMaxY() + 1 < TetrisData.ROW) 
		{
			if(isOverlap(DOWN) != true) 
			{
				center.y++;
			} else {
				return true;
			}
		} 
		else { return true; }

		return false;
	}

	public void moveLeft() {  // 왼쪽으로 이동
		if(center.x + getMinX() -1 >= 0)
			if(isOverlap(LEFT) != true) {center.x--;}
			else return;
	}

	public void moveRight() {  // 오른쪽으로 이동
		if(center.x + getMaxX() + 1 < TetrisData.COL)
			if(isOverlap(RIGHT) != true) {center.x++;}
			else return;
	}
	
	public boolean isHorizontal() { //Bar블럭 전용
	    int minX = getMinX();
	    int maxX = getMaxX();
	    int minY = getMinY();
	    int maxY = getMaxY();
	    
	    // Bar 블록이 수평 상태인지 확인
	    return (maxX - minX) == 3 && (maxY - minY) == 0;
	}

	public boolean isVertical() { //Bar블럭 전용
	    int minX = getMinX();
	    int maxX = getMaxX();
	    int minY = getMinY();
	    int maxY = getMaxY();
	    
	    // Bar 블록이 수직 상태인지 확인
	    return (maxX - minX) == 0 && (maxY - minY) == 3;
	}

	public void rotate() {  // 조각 회전
		int rc = roteType();
		if(rc <= 1) return;
		if(rc == 2) {
			rotate4();
			rotate4();
			rotate4();
		} else {
			rotate4();
		}
		rotatedCount++;
		if(rotatedCount==4)
		{
			rotatedCount = 0;
		}
		//rotationCount = (rotationCount + 1) % 4; -> 0,1,2,3
	}
	
	public int getRotatedCount()
	{
		return rotatedCount;
	}

	public void rotate4() // 조각 회전
	{
		int[] newC = new int[4];
	    int[] newR = new int[4];
	    
		for(int i =0; i<4; i++)
		{
		     int temp = c[i];
		     newC[i] = -r[i];
		     newR[i] = temp;
		}
		for(int i=0 ; i<4; i++)
		{
			int newX = getX() + newC[i];
			int newY = getY() + newR[i];
			
			//회전한 블록이 공간 밖으로 나가거나 다른 블럭과 겹치는경우, 블록이 회전하지 않게한다.
			if(newX<0 || newX>= TetrisData.COL ||  
			   newY < 0 || newY >= TetrisData.ROW ||
			   data.getAt(newY, newX) != 0)
			{
				if(newX<0)
				{
					moveRight();
					for(int j=0;j<4; j++)
					{
						c[j] = newC[j];
						r[j] = newR[j];
					}
					return;
				}
				if(newX>= TetrisData.COL)
				{
					moveLeft();
					for(int j=0;j<4; j++)
					{
						c[j] = newC[j];
						r[j] = newR[j];
					}
					return;
				}
				return;
			}
		}
		for(int i=0;i<4; i++)
		{
			c[i] = newC[i];
			r[i] = newR[i];
		}
	}
}
