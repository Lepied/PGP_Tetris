package tetris;
import java.awt.Graphics;

import javax.swing.JPanel;

public class TetrisNetworkPreview extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TetrisData data;
	private Piece nextPiece = null;
	
	
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
		
		//쌓인 조각들 그리기
		for(int i = 0; i < 4; i++) {
			for(int k = 0; k < 4; k++) {
				if(data.getAt(i, k) == 0) {
					g.setColor(Constant.getColor(0));
					g.draw3DRect(Constant.margin/2 + Constant.w * k,
							Constant.margin/2 + Constant.w * i, 
							Constant.w, Constant.w, true);
				}
			}
		}
		//System.out.println(current);

		if(nextPiece != null){
			for(int i = 0; i < 4; i++) {
				g.setColor(Constant.getColor(nextPiece.getType()));
				g.fill3DRect(Constant.margin/2 + Constant.w * (1+nextPiece.c[i]), 
						Constant.margin/2 + Constant.w * (1+nextPiece.r[i]), 
						Constant.w, Constant.w, true);
			}
		}
	}
}
