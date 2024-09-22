package co.allconnected.fussiontech.organizationsservice.repository;

import co.allconnected.fussiontech.organizationsservice.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrganizationRepository extends JpaRepository<Organization, String> {

}