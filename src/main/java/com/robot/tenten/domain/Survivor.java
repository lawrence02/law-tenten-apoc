package com.robot.tenten.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.robot.tenten.domain.enumeration.Gender;
import com.robot.tenten.domain.enumeration.InfectionStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Survivor.
 */
@Entity
@Table(name = "survivor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Survivor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The survivorId attribute.
     */
    @Schema(description = "The survivorId attribute.")
    @Column(name = "survivor_id")
    private String survivorId;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private InfectionStatus status;

    @OneToMany(mappedBy = "survivor")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "survivor" }, allowSetters = true)
    private Set<Resource> resources = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Survivor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurvivorId() {
        return this.survivorId;
    }

    public Survivor survivorId(String survivorId) {
        this.setSurvivorId(survivorId);
        return this;
    }

    public void setSurvivorId(String survivorId) {
        this.survivorId = survivorId;
    }

    public String getName() {
        return this.name;
    }

    public Survivor name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return this.age;
    }

    public Survivor age(Integer age) {
        this.setAge(age);
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Gender getGender() {
        return this.gender;
    }

    public Survivor gender(Gender gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public Survivor latitude(String latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public Survivor longitude(String longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public InfectionStatus getStatus() {
        return this.status;
    }

    public Survivor status(InfectionStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(InfectionStatus status) {
        this.status = status;
    }

    public Set<Resource> getResources() {
        return this.resources;
    }

    public void setResources(Set<Resource> resources) {
        if (this.resources != null) {
            this.resources.forEach(i -> i.setSurvivor(null));
        }
        if (resources != null) {
            resources.forEach(i -> i.setSurvivor(this));
        }
        this.resources = resources;
    }

    public Survivor resources(Set<Resource> resources) {
        this.setResources(resources);
        return this;
    }

    public Survivor addResource(Resource resource) {
        this.resources.add(resource);
        resource.setSurvivor(this);
        return this;
    }

    public Survivor removeResource(Resource resource) {
        this.resources.remove(resource);
        resource.setSurvivor(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Survivor)) {
            return false;
        }
        return id != null && id.equals(((Survivor) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Survivor{" +
            "id=" + getId() +
            ", survivorId='" + getSurvivorId() + "'" +
            ", name='" + getName() + "'" +
            ", age=" + getAge() +
            ", gender='" + getGender() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
