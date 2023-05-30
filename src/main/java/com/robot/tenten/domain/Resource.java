package com.robot.tenten.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.robot.tenten.domain.enumeration.ResourceType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Resource.
 */
@Entity
@Table(name = "resource")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The survivorId attribute.
     */
    @Schema(description = "The survivorId attribute.")
    @Enumerated(EnumType.STRING)
    @Column(name = "resource_type")
    private ResourceType resourceType;

    @Column(name = "quantity")
    private String quantity;

    @ManyToOne
    @JsonIgnoreProperties(value = { "resources" }, allowSetters = true)
    private Survivor survivor;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Resource id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ResourceType getResourceType() {
        return this.resourceType;
    }

    public Resource resourceType(ResourceType resourceType) {
        this.setResourceType(resourceType);
        return this;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public String getQuantity() {
        return this.quantity;
    }

    public Resource quantity(String quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Survivor getSurvivor() {
        return this.survivor;
    }

    public void setSurvivor(Survivor survivor) {
        this.survivor = survivor;
    }

    public Resource survivor(Survivor survivor) {
        this.setSurvivor(survivor);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Resource)) {
            return false;
        }
        return id != null && id.equals(((Resource) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Resource{" +
            "id=" + getId() +
            ", resourceType='" + getResourceType() + "'" +
            ", quantity='" + getQuantity() + "'" +
            "}";
    }
}
