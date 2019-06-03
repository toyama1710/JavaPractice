/**
 * @author ei1710
 * @version 1.00
 */

import java.util.ArrayList;
import java.util.Arrays;
 
//package toyama.ei1710.DataStructures;
/**
 * ���m�C�h���悹���镽�������̒��ۃN���X.<br>
 * operator���\�b�h���I�[�o���C�h���邱�Ƃœ񍀉��Z���`����<br>
 * �f�[�^�̋�ԍX�V�A��Ԃɑ΂��鎿��N�G����O(sqrt(N))�ōs��
 *
 * �X�V<br>
 * �X�V�����Ԃ��Abucket_i�̊Ǘ������Ԃ����S�Ɋ܂ނȂ�Alazy_i�ɒl������<br>
 * �������Ă���ꍇ�́A�Ώە����̃f�[�^���X�V���Ă���Abucket_i���X�V����<br>
 *
 * �N�G��<br>
 * ����̑Ώۋ�Ԃ��Abucket_i�����S�Ɋ܂ޏꍇ�Abucket_i��lazy_i���}�[�W���ĕԂ�
 * �������Ă��āA����lazy_i�ɒl������ꍇ�A���̋�Ԃ̃f�[�^���X�V��Abucket_i���X�V����
 * ����ƁA�ʏ�̕��������Ɠ����ɂȂ�
 *
 */
public abstract class LazySqrtDecomposition<T> {
    /** ���f�[�^ */
    protected ArrayList<T> raw_data;
    /** �o�P�b�g���ƂɎ��O�ɉ��Z�������ʂ�ێ� */
    protected ArrayList<T> bucket;
    /** �o�P�b�g�͈͂Ɉ�l�ɍ�p������l */
    protected ArrayList<T> lazy;
     
    /** lazy�Ƀf�[�^�͂��邩�ȁH */
    protected boolean[] lazy_flag;
    /** �P�ʌ� */
    protected T e;
    /** �f�[�^�� */
    protected int N;
 
    protected int sqrtN;
 
    /** �o�P�b�g�̐� */
    protected int K;
     
    /** �v�f�Ɨv�f�̍��� */
    abstract protected T f(T x, T y);
    /** �v�f�ƍ�p�f�̍��� bucket��lazy�̃}�[�W�Ɏg���� */
    abstract protected T g(T x, T y, int t);
    /** ��p�f�ƍ�p�f�̍��� lazy�Ƀf�[�^�������Ă�Ƃ��Ɏg����*/
    abstract protected T h(T x, T y);
     
    /** �P�ʌ�e�������l�Ƃ��ėv�f��nmemb�̕������� */
    public LazySqrtDecomposition(int nmemb, T e) {
        this.e = e;
        N = nmemb;
        sqrtN = 1;
        while (sqrtN * sqrtN < N) sqrtN++;
         
        K = (N + sqrtN - 1) / sqrtN;
         
        raw_data = new ArrayList<T>(N);
        bucket = new ArrayList<T>(K);
        lazy = new ArrayList<T>(K);
        lazy_flag = new boolean[K];
         
        build();
    }
     
    private void build() {
 
        for (int i = 0; i < N; i++) {
            raw_data.add(e);
        }
 
        for (int i = 0; i < K; i++) {
            bucket.add(e);
            lazy.add(e);
            lazy_flag[i] = false;
        }
    }
     
    /** bucket[k]�̋�Ԃɑ΂��āAlazy[k]��K�p������bucket[k]���Čv�Z�����肷�� */
    protected void eval(int k) {
         
        T x = e;
        for (int i = k * sqrtN; i < Math.min(N, (k + 1) * sqrtN); i++) {
            if (lazy_flag[k]) {
                raw_data.set(i, g(raw_data.get(i), lazy.get(k), 1));
            }
            x = f(x, raw_data.get(i));
        }
         
        lazy_flag[k] = false;
        bucket.set(k, x);
    }
 
     
    /** [l, r)��Ԃ�dat�ōX�V����� O(sqrt(N)) */
    public void update(int l, int r, T dat) {
        int s = l / sqrtN;
        int t = (r + sqrtN - 1) / sqrtN;
 
        int bucket_l, bucket_r;
         
        for (int i = s; i < t; i++) {
            bucket_l = i * sqrtN;
            bucket_r = (i + 1) * sqrtN;
             
            if (l <= bucket_l && bucket_r <= r) {
                if (lazy_flag[i]) {
                    lazy.set(i, h(lazy.get(i), dat));
                }
                else {
                    lazy.set(i, dat);
                }
                lazy_flag[i] = true;
            }
            else {
                eval(i);
                for (int j = Math.max(bucket_l, l); j < Math.min(bucket_r, r); j++) {
                    raw_data.set(j, g(raw_data.get(j), dat, 1));
                }
            }
        }
    }
 
    /** [l, r)�̋�ԃN�G���ɓ����� O(sqrt(N)) */
    public T query(int l, int r) {
        int s = l / sqrtN;
        int t = (r + sqrtN - 1) / sqrtN;
        int bucket_l, bucket_r;
        T x = e;
         
        for (int i = s; i < t; i++) {
            bucket_l = i * sqrtN;
            bucket_r = (i + 1) * sqrtN;
            if (l <= bucket_l && bucket_r <= r) {
                if (lazy_flag[i]) {
                    x = f(x, g(bucket.get(i), lazy.get(i), 1));
                }
                else {
                    x = f(x, bucket.get(i));
                }
            }
            else {
                eval(i);
                 
                for (int j = Math.max(bucket_l, l); j < Math.min(bucket_r, r); j++) {
                    x = f(x, raw_data.get(j));
                }
            }
        }
        return x;
    }
     
    /** �f�o�b�O���̈� */
    public void debugPrint() {
         
        System.err.println("-lazy-");
        for (int i = 0; i < lazy.size(); i++) {
            if (!lazy_flag[i]) {
                System.err.print(" --  ");
            }
            else {
                System.err.print(lazy.get(i));
                System.err.print(' ');
            }
        }
        System.err.print('\n');
         
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
        System.err.printf("N: %d, sqrtN: %d, K: %d\n", N, sqrtN, K);
    }
}