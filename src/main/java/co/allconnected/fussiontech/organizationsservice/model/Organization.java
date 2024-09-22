package co.allconnected.fussiontech.organizationsservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "organization", schema = "all_connected_users")
public class Organization {
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

}