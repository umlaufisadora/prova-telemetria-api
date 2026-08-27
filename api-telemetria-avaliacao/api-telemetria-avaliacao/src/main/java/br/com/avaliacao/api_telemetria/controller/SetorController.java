package br.com.avaliacao.api_telemetria.controller;

import br.com.avaliacao.api_telemetria.dto.ErrorResponseDTO;
import br.com.avaliacao.api_telemetria.dto.SetorRequestDTO;
import br.com.avaliacao.api_telemetria.dto.SetorResponseDTO;
import br.com.avaliacao.api_telemetria.service.SetorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(
        name = "API de Telemetria de Motores Industriais",
        description = "REST API para monitoramento, ingestão de dados e alertas de motores fabris."
)

/**
 * Controller responsável por controlar os endpoints relacionados à entidade Setor
 */
@RestController
@RequestMapping("/api/v1/setores")
public class SetorController {
    private final SetorService setorService;

    public SetorController(SetorService setorService) {
        this.setorService = setorService;
    }

    @Operation(
            summary = "Criar Setor",
            description = "Criar setor a partir da requisição da DTO"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Entidade criada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Formato inválido",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
            )
    })
    /**
     * Criar setor
     * @param dto request com todas as informações disponíveis para a criação de setor
     * @return Resposta de êxito com JSON retornado e cabeçalho Location
     */
    @PostMapping
    public ResponseEntity<SetorResponseDTO> criar(@RequestBody @Valid SetorRequestDTO dto) {
        SetorResponseDTO criado = setorService.criar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(criado.id())
                .toUri();
        return ResponseEntity.created(uri).body(criado);
    }

    @Operation(
            summary = "Listar todos os setores",
            description = "Listar todos os setores em uma página"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Requisição aceita e processada"
    )
    /**
     * Listar todos os cadastros de setores
     * @param pageable página com parâmetros de retorno informados (ex: size=20)
     * @return Resposta de êxito com JSON retornado
     */
    @GetMapping
    public ResponseEntity<Page<SetorResponseDTO>> listarTodos(
            @PageableDefault(size = 20, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(setorService.listarTodos(pageable));
    }

    @Operation(
            summary = "Buscar setor por ID",
            description = "Buscar setor cujo ID condiz"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "setor encontrado pelo ID do informado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "setor não encontrado pelo ID informado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
            )
    })
    /**
     * Buscar setor por ID
     * @param id Identificador único do setor
     * @return Resposta de êxito com JSON retornado
     */
    @GetMapping("/{id}")
    public ResponseEntity<SetorResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(setorService.buscarPorId(id));
    }

    @Operation(
            summary = "Atualizar setor",
            description = "Atualizar todos os atributos de setor, menos ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Setor Atualizado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Setor não encontrado pelo ID informado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
            )
    })
    /**
     * Atualizar completamente o setor
     * @param id Identificador único de Setor
     * @param dto request com todas as informações disponíveis para a atualização completa do setor
     * @return Resposta de êxito com JSON retornado
     */
    @PutMapping("/{id}")
    public ResponseEntity<SetorResponseDTO> atualizar(
            Integer id,
             @Valid SetorRequestDTO dto) {
        return ResponseEntity.ok(setorService.atualizar(id, dto));
    }
}
