package com.example.webfluxdemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2018/11/30 11:02
 * description:
 */
@RestController
@Slf4j
public class TestController {


    @GetMapping("1")
    public String test1() {
        log.info("start test1");
        String result = getString();
        log.info("end test1");
        return result;
    }

    private String getString() {
        try {
            TimeUnit.SECONDS.sleep(3L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "some time";
    }

    @GetMapping("2")
    public Mono<String> test2() {
        log.info("start test2");
        Mono<String> result = Mono.fromSupplier(this::getString);
        log.info("end test2");
        return result;
    }

    @GetMapping(value = "3", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> test3() {
        log.info("start test3");
        Flux<String> result = Flux.fromStream(IntStream.rangeClosed(1, 4).mapToObj(i -> {
            try {
                TimeUnit.SECONDS.sleep(3L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "flux data --" + i;

        }));
        log.info("end test3");
        return result;
    }
}
