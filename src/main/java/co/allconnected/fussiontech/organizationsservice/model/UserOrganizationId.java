package co.allconnected.fussiontech.organizationsservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class UserOrganizationId implements Serializable {
    private String idUser;
    private UUID idOrganization;

    public UserOrganizationId() {}

    public UserOrganizationId(String idUser, UUID idOrganization) {
        this.idUser = idUser;
        this.idOrganization = idOrganization;
    }

    // Getters y Setters

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public UUID getIdOrganization() {
        return idOrganization;
    }

    public void setIdOrganization(UUID idOrganization) {
        this.idOrganization = idOrganization;
    }

    // HashCode y Equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserOrganizationId that = (UserOrganizationId) o;
        return idUser.equals(that.idUser) &&
                idOrganization.equals(that.idOrganization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, idOrganization);
    }
}