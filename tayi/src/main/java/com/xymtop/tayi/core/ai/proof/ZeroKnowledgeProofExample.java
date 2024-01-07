package com.xymtop.tayi.core.ai.proof;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2024/1/7 18:47
 */
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.agreement.jpake.JPAKEParticipant;
import org.bouncycastle.crypto.agreement.jpake.JPAKERound1Payload;
import org.bouncycastle.crypto.agreement.jpake.JPAKERound2Payload;
import org.bouncycastle.crypto.agreement.jpake.JPAKERound3Payload;
import org.bouncycastle.crypto.digests.SHA256Digest;

import java.math.BigInteger;



public class ZeroKnowledgeProofExample {
    public static void main(String[] args) throws CryptoException {
        // 创建两个JPAKE参与者，模拟证明者和验证者
        String participantId1 = "Prover";
        String participantId2 = "Verifier";
        String sharedPassword = "999";

        JPAKEParticipant participant1 = new JPAKEParticipant(participantId1, sharedPassword.toCharArray());
        JPAKEParticipant participant2 = new JPAKEParticipant(participantId2, sharedPassword.toCharArray());

        // 第一轮：交换初始贡献
        JPAKERound1Payload round1Payload1 = participant1.createRound1PayloadToSend();
        JPAKERound1Payload round1Payload2 = participant2.createRound1PayloadToSend();

        participant1.validateRound1PayloadReceived(round1Payload2);
        participant2.validateRound1PayloadReceived(round1Payload1);

        // 第二轮：交换知识证明
        JPAKERound2Payload round2Payload1 = participant1.createRound2PayloadToSend();
        JPAKERound2Payload round2Payload2 = participant2.createRound2PayloadToSend();

        participant1.validateRound2PayloadReceived(round2Payload2);
        participant2.validateRound2PayloadReceived(round2Payload1);

        // 第三轮：验证知识证明
        // 注意：这里应该使用从第二轮中得到的正确的秘密值
        BigInteger keyingMaterial1 = participant1.calculateKeyingMaterial();
        BigInteger keyingMaterial2 = participant2.calculateKeyingMaterial();

        JPAKERound3Payload round3Payload1 = participant1.createRound3PayloadToSend(keyingMaterial1);
        JPAKERound3Payload round3Payload2 = participant2.createRound3PayloadToSend(keyingMaterial2);

        participant1.validateRound3PayloadReceived(round3Payload2, keyingMaterial2);
        participant2.validateRound3PayloadReceived(round3Payload1, keyingMaterial1);

        System.out.println("Zero-Knowledge Proof Completed Successfully");
    }
}
