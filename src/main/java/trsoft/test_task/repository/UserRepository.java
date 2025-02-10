package trsoft.test_task.repository;

import org.springframework.data.repository.CrudRepository;
import trsoft.test_task.model.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findAll();
}
