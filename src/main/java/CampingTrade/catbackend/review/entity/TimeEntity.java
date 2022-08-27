package CampingTrade.catbackend.review.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
abstract class TimeEntity {

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private String createdDate;

    @LastModifiedDate
    @Column(name = "modified_date", updatable = false)
    private String modifiedDate;

    /* 해당 엔티티 저장하기 이전에 실행 */
    @PrePersist
    public void onPrePersist() {
        this.createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.modifiedDate = this.createdDate;
    }

    /* 해당 엔티티 업데이트 하기 이전에 실행 */
    @PreUpdate
    public void onPreUpdate() {
        this.modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    }
}
