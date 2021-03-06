package com.hkt.btu.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A AttrValue.
 */
@Entity
@Table(name = "attr_value")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AttrValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attr_value")
    private String attrValue;

    @Column(name = "unit_of_measure")
    private String unitOfMeasure;

    @Column(name = "value_from")
    private String valueFrom;

    @Column(name = "value_to")
    private String valueTo;

    @ManyToMany(mappedBy = "attrValues")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Attr> attrs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public AttrValue attrValue(String attrValue) {
        this.attrValue = attrValue;
        return this;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public AttrValue unitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
        return this;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getValueFrom() {
        return valueFrom;
    }

    public AttrValue valueFrom(String valueFrom) {
        this.valueFrom = valueFrom;
        return this;
    }

    public void setValueFrom(String valueFrom) {
        this.valueFrom = valueFrom;
    }

    public String getValueTo() {
        return valueTo;
    }

    public AttrValue valueTo(String valueTo) {
        this.valueTo = valueTo;
        return this;
    }

    public void setValueTo(String valueTo) {
        this.valueTo = valueTo;
    }

    public Set<Attr> getAttrs() {
        return attrs;
    }

    public AttrValue attrs(Set<Attr> attrs) {
        this.attrs = attrs;
        return this;
    }

    public AttrValue addAttrs(Attr attr) {
        this.attrs.add(attr);
        attr.getAttrValues().add(this);
        return this;
    }

    public AttrValue removeAttrs(Attr attr) {
        this.attrs.remove(attr);
        attr.getAttrValues().remove(this);
        return this;
    }

    public void setAttrs(Set<Attr> attrs) {
        this.attrs = attrs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttrValue)) {
            return false;
        }
        return id != null && id.equals(((AttrValue) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AttrValue{" +
            "id=" + getId() +
            ", attrValue='" + getAttrValue() + "'" +
            ", unitOfMeasure='" + getUnitOfMeasure() + "'" +
            ", valueFrom='" + getValueFrom() + "'" +
            ", valueTo='" + getValueTo() + "'" +
            "}";
    }
}
