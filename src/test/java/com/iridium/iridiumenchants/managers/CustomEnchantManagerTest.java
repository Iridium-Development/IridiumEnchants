package com.iridium.iridiumenchants.managers;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import com.iridium.iridiumenchants.IridiumEnchants;
import org.bukkit.Bukkit;
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
        Bukkit.getScheduler().cancelTasks(IridiumEnchants.getInstance());
        MockBukkit.unmock();
    }

    @Test
    public void toRomanNumerals() {
        assertEquals(IridiumEnchants.getInstance().getCustomEnchantManager().toRomanNumerals(1), "I");
        assertEquals(IridiumEnchants.getInstance().getCustomEnchantManager().toRomanNumerals(2), "II");
        assertEquals(IridiumEnchants.getInstance().getCustomEnchantManager().toRomanNumerals(3), "III");
        assertEquals(IridiumEnchants.getInstance().getCustomEnchantManager().toRomanNumerals(4), "IV");
        assertEquals(IridiumEnchants.getInstance().getCustomEnchantManager().toRomanNumerals(5), "V");
        assertEquals(IridiumEnchants.getInstance().getCustomEnchantManager().toRomanNumerals(6), "VI");
        assertEquals(IridiumEnchants.getInstance().getCustomEnchantManager().toRomanNumerals(7), "VII");
        assertEquals(IridiumEnchants.getInstance().getCustomEnchantManager().toRomanNumerals(8), "VIII");
        assertEquals(IridiumEnchants.getInstance().getCustomEnchantManager().toRomanNumerals(9), "IX");
        assertEquals(IridiumEnchants.getInstance().getCustomEnchantManager().toRomanNumerals(10), "X");
        assertEquals(IridiumEnchants.getInstance().getCustomEnchantManager().toRomanNumerals(11), "11");
    }
}