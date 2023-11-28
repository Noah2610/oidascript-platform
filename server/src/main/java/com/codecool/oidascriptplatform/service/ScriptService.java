package com.codecool.oidascriptplatform.service;

import com.codecool.oidascriptplatform.controller.dto.CreateScriptRequestBody;
import com.codecool.oidascriptplatform.model.ScriptDetails;
import com.codecool.oidascriptplatform.model.User;
import com.codecool.oidascriptplatform.repository.ScriptDetailsRepository;
import com.codecool.oidascriptplatform.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScriptService {
    private final ScriptDetailsRepository scriptDetailsRepository;
    private final UserRepository userRepository;

    public ScriptService(ScriptDetailsRepository scriptDetailsRepository, UserRepository userRepository) {
        this.scriptDetailsRepository = scriptDetailsRepository;
        this.userRepository = userRepository;
    }

    public List<ScriptDetails> getScriptDetailsForUsername(String username) {
        return scriptDetailsRepository.filterByUsername(username);
    }

    public ScriptDetails getScriptForUsername(String username, Long id) {
        return scriptDetailsRepository.findByIdAndUsername(id, username).orElseThrow();
    }

    public ScriptDetails createScriptForUsername(String username, CreateScriptRequestBody body) {
        // TODO: save script body with ScriptBodyRepository
        User user = userRepository.findByUsername(username).orElseThrow();

        ScriptDetails scriptDetails = new ScriptDetails();
        scriptDetails.setName(body.getName());
        scriptDetails.setDescription(body.getDescription());
        scriptDetails.setUser(user);

        return scriptDetailsRepository.save(scriptDetails);
    }

    public ScriptDetails updateScriptForUsername(String username, Long id, CreateScriptRequestBody body) {
        // TODO: save script body with ScriptBodyRepository
        Optional<ScriptDetails> scriptDetailsOpt = scriptDetailsRepository.findByIdAndUsername(id, username);
        if (scriptDetailsOpt.isEmpty()) {
            throw new EntityNotFoundException(String.format("Script with ID %d for user %s not found", id, username));
        }

        ScriptDetails scriptDetails = scriptDetailsOpt.get();
        scriptDetails.setName(body.getName());
        scriptDetails.setDescription(body.getDescription());

        return scriptDetailsRepository.save(scriptDetails);
    }
}
