/**
 * @author ei1710
 * @version 1.01
 */

/**
 * モノイドを乗せられるセグメントツリーの抽象クラス<br>
 * operator メソッドをオーバライドすることで二項演算を定義する<br>
 * データの1点更新と区間に対する質問クエリをO(log(N))で行う
 */
abstract class SegmentTree<T> {

    /** 単位元 */
    protected T e;

    protected int n;
    protected ArrayList<T> tree;

    
    abstract protected T operator(T x, T y);

    /**
     * 要素eで埋められた要素数nmemb個のセグ木を生成
     * @param nmemb 要素数
     * @param e 初期要素(単位元であること)
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
     * データを更新する.<br>
     * O(log(N))
     * @param k データの位置
     * @param dat 更新データ
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
     * [l, r)区間の二項演算の結果を返す.<br>
     * O(log(N))
     * @param l 区間の左
     * @param r 区間の右
     * @return 演算結果
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
     * デバッグ情報の出力
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
