package versatile.project.lauryl.payment.viewModel;


import android.text.TextUtils;

import org.apache.commons.codec.binary.Base64;

import java.security.SignatureException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.datatype.DatatypeFactory;


/**
 * This class defines common routines for generating
 * authentication signatures for Razorpay Webhook requests.
 */
public class Signature {
    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    public static boolean verifyPaymentSignature(String order_id, String razorpay_payment_id, String secret, String paymentSignature) {
        try {
            String generatedSignature = HMAC_SHA_256(order_id + "|" + razorpay_payment_id, secret);
            return TextUtils.equals(generatedSignature, paymentSignature);
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String HMAC_SHA_256(String data, String secret) throws java.security.SignatureException {
        String result;
        try {

            // get an hmac_sha256 key from the raw secret bytes
            SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(), HMAC_SHA256_ALGORITHM);

            // get an hmac_sha256 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            mac.init(signingKey);

            // compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(data.getBytes());

            // base64-encode the hmac
            String hash = Base64.encodeBase64String(rawHmac);

            result = hash;

        } catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }
        return result;
    }
}