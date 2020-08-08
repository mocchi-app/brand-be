package org.mocchi.brand.configuration

import com.github.daniel.shuy.kafka.protobuf.serde.KafkaProtobufSerializer
import org.apache.kafka.common.serialization.StringSerializer
import org.mocchi.product.v1beta1.ProductOuterClass
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
class KafkaConfiguration {

    @Bean
    fun producerFactory(kafkaProperties: KafkaProperties): ProducerFactory<String, ProductOuterClass.Product> =
            DefaultKafkaProducerFactory(
                    kafkaProperties.buildProducerProperties(),
                    StringSerializer(),
                    KafkaProtobufSerializer()
            )

    @Bean
    fun kafkaTemplate(
            producerFactory: ProducerFactory<String, ProductOuterClass.Product>
    ): KafkaTemplate<String, ProductOuterClass.Product> = KafkaTemplate(producerFactory)
}