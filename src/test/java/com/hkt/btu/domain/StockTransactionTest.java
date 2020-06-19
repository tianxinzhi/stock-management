package com.hkt.btu.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hkt.btu.web.rest.TestUtil;

public class StockTransactionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockTransaction.class);
        StockTransaction stockTransaction1 = new StockTransaction();
        stockTransaction1.setId(1L);
        StockTransaction stockTransaction2 = new StockTransaction();
        stockTransaction2.setId(stockTransaction1.getId());
        assertThat(stockTransaction1).isEqualTo(stockTransaction2);
        stockTransaction2.setId(2L);
        assertThat(stockTransaction1).isNotEqualTo(stockTransaction2);
        stockTransaction1.setId(null);
        assertThat(stockTransaction1).isNotEqualTo(stockTransaction2);
    }
}
