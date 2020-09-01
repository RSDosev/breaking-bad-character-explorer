package com.gan.breakingbadcharacters.data.source.local.cache

import androidx.collection.LruCache

const val DEFAULT_CACHE_SIZE = 4 * 1024 * 1024 // 4Mb

class MemoryCache(cacheSize: Int = DEFAULT_CACHE_SIZE) : Cache<String, Any> {

    private val lruCache: LruCache<String, Any> by lazy { LruCache<String, Any>(cacheSize) }

    @Suppress("UNCHECKED_CAST")
    override fun <T> load(key: String): T? {
        return lruCache.get(key)?.let {
            it as T
        }
    }

    override fun save(key: String, value: Any) {
        lruCache.put(key, value)
    }
}
