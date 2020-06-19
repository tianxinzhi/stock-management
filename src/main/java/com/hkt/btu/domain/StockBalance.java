package com.hkt.btu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.hkt.btu.domain.enumeration.Subinventory;

/**
 * A StockBalance.
 */
@Entity
@Table(name = "stock_balance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StockBalance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sub_inventory", nullable = false)
    private Subinventory subInventory;

    @NotNull
    @Column(name = "quantity_onhand", nullable = false)
    private Integer quantityOnhand;

    @NotNull
    @Column(name = "quantity_reserved", nullable = false)
    private Integer quantityReserved;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("stockBalances")
    private StockItem stockItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Subinventory getSubInventory() {
        return subInventory;
    }

    public StockBalance subInventory(Subinventory subInventory) {
        this.subInventory = subInventory;
        return this;
    }

    public void setSubInventory(Subinventory subInventory) {
        this.subInventory = subInventory;
    }

    public Integer getQuantityOnhand() {
        return quantityOnhand;
    }

    public StockBalance quantityOnhand(Integer quantityOnhand) {
        this.quantityOnhand = quantityOnhand;
        return this;
    }

    public void setQuantityOnhand(Integer quantityOnhand) {
        this.quantityOnhand = quantityOnhand;
    }

    public Integer getQuantityReserved() {
        return quantityReserved;
    }

    public StockBalance quantityReserved(Integer quantityReserved) {
        this.quantityReserved = quantityReserved;
        return this;
    }

    public void setQuantityReserved(Integer quantityReserved) {
        this.quantityReserved = quantityReserved;
    }

    public StockItem getStockItem() {
        return stockItem;
    }

    public StockBalance stockItem(StockItem stockItem) {
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
        if (!(o instanceof StockBalance)) {
            return false;
        }
        return id != null && id.equals(((StockBalance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "StockBalance{" +
            "id=" + getId() +
            ", subInventory='" + getSubInventory() + "'" +
            ", quantityOnhand=" + getQuantityOnhand() +
            ", quantityReserved=" + getQuantityReserved() +
            "}";
    }
}
