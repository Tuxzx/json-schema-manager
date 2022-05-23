package me.drunkcat.jsonschemamanagerserver.schema.domain.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.drunkcat.jsonschemamanagerserver.common.entity.BaseEntity;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@TypeDef(
        name = "json",
        typeClass = JsonType.class
)
@Entity
@Table(name = "t_schema")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Schema extends BaseEntity {

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private String val;

    public static Schema of(String val) {
        return Schema.of(val);
    }

}
