package com.ics499.unorater.models;

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
    @Column(name="ROLEID", columnDefinition = "BIGINT")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    public Role() {}

    public Role(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
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
