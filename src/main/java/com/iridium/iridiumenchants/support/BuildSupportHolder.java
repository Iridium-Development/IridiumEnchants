package com.iridium.iridiumenchants.support;

import java.util.function.Supplier;

public interface BuildSupportHolder {
    boolean isInstalled();
    Supplier<BuildSupport> buildSupport();
}
