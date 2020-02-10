package naucnaCentrala.NaucnaCentrala.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ScientificCommittee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scientific_committee_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private Account chiefEditor;

    @OneToMany(mappedBy = "scientificCommittee", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private List<Account> fieldEditors;

}
