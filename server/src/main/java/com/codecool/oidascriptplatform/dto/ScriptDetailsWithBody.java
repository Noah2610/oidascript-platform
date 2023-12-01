package com.codecool.oidascriptplatform.dto;

import com.codecool.oidascriptplatform.model.ScriptBody;
import com.codecool.oidascriptplatform.model.ScriptDetails;
import org.springframework.lang.Nullable;

public class ScriptDetailsWithBody {
    private Long id;
    private String name;
    @Nullable
    private String description;
    private String body;

    public ScriptDetailsWithBody(ScriptDetails details, ScriptBody body) {
        this.id = details.getId();
        this.name = details.getName();
        this.description = details.getDescription();
        this.body = body.getBody();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public String getBody() {
        return body;
    }
}
