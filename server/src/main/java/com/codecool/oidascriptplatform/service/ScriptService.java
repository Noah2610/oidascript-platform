package com.codecool.oidascriptplatform.service;

import com.codecool.oidascriptplatform.dto.CreateScriptRequestBody;
import com.codecool.oidascriptplatform.dto.ScriptDetailsWithBody;
import com.codecool.oidascriptplatform.model.ScriptBody;
import com.codecool.oidascriptplatform.model.ScriptDetails;
import com.codecool.oidascriptplatform.model.User;
import com.codecool.oidascriptplatform.repository.ScriptBodyRepository;
import com.codecool.oidascriptplatform.repository.ScriptDetailsRepository;
import com.codecool.oidascriptplatform.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScriptService {
    private final ScriptDetailsRepository scriptDetailsRepository;
    private final ScriptBodyRepository scriptBodyRepository;
    private final UserRepository userRepository;
    private final UuidService uuidService;

    public ScriptService(ScriptDetailsRepository scriptDetailsRepository, ScriptBodyRepository scriptBodyRepository, UserRepository userRepository, UuidService uuidService) {
        this.scriptDetailsRepository = scriptDetailsRepository;
        this.scriptBodyRepository = scriptBodyRepository;
        this.userRepository = userRepository;
        this.uuidService = uuidService;
    }

    public List<ScriptDetails> getScriptDetailsForUsername(String username) {
        return scriptDetailsRepository.filterByUsername(username);
    }

    public ScriptDetailsWithBody getScriptForUsername(String username, Long id) {
        ScriptDetails details = scriptDetailsRepository.findByIdAndUsername(id, username).orElseThrow(() -> new EntityNotFoundException("ScriptDetails not found"));
        ScriptBody body = scriptBodyRepository.findById(details.getBodyId()).orElseThrow(() -> new EntityNotFoundException("ScriptBody not found"));
        return new ScriptDetailsWithBody(details, body);
    }

    public ScriptDetailsWithBody createScriptForUsername(String username, CreateScriptRequestBody body) {
        User user = userRepository.findByUsername(username).orElseThrow();

        String scriptBodyId = uuidService.randomWithPrefix(body.getName());
        ScriptBody scriptBody = new ScriptBody(scriptBodyId, body.getBody());

        ScriptDetails scriptDetails = new ScriptDetails();
        scriptDetails.setName(body.getName());
        scriptDetails.setDescription(body.getDescription());
        scriptDetails.setUser(user);
        scriptDetails.setBodyId(scriptBodyId);

        return new ScriptDetailsWithBody(
                scriptDetailsRepository.save(scriptDetails),
                scriptBodyRepository.save(scriptBody)
        );
    }

    public ScriptDetailsWithBody updateScriptForUsername(String username, Long id, CreateScriptRequestBody body) {
        ScriptDetails scriptDetails = scriptDetailsRepository.findByIdAndUsername(id, username)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Script with ID %d for user %s not found", id, username)));
        ScriptBody scriptBody = scriptBodyRepository.findById(scriptDetails.getBodyId())
                .orElseThrow(() -> new EntityNotFoundException("ScriptBody not found"));

        scriptDetails.setName(body.getName());
        scriptDetails.setDescription(body.getDescription());
        scriptBody.setBody(body.getBody());

        return new ScriptDetailsWithBody(
                scriptDetailsRepository.save(scriptDetails),
                scriptBodyRepository.save(scriptBody)
        );
    }

    public void deleteScriptForUsername(String username, Long id) {
        ScriptDetails details = scriptDetailsRepository.findByIdAndUsername(id, username)
                .orElseThrow(() -> new EntityNotFoundException("ScriptDetails not found"));
        scriptBodyRepository.deleteById(details.getBodyId());
        scriptDetailsRepository.deleteById(details.getId());
    }
}
