package com.hkt.btu.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hkt.btu.web.rest.TestUtil;

public class StockItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockItem.class);
        StockItem stockItem1 = new StockItem();
        stockItem1.setId(1L);
        StockItem stockItem2 = new StockItem();
        stockItem2.setId(stockItem1.getId());
        assertThat(stockItem1).isEqualTo(stockItem2);
        stockItem2.setId(2L);
        assertThat(stockItem1).isNotEqualTo(stockItem2);
        stockItem1.setId(null);
        assertThat(stockItem1).isNotEqualTo(stockItem2);
    }
}
