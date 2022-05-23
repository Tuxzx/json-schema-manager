package me.drunkcat.jsonschemamanagerserver.schema.application.res;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.drunkcat.jsonschemamanagerserver.schema.application.dto.req.SchemaCreateReq;
import me.drunkcat.jsonschemamanagerserver.schema.application.dto.req.SchemaUpdateReq;
import me.drunkcat.jsonschemamanagerserver.schema.application.dto.resp.SchemaView;
import me.drunkcat.jsonschemamanagerserver.schema.domain.entity.Schema;
import me.drunkcat.jsonschemamanagerserver.schema.domain.repository.SchemaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Tag(name = "Schema")
@RestController
@RequestMapping("/schemas")
@RequiredArgsConstructor
public class SchemaResource {

    private final SchemaRepository schemaRepository;


    @Operation(summary = "Get Schema by ID", operationId = "findSchemaById")
    @GetMapping("/{id}")
    public ResponseEntity<SchemaView> schema(
            @PathVariable long id
    ) {
        return schemaRepository.findById(id)
                .map(SchemaView::toView)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create Schema", operationId = "createSchema")
    @PostMapping
    public ResponseEntity<SchemaView> schemaCreate(
            @RequestBody SchemaCreateReq req
    ) throws JsonProcessingException {
        Schema schema = req.toEntity();
        Schema save = schemaRepository.save(schema);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(SchemaView.toView(save));
    }

    @Operation(summary = "Update schema", operationId = "updateSchema")
    @PutMapping("/{id}")
    public ResponseEntity<SchemaView> schemaUpdate(
            @PathVariable long id,
            @RequestBody SchemaUpdateReq req
    ) {
        Schema schema = schemaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "schema NOT exist"));

        // update
        schema.setVal(req.getVal());

        // persist
        Schema save = schemaRepository.save(schema);
        return ResponseEntity.ok(SchemaView.toView(save));
    }

    @Operation(summary = "Delete schema", operationId = "deleteSchema")
    @DeleteMapping("/{id}")
    public ResponseEntity<SchemaView> schemaDel(
            @PathVariable long id
    ) {

        schemaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
