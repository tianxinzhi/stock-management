package com.hkt.btu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

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

    @ManyToOne
    @JsonIgnoreProperties("attrs")
    private AttrValue attrValue;

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

    public AttrValue getAttrValue() {
        return attrValue;
    }

    public Attr attrValue(AttrValue attrValue) {
        this.attrValue = attrValue;
        return this;
    }

    public void setAttrValue(AttrValue attrValue) {
        this.attrValue = attrValue;
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
