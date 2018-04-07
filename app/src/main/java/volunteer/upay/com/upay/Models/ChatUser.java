package volunteer.upay.com.upay.Models;

public class ChatUser {
    String email, name, firebaseToken;

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public ChatUser(String email, String name, String firebaseToken) {
        this.email = email;
        this.name = name;
        this.firebaseToken = firebaseToken;
    }
}
