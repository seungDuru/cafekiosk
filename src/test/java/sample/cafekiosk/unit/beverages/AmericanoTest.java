package sample.cafekiosk.unit.beverages;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class AmericanoTest {

    @Test
    void getName() {
        // given
        Americano americano = new Americano();

        // then
        assertEquals(americano.getName(), "아메리카노");
        assertThat(americano.getName()).isEqualTo("아메리카노");
    }

    @Test
    void test() {
        // given
        Americano americano = new Americano();

        // then
        assertThat(americano.getPrice()).isEqualTo(4000);
    }
}