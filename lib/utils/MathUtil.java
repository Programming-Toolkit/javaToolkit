package javaToolkit.lib.utils;

import java.math.BigInteger;

public class MathUtil {

	public static BigInteger binomial(int N, int K) {
		BigInteger ret = BigInteger.ONE;
		for (int k = 0; k < K; k++) {
			ret = ret.multiply(BigInteger.valueOf(N - k)).divide(BigInteger.valueOf(k + 1));
		}
		return ret;
	}

}
