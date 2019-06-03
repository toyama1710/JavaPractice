/**
 * @author Kojima
 * @version 1.0
 */

import java.util.*;

public abstract class SqrtDecomposition<T> {

    protected ArrayList<T> raw_data;
    protected ArrayList<T> bucket;

    protected T e; //単位元

    protected int bucket_size;
    protected int n;
    protected int K;

    abstract protected T operator(T x, T y);

    public SqrtDecomposition(int nmemb, T e) {
      
        this.e = e;

        n = nmemb;

        bucket_size = 1;

        while (bucket_size * bucket_size < nmemb) {
            bucket_size++;
        }

        K = (nmemb + bucket_size - 1) / bucket_size;

        build(nmemb, e);
    }

    // size個の要素datを持った平方分割テーブルを作成
    private void build(int size, T dat) {

        raw_data = new ArrayList<T>(size);
        bucket = new ArrayList<T>(K);

        for (Integer i = 0; i < size; i++) {
            raw_data.add(dat);
        }

        for (Integer i = 0; i < K; i++) {
            bucket.add(dat);
        }

        for (Integer i = 0; i < K; i++) {
            update(i * bucket_size, e);
        }
    }

    /**
     * @param k   更新場所
     * @param dat 更新データ
     */
    public void update(Integer k, T dat) {
        raw_data.set(k, dat);

        k /= bucket_size;

        T d = e;

        for (Integer i = k * bucket_size; i < Math.min((k + 1) * bucket_size, n); i++) {
            d = operator(d, raw_data.get(i));
        }
    
        bucket.set(k, d);
    }

    /**
     * [l, r)のクエリにこたえる
     * @param l 区間の左
     * @param r 区間の右
     * @return [l, r)区間に二項演算を適用した結果
     */
    public T query(Integer l, Integer r) {
        T d = e;
    
        Integer bucket_l, bucket_r;
    
        for (Integer k = 0; k < K; k++) {
            bucket_l = k * bucket_size;
            bucket_r = Math.min((k + 1) * bucket_size, n);
      
            // bucket[k]の区間がクエリ区間の外
            if (bucket_r <= l || r <= bucket_l) {
                continue;
            }
            // bucket[k]の区間がクエリ区間の中
            else if (l <= bucket_l && bucket_r <= r) {
                d = operator(d, bucket.get(k));
            }
            // bucket[k]の区間がクエリ区間と交差
            else {
                for (Integer i = Math.max(l, bucket_l); i < Math.min(r, bucket_r); i++) {
                    d = operator(d, raw_data.get(i));
                }
            }
        }
    
        return d;
    }

    /**
     * デバッグ情報を標準エラーに出力
     */
    public void debugPrint() {
        System.err.println("-bucket-");
        for (T p : bucket) {
            System.err.print(p);
            System.err.print(' ');
        }
        System.err.print('\n');
    
        System.err.println("-raw_data-");
        for (T q : raw_data) {
            System.err.print(q);
            System.err.print(' ');
        }
        System.err.print('\n');

        System.err.println("-other data-");

        System.err.printf("n: %d, bucket_size: %d, K: %d\n", n, bucket_size, K);

    }
}
