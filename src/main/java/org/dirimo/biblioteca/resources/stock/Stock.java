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
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID", unique = true, nullable = false)
    private Book book;

    @Column(name = "TOTALE_COPIE", columnDefinition = "int default 1", nullable = false)
    private int totalCopies;

    @Column(name = "COPIE_DISPONIBILI", nullable = false)
    private int availableCopies;

    public void handleQuantity(int quantity) {
        availableCopies += quantity;
    }
}