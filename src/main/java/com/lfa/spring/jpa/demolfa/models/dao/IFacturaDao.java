package com.lfa.spring.jpa.demolfa.models.dao;

import com.lfa.spring.jpa.demolfa.models.entity.Factura;
import org.springframework.data.repository.CrudRepository;

public interface IFacturaDao extends CrudRepository<Factura, Long> {
}
