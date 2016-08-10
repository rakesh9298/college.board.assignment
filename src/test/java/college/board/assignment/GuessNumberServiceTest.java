package college.board.assignment;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;


public class GuessNumberServiceTest {
	private ByteArrayOutputStream out = new ByteArrayOutputStream();

	
	@Rule
	public ExpectedException thrown= ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		System.setOut(new PrintStream(out));
	}

	@Test(expected = InvalidNumberGuessedException.class)
	public void testGuessNumberMin() throws IOException, InvalidNumberGuessedException {		
		GuessNumberService guessNumberService = new GuessNumberService(Integer.MIN_VALUE, Integer.MAX_VALUE);
		GuessNumberService mock = Mockito.spy(guessNumberService);
		Mockito.doReturn(UserResponse.LOWER).when(mock).checkNumberWithUser(Integer.MIN_VALUE);	
		Mockito.doReturn(UserResponse.YES).when(mock).checkNumberWithUser(Integer.MAX_VALUE);	
		mock.guessNumber();		
		thrown.expect(InvalidNumberGuessedException.class);
		thrown.expectMessage(Constants.ERR_MIN_SUPPORTED);
	}
	
	@Test(expected = InvalidNumberGuessedException.class)
	public void testGuessNumberMax() throws IOException, InvalidNumberGuessedException {		
		GuessNumberService guessNumberService = new GuessNumberService(Integer.MIN_VALUE, Integer.MAX_VALUE);
		GuessNumberService mock = Mockito.spy(guessNumberService);		
		Mockito.doReturn(UserResponse.HIGHER).when(mock).checkNumberWithUser(Integer.MIN_VALUE);	
		Mockito.doReturn(UserResponse.HIGHER).when(mock).checkNumberWithUser(Integer.MAX_VALUE);
		mock.guessNumber();		
		thrown.expect(InvalidNumberGuessedException.class);
		thrown.expectMessage(Constants.ERR_MAX_SUPPORTED);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGuessNumberWrongAnswer() throws IOException, InvalidNumberGuessedException {	
		//Guessing 7.
		GuessNumberService guessNumberService = new GuessNumberService(0, 21);
		GuessNumberService mock = Mockito.spy(guessNumberService);		
		Mockito.doReturn(UserResponse.HIGHER).when(mock).checkNumberWithUser(0);	
		Mockito.doReturn(UserResponse.LOWER).when(mock).checkNumberWithUser(21);
		Mockito.doReturn(UserResponse.LOWER).when(mock).checkNumberWithUser(10);
		Mockito.doReturn(UserResponse.HIGHER).when(mock).checkNumberWithUser(5);
		Mockito.doReturn(UserResponse.LOWER).when(mock).checkNumberWithUser(8);
		Mockito.doReturn(UserResponse.HIGHER).when(mock).checkNumberWithUser(7); //Wrong answer. 7 cannot be greater than 7		
		mock.guessNumber();		
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(Constants.ERROR_INVALID_RESPONSE_IN_STAGE);
		
		//Guessing 6
		guessNumberService = new GuessNumberService(0, 21);
		mock = Mockito.spy(guessNumberService);		
		Mockito.doReturn(UserResponse.HIGHER).when(mock).checkNumberWithUser(0);	
		Mockito.doReturn(UserResponse.LOWER).when(mock).checkNumberWithUser(21);
		Mockito.doReturn(UserResponse.LOWER).when(mock).checkNumberWithUser(10);
		Mockito.doReturn(UserResponse.HIGHER).when(mock).checkNumberWithUser(5);
		Mockito.doReturn(UserResponse.LOWER).when(mock).checkNumberWithUser(8);
		Mockito.doReturn(UserResponse.LOWER).when(mock).checkNumberWithUser(7);
		Mockito.doReturn(UserResponse.LOWER).when(mock).checkNumberWithUser(6);//Wrong answer. 6 cannot be less than 6		
		mock.guessNumber();		
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(Constants.ERROR_INVALID_RESPONSE_IN_STAGE);
	}
	
	@Test
	public void testGuessNumberEven() throws IOException, InvalidNumberGuessedException {	
		//Guessing 48.
		GuessNumberService guessNumberService = new GuessNumberService(0, 50);
		GuessNumberService mock = Mockito.spy(guessNumberService);		
		Mockito.doReturn(UserResponse.HIGHER).when(mock).checkNumberWithUser(0);	
		Mockito.doReturn(UserResponse.LOWER).when(mock).checkNumberWithUser(50);
		Mockito.doReturn(UserResponse.HIGHER).when(mock).checkNumberWithUser(25);
		Mockito.doReturn(UserResponse.HIGHER).when(mock).checkNumberWithUser(38);
		Mockito.doReturn(UserResponse.HIGHER).when(mock).checkNumberWithUser(44);
		Mockito.doReturn(UserResponse.HIGHER).when(mock).checkNumberWithUser(47);
		Mockito.doReturn(UserResponse.LOWER).when(mock).checkNumberWithUser(49);
		Mockito.doReturn(UserResponse.YES).when(mock).checkNumberWithUser(48);
		mock.guessNumber();		
		assertTrue("testGuessNumberEven: ", out.toString().contains(Constants.EXIT_SUCCESS));
		
		//Guessing 2
		guessNumberService = new GuessNumberService(0, 50);
		mock = Mockito.spy(guessNumberService);		
		Mockito.doReturn(UserResponse.HIGHER).when(mock).checkNumberWithUser(0);	
		Mockito.doReturn(UserResponse.LOWER).when(mock).checkNumberWithUser(50);
		Mockito.doReturn(UserResponse.LOWER).when(mock).checkNumberWithUser(25);
		Mockito.doReturn(UserResponse.LOWER).when(mock).checkNumberWithUser(12);
		Mockito.doReturn(UserResponse.LOWER).when(mock).checkNumberWithUser(6);
		Mockito.doReturn(UserResponse.LOWER).when(mock).checkNumberWithUser(3);
		Mockito.doReturn(UserResponse.HIGHER).when(mock).checkNumberWithUser(1);
		Mockito.doReturn(UserResponse.YES).when(mock).checkNumberWithUser(2);
		mock.guessNumber();		
		assertTrue("testGuessNumberEven: ", out.toString().contains(Constants.EXIT_SUCCESS));
	}
	
	@Test
	public void testGuessNumberOdd() throws IOException, InvalidNumberGuessedException {	
		//Guessing 49.
		GuessNumberService guessNumberService = new GuessNumberService(0, 50);
		GuessNumberService mock = Mockito.spy(guessNumberService);		
		Mockito.doReturn(UserResponse.HIGHER).when(mock).checkNumberWithUser(0);	
		Mockito.doReturn(UserResponse.LOWER).when(mock).checkNumberWithUser(50);
		Mockito.doReturn(UserResponse.HIGHER).when(mock).checkNumberWithUser(25);
		Mockito.doReturn(UserResponse.HIGHER).when(mock).checkNumberWithUser(38);
		Mockito.doReturn(UserResponse.HIGHER).when(mock).checkNumberWithUser(44);
		Mockito.doReturn(UserResponse.HIGHER).when(mock).checkNumberWithUser(47);
		Mockito.doReturn(UserResponse.YES).when(mock).checkNumberWithUser(49);
		mock.guessNumber();		
		assertTrue("testGuessNumberEven: ", out.toString().contains(Constants.EXIT_SUCCESS));
		
		//Guessing 1.
		guessNumberService = new GuessNumberService(0, 50);
		mock = Mockito.spy(guessNumberService);		
		Mockito.doReturn(UserResponse.HIGHER).when(mock).checkNumberWithUser(0);	
		Mockito.doReturn(UserResponse.LOWER).when(mock).checkNumberWithUser(50);
		Mockito.doReturn(UserResponse.LOWER).when(mock).checkNumberWithUser(25);
		Mockito.doReturn(UserResponse.LOWER).when(mock).checkNumberWithUser(12);
		Mockito.doReturn(UserResponse.LOWER).when(mock).checkNumberWithUser(6);
		Mockito.doReturn(UserResponse.LOWER).when(mock).checkNumberWithUser(3);
		Mockito.doReturn(UserResponse.YES).when(mock).checkNumberWithUser(1);
		mock.guessNumber();		
		assertTrue("testGuessNumberOdd: ", out.toString().contains(Constants.EXIT_SUCCESS));
	}
	
	@Test
	public void testGuessNumberNegatives() throws IOException, InvalidNumberGuessedException {		
		//Guessing -7 between -25 and 25
		GuessNumberService guessNumberService = new GuessNumberService(-25, 25);
		GuessNumberService mock = Mockito.spy(guessNumberService);		
		Mockito.doReturn(UserResponse.HIGHER).when(mock).checkNumberWithUser(-25);	
		Mockito.doReturn(UserResponse.LOWER).when(mock).checkNumberWithUser(25);
		Mockito.doReturn(UserResponse.LOWER).when(mock).checkNumberWithUser(0);
		Mockito.doReturn(UserResponse.HIGHER).when(mock).checkNumberWithUser(-12);
		Mockito.doReturn(UserResponse.LOWER).when(mock).checkNumberWithUser(-6);
		Mockito.doReturn(UserResponse.HIGHER).when(mock).checkNumberWithUser(-9);
		Mockito.doReturn(UserResponse.HIGHER).when(mock).checkNumberWithUser(-8);
		Mockito.doReturn(UserResponse.YES).when(mock).checkNumberWithUser(-7);
		mock.guessNumber();		
		assertTrue("testGuessNumberNegative: ", out.toString().contains(Constants.EXIT_SUCCESS));
		
		//Guessing 7 between -25 and 25
		guessNumberService = new GuessNumberService(-25, 25);
		mock = Mockito.spy(guessNumberService);		
		Mockito.doReturn(UserResponse.HIGHER).when(mock).checkNumberWithUser(-25);	
		Mockito.doReturn(UserResponse.LOWER).when(mock).checkNumberWithUser(25);
		Mockito.doReturn(UserResponse.HIGHER).when(mock).checkNumberWithUser(0);
		Mockito.doReturn(UserResponse.LOWER).when(mock).checkNumberWithUser(13);
		Mockito.doReturn(UserResponse.YES).when(mock).checkNumberWithUser(7);		
		mock.guessNumber();		
		assertTrue("testGuessNumberNegative: ", out.toString().contains(Constants.EXIT_SUCCESS));
	}
	
	    @Test
		public void testGuessNumberLong() throws IOException, InvalidNumberGuessedException {	
	    	//Guessing a long value 2147483649L between Long.MIN_VALUE and Long.MAX_VALUE
			GuessNumberService guessNumberService = new GuessNumberService(Long.MIN_VALUE, Long.MAX_VALUE);
			GuessNumberService mock = Mockito.spy(guessNumberService);		
			Mockito.doAnswer(new Answer<UserResponse>() {
			    @Override
			    public UserResponse answer(InvocationOnMock invocation) throws Throwable {
			      Object[] args = invocation.getArguments();
			      long param = Long.valueOf(args[0].toString());
			      if(param > 2147483649L){
			    	  return UserResponse.LOWER;
			      } else if(param < 2147483649L){
			    	  return UserResponse.HIGHER;
			      } else {
			    	  return UserResponse.YES;
			      }
			    }
			  }).when(mock).checkNumberWithUser(Mockito.anyLong());		
			mock.guessNumber();	
			assertTrue("testGuessNumberLong: ", out.toString().contains(Constants.EXIT_SUCCESS));
			
			//Guessing a long value -2147483649L between Long.MIN_VALUE and Long.MAX_VALUE
			guessNumberService = new GuessNumberService(Long.MIN_VALUE, Long.MAX_VALUE);
			mock = Mockito.spy(guessNumberService);		
			Mockito.doAnswer(new Answer<UserResponse>() {
			    @Override
			    public UserResponse answer(InvocationOnMock invocation) throws Throwable {
			      Object[] args = invocation.getArguments();
			      long param = Long.valueOf(args[0].toString());
			      if(param > -2147483649L){
			    	  return UserResponse.LOWER;
			      } else if(param < -2147483649L){
			    	  return UserResponse.HIGHER;
			      } else {
			    	  return UserResponse.YES;
			      }
			    }
			  }).when(mock).checkNumberWithUser(Mockito.anyLong());		
			mock.guessNumber();	
			assertTrue("testGuessNumberLong: ", out.toString().contains(Constants.EXIT_SUCCESS));
		}
	
	@Test(expected=IllegalArgumentException.class)
	public void checkNumberWithUserTestIllegalCommands(){
		InputUtility scanner = Mockito.mock(InputUtility.class);
		Mockito.when(scanner.nextLine()).thenReturn("invalidCommand");
		GuessNumberService guessNumberService = new GuessNumberService(Integer.MIN_VALUE, Integer.MAX_VALUE);	
		guessNumberService.setInputScanner(scanner);
		guessNumberService.checkNumberWithUser(Integer.MIN_VALUE);
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(Constants.EXIT_COMMAND);
	}
	
	
	@After
	public void tearDown() throws Exception {
	    System.setOut(null);
	}
	

}
