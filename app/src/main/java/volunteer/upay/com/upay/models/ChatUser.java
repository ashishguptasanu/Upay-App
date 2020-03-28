package volunteer.upay.com.upay.models;

public class ChatUser {
    String email;
    String name;
    String firebaseToken;
    String upayId;
    String msg;

    public String getMsg() {
        return msg;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }
    public String getUpayId() {
        return upayId;
    }
    public ChatUser(){}



    public ChatUser(String email, String name, String firebaseToken, String upayId, String msg) {
        this.email = email;
        this.name = name;
        this.firebaseToken = firebaseToken;
        this.upayId = upayId;
        this.msg = msg;

    }
}
