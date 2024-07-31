package com.example.autorekisteri;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AutorekisteriApplicationTests {
	@Mock
  private AutoRepository autoRepository;
	@Mock
  private HenkiloRepository henkiloRepository;

	@Test
		void addOmistaja_existingAutoAndOmistaja_omistajaAddedToAuto() {
    // given
    Long autoId = 1L;
    Long omistajaId = 2L;
    Henkilo omistaja = new Henkilo();
    omistaja.setId(omistajaId);
    Auto auto = new Auto();
    auto.setId(autoId);
    auto.getOmistajat().add(omistaja);
    when(henkiloRepository.findById(omistajaId)).thenReturn(Optional.of(omistaja));
    when(autoRepository.findById(autoId)).thenReturn(Optional.of(auto));

    // when
    String redirectUrl = controller.addOmistaja(autoId, omistajaId);

    // then
    verify(autoRepository, times(1)).save(auto);
    assertThat(redirectUrl).isEqualTo("redirect:/autot/" + autoId);
}
}
