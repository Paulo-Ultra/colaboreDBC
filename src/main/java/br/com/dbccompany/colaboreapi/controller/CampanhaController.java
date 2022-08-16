package br.com.dbccompany.colaboreapi.controller;

import br.com.dbccompany.colaboreapi.dto.campanha.CampanhaCreateComFotoDTO;
import br.com.dbccompany.colaboreapi.dto.campanha.CampanhaCreateDTO;
import br.com.dbccompany.colaboreapi.dto.campanha.CampanhaDTO;
import br.com.dbccompany.colaboreapi.exceptions.AmazonS3Exception;
import br.com.dbccompany.colaboreapi.exceptions.CampanhaNaoEncontradaException;
import br.com.dbccompany.colaboreapi.exceptions.RegraDeNegocioException;
import br.com.dbccompany.colaboreapi.service.CampanhaService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/campanha")
@Validated
@RequiredArgsConstructor
public class CampanhaController {

    private final CampanhaService campanhaService;

    @Operation(summary = "realiza o cadastro de uma campanha", description = "realiza o cadastro de uma campanha no banco de dados")
    @RequestMapping(
            path = "/cadastrar",
            method = POST,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public ResponseEntity<CampanhaDTO> cadastrar (@Valid @ModelAttribute CampanhaCreateComFotoDTO campanhaCreateDTO) throws RegraDeNegocioException, AmazonS3Exception {
        return new ResponseEntity<>(campanhaService.adicionar(campanhaCreateDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "realiza a listagem de todas as campanhas", description = "lista de todas as campanhas no banco de dados")
    @GetMapping("/listar")
    public ResponseEntity<List<CampanhaDTO>> listarCampanhas() throws RegraDeNegocioException, CampanhaNaoEncontradaException {
        return new ResponseEntity<>(campanhaService.listaDeCampanhas(), HttpStatus.OK);
    }

    @Operation(summary = "realiza a listagem de todas as campanhas do usuário logado", description = "lista de todas as campanhas do usuário logado no banco de dados")
    @GetMapping("/listarCampanhasDoUsuario")
    public ResponseEntity<List<CampanhaDTO>> listarCampanhasDoUsuario() throws RegraDeNegocioException, CampanhaNaoEncontradaException {
        return new ResponseEntity<>(campanhaService.listaDeCampanhasByUsuarioLogado(), HttpStatus.OK);
    }

    @Operation(summary = "realiza a edição da campanha do usuário logado", description = "efetua a edição de campanha pelo identificador no banco de dados")
    @RequestMapping(
            path = "/editar",
            method = PUT,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public ResponseEntity<CampanhaDTO> editar(@RequestParam Integer id,
                                              @Valid @ModelAttribute CampanhaCreateComFotoDTO campanhaCreateDTO) throws CampanhaNaoEncontradaException, RegraDeNegocioException, AmazonS3Exception {
        return new ResponseEntity<>(campanhaService.editar(id, campanhaCreateDTO), HttpStatus.OK);
    }

    @Operation(summary = "realiza a deleção da campanha do usuário logado", description = "delete de campanha pelo identificador no banco de dados")
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteCampanha(@RequestParam Integer id) throws CampanhaNaoEncontradaException, RegraDeNegocioException {
        campanhaService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "realiza a listagem da campanha pelo id informado", description = "lista as informações da campanha pelo id no banco de dados")
    @GetMapping("/campanhaPeloId")
    public ResponseEntity<CampanhaDTO> CampanhaPeloId(@RequestParam Integer idCampanha) throws CampanhaNaoEncontradaException {
        return new ResponseEntity<>(campanhaService.campanhaPeloId(idCampanha), HttpStatus.OK);
    }
}
