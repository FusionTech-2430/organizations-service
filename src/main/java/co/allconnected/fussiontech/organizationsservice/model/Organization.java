package co.allconnected.fussiontech.organizationsservice.model;

import co.allconnected.fussiontech.organizationsservice.dtos.OrganizationCreateDTO;
import co.allconnected.fussiontech.organizationsservice.dtos.OrganizationDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "\"organization\"", schema = "all_connected_users")
public class Organization {

    public Organization(OrganizationCreateDTO dto) {
        this.name = dto.name();
        this.address = dto.address();
        this.locationLat = dto.location_lat();
        this.locationLng = dto.location_lng();
    }

    @Id
    @Column(name = "id_organization", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "address", nullable = false, length = 45)
    private String address;

    @Column(name = "location_lat", nullable = false, precision = 9, scale = 6)
    private BigDecimal locationLat;

    @Column(name = "location_lng", nullable = false, precision = 9, scale = 6)
    private BigDecimal locationLng;

    @Column(name = "photo_url", length = 200)
    private String photoUrl;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
    private Set<UserOrganization> userOrganizations = new LinkedHashSet<>();

}