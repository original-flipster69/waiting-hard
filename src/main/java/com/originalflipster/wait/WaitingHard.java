package com.originalflipster.wait;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
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
        new Submission("EPCO final exam", LocalDateTime.parse("2026-01-27T17:45:00"), null, Duration.ofDays(28), null),
        new Submission("BA Paper", LocalDateTime.parse("2026-01-18T23:45:00"), LocalDateTime.parse("2026-02-04T23:59:59"), Duration.ofDays(28), null),
        new Submission("CJCA Pentesting Exam", LocalDateTime.parse("2026-01-25T16:17:00"), null, Duration.ofDays(28), "PASS"),
        new Submission("TCF Francais", LocalDateTime.parse("2026-01-30T12:00:00"), null, Duration.ofDays(21), null)
    );

    Screen screen = new DefaultTerminalFactory().createScreen();
    screen.startScreen();
    screen.setCursorPosition(null);

    try {
      while(true) {
        int row = 1;
        screen.clear();
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.MAGENTA);
        text.setBackgroundColor(TextColor.ANSI.YELLOW_BRIGHT);
        text.putString(1, row++, "SITTING, WAITING, CURSING...");
        text.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
        text.setBackgroundColor(TextColor.ANSI.BLACK);
        text.putString(5, row++, "#esGehtMirEigentlichImmerGut", SGR.BLINK, SGR.ITALIC);
        for (var it : subs) {
          row += offset;
          text.setForegroundColor(TextColor.ANSI.BLACK);
          text.setBackgroundColor(TextColor.ANSI.MAGENTA);
          text.putString(1, row, it.getName());
          text.setForegroundColor(TextColor.ANSI.YELLOW);
          text.setBackgroundColor(TextColor.ANSI.BLACK);
          if(it.done()) {
            text.putString(55, row++, it.getResult(), SGR.BOLD, SGR.ITALIC);
            continue;
          }
          text.putString(55, row++, it.progressDone() + "%", SGR.BOLD, SGR.ITALIC);

          text.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
          text.setBackgroundColor(TextColor.ANSI.BLACK);

          text.putString(3, row, "It's been...", SGR.BOLD);
          text.putString(20, row, String.valueOf(it.days()));
          text.putString(23, row, "days", SGR.ITALIC);
          text.putString(30, row, "OR", SGR.BOLD);
          text.putString(36, row, String.valueOf(it.hours()));
          text.putString(41, row++, "hours", SGR.ITALIC);

          text.setForegroundColor(TextColor.ANSI.MAGENTA);
          text.putString(3, row, "Time left: ", SGR.BOLD);
          text.putString(20, row, String.valueOf(it.daysLeft()));
          text.putString(23, row, "days", SGR.ITALIC);
          text.putString(30, row, "OR", SGR.BOLD);
          text.putString(36, row, String.valueOf(it.hoursLeft()));
          text.putString(41, row++, "hours", SGR.ITALIC);
        }

        screen.refresh();
        Thread.sleep(30000);
      }
    } finally {
      screen.stopScreen();
    }
  }
}
