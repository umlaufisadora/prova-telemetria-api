package br.com.avaliacao.api_telemetria.controller;

import br.com.avaliacao.api_telemetria.dto.AlertaRequestDTO;
import br.com.avaliacao.api_telemetria.dto.AlertaResolucaoDTO;
import br.com.avaliacao.api_telemetria.dto.AlertaResponseDTO;
import br.com.avaliacao.api_telemetria.dto.ErrorResponseDTO;
import br.com.avaliacao.api_telemetria.service.AlertaMotorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
@Tag(
        name = "API de Telemetria de Motores Industriais",
        description = "REST API para monitoramento, ingestão de dados e alertas de motores fabris."
)
/**
 * Controller responsável por controlar os endpoints relacionados à entidade AlertaMotor
 */

@RestController
@RequestMapping("/api/v1/alerta-motores")
public class AlertaMotorController {
    private final AlertaMotorService alertaService;

    public AlertaMotorController(AlertaMotorService alertaService) {
        this.alertaService = alertaService;
    }

    @Operation(
            summary = "Criar Alerta de Motor",
            description = "Criar alertas de motores a partir da requisição da DTO"
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
     * Criar Alerta de Motor
     * @param dto request com todas as informações disponíveis para a criação de Alerta de Motor
     * @return Resposta de êxito com JSON retornado e cabeçalho Location
     */
    @PostMapping
    public ResponseEntity<AlertaResponseDTO> criar(@RequestBody @Valid AlertaRequestDTO dto) {
        AlertaResponseDTO criado = alertaService.criar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(criado.id())
                .toUri();
        return ResponseEntity.created(uri).body(criado);
    }

    @Operation(
            summary = "Listar todos os alertas",
            description = "Listar todos os alertas e ordenar por status de resolução"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Requisição aceita e processada"
    )
    /**
     * Listar todos os alertas de motor
     * @param pageable página com parâmetros de retorno informados (ex: size=20)
     * @return Resposta de êxito com JSON retornado
     */
    @GetMapping
    public ResponseEntity<Page<AlertaResponseDTO>> listarTodos(Boolean resolvido,
    @PageableDefault(size = 20, sort = "dataAlerta", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<AlertaResponseDTO> page = (resolvido != null)
                ? alertaService.listarPorStatusResolucao(resolvido, pageable)
                : alertaService.listarTodos(pageable);

        return ResponseEntity.ok(page);
    }

    @Operation(
            summary = "Buscar alertas por motor",
            description = "Listar todos os alertas com filtro a partir do ID do motor"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Alertas encontrados pelo ID do motor"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Alerta não encontrado pelo ID informado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
            )
    })
    /**
     * Filtrar alertas por Motor (ID)
     * @param motorID Identificador único de motor
     * @param pageable página com parâmetros de retorno informados (ex: size=20)
     * @return Resposta de êxito com JSON retornado
     */
    @GetMapping("/motor/{motorId}")
    public ResponseEntity<Page<AlertaResponseDTO>> buscarPorMotor(
           Integer motorId,
            @PageableDefault(size = 20, sort = "dataAlerta", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(alertaService.buscarPorMotor(motorId, pageable));
    }

    @Operation(
            summary = "Atualizar status de resolução"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Alertas encontrados pelo ID e alterado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Alerta não encontrado pelo ID informado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
            )
    })
    /**
     * Atualizar status de resolução do alerta
     * @param id Identificador único de alerta
     * @param dto request com todas as informações disponíveis para a atualização do status do alerta
     * @return Resposta de êxito com JSON retornado
     */
    @PatchMapping("/{id}/resolucao")
    public ResponseEntity<AlertaResponseDTO> atualizarStatusResolucao(
            @PathVariable Integer id,
            @RequestBody @Valid AlertaResolucaoDTO dto) {
        return ResponseEntity.ok(alertaService.atualizarStatusResolucao(id, dto));
    }
}
