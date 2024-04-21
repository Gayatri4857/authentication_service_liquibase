package org.dnyanyog.repo;

import java.util.List;
import org.dnyanyog.entity.Discount;
import org.dnyanyog.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface UsersRepository extends JpaRepository<Users, Long> {

  List<Users> findByUsername(String username);

  List<Users> findByEmail(String email);

  List<Users> findByAge(int age);

  void save(Discount discountEntity);
}
