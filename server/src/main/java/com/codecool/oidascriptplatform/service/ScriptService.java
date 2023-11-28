package com.codecool.oidascriptplatform.service;

import com.codecool.oidascriptplatform.model.ScriptDetails;
import com.codecool.oidascriptplatform.repository.ScriptDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScriptService {
    private final ScriptDetailsRepository scriptDetailsRepository;

    public ScriptService(ScriptDetailsRepository scriptDetailsRepository) {
        this.scriptDetailsRepository = scriptDetailsRepository;
    }

    public List<ScriptDetails> getScriptDetailsForUsername(String username) {
        return scriptDetailsRepository.findScriptDetailsByUsername(username);
    }
}
