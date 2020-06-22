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

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "spec_attrs",
               joinColumns = @JoinColumn(name = "spec_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "attrs_id", referencedColumnName = "id"))
    private Set<Attr> attrs = new HashSet<>();

    @ManyToMany(mappedBy = "specs")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Type> types = new HashSet<>();

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

    public Set<Attr> getAttrs() {
        return attrs;
    }

    public Spec attrs(Set<Attr> attrs) {
        this.attrs = attrs;
        return this;
    }

    public Spec addAttrs(Attr attr) {
        this.attrs.add(attr);
        attr.getSpecs().add(this);
        return this;
    }

    public Spec removeAttrs(Attr attr) {
        this.attrs.remove(attr);
        attr.getSpecs().remove(this);
        return this;
    }

    public void setAttrs(Set<Attr> attrs) {
        this.attrs = attrs;
    }

    public Set<Type> getTypes() {
        return types;
    }

    public Spec types(Set<Type> types) {
        this.types = types;
        return this;
    }

    public Spec addTypes(Type type) {
        this.types.add(type);
        type.getSpecs().add(this);
        return this;
    }

    public Spec removeTypes(Type type) {
        this.types.remove(type);
        type.getSpecs().remove(this);
        return this;
    }

    public void setTypes(Set<Type> types) {
        this.types = types;
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
