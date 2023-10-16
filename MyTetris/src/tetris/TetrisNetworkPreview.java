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
		
		/*
        // 패널의 배경 색상을 업데이트
        int theme = Constant.getCurrentTheme();
        if (theme == 1) {
            setBackground(new Color(80, 80, 80)); // 기본 테마의 배경 색상
        } else if (theme == 2) {
            setBackground(new Color(255, 255, 255)); // 모노크롬 테마의 배경 색상
        }
        */
		
		//쌓인 조각들 그리기
		for(int i = 0; i < 4; i++) {
			for(int k = 0; k < 4; k++) {
				if(data.getAt(i, k) == 0) {
					g.setColor(Constant.basicColor(0,Constant.getCurrentTheme()));
					g.draw3DRect(Constant.margin/2 + Constant.w * k,
							Constant.margin/2 + Constant.w * i, 
							Constant.w, Constant.w, true);
				}
			}
		}
		//System.out.println(current);

		if(nextPiece != null){
			for(int i = 0; i < 4; i++) {
				g.setColor(Constant.basicColor(nextPiece.getType(),Constant.getCurrentTheme()));
				g.fill3DRect(Constant.margin/2 + Constant.w * (1+nextPiece.c[i]), 
						Constant.margin/2 + Constant.w * (1+nextPiece.r[i]), 
						Constant.w, Constant.w, true);
			}
		}
	}
}
