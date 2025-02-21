package org.dirimo.biblioteca.resources.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    //Get all customers
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    //Get a customer by ID
    public Optional<Customer>getById(Long id) {
        return customerRepository.findById(id);
    }

    //Create a customer
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    // Update a customer
    public Customer update(Long id, Customer customer) {
        customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prenotazione con id: " + id + " non trovata"));
        return customerRepository.save(customer);
    }

    //Delete a customer
    public void delete(Long id) {
        customerRepository.deleteById(id);
    }

}
