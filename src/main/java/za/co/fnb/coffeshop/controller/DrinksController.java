package za.co.fnb.coffeshop.controller;


import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import za.co.fnb.coffeshop.exceptions.ProductNotFoundException;
import za.co.fnb.coffeshop.model.Drinks;
import za.co.fnb.coffeshop.repository.DrinksRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping(path = "coffeeshop")
public class DrinksController {

    private final DrinksRepository repository;
    private final MeterRegistry coffeeshopMeterRegistry;


    DrinksController(DrinksRepository repository, MeterRegistry coffeeshopMeterRegistry){
        this.repository = repository;
        this.coffeeshopMeterRegistry = coffeeshopMeterRegistry;
    }

    @GetMapping("/drinks")
    List<Drinks> all(){
        Timer coffeeshopTimer = coffeeshopMeterRegistry.timer("retrieve_drinks_details_timer");
        coffeeshopTimer.record(Instant.now().toEpochMilli(), TimeUnit.MILLISECONDS);

        return (List<Drinks>) repository.findAll();
    }

    @GetMapping("/drinks/{id}")
    Drinks oneDrink(@PathVariable Long id){
        Timer coffeeshopTimer = coffeeshopMeterRegistry.timer("retrieve_one_drinks_details_timer");
        coffeeshopTimer.record(Instant.now().toEpochMilli(), TimeUnit.MILLISECONDS);

        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @PostMapping("/drinks/add")
    Drinks newDrink(@RequestBody Drinks newDrinks) {
        Timer coffeeshopTimer = coffeeshopMeterRegistry.timer("retrieve_new_drinks_details_timer");
        coffeeshopTimer.record(Instant.now().toEpochMilli(), TimeUnit.MILLISECONDS);

        return repository.save(newDrinks);
    }

    @PutMapping("/drinks/replace/{id}")
    Drinks replaceProduct(@RequestBody Drinks newDrinks, @PathVariable Long id) {
        Timer coffeeshopTimer = coffeeshopMeterRegistry.timer("retrieve_replace_drinks_details_timer");
        coffeeshopTimer.record(Instant.now().toEpochMilli(), TimeUnit.MILLISECONDS);

        return repository.findById(id)
                .map(employee -> {
                    employee.setName(newDrinks.getName());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newDrinks.setId(id);
                    return repository.save(newDrinks);
                });
    }

    @DeleteMapping("/drinks/delete/{id}")
    void deleteProduct(@PathVariable Long id) {
        Timer coffeeshopTimer = coffeeshopMeterRegistry.timer("retrieve_delete_drinks_details_timer");
        coffeeshopTimer.record(Instant.now().toEpochMilli(), TimeUnit.MILLISECONDS);

        repository.deleteById(id);
        Optional<Drinks> product = repository.findById(id);
        System.out.printf("%s deleted", product);
    }

}
