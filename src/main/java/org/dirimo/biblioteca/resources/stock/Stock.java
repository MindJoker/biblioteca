package org.dirimo.biblioteca.resources.stock;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dirimo.biblioteca.resources.book.Book;
import org.dirimo.biblioteca.common.BaseEntity;

@Entity
@Table(name = "Stocks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", unique = true, nullable = false)
    private Book book;

    @Column(name = "totale_copie", columnDefinition = "int default 1", nullable = false)
    private int totalCopies;

    @Column(name = "copie_disponibili", nullable = false)
    private int availableCopies;

    public void handleQuantity(int quantity) {
        availableCopies += quantity;
    }
}