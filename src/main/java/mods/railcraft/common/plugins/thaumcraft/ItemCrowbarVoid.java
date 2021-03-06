package mods.railcraft.common.plugins.thaumcraft;

import mods.railcraft.common.core.RailcraftConfig;
import mods.railcraft.common.items.ItemCrowbar;
import mods.railcraft.common.plugins.forge.HarvestPlugin;
import mods.railcraft.common.plugins.forge.RailcraftRegistry;
import mods.railcraft.common.util.misc.Game;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import thaumcraft.api.IRepairable;
import thaumcraft.api.IWarpingGear;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;

public class ItemCrowbarVoid extends ItemCrowbar implements IRepairable, IWarpingGear {

    public static final String ITEM_TAG = "railcraft.tool.crowbar.void";
    public static final String RESEARCH_TAG = "RC_Crowbar_Void";
    public static Item item;

    public static void registerItem() {
        if (item == null && RailcraftConfig.isItemEnabled(ITEM_TAG)) {
            item = new ItemCrowbarVoid();
            RailcraftRegistry.register(item);
            HarvestPlugin.setToolClass(item, "crowbar", 0);
        }
    }

    public static void registerResearch() {
        try {
            IArcaneRecipe recipe = ThaumcraftApi.addArcaneCraftingRecipe(RESEARCH_TAG, new ItemStack(item),
                    new AspectList().add(Aspect.ENTROPY, 50),
                    " RI",
                    "RIR",
                    "IR ",
                    'I', ThaumcraftPlugin.getItem("itemResource", 16),
                    'R', "dyeRed");

            AspectList aspects = new AspectList();
            aspects.add(Aspect.TOOL, 2).add(Aspect.MECHANISM, 4).add(Aspect.TRAVEL, 2);

            ResearchItemRC voidCrowbar = new ResearchItemRC(RESEARCH_TAG, ThaumcraftPlugin.RESEARCH_CATEGORY, aspects, 0, 1, 3, new ItemStack(item));
            voidCrowbar.setPages(new ResearchPage[]{ThaumcraftPlugin.getResearchPage(RESEARCH_TAG), new ResearchPage(recipe)})
                    .setParents(ItemCrowbarMagic.RESEARCH_TAG).setParentsHidden("VOIDMETAL")
                    .registerResearchItem();

        } catch (Throwable error) {
            Game.logErrorAPI("Thaumcraft", error, ResearchItem.class);
        }
    }

    public static ItemStack getItem() {
        if (item == null)
            return null;
        return new ItemStack(item);
    }

    public ItemCrowbarVoid() {
        super(ThaumcraftPlugin.getVoidmetalToolMaterial());
        setUnlocalizedName(ITEM_TAG);
    }

    @Override
    public int getWarp(ItemStack itemstack, EntityPlayer player) {
        return 1;
    }

}
