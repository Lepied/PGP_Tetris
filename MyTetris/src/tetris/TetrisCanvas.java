package tetris;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TetrisCanvas extends JPanel implements Runnable, KeyListener, ComponentListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Thread worker;
	protected TetrisData data;
	protected boolean stop, makeNew;
	protected Piece current, nextPiece;
	
	
	//그래픽스 함수를 사용하기 위한 클래스
	private Graphics bufferGraphics = null;
	//bufferGraphics로 그림을 그릴 때 실제로 그려지는 가상 버퍼
	private Image offscreen; 
	//화면의 크기를 저장하는 변수
	private Dimension dim;
	private TetrisPreview preview;
	private MyTetris myTetris;

	
	private Piece holdPiece;
	private Piece TempPiece;
	private Piece shadowPiece;
	
	public int level;
	public int score;
	protected boolean canHold = true;
	
	public TetrisCanvas(MyTetris t) {
		this.myTetris = t;
		data = new TetrisData();
		addKeyListener(this);		
		addComponentListener(this);
	}
	
	public void setTetrisPreview(TetrisPreview preview) {
		this.preview = preview;
	}
	
	//버퍼 초기 함수
	public void initBufferd() {
        dim = getSize();
        System.out.println(dim.getSize());
        //화면의 크기를 가져온다.
        setBackground(Color.white);
        //배경 색깔을 흰색으로 변경한다. 
        offscreen = createImage(dim.width,dim.height);
        //화면 크기와 똑같은 가상 버퍼(이미지)를 생성한다.
        bufferGraphics = offscreen.getGraphics(); 
        //가상버퍼(이미지)로 부터 그래픽스 객체를 얻어옴
	}
	
	public void start() {    // 게임 시작
		data.clear();
		makeNew = true;
		stop = false;
		canHold= true;
		worker = new Thread(this);
		worker.start();
		requestFocus();
		repaint();
		score=0;
		level=1;
	}
	
	public void stop() {
		stop = true;
		current = null;
		nextPiece = null;
		holdPiece = null;
	}
	
	public void paint(Graphics g) {
		super.paint(g);
	     // 패널의 배경 색상을 업데이트
        int theme = Constant.getCurrentTheme();
        if (theme == 1) {
            setBackground(new Color(0, 0, 0)); // 기본 테마의 배경 색상
        } else if (theme == 2) {
            setBackground(new Color(255, 255, 255)); // 모노크롬 테마의 배경 색상
        }

        bufferGraphics.setColor(getBackground());
		//화면을 지운다. 지우지 않으면 이전그림이 그대로 남아 잔상이 생김
		bufferGraphics.clearRect(0,0,dim.width,dim.height); 
		
 
	    // 테트리스 판의 격자 그리기
	    for (int i = 0; i < TetrisData.ROW; i++) {
	        for (int k = 0; k < TetrisData.COL; k++) {
	            bufferGraphics.setColor(Color.GRAY);
	            bufferGraphics.draw3DRect(Constant.margin / 2 + Constant.w * k, 
	                Constant.margin / 2 + Constant.w * i, Constant.w, Constant.w, true);
	        }
	    }
		
		//쌓인 조각들 그리기
		for(int i = 0; i < TetrisData.ROW; i++) {
			for(int k = 0; k < TetrisData.COL; k++) {
				if(data.getAt(i, k) == 0) {
					bufferGraphics.setColor(Constant.basicColor(data.getAt(i, k),Constant.getCurrentTheme()));
					bufferGraphics.draw3DRect(Constant.margin/2 + Constant.w * k,
							Constant.margin/2 + Constant.w * i, Constant.w, Constant.w, true);
				} else {
					bufferGraphics.setColor(Constant.basicColor(data.getAt(i, k),Constant.getCurrentTheme()));
					bufferGraphics.fill3DRect(Constant.margin/2 + Constant.w * k, 
							Constant.margin/2 + Constant.w * i, Constant.w, Constant.w, true);
				}
			}
		}
		//내려오고 있는 블럭이 꽂힐 장소 미리보기
		if(current != null)
		{
			shadowPiece = current.clone();
		    while (!shadowPiece.moveDown()) {
		    }
			for(int i = 0; i < 4; i++) {
				bufferGraphics.setColor(Color.GRAY);
				bufferGraphics.fill3DRect(Constant.margin/2 + Constant.w * (shadowPiece.getX()+shadowPiece.c[i]), 
						Constant.margin/2 + Constant.w * (shadowPiece.getY()+shadowPiece.r[i]), 
						Constant.w, Constant.w, true);
			}
		}
		
		// 현재 내려오고 있는 테트리스 조각 그리
		if(current != null){
			for(int i = 0; i < 4; i++) {
				bufferGraphics.setColor(Constant.basicColor(current.getType(),Constant.getCurrentTheme()));
				bufferGraphics.fill3DRect(Constant.margin/2 + Constant.w * (current.getX()+current.c[i]), 
						Constant.margin/2 + Constant.w * (current.getY()+current.r[i]), 
						Constant.w, Constant.w, true);
			}
		}


		
		
		//가상버퍼(이미지)를 원본 버퍼에 복사
		g.drawImage(offscreen,0,0,this);
		myTetris.refresh();
	}

	public Dimension getPreferredSize(){ // 테트리스 판의 크기 지정
		int tw = Constant.w * TetrisData.COL + Constant.margin;
		int th = Constant.w * TetrisData.ROW + Constant.margin;
		
		return new Dimension(tw, th);
	}
	public void createNextBlock()
	{
		if(nextPiece == null)
		{
			createBlock();
			current = nextPiece;
			nextPiece = null;
			if(nextPiece == null)
			{
				createBlock();
			}
		}
		else
		{
			current = nextPiece;
			createBlock();
		}
		
	}
	private Piece createBlock() {
		int random = (int)(Math.random() * Integer.MAX_VALUE) % 7;
		switch(random){
		case 0:
			nextPiece = new Bar(data);
			break;
		case 1:
			nextPiece = new Tee(data);
			break;
		case 2:
			nextPiece = new El(data);
			break;
		case 3:
			nextPiece = new Oh(data);
			break;
			// 추가로 작성할 내용
		case 4:
			nextPiece = new Er(data);
			break;
			// 추가로 작성할 내용
		case 5:
			nextPiece = new Kl(data);
			break;
			// 추가로 작성할 내용
		case 6:
			nextPiece = new Kr(data);
			break;
		default:
			 createBlock();
			 break;
		}
		return nextPiece;
	}
	public void run(){
		while(!stop) {
			data.removeLines();
			score = data.getLine() * 175 * Constant.level;
			try {
				if(makeNew){ // 새로운 테트리스 조각 만들기
					createNextBlock();
					preview.setCurrentBlock(nextPiece);
					makeNew = false;
					canHold = true;
				} else { // 현재 만들어진 테트리스 조각 아래로 이동
					if(current.moveDown()){
						makeNew = true;
						if(current.copy()){
							stop();
							int  score = data.getLine() * 175 + Constant.level * 500;
							JOptionPane.showMessageDialog(this,"게임끝\n점수 : " + score);
						}
						current = null;
						data.removeLines();
					}
					try {
						Thread.sleep(Constant.interval/Constant.level);
					} catch(Exception e) { }
				}
				score = data.getLine() * 175 * Constant.level;
				System.out.println("현재점수 : "+ score);
				myTetris.updateScore(score);
				
				repaint();
			} catch(Exception e){ }
		}
	}


	
	@Override
	public void keyTyped(KeyEvent e) { }
	
	// 키보드를 이용해서 테트리스 조각 제어
	@Override
	public void keyPressed(KeyEvent e) {
		if(current == null) return;
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT: // 왼쪽 화살표
			current.moveLeft();
			repaint();
			break;
		case KeyEvent.VK_RIGHT:  // 오른쪽 화살표
			current.moveRight();
			repaint();
			break;
		case KeyEvent.VK_UP:  // 윗쪽 화살표
			current.rotate();
			repaint();
			break;
		case KeyEvent.VK_DOWN:  // 아랫쪽 화살표
			boolean temp = current.moveDown();
			if(temp){
				makeNew = true;
				if(current.copy()){
					stop();
					  int  score = data.getLine() * 175 * Constant.level ;
						JOptionPane.showMessageDialog(this,"게임끝\n점수 : " + score);
				}
				current = null;
			}
			data.removeLines();
			score = data.getLine() * 175 * Constant.level;
			worker.interrupt();
			myTetris.updateScore(score);
			repaint();
			break;
		case 32:
			// 블록 하드 드롭 (임시)
		    while (!current.moveDown()) {
		        // 더 이상 아래로 내려갈 수 없을 때까지 블록을 계속해서 내리기
		    }
		    makeNew = true;
		    if (current.copy()) {
		        stop();
		        int  score = data.getLine() * 175 + Constant.level * 500;
				JOptionPane.showMessageDialog(this,"게임끝\n점수 : " + score);
		    }
		    current = null;
		    data.removeLines();
		    score = data.getLine() * 175 * Constant.level;
		    worker.interrupt();
		    myTetris.updateScore(score);
		    repaint();
		    break;
		case 16:
			//블록 저장 //임시
			if(canHold ==true)
			{
				if(holdPiece ==null)
				{
					holdPiece = current;
					preview.setHoldBlock(holdPiece);
					current= null;
					makeNew =true;
					repaint();
				}
				else
				{
					TempPiece = current;
					current = holdPiece;
					current.resetPosition();
					holdPiece = TempPiece;
					preview.setHoldBlock(holdPiece);
					repaint();
				}
				canHold = false;
		
			}
			break;
		}
	}
	
	public TetrisData getData() {
		return data;
	}
	
	public Piece getNewBlock()
	{
		return nextPiece;
	}
	
	@Override
	public void keyReleased(KeyEvent e) { }
	
	@Override
	public void componentResized(ComponentEvent e) {
		System.out.println("resize");
		initBufferd();
	}
	
	@Override
	public void componentMoved(ComponentEvent e) { }
	
	@Override
	public void componentShown(ComponentEvent e) { }
	
	@Override
	public void componentHidden(ComponentEvent e) { }
	
	@Override
	public void update(Graphics g) {
		paint(g);
	}
}
