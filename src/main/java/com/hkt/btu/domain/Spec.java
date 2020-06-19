package com.hkt.btu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Spec.
 */
@Entity
@Table(name = "spec")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Spec implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "spec_name")
    private String specName;

    @Column(name = "spec_desc")
    private String specDesc;

    @Column(name = "ver_id")
    private String verId;

    @ManyToOne
    @JsonIgnoreProperties("specs")
    private Attr attrId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecName() {
        return specName;
    }

    public Spec specName(String specName) {
        this.specName = specName;
        return this;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getSpecDesc() {
        return specDesc;
    }

    public Spec specDesc(String specDesc) {
        this.specDesc = specDesc;
        return this;
    }

    public void setSpecDesc(String specDesc) {
        this.specDesc = specDesc;
    }

    public String getVerId() {
        return verId;
    }

    public Spec verId(String verId) {
        this.verId = verId;
        return this;
    }

    public void setVerId(String verId) {
        this.verId = verId;
    }

    public Attr getAttrId() {
        return attrId;
    }

    public Spec attrId(Attr attr) {
        this.attrId = attr;
        return this;
    }

    public void setAttrId(Attr attr) {
        this.attrId = attr;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Spec)) {
            return false;
        }
        return id != null && id.equals(((Spec) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Spec{" +
            "id=" + getId() +
            ", specName='" + getSpecName() + "'" +
            ", specDesc='" + getSpecDesc() + "'" +
            ", verId='" + getVerId() + "'" +
            "}";
    }
}
