package br.com.dev.valber.medeiros.controleficancas.config;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class KafkaConfigTest {

    @InjectMocks
    private KafkaConfig kafkaConfig;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(kafkaConfig, "bootstrapAddress", "localhost:9092");
    }

    @Test
    void producerFactory() {
        ProducerFactory<String, String> producerFactory = kafkaConfig.producerFactory();
        Assert.assertNotNull(producerFactory);
    }
}