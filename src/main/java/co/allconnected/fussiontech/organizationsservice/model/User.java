package co.allconnected.fussiontech.organizationsservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "\"user\"", schema = "all_connected_users")
public class User {
    @Id
    @Column(name = "id_user", nullable = false, length = 28)
    private String idUser;

    @Column(name = "fullname", length = 100)
    private String fullname;

    @Column(name = "username", nullable = false, length = 45)
    private String username;

    @Column(name = "mail", length = 45)
    private String mail;

    @Column(name = "photo_url", length = 700)
    private String photoUrl;

    @Column(name = "active", nullable = false)
    private Boolean active = false;

    @OneToMany
    @JoinTable(name = "user_organization",
            schema = "all_connected_users",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_organization"))
    private Set<Organization> organizations = new LinkedHashSet<>();
}