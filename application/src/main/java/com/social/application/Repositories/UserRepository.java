package com.social.application.Repositories;
import com.social.application.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User, Long>{
}
