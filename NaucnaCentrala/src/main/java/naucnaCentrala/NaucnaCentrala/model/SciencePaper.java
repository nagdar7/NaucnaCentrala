package naucnaCentrala.NaucnaCentrala.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import naucnaCentrala.NaucnaCentrala.model.enumeration.ScienceField;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SciencePaper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paper_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String keyWords;

    @Column(nullable = false)
    private String paperAbstract;

    @Column
    private ScienceField scienceField;

    @Column(nullable = false)
    private String magazineName;

    @OneToMany(mappedBy = "sciencePaper", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CoAuthor> coAuthors;

    @ManyToMany(mappedBy = "sciencePapers")
    private List<Account> reviewers;

    @Column(nullable = false)
    private String filePath;

}
