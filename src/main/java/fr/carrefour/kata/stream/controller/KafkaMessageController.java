package fr.carrefour.kata.stream.controller;

import fr.carrefour.kata.app.data.dto.CustomerDto;
import fr.carrefour.kata.core.utils.Constants;
import fr.carrefour.kata.stream.kafka.JsonKafkaProducer;
import fr.carrefour.kata.stream.kafka.KafkaProducer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.KAFKA_ENDPOINT)
@AllArgsConstructor
@Slf4j
public class KafkaMessageController {

    private final KafkaProducer kafkaProducer;
    private final JsonKafkaProducer jsonkafkaProducer;

    @GetMapping("/read")
    public ResponseEntity<String> publish(@RequestParam("message") String message){
        kafkaProducer.sendMessage(message);
        log.info("Message sent to the topic");
        return ResponseEntity.ok("Message sent to the topic");
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publish(@RequestBody CustomerDto customerDto){
        jsonkafkaProducer.sendMessage(customerDto);
        log.info("Json message sent to kafka topic");
        return ResponseEntity.ok("Json message sent to kafka topic");
    }
}
