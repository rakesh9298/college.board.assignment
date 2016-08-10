package college.board.assignment;

/**
 * 
 * @author rakesh
 * Class thats guesses the number that user chose, based on the answers provided for questions
 */
public class GuessNumberService {

	private final long MAX_SUPPORTED_VALUE;
	private final long MIN_SUPPORTED_VALUE;
	private InputUtility inputScanner = null; //Wrapper for java.util.Scanner to enable mocking while testing.

	public InputUtility getInputScanner() {
		return inputScanner;
	}

	public void setInputScanner(InputUtility inputScanner) {
		this.inputScanner = inputScanner;
	}

	public GuessNumberService(long minValue, long maxValue) {
		//Setting the range of numbers that program supports.
		this.MAX_SUPPORTED_VALUE = maxValue;
		this.MIN_SUPPORTED_VALUE = minValue;
		inputScanner = new InputUtility(); 
	}

	/**
	 * Public method that guesses the number based on users input
	 * Checks if the user is selecting a range beyond system supports.
	 * @throws InvalidNumberGuessedException
	 */
	public void guessNumber() throws InvalidNumberGuessedException {
		System.out.println(Constants.CHOSE_NUMBER);
		//Checking if the user  chose a number beyond the range that program supports.
		UserResponse response = this
				.checkNumberWithUser(this.MIN_SUPPORTED_VALUE);
		switch (response) {
		case LOWER:
			throw new InvalidNumberGuessedException(Constants.ERR_MIN_SUPPORTED
					+ this.MIN_SUPPORTED_VALUE);
		case YES:
			System.out.println(Constants.EXIT_SUCCESS);
			return;
		case END:
			System.out.println(Constants.EXIT_COMMAND);
			return;
		}

		response = checkNumberWithUser(this.MAX_SUPPORTED_VALUE);
		switch (response) {
		case HIGHER:
			throw new InvalidNumberGuessedException(Constants.ERR_MAX_SUPPORTED
					+ this.MAX_SUPPORTED_VALUE);
		case YES:
			System.out.println(Constants.EXIT_SUCCESS);
			return;
		case END:
			System.out.println(Constants.EXIT_COMMAND);
			return;
		}
		//User chose a valid number that program supports. Begin guessing.
		this.guessValidNumber(this.MIN_SUPPORTED_VALUE,
				this.MAX_SUPPORTED_VALUE);
	}

	/**
	 * Recursive method that guesses number using binary search.
	 * Number of questions asked depends on size of the number range.
	 * if system supports n numbers, then max number of questions asked is log(n)+2
	 * @param minNumber
	 * @param maxNumber
	 * @throws InvalidNumberGuessedException
	 */
	void guessValidNumber(long minNumber, long maxNumber)
			throws InvalidNumberGuessedException {
		//If user responded with wrong answers, inform it.
		if(!(minNumber== this.MIN_SUPPORTED_VALUE && maxNumber==this.MAX_SUPPORTED_VALUE) && minNumber==maxNumber){
			throw new IllegalArgumentException(Constants.ERROR_INVALID_RESPONSE_IN_STAGE);
		}
		long avgNumber = (maxNumber + minNumber) / 2;
		UserResponse response = checkNumberWithUser(avgNumber);
		switch (response) {
		case HIGHER:
			if (avgNumber < 0) { //Logic to support negative numbers
				avgNumber--;
			} else {
				avgNumber++;
			}
			guessValidNumber(avgNumber, maxNumber);
			break;
		case LOWER:
			guessValidNumber(minNumber, avgNumber);
			break;
		case YES:
			System.out.println(Constants.EXIT_SUCCESS);
			break;
		case END:
			System.out.println(Constants.EXIT_COMMAND);
			break;
		}
	}

	private static void exit(int exitCode) {
		System.exit(exitCode);
	}

	/**
	 * Method to communicate with user.
	 * @param possibleNumber
	 * @return
	 */
	UserResponse checkNumberWithUser(long possibleNumber) {
		StringBuilder builder = new StringBuilder();
		builder.append(Constants.IS_NUMBER).append(possibleNumber)
				.append(Constants.QUESTION_MARK);
		System.out.println(builder.toString());
		int i = 0;
		do {
			String response = inputScanner.nextLine();
			if (response == null
					|| response.trim().equals(Constants.EMPTY_STR)
					|| UserResponse.getValue(response.trim().toLowerCase()) == null) {
				System.out.println(Constants.ERR_ILLEGAL_COMMAND); //If the user types a wrong command, allow to retype it.
			} else {
				return UserResponse.getValue(response.trim().toLowerCase());
			}
			i++;
		} while (i < Constants.MAX_REPETITIONS_FOR_ILLEGAL_COMMAND); //maximum number of times user can retype a command. 
		                                                             //This can be configurable.
		throw new IllegalArgumentException(Constants.EXIT_COMMAND);
	}

	public static void main(String[] args) {
		GuessNumberService guessNumber = new GuessNumberService(0,
				Integer.MAX_VALUE); //Constructor arguments decide the range that system supports.
		                            //This can be configured from a property file or user prompt.
		try {
			guessNumber.guessNumber();
		} catch (InvalidNumberGuessedException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} catch (Throwable th) {
			th.printStackTrace();
		} finally {
			exit(0);
		}
	}

}
