package tetris;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

public class TetrisClient extends Thread {
	private String host;
	private int port;
	private  BufferedReader  i;
    private  PrintWriter     o;
    private TetrisNetworkCanvas netCanvas;
    private TetrisCanvas tetrisCanvas;
    private TetrisNetworkPreview netPreview;
    private String key;
    public TetrisClient(TetrisCanvas tetrisCanvas, TetrisNetworkCanvas netCanvas, TetrisNetworkPreview netPreview, String host, int port) {
    	this.tetrisCanvas = tetrisCanvas;
    	this.netCanvas = netCanvas;
    	this.netPreview = netPreview;
    	this.host = host;
    	this.port = port;
    	
    	UUID uuid = UUID.randomUUID();
		key = uuid.toString()+";";
		System.out.println("My key: "+key);
    }
    
    public void send() {
    	String data = tetrisCanvas.getData().saveNetworkData();
		System.out.println("send: "+data);
		int newBlock = tetrisCanvas.getNewBlock().getType();
		String current = tetrisCanvas.current.extractor();
		o.println(key+data+";"+newBlock+";"+current);
    }
    
	public void run() {
		System.out.println("client start!");
		Socket s;
		try {
			s = new Socket(host, port);
			InputStream ins = s.getInputStream();
			OutputStream os = s.getOutputStream();
			i = new BufferedReader(new InputStreamReader(ins));
			o = new PrintWriter(new OutputStreamWriter(os), true);
			
			while (true) {
				String line = i.readLine();
				if(line.length() != 0)
				{
					String[] parsedData = line.split(";");
					String checkKey = parsedData[0]+";";
					if(!checkKey.equals(key) && parsedData.length >=1) {
						netCanvas.getData().loadNetworkData(parsedData[1]);
						switch(Integer.parseInt(parsedData[2]))
						{
							case 1: //bar
								netPreview.setNextBlock(new Bar(new TetrisData()));
								break;
							case 2: //Tee
								netPreview.setNextBlock(new Tee(new TetrisData()));
								break;
							case 3: //El
								netPreview.setNextBlock(new El(new TetrisData()));
								break;
							case 4: //Oh
								netPreview.setNextBlock(new Oh(new TetrisData()));
								break;
							case 5: //Er
								netPreview.setNextBlock(new Er(new TetrisData()));
								break;
							case 6: //Kl
								netPreview.setNextBlock(new Kl(new TetrisData()));
								break;
							case 7: //Kr
								netPreview.setNextBlock(new Kr(new TetrisData()));
								break;
							default:
								netPreview.setNextBlock(null);
								break;
							}
						Piece tempPiece = new Bar(new TetrisData());
						tempPiece.combinator(parsedData[3]); // <current
						netCanvas.setCurrentPiece(tempPiece);
						}
					
					}
				}
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	
	}
}
