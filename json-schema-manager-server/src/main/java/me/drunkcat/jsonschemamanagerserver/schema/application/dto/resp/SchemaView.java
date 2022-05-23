package me.drunkcat.jsonschemamanagerserver.schema.application.dto.resp;

import lombok.Data;
import me.drunkcat.jsonschemamanagerserver.schema.domain.entity.Schema;

@Data(staticConstructor = "of")
public class SchemaView {
    private final long id;
    private final String val;

    public static SchemaView toView(Schema schema) {
        return SchemaView.of(schema.getId(), schema.getVal());
    }
}
