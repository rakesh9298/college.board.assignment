package college.board.assignment;

public enum UserResponse {
      HIGHER("higher"), LOWER("lower"), YES("yes"), END("end");
      
      private  String value;
      
      private UserResponse(String value){
    	  this.value = value;
      }
      
      public static UserResponse getValue(String value){
    	  for(UserResponse userResponses : UserResponse.values()){
    		  if(userResponses.value.equalsIgnoreCase(value)){
    			  return userResponses;
    		  }
    	  }
    	  return null;
      }
      
      
}
