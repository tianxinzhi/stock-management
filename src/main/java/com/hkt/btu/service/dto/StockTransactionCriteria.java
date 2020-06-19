package com.hkt.btu.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.hkt.btu.domain.enumeration.Subinventory;
import com.hkt.btu.domain.enumeration.Subinventory;
import com.hkt.btu.domain.enumeration.TransactionType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.hkt.btu.domain.StockTransaction} entity. This class is used
 * in {@link com.hkt.btu.web.rest.StockTransactionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /stock-transactions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StockTransactionCriteria implements Serializable, Criteria {
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
    /**
     * Class for filtering TransactionType
     */
    public static class TransactionTypeFilter extends Filter<TransactionType> {

        public TransactionTypeFilter() {
        }

        public TransactionTypeFilter(TransactionTypeFilter filter) {
            super(filter);
        }

        @Override
        public TransactionTypeFilter copy() {
            return new TransactionTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private SubinventoryFilter subinventoryFrom;

    private SubinventoryFilter subinventoryTo;

    private TransactionTypeFilter transactionType;

    private IntegerFilter transactionQuantity;

    private StringFilter reference;

    private LongFilter stockItemId;

    public StockTransactionCriteria() {
    }

    public StockTransactionCriteria(StockTransactionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.subinventoryFrom = other.subinventoryFrom == null ? null : other.subinventoryFrom.copy();
        this.subinventoryTo = other.subinventoryTo == null ? null : other.subinventoryTo.copy();
        this.transactionType = other.transactionType == null ? null : other.transactionType.copy();
        this.transactionQuantity = other.transactionQuantity == null ? null : other.transactionQuantity.copy();
        this.reference = other.reference == null ? null : other.reference.copy();
        this.stockItemId = other.stockItemId == null ? null : other.stockItemId.copy();
    }

    @Override
    public StockTransactionCriteria copy() {
        return new StockTransactionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public SubinventoryFilter getSubinventoryFrom() {
        return subinventoryFrom;
    }

    public void setSubinventoryFrom(SubinventoryFilter subinventoryFrom) {
        this.subinventoryFrom = subinventoryFrom;
    }

    public SubinventoryFilter getSubinventoryTo() {
        return subinventoryTo;
    }

    public void setSubinventoryTo(SubinventoryFilter subinventoryTo) {
        this.subinventoryTo = subinventoryTo;
    }

    public TransactionTypeFilter getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionTypeFilter transactionType) {
        this.transactionType = transactionType;
    }

    public IntegerFilter getTransactionQuantity() {
        return transactionQuantity;
    }

    public void setTransactionQuantity(IntegerFilter transactionQuantity) {
        this.transactionQuantity = transactionQuantity;
    }

    public StringFilter getReference() {
        return reference;
    }

    public void setReference(StringFilter reference) {
        this.reference = reference;
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
        final StockTransactionCriteria that = (StockTransactionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(subinventoryFrom, that.subinventoryFrom) &&
            Objects.equals(subinventoryTo, that.subinventoryTo) &&
            Objects.equals(transactionType, that.transactionType) &&
            Objects.equals(transactionQuantity, that.transactionQuantity) &&
            Objects.equals(reference, that.reference) &&
            Objects.equals(stockItemId, that.stockItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        subinventoryFrom,
        subinventoryTo,
        transactionType,
        transactionQuantity,
        reference,
        stockItemId
        );
    }

    @Override
    public String toString() {
        return "StockTransactionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (subinventoryFrom != null ? "subinventoryFrom=" + subinventoryFrom + ", " : "") +
                (subinventoryTo != null ? "subinventoryTo=" + subinventoryTo + ", " : "") +
                (transactionType != null ? "transactionType=" + transactionType + ", " : "") +
                (transactionQuantity != null ? "transactionQuantity=" + transactionQuantity + ", " : "") +
                (reference != null ? "reference=" + reference + ", " : "") +
                (stockItemId != null ? "stockItemId=" + stockItemId + ", " : "") +
            "}";
    }

}
