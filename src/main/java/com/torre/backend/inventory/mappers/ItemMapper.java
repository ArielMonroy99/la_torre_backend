package com.torre.backend.inventory.mappers;

import com.torre.backend.inventory.dto.CreateItemDto;
import com.torre.backend.inventory.dto.ItemDto;
import com.torre.backend.inventory.entities.Category;
import com.torre.backend.inventory.entities.Item;

public class ItemMapper {
    public static ItemDto toDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setStock(item.getStock());
        itemDto.setMinimumStock(item.getMinimumStock());
        itemDto.setUnit(item.getUnit());
        itemDto.setType(item.getType());
        itemDto.setCategory(CategoryMapper.toDto(item.getCategory()));
        return itemDto;
    }
    public static Item toEntity(CreateItemDto itemDto, Category category) {
        Item item = new Item();
        return getItem(item, itemDto, category);
    }
    public static Item updateEntity(Item item, CreateItemDto itemDto, Category category) {
        return getItem(item, itemDto, category);
    }

    private static Item getItem(Item item, CreateItemDto itemDto, Category category) {
        item.setName(itemDto.getName());
        item.setStock(itemDto.getStock());
        item.setMinimumStock(itemDto.getMinimumStock());
        item.setUnit(itemDto.getUnit());
        item.setType(itemDto.getType());
        item.setCategory(category);
        return item;
    }
}
