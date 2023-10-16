package tetris;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class TetrisNetworkPreview extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TetrisData data;
	private Piece nextPiece = null;
	private Piece holdPiece = null;
	
	
	public TetrisNetworkPreview(TetrisData data) {
		this.data = data;
		repaint();
	}
	
	public void setNextBlock(Piece nextPiece) {
		this.nextPiece = nextPiece;
		repaint();
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);

		
		for(int i = 0; i < 4; i++) {
			for(int k = 0; k < 4; k++) {
				if(data.getAt(i, k) == 0) {
					g.setColor(Color.gray);
					g.draw3DRect(Constant.margin/2+10 + Constant.w * k,
							Constant.margin/2+20 + Constant.w * i, 
							Constant.w, Constant.w, true);
				}
			}
		}



		if(nextPiece != null){
			for(int i = 0; i < 4; i++) {
				g.setColor(Constant.basicColor(nextPiece.getType(),Constant.getCurrentTheme()));
				g.fill3DRect(Constant.margin/2+10 + Constant.w * (1+nextPiece.c[i]), 
						Constant.margin/2+10 + Constant.w * (1+nextPiece.r[i]), 
						Constant.w, Constant.w, true);
			}
		}
		//텍스트 그리기
		g.setColor(Color.BLACK); // 텍스트 색상 설정
	    g.drawString("미리보기피스", 35, 20); // 미리보기피스 텍스트 그리기
	}
}
