import java.awt.Graphics;

import javax.swing.JPanel;

public class TetrisNetworkPreview extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TetrisData data;
	public TetrisNetworkPreview(TetrisData data) {
		this.data = data;
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
		// 현재 내려오고 있는 테트리스 조각 그리
	}
}
