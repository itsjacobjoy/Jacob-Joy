import javax.swing.JFrame;

public class GameFrame extends JFrame{
	
	GameFrame(){
		
		this.add(new GamePanel());
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack(); //It takes the JFRAME and fit into the components in the frame
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
	}
}
