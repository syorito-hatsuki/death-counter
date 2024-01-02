package dev.syoritohatsuki.deathcounter.mixin;

import dev.syoritohatsuki.deathcounter.client.ClientConfigManager;
import dev.syoritohatsuki.deathcounter.client.extension.ExtensionsKt;
import dev.syoritohatsuki.deathcounter.mixin.accessor.ClientCommonNetworkHandlerAccessor;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.c2s.play.ClientStatusC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {

    @Inject(method = "onPlayerRespawn", at = @At("TAIL"))
    public void onRespawn(PlayerRespawnS2CPacket packet, CallbackInfo ci) {

        var client = ((ClientCommonNetworkHandlerAccessor) this).getClient();

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
