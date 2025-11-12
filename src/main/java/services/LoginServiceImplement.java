package services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public class LoginServiceImplement implements LoginService {
    @Override
    public Optional<String> getUsername(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        // Si username NO es null, lo envolvemos con Optional.of().
        if (username != null) {
            return Optional.of(username);
        }

        // Si username es null, devolvemos Optional.empty().
        return Optional.empty();
    }
}