package org.dirimo.biblioteca.resources.customer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Transactional
@RestController
@RequiredArgsConstructor
@RequestMapping("Customer")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/")
    public List<Customer> getAll() {

        return customerService.getAll();
    }

    @GetMapping("/{id}")
    public Customer getById(@PathVariable Long id) {
        return customerService.getById(id)
                .orElseThrow(() -> new RuntimeException("Prenotazione con Id " + id + " non trovata."));
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer create(@RequestBody Customer customer) {
        return customerService.create(customer);
    }

    @PostMapping("/bulk")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Customer> createBulk(@RequestBody List<Customer> customers) {
        return customerService.createBulk(customers);
    }


    @PutMapping("/{id}")
    public Customer update(@PathVariable Long id, @RequestBody Customer customer) {
        return customerService.update(id, customer);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

        customerService.delete(id);
    }
}
