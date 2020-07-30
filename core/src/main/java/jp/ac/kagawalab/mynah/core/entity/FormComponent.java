package jp.ac.kagawalab.mynah.core.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "form_components")
public class FormComponent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false, referencedColumnName = "id")
    private FormType type;

    private String value;

    private String describing;  // 説明項

    @Column(nullable = false)
    private String paramName;

    @Column(columnDefinition = "boolean DEFAULT true")
    private boolean isRequired;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false, referencedColumnName = "id")
    private Board owner;

    @Column(nullable = false)
    private int orderNumber;  // 表示順序
}
