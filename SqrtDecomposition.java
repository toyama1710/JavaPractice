/**
 * @author Kojima
 * @version 1.0
 */

import java.util.*;

public abstract class SqrtDecomposition<T> {

    protected ArrayList<T> raw_data;
    protected ArrayList<T> bucket;

    protected T e; //�P�ʌ�

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

    // size�̗v�fdat�����������������e�[�u�����쐬
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
     * @param k   �X�V�ꏊ
     * @param dat �X�V�f�[�^
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
     * [l, r)�̃N�G���ɂ�������
     * @param l ��Ԃ̍�
     * @param r ��Ԃ̉E
     * @return [l, r)��Ԃɓ񍀉��Z��K�p��������
     */
    public T query(Integer l, Integer r) {
        T d = e;
    
        Integer bucket_l, bucket_r;
    
        for (Integer k = 0; k < K; k++) {
            bucket_l = k * bucket_size;
            bucket_r = Math.min((k + 1) * bucket_size, n);
      
            // bucket[k]�̋�Ԃ��N�G����Ԃ̊O
            if (bucket_r <= l || r <= bucket_l) {
                continue;
            }
            // bucket[k]�̋�Ԃ��N�G����Ԃ̒�
            else if (l <= bucket_l && bucket_r <= r) {
                d = operator(d, bucket.get(k));
            }
            // bucket[k]�̋�Ԃ��N�G����Ԃƌ���
            else {
                for (Integer i = Math.max(l, bucket_l); i < Math.min(r, bucket_r); i++) {
                    d = operator(d, raw_data.get(i));
                }
            }
        }
    
        return d;
    }

    /**
     * �f�o�b�O����W���G���[�ɏo��
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
