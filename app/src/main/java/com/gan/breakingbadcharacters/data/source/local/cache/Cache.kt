package com.gan.breakingbadcharacters.data.source.local.cache

interface Cache<Key : Any, Value : Any> {
    fun <T> load(key: Key): T?
    fun save(key: Key, value: Value)
}