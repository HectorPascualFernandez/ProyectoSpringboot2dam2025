package com.dam.tienda.entities;



import com.dam.tienda.validation.ExistsByUsername;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column (unique=true)
    @NotBlank
    @Size(min=4, max=12)
    @ExistsByUsername
    private String username;

    @NotBlank
    // Le damos acceso cuando se escribe el Json no cuando se lee
    // Por seguridad se crea el JsonProperty
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    // Para ignorar el JSON
    //@JsonIgnore
    private String password;

    // @JsonIgnoreProperties({"users"})
    @ManyToMany
    @JoinTable(
            name="users_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"),
            uniqueConstraints={@UniqueConstraint(columnNames =
            {"user_id","role_id"})}
    )
    private List<Role> roles;
    @JsonProperty (access = JsonProperty.Access.WRITE_ONLY)
    private boolean enabled;
    // Esta anotación marca un método para que sea
    // ejecutado automáticamente antes de que una entidad sea persistida
    @PrePersist
    public void prePersist(){
        enabled = true;
    }

    // No es campo de la base de datos, es una bandera
    // Para indicarle a JPA que no cree ningún campo en la bd
    @Transient
     @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean admin;

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAdmin() {

        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }



    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
