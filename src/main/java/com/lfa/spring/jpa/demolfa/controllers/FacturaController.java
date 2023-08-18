package com.lfa.spring.jpa.demolfa.controllers;


import com.lfa.spring.jpa.demolfa.models.entity.Cliente;
import com.lfa.spring.jpa.demolfa.models.entity.Factura;
import com.lfa.spring.jpa.demolfa.models.entity.ItemsFactura;
import com.lfa.spring.jpa.demolfa.models.entity.Producto;
import com.lfa.spring.jpa.demolfa.models.services.IClienteService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {

    @Autowired
    private IClienteService clienteService;


    @GetMapping("/form/{clienteId}")
    public String crear(@PathVariable(value = "clienteId") Long clienteId, Map<String, Object> model, RedirectAttributes flash){

        Cliente cliente = clienteService.findOne(clienteId);
        if(cliente == null){
            flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
            return "redirect:/listar";
        }

        Factura factura = new Factura();
        factura.setCliente(cliente);

        model.put("factura", factura);
        model.put("titulo", "Crear Factura");
        return "factura/form";
    }

    @GetMapping(value = "/cargar-productos/{term}", produces = {"application/json"})
    public @ResponseBody List<Producto> cargarProductos(@PathVariable String term){
        return clienteService.findByNombre(term);
    }

    @PostMapping("/form")
    public String guardar(@Valid Factura factura, BindingResult result,
                          @RequestParam(name = "item_id[]", required = false) Long[] itemId,
                          @RequestParam(name = "cantidad[]", required = false) Integer[] cantidad,
                          RedirectAttributes flash){

        if(result.hasErrors()){
            flash.addFlashAttribute("error", "Error al crear la factura");
            return "factura/form";
        }

        if(itemId == null || itemId.length == 0){

            flash.addFlashAttribute("error", "La factura no puede no tener líneas");
            return "redirect:/ver/" + factura.getCliente().getId();
        }

        for(int i = 0; i < itemId.length; i++){
            Producto producto = clienteService.findProductoById(itemId[i]);
            ItemsFactura linea = new ItemsFactura();

            linea.setCantidad(cantidad[i]);
            linea.setProducto(producto);
            factura.addItemFactura(linea);

        }

        clienteService.saveFactura(factura);
        flash.addFlashAttribute("success", "Factura creada con éxito");


        return "redirect:/ver/" + factura.getCliente().getId();

    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash){

        Factura factura = clienteService.findFacturaById(id);
        if(factura == null){
            flash.addFlashAttribute("error", "La factura no existe en la base de datos");
            return "redirect:/listar";
        }

        model.put("factura", factura);
        model.put("titulo", "Factura: " + factura.getDescripcion());

        return "factura/ver";
    }




}
