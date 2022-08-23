package com.dmdev.natalliavasilyeva.api.dto.responsedto;

import java.util.List;

public class BrandResponseDTO {

    private Long id;
    private String name;

    private List<ModelResponseDTO> models;

    public BrandResponseDTO(Long id, String name, List<ModelResponseDTO> models) {
        this.id = id;
        this.name = name;
        this.models = models;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<ModelResponseDTO> getModels() {
        return models;
    }
    public void setModels(List<ModelResponseDTO> models) {
        this.models = models;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}