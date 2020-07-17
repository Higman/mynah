package jp.ac.kagawalab.mynah.core.entity.board;

import jp.ac.kagawalab.mynah.core.entity.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

@Entity
@Data
@Accessors(chain = true)
@NoArgsConstructor
@Table(name = "recruitment")
public class Recruitment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User recruiter;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false, referencedColumnName = "id")
    private Board publisher;

    @JoinColumn(name = "is_recruiting", columnDefinition = "bool DEFAULT true")
    private boolean isRecruiting;

    @Column(name = "detail")
    private String detail;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", columnDefinition = "timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP")
    private Calendar createdAt;
}
