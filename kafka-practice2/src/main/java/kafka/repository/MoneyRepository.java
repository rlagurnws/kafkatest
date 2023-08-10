package kafka.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import kafka.money.Entity.MoneyEntity;

@Repository
public interface MoneyRepository extends CrudRepository<MoneyEntity,String>{

}
