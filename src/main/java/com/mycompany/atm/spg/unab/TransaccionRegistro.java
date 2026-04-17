package com.mycompany.atm.spg.unab;

import java.time.LocalDateTime;

public record TransaccionRegistro(String tipo, double monto, LocalDateTime fecha) {
}
