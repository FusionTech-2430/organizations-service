package co.allconnected.fussiontech.organizationsservice.dtos;

import co.allconnected.fussiontech.organizationsservice.model.Organization;

public class OrganizationDTO {
    // Realizar el DTO de la entidad Organization
    /*
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
     */
    private String id_organization;
    private String name;
    private String address;
    private String location_lat;
    private String location_lng;
    private String photoUrl;

    public OrganizationDTO(Organization organization){
        this.id_organization = organization.getId().toString();
        this.name = organization.getName();
        this.address = organization.getAddress();
        this.location_lat = organization.getLocationLat().toString();
        this.location_lng = organization.getLocationLng().toString();
        this.photoUrl = organization.getPhotoUrl();
    }
}
