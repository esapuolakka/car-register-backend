package com.example.autorekisteri;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.AbstractPersistable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class Auto extends AbstractPersistable<Long>{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String rekisterinumero;
  private String merkki;
  private String malli;
  private String valmistusvuosi;
  @ManyToMany(mappedBy = "autot")
  private List<Henkilo> omistajat = new ArrayList<>();

  public String autonTiedot() {
    return rekisterinumero + " / " + merkki + " / " + malli + " / " + valmistusvuosi;
  }
}
