package com.patrickgrimard.examples;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created on 2016-11-25
 *
 * @author Patrick
 */
@RepositoryRestResource
public interface ItemRepository extends CrudRepository<Item, String> {
}
