package br.com.dbccompany.colaboreapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AutenticacaoCreateDto {

    @Schema(example = "colabore@dbccompany.com.br")
    private String email;

    private String senha;
}