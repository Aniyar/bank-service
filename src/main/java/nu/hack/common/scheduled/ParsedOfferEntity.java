package nu.hack.common.scheduled;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nu.hack.common.entity.AuditEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;


@Entity
@Table(name = "parsed_offers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLRestriction("is_deleted = 0")
@SQLDelete(sql = "update parsed_offers set is_deleted = 1, deleted_at = now() where id = ?")
public class ParsedOfferEntity extends AuditEntity {

    @Column(name = "link", nullable = false)
    private String link;

    @Column(name = "success")
    private Boolean success;

    @Column(name = "error_msg")
    private String errorMsg;
}
