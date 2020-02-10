package naucnaCentrala.NaucnaCentrala.model;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Where;
import naucnaCentrala.NaucnaCentrala.model.enumeration.ScienceField;

import javax.persistence.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String country;

    @Column
    private String title;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false)
    private Boolean reviewer;

    @ElementCollection(targetClass = ScienceField.class)
    @JoinTable(name = "account_science_fields", joinColumns = @JoinColumn(name = "account_id"))
    @Column(name = "science_fields", nullable = false)
    @Enumerated(EnumType.STRING)
    private Collection<ScienceField> scienceFieldList;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "account_authority", joinColumns = {
            @JoinColumn(name = "account_id", referencedColumnName = "account_id") }, inverseJoinColumns = {
                    @JoinColumn(name = "authority_name", referencedColumnName = "name") })
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<Authority> authorities = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private ScientificCommittee scientificCommittee;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private Magazine reviewingMagazines;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "reviewer_paper", joinColumns = @JoinColumn(name = "account_id"), inverseJoinColumns = @JoinColumn(name = "paper_id"))
    List<SciencePaper> sciencePapers;

    public Account() {
    }

    public Account(Long id, String username, String password, String firstName, String lastName, String city,
            String country, String title, String email, Boolean active, Boolean reviewer,
            Collection<ScienceField> scienceFieldList, Set<Authority> authorities,
            ScientificCommittee scientificCommittee, Magazine reviewingMagazines, List<SciencePaper> sciencePapers) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.country = country;
        this.title = title;
        this.email = email;
        this.active = active;
        this.reviewer = reviewer;
        this.scienceFieldList = scienceFieldList;
        this.authorities = authorities;
        this.scientificCommittee = scientificCommittee;
        this.reviewingMagazines = reviewingMagazines;
        this.sciencePapers = sciencePapers;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isActive() {
        return this.active;
    }

    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean isReviewer() {
        return this.reviewer;
    }

    public Boolean getReviewer() {
        return this.reviewer;
    }

    public void setReviewer(Boolean reviewer) {
        this.reviewer = reviewer;
    }

    public Collection<ScienceField> getScienceFieldList() {
        return this.scienceFieldList;
    }

    public void setScienceFieldList(Collection<ScienceField> scienceFieldList) {
        this.scienceFieldList = scienceFieldList;
    }

    public Set<Authority> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public ScientificCommittee getScientificCommittee() {
        return this.scientificCommittee;
    }

    public void setScientificCommittee(ScientificCommittee scientificCommittee) {
        this.scientificCommittee = scientificCommittee;
    }

    public Magazine getReviewingMagazines() {
        return this.reviewingMagazines;
    }

    public void setReviewingMagazines(Magazine reviewingMagazines) {
        this.reviewingMagazines = reviewingMagazines;
    }

    public List<SciencePaper> getSciencePapers() {
        return this.sciencePapers;
    }

    public void setSciencePapers(List<SciencePaper> sciencePapers) {
        this.sciencePapers = sciencePapers;
    }

    public Account id(Long id) {
        this.id = id;
        return this;
    }

    public Account username(String username) {
        this.username = username;
        return this;
    }

    public Account password(String password) {
        this.password = password;
        return this;
    }

    public Account firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Account lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Account city(String city) {
        this.city = city;
        return this;
    }

    public Account country(String country) {
        this.country = country;
        return this;
    }

    public Account title(String title) {
        this.title = title;
        return this;
    }

    public Account email(String email) {
        this.email = email;
        return this;
    }

    public Account active(Boolean active) {
        this.active = active;
        return this;
    }

    public Account reviewer(Boolean reviewer) {
        this.reviewer = reviewer;
        return this;
    }

    public Account scienceFieldList(Collection<ScienceField> scienceFieldList) {
        this.scienceFieldList = scienceFieldList;
        return this;
    }

    public Account authorities(Set<Authority> authorities) {
        this.authorities = authorities;
        return this;
    }

    public Account scientificCommittee(ScientificCommittee scientificCommittee) {
        this.scientificCommittee = scientificCommittee;
        return this;
    }

    public Account reviewingMagazines(Magazine reviewingMagazines) {
        this.reviewingMagazines = reviewingMagazines;
        return this;
    }

    public Account sciencePapers(List<SciencePaper> sciencePapers) {
        this.sciencePapers = sciencePapers;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Account)) {
            return false;
        }
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(username, account.username)
                && Objects.equals(password, account.password) && Objects.equals(firstName, account.firstName)
                && Objects.equals(lastName, account.lastName) && Objects.equals(city, account.city)
                && Objects.equals(country, account.country) && Objects.equals(title, account.title)
                && Objects.equals(email, account.email) && Objects.equals(active, account.active)
                && Objects.equals(reviewer, account.reviewer)
                && Objects.equals(scienceFieldList, account.scienceFieldList)
                && Objects.equals(authorities, account.authorities)
                && Objects.equals(scientificCommittee, account.scientificCommittee)
                && Objects.equals(reviewingMagazines, account.reviewingMagazines)
                && Objects.equals(sciencePapers, account.sciencePapers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, firstName, lastName, city, country, title, email, active, reviewer,
                scienceFieldList, authorities, scientificCommittee, reviewingMagazines, sciencePapers);
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", username='" + getUsername() + "'" + ", password='" + getPassword()
                + "'" + ", firstName='" + getFirstName() + "'" + ", lastName='" + getLastName() + "'" + ", city='"
                + getCity() + "'" + ", country='" + getCountry() + "'" + ", title='" + getTitle() + "'" + ", email='"
                + getEmail() + "'" + ", active='" + isActive() + "'" + ", reviewer='" + isReviewer() + "'"
                + ", scienceFieldList='" + getScienceFieldList() + "'" + ", authorities='" + getAuthorities() + "'"
                + ", scientificCommittee='" + getScientificCommittee() + "'" + ", reviewingMagazines='"
                + getReviewingMagazines() + "'" + ", sciencePapers='" + getSciencePapers() + "'" + "}";
    }

}
