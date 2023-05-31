package za.co.fnb.coffeshop.repository;

import org.springframework.data.repository.CrudRepository;
import za.co.fnb.coffeshop.model.Drinks;

public interface DrinksRepository extends CrudRepository<Drinks, Long> {
}
