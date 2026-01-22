package walksy.customplayers.mixin;


import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import walksy.customplayers.config.Config;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

    @Redirect(method = "updateRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isInvisible()Z"))
    public boolean isInvisible(Entity instance) {
        Config.ModelEntry entry = Config.getEntry(instance);

        if (Config.modEnabled && !entry.showModel) {
            return !(Config.getEntry(instance).showModelIfInvis && instance instanceof LivingEntity living && living.isInvisible());
        }

        return instance.isInvisible();
    }
}
