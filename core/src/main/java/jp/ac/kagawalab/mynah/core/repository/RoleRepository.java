package jp.ac.kagawalab.mynah.core.repository;

import jp.ac.kagawalab.mynah.core.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRole(String roleName);
}
