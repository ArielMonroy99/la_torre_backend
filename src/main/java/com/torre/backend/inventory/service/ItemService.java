package com.torre.backend.inventory.service;

import com.torre.backend.common.dtos.QueryParamsDto;
import com.torre.backend.inventory.entities.Item;
import com.torre.backend.inventory.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static org.yaml.snakeyaml.nodes.Tag.STR;

@Service
@Slf4j
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Page<Item> getItems(QueryParamsDto queryParamsDto) {
        Integer page = queryParamsDto.getPage();
        Integer limit = queryParamsDto.getLimit();
        String filter = queryParamsDto.getFilter();
        String order = queryParamsDto.getOrder();
        String sort = queryParamsDto.getSort();
        filter = "%" + filter + "%";
        Pageable pageable = PageRequest.of(page,limit, Sort.Direction.valueOf(sort),order);
        Page<Item> pageItem = itemRepository.filterItems(filter, pageable);
        log.info("page {}", pageItem.getContent());
        return pageItem;
    }
}
