package za.co.fnb.coffeshop.controller;


import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.web.bind.annotation.*;
import za.co.fnb.coffeshop.exceptions.ProductNotFoundException;
import za.co.fnb.coffeshop.model.Bakery;
import za.co.fnb.coffeshop.repository.BakeryRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(path = "coffeeshop")
public class BakeryController {

    private final BakeryRepository repository;
    private final MeterRegistry coffeeshopMeterRegistry;


    BakeryController(BakeryRepository repository, MeterRegistry coffeeshopMeterRegistry){
        this.repository = repository;
        this.coffeeshopMeterRegistry = coffeeshopMeterRegistry;
    }

    @GetMapping("/bakery")
    List<Bakery> all(){
        Timer coffeeshopTimer = coffeeshopMeterRegistry.timer("retrieve_bakery_details_timer");
        coffeeshopTimer.record(Instant.now().toEpochMilli(), TimeUnit.MILLISECONDS);

        return (List<Bakery>) repository.findAll();
    }

    @GetMapping("/bakery/{id}")
    Bakery bakeryGood(@PathVariable Long id){
        Timer coffeeshopTimer = coffeeshopMeterRegistry.timer("retrieve_one_bakery_details_timer");
        coffeeshopTimer.record(Instant.now().toEpochMilli(), TimeUnit.MILLISECONDS);

        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @PostMapping("/bakery/add")
    Bakery newBakeryGood(@RequestBody Bakery newBakeryGood) {
        Timer coffeeshopTimer = coffeeshopMeterRegistry.timer("retrieve_new_bakery_details_timer");
        coffeeshopTimer.record(Instant.now().toEpochMilli(), TimeUnit.MILLISECONDS);

        return repository.save(newBakeryGood);
    }

    @PutMapping("/bakery/replace/{id}")
    Bakery replaceBakeryGood(@RequestBody Bakery newBakeryGood, @PathVariable Long id) {
        Timer coffeeshopTimer = coffeeshopMeterRegistry.timer("retrieve_replace_bakery_details_timer");
        coffeeshopTimer.record(Instant.now().toEpochMilli(), TimeUnit.MILLISECONDS);

        return repository.findById(id)
                .map(employee -> {
                    employee.setName(newBakeryGood.getName());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newBakeryGood.setId(id);
                    return repository.save(newBakeryGood);
                });
    }

    @DeleteMapping("/bakery/delete/{id}")
    void deleteBakeryGood(@PathVariable Long id) {
        Timer coffeeshopTimer = coffeeshopMeterRegistry.timer("retrieve_delete_bakery_details_timer");
        coffeeshopTimer.record(Instant.now().toEpochMilli(), TimeUnit.MILLISECONDS);

        repository.deleteById(id);
        Optional<Bakery> product = repository.findById(id);
        System.out.printf("%s deleted", product);
    }

}
