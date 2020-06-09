## EquipmentMaterial - Automatic Item/Tool/Armor Generation

Start building your `EquipmentMaterial` by calling the static `EquipmentMaterial.builder()` method.
- `EquipmentMaterial.builder()` will create a new `EquipmentMaterial.Builder` instance.
- Supply your String MODID and ItemName either as two seperate parameters or as an Identifier.
- If you just want to use the default values for tools and/or armor just call `.build()` on your `EquipmentMaterial.Builder` instance!
- After building your ToolMaterial and/or ArmorMaterial with the `.build()` or `.buildArmor()` or `.buildTool()`
  - call `.createArmorAndTools()` or `.createTools()` or `.createArmor()` or supply additional parameters to these methods to choose specifically which armor and which tools you want to create.

``` java
//      EquipmentMaterial.builder(new Identifier(MODID, "Delinium") -- alternative
        EquipmentMaterial.builder(MODID, "Delinium")
            .attackDamage(2.5f)
            .durability(1000)
            .enchantability(25)
            .miningLevel(1)
            .miningSpeed(5f)
            .attackSpeed(1f)
            .armorDurabilities(1000, 1000, 1000, 1000)
    //	    .armorDurability(EquipmentSlot.HEAD, 10000) -- alternative
            .armorProtectionAmounts(10, 10, 10, 10)
    //	    .armorProtectionAmount(EquipmentSlot.FEET, 20) -- alternative
            .armorEnchantability(1)
            .equipSound(SoundEvents.ITEM_ARMOR_EQUIP_GENERIC)
            .toughness(10)
            .knockbackResistance(5)
    //	    .buildArmor() -- only create the Armor Material --- won't be able to create tools
    //	    .buildTool() -- only create the Tool Material --- wont't be able to create armor
            .build()
    //	    .armorMaterial(CustomArmorMaterial customArmorMaterial) --- set the custom armor material -- useful if you only buildToolMaterial() and later on need to create armor
    //	    .toolMaterial(CustomToolMaterial customToolMaterial) --- set the custom tool material -- useful if you only buildArmorMaterial() and later on need to create tools
    //	    .createArmor() -- create all armor
    //	    .createArmor(new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.FEET}) --- alternative
    //	    .createArmor(EquipmentSlot.HEAD) --- alternative
            .createArmor(true, false, true, false)
    //	    .createTools() -- create all tools
    //	    .createTools(new EquipmentMaterial.ToolType[] {EquipmentMaterial.ToolType.PICKAXE})  --- alternative
    //	    .createTool(EquipmentMaterial.ToolType.PICKAXE) --- alternative
            .createTools(false, true, false, false, true, false);
```