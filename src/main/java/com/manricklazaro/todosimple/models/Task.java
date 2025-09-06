package com.manricklazaro.todosimple.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = Task.TABLE_NAME)
public class Task {
    public static final String TABLE_NAME = "task";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @Column(name = "description", length = 225, nullable = false)
    @NotBlank
    @Size(min = 1, max = 255)
    private String description;

    private final String uuid;
    

    public Task() {
        this.uuid = UUID.randomUUID().toString();
    }


    public Task(Long id, User user, String description, String uuid) {
        this.id = id;
        this.user = user;
        this.description = description;
        this.uuid = UUID.randomUUID().toString();
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUuid() {
        return this.uuid;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (obj == null)
            return false;

        if (!(obj instanceof User))
            return false;

        Task other = (Task) obj;

        return Objects.equals(other.uuid, this.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.uuid);
    }
}
