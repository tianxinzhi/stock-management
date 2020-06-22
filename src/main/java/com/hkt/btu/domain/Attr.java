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
 * A Attr.
 */
@Entity
@Table(name = "attr")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Attr implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attr_name")
    private String attrName;

    @Column(name = "attr_desc")
    private String attrDesc;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "attr_attr_values",
               joinColumns = @JoinColumn(name = "attr_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "attr_values_id", referencedColumnName = "id"))
    private Set<AttrValue> attrValues = new HashSet<>();

    @ManyToMany(mappedBy = "attrs")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Spec> specs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttrName() {
        return attrName;
    }

    public Attr attrName(String attrName) {
        this.attrName = attrName;
        return this;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrDesc() {
        return attrDesc;
    }

    public Attr attrDesc(String attrDesc) {
        this.attrDesc = attrDesc;
        return this;
    }

    public void setAttrDesc(String attrDesc) {
        this.attrDesc = attrDesc;
    }

    public Set<AttrValue> getAttrValues() {
        return attrValues;
    }

    public Attr attrValues(Set<AttrValue> attrValues) {
        this.attrValues = attrValues;
        return this;
    }

    public Attr addAttrValues(AttrValue attrValue) {
        this.attrValues.add(attrValue);
        attrValue.getAttrs().add(this);
        return this;
    }

    public Attr removeAttrValues(AttrValue attrValue) {
        this.attrValues.remove(attrValue);
        attrValue.getAttrs().remove(this);
        return this;
    }

    public void setAttrValues(Set<AttrValue> attrValues) {
        this.attrValues = attrValues;
    }

    public Set<Spec> getSpecs() {
        return specs;
    }

    public Attr specs(Set<Spec> specs) {
        this.specs = specs;
        return this;
    }

    public Attr addSpecs(Spec spec) {
        this.specs.add(spec);
        spec.getAttrs().add(this);
        return this;
    }

    public Attr removeSpecs(Spec spec) {
        this.specs.remove(spec);
        spec.getAttrs().remove(this);
        return this;
    }

    public void setSpecs(Set<Spec> specs) {
        this.specs = specs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attr)) {
            return false;
        }
        return id != null && id.equals(((Attr) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Attr{" +
            "id=" + getId() +
            ", attrName='" + getAttrName() + "'" +
            ", attrDesc='" + getAttrDesc() + "'" +
            "}";
    }
}
