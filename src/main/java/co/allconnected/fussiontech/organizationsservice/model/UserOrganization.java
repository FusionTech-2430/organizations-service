package co.allconnected.fussiontech.organizationsservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "user_organization", schema = "all_connected_users")
public class UserOrganization {
    @EmbeddedId
    private UserOrganizationId id;

    @MapsId("idOrganization")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_organization", nullable = false)
    private Organization idOrganization;

    @MapsId("idUser")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_user", nullable = false)
    private User idUser;

    @Column(name = "join_date", nullable = false)
    private Instant joinDate;

}