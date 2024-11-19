package com.torre.backend.inventory.service;

import com.torre.backend.authorization.entities.User;
import com.torre.backend.authorization.services.UserService;
import com.torre.backend.common.dtos.QueryParamsDto;
import com.torre.backend.common.exceptions.BaseException;
import com.torre.backend.common.utils.PageableMapper;
import com.torre.backend.inventory.dto.CreateInventoryLogDto;
import com.torre.backend.inventory.dto.InventoryLogsDto;
import com.torre.backend.inventory.entities.InventoryLogs;
import com.torre.backend.inventory.entities.Item;
import com.torre.backend.inventory.mappers.InventoryLogMapper;
import com.torre.backend.inventory.repository.InventoryLogsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

public class InventoryLogService {

        private final InventoryLogsRepository inventoryLogsRepository;
        private final UserService userService;
        private final ItemService itemService;

        public InventoryLogService(InventoryLogsRepository inventoryLogsRepository, UserService userService, ItemService itemService) {
            this.inventoryLogsRepository = inventoryLogsRepository;
            this.userService = userService;
            this.itemService = itemService;
        }

        Page<InventoryLogsDto> inventoryLogs(QueryParamsDto queryParamsDto) {
            Pageable page = PageableMapper.buildPage(queryParamsDto);
            String filter = queryParamsDto.getFilter();
            Page<InventoryLogs> inventoryLogs = inventoryLogsRepository.filterLogs(filter, page);
            return inventoryLogs.map(InventoryLogMapper::toDto);
        }

        Page<InventoryLogsDto> inventoryLogsProductId(Long id ,QueryParamsDto queryParamsDto) {
            Pageable page = PageableMapper.buildPage(queryParamsDto);
            String filter = queryParamsDto.getFilter();
            Page<InventoryLogs> inventoryLogs = inventoryLogsRepository.filterLogsByProductId(filter, id, page);
            return inventoryLogs.map(InventoryLogMapper::toDto);
        }

        void createInventoryLog(CreateInventoryLogDto inventoryLogsDto) {
            User user = userService.findById(inventoryLogsDto.getUserId());
            if(user == null) throw new BaseException(HttpStatus.NOT_FOUND,"User not found");
            Item item = itemService.findById(inventoryLogsDto.getItemId());
            if(item == null) throw new BaseException(HttpStatus.NOT_FOUND,"Item not found");
            InventoryLogs inventoryLogs = InventoryLogMapper.createEntity(inventoryLogsDto,item,user);
            inventoryLogs.setDate(LocalDateTime.now());
            inventoryLogsRepository.save(inventoryLogs);
        }
}
