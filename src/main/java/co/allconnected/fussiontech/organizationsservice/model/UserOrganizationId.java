package co.allconnected.fussiontech.organizationsservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Embeddable
public class UserOrganizationId implements Serializable {
    private static final long serialVersionUID = 7426450965082794374L;
    @Column(name = "id_organization", nullable = false)
    private UUID idOrganization;

    @Column(name = "id_user", nullable = false, length = 28)
    private String idUser;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserOrganizationId entity = (UserOrganizationId) o;
        return Objects.equals(this.idUser, entity.idUser) &&
                Objects.equals(this.idOrganization, entity.idOrganization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, idOrganization);
    }

}