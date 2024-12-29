package dev.syoritohatsuki.deathcounter.mixin;

import dev.syoritohatsuki.deathcounter.client.ClientConfigManager;
import dev.syoritohatsuki.deathcounter.client.extension.ExtensionsKt;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.c2s.play.ClientStatusC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {

    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "onPlayerRespawn", at = @At("TAIL"))
    public void onRespawn(PlayerRespawnS2CPacket packet, CallbackInfo ci) {

        if (client.player == null || client.getNetworkHandler() == null) return;

        client.getNetworkHandler().sendPacket(new ClientStatusC2SPacket(ClientStatusC2SPacket.Mode.REQUEST_STATS));

        var message = Text.translatableWithFallback("message.player.die", "You died %d times", (ExtensionsKt.getDeathCount(client.player) + 1));

        var config = ClientConfigManager.INSTANCE.read();

        if (!config.getChatMessage().getDisable()) {
            client.player.sendMessage(message);
        }

        if (!config.getTitleMessage().getDisable()) {
            client.inGameHud.setTitle(message);
            client.inGameHud.setTitleTicks(5, 5, config.getTitleMessage().getDelayInTicks());
        }
    }
}
