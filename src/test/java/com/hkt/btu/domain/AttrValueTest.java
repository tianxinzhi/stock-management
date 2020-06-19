package com.hkt.btu.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hkt.btu.web.rest.TestUtil;

public class AttrValueTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttrValue.class);
        AttrValue attrValue1 = new AttrValue();
        attrValue1.setId(1L);
        AttrValue attrValue2 = new AttrValue();
        attrValue2.setId(attrValue1.getId());
        assertThat(attrValue1).isEqualTo(attrValue2);
        attrValue2.setId(2L);
        assertThat(attrValue1).isNotEqualTo(attrValue2);
        attrValue1.setId(null);
        assertThat(attrValue1).isNotEqualTo(attrValue2);
    }
}
