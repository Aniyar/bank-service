package nu.hack.bank.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nu.hack.common.entity.AuditEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;

@Entity
@Table(name = "bank_cards")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLRestriction("is_deleted = 0")
@SQLDelete(sql = "update bank_cards set is_deleted = 1, deleted_at = now() where id = ?")
public class BankCardEntity extends AuditEntity {

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "alias")
    private String alias;

    @Column(name = "bank_id", nullable = false)
    private Integer bankId;

    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    @Column(name = "valid_until", nullable = false)
    private LocalDate validUntil;
}
