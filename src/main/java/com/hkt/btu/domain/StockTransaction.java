package com.hkt.btu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.hkt.btu.domain.enumeration.Subinventory;

import com.hkt.btu.domain.enumeration.TransactionType;

/**
 * A StockTransaction.
 */
@Entity
@Table(name = "stock_transaction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StockTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "subinventory_from")
    private Subinventory subinventoryFrom;

    @Enumerated(EnumType.STRING)
    @Column(name = "subinventory_to")
    private Subinventory subinventoryTo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @NotNull
    @Column(name = "transaction_quantity", nullable = false)
    private Integer transactionQuantity;

    @Size(max = 20)
    @Column(name = "reference", length = 20)
    private String reference;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("stockTransactions")
    private StockItem stockItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Subinventory getSubinventoryFrom() {
        return subinventoryFrom;
    }

    public StockTransaction subinventoryFrom(Subinventory subinventoryFrom) {
        this.subinventoryFrom = subinventoryFrom;
        return this;
    }

    public void setSubinventoryFrom(Subinventory subinventoryFrom) {
        this.subinventoryFrom = subinventoryFrom;
    }

    public Subinventory getSubinventoryTo() {
        return subinventoryTo;
    }

    public StockTransaction subinventoryTo(Subinventory subinventoryTo) {
        this.subinventoryTo = subinventoryTo;
        return this;
    }

    public void setSubinventoryTo(Subinventory subinventoryTo) {
        this.subinventoryTo = subinventoryTo;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public StockTransaction transactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
        return this;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Integer getTransactionQuantity() {
        return transactionQuantity;
    }

    public StockTransaction transactionQuantity(Integer transactionQuantity) {
        this.transactionQuantity = transactionQuantity;
        return this;
    }

    public void setTransactionQuantity(Integer transactionQuantity) {
        this.transactionQuantity = transactionQuantity;
    }

    public String getReference() {
        return reference;
    }

    public StockTransaction reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public StockItem getStockItem() {
        return stockItem;
    }

    public StockTransaction stockItem(StockItem stockItem) {
        this.stockItem = stockItem;
        return this;
    }

    public void setStockItem(StockItem stockItem) {
        this.stockItem = stockItem;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StockTransaction)) {
            return false;
        }
        return id != null && id.equals(((StockTransaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "StockTransaction{" +
            "id=" + getId() +
            ", subinventoryFrom='" + getSubinventoryFrom() + "'" +
            ", subinventoryTo='" + getSubinventoryTo() + "'" +
            ", transactionType='" + getTransactionType() + "'" +
            ", transactionQuantity=" + getTransactionQuantity() +
            ", reference='" + getReference() + "'" +
            "}";
    }
}
