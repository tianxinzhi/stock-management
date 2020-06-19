package com.hkt.btu.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.hkt.btu.domain.enumeration.Subinventory;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.hkt.btu.domain.StockBalance} entity. This class is used
 * in {@link com.hkt.btu.web.rest.StockBalanceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /stock-balances?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StockBalanceCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Subinventory
     */
    public static class SubinventoryFilter extends Filter<Subinventory> {

        public SubinventoryFilter() {
        }

        public SubinventoryFilter(SubinventoryFilter filter) {
            super(filter);
        }

        @Override
        public SubinventoryFilter copy() {
            return new SubinventoryFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private SubinventoryFilter subInventory;

    private IntegerFilter quantityOnhand;

    private IntegerFilter quantityReserved;

    private LongFilter stockItemId;

    public StockBalanceCriteria() {
    }

    public StockBalanceCriteria(StockBalanceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.subInventory = other.subInventory == null ? null : other.subInventory.copy();
        this.quantityOnhand = other.quantityOnhand == null ? null : other.quantityOnhand.copy();
        this.quantityReserved = other.quantityReserved == null ? null : other.quantityReserved.copy();
        this.stockItemId = other.stockItemId == null ? null : other.stockItemId.copy();
    }

    @Override
    public StockBalanceCriteria copy() {
        return new StockBalanceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public SubinventoryFilter getSubInventory() {
        return subInventory;
    }

    public void setSubInventory(SubinventoryFilter subInventory) {
        this.subInventory = subInventory;
    }

    public IntegerFilter getQuantityOnhand() {
        return quantityOnhand;
    }

    public void setQuantityOnhand(IntegerFilter quantityOnhand) {
        this.quantityOnhand = quantityOnhand;
    }

    public IntegerFilter getQuantityReserved() {
        return quantityReserved;
    }

    public void setQuantityReserved(IntegerFilter quantityReserved) {
        this.quantityReserved = quantityReserved;
    }

    public LongFilter getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(LongFilter stockItemId) {
        this.stockItemId = stockItemId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StockBalanceCriteria that = (StockBalanceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(subInventory, that.subInventory) &&
            Objects.equals(quantityOnhand, that.quantityOnhand) &&
            Objects.equals(quantityReserved, that.quantityReserved) &&
            Objects.equals(stockItemId, that.stockItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        subInventory,
        quantityOnhand,
        quantityReserved,
        stockItemId
        );
    }

    @Override
    public String toString() {
        return "StockBalanceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (subInventory != null ? "subInventory=" + subInventory + ", " : "") +
                (quantityOnhand != null ? "quantityOnhand=" + quantityOnhand + ", " : "") +
                (quantityReserved != null ? "quantityReserved=" + quantityReserved + ", " : "") +
                (stockItemId != null ? "stockItemId=" + stockItemId + ", " : "") +
            "}";
    }

}
