package za.co.fnb.coffeshop.controller;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.boot.actuate.metrics.http.Outcome;
import org.springframework.web.bind.annotation.*;
import za.co.fnb.coffeshop.exceptions.CustomerNotFoundException;
import za.co.fnb.coffeshop.model.Customer;
import za.co.fnb.coffeshop.repository.CustomerRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.springframework.boot.actuate.metrics.http.Outcome.*;


@RestController
@RequestMapping(path = "/coffeeshop")
class CustomerController {
    private final CustomerRepository repository;
    private final MeterRegistry coffeeshopMeterRegistry;

    CustomerController(CustomerRepository repository, MeterRegistry coffeeshopMeterRegistry) {
        this.repository = repository;
        this.coffeeshopMeterRegistry = coffeeshopMeterRegistry;
    }


    @GetMapping("/customer")
    List<Customer> all() {
        Timer coffeeshopTimer = coffeeshopMeterRegistry.timer("retrieve_customer_details_timer");
        coffeeshopTimer.record(Instant.now().toEpochMilli(), TimeUnit.MILLISECONDS);

        return (List<Customer>) repository.findAll();
    }

    @PostMapping("/addcustomer")
    Customer newCustomer(@RequestBody Customer newCustomer) {
        Timer coffeeshopTimer = coffeeshopMeterRegistry.timer("retrieve_add_customer_details_timer");
        coffeeshopTimer.record(Instant.now().toEpochMilli(), TimeUnit.MILLISECONDS);

        return repository.save(newCustomer);
    }


    @GetMapping("/customer/{id}")
    Customer one(@PathVariable Long id) {
        Timer coffeeshopTimer = coffeeshopMeterRegistry.timer("retrieve_one_customer_details_timer");
        coffeeshopTimer.record(Instant.now().toEpochMilli(), TimeUnit.MILLISECONDS);

        return repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @GetMapping("/customer/name/{name}")
    Outcome name(@PathVariable String name) {
        Timer coffeeshopTimer = coffeeshopMeterRegistry.timer("retrieve_one_customer_details_timer");
        coffeeshopTimer.record(Instant.now().toEpochMilli(), TimeUnit.MILLISECONDS);

        try {
            List<Customer> customer = repository.findByName(name);

            return SUCCESS;
        }
        catch (Exception e){
            return SERVER_ERROR;
        }

    }

    @PutMapping("/customer/replace/{id}")
    Customer replaceCustomer(@RequestBody Customer newCustomer, @PathVariable Long id) {
        Timer coffeeshopTimer = coffeeshopMeterRegistry.timer("retrieve_replace_customer_details_timer");
        coffeeshopTimer.record(Instant.now().toEpochMilli(), TimeUnit.MILLISECONDS);

        return repository.findById(id)
                .map(customer -> {
                    customer.setName(newCustomer.getName());
                    return repository.save(customer);
                })
                .orElseGet(() -> {
                    newCustomer.setId(id);
                    return repository.save(newCustomer);
                });
    }

    @DeleteMapping("/customer/delete/{id}")
    void deleteCustomer(@PathVariable Long id) {
        Timer coffeeshopTimer = coffeeshopMeterRegistry.timer("retrieve_delete_customer_details_timer");
        coffeeshopTimer.record(Instant.now().toEpochMilli(), TimeUnit.MILLISECONDS);

        repository.deleteById(id);
        Optional<Customer> customer = repository.findById(id);
        System.out.printf("%s deleted", customer);
    }
}