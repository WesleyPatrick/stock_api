package io.github.WesleyPatrick.stock_api.web.controller;

import io.github.WesleyPatrick.stock_api.application.dto.page.PageResponse;
import io.github.WesleyPatrick.stock_api.application.dto.product.CreateProductRequest;
import io.github.WesleyPatrick.stock_api.application.dto.product.ProductFilters;
import io.github.WesleyPatrick.stock_api.application.dto.product.ProductResponse;
import io.github.WesleyPatrick.stock_api.application.usecase.product.CreateProductUseCase;
import io.github.WesleyPatrick.stock_api.application.usecase.product.DeleteProductUseCase;
import io.github.WesleyPatrick.stock_api.application.usecase.product.FindAllProductsUseCase;
import io.github.WesleyPatrick.stock_api.application.usecase.product.FindProductByIdUseCase;
import io.github.WesleyPatrick.stock_api.domain.model.Product;
import io.github.WesleyPatrick.stock_api.infra.repository.ProductRepository;
import io.github.WesleyPatrick.stock_api.web.controller.commons.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Product")
@RestController
@RequestMapping("/product")
@SecurityRequirement(name = "bearerAuth")
public class ProductController implements BaseController {

    private CreateProductUseCase createProductUseCase;
    private FindProductByIdUseCase findProductByIdUseCase;
    private FindAllProductsUseCase findAllProductsUseCase;
    private DeleteProductUseCase deleteProductUseCase;

    public ProductController(CreateProductUseCase createProductUseCase,  FindProductByIdUseCase findProductByIdUseCase, FindAllProductsUseCase findAllProductsUseCase,  DeleteProductUseCase deleteProductUseCase)
    {
        this.createProductUseCase = createProductUseCase;
        this.findProductByIdUseCase = findProductByIdUseCase;
        this.findAllProductsUseCase = findAllProductsUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
    }

    @Operation(summary = "Criar produto")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Produto criado",
                    content = @Content(
                            schema = @Schema(implementation = ProductResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "409", description = "Produto já existe")
    })
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid CreateProductRequest request) {
        ProductResponse productResponse = createProductUseCase.execute(request);
        return ResponseEntity.created(getBaseUri(productResponse.id())).body(productResponse);
    }

    @Operation(summary = "Buscar produto por ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Produto encontrado",
                    content = @Content(
                            schema = @Schema(implementation = ProductResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(findProductByIdUseCase.execute(id));
    }

    @GetMapping
    @Operation(summary = "Listar produtos com filtros e paginação")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de produtos")
    })
    public ResponseEntity<PageResponse<ProductResponse>> findAll(@ParameterObject ProductFilters filters,
                                                                 @ParameterObject Pageable pageable) {
        PageResponse<ProductResponse> productResponsePage = findAllProductsUseCase.execute(filters, pageable);
        return ResponseEntity.ok().body(productResponsePage);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar produtos")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Produto Deletado"
            ),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteProductUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }


}
