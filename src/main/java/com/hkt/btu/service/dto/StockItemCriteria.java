package com.hkt.btu.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.hkt.btu.domain.StockItem} entity. This class is used
 * in {@link com.hkt.btu.web.rest.StockItemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /stock-items?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StockItemCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter itemNumber;

    private StringFilter itemDescription;

    public StockItemCriteria() {
    }

    public StockItemCriteria(StockItemCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.itemNumber = other.itemNumber == null ? null : other.itemNumber.copy();
        this.itemDescription = other.itemDescription == null ? null : other.itemDescription.copy();
    }

    @Override
    public StockItemCriteria copy() {
        return new StockItemCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(StringFilter itemNumber) {
        this.itemNumber = itemNumber;
    }

    public StringFilter getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(StringFilter itemDescription) {
        this.itemDescription = itemDescription;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StockItemCriteria that = (StockItemCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(itemNumber, that.itemNumber) &&
            Objects.equals(itemDescription, that.itemDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        itemNumber,
        itemDescription
        );
    }

    @Override
    public String toString() {
        return "StockItemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (itemNumber != null ? "itemNumber=" + itemNumber + ", " : "") +
                (itemDescription != null ? "itemDescription=" + itemDescription + ", " : "") +
            "}";
    }

}
