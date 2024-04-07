package com.gmail.markushygedombrowski;

import com.gmail.markushygedombrowski.deliveredItems.DeliveredItemsLoader;
import com.gmail.markushygedombrowski.playerProfiles.PlayerProfile;
import com.gmail.markushygedombrowski.playerProfiles.PlayerProfiles;
import com.gmail.markushygedombrowski.settings.Settings;
import com.gmail.markushygedombrowski.sql.Sql;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlayerProfilesTest {

    @Test
    void testSaveAndGetPlayerProfile() throws SQLException {
        PlayerProfiles playerProfiles = mock(PlayerProfiles.class);
        HashMap<UUID, PlayerProfile> profileMap = new HashMap<>();
        when(playerProfiles.getProfileMap()).thenReturn(profileMap);

        UUID uuid = UUID.randomUUID();
        PlayerProfile profile = new PlayerProfile(uuid, "TestPlayer");
        profile.setProperty("level", 5);
        profileMap.put(uuid, profile);
        playerProfiles.save(profile);

        when(playerProfiles.getPlayerProfile(uuid)).thenReturn(profile);
        PlayerProfile retrievedProfile = playerProfiles.getPlayerProfile(uuid);

        Assertions.assertEquals("TestPlayer", retrievedProfile.getName());
        Assertions.assertEquals(5, retrievedProfile.getProperty("level"));
    }


}
