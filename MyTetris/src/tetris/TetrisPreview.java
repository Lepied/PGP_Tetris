package tetris;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


public class TetrisPreview extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TetrisData data;
	private Piece   current = null;
	private Piece nextPiece = null;
	private Piece holdPiece = null;
	
	public TetrisPreview(TetrisData data) {
		this.data = data;
		repaint();
	}
	
	public void setCurrentBlock(Piece current) {
		this.current = current;
		//System.out.println(current);
		repaint();
	}
	public void setNextBlock(Piece nextPiece)
	{
		this.nextPiece = nextPiece;
		repaint();
	}
	public void setHoldBlock(Piece holdPiece)
	{
		this.holdPiece = holdPiece;
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		//미리보기 공간 그리기
		for(int i = 0; i < 4; i++) {
			for(int k = 0; k < 4; k++) {
				if(data.getAt(i, k) == 0) {
					g.setColor(Constant.getColor(0));
					g.draw3DRect(Constant.margin/2+10 + Constant.w * k,
							Constant.margin/2+20 + Constant.w * i, 
							Constant.w, Constant.w, true);
				}
			}
		}
		//홀드 피스 공간 그리기
		for(int i = 0; i < 4; i++) {
			for(int k = 0; k < 4; k++) {
				if(data.getAt(i, k) == 0) {
					g.setColor(Constant.getColor(0));
					g.draw3DRect(Constant.margin/2+10 + Constant.w * k,
							Constant.margin/2+200 + Constant.w * i, 
							Constant.w, Constant.w, true);
				}
			}
		}
		
		
		if(current != null){
			for(int i = 0; i < 4; i++) {
				g.setColor(Constant.getColor(current.getType()));
				g.fill3DRect(Constant.margin/2+10 + Constant.w * (1+current.c[i]), 
						Constant.margin/2+20 + Constant.w * (1+current.r[i]), 
						Constant.w, Constant.w, true);
			}
		}
		
		if(holdPiece !=null)  //저장된 테트리스 조각 그리기
		{
			for(int i =0; i<4; i++)
			{
				g.setColor(Constant.getColor(holdPiece.getType()));
				g.fill3DRect(Constant.margin/2+10 + Constant.w*(1+holdPiece.c[i]), Constant.margin/2+200 + Constant.w*(1+holdPiece.r[i])
						, Constant.w, Constant.w, true);
				
			}
		}
		//텍스트 그리기
		g.setColor(Color.BLACK); // 텍스트 색상 설정
	    g.drawString("미리보기피스", 35, 20); // 미리보기피스 텍스트 그리기
	    g.drawString("홀드피스", 45, 200); // 홀드피스 텍스트 그리기
		
	}
}
