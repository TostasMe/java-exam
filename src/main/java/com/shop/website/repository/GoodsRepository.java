package com.shop.website.repository;

import com.shop.website.models.Goods;
import org.springframework.data.repository.CrudRepository;

public interface GoodsRepository extends CrudRepository<Goods, Long> {
}
