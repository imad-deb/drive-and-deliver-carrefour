package fr.carrefour.kata.stream.kafka;

import fr.carrefour.kata.app.data.dto.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JsonKafkaConsumer {

    @KafkaListener(topics = "${spring.kafka.topic-json.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(CustomerDto client){
        log.info(String.format("Json message recieved -> %s", client.toString()));
    }
}
