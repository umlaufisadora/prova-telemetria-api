package br.com.avaliacao.api_telemetria.controller;

import br.com.avaliacao.api_telemetria.dto.*;
import br.com.avaliacao.api_telemetria.service.MotorService;
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
 * Controller responsável por controlar os endpoints relacionados à entidade Motor
 */

@RestController
@RequestMapping("/api/v1/motores")
public class MotorController
{
    private final MotorService motorService;

    public MotorController(MotorService motorService)
    {
        this.motorService = motorService;
    }

    @Operation(
            summary = "Criar Motor",
            description = "Criar motores a partir da requisição da DTO"
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
     * Criar Motor
     * @param dto request com todas as informações disponíveis para a criação de motor
     * @return Resposta de êxito com JSON retornado e cabeçalho Location
     */
    @PostMapping
    public ResponseEntity<MotorResponseDTO> criar(@RequestBody @Valid MotorRequestDTO dto)
    {
        MotorResponseDTO criado = motorService.criar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/id")
                .buildAndExpand(criado.id())
                .toUri();

        return ResponseEntity.created(uri).body(criado);
    }

    @Operation(
            summary = "Listar todos os Motores",
            description = "Listar todos os motores em uma página"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Requisição aceita e processada"
    )
    /**
     * Listar todos os cadastros de motores
     * @param pageable página com parâmetros de retorno informados (ex: size=20)
     * @return Resposta de êxito com JSON retornado
     */
    @GetMapping
    public ResponseEntity<Page<MotorResponseDTO>> listarTodos(
            @PageableDefault(size = 20, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable)
    {
        return ResponseEntity.ok(motorService.listarTodos(pageable));
    }

    @Operation(
            summary = "Buscar motor por ID",
            description = "Buscar motor cujo ID condiz"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Motor encontrado pelo ID do informado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Motor não encontrado pelo ID informado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
            )
    })
    /**
     * Buscar motor por ID
     * @param id Identificador único do motor
     * @return Resposta de êxito com JSON retornado
     */
    @GetMapping("/{id}")
    public ResponseEntity<MotorResponseDTO> buscarPorID(@PathVariable Integer id)
    {
        return ResponseEntity.ok(motorService.buscarPorId(id));
    }

    @Operation(
            summary = "Buscar motor por código do ativo",
            description = "Buscar motos cujo código do ativo condiz com o informado"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Motor encontrado com código do ativo"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Motor não encontrado pelo código do ativo informado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
            )
    })
    /**
     * Buscar motor por código do Ativo
     * @params codigoAtivo parâmetro usado para buscar motor por código do ativo
     * @return Resposta de êxito com JSON retornado
     */
    @GetMapping(params = "codigoAtivo")
    public ResponseEntity<MotorResponseDTO> buscarPorCodigoAtivo(String codigoAtivo)
    {
        return ResponseEntity.ok(motorService.buscarPorCodigoAtivo(codigoAtivo));
    }

    @Operation(
            summary = "Atualizar motor",
            description = "Atualizar todos os atributos de motor, menos ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Motor Atualizado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Motor não encontrado pelo ID informado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
            )
    })
    /**
     * Atualizar completamente o motor
     * @param id Identificador único de Motor
     * @param dto request com todas as informações disponíveis para a atualização completa do motor
     * @return Resposta de êxito com JSON retornado
     */
    @PutMapping("/{id}")
    public ResponseEntity<MotorResponseDTO> atualizar(
            Integer id,
            @Valid MotorRequestDTO dto
    )
    {
        return ResponseEntity.ok(motorService.atualizar(id,dto));
    }

    @Operation(
            summary = "Atualizar status de motor",
            description = "Atualizar apenas o status de motor"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Status do motor Atualizado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Motor não encontrado pelo ID informado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
            )
    })
    /**
     * Atualizar status do motor
     * @param id Identificador único de motor
     * @param dto request com todas as informações disponíveis para a atualização do status do motor
     * @return Resposta de êxito com JSON retornado
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<MotorResponseDTO> atualizarStatus(
            @PathVariable Integer id,
            @RequestBody @Valid MotorStatusUpdateDTO dto)
    {
        return ResponseEntity.ok(motorService.atualizarStatus(id, dto));
    }

}
