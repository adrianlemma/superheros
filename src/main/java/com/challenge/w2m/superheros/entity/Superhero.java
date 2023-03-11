package com.challenge.w2m.superheros.entity;

import com.challenge.w2m.superheros.exception.SuperheroException;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.http.HttpStatus;

@Entity
@Table(name = "superhero")
public class Superhero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer superheroId;

    @Column(name = "name")
    @NotNull(message = "name can not be null")
    @NotBlank(message = "name have to be a valid name")
    private String name;

    @Column(name = "secret_identity")
    @NotNull(message = "secret_identity can not be null")
    @NotBlank(message = "secret identity have to be a valid name")
    private String secretIdentity;

    @ElementCollection
    @CollectionTable(name ="super_power")
    @NotNull(message = "super_powers have to be a list")
    private List<String> superPowers;

    public Superhero() { }

    public Superhero(Integer superheroId, String name, String secretIdentity, List<String> superPowers) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (!validName(name)) {
            throw new SuperheroException("name", stringBuilder.append("invalid value [")
                    .append(name).append("]").toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (!validSecretIdentity(secretIdentity)) {
            throw new SuperheroException("secretIdentity", stringBuilder.append("invalid value [")
                    .append(secretIdentity).append("]").toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (!validSuperPowers(superPowers)) {
            throw new SuperheroException("name", "invalid value", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        this.superheroId = superheroId;
        this.name = name;
        this.secretIdentity = secretIdentity;
        this.superPowers = superPowers;
    }

    public Integer getSuperheroId() {
        return superheroId;
    }

    public String getName() {
        return name;
    }

    public String getSecretIdentity() {
        return secretIdentity;
    }

    public List<String> getSuperPowers() {
        return superPowers;
    }

    public void setSuperheroId(Integer superheroId) {
        this.superheroId = superheroId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSecretIdentity(String secretIdentity) {
        this.secretIdentity = secretIdentity;
    }

    public void setSuperPowers(List<String> superPowers) {
        this.superPowers = superPowers;
    }

    private boolean validName(String name) {
        return name != null && !name.isBlank();
    }

    private boolean validSecretIdentity(String secretIdentity) {
        return secretIdentity != null && !secretIdentity.isBlank();
    }

    private boolean validSuperPowers(List<String> superPowers) {
        if (superPowers == null) {
            return false;
        }
        AtomicBoolean result = new AtomicBoolean(true);
        superPowers.forEach(superPower -> {
            if (superPower == null || superPower.isBlank())
                result.set(false);
        });
        return result.get();
    }

}
