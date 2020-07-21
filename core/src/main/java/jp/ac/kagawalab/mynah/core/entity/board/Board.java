package jp.ac.kagawalab.mynah.core.entity.board;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

@Entity
@Data
@Accessors(chain = true)
@NoArgsConstructor
@Table(name = "boards")
public class Board implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "topic")
    private String topic;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Recruitment> recruitmentList;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", insertable = false, updatable = false, columnDefinition = "timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP")
    private Calendar createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_published_from", columnDefinition = "timestamp with time zone NOT NULL")
    private Calendar dataPublishedFrom;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_published_to", columnDefinition = "timestamp with time zone NOT NULL")
    private Calendar dataPublishedTo;
}
