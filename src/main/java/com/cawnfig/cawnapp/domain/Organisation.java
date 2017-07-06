package com.cawnfig.cawnapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.cawnfig.cawnapp.domain.enumeration.Subs_Type;

/**
 * A Organisation.
 */
@Entity
@Table(name = "organisation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "organisation")
public class Organisation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "subscriptiontype")
    private Subs_Type subscriptiontype;

    @OneToMany(mappedBy = "organisation")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Application> applications = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Organisation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Subs_Type getSubscriptiontype() {
        return subscriptiontype;
    }

    public Organisation subscriptiontype(Subs_Type subscriptiontype) {
        this.subscriptiontype = subscriptiontype;
        return this;
    }

    public void setSubscriptiontype(Subs_Type subscriptiontype) {
        this.subscriptiontype = subscriptiontype;
    }

    public Set<Application> getApplications() {
        return applications;
    }

    public Organisation applications(Set<Application> applications) {
        this.applications = applications;
        return this;
    }

    public Organisation addApplication(Application application) {
        this.applications.add(application);
        application.setOrganisation(this);
        return this;
    }

    public Organisation removeApplication(Application application) {
        this.applications.remove(application);
        application.setOrganisation(null);
        return this;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Organisation organisation = (Organisation) o;
        if (organisation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), organisation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Organisation{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", subscriptiontype='" + getSubscriptiontype() + "'" +
            "}";
    }
}
