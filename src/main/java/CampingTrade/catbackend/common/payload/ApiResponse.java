package CampingTrade.catbackend.common.payload;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class ApiResponse<T> {

    public static <T>ResponseEntity<T> success (T body) {
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    public static <T> ResponseEntity<T> forbidden (T body) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }
}