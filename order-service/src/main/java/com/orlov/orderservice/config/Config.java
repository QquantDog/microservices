package com.orlov.orderservice.config;


import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Config {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT);

//        Converter<List<OrderItem>, List<OrderItemDto>> orderItemConverter = new Converter<>(){
//            @Override
//            public List<OrderItemDto> convert(MappingContext<List<OrderItem>, List<OrderItemDto>> mappingContext) {
//                List<OrderItem> from = mappingContext.getSource();
//                List<OrderItemDto> to = mappingContext.getDestination();
//                for (OrderItem orderItem : from) {
//                    to.add(modelMapper.map(orderItem, OrderItemDto.class));
//                }
//                return to;
//            }
//        };
//        modelMapper.addConverter(orderItemConverter);

        return modelMapper;
    }
}
