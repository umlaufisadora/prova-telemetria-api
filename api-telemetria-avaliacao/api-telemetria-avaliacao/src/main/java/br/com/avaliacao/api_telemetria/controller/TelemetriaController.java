package br.com.avaliacao.api_telemetria.controller;

import br.com.avaliacao.api_telemetria.dto.ErrorResponseDTO;
import br.com.avaliacao.api_telemetria.dto.TelemetriaRequestDTO;
import br.com.avaliacao.api_telemetria.dto.TelemetriaResponseDTO;
import br.com.avaliacao.api_telemetria.service.TelemetriaService;
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
 * Controller responsável por controlar os endpoints relacionados à entidade HistoricoTelemetria
 */

@RestController
@RequestMapping("/api/v1/telemetria")
public class TelemetriaController {
    private final TelemetriaService telemetriaService;

    public TelemetriaController(TelemetriaService telemetriaService) {
        this.telemetriaService = telemetriaService;
    }

    @Operation(
            summary = "Listar todos os registros",
            description = "Listar todos os registros em uma página"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Requisição aceita e processada"
    )
    /**
     * Listar todos os registros de telemetria
     * @param pageable página com parâmetros de retorno informados (ex: size=20)
     * @return Resposta de êxito com JSON retornado
     */
    @GetMapping
    public ResponseEntity<Page<TelemetriaResponseDTO>> listarTodos(
            @PageableDefault(size = 20, sort = "dataHora", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<TelemetriaResponseDTO> page = telemetriaService.listarTodos(pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(
            summary = "Buscar registro por ID",
            description = "Buscar registro cujo ID condiz"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Registro encontrado pelo ID do informado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Registro não encontrado pelo ID informado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
            )
    })
    /**
     * Buscar registro por ID
     * @param id Identificador único do registro
     * @return Resposta de êxito com JSON retornado
     */
    @GetMapping("/{id}")
    public ResponseEntity<TelemetriaResponseDTO> buscarPorId(@PathVariable Long id) {
        TelemetriaResponseDTO dto = telemetriaService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "Buscar registro pelo ID do motor",
            description = "Buscar registro cujo ID do motor condiz"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Registro encontrado pelo ID do motor informado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Registro não encontrado pelo ID do motor informado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
            )
    })
    /**
     * Filtrar registros por Motor (ID)
     * @param motorID Identificador único de motor
     * @param pageable página com parâmetros de retorno informados (ex: size=20)
     * @return Resposta de êxito com JSON retornado
     */
    @GetMapping("/motor/{motorId}")
    public ResponseEntity<Page<TelemetriaResponseDTO>> listarPorMotor(
            @PathVariable Integer motorId,
            @PageableDefault(size = 20, sort = "dataHora") Pageable pageable) {
        return ResponseEntity.ok(telemetriaService.buscarPorMotor(motorId, pageable));
    }

    @Operation(
            summary = "Registrar Telemetria",
            description = "registrar telemetria a partir da requisição da DTO"
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
     * Registrar telemetria
     * @param dto request com todas as informações disponíveis para o registro de uma telemetria
     * @return Resposta de êxito com JSON retornado e cabeçalho Location
     */
    @PostMapping
    public ResponseEntity<TelemetriaResponseDTO> registrar(@RequestBody @Valid TelemetriaRequestDTO dto) {
        TelemetriaResponseDTO response = telemetriaService.registrar(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

}
