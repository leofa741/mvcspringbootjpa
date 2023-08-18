package com.lfa.spring.jpa.demolfa.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "facturas")
public class Factura implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotEmpty
    private String descripcion;

    private String observacion;


    @Temporal(TemporalType.DATE)
    @Column(name = "create_at")
    private Date createAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "factura_id")
    private List<ItemsFactura> items;

    @PrePersist
    public void prePersist(){
        createAt = new Date();
    }

    public Factura(){
        items = new ArrayList<ItemsFactura>();
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }


    public List<ItemsFactura> getItems() {
        return items;
    }

    public void setItems(List<ItemsFactura> items) {
        this.items = items;
    }

    public void addItemFactura(ItemsFactura item){
        this.items.add(item);
    }

    public Double getTotal(){
        Double total = 0.0;
        int size = items.size();

        for(int i = 0; i < size; i++){
            total += items.get(i).calcularImporte();
        }

        return total;
    }


    private static final long serialVersionUID = 1L;
}
