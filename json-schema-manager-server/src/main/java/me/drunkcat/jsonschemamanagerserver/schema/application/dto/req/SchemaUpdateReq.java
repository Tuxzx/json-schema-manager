package me.drunkcat.jsonschemamanagerserver.schema.application.dto.req;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import me.drunkcat.jsonschemamanagerserver.schema.domain.entity.Schema;

@Data
public class SchemaUpdateReq {
    private String val;
}
