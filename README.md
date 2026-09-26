# Itens Clumps

A lightweight performance mod for NeoForge that merges identical item stacks lying on the ground into a single entity, **with no stack size limit**, without ever touching how items stack inside inventories.

## The problem

Vanilla Minecraft already merges nearby dropped items of the same type, but it stops at the item's normal max stack size (usually 64). Drop a stack of loot from a large farm, a mob grinder, or a chest full of the same item, and you end up with dozens of separate item entities littering the ground, each one costing the server tick time, network bandwidth, and client rendering.

## What this mod does

- Ground items of the same type merge into **one single stack**, with no upper limit on the count.
- Merging happens **instantly** when an item lands near an existing pile, and is also **rechecked periodically** so items that drift close together over time (falling, sliding, water flow) still merge.
- **Inventories are never affected.** Picking up a giant pile still fills your inventory in normal-sized stacks (64, 16, 1...), exactly like vanilla.

## Compatibility

- Requires installation on **both client and server** (needed for a custom entity used for stacks larger than the vanilla limit).
- Items belonging to other mods that use their own custom item-entity type are **left untouched**, they simply won't be merged by this mod, but nothing about them is modified or broken.
- Ground items that another mod has attached its own data to (for example, items being processed by a Create fan) are **never merged**, so in-world processing from other mods keeps working as intended.

## Known limitations

- `/kill @e[type=item]` will **not** remove oversized merged piles, since they use a different entity type once they grow past the normal stack limit. Use `/kill @e[type=itens_clumps:clump_item]` instead.
- If this mod is removed from a world, any oversized pile that was on the ground **at that exact moment** will be lost when the chunk is reloaded, since the game no longer recognizes the custom entity type. Regular-sized item stacks are completely unaffected.

## Installation

1. Install [NeoForge](https://neoforged.net/) for Minecraft 1.21.1.
2. Drop the mod's `.jar` file into your `mods` folder.

## License

All Rights Reserved.

## Credits

Created by **Topiinho**.
