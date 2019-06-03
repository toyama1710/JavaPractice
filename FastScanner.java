import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class FastScanner {
    private int n;
    private long m;
    private int minus;
    
    private BufferedReader reader = null;
    private StringTokenizer tokenizer = null;

    public FastScanner(InputStream source) {
        reader = new BufferedReader(new InputStreamReader(source));
    }

    public String next() {
        if (tokenizer == null || !tokenizer.hasMoreTokens()) {
            try {
                tokenizer = new StringTokenizer(reader.readLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return tokenizer.nextToken();
    }

    public int nextInt() {
        n = 0;
        minus = 0;
        String str = next();

        if (str.charAt(0) == '-') {
            minus = 1;
        }

        for (int i = minus; i < str.length(); i++) {
            n *= 10;
            n += (int)(str.charAt(i) - '0');
        }

        if (minus == 1) {
            n *= -1;
        }

        return n;
    }

    public long nextLong() {
        m = 0;
        minus = 0;
        String str = next();

        if (str.charAt(0) == '-') {
            minus = 1;
        }

        for (int i = minus; i < str.length(); i++) {
            m *= 10;
            m += (int)(str.charAt(i) - '0');
        }

        if (minus == 1) {
            m *= -1;
        }

        return m;
    }
}
