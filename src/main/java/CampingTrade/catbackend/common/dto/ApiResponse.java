package CampingTrade.catbackend.common.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class ApiResponse<T> {

    public static <T> ResponseEntity<T> success (T body) {
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    public static <T> ResponseEntity<T> created (T body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    public static <T> ResponseEntity<T> forbidden (T body) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }

    public static <T> ResponseEntity<T> internalServerError (T body) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}