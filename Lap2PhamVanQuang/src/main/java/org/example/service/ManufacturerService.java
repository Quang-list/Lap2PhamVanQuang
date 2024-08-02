package org.example.service;

import org.example.dto.ManufacturerDTO;
import org.example.entity.Manufacturer;
import org.example.exception.ResourceNotFoundException;
import org.example.repository.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManufacturerService {
    @Autowired
    private ManufacturerRepository repository;

    public List<ManufacturerDTO> getAllManufacturers() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ManufacturerDTO createManufacturer(ManufacturerDTO dto) {
        Manufacturer manufacturer = convertToEntity(dto);
        repository.save(manufacturer);
        return convertToDTO(manufacturer);
    }

    public ManufacturerDTO updateManufacturer(Long id, ManufacturerDTO dto) {
        Manufacturer manufacturer = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Manufacturer not found"));
        manufacturer.setName(dto.getName());
        manufacturer.setOrigin(dto.getOrigin());
        manufacturer.setDescription(dto.getDescription());
        repository.save(manufacturer);
        return convertToDTO(manufacturer);
    }

    public void deleteManufacturer(Long id) {
        Manufacturer manufacturer = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Manufacturer not found"));
        if (!manufacturer.getProducts().isEmpty()) {
            throw new IllegalStateException("Cannot delete manufacturer with products");
        }
        repository.delete(manufacturer);
    }

    private ManufacturerDTO convertToDTO(Manufacturer manufacturer) {
        ManufacturerDTO dto = new ManufacturerDTO();
        dto.setId(manufacturer.getId());
        dto.setName(manufacturer.getName());
        dto.setOrigin(manufacturer.getOrigin());
        dto.setDescription(manufacturer.getDescription());
        return dto;
    }

    private Manufacturer convertToEntity(ManufacturerDTO dto) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(dto.getName());
        manufacturer.setOrigin(dto.getOrigin());
        manufacturer.setDescription(dto.getDescription());
        return manufacturer;
    }
}
