package com.meneghin.morefood;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MoreFood.MOD_ID)
public class MoreFood
{
    public static final String MOD_ID = "bnmf";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public MoreFood(){
    }
}
