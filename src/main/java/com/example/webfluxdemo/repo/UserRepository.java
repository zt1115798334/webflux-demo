package com.example.webfluxdemo.repo;

import com.example.webfluxdemo.entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2018/11/30 13:23
 * description:
 */
public interface UserRepository extends ReactiveMongoRepository<User, String> {

}
