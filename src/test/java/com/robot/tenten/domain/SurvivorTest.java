package com.robot.tenten.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.robot.tenten.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SurvivorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Survivor.class);
        Survivor survivor1 = new Survivor();
        survivor1.setId(1L);
        Survivor survivor2 = new Survivor();
        survivor2.setId(survivor1.getId());
        assertThat(survivor1).isEqualTo(survivor2);
        survivor2.setId(2L);
        assertThat(survivor1).isNotEqualTo(survivor2);
        survivor1.setId(null);
        assertThat(survivor1).isNotEqualTo(survivor2);
    }
}
