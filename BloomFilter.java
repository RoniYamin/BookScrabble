package test;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;

public class BloomFilter {
    private BitSet bitSet;
    private int numHashFunctions;
    private String[] hashAlgorithms;

    public BloomFilter(int size, String... hashAlgorithms) {
        this.bitSet = new BitSet(size);
        this.numHashFunctions = hashAlgorithms.length;
        this.hashAlgorithms = hashAlgorithms;
    }

    public void add(String word) {
        for (String hash : hashAlgorithms) {
          try {
                MessageDigest md = MessageDigest.getInstance(hash);
                byte[] digest = md.digest(word.getBytes());
                BigInteger bigInt = new BigInteger(digest);
                int index = Math.abs(bigInt.intValue())% bitSet.size();
                bitSet.set(index);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public boolean contains(String word) {
        for (String hashAlgorithm : hashAlgorithms) {
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance(hashAlgorithm);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            byte[] digest = md.digest(word.getBytes());
            BigInteger bigInt = new BigInteger(digest);
            int index = Math.abs(bigInt.intValue())% bitSet.size();
            if(!bitSet.get(index))
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bitSet.length(); i++) {
            sb.append(bitSet.get(i) ? "1" : "0");
        }
        return sb.toString();
    }
}
