package naucnaCentrala.NaucnaCentrala.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import naucnaCentrala.NaucnaCentrala.model.enumeration.BillingType;
import naucnaCentrala.NaucnaCentrala.model.enumeration.ScienceField;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collection;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Magazine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "magazine_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(unique = true)
    private String issn;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BillingType billingType;

    @ElementCollection(targetClass = ScienceField.class)
    @JoinTable(name = "magazine_science_fields", joinColumns = @JoinColumn(name = "magazine_id"))
    @Column(name = "science_fields", nullable = false)
    @Enumerated(EnumType.STRING)
    private Collection<ScienceField> scienceFieldList;

    @JsonIgnore
    @OneToOne
    private ScientificCommittee scientificCommittee;

    @OneToMany(mappedBy = "reviewingMagazines", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private List<Account> reviewers;

    @ManyToMany
    @JoinTable(name = "open_access_authors", joinColumns = @JoinColumn(name = "magazine_id"), inverseJoinColumns = @JoinColumn(name = "account_id"))
    private List<Account> openAccessAuthors;
}
