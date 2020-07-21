package jp.ac.kagawalab.mynah.core.repository;

import jp.ac.kagawalab.mynah.core.entity.user.Role;
import jp.ac.kagawalab.mynah.core.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRole(String roleName);
}
