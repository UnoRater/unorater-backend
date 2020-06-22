package com.ics499.unorater.models;

import com.ics499.unorater.models.enums.RoleName;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

/**
 * Model for the Roles entity.
 *
 * @author UNO TEAM
 */
@Entity
@Table(name="ROLES")
public class Role {

    @Id
    @GeneratedValue
    @Column(name="ROLEID", columnDefinition = "BIGINT")
    private Long id;


    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(name= "ROLENAME", length = 60)
    private RoleName name;

    public Role() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleName getName() {
        return name;
    }

    public void setName(RoleName name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
