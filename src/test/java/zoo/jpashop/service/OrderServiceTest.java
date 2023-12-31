package zoo.jpashop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import zoo.jpashop.domain.Member;
import zoo.jpashop.domain.Order;
import zoo.jpashop.domain.OrderStatus;
import zoo.jpashop.domain.item.Book;
import zoo.jpashop.exception.NotEnoughStockException;
import zoo.jpashop.repository.OrderRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    void 상품주문() {
        // given
        Member member = createMember();
        Book book = createBook("JPA", 10000, 10);

        int orderCount = 2;

        // when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // then
        Order order = orderRepository.findOne(orderId);

        assertThat(OrderStatus.ORDER).isEqualTo(order.getStatus());
        assertThat(order.getOrderItems().size()).isEqualTo(1);
        assertThat(order.getTotalPrice()).isEqualTo(20000);
        assertThat(book.getStockQuantity()).isEqualTo(8);
    }

    @Test
    void 주문취소() {
        // given
        Member member = createMember();
        Book book = createBook("JPA", 10000, 10);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // when
        orderService.cancelOrder(orderId);

        // then
        Order order = orderRepository.findOne(orderId);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(book.getStockQuantity()).isEqualTo(10);
    }

    @Test
    void 상품주문_재고수량초과() {
        // given
        Member member = createMember();
        Book book = createBook("JPA", 10000, 10);

        int orderCount = 11;

        // when
        // then
        assertThrows(
                NotEnoughStockException.class,
                () -> orderService.order(member.getId(), book.getId(), orderCount)
        );
    }

    private Book createBook(String name, int price, int quantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(quantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        em.persist(member);
        return member;
    }
}