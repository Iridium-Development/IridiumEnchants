package com.iridium.iridiumenchants.managers;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import com.iridium.iridiumenchants.IridiumEnchants;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomEnchantManagerTest {

    private ServerMock serverMock;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @BeforeEach
    public void setup() {
        this.serverMock = MockBukkit.mock();
        MockBukkit.load(IridiumEnchants.class);
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void toRomanNumerals() {
        assertEquals(1, 1);
    }

    @Test
    public void applyEnchantment() {
    }

    @Test
    public void getEnchantmentFromCrystal() {
    }

    @Test
    public void getEnchantmentLevelFromCrystal() {
    }

    @Test
    public void getEnchantmentCrystal() {
    }

    @Test
    public void getEnchantmentsFromItem() {
    }

    @Test
    public void applyEffectsFromItem() {
    }
}