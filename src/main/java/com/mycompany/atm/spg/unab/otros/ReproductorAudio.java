package com.mycompany.atm.spg.unab.otros;

import java.io.BufferedInputStream;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public final class ReproductorAudio {

    private static final String AUDIO_TOQUE_OPCION = "/audio/toque-de-opcion.wav";
    private static final String AUDIO_RETIRO_DINERO = "/audio/retiro-de-dinero.wav";

    private ReproductorAudio() {
    }

    public static void reproducirToqueOpcion() {
        reproducir(AUDIO_TOQUE_OPCION);
    }

    public static void reproducirRetiroDinero() {
        reproducir(AUDIO_RETIRO_DINERO);
    }

    public static void reproducir(String rutaAudio) {
        new Thread(() -> reproducirInterno(rutaAudio), "audio-atm").start();
    }

    private static void reproducirInterno(String rutaAudio) {
        try (InputStream recurso = ReproductorAudio.class.getResourceAsStream(rutaAudio)) {
            if (recurso == null) {
                return;
            }
            try (BufferedInputStream buffer = new BufferedInputStream(recurso);
                 AudioInputStream audioStream = AudioSystem.getAudioInputStream(buffer)) {
                Clip clip = AudioSystem.getClip();
                clip.addLineListener(evento -> {
                    switch (evento.getType()) {
                        case STOP, CLOSE -> clip.close();
                        default -> {
                        }
                    }
                });
                clip.open(audioStream);
                clip.start();
            }
        } catch (Exception ex) {
        }
    }
}
