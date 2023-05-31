package za.co.fnb.coffeshop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import za.co.fnb.coffeshop.model.Bakery;
import za.co.fnb.coffeshop.model.Customer;

@Repository
public interface BakeryRepository extends CrudRepository<Bakery, Long> {
}