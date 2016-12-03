package com.patrickgrimard.examples

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.rest.core.annotation.RepositoryRestResource

/**
 *
 *
 * Created on 2016-12-03
 *
 * @author Patrick
 */
@RepositoryRestResource
interface ItemRepository : JpaRepository<Item, String>, JpaSpecificationExecutor<Item>