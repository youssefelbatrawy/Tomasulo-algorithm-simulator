package com.tomasulo.memory;

import com.tomasulo.predefintions.MemorySize;
import java.util.Arrays;

public class Memory {
    private int memorySize;
    private MemorySize memorySizeType;
    private byte[] memoryArray;

    public Memory(int memorySize, MemorySize memorySizeType) {
        this.memorySize = memorySize;
        this.memorySizeType = memorySizeType;
        this.memoryArray = new byte[memorySize * getSizeInBytes(memorySizeType)];
    }

    public int getMemorySize() {
        return memorySize;
    }

    public MemorySize getMemorySizeType() {
        return memorySizeType;
    }

    private int getSizeInBytes(MemorySize sizeType) {
        switch (sizeType) {
            case Single:
                return 4;
            case Double:
                return 8;
            default:
                throw new IllegalArgumentException("Unsupported MemorySize type.");
        }
    }

    public byte[] read(int address) {
        if (address < 0 || address >= memorySize) {
            throw new IllegalArgumentException("Invalid memory address.");
        }
        int wordSizeInBytes = getSizeInBytes(memorySizeType);
        int startIndex = address * wordSizeInBytes;
        byte[] word = new byte[wordSizeInBytes];
        System.arraycopy(memoryArray, startIndex, word, 0, wordSizeInBytes);
        return word;
    }

    public void write(int address, byte[] data) {
        if (address < 0 || address >= memorySize) {
            throw new IllegalArgumentException("Invalid memory address.");
        }
        int wordSizeInBytes = getSizeInBytes(memorySizeType);
        if (data.length != wordSizeInBytes) {
            throw new IllegalArgumentException("Data size does not match word size.");
        }
        int startIndex = address * wordSizeInBytes;
        System.arraycopy(data, 0, memoryArray, startIndex, wordSizeInBytes);
    }

    public static void main(String[] args) {
        Memory memory = new Memory(10, MemorySize.Single);

        for (int i = 0; i < memory.getMemorySize(); i++) {
            byte[] data = {(byte) i, (byte) (i + 1), (byte) (i + 2), (byte) (i + 3)};
            memory.write(i, data);
        }

        for (int i = 0; i < memory.getMemorySize(); i++) {
            System.out.println("Address " + i + ": " + Arrays.toString(memory.read(i)));
        }

        Memory doubleWordMemory = new Memory(5, MemorySize.Double);
        for (int i = 0; i < doubleWordMemory.getMemorySize(); i++) {
            byte[] data = {(byte) i, (byte) (i + 1), (byte) (i + 2), (byte) (i + 3), (byte) (i + 4), (byte) (i + 5), (byte) (i + 6), (byte) (i + 7)};
            doubleWordMemory.write(i, data);
        }

        for (int i = 0; i < doubleWordMemory.getMemorySize(); i++) {
            System.out.println("Address " + i + ": " + Arrays.toString(doubleWordMemory.read(i)));
        }
    }
}