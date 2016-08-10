package college.board.assignment;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public class UserInputProcessTest {

	@Rule
	public ExpectedException thrown= ExpectedException.none();


    @Test
	public void checkNumberWithUserTest(){
		input("higher");
		GuessNumberService guessNumberService = new GuessNumberService(Integer.MIN_VALUE, Integer.MAX_VALUE);			
		assertEquals(UserResponse.HIGHER, guessNumberService.checkNumberWithUser(Mockito.anyLong()));		
	}
	
	
	
	
	private void input(String input) {
		ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);		
	}	
	
}
