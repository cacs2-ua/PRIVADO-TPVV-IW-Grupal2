package tpvv.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason="Usuario no autorizado")
public class UsuarioNoLogeadoException extends RuntimeException {
    public UsuarioNoLogeadoException() {
        super("Usuario no logeado o sesión expirada");
    }
}
