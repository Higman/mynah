package jp.ac.kagawalab.mynah.core.entity;

import jp.ac.kagawalab.mynah.core.dto.model.RoleDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
@Table(name = "roles")
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "role_name", unique = true, nullable = false)
    private String role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (o instanceof RoleDto) return this.role.equals(((RoleDto) o).toString());
        if (getClass() != o.getClass()) return false;

        Role role1 = (Role) o;

        return role != null ? role.equals(role1.role) : role1.role == null;
    }

    @Override
    public int hashCode() {
        return role != null ? role.hashCode() : 0;
    }
}
