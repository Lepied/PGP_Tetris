package tetris;


public class Kl extends Piece{
	public Kl(TetrisData data)
	{
		super(data,6);
		c[0] = -1;   r[0] = 0;
		c[1] = 0;	r[1] = 0;
		c[2] = 0;	r[2] = 1;
		c[3] = 1;	r[3] = 1;
	}
	public int getType() 
	{
		return 6;
	}
	public int roteType()
	{
		return 4;
	}

}
