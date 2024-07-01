package com.proposta.proposta_app.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Value("${rabbitmq.propostapendente.exchange}")
    private String exchangePropostaPendente;

    @Value("${rabbitmq.propostaconcluida.exchange}")
    private String exchangePropostaConcluida;

    //    Gerenciar permisões spring-rabbit
    @Bean
    public RabbitAdmin criarRabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializarAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

//    Criar filas pendentes e concluídas

    @Bean
    public Queue criarFilaPropostaPendenteMsAnaliseCredito() {
        return QueueBuilder.durable("proposta-pendente.ms-analise-credito")
                .ttl(10000)
                .deadLetterExchange("proposta-pendente-dlx.ex")
                .build();
    }

    @Bean
    public Queue criarFilaPropostaPendenteMsNotificacao() {
        return QueueBuilder.durable("proposta-pendente.ms-notificacao").build();
    }

    @Bean
    public Queue criarFilaPropostaConcluidaMsProposta() {
        return QueueBuilder.durable("proposta-concluida.ms-proposta").build();
    }

    @Bean
    public Queue criarFilaPropostaConcluidaMsNotificacao() {
        return QueueBuilder.durable("proposta-concluida.ms-notificacao").build();
    }

    // Criação de exchanges e bingings
    @Bean
    public FanoutExchange criarFanoutExchangePropostaPendente() {
        return ExchangeBuilder.fanoutExchange(exchangePropostaPendente).build();
    }

    @Bean
    public FanoutExchange criarFanoutExchangePropostaConcluida() {
        return ExchangeBuilder.fanoutExchange(exchangePropostaConcluida).build();
    }

    @Bean
    public Binding criarBindingPropostaPendenteMsAnaliseCredito() {
        return BindingBuilder.bind(criarFilaPropostaPendenteMsAnaliseCredito())
                .to(criarFanoutExchangePropostaPendente());
    }

    @Bean
    public Binding criarBindingPropostaPendenteMsNotificacao() {
        return BindingBuilder.bind(criarFilaPropostaPendenteMsNotificacao())
                .to(criarFanoutExchangePropostaPendente());
    }

    @Bean
    public Binding criarBindingPropostaConcluidaMsPropostaApp() {
        return BindingBuilder.bind(criarFilaPropostaConcluidaMsProposta())
                .to(criarFanoutExchangePropostaConcluida());
    }

    @Bean
    public Binding criarBindingPropostaConcluidaMsNotificacao() {
        return BindingBuilder.bind(criarFilaPropostaConcluidaMsNotificacao())
                .to(criarFanoutExchangePropostaConcluida());
    }

    //    Criar rabbit template com conversor para objetos
    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());

        return rabbitTemplate;
    }

    // Criar Dead Letter Queue

    @Bean
    public Queue criarFilaPropostapendenteDlq() {
        return QueueBuilder.durable("proposta-pendente.dlq").build();
    }

    @Bean
    public FanoutExchange deadLetterExchange() {
        return ExchangeBuilder.fanoutExchange("proposta-pendente-dlx.ex").build();
    }

    @Bean
    public Binding criarBinding() {
        return BindingBuilder.bind(criarFilaPropostapendenteDlq()).to(deadLetterExchange());
    }

}
