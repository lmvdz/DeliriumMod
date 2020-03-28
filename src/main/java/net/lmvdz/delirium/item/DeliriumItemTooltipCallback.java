/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.lmvdz.delirium.item;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.List;

public interface DeliriumItemTooltipCallback {

    /** Fired after thsee game has appended all ba tooltip lines to the list */
    Event<DeliriumItemTooltipCallback> EVENT = EventFactory.createArrayBacked(DeliriumItemTooltipCallback.class,
            (listeners) -> (stack, playerEntity, tooltipContext, lines) -> {
                for (DeliriumItemTooltipCallback callback : listeners) {
                    callback.getTooltip(stack, playerEntity, tooltipContext, lines);
                }
            });

    /**
     * Called when an item stack's tooltip is rendered. Text added to {@code lines}
     * will be rendered with the tooltip.
     * 
     * @param lines the list containing the lines of text displayed on the stack's
     *              tooltip
     */
    void getTooltip(ItemStack stack, PlayerEntity playerEntity, TooltipContext tooltipContext, List<Text> lines);

}