package reposeModel;

public class ReceivedUserResponse {

    private int code;
    private String type;
    private String message;

    public int getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public void printResponse(){
        System.out.println("The response after post is: code: " + code + "\n" +
                "type: " + type + "\n" +
                "message: " + message);
    }
}