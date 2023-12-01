package com.codecool.oidascriptplatform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

// NOTE: `ScriptBody` is not saved in database, instead in files managed by `ScriptBodyRepository`.
@Entity
public class ScriptBody {
    @Id
    private String id;
    private String body;

    public ScriptBody(String id, String body) {
        this.id = id;
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
