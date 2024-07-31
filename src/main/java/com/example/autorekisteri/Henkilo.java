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
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class Henkilo extends AbstractPersistable<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String etunimi;
  private String sukunimi;

  @ManyToMany
  private List<Auto> autot = new ArrayList<>();

  public String kokonimi() {
    return etunimi + " " + sukunimi;
  }
}
