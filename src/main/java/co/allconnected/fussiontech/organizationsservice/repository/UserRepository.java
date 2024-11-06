package co.allconnected.fussiontech.organizationsservice.repository;

import co.allconnected.fussiontech.organizationsservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
