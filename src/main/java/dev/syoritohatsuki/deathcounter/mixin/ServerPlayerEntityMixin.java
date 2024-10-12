package dev.syoritohatsuki.deathcounter.mixin;

import dev.syoritohatsuki.deathcounter.event.PlayerDeathEvents;
import dev.syoritohatsuki.deathcounter.util.specialdays.SpecialDaysEvents;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
    @Inject(at = @At(value = "TAIL"), method = "onDeath")
    private void onPlayerDeath(DamageSource source, CallbackInfo info) {
        PlayerDeathEvents.INSTANCE.getDEATH().invoker().onDie((ServerPlayerEntity) (Object) this);
        SpecialDaysEvents.INSTANCE.grand((ServerPlayerEntity) (Object) this);
    }
}
