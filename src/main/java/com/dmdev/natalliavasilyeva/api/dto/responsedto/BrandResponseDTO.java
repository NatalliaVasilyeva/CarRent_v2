package com.dmdev.natalliavasilyeva.api.dto.responsedto;

import java.util.List;

public class BrandResponseDTO {

    private String name;

    private List<ModelResponseDTO> models;

    public BrandResponseDTO(String name, List<ModelResponseDTO> models) {
        this.name = name;
        this.models = models;
    }
}