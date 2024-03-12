package sample.cafekiosk.spring.domain.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.IntegrationTestSupport;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.HOLD;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static sample.cafekiosk.spring.domain.product.ProductType.*;

//@ActiveProfiles("test")
//@DataJpaTest
@Transactional
class OrderRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @DisplayName("특정날짜에 결제완료 된 주문을 모두 조회한다.")
    @Test
    void test() {
        // given
        Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        Product product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 4500);
        productRepository.saveAll(List.of(product1, product2));

        LocalDateTime now = LocalDateTime.now();
        Order order = Order.create(List.of(product1, product2), now);
        Order updatedOrder = order.updateStatus(OrderStatus.PAYMENT_COMPLETED);

        Order saved = orderRepository.save(updatedOrder);

        // when
        List<Order> orders = orderRepository.findOrdersBy(
                saved.getRegisteredDateTime().toLocalDate().atStartOfDay(),
                saved.getRegisteredDateTime().toLocalDate().plusDays(1).atStartOfDay(),
                saved.getOrderStatus()
        );

        // then
        assertThat(orders).hasSize(1)
                .extracting("OrderStatus")
                .contains(OrderStatus.PAYMENT_COMPLETED);
    }

    private Product createProduct(String productNumber, ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .type(type)
                .sellingStatus(sellingStatus)
                .name(name)
                .price(price)
                .build();
    }
}