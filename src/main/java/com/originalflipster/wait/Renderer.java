package com.originalflipster.wait;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

import java.util.List;

class Renderer {

    private final List<Submission> subs;
    private final int offset;

    Renderer(List<Submission> subs, int offset) {
        this.subs = subs;
        this.offset = offset;
    }

    void render(Screen screen, Mode mode) {
        int row = 1;
        screen.clear();
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(TextColor.ANSI.MAGENTA);
        text.setBackgroundColor(TextColor.ANSI.YELLOW_BRIGHT);
        text.putString(1, row++, "SITTING, WAITING, CURSING...");
        text.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
        text.setBackgroundColor(TextColor.ANSI.BLACK);
        text.putString(5, row++, "#esGehtMirEigentlichImmerGut", SGR.ITALIC);
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

        row += offset;
        text.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
        text.setBackgroundColor(TextColor.ANSI.MAGENTA);
        text.putString(3, row, mode.name(), SGR.ITALIC);
        text.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
        text.setBackgroundColor(TextColor.ANSI.BLACK);
        text.putString(13, row, "mode active... switch by pressing 's'");
    }
}
