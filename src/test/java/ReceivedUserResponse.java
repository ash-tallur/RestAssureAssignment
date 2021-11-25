public class ReceivedUserResponse {

    private int code;
    private String type,message;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void printResponse(){
        //printing the response received after post request
        System.out.println("The response after post is: code: " + getCode() + "\n" +
                "type: " + getType() + "\n" +
                "message: " + getMessage());
    }
}
