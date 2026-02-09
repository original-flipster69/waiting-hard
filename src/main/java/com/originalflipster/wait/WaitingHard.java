package com.originalflipster.wait;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class WaitingHard {

    public static void main(String[] args) throws InterruptedException, IOException {
        int offset = 2;
        var subs = List.of(
                new Submission("EPCO final exam", LocalDateTime.parse("2026-01-27T17:45:00"), null, Duration.ofDays(28), Duration.ofDays(21), null),
                new Submission("BA Paper", LocalDateTime.parse("2026-01-18T23:45:00"), LocalDateTime.parse("2026-02-04T23:59:59"), Duration.ofDays(28), null, null),
                new Submission("CJCA Pentesting Exam", LocalDateTime.parse("2026-01-25T16:17:00"), null, Duration.ofDays(28), null, "PASS"),
                new Submission("TCF Francais", LocalDateTime.parse("2026-01-30T12:00:00"), null, Duration.ofDays(21), Duration.ofDays(14), null)
        );

        Renderer rend = new Renderer(subs, offset);

        Screen screen = new DefaultTerminalFactory().createScreen();
        screen.startScreen();
        screen.setCursorPosition(null);

        Mode activeMode = Mode.MAX;
        for(var sub : subs) {
            sub.setMode(activeMode);
        }
        try {
            long lastRefresh = 0;
            while(true) {
                KeyStroke keyStroke = screen.pollInput();
                if (keyStroke != null) {
                    if (keyStroke.getKeyType() == KeyType.Escape ||
                            (keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'q')) {
                        break;
                    }

                    if (keyStroke.getCharacter() != null && keyStroke.getCharacter() == 's') {
                        activeMode = activeMode == Mode.MAX ? Mode.ESTIMATE : Mode.MAX;
                        for(var sub : subs) {
                            sub.setMode(activeMode);
                        }
                        lastRefresh = 0;
                    }
                }

                long currentTime = System.currentTimeMillis();
                if (currentTime - lastRefresh > 60000) {
                    rend.render(screen, activeMode);
                    screen.refresh();
                    lastRefresh = currentTime;
                }

                Thread.sleep(500);
            }
        } finally {
            screen.stopScreen();
        }
    }
}
