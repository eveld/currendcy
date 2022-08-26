package net.eveld.currendcy.mixin;

import net.eveld.currendcy.Currendcy;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.Map;

import com.google.gson.JsonElement;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {
		@Inject(method = "apply", at = @At("HEAD"))
		public void interceptApply(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo info) {
			map.put(Currendcy.BOOK_ID, Currendcy.BOOK_RECIPE);
			map.put(Currendcy.CHIP_ID, Currendcy.CHIP_RECIPE);
			map.put(Currendcy.CARD_ID, Currendcy.CARD_RECIPE);
			map.put(Currendcy.COLLECTOR_ID, Currendcy.COLLECTOR_RECIPE);
			map.put(Currendcy.DISPLAY_ID, Currendcy.DISPLAY_RECIPE);
			map.put(Currendcy.DISPENSER_ID, Currendcy.DISPENSER_RECIPE);
		}

}
