package jp.ac.kagawalab.mynah.core.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@EqualsAndHashCode
@Setter
@Getter
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "recruitment_details")
public class RecruitmentDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private FormComponent formComponent;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id", referencedColumnName = "id", nullable = false)
    private Recruitment owner;

    private String value;
}
