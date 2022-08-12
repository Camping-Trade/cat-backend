package CampingTrade.catbackend.oauth.exception;

public class TokenValidFailedException extends RuntimeException {

    public TokenValidFailedException() {
        super("Failed to generate Token.");
    }

    public TokenValidFailedException(String msg) {
        super(msg);
    }
}
