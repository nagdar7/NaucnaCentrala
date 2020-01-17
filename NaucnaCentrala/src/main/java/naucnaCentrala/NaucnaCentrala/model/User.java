package naucnaCentrala.NaucnaCentrala.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ime;

    @Column(nullable = false)
    private String prezime;

    @Column(nullable = false)
    private String grad;

    @Column(nullable = false)
    private String drzava;

    @Column(nullable = true)
    private String titula;

    @Column(nullable = false)
    private String email;

    // @Column(nullable = true)
    // private List<String> naucneOblasti;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    // @Column(nullable = false)
    // private String role;

    @Column(nullable = false)
    private Boolean activated;

    @Column(nullable = false)
    private Boolean recezent;

    public User() {
        activated = false;
    }

    /**
     * @return Long return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return String return the ime
     */
    public String getIme() {
        return ime;
    }

    /**
     * @param ime the ime to set
     */
    public void setIme(String ime) {
        this.ime = ime;
    }

    /**
     * @return String return the prezime
     */
    public String getPrezime() {
        return prezime;
    }

    /**
     * @param prezime the prezime to set
     */
    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    /**
     * @return String return the grad
     */
    public String getGrad() {
        return grad;
    }

    /**
     * @param grad the grad to set
     */
    public void setGrad(String grad) {
        this.grad = grad;
    }

    /**
     * @return String return the drzava
     */
    public String getDrzava() {
        return drzava;
    }

    /**
     * @param drzava the drzava to set
     */
    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    /**
     * @return String return the titula
     */
    public String getTitula() {
        return titula;
    }

    /**
     * @param titula the titula to set
     */
    public void setTitula(String titula) {
        this.titula = titula;
    }

    /**
     * @return String return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return String return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return String return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return Boolean return the activated
     */
    public Boolean isActivated() {
        return activated;
    }

    /**
     * @param activated the activated to set
     */
    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    /**
     * @return Boolean return the recezent
     */
    public Boolean isRecezent() {
        return recezent;
    }

    /**
     * @param recezent the recezent to set
     */
    public void setRecezent(Boolean recezent) {
        this.recezent = recezent;
    }

}
