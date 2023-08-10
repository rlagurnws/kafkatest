package kafka.user.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import kafka.user.domain.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity,String>{

}
