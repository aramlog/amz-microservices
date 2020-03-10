package com.amz.microservices.common.security.manager;

import com.amz.microservices.common.security.config.properties.JwtConfigProperties;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.function.Function;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;

@Data
@Component
@RequiredArgsConstructor
public class CertificateManager {

  private final JwtConfigProperties jwtConfigProperties;

  public PublicKey publicKey() {
    String publicKeyPath = jwtConfigProperties.getPublicKeyPath();
    SignatureAlgorithm signatureAlgorithm = jwtConfigProperties.getSignatureAlgorithm();

    Assert.notNull(publicKeyPath, "Public key path is required");

    try {
      KeyFactory keyFactory = KeyFactory.getInstance(signatureAlgorithm.getFamilyName());
      return readKey(publicKeyPath, (bytes -> {
        try {
          return keyFactory.generatePublic(new X509EncodedKeySpec(bytes));
        } catch (InvalidKeySpecException e) {
          throw new RuntimeException("InvalidKeySpecException", e);
        }
      }));
    } catch (Exception ex) {
      throw new RuntimeException("Exception occurred when retrieving public key", ex);
    }
  }

  public PrivateKey privateKey() {
    String privateKeyPath = jwtConfigProperties.getPrivateKeyPath();
    SignatureAlgorithm signatureAlgorithm = jwtConfigProperties.getSignatureAlgorithm();

    Assert.notNull(privateKeyPath, "Private key path is required");

    try {
      KeyFactory keyFactory = KeyFactory.getInstance(signatureAlgorithm.getFamilyName());
      return readKey(privateKeyPath, (bytes -> {
        try {
          return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(bytes));
        } catch (InvalidKeySpecException ex) {
          throw new RuntimeException("InvalidKeySpecException", ex);
        }
      }));
    } catch (Exception ex) {
      throw new RuntimeException("Exception occurred when retrieving private key", ex);
    }
  }

  private <T extends Key> T readKey(String keyPath, Function<byte[], T> keyReader) throws IOException {
    return keyReader.apply(FileCopyUtils.copyToByteArray(new ClassPathResource(keyPath).getInputStream()));
  }

}
