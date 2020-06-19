package com.hkt.btu.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hkt.btu.web.rest.TestUtil;

public class AttrTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Attr.class);
        Attr attr1 = new Attr();
        attr1.setId(1L);
        Attr attr2 = new Attr();
        attr2.setId(attr1.getId());
        assertThat(attr1).isEqualTo(attr2);
        attr2.setId(2L);
        assertThat(attr1).isNotEqualTo(attr2);
        attr1.setId(null);
        assertThat(attr1).isNotEqualTo(attr2);
    }
}
