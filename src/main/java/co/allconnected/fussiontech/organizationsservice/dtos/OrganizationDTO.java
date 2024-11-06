package co.allconnected.fussiontech.organizationsservice.dtos;

import co.allconnected.fussiontech.organizationsservice.model.Organization;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class OrganizationDTO {

    private String id_organization;
    private String name;
    private String address;
    private BigDecimal location_lat;
    private BigDecimal location_lng;
    private String photoUrl;
    private String[] users;

    public OrganizationDTO(Organization organization) {
        this.id_organization = organization.getId().toString();
        this.name = organization.getName();
        this.address = organization.getAddress();
        this.location_lat = organization.getLocationLat();
        this.location_lng = organization.getLocationLng();
        this.photoUrl = organization.getPhotoUrl();
        this.users = organization.getUserOrganizations().stream().map(userOrganization -> userOrganization.getUser().getIdUser()).toArray(String[]::new);
    }
}