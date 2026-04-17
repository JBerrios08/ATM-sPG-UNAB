package com.mycompany.atm.spg.unab;

import java.time.LocalDateTime;

public record HistorialTransacciones(String tipo, double monto, LocalDateTime fecha) {
}
