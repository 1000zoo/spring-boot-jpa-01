package zoo.jpashop.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import zoo.jpashop.domain.item.Book;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ItemUpdateTest {

    @Autowired
    EntityManager em;

    @Test
    void updateTest() {
        // given
        Book book = em.find(Book.class, 1L);

        // when

        // then

    }
}
