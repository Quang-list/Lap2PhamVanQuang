package org.example.controller;

import org.example.dto.ManufacturerDTO;
import org.example.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manufacturers")
public class ManufacturerController {
    @Autowired
    private ManufacturerService service;

    @GetMapping
    public List<ManufacturerDTO> getAllManufacturers() {
        return service.getAllManufacturers();
    }

    @PostMapping
    public ManufacturerDTO createManufacturer(@RequestBody ManufacturerDTO dto) {
        return service.createManufacturer(dto);
    }

    @PutMapping("/{id}")
    public ManufacturerDTO updateManufacturer(@PathVariable Long id, @RequestBody ManufacturerDTO dto) {
        return service.updateManufacturer(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteManufacturer(@PathVariable Long id) {
        service.deleteManufacturer(id);
    }
}
