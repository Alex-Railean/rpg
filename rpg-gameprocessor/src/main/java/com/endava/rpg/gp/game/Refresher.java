package com.endava.rpg.gp.game;

import com.endava.rpg.gp.util.Refreshable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public final class Refresher {

    private static final List<Refreshable> refreshables = new ArrayList<>();

    public static void addRefreshable(Refreshable r) {
        refreshables.add(r);
    }

    public static void refresh() {
        refreshables.forEach(Refreshable::refresh);
    }
}
