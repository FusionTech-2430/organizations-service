package co.allconnected.fussiontech.organizationsservice.repository;

import co.allconnected.fussiontech.organizationsservice.model.UserOrganization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOrganizationRepository extends JpaRepository<UserOrganization, String> {
}
