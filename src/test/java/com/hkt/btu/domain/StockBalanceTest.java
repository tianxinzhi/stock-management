package com.hkt.btu.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hkt.btu.web.rest.TestUtil;

public class StockBalanceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockBalance.class);
        StockBalance stockBalance1 = new StockBalance();
        stockBalance1.setId(1L);
        StockBalance stockBalance2 = new StockBalance();
        stockBalance2.setId(stockBalance1.getId());
        assertThat(stockBalance1).isEqualTo(stockBalance2);
        stockBalance2.setId(2L);
        assertThat(stockBalance1).isNotEqualTo(stockBalance2);
        stockBalance1.setId(null);
        assertThat(stockBalance1).isNotEqualTo(stockBalance2);
    }
}
