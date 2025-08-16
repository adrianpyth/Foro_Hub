package com.alura.foroHub.infra.security;

import com.alura.foroHub.domain.usuario.DatosAutenticacionUsuario;
import com.alura.foroHub.domain.usuario.Usuario;
import com.alura.foroHub.infra.security.DatosJWTToken;
import com.alura.foroHub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<DatosJWTToken> autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario) {
        var authToken = new UsernamePasswordAuthenticationToken(
                datosAutenticacionUsuario.correoElectronico(),
                datosAutenticacionUsuario.contrasena()
        );

        var authentication = authenticationManager.authenticate(authToken);
        var tokenJWT = tokenService.generarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DatosJWTToken(tokenJWT));
    }
}
