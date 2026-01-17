package io.github.WesleyPatrick.stock_api.web.security;

import io.github.WesleyPatrick.stock_api.domain.model.RefreshToken;
import io.github.WesleyPatrick.stock_api.domain.model.User;
import io.github.WesleyPatrick.stock_api.infra.repository.RefreshTokenRepository;
import io.github.WesleyPatrick.stock_api.infra.repository.UserRepository;
import io.github.WesleyPatrick.stock_api.web.security.utils.HashUtils;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RefreshTokenService {

    private RefreshTokenRepository refreshTokenRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public RefreshTokenService(
            RefreshTokenRepository refreshTokenRepository, UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RefreshToken upsert(User user, String token){
        String lookup = HashUtils.sha256Hex(token);
        String hashedToken = passwordEncoder.encode(lookup);

        RefreshToken refreshToken = refreshTokenRepository.findByUser(user).orElseGet(RefreshToken::new);

        refreshToken.setUser(user);
        refreshToken.setLookupHash(lookup);
        refreshToken.setHashedToken(hashedToken);

        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken validateAndGet(String token){
        String lookup = HashUtils.sha256Hex(token);

        RefreshToken refreshToken = refreshTokenRepository.findByLookupHash(lookup).orElseThrow(
                () -> new RuntimeException("Refresh token inválido"));

        if(!passwordEncoder.matches(lookup, refreshToken.getHashedToken())){
            throw new RuntimeException("Refresh token inválido");
        }

        return refreshToken;
    }

    @Transactional
    public User getUserFromToken(String token){
        System.out.println("caiu aqui");
        RefreshToken refreshToken = validateAndGet(token);
        UUID user = refreshToken.getUser().getId();

        return userRepository.findById(user).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado")
        );
    }

    @Transactional
    public void revokeToken(String email){
        refreshTokenRepository.deleteByUser_Email(email);
    }



}
