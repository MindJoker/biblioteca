package org.dirimo.biblioteca.resources.stock;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dirimo.biblioteca.resources.book.Book;


@Entity
@Table(name = "stock")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stock {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    @Column(name = "tot_copies",nullable = false, columnDefinition = "int default 1 not null")
    private int totCopies;

    @Column(name = "av_copies", nullable = false, columnDefinition = "int default 1 not null")
    private int avCopies;
}
