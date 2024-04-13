package nu.hack.offer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nu.hack.bankcard.entity.BankCardEntity;
import nu.hack.category.entity.CategoryEntity;

import nu.hack.common.entity.AuditEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "offers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLRestriction("is_deleted = 0")
@SQLDelete(sql = "update offers set is_deleted = 1, deleted_at = now() where id = ?")
public class OfferEntity extends AuditEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_card_id", nullable = false)
    private BankCardEntity bank;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @Column(name = "percentage", nullable = false)
    private BigDecimal percentage;

    @Column(name = "payment_terms")
    private String paymentTerms;

    @Column(name = "conditions")
    private String conditions;

    @Column(name = "dateFrom", nullable = false)
    private LocalDate dateFrom;

    @Column(name = "dateTo", nullable = false)
    private LocalDate dateTo;
}
