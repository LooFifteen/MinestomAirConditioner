package dev.sllcoding.minestomairconditioner.checks;

import dev.sllcoding.minestomairconditioner.checks.impl.FlightACheck;
import dev.sllcoding.minestomairconditioner.checks.impl.NoFallACheck;
import dev.sllcoding.minestomairconditioner.checks.impl.SpeedACheck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CheckManager {

    private static final List<Class<? extends Check>> checks = new ArrayList<>();

    public static void setup() {
        checks.add(SpeedACheck.class);
        checks.add(FlightACheck.class);
        checks.add(NoFallACheck.class);
    }

    public static List<Class<? extends Check>> getChecks() {
        return Collections.unmodifiableList(checks);
    }

}
