package com.lfa.spring.jpa.demolfa.models.dao;

import com.lfa.spring.jpa.demolfa.models.entity.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;



public interface IClienteDao extends CrudRepository<Cliente, Long> {


}
