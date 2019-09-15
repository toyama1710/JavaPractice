/**
 * @author ei1710
 * @version 1.01
 */

/**
 * ���m�C�h���悹����Z�O�����g�c���[�̒��ۃN���X<br>
 * operator ���\�b�h���I�[�o���C�h���邱�Ƃœ񍀉��Z���`����<br>
 * �f�[�^��1�_�X�V�Ƌ�Ԃɑ΂��鎿��N�G����O(log(N))�ōs��
 */
abstract class SegmentTree<T> {

    /** �P�ʌ� */
    protected T e;

    protected int n;
    protected ArrayList<T> tree;

    
    abstract protected T operator(T x, T y);

    /**
     * �v�fe�Ŗ��߂�ꂽ�v�f��nmemb�̃Z�O�؂𐶐�
     * @param nmemb �v�f��
     * @param e �����v�f(�P�ʌ��ł��邱��)
     */
    public SegmentTree(int nmemb, T e) {
        this.e = e;
        
        n = 1;
        while (n < nmemb) {
            n *= 2;
        }

        tree = new ArrayList<T>(2 * n - 1);
        for (int i = 0; i < 2 * n - 1; i++) {
            tree.add(e);
        }
    }

    /**
     * �f�[�^���X�V����.<br>
     * O(log(N))
     * @param k �f�[�^�̈ʒu
     * @param dat �X�V�f�[�^
     */
    public void update(int k, T dat) {
        k += n - 1;

        tree.set(k, dat);

        while(k > 0) {
            k = (k - 1) / 2;
            tree.set(k, operator(tree.get(k * 2 + 1), tree.get(k * 2 + 2)));
        }
    }
    
    /**
     * [l, r)��Ԃ̓񍀉��Z�̌��ʂ�Ԃ�.<br>
     * O(log(N))
     * @param l ��Ԃ̍�
     * @param r ��Ԃ̉E
     * @return ���Z����
     */
    public T query(int l, int r) {
        l += n - 1;
        r += n - 1;

		T vl = e;
		T vr = e;
        while (l < r) {
            if (l % 2 == 0) {
                vl = operator(vl, tree.get(l));
                l++;
            }
            if (r % 2 == 0) {
                r--;
                vr = operator(tree.get(r), vr);
            }

            l = (l - 1) / 2;
            r = (r - 1) / 2;
        }

        return f(vl, vr);
    }

    /**
     * �f�o�b�O���̏o��
     */
    public void debugPrint() {
        int cnt = 1;
        int i = 0;

        System.err.printf("n:%d tree.size():%d\n", n, tree.size());

        while (i < 2 * n - 1) {
            for (int j = 0; j < cnt; i++, j++) {
                System.err.printf(" [%d]: ", i);
                System.err.print(tree.get(i));
            }
            System.err.println("");
            
            cnt *= 2;
        }
    }
}
