package co.allconnected.fussiontech.organizationsservice.model;

import co.allconnected.fussiontech.organizationsservice.model.Organization;
import co.allconnected.fussiontech.organizationsservice.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "user_organization", schema = "all_connected_users")
@IdClass(UserOrganizationId.class)  // Indica que estamos usando una clave compuesta
public class UserOrganization {

    @Id
    @Column(name = "id_user", nullable = false)
    private String idUser;

    @Id
    @Column(name = "id_organization", nullable = false)
    private UUID idOrganization;

    @ManyToOne
    @JoinColumn(name = "id_user", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_organization", insertable = false, updatable = false)
    private Organization organization;

    @Column(name = "join_date", nullable = false)
    private LocalDateTime joinDate;

    public UserOrganization() {}

    public UserOrganization(User user, Organization organization, LocalDateTime joinDate) {
        this.user = user;
        this.organization = organization;
        this.idUser = user.getIdUser();  // Llave primaria compuesta
        this.idOrganization = organization.getId();
        this.joinDate = joinDate;
    }
}
