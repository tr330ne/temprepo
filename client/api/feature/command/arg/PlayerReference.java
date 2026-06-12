package org.rusherhack.client.api.feature.command.arg;

import net.minecraft.client.multiplayer.PlayerInfo;
import org.jetbrains.annotations.Nullable;

public record PlayerReference(String name, @Nullable PlayerInfo playerInfo) {
}
