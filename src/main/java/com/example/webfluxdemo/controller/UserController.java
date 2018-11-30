package com.example.webfluxdemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.webfluxdemo.entity.User;
import com.example.webfluxdemo.repo.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2018/11/30 13:25
 * description:
 */
@RestController
@RequestMapping("user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("findAll")
    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    @GetMapping(value = "/findStreamAll", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> findStreamAll() {
        return userRepository.findAll();
    }

    @PostMapping(value = "/saveUser")
    public Mono<User> saveUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    /**
     * @param id
     * @return 存在返回200  不存在 返回404
     */
    @DeleteMapping(value = "/delete/{id}")
    public Mono<ResponseEntity> deleteUser(@PathVariable("id") String id) {
        return userRepository.findById(id)
                .flatMap(user -> userRepository.delete(user)
                        .then(Mono.just(new ResponseEntity(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @PutMapping(value = "/update/{id}")
    public Mono<ResponseEntity> updateUser(@PathVariable("id")String id,
                                           @RequestBody User user) {
        user.setId(id);
        return userRepository.findById(id)
                .flatMap(u -> {
                    u.setName(user.getName());
                    u.setAge(user.getAge());
                    return userRepository.save(u)
                            .then(Mono.just(new ResponseEntity(HttpStatus.OK)));
                }).defaultIfEmpty(new ResponseEntity(HttpStatus.NOT_FOUND));

    }

    @GetMapping(value = "/find/{id}")
    public Mono<ResponseEntity<User>> findUser(@PathVariable("id")String id) {
        return userRepository.findById(id)
                .map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity(HttpStatus.NOT_FOUND));

    }

    public static void main(String[] args) {
        User user = new User();
        user.setName("小明");
        user.setAge(25);
        System.out.println(JSONObject.toJSON(user));
    }
}
