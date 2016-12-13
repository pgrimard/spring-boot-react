package com.patrickgrimard.examples

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

/**
 *
 *
 * Created on 2016-12-13
 *
 * @author Patrick
 */
@Component
open class ItemRepositoryInitializer(private val itemRepository: ItemRepository) : CommandLineRunner {
    override fun run(vararg args: String?) {
        itemRepository.save(Item("JavaScript", 0))
        itemRepository.save(Item("React", 1))
        itemRepository.save(Item("React Router", 2))
        itemRepository.save(Item("Redux", 0))
        itemRepository.save(Item("RxJS", 4))
    }
}