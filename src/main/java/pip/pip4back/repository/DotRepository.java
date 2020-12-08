package pip.pip4back.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pip.pip4back.model.Dot;
import pip.pip4back.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface DotRepository extends CrudRepository<Dot, Long> {

    List<Dot> findAllByUserId(User user);

}
