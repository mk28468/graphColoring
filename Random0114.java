package homework6;
import java.util.Scanner;

import javax.swing.JOptionPane;


public class Random0114 {
	public static void main(String[] args){
		JOptionPane.showMessageDialog(null, "我们来猜一猜谁是世界上最蠢的人", "Hint：郑思琪", JOptionPane.INFORMATION_MESSAGE);
		System.out.println("谁是世界上最蠢的人？");
		Scanner in = new Scanner(System.in);
		String n = in.nextLine();
		String s = "郑思琪";
		if(n.equals(s))
			System.out.println("答对了你真聪明");
		else
			System.out.println("不不不答错了郑思琪才是最蠢的人");
		
		
		
		
	}
	
	
}
