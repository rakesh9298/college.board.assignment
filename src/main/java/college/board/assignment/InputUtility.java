package college.board.assignment;

import java.util.Scanner;

public class InputUtility {
	
	private Scanner scanner = null;
	
	public InputUtility(){
		scanner = new Scanner(System.in);
	}

	public String nextLine() {
		return scanner.nextLine();
	}
    
}
