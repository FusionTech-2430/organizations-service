package co.allconnected.fussiontech.organizationsservice.repository;

import co.allconnected.fussiontech.organizationsservice.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface OrganizationRepository extends JpaRepository<Organization, UUID> {

    // Consulta personalizada para filtrar por varios criterios.
    @Query("SELECT o FROM Organization o " +
            "WHERE (:name IS NULL OR o.name LIKE %:name%) " +
            "AND (:address IS NULL OR o.address LIKE %:address%) " +
            "AND (:locationLat IS NULL OR o.locationLat = :locationLat) " +
            "AND (:locationLng IS NULL OR o.locationLng = :locationLng)")
    List<Organization> findOrganizationsByFilters(@Param("name") String name,
                                                  @Param("address") String address,
                                                  @Param("locationLat") BigDecimal locationLat,  // Cambio a BigDecimal.
                                                  @Param("locationLng") BigDecimal locationLng);  // Cambio a BigDecimal.
}