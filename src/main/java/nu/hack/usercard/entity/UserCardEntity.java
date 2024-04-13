package nu.hack.usercard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nu.hack.appUser.entity.AppUserEntity;
import nu.hack.bankcard.entity.BankCardEntity;
import nu.hack.common.entity.AuditEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;

@Entity
@Table(name = "user_cards")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLRestriction("is_deleted = 0")
@SQLDelete(sql = "update user_cards set is_deleted = 1, deleted_at = now() where id = ?")
public class UserCardEntity extends AuditEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUserEntity user;

    @Column(name = "alias")
    private String alias;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_card_id", nullable = false)
    private BankCardEntity bankCard;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "valid_until")
    private LocalDate validUntil;
}
