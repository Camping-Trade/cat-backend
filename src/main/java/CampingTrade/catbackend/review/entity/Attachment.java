package CampingTrade.catbackend.review.entity;

import com.nimbusds.openid.connect.sdk.assurance.evidences.attachment.AttachmentType;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@SequenceGenerator(
        name = "ATTACHMENT_SEQ_GENERATOR",
        sequenceName = "ATTACHMENT_SEQ"
)
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String originFileName;
    private String storeFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Builder
    public Attachment(Long id, String originFileName, String storePath) {
        this.id = id;
        this.originFileName = originFileName;
        this.storeFileName = storePath;
    }
}
