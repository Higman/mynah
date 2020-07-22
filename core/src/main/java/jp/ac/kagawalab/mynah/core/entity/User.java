package jp.ac.kagawalab.mynah.core.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

@Entity
@Setter
@Getter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
@Table(name = "users")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(nullable = false)
    private String userName;

    @Column(unique = true)
    private String userId;

    private String password;

    @Column(name = "is_oauth2_user", columnDefinition = "boolean DEFAULT true")
    private boolean isOAuth2User;

    private String provider;

    @Column(unique = true)
    private String providerId;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", insertable = false, updatable = false, columnDefinition = "timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP")
    private Calendar createdAt;
}
