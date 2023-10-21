package com.xantrix.webapp.services;

import com.xantrix.webapp.dtos.IvaDto;
import com.xantrix.webapp.entities.Iva;
import com.xantrix.webapp.repository.IvaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Service
@Transactional()
public class IvaServiceImpl implements IvaService{

    @Autowired
    IvaRepository ivaRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<IvaDto> selTutti() {
       List<Iva> ivaList = ivaRepository.findAll();
       return ivaList
               .stream()
               .map(source -> modelMapper.map(source, IvaDto.class))
               .collect(Collectors.toList());
    }
}
