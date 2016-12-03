package com.patrickgrimard.examples

import javax.persistence.Entity
import javax.persistence.Id

/**
 *
 *
 * Created on 2016-12-03
 *
 * @author Patrick
 */
@Entity
data class Item(@Id val name: String, val quantity: Long = 0) {
    private constructor() : this("", 0)
}