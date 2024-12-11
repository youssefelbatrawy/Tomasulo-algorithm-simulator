package com.tomasulo.memory;

import java.util.Arrays;

/**
 * A simplified direct-mapped cache implementation aligned with the Tomasulo simulation logic.
 * 
 * Assumptions:
 * - Each address corresponds to a single word.
 * - blockSize matches the word size in memory (e.g., 4 bytes for single-precision).
 * - The cache is direct-mapped, and each block holds exactly one word.
 * - Methods:
 *    checkHit(address) : Check if the address hits in the cache.
 *    read(address)     : Read the word at the given address if present (or null if miss).
 *    fill(address, data): Fill the cache with the given data at the specified address.
 */
public class Cache {
    private int cacheSize;
    private int blockSize;
    private CacheBlock[] cacheBlocks;

    public Cache(int cacheSize, int blockSize) {
        if (cacheSize % blockSize != 0) {
            throw new IllegalArgumentException("Cache size must be a multiple of block size.");
        }
        this.cacheSize = cacheSize;
        this.blockSize = blockSize;
        int numBlocks = cacheSize / blockSize;
        this.cacheBlocks = new CacheBlock[numBlocks];
        for (int i = 0; i < numBlocks; i++) {
            cacheBlocks[i] = new CacheBlock(blockSize);
        }
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public int getBlockCount() {
        return cacheBlocks.length;
    }

    /**
     * Check if the given address is a hit in the cache.
     */
    public boolean checkHit(int address) {
        int blockIndex = address % getBlockCount();
        int tag = address / getBlockCount();
        CacheBlock block = cacheBlocks[blockIndex];
        return block.isValid() && block.getTag() == tag;
    }

    /**
     * Read data for the given address from the cache if it is a hit.
     * Returns the word data if present, or null if not a hit.
     */
    public byte[] read(int address) {
        int blockIndex = address % getBlockCount();
        int tag = address / getBlockCount();

        CacheBlock block = cacheBlocks[blockIndex];
        if (block.isValid() && block.getTag() == tag) {
            return Arrays.copyOf(block.getData(), blockSize);
        } else {
            return null; // Miss
        }
    }

    /**
     * Fill the cache with the given data for the specified address.
     * This simulates bringing the data from memory on a miss.
     */
    public void fill(int address, byte[] data) {
        if (data.length != blockSize) {
            throw new IllegalArgumentException("Data size does not match block size.");
        }

        int blockIndex = address % getBlockCount();
        int tag = address / getBlockCount();

        CacheBlock block = cacheBlocks[blockIndex];
        block.setData(Arrays.copyOf(data, blockSize));
        block.setTag(tag);
        block.setValid(true);
        block.setDirty(false); // Assume we just loaded clean data.
    }

    // Inner class representing a single cache block
    private static class CacheBlock {
        private int tag;
        private boolean valid;
        private boolean dirty;
        private byte[] data;

        public CacheBlock(int blockSize) {
            this.data = new byte[blockSize];
            this.valid = false;
            this.dirty = false;
        }

        public int getTag() {
            return tag;
        }

        public void setTag(int tag) {
            this.tag = tag;
        }

        public boolean isValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }

        public boolean isDirty() {
            return dirty;
        }

        public void setDirty(boolean dirty) {
            this.dirty = dirty;
        }

        public byte[] getData() {
            return data;
        }

        public void setData(byte[] data) {
            this.data = data;
        }
    }
}