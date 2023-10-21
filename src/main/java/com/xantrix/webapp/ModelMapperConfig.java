package com.xantrix.webapp;

import com.xantrix.webapp.dtos.ArticoliDto;
import com.xantrix.webapp.dtos.BarcodeDto;
import com.xantrix.webapp.entities.Articoli;
import com.xantrix.webapp.entities.Barcode;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.addMappings(articoliMapping);
        /*modelMapper.addMappings(barcodeMapping);*/
        modelMapper.addConverter(articoliConverter);
        return modelMapper;
    }

    PropertyMap<Articoli, ArticoliDto> articoliMapping = new PropertyMap<Articoli,ArticoliDto>(){

        @Override
        protected void configure() {
            map().setDataCreazione(source.getDataCreaz());
        }
    };

   /* PropertyMap<Barcode, BarcodeDto> barcodeMapping = new PropertyMap<Barcode, BarcodeDto>() {
        @Override
        protected void configure() {
            map().setTipo(source.getIdTipoArt());
        }
    };*/

    Converter<String, String> articoliConverter = new Converter<String, String>(){
        @Override
        public String convert(MappingContext<String, String> mappingContext) {
            return mappingContext.getSource() == null ? "" : mappingContext.getSource().trim();
        }
    };

}
