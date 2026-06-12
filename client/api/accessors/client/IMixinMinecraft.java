package org.rusherhack.client.api.accessors.client;

import com.mojang.authlib.minecraft.UserApiService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import net.minecraft.client.Minecraft;
import net.minecraft.client.User;
import net.minecraft.client.DeltaTracker.Timer;
import net.minecraft.client.gui.screens.social.PlayerSocialManager;
import net.minecraft.client.main.GameConfig;
import net.minecraft.client.multiplayer.ProfileKeyPairManager;
import net.minecraft.client.multiplayer.chat.report.ReportingContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Minecraft.class)
public interface IMixinMinecraft {
   @Accessor("timer")
   Timer getTimer();

   @Accessor
   int getRightClickDelay();

   @Accessor("rightClickDelay")
   void setRightClickDelay(int var1);

   @Invoker("startUseItem")
   void invokeStartUseItem();

   @Accessor("user")
   @Mutable
   void setUser(User var1);

   @Accessor("playerSocialManager")
   @Mutable
   void setPlayerSocialManager(PlayerSocialManager var1);

   @Accessor("profileKeyPairManager")
   @Mutable
   void setProfileKeyPairManager(ProfileKeyPairManager var1);

   @Accessor("reportingContext")
   @Mutable
   void setReportingContext(ReportingContext var1);

   @Accessor("userApiService")
   @Mutable
   void setUserApiService(UserApiService var1);

   @Invoker("createUserApiService")
   UserApiService invokeCreateUserApiService(YggdrasilAuthenticationService var1, GameConfig var2);

   @Accessor("authenticationService")
   YggdrasilAuthenticationService getAuthenticationService();
}
