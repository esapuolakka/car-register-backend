package com.example.autorekisteri;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AutorekisteriController {

  @Autowired
  private AutoRepository autoRepository;
  @Autowired
  private HenkiloRepository henkiloRepository;


  @GetMapping("/")
  public String home() {
    return "index";
  }

  // Autot endpoints
  @GetMapping("/autot")
  public String autot(Model model) {
    model.addAttribute("autot", autoRepository.findAll());
    return "autot";
  }
  @PostMapping("/autot")
  public String createAuto(@RequestParam String rekisterinumero, @RequestParam String merkki, @RequestParam String malli, @RequestParam String valmistusvuosi) {
    Auto auto = new Auto();
    auto.setRekisterinumero(rekisterinumero);
    auto.setMerkki(merkki);
    auto.setMalli(malli);
    auto.setValmistusvuosi(valmistusvuosi);
    autoRepository.save(auto);
    return "redirect:/autot";
  }
  @GetMapping("/autot/{id}")
  public String getAuto(Model model, @PathVariable Long id) {
    Auto auto = autoRepository.findById(id).orElse(null);
    model.addAttribute("auto", auto);
    model.addAttribute("omistajat", henkiloRepository.findAll());
    return "auto";
  }
  @PostMapping("/autot/{id}/omistaja")
  public String addOmistaja(@PathVariable("id") Long autoId, Long omistajaId) {
    Auto auto = autoRepository.findById(autoId).orElse(null);
    Henkilo omistaja = henkiloRepository.findById(omistajaId).orElse(null);

    if (auto != null && omistaja != null && !omistaja.getAutot().contains(auto)) {
      omistaja.getAutot().add(auto);
      henkiloRepository.save(omistaja);
    }
    return "redirect:/autot/" + autoId;
  }
  @GetMapping("/autot/delete/{id}")
  public String deleteAuto(@PathVariable Long id) {
    autoRepository.deleteById(id);
    return "redirect:/autot";
  }


  // Omistajat endpoints
  @GetMapping("/omistajat")
  public String henkilot(Model model) {
    model.addAttribute("omistajat", henkiloRepository.findAll());
    return "omistajat";
  }
  @PostMapping("/omistajat")
  public String createHenkilo(@RequestParam String etunimi, @RequestParam String sukunimi) {
    Henkilo henkilo = new Henkilo();
    henkilo.setEtunimi(etunimi);
    henkilo.setSukunimi(sukunimi);
    henkiloRepository.save(henkilo);
    return "redirect:/omistajat";
  }
  @GetMapping("/omistajat/{id}")
  public String getHenkilo(Model model, @PathVariable Long id) {
    Henkilo omistaja = henkiloRepository.findById(id).orElse(null);
    model.addAttribute("omistaja", omistaja);
    model.addAttribute("autot", autoRepository.findAll());
    return "omistaja";
  }
  @PostMapping("/omistajat/{id}/auto")
  public String addAuto(@PathVariable("id") Long omistajaId, @RequestParam Long autoId) {
    Auto auto = autoRepository.findById(autoId).orElse(null);
    Henkilo omistaja = henkiloRepository.findById(omistajaId).orElse(null);

    if (auto != null && omistaja != null && !omistaja.getAutot().contains(auto)) {
      omistaja.getAutot().add(auto);
      henkiloRepository.save(omistaja);
    }
    return "redirect:/omistajat/" + omistajaId;
  }
  @GetMapping("/omistajat/delete/{id}")
    public String deleteOmistaja(@PathVariable Long id) {
        henkiloRepository.deleteById(id);
        return "redirect:/omistajat";
    }
}
