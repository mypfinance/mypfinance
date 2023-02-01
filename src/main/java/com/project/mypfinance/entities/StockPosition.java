package com.project.mypfinance.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

import static javax.persistence.CascadeType.*;
import static javax.persistence.CascadeType.DETACH;

@Entity
@Table(name="stock_positions")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_position_id")
    @JsonIgnore
    private Long stockPositionId;

    @Column(name = "stock_symbol")
    private String stockSymbol;

    @Column(name = "stock_name")
    private String stockName;

    @Column(name = "stock_price")
    private BigDecimal stockPrice;

    @Column(name = "stock_units")
    private Long stockUnits;

    @ManyToOne(cascade= {PERSIST, MERGE, REFRESH, DETACH})
    @JoinColumn(name = "user_stock_position_id", referencedColumnName = "user_id")
    @JsonIgnore
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        StockPosition that = (StockPosition) o;
        return stockPositionId != null && Objects.equals(stockPositionId, that.stockPositionId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
