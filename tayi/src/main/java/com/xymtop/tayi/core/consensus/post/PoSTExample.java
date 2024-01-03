package com.xymtop.tayi.core.consensus.post;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/3 17:30
 */
import java.util.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

public class PoSTExample {
    static class PoST {
        private final List<String> space;
        private final int spaceSize;

        public PoST(int spaceSize) {
            this.spaceSize = spaceSize;
            this.space = new ArrayList<>(spaceSize);
            allocateSpace();
        }

        private void allocateSpace() {
            for (int i = 0; i < spaceSize; i++) {
                space.add(generateData(i));
            }
        }

        private String generateData(int i) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(Integer.toString(i).getBytes(StandardCharsets.UTF_8));
                return bytesToHex(hash);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }

        private static String bytesToHex(byte[] hash) {
            StringBuilder hexString = new StringBuilder(2 * hash.length);
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        }

        public boolean verifyChallenge(String challenge) {
            long startTime = System.currentTimeMillis();
            boolean contains = space.contains(challenge);
            long endTime = System.currentTimeMillis();

            // 模拟时间证明
            long duration = endTime - startTime;
            if (duration > 1000) { // 假设验证需要超过1秒
                return contains;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        PoST post = new PoST(10000); // 分配空间

        // 挑战
        String challenge = post.generateData(5000);
        boolean isValid = post.verifyChallenge(challenge);

        System.out.println("验证结果: " + isValid);
    }
}
