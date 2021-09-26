package com.iridium.iridiumenchants.support;

import java.util.function.Supplier;

public interface FriendlySupportHolder {
    boolean isInstalled();

    Supplier<FriendlySupport> friendlySupport();
}
