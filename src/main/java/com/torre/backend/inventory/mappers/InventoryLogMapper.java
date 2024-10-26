package com.torre.backend.inventory.mappers;

import com.torre.backend.authorization.entities.User;
import com.torre.backend.authorization.mappers.UserMapper;
import com.torre.backend.inventory.dto.CreateInventoryLogDto;
import com.torre.backend.inventory.dto.InventoryLogsDto;
import com.torre.backend.inventory.entities.InventoryLogs;
import com.torre.backend.inventory.entities.Item;

public class InventoryLogMapper {
    public static InventoryLogs toEntity(InventoryLogsDto inventoryLogsDto) {
        InventoryLogs inventoryLogsEntity = new InventoryLogs();
        inventoryLogsEntity.setId(inventoryLogsDto.getId());
        inventoryLogsEntity.setComment(inventoryLogsDto.getComment());
        inventoryLogsEntity.setDate(inventoryLogsDto.getDate());
        inventoryLogsEntity.setItem(ItemMapper.toEntity(inventoryLogsDto.getItem()));
        inventoryLogsEntity.setUser(UserMapper.toEntity(inventoryLogsDto.getUser()));
        return inventoryLogsEntity;
    }

    public static InventoryLogs createEntity(CreateInventoryLogDto inventoryLogsDto, Item item, User user) {
        InventoryLogs inventoryLogsEntity = new InventoryLogs();
        inventoryLogsEntity.setComment(inventoryLogsDto.getComment());
        inventoryLogsEntity.setItem(item);
        inventoryLogsEntity.setUser(user);
        return inventoryLogsEntity;
    }

    public static InventoryLogsDto toDto(InventoryLogs inventoryLogs) {
        InventoryLogsDto inventoryLogsDto = new InventoryLogsDto();
        inventoryLogsDto.setId(inventoryLogs.getId());
        inventoryLogsDto.setComment(inventoryLogs.getComment());
        inventoryLogsDto.setDate(inventoryLogs.getDate());
        inventoryLogsDto.setItem(ItemMapper.toDto(inventoryLogs.getItem()));
        inventoryLogsDto.setUser(UserMapper.toDto(inventoryLogs.getUser()));
        return inventoryLogsDto;
    }
}
