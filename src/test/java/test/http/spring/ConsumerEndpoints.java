package test.http.spring;

import org.springframework.web.bind.annotation.*;


public class ConsumerEndpoints {
    @PostMapping(value = "a")
    public void receivesAPostRequest() {
    }

    @GetMapping(value = "b")
    public void receivesAGetRequest() {
    }

    @RequestMapping(path = "c", method = RequestMethod.HEAD)
    public void receivesAHeadRequest() {
    }

    @RequestMapping(value = "d", method = RequestMethod.OPTIONS)
    public void receivesAOptionsRequest() {
    }

    @PutMapping(value = "e")
    public void receivesAPutRequest() {
    }

    @PatchMapping(value = "f")
    public void receivesAPatchRequest() {
    }

    @DeleteMapping(value = "g")
    public void receivesADeleteRequest() {
    }
}
